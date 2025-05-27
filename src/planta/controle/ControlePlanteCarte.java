package planta.controle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import planta.modele.Plante;


public class ControlePlanteCarte {

    @FXML private Label nom;
    @FXML private Label type;
    @FXML private Label prix;
    // TODO: @FXML private Label populaire;

    public void setPlante(Plante plante) {
        this.nom.setText(plante.getNom());
        this.type.setText(plante.getType().name().toLowerCase());
        this.prix.setText(String.format("%.2f €", plante.getPrix()));
    }
}
