package fr.icom.info.m1.balleauprisonnier_mvn.Controler;

import fr.icom.info.m1.balleauprisonnier_mvn.Model.Field;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Projectile;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class Controler {

    ArrayList<String> input = new ArrayList<String>();

    public Controler(){
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

                // Deplacement et affichage des joueurs
                for (int i = 0; i < joueurs.length; i++)
                {

                    //System.out.println(joueurs[2].getY());
                    //System.out.println(this.ball.getY());
                    if((int)this.ball.getY() == (int)(joueurs[i].getY()) && (int)this.ball.getX() == (int)joueurs[i].getX() && !this.ball.isBallTaken()){
                        // si un joueur est sur la balle il la prends
                        joueurs[i].setHasBall(true);
                        joueurs[i].createBall();
                        this.ball = joueurs[i].getBall();
                        this.ball.setBallIsTaken(true);

                    }
                    if(joueurs[i].isBot){
                        joueurs[i].move("bot");
                        //joueurs[i].turn("bot");
                    }
                    if(joueurs[i].isBot && joueurs[i].hasBall()){
                        this.ball = joueurs[i].shoot();
                    }
                    if (joueurs[i].getSide() == "bottom" && input.contains("LEFT") && !joueurs[i].isBot)
                    {
                        joueurs[i].move("left");
                    }
                    if (joueurs[i].getSide() == "bottom" && input.contains("RIGHT") && !joueurs[i].isBot)
                    {
                        joueurs[i].move("right");
                    }
                    if (joueurs[i].getSide() == "bottom" && input.contains("UP"))
                    {
                        joueurs[i].turn("left");
                    }
                    if (joueurs[i].getSide() == "bottom"&& input.contains("DOWN") && !joueurs[i].isBot)
                    {
                        joueurs[i].turn("right");
                    }
                    if (joueurs[i].getSide() == "top" && input.contains("Q") && !joueurs[i].isBot)
                    {
                        joueurs[i].move("left");
                    }
                    if (joueurs[i].getSide() == "top" && input.contains("D")&& !joueurs[i].isBot)
                    {
                        joueurs[i].move("right");
                    }
                    if (joueurs[i].getSide() == "top" && input.contains("Z")&& !joueurs[i].isBot)
                    {
                        joueurs[i].turn("left");
                    }
                    if (joueurs[i].getSide() == "top"&& input.contains("S")&& !joueurs[i].isBot)
                    {
                        joueurs[i].turn("right");
                    }
                    if (joueurs[i].getSide() == "top" && input.contains("SPACE") && joueurs[i].hasBall() && !joueurs[i].isBot){
                        this.ball = joueurs[i].shoot();
                    }
                    if (joueurs[i].getSide() == "bottom" && input.contains("ENTER") && joueurs[i].hasBall() && !joueurs[i].isBot){
                        this.ball = joueurs[i].shoot();
                    }
                    if (this.ball != null) {
                        this.ball.display();
                    }

                    joueurs[i].display();
                }
            }
        }.start(); // On lance la boucle de rafraichissement

    }
}
