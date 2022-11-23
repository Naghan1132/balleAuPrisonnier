package fr.icom.info.m1.balleauprisonnier_mvn.Model;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {

	private static Field instance;

	/** Joueurs */
	Player [] joueurs = new Player[6];
	Player [] equipe1 = new Player[3];
	Player [] equipe2 = new Player[3];
	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    final GraphicsContext gc;
    final int width;
    final int height;
	Projectile ball;
    
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

		gc = this.getGraphicsContext2D();

		/** On initialise le terrain de jeu */
		equipe1[0] = new Player(gc, colorMap[0], w / 2, h - 30, "bottom");
		equipe1[1] = new Bot(gc, colorMap[0], w - 400, h - 30, "bottom",equipe2);
		equipe1[2] = new Bot(gc, colorMap[0], w - 200, h - 30, "bottom",equipe2);
		equipe2[0] = new Player(gc, colorMap[1], w / 2, 15, "top");
		equipe2[1] = new Bot(gc, colorMap[1], w - 400, 15, "top",equipe1);
		equipe2[2] = new Bot(gc, colorMap[1], w - 200, 15, "top",equipe1);

		int len1 = equipe1.length;
		int len2 = equipe2.length;

		System.arraycopy(equipe1, 0, joueurs, 0, len1);
		System.arraycopy(equipe2, 0, joueurs, len1, len2);

		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i].display(); // on affiche tous les joueurs
		}

		joueurs[3].setHasBall(true);
		joueurs[3].createBall();
		ball = joueurs[3].getBall();

		//si la balle touche le sol (personne) alors un joueur doit la 'ramasser'
		//si la balle touche qql il meurt
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

	public Projectile getProjectile(){
		return this.ball;
	}

	public GraphicsContext getGc(){
		return this.gc;
	}

	public static Field getInstance(){
		if(instance == null){
			instance = new Field(600, 600);
		}
		return instance;
	}
}
