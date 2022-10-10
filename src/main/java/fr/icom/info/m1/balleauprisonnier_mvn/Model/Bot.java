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
    void shoot() {
        super.shoot();
    }

    @Override
    void turn() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            super.turnRight();
        } else {
            super.turnLeft();
        }
    }

    @Override
    void move() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            super.moveLeft();
        } else {
            super.moveRight();
        }

    }
}
