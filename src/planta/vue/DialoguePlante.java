package planta.vue;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import planta.exceptions.PlantaException;
import planta.modele.Catalogue;
import planta.modele.Plante;
import planta.modele.TypePlante;
import java.io.File;
import java.util.Optional;


public class DialoguePlante {

    public static void afficher(Window owner, Plante plante) {
        // 1. Initialisation du dialogue
        boolean estModification   = plante != null;
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(owner);
        dialog.setTitle(estModification ? "Modifier une plante" : "Ajouter une plante");
        dialog.setHeaderText(estModification ? "Mets à jour les infos de ta plante 🌱" : "Remplis les infos de la nouvelle plante 🌿");

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 2. Création des champs, les prelemplis si nécessaire
        TextField nom         = new TextField(estModification ? plante.getNom() : "");
        TextField description = new TextField(estModification ? plante.getDescription() : "");
        TextField origine     = new TextField(estModification ? plante.getOrigine() : "");
        TextField prix        = new TextField(estModification ? String.valueOf(plante.getPrix()) : "");

        Spinner<Integer> tempMinSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, estModification  ? plante.getTemperature()[0] : 0));
        Spinner<Integer> tempMaxSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, estModification  ? plante.getTemperature()[1] : 50));
        Spinner<Integer> humMinSpinner  = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, estModification ? plante.getHumidite()[0]    : 0 ));
        Spinner<Integer> humMaxSpinner  = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, estModification ? plante.getHumidite()[1]    : 100));

        HBox tempBox = new HBox(5, tempMinSpinner, tempMaxSpinner);
        HBox humBox  = new HBox(5, humMinSpinner, humMaxSpinner);

        // Choix Intérieur / Extérieur
        ChoiceBox<TypePlante> typeChoix = new ChoiceBox<>();
        typeChoix.getItems().addAll(TypePlante.INTERIEUR, TypePlante.EXTERIEUR);
        typeChoix.getSelectionModel().select(estModification ? plante.getType() : TypePlante.INTERIEUR);

        // Sélection d'image
        Button imageBouton      = new Button(estModification ? "Changer l'image..." : "Choisir l'image...");
        FileChooser fileChooser = new FileChooser();
        File[] fichier          = new File[1];
        imageBouton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(owner);
            if (file != null) {
                fichier[0] = file;
                imageBouton.setText(file.getName());
            }
        });

        // 3. Mise en page
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nom :"), nom);
        grid.addRow(1, new Label("Description :"), description);
        grid.addRow(2, new Label("Origine :"), origine);
        grid.addRow(3, new Label("Prix (€) :"), prix);
        grid.addRow(4, new Label("Type :"), typeChoix);
        grid.addRow(5, new Label("Température (min/max) :"), tempBox);
        grid.addRow(6, new Label("Humidité (min/max) :"), humBox);
        grid.addRow(7, new Label("Image :"), imageBouton);
        pane.setContent(grid);

        // 4. Affichage et récupération du résultat
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            // Création ou mise à jour
            if(!checkChampsValides(estModification, nom, description, origine, prix, tempMinSpinner, tempMaxSpinner, humMinSpinner, humMaxSpinner)) {
                return;
            }

            Plante p = estModification ? plante : new Plante();
            p.setNom(nom.getText());
            p.setDescription(description.getText());
            p.setOrigine(origine.getText());
            p.setType(typeChoix.getValue());
            p.setPrix(Double.parseDouble(prix.getText()));
            p.setTemperature(new int[] { tempMinSpinner.getValue(), tempMaxSpinner.getValue() });
            p.setHumidite(new int[]    { humMinSpinner.getValue(),  humMaxSpinner.getValue() });
            if (fichier[0] != null)    { p.setImage(fichier[0].toURI().toString()); }

            if(!estModification) {
                Catalogue.ajouterPlante(p);
            }
        }
    }

    private static boolean checkChampsValides(boolean estModification, TextField nom, TextField description, TextField origine, TextField prix, Spinner<Integer> tempMinSpinner, Spinner<Integer> tempMaxSpinner, Spinner<Integer> humMinSpinner, Spinner<Integer> humMaxSpinner) {
        if(nom.getText().isEmpty())                               { PlantaException.alert("Erreur de modification", "Oups ! Le champ 'Nom' ne peut pas être vide 🌱");                          return false; }
        if(description.getText().isEmpty())                       { PlantaException.alert("Erreur de modification", "Eh bien ! Une plante sans description, c'est un peu triste 😢");           return false; }
        if(origine.getText().isEmpty())                           { PlantaException.alert("Erreur de modification", "Pas d'origine ? Mais d'où vient-elle alors ? 🧐");                         return false; }
        if(tempMinSpinner.getValue() > tempMaxSpinner.getValue()) { PlantaException.alert("Erreur de modification", "Température incohérente 🌡️ : la valeur minimale dépasse la maximale !");   return false; }
        if(humMinSpinner.getValue() > humMaxSpinner.getValue())   { PlantaException.alert("Erreur de modification", "Humidité bizarre 💧 : la valeur minimale est supérieure à la maximale !"); return false; }

        try {
            Double.parseDouble(prix.getText());
        } catch (Exception ex) {
            PlantaException.alert(estModification ? "Erreur de modification" : "Erreur d'ajout", "Le prix doit être un nombre valide 💰");
        }

        return true;
    }
}