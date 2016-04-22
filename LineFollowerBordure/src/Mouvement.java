import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.Navigator;
import lejos.util.Delay;

public class Mouvement {
	
	private int vitesseDefault;
	Capteur cs;
	
	double lumBord;
	double range;
	
	int defaultSpeed = 240;
	int speedA = defaultSpeed;
	int speedC = defaultSpeed;
	
	int limit = 500;
	int acceleration = 0;
	
	public Mouvement(Capteur cs){
		this.cs = cs;
	}
	
	public void sortirDebut(Couleur couleurDebut, Couleur couleurLigne){
		Motor.A.setSpeed(350);
		Motor.C.setSpeed(350);
		while(!couleurLigne.egale(cs.getColor())){
			Motor.A.forward();
			Motor.C.forward();
		}

		Delay.msDelay(200);
		Motor.A.stop();
		Motor.C.stop();
	}
	public void getBord(){
		double lumMin=200;
		double lumMax = -1;
		
		Motor.A.setSpeed(defaultSpeed/2);
		Motor.C.setSpeed(defaultSpeed/2);
		
		long t= System.currentTimeMillis();
		//while(Motor.A.getTachoCount() < 200){
		while(System.currentTimeMillis()<t+800){	
			Motor.A.forward();
			Motor.C.backward();
			if(cs.cs.getLightValue()<lumMin){
				lumMin = cs.cs.getLightValue();
			}
			if(cs.cs.getLightValue()>lumMax){
				lumMax = cs.cs.getLightValue();
			}
		}
		Motor.A.stop();
		Motor.C.stop();
	
		this.lumBord = (lumMax+lumMin)/2;
		//Button.waitForAnyPress();
		while(cs.cs.getLightValue() > lumBord){
			Motor.C.forward();
			Motor.A.backward();
		}
		Delay.msDelay(150);
		Motor.A.stop();
		Motor.C.stop();
		LCD.clear();
		LCD.drawString("Ready ..."+lumBord, 0, 0);
	//	Button.waitForAnyPress();
		LCD.clear();
		this.range = lumMax - lumMin;
	}
	public void avancer(Couleur couleurLigne, Couleur couleurFin){	

		double lumCouleurLu;
	
		Motor.A.setSpeed(speedA/2);
		Motor.C.setSpeed(speedC/2);
	
		Motor.A.forward();
		Motor.C.forward();
		LCD.clear();
		while(!couleurFin.egale(cs.getColor())){
		
			lumCouleurLu = cs.cs.getLightValue();//Récupérer la luminance de la couleur captée

			acceleration =  (int) (300 * (lumCouleurLu - this.lumBord)/this.range);
			
			this.speedA = defaultSpeed + acceleration;
			this.speedC = defaultSpeed - acceleration;

			Motor.A.setSpeed(speedC);
			Motor.C.setSpeed(speedA);
			
			Motor.A.forward();
			Motor.C.forward();
		}
	
	}
	
	public void demiTour(Couleur couleurLigne){
		Motor.A.setSpeed(defaultSpeed/2);
		Motor.C.setSpeed(defaultSpeed/2);
		
		Motor.C.forward();
		Motor.A.backward();
		
		Delay.msDelay(200);
		
		while(!couleurLigne.egale(cs.getColor())){
			Motor.C.forward();
			Motor.A.backward();	
		}
		Motor.A.stop();
		Motor.C.stop();
	}

	public void stop(){
		Motor.A.stop();
		Motor.C.stop();
	}
}


