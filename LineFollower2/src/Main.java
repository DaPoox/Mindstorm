import lejos.nxt.Button;
import lejos.nxt.ColorSensor.Color;
import lejos.util.Delay;
import lejos.util.TextMenu;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class Main {
	
	static Capteur cs;
	
	static Fichier file;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		file = new Fichier("couleur.dat");//Fichier qui contient les infos sur les couleurs
		cs = new Capteur(SensorPort.S1);
		cs.start();

		SuiveurLigne sv = new SuiveurLigne(cs,file);
		Detecter dt = new Detecter(cs, file);
		
		/*
		 * Choix, etalonnage, demarrer...
		 */
		
		String[] tabOption= {"Etalonnage", "SuivLigne(A/R)", "SuivLigne(Aller))", "Detecter"};
		TextMenu textmenu = new TextMenu(tabOption);
		textmenu.setTitle("Que faire?");
		LCD.drawString("Suiveur de ligne", 0, 4);
		LCD.drawString("needs etalonnage", 0, 5);
		
		boolean good = false;
		while(true){
			LCD.clear();
			int selected = textmenu.select();
			switch(selected){
				case 0: Etalonnage();
				break;
				case 1: sv.SuivreLigneAllerRetour();
				break;
				case 2: sv.SuivreLigneAller();
				//case 2: sv.testerCode();
				break;
				case 3: dt.detecter();
				break;
			}
		}
	}

//**** Etalonnage: 
	public static void Etalonnage(){
		LCD.clear();
		LCD.drawString("Entrer pour", 0, 0);
		LCD.drawString("Demarrer ", 0, 1);
		LCD.drawString("L\'etalonnage", 0, 2);
		Button.waitForAnyPress();
		LCD.clear();
		
			Button.waitForAnyPress();
			LCD.clear();
			boolean resultat = cs.Calibrate();
			LCD.clear();
			LCD.drawString("Finish", 0, 0);
			if(!resultat){
				//Erreur dans l'etalonnage, quitter le programme:
				return;
			}
			
		//Enregistrement des couleurs dans le fichier:
		file.write(contenuFichier());
		System.out.print("file done");
		Button.waitForAnyPress();
	}
	
	public static String contenuFichier(){
		String contenu = "";
		contenu += cs.NOIR.toStringFile()+"\n";
		contenu += cs.BLEU.toStringFile()+'\n';
		contenu += cs.VERT.toStringFile()+'\n';
		contenu += cs.JAUNE.toStringFile()+'\n';
		contenu += cs.ROUGE.toStringFile()+'\n';
		contenu += cs.ORANGE.toStringFile()+'\n';
		return contenu;
	}
}