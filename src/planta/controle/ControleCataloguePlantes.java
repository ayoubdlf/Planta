package planta.controle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import planta.modele.Plante;
import planta.modele.TypePlante;
import java.io.IOException;
import java.util.ArrayList;


public class ControleCataloguePlantes {

    @FXML private VBox     racine;
    @FXML private FlowPane listePlantes;

    /**
     * Initialise la liste et construit les cartes des plantes pour l'affichage.
     */
    @FXML
    public void initialize() {
        ArrayList<Plante> plantes = new ArrayList<>();

        // Plantes par default
        plantes.add(new Plante(
                "Lys de la Paix",
                TypePlante.EXTERIEUR,
                "Une plante élégante, facile à entretenir...",
                120,
                new int[]{18, 25},
                false,
                "Amérique",
                new int[]{50, 70},
                20.0,
                true,
                "https://images.unsplash.com/photo-1616694547693-b0f829a6cf30"
        ));
        plantes.add(new Plante(
                "Pothos",
                TypePlante.INTERIEUR,
                "Liane d'intérieur simple à cultiver...",
                100,
                new int[]{16, 28},
                true,
                "Asie",
                new int[]{60, 80},
                15.0,
                false,
                "https://images.unsplash.com/photo-1658309833602-854ab8e1d9f5"
        ));

        // reconstruit le FlowPane avec la liste des plantes
        this.listePlantes.getChildren().clear();
        for (Plante plante : plantes) {
            this.listePlantes.getChildren().add(this.creerCartePlante(plante));
        }
    }

    /**
     * Charge la carte depuis son FXML et initialise son controller.
     * @param plante la plante à afficher
     */
    private Node creerCartePlante(Plante plante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/planteCarte.fxml"));
            Node carte = loader.load();

            // Initialise le controller de la carte
            ControlePlanteCarte controle = loader.getController();
            controle.setPlante(plante);

            // Clic sur la plante -> navigation vers la plante en détail
            carte.setOnMouseClicked((MouseEvent e) -> this.ouvrirDetail(plante));
            return carte;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Remplace la racine de la scène par la vue détail de la plante.
     * @param plante la plante à ouvrir en détail
     */
    private void ouvrirDetail(Plante plante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/plante.fxml"));
            Parent detailRoot = loader.load();

            // Injecte la plante dans le controller détail
            ControlePlante ctrl = loader.getController();
            ctrl.setPlante(plante);

            // Remplace la vue actuelle
            racine.getScene().setRoot(detailRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
