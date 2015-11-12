import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class Capteur{

	static Couleur BLEU;
	static Couleur JAUNE;
	static Couleur VERT;
	static Couleur ROUGE;
	static Couleur NOIR;
	static Couleur BLANC;
	
	public ColorSensor cs;
	
	public Capteur(SensorPort port) {
		this.cs = new ColorSensor(port);
	}
	
	/*
	 * Placer le robot sur chaque couleur pendant quelques secondes, 
	 * le temps que le capteur lit les valeur est en deduit les Min et Max
	 * de la couleur.
	 */
	
	public boolean Calibrate(){
	//Noir:
		//Afficher le msg et Attendre l'utilisateur 
		afficherMsgCalibrate();
		LCD.drawString("sur le noir", 0, 2);
		while(Button.ENTER.isUp()){
			if(Button.ESCAPE.isDown()){
				return false;
			}
		}
		LCD.clear();
		NOIR = new Couleur();
		NOIR = Calibrate(NOIR);
			
	//Bleu:
		//Afficher le msg et Attendre l'utilisateur 
		BLEU = new Couleur();
		afficherMsgCalibrate();
		LCD.drawString("sur le bleu", 0, 2);
		while(Button.ENTER.isUp()){
			if(Button.ESCAPE.isDown()){
				return false;
			}
		}
		BLEU = Calibrate(BLEU);
		LCD.clear();

	//Vert:
		//Afficher le msg et Attendre l'utilisateur 
		VERT = new Couleur();
		afficherMsgCalibrate();
		LCD.drawString("sur le vert", 0, 2);
		while(Button.ENTER.isUp()){
			if(Button.ESCAPE.isDown()){
				return false;
			}
		}
		VERT = Calibrate(VERT);
		LCD.clear();
			
	//Jaune:
		//Afficher le msg et Attendre l'utilisateur 
		JAUNE = new Couleur();
		afficherMsgCalibrate();
		LCD.drawString("sur le Jaune", 0, 2);
		while(Button.ENTER.isUp()){
			if(Button.ESCAPE.isDown()){
				return false;
			}
		}
		JAUNE = Calibrate(JAUNE);
		LCD.clear();
		
	//Rouge:
		//Afficher le msg et Attendre l'utilisateur 
		ROUGE = new Couleur();
		afficherMsgCalibrate();
		LCD.drawString("sur le Rouge", 0, 2);
		while(Button.ENTER.isUp()){
			if(Button.ESCAPE.isDown()){
				return false;
			}
		}
		ROUGE = Calibrate(ROUGE);
		LCD.clear();
		
	//Blanc:
		//Afficher le msg et Attendre l'utilisateur 
		BLANC = new Couleur();
		afficherMsgCalibrate();
		LCD.drawString("sur le Blanc", 0, 2);
		while(Button.ENTER.isUp()){
			if(Button.ESCAPE.isDown()){
				return false;
			}
		}
		BLANC = Calibrate(BLANC);
		LCD.clear();		
	
		return true;
	}
	
	public void afficherMsgCalibrate(){
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
		while(System.currentTimeMillis() < time+5000){
			color = cs.getColor();
			LCD.drawString("RGB: ", 0, 1);
			LCD.drawString(color.getRed()+" - "+color.getGreen()+" - "+color.getBlue(), 0, 2);
			clr = UpdateColor(clr, color);
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
