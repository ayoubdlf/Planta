package planta.controle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import planta.modele.Plante;


public class ControlePlanteCarte {

    @FXML private Label nom;
    @FXML private Label type;
    @FXML private Label prix;
    @FXML private ImageView image;

    public void setPlante(Plante plante) {
        this.nom.setText(plante.getNom());
        this.type.setText(plante.getType().name().toLowerCase());
        this.prix.setText(String.format("%.2f €", plante.getPrix()));

        try {
            this.image.setImage(new Image(plante.getImage(), 144, 128, false, true));
            Rectangle clip = new Rectangle(144, 128);
            clip.setArcWidth(24);
            clip.setArcHeight(24);
            this.image.setClip(clip);
        } catch (Exception ignored) {}
    }
}
