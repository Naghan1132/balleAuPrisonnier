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

/**
 * Classe principale de l'application
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application {

    /**
     * En javafx start() lance l'application
     * <p>
     * On cree le SceneGraph de l'application ici
     *
     * @see //http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Start.fxml")); //error
            URL url = new File("src/main/java/fr/icom/info/m1/balleauprisonnier_mvn/View/Start.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }

        /*

        Pattern a utiliser  : Behavioral patterns => Strategy (simple => voir CC de licence) OK
                                (avec obstables différents endroits, vitesse différentes etc...)
                            : Creational patterns => Singleton Java classe Field  (voir tp python) OK
         */
        /*
         TODO : - faire colisions balle/joueurs
                - shot des bots avec IA OK
                - desing paterns (singleton et strategy fait !)
                - faire des obstacles
                - plusieurs vues :  une présentant le terrain (OK)
                                    une autre présentant le score (win GUI)
                                    une autre les contrôles du jeu (OK)


         */



    }

    public void setUp(ActionEvent event) throws IOException{
        //Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Group root = new Group();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Nom de la fenetre
        //stage = new Stage();
        stage.setTitle("Balle Au Prisonnier");
        stage.getIcons().add(new Image("assets/ball.png")); // icon du jeu


        Scene scene = new Scene(root);

        // On cree le terrain de jeu et on l'ajoute a la racine de la scene

        //Field gameField = Field.getInstance();
        Singleton singleField = Singleton.getInstance();
        Field gameField = singleField.gameField;

        new Strategy1(gameField);

        new Controler();

        root.getChildren().add(gameField);
        root.getChildren().add(gameField.getJoueurs()[0].getSprite());
        root.getChildren().add(gameField.getJoueurs()[1].getSprite());
        root.getChildren().add(gameField.getJoueurs()[2].getSprite());
        root.getChildren().add(gameField.getJoueurs()[3].getSprite());
        root.getChildren().add(gameField.getJoueurs()[4].getSprite());
        root.getChildren().add(gameField.getJoueurs()[5].getSprite());

        // On ajoute la scene a la fenetre et on affiche

        stage.setScene(scene);
        stage.show();
    }

        public static void main(String[] args) {
        Application.launch(args);
    }
}
