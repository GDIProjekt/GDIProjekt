package de.tudarmstadt.gdi1.project.cipher.enigma;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;

import java.util.ArrayList;
import java.util.List;

/**
 * Implmentierung des Rotor Interfaces.
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class RotorImpl implements Rotor {
	
	/**
	 * Das Eingangsalphabet
	 */
	Alphabet entryAlph;
	
	/**
	 * Das Ausgangsalphabet
	 */
	Alphabet exitAlph;
	
	/**
	 * Das Urspruengliche Alphabet.
	 */
	Alphabet initialExitAlph;
	
	/**
	 * Die Verschiebungsarray, zum uebersetzten vom Eingangs- zum Ausgangsalphabet.
	 */
	int[] entryToExit;
	
	/**
	 * Das urspruengliche Verschiebungsarray, bei 0 ausgefuehrten Rotationen.
	 */
	int[] initialEntryToExit;
	
	/**
	 * Urspruengliche Position des Rotors.
	 */
	int startPosition;
	
	/**
	 * Die Momentane Position des Rotors
	 */
	int position;
	
	
	/**
	 * Konstruktor der RotorImpl
	 * @param entryAlph Das Eingangsalphabet
	 * @param exitAlph Das Ausgangsalphabet
	 * @param startPosition Die Startposition des Rotors
	 */
	public RotorImpl (Alphabet entryAlph, Alphabet exitAlph, int startPosition){
		
		if (entryAlph.size() != exitAlph.size()) 
			throw new IllegalArgumentException("Error: entry and exit alphabet have to be of same size.");
		
		this.entryAlph = entryAlph;
		this.exitAlph = exitAlph;
		
		entryToExit = new int[entryAlph.size()];
	
		
		int i = 0;
		//Bestimmt die Verschiebungswerte der einzelnen Buchstaben des entryAlph zum exitAlph
		for (i = 0; i<entryAlph.size(); i++){
			entryToExit[i] = (entryAlph.getIndex(exitAlph.getChar(i))-i)%entryAlph.size();
		}
		//Urspruengliche Verschiebungswerte (also fuer entryAlph -> exitAlph fuer 0 Rotationen
		initialEntryToExit = entryToExit.clone();
		
		//Rotiert so oft wie die uebergebene startPosition es verlangt
		for(int j = 0; j < startPosition%entryAlph.size(); j++){
			rotate();	
		}
		initialExitAlph = this.exitAlph;		
		
		this.startPosition = (startPosition + entryAlph.size())%entryAlph.size(); /////!
		position = this.startPosition;
		
	}
	

	@Override
	public char translate(char c, boolean forward){
		char result;
		if (forward){
			//Pruefen ob zu uebersetzender Buchstabe im entryAlph vorhanden ist 
			if (!entryAlph.contains(c)){
				throw new IllegalArgumentException("Error: Entry alphabet doesn't contain character which is to translate.");
			}
			else{
				result = exitAlph.getChar(entryAlph.getIndex(c));
			}
		}
		else{
			//Pruefen ob zu uebersetzender Buchstabe im exitAlph vorhanden ist
			if (!exitAlph.contains(c)){
				throw new IllegalArgumentException("Error: Exit alphabet doesn't contain character which is to translate.");
			}
			else{
				result = entryAlph.getChar(exitAlph.getIndex(c));
			}
		}
		return result;
	}

	@Override
	public boolean rotate(){
		boolean result;
		position++;
		
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
			//Index des uebersetzten Buchstabens ergibt sich aus dem Index des Buchstabens im entryAlph + dem Verschiebungswert
			// + der Groesse des entryAlph (falls Index negativ sein wuerde) modulo der Groesse des etryAlph
			chars[j] = entryAlph.getChar((entryAlph.getIndex(c)+entryToExit[j]+entryAlph.size())%entryAlph.size());
			j++;
		}
		for (Character c: chars){
			temp.add(c);
		}
		
		exitAlph = new AlphabetImpl(temp);
		
		//Wenn die momentane Position modulo Alphabetsgr��e = startPosition ist (d.h. eine volle Umdrehung stattfand) wird die
		//Position auf die Startposition zur�ckgesetzt und true zur�ckgegeben
		if (position%entryAlph.size() == startPosition){
			position = startPosition;
			result = true;
		}
		//ansonsten wird die Position um 1 hochgez�hlt und false ausgegeben
		else{
			result = false;
		}
		return result;
	}

	@Override
	public void reset(){
		entryToExit = initialEntryToExit;
		int eTeListTemp = entryToExit[0];
		
		//Verschiebeliste wieder auf Startposition bringen (Verschiebeliste gilt bis dahin nur f�r Index = 0)  
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
		position = startPosition;
	}
}
