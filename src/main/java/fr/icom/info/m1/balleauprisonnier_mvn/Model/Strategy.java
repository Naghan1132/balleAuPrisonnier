package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.GraphicsContext;

public abstract class Strategy {

    protected Field gamefield;
    protected Player[] joueurs;
    protected Player[] equipe1;
    protected Player[] equipe2;
    String[] colorMap = new String[]{"blue", "green", "orange", "purple", "yellow"};
    protected GraphicsContext gc;
    protected int w;
    protected int h;


    public Strategy(Field gamefield) {

        this.gamefield = gamefield;
        this.joueurs = this.gamefield.getJoueurs();
        this.equipe1 = this.gamefield.getEquipe1();
        this.equipe2 = this.gamefield.getEquipe2();
        this.gc = this.gamefield.getGc();
        this.w = gamefield.width;
        this.h = gamefield.height;
        this.colorMap = gamefield.colorMap;

        this.setPlayers();
        this.setPlayersSpeed();
        this.setBallSpeed();
    }

    public abstract void setPlayers();

    public abstract void setPlayersSpeed();

    public abstract void setBallSpeed();
}