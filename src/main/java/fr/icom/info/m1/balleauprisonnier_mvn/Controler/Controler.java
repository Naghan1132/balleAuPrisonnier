package fr.icom.info.m1.balleauprisonnier_mvn.Controler;

import fr.icom.info.m1.balleauprisonnier_mvn.Model.Field;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Projectile;
import fr.icom.info.m1.balleauprisonnier_mvn.View.WinGUI;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;

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
                    gc.clearRect(0, 0, width, height);
                    new WinGUI();
                } else if (field.equipe2Won()) {
                    gc.clearRect(0, 0, width, height);
                    new WinGUI();
                }
                // Deplacement et affichage des joueurs
                for (int i = 0; i < joueurs.length; i++) {

                    if (!joueurs[i].getIsDead()) { //si le joueur est mort il ne peut rien faire
                        //explosion quand un joueur meurt ?
                        if (ball.getVitesse() != 0) {
                            // balle en mouvement on test si un joueur est touché
                            if (ball.getShootedFrom() == "top") {
                                if (joueurs[i].getSide() == "bottom") {
                                    if (ball.getY() > 400) {
                                        //System.out.println("y :"+joueurs[0].getY()); //500
                                        //y_joueur = [500, 500+50] ?
                                        //x_joueur = [joueur.get(x), + joueur.get(x)+30]

                                        //List<Point2D> coordJoueur = new ArrayList<Point2D>();
                                        //List<Point2D> coordBall = new ArrayList<Point2D>();
                                        //int valeurArrondieBalleX = (int) ball.getX();
                                        //int valeurArrondieBalleY = (int) ball.getY();
                                        //Point2D testBalle = new Point2D.Double(valeurArrondieBalleX,valeurArrondieBalleY);


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
                                        //System.out.println("xJ "+xJoueur);
                                        //System.out.println("yJ "+yJoueur);
                                        //System.out.println("xBalle "+xBalle);
                                        //System.out.println("yBalle "+yBalle);
                                        for (Integer xJ : xJoueur) {
                                            //System.out.println("test");
                                            if (xBalle.contains(xJ)) {
                                                for (Integer yJ : yJoueur) {
                                                    if (yBalle.contains(yJ)) {
                                                        //System.out.println("MORT");
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
                                        this.ball = joueurs[i].getBall();
                                        this.ball.setShootedFrom("null");
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
                                        this.ball = joueurs[i].getBall();
                                        this.ball.setShootedFrom("null");
                                    }
                                }
                            }
                        }

                        if (joueurs[i].isBot) {
                            joueurs[i].move("bot");
                            //joueurs[i].turn("bot"); //pas réellement besoin
                        }
                        if (joueurs[i].isBot && joueurs[i].hasBall()) {
                            this.ball = joueurs[i].shoot();
                            this.ball.setShootedFrom(joueurs[i].getSide());
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
                            this.ball = joueurs[i].shoot();
                            this.ball.setShootedFrom(joueurs[i].getSide());
                        }
                        if (joueurs[i].getSide() == "bottom" && input.contains("ENTER") && joueurs[i].hasBall() && !joueurs[i].isBot) {
                            this.ball = joueurs[i].shoot();
                            this.ball.setShootedFrom(joueurs[i].getSide());

                        }
                        if (this.ball != null) {
                            this.ball.display();
                        }

                        joueurs[i].display();
                    }
                }
            }
        }.start(); // On lance la boucle de rafraichissement
    }
}
