import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.util.Delay;

public class Fichier {
	 	File data = new File("couleur.dat");
	    int i;
	    
	    public Fichier(){
	    	this.data = new File("couleur.dat");
	    	if (!data.exists()) {
				try {
					data.createNewFile();
					System.out.println("Fichier Cree");
					Delay.msDelay(500);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Can't create file");
					e.printStackTrace();
				}
			}
	    }
	    public Couleur read(int ligne){
	    	try {
	    		InputStream in = new FileInputStream(data);
	    		//DataInputStream din = new DataInputStream(in);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            for(int i=0; i<ligne; i++){
	            	reader.readLine();
	            }
	            String line = reader.readLine();
	    		in.close();
		    	Couleur couleur = this.StringToCouleur(line);	 		    	
	            return couleur;
	    	} catch (IOException ioe) {
	    		System.err.println("Read Exception");
	    		return null;
	    	}
	    }

	    public void write(String contenu){
	    	FileOutputStream out = null; // declare outside the try block	    	    
	        try {
	          out = new FileOutputStream(data);
	        } catch(IOException e) {
	        	System.err.println("Failed to create output stream");
	        	Button.waitForAnyPress();
	        	System.exit(1);
	   	    }
	   	    DataOutputStream dataOut = new DataOutputStream(out);
	   	    
	   	    try { // write
	   	    	// get the content in bytes
				byte[] contentInBytes = (contenu).getBytes();
				out.write(contentInBytes);
				
				out.close(); // flush the buffer and write the file
				System.out.println("Done");

	        } catch (IOException e) {
	          System.err.println("Failed to write to output stream");
	        }
	   	    Sound.beep();
	   	    Button.waitForAnyPress();
	    }
	    
	    public Couleur StringToCouleur(String chaine){
	    	Couleur clr = new Couleur();
	    	if(chaine.startsWith("Calib")){
	    		return clr;
	    	}
	    	// structure de la chaine: 
	    	//minR-maxR/minG-maxG/minB-maxB
	    	int i=0;
	    // ---- R: 	
	    	String min = "";
	    	String max = "";
	    	while(chaine.charAt(i) != '-'){
	    		min += chaine.charAt(i);
	    		i++;
	    	}
	    	i++;
	    	while(chaine.charAt(i) != '/'){
	    		max += chaine.charAt(i);
	    		i++;
	    	}
	    	clr.setMinR(Integer.parseInt(min));
	    	clr.setMaxR(Integer.parseInt(max));
	    //--- G:
	    	i++;
	    	min = "";
	    	max = "";
	    	while(chaine.charAt(i) != '-'){
	    		min += chaine.charAt(i);
	    		i++;
	    	}
	    	i++;
	    	while(chaine.charAt(i) != '/'){
	    		max += chaine.charAt(i);
	    		i ++;
	    	}
	    	clr.setMinG(Integer.parseInt(min));
	    	clr.setMaxG(Integer.parseInt(max));
	    	
	    // ---- B: 
	    	i++;
	    	min = "";
	    	max = "";
	    	while(chaine.charAt(i) != '-'){
	    		min += chaine.charAt(i);
	    		i ++;
	    	}
	    	i++;
	    	while(chaine.charAt(i) != '/'){
	    		max += chaine.charAt(i);
	    		i ++;
	    	}

	    	clr.setMinB(Integer.parseInt(min));
	    	clr.setMaxB(Integer.parseInt(max));
	    	clr.setCalibrated(true);
	    	return clr;
	    }
}
