package planta.controle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import planta.modele.Plante;
import java.io.IOException;
import java.util.List;


public class ControlePlante {

    @FXML private ScrollPane racine;
    @FXML private Label      nom;
    @FXML private Label      type;
    @FXML private Label      description;
    @FXML private Label      eau;
    @FXML private Label      temperature;
    @FXML private Label      toxique;
    @FXML private Label      origine;
    @FXML private Label      humidite;
    // TODO: @FXML private Label prix;

    private Plante       plante;
    private List<Plante> catalogue;
    private int          index;

    /**
     * Injecte la plante à afficher et met à jour la vue.
     */
    public void setPlante(Plante plante) {
        this.plante = plante;
        this.afficherPlante();
    }

    public void setCatalogue(List<Plante> catalogue, int index) {
        this.catalogue = catalogue;
        this.index     = index;
        this.setPlante(catalogue.get(index));
    }

    private void afficherPlante() {
        this.racine.setVvalue(0);
        this.nom.setText(this.plante.getNom());
        this.type.setText(this.plante.getType().name().toLowerCase());
        this.description.setText(this.plante.getDescription());
        this.eau.setText(this.plante.getEau() + " mL");
        this.temperature.setText(String.format("%d°C - %d°C", this.plante.getTemperature()[0], this.plante.getTemperature()[1]));
        this.toxique.setText(this.plante.estToxique() ? "Oui" : "Non");
        this.origine.setText(this.plante.getOrigine());
        this.humidite.setText(String.format("%d%% - %d%%", this.plante.getHumidite()[0], this.plante.getHumidite()[1]));
    }

    @FXML private void onHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cataloguePlantes.fxml"));
        Parent     root   = loader.load();

        this.racine.getScene().setRoot(root);
    }

    @FXML
    public void onPrecedent() {
        if (catalogue != null && index > 0) {
            index--;
            setPlante(catalogue.get(index));
        }
    }

    @FXML
    public void onProchain() {
        if (catalogue != null && index < catalogue.size() - 1) {
            index++;
            setPlante(catalogue.get(index));
        }
    }
}
