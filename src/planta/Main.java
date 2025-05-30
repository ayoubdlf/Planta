package planta;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import planta.vue.Loader;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = Loader.afficherCatalogue();

        stage.setTitle("Catalogue de Plantes 🌿");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
