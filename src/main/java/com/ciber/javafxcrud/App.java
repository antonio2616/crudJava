package com.ciber.javafxcrud;

import com.ciber.javafxcrud.database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    //aqui se inicializa la base de datos
    private static Scene scene;
    private EstudiantesController eControl = new EstudiantesController();
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("estudiantes"));
        stage.setTitle("Prueba FX");
        eControl.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();

        
    }

}