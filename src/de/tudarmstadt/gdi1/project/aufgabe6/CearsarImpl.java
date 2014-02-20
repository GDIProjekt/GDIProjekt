
/**
 * Implementierung des Ceasar Interfaces.
 * @author .., .., .., Laurin Strelow
 *
 */
public class CearsarImpl implements cipher.substitution.monoalphabetic.Caesar extends MonoalphabeticCipherImpl {
    
	/**
	 * Konstruktor für CeasarImpl.
	 * @param a Das Ausgangs Alphabet. (Klartext).
	 * @param key Die Anzahl der Buchstaben, um das das Ausgangsalphabet verschoben wird, zum verschlüsseln.
	 */
	public CeasarImpl(Alphabet src, int key) {
        super(src, Utils.shiftAlphabet(a,key));
    }
}
