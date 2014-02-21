package aufgabe6;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.*;

import aufgabe6.SubstitutionCipherImpl;

/**
 * Implementierung des SubstitutionCipher Interfaces
 * @author .., .., .., Laurin Strelow
 *
 */
public class MonoalphabeticCipherImpl extends SubstitutionCipherImpl implements MonoalphabeticCipher {
	
	private Alphabet src;
	private Alphabet dest;	
	
	/**
	 * Konstruktor für MonoalphabeticCipherImpl.
	 * @param src Das Ausgangs Alphabet von dem Verschlüsselt werden soll. (Klartext)
	 * @param dest Das Alphabet zu dem hin verschlüsselt werden soll. (Chiffretext)
	 */
    public MonoalphabeticCipherImpl (Alphabet src, Alphabet dest) {
        
        if (src.size() != dest.size())
            throw new IllegalArgumentException("Both Alphabets need the same size!");
        
         this.src = src;
         this.dest = dest;
    }
	
    @Override
	public char translate(char chr, int i){
    	
    	if (!src.contains(chr))
    		throw new IllegalArgumentException("Character is not in src Alphabet");
    	
    	return dest.getChar(src.getIndex(chr));
	}
	
    @Override
	public char reverseTranslate(char chr, int i){
    	if (!dest.contains(chr))
    		throw new IllegalArgumentException("Chracter is not in dest Alphabet");
    	
    	return src.getChar(dest.getIndex(chr));
	}

	
}
