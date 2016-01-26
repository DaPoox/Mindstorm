import lejos.nxt.Motor;
import lejos.util.Delay;

public class Mouvement {
	
	private int vitesseDefault;
	Capteur cs;
	public Mouvement(Capteur cs ){
		this.cs = cs;
	}
	
	public void avancer(Couleur couleurLigne){	
		while(couleurLigne.egale(cs.getColor())){
			//Tourner à droite
			Motor.A.setSpeed(110);//110
			Motor.C.setSpeed(250);//250
			
			Motor.A.forward();
			Motor.C.forward();
			//mouvement.trounerDroite(180+50);
		}
			//Tourner à gauche:
			Motor.C.setSpeed(110);//110
			Motor.A.setSpeed(250);//250
			
			Motor.A.forward();
			Motor.C.forward();
	}
	
	public void demiTour(Couleur couleurLigne){
		Motor.A.forward();
		Motor.C.backward();
		Delay.msDelay(200);
		while(!couleurLigne.egale(cs.getColor())){
			Motor.A.forward();
			Motor.C.backward();	
		}
		Motor.A.stop();
		Motor.C.stop();
	}
	public void stop(){
		Motor.A.stop();
		Motor.C.stop();
	}
	public void trounerGauche(int vitesse){
		//Motor.A.setSpeed(this.vitesseCourante + vitesse);
		//Motor.C.setSpeed(this.vitesseCourante - vitesse);
		
		Motor.A.setSpeed(vitesse);
		Motor.C.setSpeed(this.vitesseDefault);
		
		Motor.A.forward();
		Motor.C.forward();
	}
	
	public void trounerDroite(int vitesse){
		//Motor.A.setSpeed(this.vitesseCourante + vitesse);
		//Motor.C.setSpeed(this.vitesseCourante - vitesse);
		
		Motor.C.setSpeed(vitesse);
		Motor.A.setSpeed(this.vitesseDefault);
		
		Motor.A.forward();
		Motor.C.forward();
	}
	
	public void arreter(){
		Motor.A.stop();
		Motor.C.stop();
	}
	
	public void reculer(int vitesse){
		Motor.A.backward();
		Motor.C.backward();
	}
}


