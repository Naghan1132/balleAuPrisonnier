package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Bot extends Player {

    public Bot(GraphicsContext gc, String color, int xInit, int yInit, String side) {
        super(gc, color, xInit, yInit, side);
        this.isBot = true;
        this.step = 0.6;
    }

    @Override
    public Projectile shoot() {
        return super.shoot();
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
