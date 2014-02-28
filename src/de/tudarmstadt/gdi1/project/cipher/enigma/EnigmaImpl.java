package src.de.tudarmstadt.gdi1.project.cipher.enigma;
import java.util.List;

import de.tudarmstadt.gdi1.project.cipher.enigma.Enigma;

/**
 * 
 * @author Quoc Thong Huynh, ï¿¼Dennis Kuhn, Moritz Matthiesen, ï¿¼Erik Laurin Strelow
 *
 */
public class EnigmaImpl implements Enigma{
	List<Rotor> rotors;
	PinBoard pinboard;
	ReverseRotor reverseRotor;
	/**
	 * Konstruktor der Enigma
	 * @param rotors Liste der Rotoren
	 * @param pinboard Das Steckbrett
	 * @param reverseRotor Der Umkehrrotor
	 */
	public EnigmaImpl (List<Rotor> rotors, PinBoard pinboard, ReverseRotor reverseRotor){
		this.rotors = rotors;
		this.pinboard = pinboard;
		this.reverseRotor = reverseRotor;
		
	}
	
	public String encrypt(String text){
		char[] chars = text.toCharArray();
		char[] transChars = new char[chars.length];
		String result = "";
		int i = 0;
		//Einzelnen Buchstaben durch Pinboard und durch alle Rotoren
		for(Character c: chars){
			transChars[i] = pinboard.translate(c);
			
			//Buchstaben durch alle Rotoren prÃ¼geln
			for(int j = 0; j < rotors.size(); j++){				
				transChars[i] = rotors.get(j).translate(transChars[i], true);				
			}
			
			//Buchstaben durch reverseRotor werfen
			transChars[i] = reverseRotor.translate(transChars[i]);
			
			//Buchstaben durch alle Rotoren in umgekehrter Reihenfolge werfen
			for(int k = rotors.size()-1; k >= 0; k--){				
				transChars[i] = rotors.get(k).translate(transChars[i], false);			
			}
			
			transChars[i]=pinboard.translate(transChars[i]);
			
				/**for (int j = 1; rotors.get(j-1).rotate() ; j++){
						rotors.get(j).rotate();					
				}**/
				
			RotationAllRotors(0);
			result += transChars[i];	
			i++;
			
		}
		for (int z = 0; z < rotors.size(); z++) rotors.get(z).reset();
		
		return result;
	}
	/**
	 * Hilfsprozedur RotationAllRotors: Rotiert stets den Rotor an der Stelle i, prüft ob rotate() true ausgibt (also eine komplette Umdrehung erfolgt ist) und ob es einen
	 * 									weiteren Rotor gibt, wenn ja wird RotationAllRotors rekursiv für den nächsten Rotor aufgerufen 
	 * @param i Der zu rotierende Rotor
	 */
	public void RotationAllRotors(int i){
	
		if((rotors.get(i).rotate())&&(i+1<rotors.size()))
			RotationAllRotors(i+1);
	}
	
	
	public String decrypt(String text){
		char[] chars = text.toCharArray();
		char[] transChars = new char[chars.length];
		String result = "";
		int i = 0;
		//Einzelne Buchstaben durch Pinboard und durch alle Rotoren
		for(Character c: chars){
			transChars[i] = pinboard.translate(c);
			
			//Buchstaben durch alle Rotoren prÃ¼geln
			for(int j = 0; j < rotors.size(); j++){				
				transChars[i] = rotors.get(j).translate(transChars[i], true);				
			}
			
			//Buchstaben durch reverseRotor werfen
			transChars[i] = reverseRotor.translate(transChars[i]);
			
			//Buchstaben durch alle Rotoren in umgekehrter Reihenfolge kloppen
			for(int k = rotors.size()-1; k >= 0; k--){				
				transChars[i] = rotors.get(k).translate(transChars[i], false);			
			}
			
			transChars[i]=pinboard.translate(transChars[i]);
			
				/**for (int j = 1; rotors.get(j-1).rotate() ; j++){
						rotors.get(j).rotate();					
				}**/
				
			rotors.get(0).rotate();
			result += transChars[i];	
			i++;
			
		}
		for (int z = 0; z < rotors.size(); z++) rotors.get(z).reset();
		
		return result;
	}
}
