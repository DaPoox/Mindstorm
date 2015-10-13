import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * - Bouton pour demarrer le moteur
		 * - Appuyez de nouveau pour le faire trouner sense inversé.
		 * - Appuez de nouveau pour l'arreter
		 */
		/*
		LCD.drawString("Programme 1", 0,0);
		Button.waitForAnyPress();
		Motor.A.forward();
		LCD.drawString("FORWARD", 0,1);
		Button.waitForAnyPress();
		Motor.A.backward();
		LCD.drawString("BACKWARD", 0, 2);
		Button.waitForAnyPress();
		Motor.A.stop();
		*/
/*
		//Partie 2:
		 * 
		 * - Affiche l'angle
		 * -  Trouner le moteur vitesse: 2 tour par seconde
		 * - attendre 1 seconde
		 * - Afficher l'angle du moteur
		 * - Tourner le moteur sense contraire et afficher l'angle tant que ce n'est
		 * 		pas 0;
		 */
		/*
		LCD.drawString("Programme 2", 0, 0);
		LCD.drawString(""+Motor.A.getTachoCount(), 0, 1);
		Motor.A.setSpeed(360*2);
		Motor.A.forward();
		Delay.msDelay(1000);
		LCD.drawString(""+Motor.A.getTachoCount(), 0, 2);	
		Motor.A.stop();
		LCD.drawString(""+Motor.A.getTachoCount(), 0, 3);	
		Motor.A.backward();
		while(Motor.A.getTachoCount() != 0){};
		LCD.drawString(""+Motor.A.getTachoCount(), 0, 4);	
		Motor.A.stop();
		Button.waitForAnyPress();
*/
		
		/*
		 * //Partie 3
		 * Tourner le moteur 4 tours
		 * faire tourner le moteur pour qu'il revien a son etat initial
		LCD.drawString("Programme 3", 0, 0);
		Button.waitForAnyPress();
		Motor.A.rotate(4*360);
		LCD.drawInt(Motor.A.getTachoCount(),0, 1);
		Motor.A.rotateTo(0);
		LCD.drawInt(Motor.A.getTachoCount(),0, 1);
		Button.waitForAnyPress();
		
		*/
		
		
		LCD.clear();
		LCD.drawString("Program ended!", 0, 1);
		Button.waitForAnyPress();
		
	}

}
