package hu.vasvari;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(App.class.getResource("view.fxml"))));

        primaryStage.setWidth(700);
        primaryStage.setHeight(650);
    //    primaryStage.setResizable(false);
        primaryStage.setTitle("KINYIR - Kisállat nyílvántartó Rendszer");

        primaryStage.show();
    }

}