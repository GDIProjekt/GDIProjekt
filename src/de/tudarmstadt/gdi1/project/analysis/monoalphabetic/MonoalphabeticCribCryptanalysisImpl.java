package de.tudarmstadt.gdi1.project.analysis.monoalphabetic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;

public class MonoalphabeticCribCryptanalysisImpl implements
		MonoalphabeticCribCryptanalysis, BacktrackingAnalysis {

	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary,
			List<String> cribs,
			ValidateDecryptionOracle validateDecryptionOracle) {
		
		HashMap<Character, Character> key = new HashMap<Character, Character>();
		Alphabet alphabet = distribution.getAlphabet();
		
		Map<Character, Character> constructedKey = reconstructKey(key, ciphertext, alphabet, distribution, dictionary, cribs, validateDecryptionOracle);
	
		char[] result = new char[alphabet.size()];
		
		int i = 0;
		for (Character c : alphabet) {
			result[i] = constructedKey.get(c);
			i++;
		}
		
		return result;
	}
	
	@Override
	public Map<Character, Character> reconstructKey( Map<Character, Character> key, String ciphertext,Alphabet alphabet, 
			Distribution distribution,Dictionary dictionary, List<String> cribs, ValidateDecryptionOracle validateDecryptionOracle) {
		
		
		
		
		return null;
	}

	@Override
	public Collection<Character> getPotentialAssignments(
			Character targetCharacter, Map<Character, Character> key,
			String ciphertext, Alphabet alphabet, Distribution distribution,
			Dictionary dictionary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character getNextSourceChar(Map<Character, Character> key,
			Alphabet alphabet, Distribution distribution,
			Dictionary dictionary, List<String> cribs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPromisingPath(Alphabet alphabet, String ciphertext,
			Map<Character, Character> key, Distribution distribution,
			Dictionary dictionary, Collection<String> cribs) {
		
		
		return false;
	}

	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary, List<String> cribs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getState(Alphabet sourceAlphabet, Alphabet targetKey) {
		
		///Soll einen zwischen stand zur berechnung ausgeben
		
		return null;
	}

}
