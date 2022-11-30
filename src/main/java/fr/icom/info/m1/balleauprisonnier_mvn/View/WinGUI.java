package fr.icom.info.m1.balleauprisonnier_mvn.View;


import fr.icom.info.m1.balleauprisonnier_mvn.Controler.Controler;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Field;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Singleton;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Strategy1;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class WinGUI extends Application {

    String whoWon;

    @Override
    public void start(Stage stage) throws Exception {
        if (this.whoWon == "bottom"){
            try {
                URL url = new File("src/main/java/fr/icom/info/m1/balleauprisonnier_mvn/View/Win1.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                URL url = new File("src/main/java/fr/icom/info/m1/balleauprisonnier_mvn/View/Win2.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void fin(Scene s,String side){

        this.whoWon = side;
        Stage thisStage = (Stage) s.getWindow();
        try{
            this.start(thisStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}