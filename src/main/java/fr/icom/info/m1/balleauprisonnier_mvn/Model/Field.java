package fr.icom.info.m1.balleauprisonnier_mvn.Model;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Classe du terrain de jeu.
 */

public class Field extends Canvas {

    private static Field instance;
    final GraphicsContext gc;
    final int width;
    final int height;
    public Projectile ball;
    /**
     * Joueurs
     */
    Player[] joueurs = new Player[6];
    Player[] equipe1 = new Player[3];
    Player[] equipe2 = new Player[3];
    /**
     * Couleurs possibles
     */
    String[] colorMap = new String[]{"blue", "green", "orange", "purple", "yellow"};
    /**
     * Tableau traçant les evenements
     */
    ArrayList<String> input = new ArrayList<String>();

    /**
     * Canvas dans lequel on va dessiner le jeu.
     *
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    public Field(int w, int h) {
        super(w, h);
        this.width = w;
        this.height = h;

        /** permet de capturer le focus et donc les evenements clavier et souris */
        this.setFocusTraversable(true);

        this.gc = this.getGraphicsContext2D();
    }

    public static Field getInstance() {
        if (instance == null) {
            instance = new Field(600, 600);
        }
        return instance;
    }

    public void setField() {

        int len1 = equipe1.length;
        int len2 = equipe2.length;

        System.arraycopy(equipe1, 0, joueurs, 0, len1);
        System.arraycopy(equipe2, 0, joueurs, len1, len2);

        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i].display(); // on affiche tous les joueurs
        }
    }

    public Player[] getJoueurs() {
        return joueurs;
    }

    public Player[] getEquipe1() {
        return equipe1;
    }

    public Player[] getEquipe2() {
        return equipe2;
    }

    public Projectile getProjectile() {
        return this.ball;
    }

    public GraphicsContext getGc() {
        return this.gc;
    }

    public boolean equipe1Won() {
        Integer cptDead = 0;
        for (Player p : this.equipe2) {
            if (p.isDead) {
                cptDead += 1;
            }
        }
        return cptDead == 3;
    }

    public boolean equipe2Won() {
        Integer cptDead = 0;
        for (Player p : this.equipe1) {
            if (p.isDead) {
                cptDead += 1;
            }
        }
        return cptDead == 3;
    }

    public Projectile getBall() {
        return ball;
    }

    public void setBall(Projectile ball) {
        this.ball = ball;
    }
}
