package de.tudarmstadt.gdi1.project.analysis.monoalphabetic;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.EncryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCpaNpaCryptanalysis;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;

/**
 * Implementierung des MonoalphabeticCpaNpaCryptanalysis Interfaces.
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class MonoalphabeticCpaNpaCryptanalysisImpl implements
		MonoalphabeticCpaNpaCryptanalysis {


	@Override
	public char[] chosenPlaintextAttack(
			EncryptionOracle<MonoalphabeticCipher> oracle, Alphabet alphabet) {
		
		char[] plainChars = alphabet.asCharArray();
		String plainCharsString = new String(plainChars);
		
		//Wir lassen uns vom verschluesselungs Oracle unser Alphabet verschluesseln und das ist die Loesung.
		String cipherCharsString = oracle.encrypt(plainCharsString);
		
		return cipherCharsString.toCharArray();
	}

	@Override
	public char[] knownPlaintextAttack(String ciphertext, String plaintext, Alphabet alphabet) {
		
		char[] cipherChars = new char[alphabet.size()];
		
		//Wir haben zu unserem Verschluesselungstext den Klartext und uebertragen die Buchstaben, die vorhanden sind.
		int i = 0;
		for (Character c : alphabet) {
			int index = plaintext.indexOf(c);
			if (index >= 0) {
				cipherChars[i] = ciphertext.charAt(index);
			} else {
				cipherChars[i] = ' ';
			}
			i++;
		}
		
		return cipherChars;
	}

	@Override
	public Object knownPlaintextAttack(String ciphertext, String plaintext,
			Distribution distribution) {
		return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
	}
	
	
	@Override
	public Object knownPlaintextAttack(String ciphertext, String plaintext,
			Distribution distribution, Dictionary dictionary) {
		return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
	}

}
