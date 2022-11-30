package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import java.util.Random;

public class Strategy1 extends Strategy {

    public Strategy1(Field gamefield) {
        super(gamefield);
    }

    @Override
    public void setPlayers() {
        equipe1[0] = new Player(gc, colorMap[0], w / 2, h - 100, "bottom");
        equipe1[1] = new Bot(gc, colorMap[0], w - 400, h - 100, "bottom", equipe2, this.gamefield);
        equipe1[2] = new Bot(gc, colorMap[0], w - 200, h - 100, "bottom", equipe2, this.gamefield);

        equipe2[0] = new Player(gc, colorMap[1], w / 2, 15, "top");
        equipe2[1] = new Bot(gc, colorMap[1], w - 400, 15, "top", equipe1, this.gamefield);
        equipe2[2] = new Bot(gc, colorMap[1], w - 200, 15, "top", equipe1, this.gamefield);
        gamefield.setField();
    }

    @Override
    public void setBallSpeed() {
        for (Player p : joueurs){
            p.setMaxBallSpeed(0.1); // vitesse de la balle
        }
    }

    @Override
    public void setPlayersSpeed() {
        for (int i = 0; i < this.joueurs.length; i++) {
            //Tous les bots ont une vitesse aleatoire entre 0.1 et 0.3
            double step = 0.0;
            if (joueurs[i].isBot) {
                Random r = new Random();
                step =  (r.nextInt((int)((0.3-0.1)*10+1))+0.1*10) / 10.0;
            } else {
                // on laisse les joueurs à 0.1 ou 0.2
                step = 0.2;
            }
            joueurs[i].setStep(step);
        }
    }

    @Override
    public void createAndGiveABallToOnePlayer() {
        joueurs[3].setHasBall(true);
        joueurs[3].createBall();
        gamefield.ball = joueurs[3].getBall();
        gamefield.ball.setVitesse(joueurs[3].getMaxBallSpeed());
    }
}
