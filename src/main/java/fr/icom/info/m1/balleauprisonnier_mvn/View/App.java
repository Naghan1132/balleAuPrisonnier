package fr.icom.info.m1.balleauprisonnier_mvn.View;


import fr.icom.info.m1.balleauprisonnier_mvn.Controler.Controler;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Field;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Projectile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        /*
        séparer la vue du model pour le main, créer classe GUI...
        (voir projet l3 s1 méthodeProg CC2)

        Pattern a utiliser  : Strategy (simple)
                            :
         */
        // Nom de la fenetre
        stage.setTitle("Balle Au Prisonnier");
        stage.getIcons().add(new Image("assets/ball.png")); // icon du jeu

        Group root = new Group();
        Scene scene = new Scene(root);

        // On cree le terrain de jeu et on l'ajoute a la racine de la scene

        Field gameField = Field.getInstance();
        Controler controler = new Controler();
        //Projectile ball = new Projectile(gameField.getGc(),"top",gameField.getJoueurs()[3].getX(),gameField.getJoueurs()[3].getY(),gameField.getJoueurs()[3]);
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
