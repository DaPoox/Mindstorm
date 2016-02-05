import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Etalonnage {
	/*
	 * 
	 * 
	 * 
	 * 				NE MARCHE PAS  !!
	 * 
	 * 
	 */
	Capteur cs;
	Fichier file;
	
	public Etalonnage(Capteur cs, Fichier file){
		this.cs = cs;
		this.file = file;
	}
	
	public void etalonner(){
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
	
	public String contenuFichier(){
		String contenu = "";
		contenu += cs.NOIR.toStringFile()+"\n";
		contenu += cs.BLEU.toStringFile()+'\n';
		contenu += cs.VERT.toStringFile()+'\n';
		contenu += cs.JAUNE.toStringFile()+'\n';
		contenu += cs.ROUGE.toStringFile()+'\n';
		contenu += cs.BLANC.toStringFile()+'\n';
		return contenu;
	}
}
