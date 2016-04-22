
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
	
	boolean isTurning;
	double radius;
	
	int defaultSpeed = 240;
	int speedA = defaultSpeed;
	int speedC = defaultSpeed;
	
	static int acceleration = 0;
	
	//Couleur des codes: 
	private Couleur couleur1;
	private Couleur couleur2;
	private Code code;
	
	public Mouvement(Capteur cs, Couleur c1, Couleur c2){
		this.cs = cs;
		pilot = new DifferentialPilot(4.3f,12.7f, Motor.C, Motor.A);
		pilot.setTravelSpeed(10);

		navigator = new Navigator(pilot);
		pp = new OdometryPoseProvider(pilot);
		this.listPoints = new ArrayList<Point>();
		this.isTurning = false;
		this.radius =0;
		
		this.couleur1 = c1;
		this.couleur2 = c2;
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

		int nbPoint = 0;
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
			if((t2-t1)>500){
				location = pp.getPose().getLocation();
				//Afficher le type du deplacement
				//this.getMouvementType(location);
				nbPoint ++;
				if(nbPoint == 3){
					//On a 
					this.getMouvementType(listPoints.get(listPoints.size()-1));
					nbPoint = 0;
				}
				listPoints.add(location);
				t1 = System.currentTimeMillis();
			}
			
			//Vérifier si on est dans la partie "code": 
			if(this.couleur1.egale(cs.getColor()) || this.couleur2.egale(cs.getColor())){
				//On est dans la partie Code, lancer la méthode pour enregistrer le code:
				getCode();
				/*
				 * 
				 */
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
	public void calculerRayon(){
		this.radius = pilot.getMovement().getArcRadius();
/*		Point pt = new Point(0,0);
		pt.x = (float) (listPoints.get(listPoints.size()-1).getX() - this.radius*Math.cos(pilot.getMovement().getAngleTurned()));
		pt.y = (float) (listPoints.get(listPoints.size()-1).getY() - this.radius*Math.sin(pilot.getMovement().getAngleTurned()));
		 this.listPoints.add(pt);
		 */
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
		Point last = this.listPoints.get(this.listPoints.size()-2);
		double dif = 2;
		if(location.getY() < last.getY() + dif && location.getY() > last.getY() - dif){
			LCD.clear();
			LCD.drawString("Robot tout droit", 0, 1);
			this.radius = 0;
			this.isTurning = false;
		}else if(location.getY() > last.getY() + dif){
			LCD.clear();
			LCD.drawString("Virage gauche", 0, 1);
			calculerRayon();
			this.isTurning = true;
		}else{
			LCD.clear();
			LCD.drawString("Virage à droite",0,1);
			calculerRayon();
			this.isTurning = true;
		}
	}
	
	public void stop(){
		Motor.A.stop();
		Motor.C.stop();
	}
	public void follow(Path path){
		this.navigator.followPath(path);
	}
	
	public void getCode(){
		pilot.stop();
		Button.waitForAnyPress();
		Couleur c1, c2, c3;
	/* 1ere tranche du code: */
		if(this.couleur1.egale(cs.getColor())){
			c1 = this.couleur1;
		}else c1 = this.couleur2;
		while(c1.egale(cs.getColor())){
			pilot.forward();
		}
		pilot.stop();
		LCD.clear();
		LCD.drawString("1", 0, 0);
		Button.waitForAnyPress();
	/* On est sortie de la premiere tranche... 2eme tranche maintenant: */
		if(this.couleur1.egale(cs.getColor())){
			c2 = this.couleur1;
		}else c2 = this.couleur2;
		while(c2.egale(cs.getColor())){
			pilot.forward();
		}
		pilot.stop();
		LCD.clear();
		LCD.drawString("2", 0, 0);
		Button.waitForAnyPress();
	/* On est sortie de la 2eme tranche... 3eme tranche maintenant: */
		if(this.couleur1.egale(cs.getColor())){
			c3 = this.couleur1;
		}else c3 = this.couleur2;
		while(c3.egale(cs.getColor())){
			pilot.forward();
		}
		pilot.stop();
		LCD.clear();
		LCD.drawString("3", 0, 0);
		Button.waitForAnyPress();
		pilot.forward();
	/* On a terminer la lecture du code */
		this.code = new Code(c1, c2, c3);
		pilot.stop();
		this.code.afficherCode();
		Button.waitForAnyPress();
		pilot.forward();
	}
	/* Méthode temporaire, juste pour tester la partie Code: */
	public void avancerTtDroit(){
		//Vérifier si on est dans la partie "code": 
		while(!(this.couleur1.egale(cs.getColor()) || this.couleur1.egale(cs.getColor()))){
			//On PAS est dans la partie Code, Avancer:
			pilot.forward();
		}
		getCode();
		Delay.msDelay(3000);
		pilot.stop();
	}
}


