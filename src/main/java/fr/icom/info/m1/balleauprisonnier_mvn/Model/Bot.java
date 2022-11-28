package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;

import java.lang.Math;
import java.util.Random;

public class Bot extends Player{
    Player[] equipeAdverse;

    public Bot(GraphicsContext gc, String color, int xInit, int yInit, String side, Player[] equipeAdverse) {
        super(gc, color, xInit, yInit, side);
        this.isBot = true;
        this.step = 0.4;
        this.equipeAdverse = equipeAdverse; // pour l'IA
    }

    @Override
    public Projectile shoot() {

        //choisir le meilleur angle pour tirer là où il y a un joueur OK
        this.angle = chooseBestAngle(); // enemis a droite donc on mets moins
        this.ball.setAngle(this.angle);
        Projectile ballShooted = this.ball;
        this.setHasBall(false);
        this.setBall(null);
        sprite.playShoot();
        return ballShooted;
    }

    public double chooseBestAngle() {
        // MARCHE POUR  LES BOTS DU HAUT !! (à voir avec ceux du bas si correction bug affichage=
        //faire trigo :
        Random random = new Random();
        int indiceTarget= random.nextInt(2 - 0 + 1) + 0;
        Player target = equipeAdverse[indiceTarget]; // on prends une cible au hasard

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
            System.out.println("meilleur angle : " + bestAngle);

            //tester si enemi a droite ou a gauche de lui - ou + :
            if(coordTarget[0] > coordBot[0]){
                // a droite ou devant : (-)
                return -bestAngle;
            }else{
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
        }else{
            super.turn("right");
        }
    }




    @Override
    public void move(String direc) {
        //aller chercher la balle si elle est dans leurs camps sinon random
        Random rand = new Random();
        if (rand.nextBoolean()) {
            super.move("left");
        } else {
            super.move("right");
        }

    }
}
