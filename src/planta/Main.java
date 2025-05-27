package planta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Chargement de la vue principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cataloguePlantes.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Catalogue de Plantes 🌿");
            primaryStage.setScene(scene);
            primaryStage.show();

            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
