import lejos.nxt.Motor;

public class Mouvement {
	
	private int vitesseDefault;
	
	public Mouvement(int vitsDefault){
		this.vitesseDefault = vitsDefault;
	}
	
	public void avancer(){
		Motor.A.setSpeed(this.vitesseDefault);
		Motor.C.setSpeed(this.vitesseDefault);
		
		Motor.A.forward();
		Motor.C.forward();
	}
	
	public void avancer(int vitesse){
		
		Motor.A.setSpeed(vitesse);
		Motor.C.setSpeed(vitesse);
		
		Motor.A.forward();
		Motor.C.forward();
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


