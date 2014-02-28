package de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic;

import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;

/**
 * Implementierung des KeywordMonoalphabeticCipher Interfaces.
 * @author Quoc Thong Huynh, Dennis Kuhn, Moritz Matthiesen, Erik Laurin Strelow
 * 
 */
public class KeywordMonoalphabeticCipherImpl extends MonoalphabeticCipherImpl implements KeywordMonoalphabeticCipher {

	/**
	 * Konstruktor fuer die KeywordMonoalphabeticCipher Implmentierung
	 * @param keyWord Das keyWord mit dem verschluesselt werden soll.
	 * @param a Das Klartext alphabet, aus diesem wird mit dem Schluesselwort die Verschluesselung erstellt.
	 */
	public KeywordMonoalphabeticCipherImpl(String keyWord, Alphabet a) {
		super(a, createDestAlphabet(keyWord, a));	
	}
	
	/**
	 * Erstellt aus einem Schluesselword und einem Klartext Alphabet das Verschluesselungs Alphabet.
	 * @param keyWord Das Schluesselwort
	 * @param a Das Klartext Alphabet
	 * @return Das Verschluesselungs Alphabet.
	 */
	private static Alphabet createDestAlphabet(String keyWord, Alphabet a) {
		char[] array = keyWord.toCharArray();
		
		ArrayList<Character> list = new ArrayList<Character>();
		
		for (char c : array) {
			list.add(c);
		}
		
		Utils util = new UtilsImpl();
		
		Alphabet reversedAlphabet = util.reverseAlphabet(a);
		
		for (Character c : reversedAlphabet) {
			
			if (!list.contains(c))
				list.add(c);
		}
		
		return new AlphabetImpl(list.subList(0, a.size()));
	}
	
}
