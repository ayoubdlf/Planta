package planta.controle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import planta.modele.Catalogue;
import planta.modele.Plante;
import planta.vue.Loader;


public class ControleCarte extends VBox {

    @FXML private ImageView image;
    @FXML private Label     nom;
    @FXML private Label     type;
    @FXML private Label     prix;

    private Plante plante;

    public ControleCarte(Plante plante) {
        this.plante = plante;

        try {
            // charge son propre fxml dans "this"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carte.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // remplire les donnes dla carte
        this.nom.setText(this.plante.getNom());
        this.type.setText(this.plante.getType().name().toLowerCase());
        this.prix.setText(String.format("%.2f €", this.plante.getPrix()));

        try {
            this.image.setImage(new Image(this.plante.getImage(), 144, 128, false, true));
            Rectangle clip = new Rectangle(144, 128);
            clip.setArcWidth(24);
            clip.setArcHeight(24);
            this.image.setClip(clip);
        } catch (Exception ignored) {}


        // clic -> détail
        this.setOnMouseClicked(e -> {
            int index    = Catalogue.getPlantes().indexOf(this.plante);
            Stage stage  = (Stage) getScene().getWindow();
            Scene detail = Loader.afficherDetail(index);

            stage.setScene(detail);
        });
    }
}
