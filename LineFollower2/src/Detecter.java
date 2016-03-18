import lejos.nxt.Button;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;

public class Detecter {
	Capteur cs;
	Fichier file;
	Couleur[] tabCouleur = new Couleur[6];
	String[] tabNomCouleur = new String[6];
	
	public Detecter(Capteur cs, Fichier file){
		this.cs = cs;
		this.file = file;
	}
	public void initCouleurs(){
		tabCouleur[0] = file.read(0);//Noir
		tabNomCouleur[0] = "Noir";
		
		tabCouleur[1] = file.read(1);//Bleu
		tabNomCouleur[1] = "Bleu";
		
		tabCouleur[2] = file.read(2);//Vert
		tabNomCouleur[2] = "Vert";
		
		tabCouleur[3] = file.read(3);//Jaune
		tabNomCouleur[3] = "Jaune";
		
		tabCouleur[4] = file.read(4);//Rouge
		tabNomCouleur[4] = "Rouge";
		
		tabCouleur[5] = file.read(5);//Blanc
		tabNomCouleur[5] = "Blanc";
		
	}
	
	public void detecter(){
		Color couleur; 
		this.initCouleurs();
		
		LCD.clear();
		LCD.drawString("Mettre robot", 0, 0);
		LCD.drawString("Sur Couleur", 0, 1);
		LCD.drawString("Entrer", 0, 2);
		Button.waitForAnyPress();
		
		//Lire la couleur: 
		couleur = cs.getColor();
		
		//Comparer la couleur: 
		boolean trouv = false;
		int i = 0;
		while(i< 6 && trouv == false){
			if(this.tabCouleur[i].egale(couleur)) trouv = true;
			i ++;
		}
		if(trouv == false){
			LCD.clear();
			LCD.drawString("Couleur ", 0, 0);
			LCD.drawString("Inconnue", 0, 1);
			Button.waitForAnyPress();
		}else{
			LCD.clear();
			LCD.drawString("Couleur", 0, 0);
			LCD.drawString("Trouvee: "+this.tabNomCouleur[i-1], 0, 1);
			Button.waitForAnyPress();
			
		}
		LCD.clear();
		return;
	}
}

