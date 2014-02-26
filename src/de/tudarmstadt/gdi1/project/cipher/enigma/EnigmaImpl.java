package src.de.tudarmstadt.gdi1.project.cipher.enigma;
import java.util.List;

import de.tudarmstadt.gdi1.project.cipher.enigma.Enigma;


public class EnigmaImpl implements Enigma{
	List<Rotor> rotors;
	PinBoard pinboard;
	ReverseRotor reverseRotor;
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
			
			//Buchstaben durch alle Rotoren prügeln
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
	
	public void RotationAllRotors(int i){
	
		if((rotors.get(i).rotate())&&(i<rotors.size()))
			RotationAllRotors(i++);
	}
	
	
	public String decrypt(String text){
		char[] chars = text.toCharArray();
		char[] transChars = new char[chars.length];
		String result = "";
		int i = 0;
		//Einzelne Buchstaben durch Pinboard und durch alle Rotoren
		for(Character c: chars){
			transChars[i] = pinboard.translate(c);
			
			//Buchstaben durch alle Rotoren prügeln
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