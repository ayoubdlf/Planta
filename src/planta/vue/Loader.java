package planta.vue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import planta.controle.ControlePlante;


public class Loader {

    public static Scene afficherCatalogue() {
        try {
            FXMLLoader loader = new FXMLLoader(Loader.class.getResource("/fxml/catalogue.fxml"));
            return new Scene(loader.load());
        } catch (Exception e) {
            return null;
        }
    }

    public static Scene afficherDetail(int index) {
        try {
            FXMLLoader       loader   = new FXMLLoader(Loader.class.getResource("/fxml/plante.fxml"));
            Parent           parent   = loader.load();
            ControlePlante controle = loader.getController();

            controle.init(index);

            return new Scene(parent);
        } catch (Exception e) {
            return null;
        }
    }
}
