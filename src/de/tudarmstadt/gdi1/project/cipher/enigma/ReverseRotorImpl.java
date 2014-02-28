package de.tudarmstadt.gdi1.project.cipher.enigma;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;

/**
 * 
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class ReverseRotorImpl implements ReverseRotor{
	Alphabet entryAlph;
	Alphabet exitAlph;
	
	/**
	 * Konstruktor des ReverseRotorImpl
	 * @param entryAlph Das Eingangsalphabet
	 * @param exitAlph Das Ausgangsalphabet
	 */
	public ReverseRotorImpl (Alphabet entryAlph, Alphabet exitAlph){
		for (Character c: entryAlph.asCharArray()){
			if (entryAlph.getIndex(c)==exitAlph.getIndex(c)){
				throw new IllegalArgumentException("Error: Alphabet can never be symmetrical.");
			}
		}
		this.entryAlph = entryAlph;
		this.exitAlph = exitAlph;
	}
	
	@Override
	public char translate(char c){
		 return entryAlph.getChar(exitAlph.getIndex(c));
	}
}
