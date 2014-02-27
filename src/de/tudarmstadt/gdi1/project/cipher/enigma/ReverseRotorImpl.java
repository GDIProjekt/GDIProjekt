package de.tudarmstadt.gdi1.project.cipher.enigma;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;


public class ReverseRotorImpl implements ReverseRotor{
	Alphabet entryAlph;
	Alphabet exitAlph;
	
	/**
	 * Constructor for class ReverseRotorImpl
	 * @param entryAlph
	 * @param exitAlph
	 */
	public ReverseRotorImpl (Alphabet entryAlph, Alphabet exitAlph){
		
		for (Character c: entryAlph.asCharArray()){
			//Prüfen ob irgendein Buchstabe vom entryAlph reflexiv auf exitAlph abgebildet wird, wenn ja wird ein Fehler ausgeworfen
			if (entryAlph.getIndex(c)==exitAlph.getIndex(c)){
				throw new IllegalArgumentException("Error: Alphabet can never be symmetrical.");
			}
		}
		this.entryAlph = entryAlph;
		this.exitAlph = exitAlph;
	}
	/**
	 * passes the given character through the ReverseRotor of an enigma.
	 * 
	 * @param c
	 *            the character that should be encrypted
	 * @return the encrypted character
	 */
	public char translate(char c){
		 return entryAlph.getChar(exitAlph.getIndex(c));
	}
}
