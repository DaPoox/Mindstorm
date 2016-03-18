import lejos.nxt.LCD;
import lejos.nxt.ColorSensor.Color;

public class Couleur {
	private int minR;
	private int maxR;
	
	private int minG;
	private int maxG;
	
	private int minB;
	private int maxB;

	
	private boolean calibrated = false;
	
	//Constructeurs: 
	public Couleur(){
		this.minR = 5000;
		this.maxR = 0;
		this.minG = 5000;
		this.maxG = 0;
		this.minB = 5000;
		this.maxB = 0;
	}
 	public Couleur(int minR, int maxR, int minG, int maxG, int minB, int maxB){
		this.minR = minR;
		this.maxR = maxR;
		this.minG = minG;
		this.maxG = maxG;
		this.minB = minB;
		this.maxB = maxB;
	}

	
	//Setters: 
	public void setMinR(int minR) {
		this.minR = minR;
	}
	public void setMaxR(int maxR) {
		this.maxR = maxR;
	}
	
	public void setMinG(int minG) {
		this.minG = minG;
	}
	public void setMaxG(int maxG) {
		this.maxG = maxG;
	}
	
	public void setMinB(int minB) {
		this.minB = minB;
	}
	public void setMaxB(int maxB) {
		this.maxB = maxB;
	}
	public void setCalibrated(boolean bool){
		this.calibrated = bool;
	}
	//Getters: 
	public int getMinR(){

		return this.minR;
	}
	public int getMaxR(){
		return this.maxR;
	}

	public int getMinG(){
		return this.minG;
	}
	public int getMaxG(){
		return this.maxG;
	}

	public int getMinB(){
		return this.minB;
	}
	public int getMaxB(){
		return this.maxB;
	}
	public boolean isCalibrated(){
		return 	this.calibrated;
	}

	public boolean egale(Color color){
		LCD.clear();
		boolean rslt = ((color.getRed()>= this.minR && color.getRed()<= this.maxR) &&
				(color.getBlue()>= this.minB && color.getBlue()<= this.maxB)&&
				(color.getGreen()>= this.minG && color.getGreen()<= this.maxG));
		
		LCD.drawString(""+this.minR+" - "+color.getRed()+" - "+this.maxR, 0, 0);
		LCD.drawString(""+this.minG+" - "+color.getGreen()+" - "+this.maxG, 0, 1);
		LCD.drawString(""+this.minB+" - "+color.getBlue()+" - "+this.maxB, 0, 2);
		LCD.drawString(""+rslt,0, 3);
		
		return rslt;
	}
	public String toString(){
		if(this.calibrated)
			return ""+this.minR+" - "+this.maxR+"\n"+
				this.minG+" - "+this.maxG+"\n"+
				this.minB+" - "+this.maxB+"\n";
		else return "Calibrated:"+this.calibrated;
	}
	
	public String toStringFile(){
		if(this.calibrated)
			return ""+this.minR+"-"+this.maxR+"/"+
				this.minG+"-"+this.maxG+"/"+
				this.minB+"-"+this.maxB+"/";
		else return "Calibrated:"+this.calibrated;
	}

}
