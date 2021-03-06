
package de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;

/**
 * Implementierung des Ceasar Interfaces.
 * @author Quoc Thong Huynh, Dennis Kuhn, Moritz Matthiesen, Erik Laurin Strelow
 *
 */
public class CaesarImpl extends MonoalphabeticCipherImpl implements Caesar{

	/**
	 * Konstruktor fuer CeasarImpl.
	 * @param a Das Ausgangs Alphabet. (Klartext).
	 * @param key Die Anzahl der Buchstaben, um das das Ausgangsalphabet verschoben wird, zum verschluesseln.
	 */
	public CaesarImpl(int key, Alphabet src) {
		super(src, createDestAlphabet(key, src));
	}
	
	/**
	 * Erstellt aus einem Schluessel und einem Klartext Alphabet das Verschluesselungs Alphabet.
	 * @param key Der Schluessel
	 * @param src Das Klartext Alphabet
	 * @return Das Verschluesselungsalphabet.
	 */
	private static Alphabet createDestAlphabet(int key, Alphabet src) {
		Utils util = new UtilsImpl();
		
		return util.shiftAlphabet(src, key);
	}
}
