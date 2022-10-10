package fr.icom.info.m1.balleauprisonnier_mvn.Controler;

import fr.icom.info.m1.balleauprisonnier_mvn.Model.Bot;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Projectile;
import fr.icom.info.m1.balleauprisonnier_mvn.Model.Sprite;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class KeyListener extends Canvas{
    public KeyListener(){

    }

    /**
     * Event Listener du clavier
     * quand une touche est pressee on la rajoute a la liste d'input
     *
     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
    {
        public void handle(KeyEvent e)
        {
            String code = e.getCode().toString();
            // only add once... prevent duplicates
            if ( !input.contains(code) )
                input.add( code );
        }
    });

    /**
     * Event Listener du clavier
     * quand une touche est relachee on l'enleve de la liste d'input
     *
     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
    {
        public void handle(KeyEvent e)
        {
            String code = e.getCode().toString();
            input.remove( code );
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
	    new AnimationTimer()
    {
        public void handle(long currentNanoTime)
        {
            // On nettoie le canvas a chaque frame
            gc.setFill( Color.LIGHTGRAY);
            gc.fillRect(0, 0, width, height);

            // Deplacement et affichage des joueurs
            for (int i = 0; i < joueurs.length; i++)
            {
                if(colision(joueurs[i],ball)){
                    //le joueur meurt balle chez le camp perdant
                }
                if(!ball.ballIsTaken){
                    ball.displayBall();
                }
                if(joueurs[i].hasBall){
                    joueurs[i].displayBall();
                }
                if(joueurs[i].isBot){
                    joueurs[i].move();
                    joueurs[i].turn();
                }
                if (joueurs[i].side == "bottom" && input.contains("LEFT") && !joueurs[i].isBot)
                {
                    joueurs[i].moveLeft();
                }
                if (joueurs[i].side == "bottom" && input.contains("RIGHT") && !joueurs[i].isBot)
                {
                    joueurs[i].moveRight();
                }
                if (joueurs[i].side == "bottom" && input.contains("UP") && !joueurs[i].isBot)
                {
                    joueurs[i].turnLeft();
                }
                if (joueurs[i].side == "bottom"&& input.contains("DOWN") && !joueurs[i].isBot)
                {
                    joueurs[i].turnRight();
                }
                if (joueurs[i].side == "bottom" && input.contains("ENTER") && !joueurs[i].isBot && joueurs[i].hasBall){
                    joueurs[i].shoot();
                }
                if (joueurs[i].side == "top" && input.contains("Q") && !joueurs[i].isBot)
                {
                    joueurs[i].moveLeft();
                }
                if (joueurs[i].side == "top" && input.contains("D") && !joueurs[i].isBot)
                {
                    joueurs[i].moveRight();
                }
                if (joueurs[i].side == "top" && input.contains("Z") && !joueurs[i].isBot)
                {
                    joueurs[i].turnLeft();
                }
                if (joueurs[i].side == "top"&& input.contains("S") && !joueurs[i].isBot)
                {
                    joueurs[i].turnRight();
                }
                if (joueurs[i].side == "top" && input.contains("SPACE") && !joueurs[i].isBot && joueurs[i].hasBall){
                    joueurs[i].shoot();
                }
                joueurs[i].display();
            }
        }
    }.start(); // On lance la boucle de rafraichissement

}
}
