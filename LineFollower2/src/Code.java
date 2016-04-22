import lejos.nxt.LCD;
import lejos.nxt.ColorSensor.Color;

public class Code {
	private Couleur c1;
	private Couleur c3;
	private Couleur c2;
	
	public Code(Couleur c1, Couleur c2, Couleur c3){
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
	
	public Couleur getC1() {
		return c1;
	}

	public void setC1(Couleur c1) {
		this.c1 = c1;
	}

	public Couleur getC2() {
		return c2;
	}

	public void setC2(Couleur c2) {
		this.c2 = c2;
	}

	public Couleur getC3() {
		return c3;
	}

	public void setC3(Couleur c3) {
		this.c3 = c3;
	}

	public void afficherCode(){
		LCD.clear();
		LCD.drawString("Code: ", 0, 0);
		LCD.drawString(c1.toString(), 1, 0);
		LCD.drawString(c2.toString(), 2,0);
		LCD.drawString(c3.toString(), 3, 0);
	}
}
