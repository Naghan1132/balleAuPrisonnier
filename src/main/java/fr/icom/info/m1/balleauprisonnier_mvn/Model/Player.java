package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

/**
 * Classe gerant un joueur
 */
public class Player {
    boolean isBot;

    boolean isDead = false;

    boolean hasBall = false;

    String side;

    double x;       // position horizontale du joueur
    final double y;      // position verticale du joueur
    double angle = 0; // rotation du joueur, devrait toujours être en 0 et 180
    double step;    // pas d'un joueur
    String playerColor;

    // On une image globale du joueur
    Image directionArrow;
    Projectile ball;
    Sprite sprite;
    ImageView PlayerDirectionArrow;
    GraphicsContext graphicsContext;

    /**
     * Constructeur du Joueur
     *
     * @param gc    ContextGraphic dans lequel on va afficher le joueur
     * @param color couleur du joueur
     * @param yInit position verticale
     */
    Player(GraphicsContext gc, String color, double xInit, double yInit, String side) {
        // Tous les joueurs commencent au centre du canvas,
        x = xInit;
        y = yInit;
        graphicsContext = gc;
        playerColor = color;
        angle = 0;
        this.isBot = false;
        this.side = side;


        // On charge la representation du joueur
        if (side == "top") {
            directionArrow = new Image("assets/PlayerArrowDown.png");
        } else {
            directionArrow = new Image("assets/PlayerArrowUp.png");
        }
        //FLECHES DIRECTION
        PlayerDirectionArrow = new ImageView();
        PlayerDirectionArrow.setImage(directionArrow);
        PlayerDirectionArrow.setFitWidth(10);
        PlayerDirectionArrow.setPreserveRatio(true);
        PlayerDirectionArrow.setSmooth(true);
        PlayerDirectionArrow.setCache(true);

        Image tilesheetImage = new Image("assets/orc.png");
        this.sprite = new Sprite(tilesheetImage, 0, 0, Duration.seconds(.2), side);
        this.sprite.setX(x);
        this.sprite.setY(y);


        //directionArrow = sprite.getClip().;

        //Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
        //Random randomGenerator = new Random();
        //step = randomGenerator.nextFloat();

        // Pour commencer les joueurs ont une vitesse / un pas fixe
        step = 0.4;

    }



    /**
     * Affichage du joueur
     */
    void display() {
        //affiche la fleche
        graphicsContext.save(); // saves the current state on stack, including the current transform
        rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
        graphicsContext.drawImage(directionArrow, x, y);
        graphicsContext.restore(); // back to original state (before rotation)
    }


    void displayBall() {
        //NE PAS METTRE ICI
        //affiche la balle
        graphicsContext.save(); // saves the current state on stack, including the current transform
        if(this.side == "top"){
            //mettre devant le joueurs
            graphicsContext.drawImage(ball.getBall(),x+10, y+60);
            ball.setDirection(this.angle);
        }else{
            graphicsContext.drawImage(ball.getBall(),x-10, y-60);
            ball.setDirection(this.angle);
        }
        graphicsContext.restore(); // back to original state
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Deplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de jeu
     */

    void moveLeft() {
        if (x > 10) {
            spriteAnimate();
            x -= step;
        }
    }

    /**
     * Deplacement du joueur vers la droite
     */
    void moveRight() {
        if (x < 520) {
            spriteAnimate();
            x += step;
        }
    }


    /**
     * Rotation du joueur vers la gauche
     */
    void turnLeft() {
        if (angle < 90) {
            angle += 0.3;
        }
    }


    /**
     * Rotation du joueur vers la droite
     */
    void turnRight() {
        if (angle > -90) {
            angle -= 0.3;
        }
    }

    void shoot() {
        sprite.playShoot();
        this.hasBall = false;
        ball.ballIsTaken = false;

        if(this.side=="top"){
            double v = ball.getVitesse();
            for (double i = ball.getY();i<560;i+=v){

                //a revoir
                ball.setX(this.x);
                ball.setY(i);
                ball.setDirection(this.angle);
                ball.displayBall(); //déjà display dans la boucle
            }
        }

    }

    /**
     * Deplacement en mode boost
     */
    void boost() {
        x += step * 2;
        spriteAnimate();
    }

    void spriteAnimate() {
        //System.out.println("Animating sprite");
        if (!sprite.isRunning) {
            sprite.playContinuously();
        }
        sprite.setX(x);
        sprite.setY(y);
    }

    void move() {
        //rien, pour les bots
    }

    void turn() {
        //rien, pour les bots
    }
    public boolean isHasBall() {
        return hasBall;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public void setBall(Projectile ball){
        this.ball = ball;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
}
