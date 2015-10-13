import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class MainLum {
	public static void main(String args[]){
		LightSensor ls = new LightSensor(SensorPort.S1);
		ls.setFloodlight(false);
		System.out.println("Lecture de \nla luminosite: \n");
		while(Button.ENTER.isUp()){
			LCD.drawInt(ls.getNormalizedLightValue(), 2, 4);
		}
		LCD.clear();
		System.out.println("Program ended");
	}
}
