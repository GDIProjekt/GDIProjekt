
package aufgabe6;

import aufgabe6.MonoalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.*;
import de.tudarmstadt.gdi1.project.utils.Utils;

/**
 * Implementierung des Ceasar Interfaces.
 * @author .., .., .., Laurin Strelow
 *
 */
public class CaersarImpl extends MonoalphabeticCipherImpl implements Caesar{

	/**
	 * Konstruktor für CeasarImpl.
	 * @param a Das Ausgangs Alphabet. (Klartext).
	 * @param key Die Anzahl der Buchstaben, um das das Ausgangsalphabet verschoben wird, zum verschlüsseln.
	 */
	public CaersarImpl(Alphabet src, int key) {
		super(src, createDestAlphabet(src, key));
	}

	private static Alphabet createDestAlphabet(Alphabet src, int key) {
		Utils util = new UtilsImpl();
		
		return util.shiftAlphabet(src, key);
	}
	

}
