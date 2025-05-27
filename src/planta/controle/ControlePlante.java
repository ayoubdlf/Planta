package planta.controle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import planta.modele.Plante;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.Optional;


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
    @FXML private ImageView  image;
    @FXML private MenuItem   menuEditer;

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

        try {
            this.image.setImage(new Image(plante.getImage(), 256, 256, false, true));
            Rectangle clip = new Rectangle(256, 256);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            this.image.setClip(clip);
        } catch (Exception ignored) {}

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

    @FXML
    public void onFermer() {
        Platform.exit();
    }

    @FXML
    public void onEditer() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier la plante");
        dialog.setHeaderText("Éditer les informations de la plante");

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField newNom         = new TextField(plante.getNom());
        TextField newDescription = new TextField(plante.getDescription());
        TextField newOrigine     = new TextField(plante.getOrigine());

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

        Button newImage               = new Button("Choisir l'image...");
        final FileChooser fileChooser = new FileChooser();
        final File[] selectedFile     = new File[1];
        newImage.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(racine.getScene().getWindow());
            if (file != null) {
                selectedFile[0] = file;
                newImage.setText(file.getName());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nom:"), newNom);
        grid.addRow(1, new Label("Description:"), newDescription);
        grid.addRow(2, new Label("Origine:"), newOrigine);
        grid.addRow(3, new Label("Température (min/max):"), newTemperature);
        grid.addRow(4, new Label("Humidité (min/max):"), newHumidite);
        grid.addRow(5, new Label("Image:"), newImage);

        pane.setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.modifier(newNom, newDescription, newOrigine, temperatureSpinnerMin, temperatureSpinnerMax, humiditeSpinnerMin, humiditeSpinnerMax, selectedFile[0]);
        }
    }

    private void modifier(TextField newNom, TextField newDescription, TextField newOrigine, Spinner<Integer> tempMinSpinner, Spinner<Integer> tempMaxSpinner, Spinner<Integer> humMinSpinner, Spinner<Integer> humMaxSpinner, File selectedFile) {
        try {
            plante.setNom(newNom.getText());
            plante.setDescription(newDescription.getText());
            plante.setOrigine(newOrigine.getText());
            plante.setTemperature(new int[]{tempMinSpinner.getValue(), tempMaxSpinner.getValue()});
            plante.setHumidite(new int[]{humMinSpinner.getValue(), humMaxSpinner.getValue()});
            if (selectedFile != null) {
                plante.setImage(selectedFile.toURI().toString());
            }
            this.afficherPlante();
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
