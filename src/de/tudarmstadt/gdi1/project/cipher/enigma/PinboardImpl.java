package de.tudarmstadt.gdi1.project.cipher.enigma;

import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

/**
 * 
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class PinboardImpl implements PinBoard{
	Alphabet source;
	Alphabet dest;
	
	/**
	 * Konstruktor des PinboardImpl
	 * @param source Das Eingangsalphabet
	 * @param dest Das Ausgangsalphabet
	 */
	public PinboardImpl(Alphabet source, Alphabet dest){
		//prüfen ob Alphabet bzw. die Buchstaben symmetrisch zueinander sind, wenn nicht wird eine Exception geworfen
		for (Character c: source.asCharArray()){
			if (source.getChar(dest.getIndex(c)) != dest.getChar(source.getIndex(c)))
				throw new IllegalArgumentException("Error: Given alphabets are not compatible.");
		}
		this.source = source;
		this.dest = dest;
	}

	@Override
	public char translate(char c){
		return source.getChar(dest.getIndex(c));
	}

}
