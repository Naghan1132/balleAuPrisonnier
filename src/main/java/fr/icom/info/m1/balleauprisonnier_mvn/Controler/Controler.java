package fr.icom.info.m1.balleauprisonnier_mvn.Controler;

import fr.icom.info.m1.balleauprisonnier_mvn.Model.Field;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Projectile;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Sprite;
import fr.icom.info.m1.balleauprisonnier_mvn.View.WinGUI;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controler {

    ArrayList<String> input = new ArrayList<String>();

    public Controler() {
        Field field = Field.getInstance();
        GraphicsContext gc = field.getGc();

        field.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        // only add once... prevent duplicates
                        if (!input.contains(code))
                            input.add(code);
                    }
                });

        /**
         * Event Listener du clavier
         * quand une touche est relachee on l'enleve de la liste d'input
         *
         */
        field.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });

        /**
         *
         * Boucle principale du jeu
         *
         * handle() est appelee a chaque rafraichissement de frame
         * soit environ 60 fois par seconde.
         *
         */
        new AnimationTimer() {
            Player[] joueurs = field.getJoueurs();
            double width = field.getWidth();
            double height = field.getHeight();

            Projectile ball = field.getProjectile();

            public void handle(long currentNanoTime) {
                // On nettoie le canvas a chaque frame
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(0, 0, width, height);

                if (field.equipe1Won()) {
                    stop();
                    gc.clearRect(0, 0, width, height);
                    WinGUI win = new WinGUI();
                    win.fin(field.getScene(),field.getEquipe1()[0].getSide());
                } else if (field.equipe2Won()) {
                    stop();
                    gc.clearRect(0, 0, width, height);
                    WinGUI win = new WinGUI();
                    win.fin(field.getScene(),field.getEquipe2()[0].getSide());
                    //(Stage) field.getParent().getWindow();

                    ////((Node) event.getTarget()).getScene().getWindow();
                }
                // Deplacement et affichage des joueurs
                for (int i = 0; i < joueurs.length; i++) {

                    if (!joueurs[i].getIsDead()) { //si le joueur est mort il ne peut rien faire
                        if (ball.getVitesse() != 0) {
                            // balle en mouvement on test si un joueur est touché
                            if (ball.getShootedFrom() == "top") {
                                if (joueurs[i].getSide() == "bottom") {
                                    if (ball.getY() > 400) {
                                        ArrayList<Integer> xJoueur = new ArrayList<Integer>();
                                        ArrayList<Integer> yJoueur = new ArrayList<Integer>();
                                        // HITBOX :
                                        for (double x = joueurs[i].getX(); x < joueurs[i].getX() + 30; x++) {
                                            int valeurArrondieBalle = (int) x;
                                            xJoueur.add(valeurArrondieBalle);
                                        }
                                        for (double y = joueurs[i].getY(); y < joueurs[i].getY() + 50; y++) {
                                            // y -- car on est en bas
                                            int valeurArrondieBalle = (int) y;
                                            yJoueur.add(valeurArrondieBalle);
                                        }
                                        ArrayList<Integer> xBalle = new ArrayList<Integer>();
                                        ArrayList<Integer> yBalle = new ArrayList<Integer>();
                                        for (double x = ball.getX(); x < ball.getX() + ball.getSprite().getTailleImage(); x++) {
                                            int valeurArrondieBalle = (int) x;
                                            xBalle.add(valeurArrondieBalle);
                                        }
                                        for (double y = ball.getY(); y < ball.getY() + ball.getSprite().getTailleImage(); y++) {
                                            int valeurArrondieBalle = (int) y;
                                            yBalle.add(valeurArrondieBalle);
                                        }
                                        for (Integer xJ : xJoueur) {
                                            if (xBalle.contains(xJ)) {
                                                for (Integer yJ : yJoueur) {
                                                    if (yBalle.contains(yJ)) {
                                                        //explosion ?
                                                        joueurs[i].setIsDead(true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (ball.getShootedFrom() == "bottom") {
                                if (joueurs[i].getSide() == "top") {
                                    if (ball.getY() < 300) {
                                        ArrayList<Integer> xJoueur = new ArrayList<Integer>();
                                        ArrayList<Integer> yJoueur = new ArrayList<Integer>();
                                        // HITBOX :
                                        for (double x = joueurs[i].getX(); x < joueurs[i].getX() + 30; x++) {
                                            int valeurArrondieBalle = (int) x;
                                            xJoueur.add(valeurArrondieBalle);
                                        }
                                        for (double y = joueurs[i].getY(); y < joueurs[i].getY() + 50; y++) {
                                            int valeurArrondieBalle = (int) y;
                                            yJoueur.add(valeurArrondieBalle);
                                        }
                                        ArrayList<Integer> xBalle = new ArrayList<Integer>();
                                        ArrayList<Integer> yBalle = new ArrayList<Integer>();
                                        for (double x = ball.getX(); x < ball.getX() + ball.getSprite().getTailleImage(); x++) {
                                            int valeurArrondieBalle = (int) x;
                                            xBalle.add(valeurArrondieBalle);
                                        }
                                        for (double y = ball.getY(); y < ball.getY() + ball.getSprite().getTailleImage(); y++) {
                                            int valeurArrondieBalle = (int) y;
                                            yBalle.add(valeurArrondieBalle);
                                        }
                                        for (Integer xJ : xJoueur) {
                                            if (xBalle.contains(xJ)) {
                                                for (Integer yJ : yJoueur) {
                                                    if (yBalle.contains(yJ)) {
                                                        //explosion ?
                                                        joueurs[i].setIsDead(true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // la balle est au sol tester le ramassage de balle OK
                            ArrayList<Integer> intervalle = new ArrayList<Integer>();

                            for (double x = ball.getX(); x < ball.getX() + ball.getSprite().getTailleImage(); x++) {
                                int valeurArrondieBalle = (int) x;
                                intervalle.add(valeurArrondieBalle);
                            }
                            if (ball.getY() >= 300) {
                                //seuls les joueurs botom peuvent la prendre
                                if (joueurs[i].getSide() == "bottom") {
                                    int valeurArrondieJoueur = (int) joueurs[i].getX();
                                    if (intervalle.contains(valeurArrondieJoueur)) {
                                        joueurs[i].setHasBall(true);
                                        joueurs[i].createBall();
                                        ball = joueurs[i].getBall();
                                        ball.setShootedFrom("null");
                                    }
                                }
                            } else {
                                // < 400
                                //seuls les joueurs top peuvent la prendre
                                if (joueurs[i].getSide() == "top") {
                                    int valeurArrondieJoueur = (int) joueurs[i].getX();
                                    if (intervalle.contains(valeurArrondieJoueur)) {
                                        joueurs[i].setHasBall(true);
                                        joueurs[i].createBall();
                                        ball = joueurs[i].getBall();
                                        ball.setShootedFrom("null");
                                    }
                                }
                            }
                        }

                        if (joueurs[i].isBot) {
                            joueurs[i].move("bot");
                        }
                        if (joueurs[i].isBot && joueurs[i].hasBall()) {
                            ball = joueurs[i].shoot();
                            //this.ball.setShootedFrom(joueurs[i].getSide());
                            field.setBall(ball);
                        }
                        if (joueurs[i].getSide() == "bottom" && input.contains("LEFT") && !joueurs[i].isBot) {
                            joueurs[i].move("left");
                        }
                        if (joueurs[i].getSide() == "bottom" && input.contains("RIGHT") && !joueurs[i].isBot) {
                            joueurs[i].move("right");
                        }
                        if (joueurs[i].getSide() == "bottom" && input.contains("UP")) {
                            joueurs[i].turn("left");
                        }
                        if (joueurs[i].getSide() == "bottom" && input.contains("DOWN") && !joueurs[i].isBot) {
                            joueurs[i].turn("right");
                        }
                        if (joueurs[i].getSide() == "top" && input.contains("Q") && !joueurs[i].isBot) {
                            joueurs[i].move("left");
                        }
                        if (joueurs[i].getSide() == "top" && input.contains("D") && !joueurs[i].isBot) {
                            joueurs[i].move("right");
                        }
                        if (joueurs[i].getSide() == "top" && input.contains("Z") && !joueurs[i].isBot) {
                            joueurs[i].turn("left");
                        }
                        if (joueurs[i].getSide() == "top" && input.contains("S") && !joueurs[i].isBot) {
                            joueurs[i].turn("right");
                        }
                        if (joueurs[i].getSide() == "top" && input.contains("SPACE") && joueurs[i].hasBall() && !joueurs[i].isBot) {
                            ball = joueurs[i].shoot();
                            //this.ball.setShootedFrom(joueurs[i].getSide());
                            field.setBall(ball);
                        }
                        if (joueurs[i].getSide() == "bottom" && input.contains("ENTER") && joueurs[i].hasBall() && !joueurs[i].isBot) {
                            ball = joueurs[i].shoot();
                            //this.ball.setShootedFrom(joueurs[i].getSide());
                            field.setBall(ball);

                        }
                        if (ball != null) {
                            ball.display();
                        }

                        joueurs[i].display();
                    }
                }
            }
        }.start(); // On lance la boucle de rafraichissement
    }
}
