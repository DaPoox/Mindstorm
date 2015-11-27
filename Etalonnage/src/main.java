import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.TextMenu;

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
/*import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class LineFollowerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ColorSensor cs = new ColorSensor(SensorPort.S1);
		Capteur d = new Capteur(SensorPort.S1);
		
		int sleep = 100;
		
		int i=0;
		
		Delay.msDelay(300);
		
		Motor.A.setSpeed(120);
		Motor.B.setSpeed(120);
		
		LCD.clear();
		LCD.drawString("Appuiyez", 1, 2);
		Button.waitForAnyPress();
		Delay.msDelay(300);
		
		d.start();
		
	    //cs.initWhiteBalance();
	    while(Button.ENTER.isUp()){

			//while((RougeLu>Rouge-20 && RougeLu<Rouge+20) &&(BleuLu>Bleu-20 && BleuLu<Bleu+20) &&(VertLu>Vert-20 && VertLu<Vert+20)){
			while(d.couleur == cs.BLUE && Button.ENTER.isUp()){

				Motor.A.setSpeed(200);
				Motor.B.setSpeed(200);
				//On est dans la ligne
				Motor.A.forward();
				Motor.B.forward();
			
			}
			sleep = 200;
			
			while(d.couleur != cs.BLUE && Button.ENTER.isUp()){
				LCD.clear();
				LCD.drawString("clr: "+d.couleur+" - B: "+cs.BLUE, 1, 2);
				//On est pas dans la ligne
				// i = 1 : Gauche, i = 2 Droite
				Motor.A.setSpeed(120);
				Motor.B.setSpeed(120);
				if(i==1){
					//Chercher à gauche:
					Motor.A.forward();
					Motor.B.backward();
					i = 2;
				}else{
					//Aller à droite:
					Motor.A.backward();
					Motor.B.forward();
					i = 1;
				}
				sleep += 200;
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		Motor.A.stop();
		Motor.B.stop();
		Button.waitForAnyPress();
	}

}
*/
