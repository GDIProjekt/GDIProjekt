package de.tudarmstadt.gdi1.project.cipher.substitution;
import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;

public abstract class SubstitutionCipherImpl implements SubstitutionCipher{

	
	/**
	 * Encrypt a text according to the encryption method of the cipher
	 * @param text the plaintext to encrypt
	 * @return the encrypted plaintext (=ciphertext)
	 */
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
	
	/**
	 * Decrypt a text according to the decryption method of the cipher
	 * @param text the ciphertext to decrypt
	 * @return the decrypted ciphertext (=plaintext)
	 */
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
		public abstract char translate(char chr, int i);
		

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
		public abstract char reverseTranslate(char chr, int i);
}

