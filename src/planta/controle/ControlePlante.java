package planta.controle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import planta.modele.Catalogue;
import planta.modele.Plante;
import planta.vue.Loader;
import java.io.File;
import java.util.Optional;


public class ControlePlante {

    @FXML private ImageView image;
    @FXML private Label     nom;
    @FXML private Label     type;
    @FXML private Label     description;
    @FXML private Label     eau;
    @FXML private Label     toxique;
    @FXML private Label     origine;
    @FXML private Label     temperature;
    @FXML private Label     humidite;
    @FXML private Label     prix;

    private ObservableList<Plante> plantes;
    private int                    index;

    public void init(int index) {
        this.index   = index;
        this.plantes = Catalogue.getPlantes();

        this.initPlante();
    }

    private void initPlante() {
        Plante plante = this.plantes.get(index);

        this.nom.setText(plante.getNom());
        this.type.setText(plante.getType().name().toLowerCase());
        this.description.setText(plante.getDescription());
        this.eau.setText(String.format("%d mL", plante.getEau()));
        this.toxique.setText(plante.getEstToxique() ? "Oui" : "Non");
        this.origine.setText(plante.getOrigine());
        this.prix.setText(String.format("%.2f €", plante.getPrix()));
        this.temperature.setText(String.format("%d°C - %d°C", plante.getTemperature()[0], plante.getTemperature()[1]));
        this.humidite.setText(String.format("%d%% - %d%%", plante.getHumidite()[0], plante.getHumidite()[1]));

        try {
            this.image.setImage(new Image(plante.getImage(), 256, 256, false, true));
            Rectangle clip = new Rectangle(256, 256);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            this.image.setClip(clip);
        } catch (Exception ignored) {}

    }

    // nav
    @FXML private void onSuivant()   { this.allerA((this.index + 1) % this.plantes.size()); }
    @FXML private void onPrecedent() { this.allerA((this.index - 1 + this.plantes.size()) % this.plantes.size()); }

    private void allerA(int index) {
        Stage stage = (Stage) this.nom.getScene().getWindow();
        stage.setScene(Loader.afficherDetail(index));
    }

    @FXML private void onSupprimer() {
        this.plantes.remove(index);
        this.onRetourCatalogue();
    }

    @FXML private void onRetourCatalogue() {
        Stage stage = (Stage) this.nom.getScene().getWindow();
        stage.setScene(Loader.afficherCatalogue());
    }

    @FXML private void onQuitter() {
        Platform.exit();
    }

    @FXML
    public void onEditer() {
        Plante plante = this.plantes.get(index);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier la plante");
        dialog.setHeaderText("Éditer les informations de la plante");

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField newNom         = new TextField(plante.getNom());
        TextField newDescription = new TextField(plante.getDescription());
        TextField newOrigine     = new TextField(plante.getOrigine());
        TextField newPrix        = new TextField(Double.toString(plante.getPrix()));

        Spinner<Integer> temperatureSpinnerMin = new Spinner<>();
        Spinner<Integer> temperatureSpinnerMax = new Spinner<>();
        Spinner<Integer> humiditeSpinnerMin    = new Spinner<>();
        Spinner<Integer> humiditeSpinnerMax    = new Spinner<>();

        temperatureSpinnerMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, plante.getTemperature()[0]));
        temperatureSpinnerMax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, plante.getTemperature()[1]));
        humiditeSpinnerMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, plante.getHumidite()[0]));
        humiditeSpinnerMax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, plante.getHumidite()[1]));

        HBox newTemperature = new HBox(5, temperatureSpinnerMin, temperatureSpinnerMax);
        HBox newHumidite    = new HBox(5, humiditeSpinnerMin, humiditeSpinnerMax);

        Button newImage         = new Button("Choisir l'image...");
        FileChooser fileChooser = new FileChooser();
        File[] fichier          = new File[1]; // ca me pose une erreur si je met new File() uniquement : `atomic error`
        newImage.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(this.nom.getScene().getWindow()); // this.nom mais on peut mettre n'importe quel autre champ
            if (file != null) {
                fichier[0] = file;
                newImage.setText(file.getName());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nom:"), newNom);
        grid.addRow(1, new Label("Description:"), newDescription);
        grid.addRow(2, new Label("Origine:"), newOrigine);
        grid.addRow(3, new Label("Prix:"), newPrix);
        grid.addRow(4, new Label("Température (min/max):"), newTemperature);
        grid.addRow(5, new Label("Humidité (min/max):"), newHumidite);
        grid.addRow(6, new Label("Image:"), newImage);

        pane.setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.modifier(newNom, newDescription, newOrigine, temperatureSpinnerMin, temperatureSpinnerMax, humiditeSpinnerMin, humiditeSpinnerMax, fichier[0], newPrix);
        }
    }

    private void modifier(TextField nom, TextField description, TextField origine, Spinner<Integer> tempMinSpinner, Spinner<Integer> tempMaxSpinner, Spinner<Integer> humMinSpinner, Spinner<Integer> humMaxSpinner, File fichier, TextField prix) {
        Plante plante = this.plantes.get(index);

        try {
            if(nom.getText().isEmpty())                               { this.afficherMessageErreur("Oups ! Le champ 'Nom' ne peut pas être vide 🌱");                          return; }
            if(description.getText().isEmpty())                       { this.afficherMessageErreur("Eh bien ! Une plante sans description, c'est un peu triste 😢");           return; }
            if(origine.getText().isEmpty())                           { this.afficherMessageErreur("Pas d'origine ? Mais d'où vient-elle alors ? 🧐");                         return; }
            if(tempMinSpinner.getValue() > tempMaxSpinner.getValue()) { this.afficherMessageErreur("Température incohérente 🌡️ : la valeur minimale dépasse la maximale !");   return; }
            if(humMinSpinner.getValue() > humMaxSpinner.getValue())   { this.afficherMessageErreur("Humidité bizarre 💧 : la valeur minimale est supérieure à la maximale !"); return; }
            try {
                plante.setPrix(Double.parseDouble(prix.getText()));
            } catch (NumberFormatException e) {
                this.afficherMessageErreur("Le prix doit être un nombre valide 💰");
                return;
            }


            plante.setNom(nom.getText());
            plante.setDescription(ControlePlante.this.description.getText());
            plante.setOrigine(origine.getText());
            plante.setTemperature(new int[]{tempMinSpinner.getValue(), tempMaxSpinner.getValue()});
            plante.setHumidite(new int[]{humMinSpinner.getValue(), humMaxSpinner.getValue()});
            if (fichier != null) {
                plante.setImage(fichier.toURI().toString());
            }
            this.initPlante(); // this.allerA(this.index); ca marche aussi mais il y'a un "flickering", notamment à cause de la recharge de la page
        } catch (AssertionError | Exception e) {
            this.afficherMessageErreur(e.getMessage());
        }
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur lors de la sauvegarde");
        alert.setContentText(message);
        alert.showAndWait();
    }

}