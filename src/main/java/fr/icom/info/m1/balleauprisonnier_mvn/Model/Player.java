package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Classe gerant un joueur
 */
public class Player {
    public boolean isBot;
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

        if (side == "top") {
            Image tilesheetImage = new Image("assets/PlayerRed.png");
            this.sprite = new Sprite(tilesheetImage, 0, 0, Duration.seconds(.2), side);
            this.sprite.setX(x);
            this.sprite.setY(y);
        } else {
            Image tilesheetImage = new Image("assets/PlayerBlue.png");
            this.sprite = new Sprite(tilesheetImage, 0, 0, Duration.seconds(.2), side);
            this.sprite.setX(x);
            this.sprite.setY(y);
        }

        //directionArrow = sprite.getClip().;
    }


    /**
     * Affichage du joueur
     */
    public void display() {
        //affiche la fleche
        graphicsContext.save(); // saves the current state on stack, including the current transform

        if (this.getSide() == "top") {
            rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
            graphicsContext.drawImage(directionArrow, x, y);
        } else {
            rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 6);
            graphicsContext.drawImage(directionArrow, x, y - 50);
        }
        graphicsContext.restore(); // back to original state (before rotation)
        if (this.ball != null) {
            if (this.getSide() == "top") {
                this.ball.reset(this.x + 10, this.y + 50, this.angle);
            } else {
                this.ball.reset(this.x + 10, this.y, this.angle);
            }

        }
    }


    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public Projectile shoot() {
        sprite.playShoot();
        this.ball.setAngle(angle);
        Projectile ballShooted = this.ball;
        this.setHasBall(false);
        this.setBall(null);
        ballShooted.setBallIsTaken(false);
        return ballShooted;
    }

    /**
     * Deplacement en mode boost
     */
    public void boost() {
        x += step * 2;
        spriteAnimate();
    }

    public void spriteAnimate() {
        if (!sprite.isRunning) {
            sprite.playContinuously();
        }
        sprite.setX(x);
        sprite.setY(y);
    }

    public void move(String direc) {
        //rien, pour les bots
        if (direc == "right") {
            if (x < 520) {
                spriteAnimate();
                x += step;
            }
        } else if (direc == "left") {
            if (x > 10) {
                spriteAnimate();
                x -= step;
            }
        }
    }

    /**
     * Rotation du tir
     */
    public void turn(String direc) {
        // Rotation du tir vers la gauche
        if (direc == "left") {
            if (this.angle < 90) {
                this.angle += 0.3;
            }
        }
        // Rotation du tir vers la droite
        else if (direc == "right") {
            if (this.angle > -90) {
                this.angle -= 0.3;
            }
        }
    }

    public boolean hasBall() {
        return hasBall;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public void setBall(Projectile ball) {
        this.ball = ball;
    }

    public Projectile getBall() {
        return this.ball;
    }

    public void createBall() {
        ball = new Projectile(this.graphicsContext, this.side, this.x, this.y, this.angle);
        this.setBall(ball);
    }

    public void setIsDead(boolean dead) {
        this.sprite.removeImage();
        this.isDead = dead;
    }

    public boolean getIsDead() {
        return this.isDead;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public String getSide() {
        return this.side;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
