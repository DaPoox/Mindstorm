import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Capteur cs = new Capteur(SensorPort.S1);
		int sleep = 200;
		cs.start();
		
		LCD.drawString("Entrer pour", 0, 0);
		LCD.drawString("Demarrer ", 0, 1);
		LCD.drawString("L\'étalonnage", 0, 2);
		Button.waitForAnyPress();
		LCD.clear();
		
		boolean resultat = cs.Calibrate();
		LCD.clear();
		LCD.drawString("Finish", 0, 0);
		if(!resultat){
			//Erreur dans l'étalonnage, quitter le programme:
			return;
		}
		//Version beta: suivre une ligne bleu
		LCD.clear();
		LCD.drawString(cs.BLEU.toString(), 0, 1);
		Button.waitForAnyPress();
		LCD.clear();
		
		LCD.drawString("Appuiyez Pour", 1, 1);
		LCD.drawString("Demarrer", 2, 1);
		Button.waitForAnyPress();
		int i= 1;
		Delay.msDelay(200);
		while(Button.ESCAPE.isUp()){
			LCD.clear();
			Delay.msDelay(200);
			while(cs.BLEU.egale(cs.getColor())  && Button.ENTER.isUp() ){
				//Avancer
				Motor.A.setSpeed(150);
				Motor.C.setSpeed(150);
				
				Motor.A.forward();
				Motor.C.forward();
			}
			Delay.msDelay(50);
			sleep = 200;
			LCD.clear();
			LCD.drawString("hors ligne", 0, 0);
			//Button.waitForAnyPress();
			LCD.clear();
			//Rechercher la ligne: 
			long time = System.currentTimeMillis();
			while(!(cs.BLEU.egale(cs.getColor())) && Button.ENTER.isUp()){
				sleep+= 50;
				time = System.currentTimeMillis();
				while(System.currentTimeMillis() < time+sleep && !(cs.BLEU.egale(cs.getColor()))){
					//On est pas dans la ligne
					// i = 1 : Gauche, i = 2 Droite
					Motor.A.setSpeed(120);
					Motor.C.setSpeed(120);
					if(i==1){
						//Chercher à gauche:
						Motor.A.forward();
						Motor.C.backward();
					}else{
						//Aller à droite:
						Motor.A.backward();
						Motor.C.forward();
					}
				}
				if(i == 1) i = 2;
				else i = 1;
				//Delay.msDelay(sleep);
			}
			
		}
		Motor.A.stop();
		Motor.C.stop();
		LCD.clear();
		System.out.println("wiw\n");
		Button.waitForAnyPress();
	}

}
