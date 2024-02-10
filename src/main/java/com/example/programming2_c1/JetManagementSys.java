package com.example.programming2_c1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class JetManagementSys extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml"))); //for login
        stage.setResizable(false);
        Scene scene = new Scene(root, 1132, 706);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}