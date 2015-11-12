
public class Couleur {
	private int minR;
	private int maxR;
	
	private int minG;
	private int maxG;
	
	private int minB;
	private int maxB;
	
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
	
	public String toString(){
		return ""+this.minR+" - "+this.maxR+"\n"+
				this.minG+" - "+this.maxG+"\n"+
				this.minB+" - "+this.maxB+"\n";
	}

}
