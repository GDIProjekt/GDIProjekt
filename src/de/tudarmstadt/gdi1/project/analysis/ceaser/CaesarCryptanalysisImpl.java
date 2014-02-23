package de.tudarmstadt.gdi1.project.analysis.ceaser;

import java.util.List;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.alphabet.DistributionImpl;
import de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;

public class CaesarCryptanalysisImpl implements CaesarCryptanalysis {

		
	@Override
	public Integer knownCiphertextAttack(String ciphertext,
			Distribution distribution) {
		
		Alphabet plainAlphabet = distribution.getAlphabet();
		
		Distribution cipherDistribution = new DistributionImpl(plainAlphabet, ciphertext, 1);
		
		List<String> plainCharacters = distribution.getSorted(1);
		List<String> cipherCharacters = cipherDistribution.getSorted(1);
		
		final int deep = (3 >= plainCharacters.size()) ? (plainCharacters.size()-1): 3 ;
		
		int[] keys = new int[deep];
		
		/**
		 * Berechnet die einzelnen Schlüssel.
		 */
		for (int i = 0; i < deep; i++) {
			
			char plainChar = plainCharacters.get(i).charAt(0);
			char cipherChar = cipherCharacters.get(i).charAt(0);
			
			int plainIndex = plainAlphabet.getIndex(plainChar);
			int cipherIndex = plainAlphabet.getIndex(cipherChar);
			
			keys[i] = cipherIndex - plainIndex;
		}
		
		/**
		 * Berechnet die Standartabweichung der einzelnen Schluessel.
		 * Der Schluessel mit der kleinsten Standartabweichung, sollte mit der groessten Wahrscheinlichkeit der richtige sein.
		 */
		Utils utils = new UtilsImpl();
		double[] averageVariance = new double[deep];
		
		for (int i = 0; i < deep; i++) {
			
			Alphabet cipherAlphabet = utils.shiftAlphabet(plainAlphabet, keys[i]); 
			
			double variance = 0.0;
			
			for (int j = 0; j < plainAlphabet.size(); j++) {
				
				double plainFrequency = distribution.getFrequency(plainAlphabet.getChar(j) + "");
				double cipherFrequency = cipherDistribution.getFrequency(cipherAlphabet.getChar(j) + "");
				
				variance += Math.abs(plainFrequency - cipherFrequency);
			}
			
			averageVariance[i] = variance/plainAlphabet.size();  
			
		}
		
		int lowestAverageVariance = 0;
		
		for (int i = 1; i < deep; i++) {
			if (averageVariance[i] < averageVariance[lowestAverageVariance])
				lowestAverageVariance = i;
		}
		
		return new Integer(keys[lowestAverageVariance]);
	}

	@Override
	public Integer knownCiphertextAttack(String ciphertext, Dictionary dictionary) {
		return null;
	}

	@Override
	public Integer knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dict) {
		return knownCiphertextAttack(ciphertext, distribution); ///?
	}

    @Override
    public Integer knownPlaintextAttack(String ciphertext, String plaintext, Alphabet alphabet) {
    	
    	if (ciphertext.length() == 0 || plaintext.length() == 0)
    		throw new IllegalArgumentException();
    	
    	char cipherChar = ciphertext.charAt(0);
    	char plainChar = plaintext.charAt(0); 
    	
    	int cipherIndex = alphabet.getIndex(cipherChar);
    	int plainIndex = alphabet.getIndex(plainChar);
    	
    	return cipherIndex - plainIndex;
    }

    @Override
    public Integer knownPlaintextAttack(String ciphertext, String plaintext, Distribution distribution) {
        return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
    }

    @Override
    public Integer knownPlaintextAttack(String ciphertext, String plaintext, Distribution distribution, Dictionary dictionary) {
        return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
    }

}
