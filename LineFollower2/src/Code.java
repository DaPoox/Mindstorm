import lejos.nxt.LCD;
import lejos.nxt.ColorSensor.Color;

public class Code {
	private Color c1;
	private Color c3;
	private Color c2;
	
	public Code(Color c1, Color c2, Color c3){
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
	
	public Color getC1() {
		return c1;
	}

	public void setC1(Color c1) {
		this.c1 = c1;
	}

	public Color getC2() {
		return c2;
	}

	public void setC2(Color c2) {
		this.c2 = c2;
	}

	public Color getC3() {
		return c3;
	}

	public void setC3(Color c3) {
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
