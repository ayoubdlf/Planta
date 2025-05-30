package planta.controle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import planta.modele.Catalogue;
import planta.modele.Plante;
import planta.vue.Loader;
import planta.vue.DialoguePlante;


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
        DialoguePlante.afficher(
                this.nom.getScene().getWindow(),
                this.plantes.get(index)
        );

        this.initPlante();
    }
}