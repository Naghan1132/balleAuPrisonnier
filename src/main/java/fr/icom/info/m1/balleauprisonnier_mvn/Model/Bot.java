package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Bot extends Player {
    Player[] equipeAdverse;
    public Bot(GraphicsContext gc, String color, int xInit, int yInit, String side,Player[] equipeAdverse) {
        super(gc, color, xInit, yInit, side);
        this.isBot = true;
        this.step = 0.4;
        this.equipeAdverse = equipeAdverse; // pour l'IA
    }

    @Override
    public Projectile shoot() {
        //choisir le meilleur angle pour tirer là où il y a le + de joueurs
        angle = chooseBestAngle();
        sprite.playShoot();
        this.ball.setAngle(angle);
        Projectile ballShooted = this.ball;
        this.setHasBall(false);
        this.setBall(null);
        return ballShooted;
    }

    public double chooseBestAngle(){
        for (int i = 0;i < this.equipeAdverse.length;i++){
            System.out.println(equipeAdverse[i].getX());
        }
        return 0.0;
    }

    @Override
    public void turn(String direc) {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            super.turn("left");
        } else {
            super.turn("right");
        }
    }

    @Override
    public void move(String direc) {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            super.move("left");
        } else {
            super.move("right");
        }

    }
}
