package de.tudarmstadt.gdi1.project.analysis.monoalphabetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.alphabet.DistributionImpl;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracleImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipherImpl;

/**
 * 
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class MonoalphabeticCribCryptanalysisImpl implements
		MonoalphabeticCribCryptanalysis, BacktrackingAnalysis {

	private String currentPath = "";
	private Map<Character, Character> currentKey = new HashMap<Character, Character>();;
	private int iterations;
	
	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary, List<String> cribs) {

		ValidateDecryptionOracle oracle = new ValidateDecryptionOracleImpl(distribution, dictionary);
		
		return knownCiphertextAttack(ciphertext, distribution, dictionary, cribs, oracle);
	}
	
	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary,
			List<String> cribs,
			ValidateDecryptionOracle validateDecryptionOracle) {
		
		Map<Character, Character> key = currentKey;
		
		key.clear();
		
		currentPath = "";
		iterations = 0;
		
		Alphabet alphabet = distribution.getAlphabet();
		
		Map<Character, Character> constructedKey = reconstructKey(key, ciphertext, alphabet, distribution, dictionary, cribs, validateDecryptionOracle);
	
		if (constructedKey == null)
			return null;
		
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
		
		iterations++;
		
		if (isKeyComplete(key, alphabet)) {
			
			String plainText = createPlainTextOfCipherTextFromKey(key, ciphertext, alphabet);
			
			if (validateDecryptionOracle.isCorrect(plainText))
				return key;
			else
				return null; //Signalisiert, dass der key komplett ist aber keine richtige Lösung
		} else {
			
			if (isPromisingPath(alphabet, ciphertext, key, distribution, dictionary, cribs) == false)
				return null;
			
			char nextChar = getNextSourceChar(key, alphabet, distribution, dictionary, cribs);
			
			Collection<Character> potentialAssigments = getPotentialAssignments(nextChar, key, ciphertext, alphabet, distribution, dictionary);
			
			for (Character nextCipherChar : potentialAssigments) {
				key.put(nextChar, nextCipherChar);
				
				currentPath += nextChar;
				
				Map<Character, Character> result = reconstructKey(key, ciphertext, alphabet, distribution, dictionary, cribs, validateDecryptionOracle);
				if (result != null)
					return result;
				
				currentPath = currentPath.substring(0, currentPath.length() -1);
				
				key.remove(nextChar);
			}
			
			return null; //Signalisiert, dass keine richtige Lösung gefunden worden ist.
		}
	}

	@Override
	public Collection<Character> getPotentialAssignments(
			Character targetCharacter, Map<Character, Character> key,
			String ciphertext, Alphabet alphabet, Distribution distribution,
			Dictionary dictionary) {
		
		//Die verschieden möglichen Buchstaben, werden danach sortiert, wer die kleinste Differenz zur Häufigkeit von target hat.
		
		Collection<Character> values = key.values();
		final Distribution cipherDistribution = new DistributionImpl(alphabet, ciphertext, 1);
		
		final Double targetFrequency = distribution.getFrequency(targetCharacter.toString());
		
		Comparator<Character> comparator = new Comparator<Character>(){
			@Override
			public int compare(Character key1, Character key2) {
				
				Double frequency1 = Math.abs(targetFrequency - cipherDistribution.getFrequency(key1.toString()));
				Double frequency2 = Math.abs(targetFrequency - cipherDistribution.getFrequency(key2.toString()));
				
				return frequency1.compareTo(frequency2);
			}
		};
		
		ArrayList<Character> potentialAssignments = new ArrayList<Character>();
		for (Character c : alphabet) {
			if (!values.contains(c))
				potentialAssignments.add(c);
		}	
		Collections.sort(potentialAssignments, comparator);
		
		return potentialAssignments;
	}

	@Override
	public Character getNextSourceChar(Map<Character, Character> key,
			Alphabet alphabet, Distribution distribution,
			Dictionary dictionary, List<String> cribs) {
		
		char mostFrequentChar = alphabet.getChar(0);
		double frequency = -1.0;
		
		Set<Character> cribsCharacters = createCharacterSetFromCribsList(cribs);
		
		for (Character c : alphabet) {
			double f = distribution.getFrequency(c.toString());
			
			if (cribsCharacters.contains(c))
				f += 2; //Stellt sicher, dass die Buchstaben aus dem Cribs immer als erstes gewählt werden
			
			if (!key.containsKey(c) && f > frequency) {
				frequency = f;
				mostFrequentChar = c;
			}
		}
		
		return mostFrequentChar;
	}

	@Override
	public boolean isPromisingPath(Alphabet alphabet, String ciphertext,
			Map<Character, Character> key, Distribution distribution,
			Dictionary dictionary, Collection<String> cribs) {
		
		
		Set<Character> cribsCharacters = createCharacterSetFromCribsList(cribs);
		
		if (key.keySet().containsAll(cribsCharacters)) {
			
			//Exact Crib Match, wir verschlüsseln die cribs und schauen, ob dieses Exact so im Text vorkommt.
			
			for (String crib : cribs) {
				
				StringBuilder cipherCribBuilder = new StringBuilder(crib.length());
				for (int i = 0; i < crib.length(); i++) {
					cipherCribBuilder.append(key.get(crib.charAt(i)));
				}
				String cipherCrib = cipherCribBuilder.toString(); 
				
				if (!ciphertext.contains(cipherCrib))
					return false;
			}
			return true;
		} else {
			//Partial Crib Match
			Collection<Character> cipherChars = key.values();
			
			//Der ciphertext wird so berabeitet dass alle buchstaben die wir noch keinen klartext buchstaben haben durch # belegt wird.
			StringBuilder preparedCipherTextBuilder = new StringBuilder(ciphertext.length());
			for (int i = 0; i < ciphertext.length(); i++) {
				char c = ciphertext.charAt(i);
				if (cipherChars.contains(c)) {
					preparedCipherTextBuilder.append(c);
				} else {
					preparedCipherTextBuilder.append('#');
				}
			}
			String preparedCipherText = preparedCipherTextBuilder.toString();
			
			//Der crib wird "verschlüsselt" mit dem schlüssel den wir schon gefunden haben, alle anderen werden durch # ersetzt.
			for (String crib : cribs) {
				
				StringBuilder preparedCribBuilder = new StringBuilder(crib.length());
				for (int i = 0; i < crib.length(); i++) {
					char c = crib.charAt(i);
					if (key.containsKey(c)) {
						preparedCribBuilder.append(key.get(c));
					} else {
						preparedCribBuilder.append('#');
					}
				}
				String preparedCrib = preparedCribBuilder.toString();
				
				//Stimmt der weg bis hier hin, sollte preparedCipherText preparedCrib beinhalten.
				if (!preparedCipherText.contains(preparedCrib)) 
					return false;
			}
			
			return true;
		}
	}

	@Override
	public String getState(Alphabet sourceAlphabet, Alphabet targetKey) {
		
		try {
		
			char[] sourceArray = sourceAlphabet.asCharArray();
			char[] targetArray = targetKey.asCharArray();
		
			String thirdLine = "[";
			String fourthLine = "[";
		
			int i = 0;
			for (Character c : sourceAlphabet) {
			
				if (currentKey.containsKey(c)) {
					thirdLine += currentKey.get(c) + ", ";
					fourthLine += (currentKey.get(c) == targetKey.getChar(i)) ? "1, " : "0, "; //index fehler
				} else {
					thirdLine += " , ";
					fourthLine += "0, ";
				}
				i++;
			}
			thirdLine += "]";
			fourthLine += "]";
			
			int correctness = 0;
			for (i = 0; i < currentPath.length(); i++) {
				char c = currentPath.charAt(i);
			
				char rightChar = targetKey.getChar(sourceAlphabet.getIndex(c));
				char chosenChar = currentKey.get(c);
			
				if (rightChar == chosenChar)
					correctness++;
				else 
					break;
			}
		
			String result = Arrays.toString(sourceArray) + System.lineSeparator() 
				+ Arrays.toString(targetArray) + System.lineSeparator() 
				+ thirdLine + System.lineSeparator()
				+ fourthLine + System.lineSeparator()
				+ "iterations: " + iterations + System.lineSeparator()
				+ "path: " + currentPath + System.lineSeparator() 
				+ "correctness: " + correctness;
			return result;
		} catch (NullPointerException e) {
			return "----";
		}
	}

	
	/**
	 * Ueberprueft, ob der uebergebenene vollstaendig ist, d.h alle Buchstaben aus dem Alphabet belegt sind.
	 * @param key Der Schluessel
	 * @param alphabet Das Alphabet
	 * @return true wenn der key vollstaendig ist, sonst false
	 */
	private boolean isKeyComplete(Map<Character, Character> key, Alphabet alphabet) {

		for (Character c : alphabet) {
			if (!key.containsKey(c)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Setzt vorraus, dass isKeyComplete(key, alphabet) true ist.
	 * @param key
	 * @param cipherText
	 * @param alphabet
	 * @return
	 */
	private String createPlainTextOfCipherTextFromKey(Map<Character, Character> key, String cipherText, Alphabet alphabet) {

		List<Character> cipherChars = new ArrayList<Character>(alphabet.size()); //TODO ?
		
		for (Character c : alphabet) {
			cipherChars.add(key.get(c));
		}
		
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(alphabet, new AlphabetImpl(cipherChars));
		return cipher.decrypt(cipherText);
	}
	
	/**
	 * Erstellt aus einer Liste von cribs ein Set, in dem jeder Buchstaben der cribs vertreten ist.
	 * @param cribs Die List von cribs.
	 * @return Eine Set von Buchstaben.
	 */
	private Set<Character> createCharacterSetFromCribsList(Collection<String> cribs) {
		
		HashSet<Character> set = new HashSet<Character>();
		
		for (String s : cribs) {
			for (int i = 0; i < s.length(); i++)
				set.add(s.charAt(i));
		}
		
		return set;
	}
	
}
