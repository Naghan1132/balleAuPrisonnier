package fr.icom.info.m1.balleauprisonnier_mvn.View;


import fr.icom.info.m1.balleauprisonnier_mvn.Controler.Controler;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Field;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Projectile;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Singleton;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
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
        //new StartGUI().start();
        //Rectangle rect = new Rectangle(100,100);
        // faire new pane et test si on appuie sur le rectangle start => commencer la partie
        /*
        séparer la vue du model pour le main, créer classe GUI...
        (voir projet l3 s1 méthodeProg CC2)

        Pattern a utiliser  : Behavioral patterns => Strategy (simple => voir CC de licence)
                            : Creational patterns => Singleton Java classe Field  (voir tp python) OK
         */
        /*
         TODO : - régler le problème du fillRect de la fenetre !!!! ou tester sur machine fac
                - faire colisions avec joueurs (mais impossbile de test sans le fillRect)
                - shot des bots avec IA
                - desing paterns
                - faire des obstacles
                - plusieurs vues :  une présentant le terrain (OK)
                                    une autre présentant le score (a faire)
                                    une autre les contrôles du jeu (


         */
        // Nom de la fenetre

        stage.setTitle("Balle Au Prisonnier");
        stage.getIcons().add(new Image("assets/ball.png")); // icon du jeu

        Group root = new Group();
        Scene scene = new Scene(root);

        // On cree le terrain de jeu et on l'ajoute a la racine de la scene

        //Field gameField = Field.getInstance();
        Singleton singleField = Singleton.getInstance();
        Field gameField = singleField.gameField;
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
