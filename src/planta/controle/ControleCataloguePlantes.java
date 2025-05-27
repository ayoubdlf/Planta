package planta.controle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import planta.modele.Plante;
import planta.modele.TypePlante;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class ControleCataloguePlantes {

    @FXML public TextField     recherche;
    @FXML private VBox         racine;
    @FXML private FlowPane     listePlantes;
    @FXML private ToggleButton filtrerParTout;
    @FXML private ToggleButton filtrerParExterieur;
    @FXML private ToggleButton filtrerParInterieur;

    private ArrayList<Plante> plantes;

    /**
     * Initialise la liste et construit les cartes des plantes pour l'affichage.
     */
    @FXML
    public void initialize() {
        this.plantes = new ArrayList<>();

        // Plantes par default
        this.plantes.add(new Plante("Lys de la Paix", TypePlante.EXTERIEUR, "Une plante élégante, facile à entretenir...", 120, new int[]{18, 25}, false, "Amérique", new int[]{50, 70}, 20.0, "/images/p1.jpeg"));
        this.plantes.add(new Plante("Pothos", TypePlante.INTERIEUR, "Liane d'intérieur simple à cultiver...", 100, new int[]{16, 28}, true, "Asie", new int[]{60, 80}, 15.0, "/images/p2.jpeg"));

        // reconstruit le FlowPane avec la liste des plantes
        this.afficherPlantes(this.plantes);

        // Le bouton "Tout" des tri est selectionne par default
        ToggleGroup group = new ToggleGroup();
        filtrerParTout.setToggleGroup(group);
        filtrerParExterieur.setToggleGroup(group);
        filtrerParInterieur.setToggleGroup(group);
        filtrerParTout.setSelected(true);

        group.selectedToggleProperty().addListener((obs, oldFiltre, newFiltre) -> {
            if (newFiltre == filtrerParTout) {
                this.afficherPlantes(plantes);
            } else {
                ArrayList<Plante> filtres = plantes.stream()
                        .filter(p -> p.getType() == ((newFiltre == filtrerParExterieur) ? TypePlante.EXTERIEUR : TypePlante.INTERIEUR))
                        .collect(Collectors.toCollection(ArrayList::new));

                this.afficherPlantes(filtres);
            }
        });

        // Filtrage des plantes par leur nom
        this.recherche.setOnKeyReleased(event -> {
            String nom = this.recherche.getText().trim().toLowerCase();
            TypePlante filtreSelectionne = filtrerParTout.isSelected() ? null : filtrerParInterieur.isSelected() ? TypePlante.INTERIEUR : TypePlante.EXTERIEUR;

            if (nom.isEmpty()) {
                // Le filtre "Tout" est selectionne, on affiche toute les plants
                if(filtreSelectionne == null) {
                    this.afficherPlantes(plantes);
                } else {
                    // Le filtre "Tout" n'est selectionne, on afficheles plantes qui ont le meme filtre demande
                    ArrayList<Plante> filtres = plantes.stream()
                            .filter(p -> p.getType() == filtreSelectionne)
                            .collect(Collectors.toCollection(ArrayList::new));

                    this.afficherPlantes(filtres);
                }
            } else {
                ArrayList<Plante> filtres = new ArrayList<>();
                for (Plante plante : plantes) {
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

    private void afficherPlantes(ArrayList<Plante> plantes) {
        this.listePlantes.getChildren().clear();
        for (Plante plante : plantes) {
            this.listePlantes.getChildren().add(this.creerCartePlante(plante));
        }
    }

    /**
     * Remplace la racine de la scène par la vue détail de la plante.
     * @param plante la plante à ouvrir en détail
     */
    private void ouvrirDetail(Plante plante) {
        try {
            int index = this.plantes.indexOf(plante);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/plante.fxml"));
            Parent detailRoot = loader.load();

            // Injecte la plante dans le controller détail
            ControlePlante controle = loader.getController();
            controle.setCatalogue(this.plantes, index);

            // Remplace la vue actuelle
            racine.getScene().setRoot(detailRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onFermer() {
        Platform.exit();
    }

    @FXML
    public void onAjouter() {
       // TODO
    }
}
