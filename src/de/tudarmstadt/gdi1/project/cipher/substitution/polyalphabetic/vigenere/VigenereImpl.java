package de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.vigenere;

import de.tudarmstadt.gdi1.project.utils.UtilsImpl;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.PolyalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.Vigenere;
import de.tudarmstadt.gdi1.project.utils.Utils;

public class VigenereImpl extends PolyalphabeticCipherImpl implements Vigenere {

	/**
	 * 
	 * @param key
	 * @param src
	 */
	public VigenereImpl(String key, Alphabet src) {
		super(src, createDestAlphabets(key, src));
	}
	
	/**
	 * 
	 * @param key
	 * @param src
	 * @return
	 */
	private static Alphabet[] createDestAlphabets(String key, Alphabet src) {
		
		if (key.length() == 0)
			throw new IllegalArgumentException("key.length() needs length > 0");
		
		Alphabet[] dests = new Alphabet[key.length()];
	
		Utils utils = new UtilsImpl();
		
		for (int i = 0; i < key.length(); i++) {
			//Erzeugen der Alphabete bei denen je der erste Buchstabe des n-ten Alphabets dem n-ten Buchstaben des keys übereinstimmt
			//indem das source Alphabet um x-Stellen verschoben wird (x: Index des n-ten Buchstaben des keys im source.Alphabet) 
			char c = key.charAt(i);
			int index = src.getIndex(c);
			
			dests[i] = utils.shiftAlphabet(src, index);
		}
		
		return dests;
	}
	
}