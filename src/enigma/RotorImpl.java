package de.tudarmstadt.gdi1.project.cipher.enigma;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;
import java.util.ArrayList;
import java.util.List;

public class RotorImpl implements Rotor {
	Alphabet entryAlph;
	Alphabet exitAlph;
	Alphabet initialExitAlph;
	
	int[] entryToExit;
	int[] initialEntryToExit;
	int startPosition;
	int Position;
	/**
	 * Constructor of class RotorImpl
	 * @param entryAlph
	 * @param exitAlph
	 * @param startPosition
	 */
	public RotorImpl (Alphabet entryAlph, Alphabet exitAlph, int startPosition){
		
		this.entryAlph = entryAlph;
		this.exitAlph = exitAlph;
		
		entryToExit = new int[entryAlph.size()];
	
		
		int i = 0;
		//Bestimmt die Verschiebungswerte der einzelnen Buchstaben des entryAlph zum exitAlph
		for (i = 0; i<entryAlph.size(); i++){
			entryToExit[i] = (entryAlph.getIndex(exitAlph.getChar(i))-i)%entryAlph.size();
		}
		
		initialEntryToExit = entryToExit.clone();
		
		//Rotiert so oft wie die übergebene startPosition es verlangt
		for(int j = 0; j < startPosition%entryAlph.size(); j++){
			rotate();	
		}
		initialExitAlph = this.exitAlph;		
		
		
		this.startPosition = startPosition;
		Position = startPosition;
		
	}
	
	/**
	 * passes a given character through the rotor of an enigma.
	 * 
	 * @param c
	 *            the character that should be passed through the rotor
	 * @param forward
	 *            true if we pass the character forward through the rotor.
	 *            Should be true before the ReverseRotor has been passed and
	 *            false afterwards.
	 * @return the translated character.
	 */
	public char translate(char c, boolean forward){
		char result;
		if (forward){
			if (!entryAlph.contains(c)){
				throw new IllegalArgumentException("Error: Entry alphabet doesn't contain character which is to translate.");
			}
			else{
				result = exitAlph.getChar(entryAlph.getIndex(c));
			}
		}
		else{
			if (!exitAlph.contains(c)){
				throw new IllegalArgumentException("Error: Exit alphabet doesn't contain character which is to translate.");
			}
			else{
				result = entryAlph.getChar(exitAlph.getIndex(c));
			}
		}
		return result;
	}

	/**
	 * rotates the rotor to its next position.
	 * 
	 * @return true if the rotor reached its initial position (i.e., the next
	 *         rotor has to be rotated), otherwise false
	 */
	public boolean rotate(){
		boolean result;
		
		int eTeListTemp = entryToExit[0];
		
		
		//Verschieben der Alphabetsverschiebungswerte
		for (int i=0; i<entryToExit.length;i++){
			if (i==entryToExit.length-1){
				
				entryToExit[i] = eTeListTemp;			
			}
			else{
				entryToExit[i]=entryToExit[i+1];
			}
		}
		
		char[] alphChars = entryAlph.asCharArray();
		char[] chars = new char[entryAlph.size()];
		List <Character> temp = new ArrayList <Character>();
		
		int j = 0;
		//Verschieben des Zielalphabets
		for (Character c : alphChars){
			chars[j] = entryAlph.getChar((entryAlph.getIndex(c)+entryToExit[j]+entryAlph.size())%entryAlph.size());
			j++;
		}
		for (Character c: chars){
			temp.add(c);
		}
		exitAlph = new AlphabetImpl(temp);
		
		if (Position == startPosition){
			Position = startPosition;
			result = true;
		}
		else{
			Position = (Position+1)%entryAlph.size();
			result = false;
		}
		return result;
	}

	/**
	 * resets the rotor to its default position
	 */
	public void reset(){
		entryToExit = initialEntryToExit;
		int eTeListTemp = entryToExit[0];
		
		//Verschiebeliste wieder auf Startposition bringen (Verschiebeliste gilt bis dahin nur für Index = 0)  
		for (int j=0; j < startPosition; j++){
			
			for (int i=0; i<entryToExit.length;i++){
				if (i==entryToExit.length-1){			
					entryToExit[i] = eTeListTemp;			
				}
				else{
					entryToExit[i]=entryToExit[i+1];
				}
			}
		}
		exitAlph = initialExitAlph;
		Position = startPosition;
	}
}
