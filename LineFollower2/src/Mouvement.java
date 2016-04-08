
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
	
	static DifferentialPilot pilot;
	Navigator navigator;
	PoseProvider pp;

	ArrayList<Point> listPoints;
	
	double lumBord;
	double range;
	
	int defaultSpeed = 240;
	int speedA = defaultSpeed;
	int speedC = defaultSpeed;
	
	static int acceleration = 0;
	
	public Mouvement(Capteur cs){
		this.cs = cs;
		pilot = new DifferentialPilot(4.3f,12.7f, Motor.C, Motor.A);
		pilot.setTravelSpeed(10);

		navigator = new Navigator(pilot);
		pp = new OdometryPoseProvider(pilot);
		this.listPoints = new ArrayList<Point>();
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
		
		while(cs.cs.getLightValue() > lumBord){
			Motor.C.forward();
			Motor.A.backward();
		}
		Delay.msDelay(150);
		Motor.A.stop();
		Motor.C.stop();
		
		this.range = lumMax - lumMin;
	}

	public Path avancer(Couleur couleurLigne, Couleur couleurFin){	
		double lumCouleurLu;
		Point location;
		long t1, t2;

		location = pp.getPose().getLocation();
		listPoints.add(location);
		
		pilot.forward();
	
		LCD.clear();

		t1 = System.currentTimeMillis();
		while(!couleurFin.egale(cs.getColor())){
			//Récupérer la luminance de la couleur captée
			lumCouleurLu = cs.getLight();
			acceleration =  (int)((lumCouleurLu - this.lumBord));
		
			if(lumCouleurLu>lumBord){
				acceleration *= 2;	
			}		

			cs.setSpeed(acceleration);
			
			//Get la position chaque seconde
			t2 = System.currentTimeMillis();
			if((t2-t1)>2000){
				location = pp.getPose().getLocation();
				//Afficher le type du deplacement
				this.getMouvementType(location);
				listPoints.add(location);
				t1 = System.currentTimeMillis();
			}
		}
		//On a terminé la ligne, on sauvgarde les points dans Path: 
		cs.setStop(true);
		pilot.stop();
		
		Path path = new Path();
		for(Point point : listPoints){
			LCD.clear();
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
	
	public void getMouvementType(Point location){
		Point last = this.listPoints.get(this.listPoints.size()-1);
		double dif = 2;
		if(location.getY() < last.getY() + dif && location.getY() > last.getY() - dif){
			LCD.clear();
			LCD.drawString("Robot tout droit", 0, 1);
		}else if(location.getY() > last.getY() + dif){
			LCD.clear();
			LCD.drawString("Virage gauche", 0, 1);;
		}else{
			LCD.clear();
			LCD.drawString("Virage à droite",0,1);
		}
		LCD.drawString("X:"+location.getX(), 0,2);
		LCD.drawString("Y:"+location.getY(), 0,3);
		pilot.stop();
		Button.waitForAnyPress();
		pilot.forward();
	}
	
	public void stop(){
		Motor.A.stop();
		Motor.C.stop();
	}
	
	
	public void follow(Path path){
		this.navigator.followPath(path);
	}
	

}


