package de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipherImpl;

/**
 * Implementierung des SubstitutionCipher Interfaces.
 * @author Quoc Thong Huynh, Dennis Kuhn, Moritz Matthiesen, Erik Laurin Strelow
 *
 */
public class MonoalphabeticCipherImpl extends SubstitutionCipherImpl implements MonoalphabeticCipher {
	
	/**
	 * Das Ausgangsalphabet (Klartext Alphabet)
	 */
	private Alphabet src;
	
	/**
	 * Das Zielalphabet (Chiffretext)
	 */
	private Alphabet dest;	
	
	/**
	 * Konstruktor fuer MonoalphabeticCipherImpl.
	 * @param src Das Ausgangs Alphabet von dem Verschluesselt werden soll. (Klartext)
	 * @param dest Das Alphabet zu dem hin verschluesselt werden soll. (Chiffretext)
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
