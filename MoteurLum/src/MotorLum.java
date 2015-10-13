import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class MotorLum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LightSensor ls = new LightSensor(SensorPort.S1);
		System.out.println("Moteur tourne \nselon la\nluminosite: ");
		ls.setFloodlight(false);
		Motor.A.forward();
		while(Button.ENTER.isUp()){
			Motor.A.setSpeed(ls.getNormalizedLightValue()/2);
			LCD.drawString("Vitesse: "+Motor.A.getSpeed(), 2, 5);
			LCD.drawString("Luminosit: "+ls.getNormalizedLightValue(), 2, 6);
		}
		Button.waitForAnyPress();
		
	}

}
