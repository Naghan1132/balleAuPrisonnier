package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Projectile {

    double vitesse = 0.1;
    double angle = 90;
    protected double direction;
    Image ball;
    GraphicsContext graphicsContext;
    String side;
    double x;
    double y;
    Sprite sprite;
    Player player;

    boolean ballIsTaken = true;

    public Projectile(GraphicsContext gc, String side,double x,double y,double vitesse,double angle) {
        graphicsContext = gc;
        //this.player = player;
        this.side = side;
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        this.angle = angle;
        ball = new Image("assets/ball.png");
        sprite = new Sprite(ball, 0, 0, Duration.seconds(0.5), side);
        sprite.setX(x);
        sprite.setY(y);
    }
    void spriteAnimate() {
        //System.out.println("Animating sprite");
        if (!sprite.isRunning) {
            sprite.playContinuously();
        }
        sprite.setX(x);
        sprite.setY(y);
    }
    public void displayBall(){
        //changer de place
        graphicsContext.save(); // saves the current state on stack, including the current transform
        //System.out.println("x : "+getX());
        //System.out.println("y : "+getY());
        spriteAnimate();
        graphicsContext.drawImage(ball,x, y);
        graphicsContext.restore(); // back to original state

        //final double[] vector = new double[2];
        //vector[0] = Math.cos(Math.toRadians(angle+rotation));
        //vector[1] = Math.sin(Math.toRadians(angle+rotation));
        ///x += vector[0] * speed;
        //y += vector[1] * speed;

        //System.out.println(vecteur[0]);
        //System.out.println(vecteur[1]);


    }
    public Image getBall(){
        return this.ball;
    }
    public double getDirection(){
        return this.direction;
    }
    public void setDirection(double direction){
        this.direction = direction;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public void setVitesse(double v) {
        this.vitesse = v;
    }
    public void setAngle(double a) {
        this.angle = a;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isBallIsTaken() {
        return ballIsTaken;
    }

    public void setBallIsTaken(boolean ballIsTaken) {
        this.ballIsTaken = ballIsTaken;
    }
    public double getVitesse(){
        return this.vitesse;
    }
    public Sprite getSprite(){
        return this.sprite;
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {

        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }



}
