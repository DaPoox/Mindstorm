import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.pathfinding.Path;
import lejos.util.TextMenu;

public class SuiveurLigne {
	Capteur cs;
	
	Fichier file;
	String[] tabOption= new String[6];
	Couleur[] couleurs = new Couleur[6];
	
	Couleur couleurDebut;
	Couleur couleurFin;
	Couleur couleurLigne;
	Couleur couleurBlanc;
	
	Path chemin;
	
	public SuiveurLigne(Capteur cs, Fichier file){
		this.cs = cs;
		this.file = file;
		chemin = new Path();
	}
	
	public void initMenu(){
		cs.NOIR = file.read(0);
		cs.BLEU = file.read(1);
		cs.VERT = file.read(2);
		cs.JAUNE = file.read(3);
		cs.ROUGE = file.read(4);
		cs.BLANC = file.read(5);
		int i=0;
		if(cs.NOIR.isCalibrated()){
			tabOption[i] = "Noir";
			couleurs[i] = cs.NOIR;
			i++;
		}
		if(cs.BLEU.isCalibrated()){
			tabOption[i] = "Bleu";
			couleurs[i] = cs.BLEU;

			i++;
		}
		if(cs.VERT.isCalibrated()){
			tabOption[i] = "Vert";
			couleurs[i] = cs.VERT;

			i++;
		}
		if(cs.JAUNE.isCalibrated()){
			tabOption[i] = "Jaune";
			couleurs[i] = cs.JAUNE;

			i++;
		}
		if(cs.ROUGE.isCalibrated()){
			tabOption[i] = "Rouge";
			couleurs[i] = cs.ROUGE;

			i++;
		}
		if(cs.BLANC.isCalibrated()){
			tabOption[i] = "Blanc";
			couleurs[i] = cs.BLANC;

			i++;
		}
		tabOption[i] = "Retour";
		
	}
	
	public int menuCouleurLigne(){
		LCD.clear();
		TextMenu textmenu = new TextMenu(tabOption);
		textmenu.setTitle("Couleur ligne");
		return textmenu.select();
	}
	
	public int menuCouleurDebut(){
		LCD.clear();
		TextMenu textmenu = new TextMenu(tabOption);
		textmenu.setTitle("Couleur debut");
		return textmenu.select();
	}
	
	public int menuCouleurFin(){
		LCD.clear();
		TextMenu textmenu = new TextMenu(tabOption);
		textmenu.setTitle("Couleur Fin");
		
		return textmenu.select();
	}

	public void initCouleur(){
		int choix = 0;
		choix = menuCouleurDebut();
		if(choix == tabOption.length-1){
			return;
		}else couleurDebut = couleurs[choix];
		
		choix = menuCouleurFin();		
		if(choix == tabOption.length-1){
			return;
		}else couleurFin = couleurs[choix];
		
		choix = menuCouleurLigne();
		if(choix == tabOption.length-1){
			return;
		}else couleurLigne = couleurs[choix];
	}
	
	
	public boolean SuivreLigneAller(){
		this.initMenu();
		this.initCouleur();
		Mouvement mv = new Mouvement(cs);
		
		LCD.clear();
		System.out.println("Enter pour lancer");
		Button.waitForAnyPress();
		
		//Mouvement mouvement = new Mouvement(180);
		/*
		 * Pseudocode 
		 * 
		 * - Tantque ce n'est pas rouge, on avance
		 * - Quand c'est Rouge, on arrete
		 * - Faire demi tour
		 * - Tant que ce n'est pas jaune, on avance
		 * - Quand c'est jaune, on arrete
		 * 
		 */
		
		//Sortir de la couleur debut
		mv.sortirDebut(couleurDebut, couleurLigne);
				
		//Detecter le bord: 
		mv.getBord();
		
		//Suivre la ligne allez vers la fin, on recupère le chemin 
		this.chemin = mv.avancer(couleurLigne, couleurFin);		

		mv.stop();
		/*
		 * Refaire/dessiner le chemin...
		 */
		System.out.println("Refaire/dessiner le chemin...\n");
		Button.waitForAnyPress();
		
		refaireChemin();

		
		return true;
	}
	
	public boolean SuivreLigneAllerRetour(){
		this.initMenu();
		this.initCouleur();
		Mouvement mv = new Mouvement(cs);
	
		LCD.clear();
		System.out.println("Enter pour lancer");
		Button.waitForAnyPress();
		
		//Mouvement mouvement = new Mouvement(180);
		/*
		 * Pseudocode 
		 * 
		 * - Tantque ce n'est pas rouge, on avance
		 * - Quand c'est Rouge, on arrete
		 * - Faire demi tour
		 * - Tant que ce n'est pas jaune, on avance
		 * - Quand c'est jaune, on arrete
		 * 
		 */
		
		//Sortir de la couleur debut
		mv.sortirDebut(couleurDebut, couleurLigne);
				
		//Detecter le bord: 
		mv.getBord();
		
		//Suivre la ligne allez vers la fin
		mv.avancer(couleurLigne, couleurFin);		
		
		mv.stop();
		//Faire demi-tour: 
		mv.demiTour(couleurLigne);
		
		//Suivre la ligne et revenir au debut
		mv.avancer(couleurLigne, couleurDebut);
		
		mv.stop();
		return true;
	}
	
	public void refaireChemin(){

		Mouvement mv = new Mouvement(cs);
		mv.follow(this.chemin);
	}
}
