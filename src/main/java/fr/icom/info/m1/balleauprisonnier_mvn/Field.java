package fr.icom.info.m1.balleauprisonnier_mvn;


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
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	public Field(Scene scene, int w, int h) 
	{
		super(w, h); 
		width = w;
		height = h;
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();
        
        /** On initialise le terrain de jeu */
    	equipe1[0] = new Player(gc, colorMap[0], w/2, h-30, "bottom");
		equipe1[1] = new Bot(gc, colorMap[0], w-400, h-30, "bottom");
		equipe1[2] = new Bot(gc, colorMap[0], w-200, h-30, "bottom");
		equipe2[0] = new Player(gc, colorMap[1], w/2, 15, "top");
		equipe2[1] = new Bot(gc, colorMap[1], w-400, 15, "top");
		equipe2[2] = new Bot(gc, colorMap[1], w-200, 15, "top");

		int len1 = equipe1.length;
		int len2 = equipe2.length;

		System.arraycopy(equipe1, 0, joueurs, 0, len1);
		System.arraycopy(equipe2, 0, joueurs, len1, len2);

		for (int i=0;i<joueurs.length;i++){
			joueurs[i].display(); // on affiche tous les joueurs
		}

		// 1 Balle par jeu : Initialise la balle
		Projectile ball = new Projectile(gc,"top",joueurs[3].x,joueurs[3].y,joueurs[3]);
		joueurs[3].setBall(ball);
		joueurs[3].setHasBall(true);


		//display la balle dans la boucle
		//si la balle touche le sol (personne) alors la remettre à qql d'aléatoire dans le camp en question
		//si la balle touche qql il meurt



	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) )
	    	                input.add( code );
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer() 
	    {
	        public void handle(long currentNanoTime)
	        {	 
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	        	
	            // Deplacement et affichage des joueurs
	        	for (int i = 0; i < joueurs.length; i++) 
	    	    {
					if(colision(joueurs[i],ball)){
						//le joueur meurt balle chez le camp perdant
					}
					if(!ball.ballIsTaken){
						ball.displayBall();
					}
					if(joueurs[i].hasBall){
						joueurs[i].displayBall();
					}
					if(joueurs[i].isBot){
						joueurs[i].move();
						joueurs[i].turn();
					}
	        		if (joueurs[i].side == "bottom" && input.contains("LEFT") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].moveLeft();
	        		} 
	        		if (joueurs[i].side == "bottom" && input.contains("RIGHT") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].moveRight();	        			
	        		}
	        		if (joueurs[i].side == "bottom" && input.contains("UP") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].turnLeft();
	        		} 
	        		if (joueurs[i].side == "bottom"&& input.contains("DOWN") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].turnRight();	        			
	        		}
					if (joueurs[i].side == "bottom" && input.contains("ENTER") && !joueurs[i].isBot && joueurs[i].hasBall){
						joueurs[i].shoot();
					}
	        		if (joueurs[i].side == "top" && input.contains("Q") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].moveLeft();
	        		} 
	        		if (joueurs[i].side == "top" && input.contains("D") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].moveRight();	        			
	        		}
	        		if (joueurs[i].side == "top" && input.contains("Z") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].turnLeft();
	        		} 
	        		if (joueurs[i].side == "top"&& input.contains("S") && !joueurs[i].isBot)
	        		{
	        			joueurs[i].turnRight();	        			
	        		}
	        		if (joueurs[i].side == "top" && input.contains("SPACE") && !joueurs[i].isBot && joueurs[i].hasBall){
	        			joueurs[i].shoot();
					}
	        		joueurs[i].display();
	    	    }
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
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

	public boolean colision (Player joueur,Projectile ball){
		Sprite sprite1 = joueur.getSprite();
		Sprite sprite2 = ball.getSprite();

		double x1 = joueur.getX();
		double y1 = joueur.getY();



		double h1 = sprite1.getHauteurImage();
		double h2 = sprite2.getHauteurImage();
		double t1 = sprite1.getTailleImage();
		double t2 = sprite2.getTailleImage();

		//coordonneesJoueur =;
		for (double i = x1;i<h1;i++){

		}

		//récupérer les coordonnées

		if(h1 == h2 && t1 == t2){
			//return true;
		}else{
			//bornes de la hitbox :
			//for (int i=-30;i<30;i++){
			//	if(t1+i == t2){
					//t1
			//	}
			//}
		}
		return false;
	}
}
