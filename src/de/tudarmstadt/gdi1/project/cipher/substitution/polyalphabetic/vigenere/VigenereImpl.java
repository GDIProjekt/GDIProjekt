package de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.vigenere;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.PolyalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.Vigenere;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;

/**
 * Implementierung des Vignere Interfaces
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class VigenereImpl extends PolyalphabeticCipherImpl implements Vigenere {

	/**
	 * Konstruktor für die Implementierung des Vignere Interfaces
	 * @param key Der Schluessel mit dem verschlüsselt werden soll.
	 * @param src Das Klartext Alphabet
	 */
	public VigenereImpl(String key, Alphabet src) {
		super(src, createDestAlphabets(key, src));
	}
	
	/**
	 * Erstellt ein Array von Alphabeten, für die Verschlüsselung, ausgehend von einen Schluessel und einem Klartext Alphabet.
	 * @param key Der Schluessel
	 * @param src Das Klartext Alphabet.
	 * @return Die Array von Alphabeten.
	 */
	private static Alphabet[] createDestAlphabets(String key, Alphabet src) {
		
		if (key.length() == 0)
			throw new IllegalArgumentException("key.length() needs length > 0");
		
		Alphabet[] dests = new Alphabet[key.length()];
	
		Utils utils = new UtilsImpl();
		
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int index = src.getIndex(c);
			
			dests[i] = utils.shiftAlphabet(src, index);
		}
		
		return dests;
	}
	
}
