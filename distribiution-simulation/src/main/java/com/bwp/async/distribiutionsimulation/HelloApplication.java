package com.bwp.async.distribiutionsimulation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load());
        HelloController controller = loader.getController();

        stage.setTitle("Simulation App");
        stage.setScene(scene);

        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}