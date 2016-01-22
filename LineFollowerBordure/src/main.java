import lejos.nxt.Button;
import lejos.nxt.ColorSensor.Color;
import lejos.util.Delay;
import lejos.util.TextMenu;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class main {
	
	static Capteur cs;
	static Fichier file;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		file = new Fichier();
		cs = new Capteur(SensorPort.S1);
		cs.start();
		
		/*
		 * Choix, etalonnage, demarrer...
		 */
		
		String[] tabOption= {"Etalonnage", "Suivre ligne"};
		TextMenu textmenu = new TextMenu(tabOption);
		textmenu.setTitle("Que faire?");
		LCD.drawString("Suiveur de ligne", 0, 4);
		LCD.drawString("needs etalonnage", 0, 5);
		
		boolean good = false;
		while(!good){
			int selected = textmenu.select();
			switch(selected){
				case 0: Etalonnage();
				break;
				case 1: good = SuivreLigne();
				break;
			}
		}
	}
	
	public static void Etalonnage(){
		LCD.clear();
		LCD.drawString("Entrer pour", 0, 0);
		LCD.drawString("Demarrer ", 0, 1);
		LCD.drawString("L\'etalonnage", 0, 2);
		Button.waitForAnyPress();
		LCD.clear();


		while(cs.VERT.isCalibrated() == false && cs.ROUGE.isCalibrated() == false && cs.JAUNE.isCalibrated() == false){
			LCD.drawString("Etalonnage ", 0, 0);
			LCD.drawString("Vert, Rouge ", 0, 1);
			LCD.drawString("et Jaune ", 0, 2);
			
			Button.waitForAnyPress();
			LCD.clear();
			boolean resultat = cs.Calibrate();
			LCD.clear();
			LCD.drawString("Finish", 0, 0);
			if(!resultat){
				//Erreur dans l'�talonnage, quitter le programme:
				return;
			}
		}
		//Enregistrement des couleurs dans le fichier:
		file.write(contenuFichier());
		System.out.print("file done");
		Button.waitForAnyPress();
	}
	
	public static boolean SuivreLigne(){
		LCD.clear();

		cs.VERT = file.read(2);//le vert c'est la 3eme ligne dans le fichier
		cs.ROUGE = file.read(4);//le rouge c'est la 5eme ligne dans le fichier
		cs.JAUNE = file.read(3);//Le jaune c'est la 4eme ligne dans le fichier
		LCD.clear();
		System.out.println("Read done...");
		Button.waitForAnyPress();
		
		//Vérification des couleurs: 
		if(!cs.VERT.isCalibrated()){
			LCD.clear();
			System.out.println("Vert pas pret");
			return false;
		}
		Button.waitForAnyPress();
		if(!cs.JAUNE.isCalibrated()){
			LCD.clear();
			System.out.println("Jaune pas pret");
			return false;
		}
		Button.waitForAnyPress();
		if(!cs.ROUGE.isCalibrated()){
			LCD.clear();
			System.out.println("Rouge pas pret");
			return false;
		}
		/*
		 * Affichage des couleurs...
		 */
		Button.waitForAnyPress();
/*
 * Excpetion is down here ... ... ...
 */
		//Vert: 
		LCD.clear();
		System.out.println(cs.VERT.toString());
		Button.waitForAnyPress();
		System.out.println("Here..\n");
		LCD.drawString(cs.getColor().getRed()+"-"+cs.getColor().getGreen()+"-"+cs.getColor().getBlue(), 0, 5);
		Button.waitForAnyPress();

	
		
		//Rouge: 
		LCD.clear();
		System.out.println(cs.ROUGE.toString());
		LCD.drawString(cs.getColor().getRed()+"-"+cs.getColor().getGreen()+"-"+cs.getColor().getBlue(), 0, 5);
		Button.waitForAnyPress();
		
		//Jaune: 
		LCD.clear();
		System.out.println(cs.JAUNE.toString());
		LCD.drawString(cs.getColor().getRed()+"-"+cs.getColor().getGreen()+"-"+cs.getColor().getBlue(), 0, 5);
		Button.waitForAnyPress();
		
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
		
		/*
		 * varier X et Y 
		 * Y = X + 1... 
		 * Acceleration 
		 * 
		 */

		while(!cs.ROUGE.egale(cs.getColor())){
			avancer();				
		}
		Motor.A.stop();
		Motor.C.stop();
		demiTour();
		while(!cs.JAUNE.egale(cs.getColor())){
			avancer();
		}
		Motor.A.stop();
		Motor.C.stop();
		
		return true;
	}
	
	public static String contenuFichier(){
		String contenu = "";
		contenu += cs.NOIR.toStringFile()+"\n";
		contenu += cs.BLEU.toStringFile()+'\n';
		contenu += cs.VERT.toStringFile()+'\n';
		contenu += cs.JAUNE.toStringFile()+'\n';
		contenu += cs.ROUGE.toStringFile()+'\n';
		contenu += cs.BLANC.toStringFile()+'\n';
		return contenu;
	}
	public static void avancer(){	
		while(cs.VERT.egale(cs.getColor())){
			//Tourner à droite
			Motor.A.setSpeed(110);//110
			Motor.C.setSpeed(250);//250
			
			Motor.A.forward();
			Motor.C.forward();
			//mouvement.trounerDroite(180+50);
		}
			//Tourner à gauche:
			Motor.C.setSpeed(110);//110
			Motor.A.setSpeed(250);//250
			
			Motor.A.forward();
			Motor.C.forward();
	}

	public static void demiTour(){
		Motor.A.forward();
		Motor.C.backward();
		Delay.msDelay(200);
		while(!cs.VERT.egale(cs.getColor())){
			Motor.A.forward();
			Motor.C.backward();	
		}
		Motor.A.stop();
		Motor.C.stop();
	}
}
