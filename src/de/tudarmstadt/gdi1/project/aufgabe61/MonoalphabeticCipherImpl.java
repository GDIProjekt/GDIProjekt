package aufgabe61;

import aufgabe5.AlphabetImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
public class MonoalphabeticCipherImpl extends SubstitutionCipherImpl implements MonoalphabeticCipher{
	
	protected Alphabet src;
	protected Alphabet dest;	
	AlphabetNotSameSizeException ansse = new AlphabetNotSameSizeException();		
	

    public MonoalphabeticCipherImpl (Alphabet src, Alphabet dest) {
    	
        //Alphabete müssen gleich lang sein
        try{
        	if (src.size() != dest.size())       {
        		throw ansse;
        	}
        	else{
        		this.src = src;
        		this.dest = dest;
        	}
        }
        catch (AlphabetNotSameSizeException ansse){
        	System.err.println("Error: Source alphabet and destination alphabet have to have same length.");
        	
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
	
    @SuppressWarnings("serial")
	class AlphabetNotSameSizeException extends Exception {
    	
    	public AlphabetNotSameSizeException (){
        
    	}
    	
    	public AlphabetNotSameSizeException (String message){
    		super(message);
    	}
    	
    	public AlphabetNotSameSizeException (Throwable cause){
    		super(cause);
    	}
    	
    	public AlphabetNotSameSizeException (String message, Throwable cause){
    		super (message, cause);
    	}
    }
}
