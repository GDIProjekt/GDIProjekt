package de.tudarmstadt.gdi1.project.cipher.substitution;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;

/**
 * Abstrakte Implementierung des SubstionCipher Interfaces.
 * 
 * @author Laurin Strelow
 *
 */
public abstract class SubstitutionCipherImpl implements SubstitutionCipher {

	@Override
	public final String encrypt(String text){
		char[] textchar = text.toCharArray();
		String result = "";
		int i = 0;
		for (char c: textchar){
			result += translate(c, i);
			i++;
		}
		return result;
	}
	
	@Override
	public final String decrypt(String text){
		char[] textchar = text.toCharArray();
		String result = "";
		int i = 0;
		for (char c: textchar){
			result += reverseTranslate(c, i);
			i++;
		}
		return result;
	}
	
	@Override
	public abstract char translate(char chr, int i);
		
	@Override
	public abstract char reverseTranslate(char chr, int i);
}
