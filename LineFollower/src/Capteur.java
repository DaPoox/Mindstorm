import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.util.TextMenu;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class Capteur extends Thread{

	static Couleur BLEU;
	static Couleur JAUNE;
	static Couleur VERT;
	static Couleur ROUGE;
	static Couleur NOIR;
	static Couleur BLANC;
	
	private Color couleurCapte;
	public ColorSensor cs;
	
	public Capteur(SensorPort port){
		this.cs = new ColorSensor(port);
		BLEU = new Couleur();
		BLANC = new Couleur();
		VERT = new Couleur();
		JAUNE = new Couleur();
		NOIR = new Couleur();
		ROUGE = new Couleur();
	}
	public Color getColor(){
		return couleurCapte;
	}
	public void run(){
		do {
        	couleurCapte = cs.getColor();
        } while (!isInterrupted());
	}
	/*
	 * Placer le robot sur chaque couleur pendant quelques secondes, 
	 * le temps que le capteur lit les valeur est en deduit les Min et Max
	 * de la couleur.
	 */
	
	public boolean Calibrate(){
		String[] tabOption= {"Noir", "Bleu", "Vert", "Jaune", "Rouge", "Blanc", "Fin"};
		TextMenu textmenu = new TextMenu(tabOption);
		textmenu.setTitle("Choisir la couleur:");
		
		int selected;
		boolean finChoix = false;
		while(!finChoix){
			selected = textmenu.select();
			switch(selected){
			//Noir: 
			case 0:{
				//Noir:
				//Afficher le msg et Attendre l'utilisateur 
				afficherMsgCalibrate();
				LCD.drawString("sur le noir", 0, 2);
				Button.waitForAnyPress();
				while(Button.ENTER.isUp()){
					if(Button.ESCAPE.isDown()){
						break;
					}
				}
				LCD.clear();
				NOIR = new Couleur();
				NOIR = Calibrate(NOIR);
				NOIR.setCalibrated(true);
				tabOption[0]+= "--- OK";
				textmenu.setItems(tabOption);
				LCD.clear();
			}break;
			//Bleu: 
			case 1:{
				LCD.clear();
				//Bleu:
				//Afficher le msg et Attendre l'utilisateur 
				BLEU = new Couleur();
				afficherMsgCalibrate();
				LCD.drawString("sur le bleu", 0, 2);
				Button.waitForAnyPress();
				while(Button.ENTER.isUp()){
					if(Button.ESCAPE.isDown()){
						break;
					}
				}
				BLEU = Calibrate(BLEU);
				BLEU.setCalibrated(true);
				tabOption[1]+= "--- OK";
				textmenu.setItems(tabOption);
				LCD.clear();
			}break;
			//Vert: 
			case 2:{
				//Vert:
				//Afficher le msg et Attendre l'utilisateur 
				VERT = new Couleur();
				afficherMsgCalibrate();
				LCD.drawString("sur le vert", 0, 2);
				Button.waitForAnyPress();

				while(Button.ENTER.isUp()){
					if(Button.ESCAPE.isDown()){
						break;
					}
				}
				VERT = Calibrate(VERT);
				VERT.setCalibrated(true);
				tabOption[2]+= "--- OK";
				textmenu.setItems(tabOption);
				LCD.clear();
			}break;
			//Jaune: 
			case 3:{
				//Jaune:
				//Afficher le msg et Attendre l'utilisateur 
				JAUNE = new Couleur();
				afficherMsgCalibrate();
				LCD.drawString("sur le Jaune", 0, 2);
				Button.waitForAnyPress();

				while(Button.ENTER.isUp()){
					if(Button.ESCAPE.isDown()){
						break;
					}
				}
				JAUNE = Calibrate(JAUNE);
				JAUNE.setCalibrated(true);
				tabOption[3]+= "--- OK";
				textmenu.setItems(tabOption);
				LCD.clear();
			}break;
			//Rouge: 
			case 4:{
				//Rouge:
				//Afficher le msg et Attendre l'utilisateur 
				ROUGE = new Couleur();
				afficherMsgCalibrate();
				LCD.drawString("sur le Rouge", 0, 2);
				Button.waitForAnyPress();

				while(Button.ENTER.isUp()){
					if(Button.ESCAPE.isDown()){
						break;
					}
				}
				ROUGE = Calibrate(ROUGE);
				ROUGE.setCalibrated(true);
				tabOption[4]+= "--- OK";
				textmenu.setItems(tabOption);
				LCD.clear();
			}break;
			//Blanc: 
			case 5:{
				//Blanc:
				//Afficher le msg et Attendre l'utilisateur 
				BLANC = new Couleur();
				afficherMsgCalibrate();
				LCD.drawString("sur le Blanc", 0, 2);
				Button.waitForAnyPress();

				while(Button.ENTER.isUp()){
					if(Button.ESCAPE.isDown()){
						break;
					}
				}
				BLANC = Calibrate(BLANC);
				BLANC.setCalibrated(true);
				tabOption[5]+= "--- OK";
				textmenu.setItems(tabOption);
				LCD.clear();		
			}break;
			//Fin: 
			case 6:{
				finChoix = true;
			}break;
		}
			//Button.waitForAnyPress();
		}
		return true;
	}
	
	public void afficherMsgCalibrate(){
		LCD.clear();
		LCD.drawString("Placer Ccapteur", 0, 1);
		LCD.drawString("Entrer: Continuer", 0, 3);
		LCD.drawString("Esc: Quitter", 0, 4);
	
	}
	
	public Couleur Calibrate(Couleur c){
		Couleur clr = c;
		Color color;
		LCD.clear();
		
		//Executer la boucle pendant 5 secondes:
		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() < time+10000){
		int i=0;
		//while(i != 5){
			color = couleurCapte;
			LCD.clear();
			LCD.drawString("RGB: ", 0, 1);
			LCD.drawString(color.getRed()+" - "+color.getGreen()+" - "+color.getBlue(), 0, 2);
			clr = UpdateColor(clr, color);
			//Button.waitForAnyPress();
			i++;
		}	
		return clr;
	}

	public Couleur UpdateColor(Couleur clr, Color c){
		Couleur c2 = clr;
		
		//Composante Rouge: 
		if(clr.getMinR() > c.getRed())
			clr.setMinR(c.getRed());;
		if(clr.getMaxR() < c.getRed())
			clr.setMaxR(c.getRed());
		
		//Composante Verte:
		if(clr.getMinG() > c.getGreen())
			clr.setMinG(c.getGreen());
		if(clr.getMaxG() < c.getGreen())
			clr.setMaxG(c.getGreen());
		
		//Composante Bleue:
		if(clr.getMinB() > c.getBlue())
			clr.setMinB(c.getBlue());
		if(clr.getMaxB() <  c.getBlue())
			clr.setMaxB(c.getBlue());
		
		return c2;
	}
	
}
