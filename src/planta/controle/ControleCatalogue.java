package planta.controle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import planta.modele.Catalogue;
import planta.modele.Plante;
import planta.modele.TypePlante;
import planta.vue.DialoguePlante;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ControleCatalogue {

    @FXML private FlowPane     listePlantes;
    @FXML private TextField    recherche;
    @FXML private ToggleButton filtrerParTout;
    @FXML private ToggleButton filtrerParPrix;
    @FXML private ToggleButton filtrerParExterieur;
    @FXML private ToggleButton filtrerParInterieur;


    public ControleCatalogue() {
        this.listePlantes = new FlowPane();
    }

    private void afficherPlantes(List<Plante> plantes) {
        this.listePlantes.getChildren().clear();
        for (Plante plante : plantes) {
            ControleCarte carte = new ControleCarte(plante);
            this.listePlantes.getChildren().add(carte);
        }
    }

    @FXML
    private void initialize() {
        this.afficherPlantes(Catalogue.getPlantes());

        // Le bouton "Tout" des tri est selectionne par default
        ToggleGroup group = new ToggleGroup();
        filtrerParTout.setToggleGroup(group);
        filtrerParPrix.setToggleGroup(group);
        filtrerParExterieur.setToggleGroup(group);
        filtrerParInterieur.setToggleGroup(group);
        filtrerParTout.setSelected(true);

        group.selectedToggleProperty().addListener((obs, oldFiltre, newFiltre) -> {
            List<Plante> plantesFiltres;
            if (newFiltre == filtrerParTout) {
                plantesFiltres = Catalogue.getPlantes();
            } else if (newFiltre == filtrerParPrix) {
                ArrayList<Plante> trie = new ArrayList<>(Catalogue.getPlantes());
                trie.sort(Comparator.comparingDouble(Plante::getPrix));
                plantesFiltres = trie;
            } else {
                plantesFiltres = Catalogue.getPlantes().stream()
                        .filter(p -> p.getType() == ((newFiltre == filtrerParExterieur) ? TypePlante.EXTERIEUR : TypePlante.INTERIEUR))
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            this.afficherPlantes(plantesFiltres);
        });

        // Filtrage des plantes par leur nom
        this.recherche.setOnKeyReleased(event -> {
            String nom = this.recherche.getText().trim().toLowerCase();
            TypePlante filtreSelectionne = filtrerParTout.isSelected() ? null : filtrerParInterieur.isSelected() ? TypePlante.INTERIEUR : TypePlante.EXTERIEUR;

            if (nom.isEmpty()) {
                // Le filtre "Tout" est selectionne, on affiche toute les plants
                if(filtreSelectionne == null) {
                    this.afficherPlantes(Catalogue.getPlantes());
                } else {
                    // Le filtre "Tout" n'est selectionne, on afficheles plantes qui ont le meme filtre demande
                    ArrayList<Plante> filtres = Catalogue.getPlantes().stream()
                            .filter(p -> p.getType() == filtreSelectionne)
                            .collect(Collectors.toCollection(ArrayList::new));

                    this.afficherPlantes(filtres);
                }
            } else {
                ArrayList<Plante> filtres = new ArrayList<>();
                for (Plante plante : Catalogue.getPlantes()) {
                    boolean memeNom  = plante.getNom().toLowerCase().contains(nom);
                    boolean memeType = (filtreSelectionne == null) || plante.getType() == filtreSelectionne;

                    if (memeNom && memeType) {
                        filtres.add(plante);
                    }
                }
                this.afficherPlantes(filtres);
            }
        });
    }

    @FXML
    public void onQuitter() { Platform.exit(); }

    @FXML
    public void onAjouter() {
        DialoguePlante.afficher(
                this.recherche.getScene().getWindow(),
                null // on ajoutter une nouvelle plante
        );

        this.afficherPlantes(Catalogue.getPlantes());
    }

    @FXML
    public void onCharger() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un fichier JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File file = fileChooser.showOpenDialog(this.listePlantes.getScene().getWindow());

        if (file != null) {
            try {
                String json               = Files.readString(file.toPath());
                Type type                 = new TypeToken<List<Plante>>(){}.getType();
                ArrayList<Plante> plantes = new Gson().fromJson(json, type);

                Catalogue.setPlantesDepuisJSON(plantes);
                this.afficherPlantes(Catalogue.getPlantes());
            } catch (Exception e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de chargement");
                    alert.setHeaderText("Impossible de charger le fichier JSON");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                });
            }
        }
    }

    @FXML
    public void onSauvegarder() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder un fichier JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
        File file = fileChooser.showSaveDialog(this.listePlantes.getScene().getWindow());

        if (file != null) {
            try {
                Files.writeString(file.toPath(), Catalogue.toJSON());
            } catch (IOException e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de sauvegarde");
                    alert.setHeaderText("Impossible de sauvegarder le fichier JSON");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                });
            }
        }
    }
}
