package aufgabe6;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;

/**
 * Abstrakte Implementierung des SubstionCipher Interfaces.
 * 
 * @author Laurin Strelow
 *
 */
public abstract class SubstitutionCipherImpl implements SubstitutionCipher {

	
	public String encrypt(String text){
		char[] textchar = text.toCharArray();
		String result = "";
		int i = 0;
		for (char c: textchar){
			result += translate(c, i);
			i++;
		}
		return result;
	}
	
	public String decrypt(String text){
		char[] textchar = text.toCharArray();
		String result = "";
		int i = 0;
		for (char c: textchar){
			result += reverseTranslate(c, i);
			i++;
		}
		return result;
	}
	
	public abstract char translate(char chr, int i);
		
	public abstract char reverseTranslate(char chr, int i);
}
