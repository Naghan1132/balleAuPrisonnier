package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends Player {
    Player[] equipeAdverse;
    Field field;


    public Bot(GraphicsContext gc, String color, int xInit, int yInit, String side, Player[] equipeAdverse, Field field) {
        super(gc, color, xInit, yInit, side);
        this.isBot = true;
        this.step = 0.4;
        this.equipeAdverse = equipeAdverse;
        this.field = field;
    }

    @Override
    public Projectile shoot() {
        //choisir le meilleur angle pour tirer là où il y a un joueur OK
        this.angle = chooseBestAngle();
        this.ball.setAngle(this.angle);
        Projectile ballShooted = this.ball;
        this.ball.setShootedFrom(this.getSide());
        this.setHasBall(false);
        this.setBall(null);
        ballShooted.setVitesse(0.2);
        sprite.playShoot();
        return ballShooted;
    }

    public double chooseBestAngle() {
        List<Player> aliveEnemies = new ArrayList<>();
        // choisir parmit les enemis vivants :
        for (Player p : equipeAdverse) {
            if (!p.isDead) {
                aliveEnemies.add(p);
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(aliveEnemies.size());
        Player target = aliveEnemies.get(randomIndex); // on prends une cible au hasard

        // on imagine un triangle rectangle (sommmets = camps adverse,bot,target)
        // on trace une ligne verticale entre le bot qui tire et le camps adverse
        // et du bot a la target, on cherche cet angle !!
        if (this.side == "top") {
            double[] coordCampsAdverse = new double[2];
            coordCampsAdverse[0] = this.x;
            coordCampsAdverse[1] = target.getY(); // => 570 pour les joueurs en bas

            double[] coordBot = new double[2];
            coordBot[0] = this.x;
            coordBot[1] = this.y;

            double[] coordTarget = new double[2];
            coordTarget[0] = target.getX();
            coordTarget[1] = target.getY();

            double[] vectU = new double[2];
            // u  = (coordTarget - coordBot)
            vectU[0] = coordTarget[0] - coordBot[0];
            vectU[1] = coordTarget[1] - coordBot[1];

            double[] vectV = new double[2];
            // v  = (coordTarget - coordBot)
            vectV[0] = coordCampsAdverse[0] - coordBot[0];
            vectV[1] = coordCampsAdverse[1] - coordBot[1];

            double prodscal = vectU[0] * vectV[0] + vectU[1] * vectV[1];

            double NormeU = Math.sqrt(Math.pow(vectU[0], 2) + Math.pow(vectU[1], 2));
            double NormeV = Math.sqrt(Math.pow(vectV[0], 2) + Math.pow(vectV[1], 2));

            double bestAngle = Math.acos(prodscal / (NormeU * NormeV)) * 180 / Math.PI;
            //tester si enemi a droite ou a gauche de lui - ou + :
            if (coordTarget[0] > coordBot[0]) {
                return -bestAngle;
            } else {
                return bestAngle;
            }
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
        //aller chercher la balle si elle est dans leurs camps sinon random

        boolean ballPicked = false;
        Player[] equipeALlie = new Player[3];
        if (this.getSide() == "top") {
            equipeALlie = this.field.getEquipe2();
        } else {
            equipeALlie = this.field.getEquipe1();
        }
        for (Player p : equipeALlie) {
            if (p.hasBall()) {
                ballPicked = true;
            }
        }
        if (this.field.getBall().getVitesse() == 0) {
            if (this.field.getBall().getY() <= 100 && !ballPicked && this.getSide() == "top") {
                double xB = this.field.getBall().getX();
                if (this.x < xB) {
                    super.move("right");
                } else {
                    super.move("left");
                }
            } else if (this.field.getBall().getY() >= 500 && !ballPicked && this.getSide() == "bottom") {
                double xB = this.field.getBall().getX();
                if (this.x < xB) {
                    super.move("right");
                } else {
                    super.move("left");
                }
            }

        } else {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                super.move("left");
            } else {
                super.move("right");
            }
        }
    }
}
