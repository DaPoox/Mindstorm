
import java.util.ArrayList;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.util.Delay;

public class Mouvement {
	
	Capteur cs;
	
	DifferentialPilot pilot;
	Navigator navigator;
	PoseProvider pp;
	
	double lumBord;
	double range;
	
	int defaultSpeed = 240;
	int speedA = defaultSpeed;
	int speedC = defaultSpeed;
	
	int acceleration = 0;
	
	public Mouvement(Capteur cs){
		this.cs = cs;
		pilot = new DifferentialPilot(4.3f,12.7f, Motor.C, Motor.A);
		pilot.setTravelSpeed(10);

		navigator = new Navigator(pilot);
		pp = new OdometryPoseProvider(pilot);
	}
	
	public void sortirDebut(Couleur couleurDebut, Couleur couleurLigne){
		Motor.A.setSpeed(250);
		Motor.C.setSpeed(250);
		while(!couleurLigne.egale(cs.getColor())){
			Motor.A.forward();
			Motor.C.forward();
		}

		Delay.msDelay(100);
		Motor.A.stop();
		Motor.C.stop();
	}
	
	public void getBord(){
		double lumMin=900;
		double lumMax = -1;
		
		Motor.A.setSpeed(defaultSpeed/2);
		Motor.C.setSpeed(defaultSpeed/2);
		
		long t= System.currentTimeMillis();
		//while(Motor.A.getTachoCount() < 200){
		while(System.currentTimeMillis()<t+1000){	
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
	public Path avancer(Couleur couleurLigne, Couleur couleurFin){	

		double lumCouleurLu;
		Point location;
		long t1, t2;
		
		pilot.forward();
		ArrayList<Point> listPoints = new ArrayList<Point>();
	
		LCD.clear();

		t1 = System.currentTimeMillis();
		while(!couleurFin.egale(cs.getColor())){
			//Récupérer la luminance de la couleur captée
			lumCouleurLu = cs.getLight();
			acceleration =  (int)((lumCouleurLu - this.lumBord));
		
			if(lumCouleurLu>lumBord){
				acceleration *= 2;	
			}
			
			pilot.steer(acceleration);
			t2 = System.currentTimeMillis();
			if((t2-t1)>1000){
				location = pp.getPose().getLocation();
				listPoints.add(location);
				LCD.clear();
				t1 = System.currentTimeMillis();
			}
			
		}
		
		//On a terminé la ligne, on sauvgarde les points dans Path: 
		pilot.stop();
		
		Path path = new Path();
		for(Point point : listPoints){
			LCD.clear();
			System.out.print("X= "+point.getX());
			System.out.print("\nY= "+point.getY());
			Waypoint waypoint = new Waypoint(point.getX(), point.getY());
			path.add(waypoint);
		}
		LCD.clear();
		return path;
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
	
	
	public void follow(Path path){
		this.navigator.followPath(path);
	}
}


