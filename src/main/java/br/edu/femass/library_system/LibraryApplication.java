package br.edu.femass.library_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("library-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setUserData(fxmlLoader.getController());
        stage.setTitle("Biblioteca Prog 3");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setMinWidth(800);
        stage.setWidth(screenBounds.getWidth() * 0.7);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}