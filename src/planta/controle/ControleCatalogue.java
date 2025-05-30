package planta.controle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import planta.modele.Catalogue;
import planta.modele.Plante;
import planta.modele.TypePlante;
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
}
