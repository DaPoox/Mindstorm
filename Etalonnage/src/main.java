import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Capteur cs = new Capteur(SensorPort.S1);
		LCD.drawString("Entrer pour", 0, 0);
		LCD.drawString("Demarrer ", 0, 1);
		LCD.drawString("L\'étalonnage", 0, 2);
		Button.waitForAnyPress();
		LCD.clear();
		
		boolean resultat = cs.Calibrate();
		LCD.clear();
		LCD.drawString("Finish", 0, 0);
		if(resultat){
			LCD.drawString("Noir: \n"+cs.NOIR.toString(), 0, 2);
			Button.waitForAnyPress();
			LCD.clear();
			
			LCD.drawString("Rouge: \n"+cs.ROUGE.toString(), 0, 2);
			Button.waitForAnyPress();
			LCD.clear();
			
			LCD.drawString("Bleu: \n"+cs.BLEU.toString(), 0, 2);
			Button.waitForAnyPress();
			LCD.clear();
			
			LCD.drawString("JAUNE: \n"+cs.JAUNE.toString(), 0, 2);
			Button.waitForAnyPress();
			LCD.clear();
			
			LCD.drawString("VERT: \n"+cs.VERT.toString(), 0, 2);
			Button.waitForAnyPress();
			LCD.clear();
			
			LCD.drawString("Blanc: \n"+cs.BLANC.toString(), 0, 2);
			Button.waitForAnyPress();
			LCD.clear();
		}else System.out.println("Erreur false");
		Button.waitForAnyPress();
		
		
		
	}

}
