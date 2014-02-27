package de.tudarmstadt.gdi1.project.cipher.enigma;

import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

public class PinboardImpl implements PinBoard{
	Alphabet source;
	Alphabet dest;
	public PinboardImpl(Alphabet source, Alphabet dest){
		//pr��fen ob Alphabet bzw. die Buchstaben symmetrisch zueinander sind, wenn nicht wird eine Exception geworfen
		for (Character c: source.asCharArray()){
			if (source.getChar(dest.getIndex(c)) != dest.getChar(source.getIndex(c)))
				throw new IllegalArgumentException("Error: Given alphabets are not compatible.");
		}
		this.source = source;
		this.dest = dest;
	}
	
	/**
	 * passes the given character through the pinboard.
	 * 
	 * @param c
	 *            the character that should be passed through the pinboard.
	 * @return The translated Character at the end of the pinboard
	 */
	public char translate(char c){
		return source.getChar(dest.getIndex(c));
	}

}
