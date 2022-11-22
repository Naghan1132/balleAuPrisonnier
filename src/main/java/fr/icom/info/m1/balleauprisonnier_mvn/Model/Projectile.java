package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Projectile {

    double vitesse = 0.2;
    double angle = 90;
    protected double direction;
    Image ball;
    GraphicsContext graphicsContext;
    String side;
    double x;
    double y;
    Sprite sprite;
    //Player player;

    boolean ballIsTaken = true;

    public Projectile(GraphicsContext gc, String side,double x,double y,double angle) {
        graphicsContext = gc;
        this.side = side;
        this.x = x;
        this.y = y;
        this.angle = angle;
        ball = new Image("assets/ball.png");
        sprite = new Sprite(ball, 0, 0, Duration.seconds(0.5), side);
        sprite.setX(x);
        sprite.setY(y);
    }
    public void spriteAnimate() {
        if (!sprite.isRunning) {
            sprite.playContinuously();
        }
        sprite.setX(x);
        sprite.setY(y);
    }

    public void display(){
        shooted();
        this.graphicsContext.save(); // saves the current state on stack, including the current transform
        rotate(this.graphicsContext, this.angle, this.x + this.ball.getWidth() / 2, this.y + this.ball.getHeight() / 2);
        this.graphicsContext.drawImage(this.ball,this.x,this.y);
        this.graphicsContext.restore(); // back to original state (before rotation)
    }
    public void shooted(){

        // Collision contre les murs
        if (x < 0 || x > 600 - ball.getWidth()) {
            angle = -angle;
        }
        // La balle s'arrete dans le camp adverse
        if (y < 0 || y > 600 - ball.getHeight()){
            this.setVitesse(0);
        }

        int rotation;
        if(side.equals("top")){
            rotation = 90;
        }
        else{
            rotation = 270;
        }
        final double[] vector = new double[2];
        vector[0] = Math.cos(Math.toRadians(angle+rotation));
        vector[1] = Math.sin(Math.toRadians(angle+rotation));
        x += vector[0] * this.vitesse;
        y += vector[1] * this.vitesse;
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
    public void reset(double x,double y,double a){
        this.x = x;
        this.y = y;
        this.angle= a;
    }



}
