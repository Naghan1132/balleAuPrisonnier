package fr.icom.info.m1.balleauprisonnier_mvn.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class StartGUI extends Application {


    @Override
    public void start(Stage primaryStage) {
        final Rectangle rectangle = new Rectangle(100, 100, 150, 100);
        rectangle.setFill(Color.RED);
        final Pane root = new Pane();
        root.getChildren().setAll(rectangle);
        final Scene scene = new Scene(root, 350, 300);
        primaryStage.setTitle("Test sur l'opacité de la fenêtre");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOpacity(0.5);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
