package aufgabe61;


import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
public class MonoalphabeticCipherImpl extends SubstitutionCipherImpl implements MonoalphabeticCipher{
	
	protected Alphabet src;
	protected Alphabet dest;	


    public MonoalphabeticCipherImpl (Alphabet src, Alphabet dest) {
    	
        //Alphabete müssen gleich lang sein
     
        	if (src.size() != dest.size())       {
        		throw new IllegalArgumentException("Error: Source alphabet and destination alphabet have to have same length.");
        	}
        	else{
        		this.src = src;
        		this.dest = dest;
        	
        }
        
    }
    
    /**
	 * Translates the given character that is on the given position in the text
	 * into its encrypted equivalent.
	 * 
	 * @param chr
	 *            the character that needs to be translated
	 * @param i
	 *            the position the character stands in the text
	 * @return the translated/encrypted character
	 */
	public char translate(char chr, int i){
		if (!src.contains(chr)){
			throw new IllegalArgumentException("Character is not in source alphabet.");
		}
		else
		return dest.getChar(src.getIndex(chr));
	}
	
	/**
	 * translates the given character that is on the given position in the text
	 * back into its decrypted equivalent
	 * 
	 * @param chr
	 *            the character that needs to be reversetranslated
	 * @param i
	 *            the position of the character in the text
	 * @return the reversetranslated/decrypted character
	 */
	public char reverseTranslate(char chr, int i){
		if (!dest.contains(chr)){
			throw new IllegalArgumentException("Character is not in destination alphabet.");
		}
		else
		return src.getChar(dest.getIndex(chr));
	}
	
   
}
