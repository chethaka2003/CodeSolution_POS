package com.codesolution.cs_pos_v1;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmls/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/Styles/mainStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        //Removing the default control buttons
        primaryStage.show();

        //Fade in effect
        new FadeIn(scene.getRoot()).play();
    }

    public static void main(String[] args) {
        launch(args);

    }
}