package de.tudarmstadt.gdi1.project.cipher.enigma;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Quoc Thong Huynh, ï¿¼Dennis Kuhn, Moritz Matthiesen, ï¿¼Erik Laurin Strelow
 *
 */
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
	 * @param entryAlph Das Eingangsalphabet
	 * @param exitAlph Das Ausgangsalphabet
	 * @param startPosition Die Startposition des Rotors
	 */
	public RotorImpl (Alphabet entryAlph, Alphabet exitAlph, int startPosition){
		
		if (entryAlph.size()!=exitAlph.size()) 
			throw new IllegalArgumentException("Error: entry and exit alphabet have to be of same size.");
		
		this.entryAlph = entryAlph;
		this.exitAlph = exitAlph;
		
		entryToExit = new int[entryAlph.size()];
	
		
		int i = 0;
		//Bestimmt die Verschiebungswerte der einzelnen Buchstaben des entryAlph zum exitAlph
		for (i = 0; i<entryAlph.size(); i++){
			entryToExit[i] = (entryAlph.getIndex(exitAlph.getChar(i))-i)%entryAlph.size();
		}
		//Ursprüngliche Verschiebungswerte (also für entryAlph -> exitAlph für 0 Rotationen
		initialEntryToExit = entryToExit.clone();
		
		//Rotiert so oft wie die übergebene startPosition es verlangt
		for(int j = 0; j < startPosition%entryAlph.size(); j++){
			rotate();	
		}
		initialExitAlph = this.exitAlph;		
		
		this.startPosition = startPosition%entryAlph.size();
		Position = this.startPosition;
		
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
			//Prüfen ob zu übersetzender Buchstabe im entryAlph vorhanden ist 
			if (!entryAlph.contains(c)){
				throw new IllegalArgumentException("Error: Entry alphabet doesn't contain character which is to translate.");
			}
			else{
				result = exitAlph.getChar(entryAlph.getIndex(c));
			}
		}
		else{
			//Prüfen ob zu übersetzender Buchstabe im exitAlph vorhanden ist
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
		Position++;
		
		int eTeListTemp = entryToExit[0];		
		
		//Verschieben der Alphabetsverschiebungswerte
		for (int i=0; i<entryToExit.length;i++){
			if (i==entryToExit.length-1){
				//alle Werte um 1 nach vorne verschieben
				entryToExit[i] = eTeListTemp;			
			}
			else{
				//ersten Wert an letzte Stelle setzen
				entryToExit[i]=entryToExit[i+1];
			}
		}
		
		char[] alphChars = entryAlph.asCharArray();
		char[] chars = new char[entryAlph.size()];
		List <Character> temp = new ArrayList <Character>();
		
		int j = 0;
		//Verschieben des Zielalphabets
		for (Character c : alphChars){
			//Index des übersetzten Buchstabens ergibt sich aus dem Index des Buchstabens im entryAlph + dem Verschiebungswert
			// + der Größe des entryAlph (falls Index negativ sein würde) modulo der Größe des etryAlph
			chars[j] = entryAlph.getChar((entryAlph.getIndex(c)+entryToExit[j]+entryAlph.size())%entryAlph.size());
			j++;
		}
		for (Character c: chars){
			temp.add(c);
		}
		
		exitAlph = new AlphabetImpl(temp);
		
		//Wenn die momentane Position modulo Alphabetsgröße = startPosition ist (d.h. eine volle Umdrehung stattfand) wird die
		//Position auf die Startposition zurückgesetzt und true zurückgegeben
		if (Position%entryAlph.size() == startPosition){
			Position = startPosition;
			result = true;
		}
		//ansonsten wird die Position um 1 hochgezählt und false ausgegeben
		else{
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
