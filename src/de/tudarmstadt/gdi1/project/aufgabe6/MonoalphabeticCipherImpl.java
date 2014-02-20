package aufgabe61;

import aufgabe5.AlphabetImpl;

/**
 * Implementierung des SubstitutionCipher Interfaces
 * @author .., .., .., Laurin Strelow
 *
 */
public abstract MonoalphabeticCipherImpl implements cipher.substitution.monoalphabetic.MonoalphabeticCipher extends SubstitutionCipherImpl {
	
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
    		throws new IllegalArgumentException("Character is not in src Alphabet");
    	
    	return dest.getChar(src.getIndex(chr));
	}
	
    @Override
	public char reverseTranslate(char chr, int i){
    	if (!dest.contains(chr))
    		throws new IllegalArgumentException("Chracter is not in dest Alphabet");
    	
    	return src.getChar(dest.getIndex(chr));
	}
	
}
