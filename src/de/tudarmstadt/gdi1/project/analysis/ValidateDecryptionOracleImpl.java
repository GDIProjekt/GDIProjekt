package de.tudarmstadt.gdi1.project.analysis;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.alphabet.DistributionImpl;

public class ValidateDecryptionOracleImpl implements ValidateDecryptionOracle {

	private Distribution distribution;
	private Dictionary dictionary;
	
	public ValidateDecryptionOracleImpl(Distribution distribution, Dictionary dictionary) {
		super();
		this.distribution = distribution;
		this.dictionary = dictionary;
	}

	@Override
	public boolean isCorrect(String plaintext) {
				
		Alphabet alphabet = distribution.getAlphabet();
		
		Distribution plainDistribution = new DistributionImpl(distribution.getAlphabet(), plaintext, 1);
		
		double variance = 0.0;
		
		for (Character c : alphabet) {
			
			double frequencyStandart = distribution.getFrequency(c.toString());
			double frequencyPlain = plainDistribution.getFrequency(c.toString());
			
			variance += Math.abs(frequencyStandart - frequencyPlain);
		}
		
		double averageVariance = variance/(double) alphabet.size();
		
		//Wir vergleichen die Häufigkeit eines Buchstaben im plaintext mit der Häufigkeit des Buchstaben in der normalen Verteilung
		//Diese Abweichungen sollten möglichst gering sein, sonst ist mit hoher Wahrscheinlichkeit der plaintext kein gute Entschlüsselung.
		
		if (averageVariance > 0.1) {
			return false;
		}
		
		int wordCount = 0;
		
		for (String word : dictionary) {
			if (plaintext.contains(word))
				wordCount++;
		}
		
		//Im Durchschnitt hat ein Deutsches Wort 5,3 Buchstaben (...)
		//Daher erwarten wir im plaintext plaintext.length/5.3 Wörter im Text
		//Dabei kleinen Texten hier große Abweichungen geben kann und nicht unbedingt alle Wörter im Wörter buch sind,
		// Erwarten wir dass 50% gefunden werden.
		
		double averageWordsInPlainText = (double) plaintext.length()/5.3;
		double expectedFoundWords = averageWordsInPlainText*0.5;
		
		if (wordCount < expectedFoundWords) 
			return false;
		
		return true;
	}

}
