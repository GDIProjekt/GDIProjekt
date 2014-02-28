package de.tudarmstadt.gdi1.project.analysis.monoalphabetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
public class MonoalphabeticKnownCiphertextCryptanalysisImpl implements MonoalphabeticKnownCiphertextCryptanalysis, GeneticAnalysis {
	
	private int stableCounter = 0;
	private int generationCounter = 0;
	private Individual bestIndividual = null;
	
	@Override
	public char[] knownCiphertextAttack(String ciphertext, Distribution distribution, Dictionary dictionary) {
		
		ValidateDecryptionOracle oracle = new ValidateDecryptionOracleImpl(distribution, dictionary);
		return knownCiphertextAttack(ciphertext, distribution, dictionary, oracle);
	}
	
	@Override
	public Object knownCiphertextAttack(String ciphertext, Distribution distribution) {
		return null;
	}

	@Override
	public Object knownCiphertextAttack(String ciphertext, Dictionary dictionary) {
		return null;
	}

	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary,
			ValidateDecryptionOracle validateDecryptionOracle) {
	
		Random random = new Random();
		
		final int populationSize = 10;
		final int numberOfSurvivors = 3;
		final int stableRunsTillBruteForce = 2000;
		final int generationsTilRestart = 5000;
		Alphabet alphabet = distribution.getAlphabet();
		
		boolean foundSolution = false;
		
		while (!foundSolution) {
			
			List<Individual> population;
			population = prepareInitialGeneration(ciphertext, alphabet, distribution, populationSize);
			bestIndividual = population.get(0);
			
			stableCounter = 0;
			generationCounter = 0;
			
			while (generationCounter < generationsTilRestart || stableCounter <= stableRunsTillBruteForce) {
			
				for (Individual i : population) {
					computeFitness(i, ciphertext, alphabet, distribution, dictionary);
				}
			
				population = computeSurvivors(ciphertext, alphabet, population, distribution, dictionary, numberOfSurvivors);
				
				if ((bestIndividual.equals(population.get(0)))) {
					stableCounter++;
				} else {
					stableCounter = 0;
					bestIndividual = population.get(0);
				}
				
				if (stableCounter == stableRunsTillBruteForce) {
					
					foundSolution = true;
					break;
					
					//für die letzten buchstaben benutzen wir reconstructKey,
					//dazu löschen wir im ersten Schritt zwei Buchstaben und danach immer einer und werten die ergebnisse aus.
					//macht auch keinen sinn
					
					/*Individual newIndividual = bruteForceBacktracking(0, bestIndividual, validateDecryptionOracle, 
							alphabet, ciphertext, distribution, dictionary);
					
					if (newIndividual.getFitness() == Double.POSITIVE_INFINITY) {
						bestIndividual = newIndividual;
						foundSolution = true;
						break;
					} else if (newIndividual.getFitness() > bestIndividual.getFitness()) {
						bestIndividual = newIndividual;
						population.add(0, newIndividual);
						stableCounter = 0;
					} else {
						//stableCounter = 0;
					}*/
				}
			
				population = generateNextGeneration(population, populationSize, random, alphabet, distribution, dictionary);
				generationCounter++;
			}
		}
		
		return bestIndividual.getAlphabet().asCharArray();
	}
	

	/** wahrscheinlich müll
	 * Gibt das Individuum zurück mit dem höchsten Fitness wert. Wird die richtige Lösung gefunden ist der Fitness wert +unendlich.
	 * @param changePosition Wird für die Rekursion gebraucht, beim ersten Aufruf sollte die position 0 sein.
	 * @param individual Das startIndiviual auf dem gesucht werden soll.
	 * @param validateDecryptionOracle Ein Oracle was die richtig Lösung erkennt.
	 * @param plainAlphabet Das Klartext Alphabet.
	 * @return Das beste gefundene Individual
	 */
	public Individual bruteForceBacktracking(int changePosition, Individual individual, ValidateDecryptionOracle validateDecryptionOracle, 
			Alphabet plainAlphabet, String ciphertext, Distribution distribution, Dictionary dictionary) {
		
		
		///wenn man hier nur sinnvoll tauscht und und nicht alle möglichen dinge tauscht kann das hier noch sinn machen ;)
		
		Alphabet cipherAlphabet = individual.getAlphabet();
		
		if (changePosition == cipherAlphabet.size() -1) {
			MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(plainAlphabet, cipherAlphabet);
			String plaintext = cipher.decrypt(ciphertext);
			
			if (validateDecryptionOracle.isCorrect(plaintext)) {
				individual.setFitness(Double.POSITIVE_INFINITY); //Ein fitness wert kann nicht größer sein
			} else {
				computeFitness(individual, ciphertext, plainAlphabet, distribution, dictionary);
			}
			return individual;
		} else {
		
			Individual bestFound = individual;
			
			for (int i = changePosition; i < individual.getAlphabet().size(); i++) {
				
				char[] array = individual.getAlphabet().asCharArray();
				swapEntriesInArray(array, i, changePosition);
				Individual newIndi = new IndividualImpl(array);
				
				newIndi = bruteForceBacktracking(changePosition +1, newIndi, validateDecryptionOracle, plainAlphabet, ciphertext, 
						distribution, dictionary);
				
				if (newIndi.getFitness() == Double.POSITIVE_INFINITY)
					return newIndi;
				else if (newIndi.getFitness() > bestFound.getFitness())
					bestFound = newIndi;
			}
			return bestFound;	
		}
	}
	
	
	@Override
	public List<Individual> prepareInitialGeneration(String ciphertext,
			Alphabet alphabet, Distribution distribution, int populationSize) {

		
		//Distribution cipherDistribution = new DistributionImpl(alphabet, ciphertext, 1);
		
		List<Individual> population = new ArrayList<Individual>();
		
		MonoalphabeticCribCryptanalysisImpl xyz = new MonoalphabeticCribCryptanalysisImpl();
		
		HashMap<Character, Character> emptyKey = new HashMap<Character, Character>();
		HashMap<Character, Collection<Character>> potentialAssigmentMap = new HashMap<Character, Collection<Character>>();
 		List<String> frequentChars = distribution.getSorted(1);
		
 		//wir erstellen eine Map von der zu jeden Buchstaben, eine List mit den besten belegungen erstellt wird
		for (Character c : alphabet) {
			Collection<Character> potentialAssigmentColl = xyz.getPotentialAssignments(c, emptyKey, ciphertext, alphabet, distribution, null);
			potentialAssigmentMap.put(c, potentialAssigmentColl);
		}
		
		int i = 0;
		while (population.size() < populationSize) {
			
			char[] chars = new char[alphabet.size()];
			
			for (int j = 0; j < alphabet.size(); j++) {
				
				//der index verschiebt sich jedes mal, so dass jedes Individuum anderes aussieht
				int index = (i+j+6)%alphabet.size();
				
				char c = frequentChars.get(index).charAt(0);
				
				Collection<Character> potentialAssigmentColl = potentialAssigmentMap.get(c);
				
				//wir suchen nach dem ersten nicht belegten character, potentialAssigmentColl hat immer alphabet.size()-1 Einträge, womit immer einer gefunden wird
				for (Character x : potentialAssigmentColl) {
					
					boolean found = false;
					for (int y = 0; y < chars.length; y++) {
						if (chars[y] == x) {
							found = true;
							break;
						}
					}
					
					if (found == false) {
						chars[index] = x;
						break;
					}
					
				}
			}
			
			
			Individual indi = new IndividualImpl(chars);
			
			if (!population.contains(indi)) {
				population.add(indi);
				System.out.println(Arrays.toString(chars));
			}
			
			i++;
		}
		
		
		///wenn man etwas hat und dieses gibt dir zu einem buchstaben eine liste von zeichen zurück, die sortiert nach der differenz der frequenz
		//dabei sollten immer es so gewählt werden, dass man diese nimmt
		
		//Buchstaben mit den ähnlichsten häufigkeiten sollten zusammen gelegt werden.
		
		
		//erzeugt eine initiale Generation,  mit einer bestimmen größe
		//erstellt aus einer häufigkeits verteilung wahscheinliche belegungen
		//individuen bekommen noch keinen fitness wert zu gewiesen
		
		return population;
	}

	@Override
	public List<Individual> generateNextGeneration(List<Individual> survivors,
			int populationSize, Random random, Alphabet alphabet,
			Distribution distribution, Dictionary dictionary) {

		//erstellt aus den überlebenen die nächste generation
		//durch zb mutation, vertauschung von buchstaben 
		
		//wir brauchen populationSize - survivos.size() neue Individuum 
		
		List<Individual> nextGeneration = new ArrayList<Individual>();
		
		int chosenSurvivor = 0;
		
		for (int i = survivors.size(); i < populationSize; i++) {
			Alphabet individualAlphabet = survivors.get(chosenSurvivor).getAlphabet();
			
			char[] array = individualAlphabet.asCharArray();
			int permutation = random.nextInt(individualAlphabet.size());
			
			for (int p = 0; p < permutation; p++) {
				
				int from = random.nextInt(individualAlphabet.size());
				int to = random.nextInt(individualAlphabet.size());
				
				swapEntriesInArray(array, from, to);
			}
			
			Individual newIndividual = new IndividualImpl(array);
			nextGeneration.add(newIndividual);
			
			chosenSurvivor = (chosenSurvivor+1)%survivors.size();
		}
		
		nextGeneration.addAll(survivors);
		
		return nextGeneration;
	}

	@Override
	public List<Individual> computeSurvivors(String ciphertext,
			Alphabet alphabet, List<Individual> generation,
			Distribution distribution, Dictionary dictionary, int nrOfSurvivors) {
		
		Comparator<Individual> comp = new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				Double fitness1 = o1.getFitness();
				Double fitness2 = o2.getFitness();
				
				return fitness2.compareTo(fitness1);
			}
		};
		
		Collections.sort(generation, comp);	
		int sizeOfSublist = (nrOfSurvivors > generation.size()) ? generation.size() : nrOfSurvivors;
		
		return generation.subList(0, sizeOfSublist);
	}

	@Override
	public double computeFitness(Individual individual, String ciphertext,
			Alphabet alphabet, Distribution distribution, Dictionary dictionary) {

		final int ngramSize = 1;
		final double frequencyWeight = 0.3;
		final double wordWeight = 1.0;
		
		double fitness = 0.0;
		
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(alphabet, individual.getAlphabet());
		String decryptedText = cipher.decrypt(ciphertext);
		Distribution cipherDistribution = new DistributionImpl(alphabet, decryptedText, ngramSize);
		
		for (int i = 1; i <= ngramSize; i++) {
			List<String> grams = distribution.getSorted(i);
			
			for (String gram : grams) {
				
				double normalFrequency = distribution.getFrequency(gram);
				double decryptedFreqeuncy = cipherDistribution.getFrequency(gram);
				
				if (normalFrequency > 0.01 || decryptedFreqeuncy > 0.01) {
					
					double diff = Math.abs(cipherDistribution.getFrequency(gram) - distribution.getFrequency(gram));
				
					//diff ist ein wert zwischen 0.0 (sehr nah) und 1.0 (weit entfernt)
					
					if (diff < 0.05) {
						fitness += i*frequencyWeight;
					}
				}
			}
		}
		
		
		for (String word : dictionary) {
			if (decryptedText.contains(word)) {
				fitness += wordWeight*word.length();
			}		
		}
		
		individual.setFitness(fitness);
		
		return fitness;
	}
	
	private void swapEntriesInArray(char[] array, int from, int to) {
		  char t = array[from];
		  array[from] = array[to];
		  array[to] = t;
	}
	
	
	@Override
	public String getState(Alphabet sourceAlphabet, Alphabet targetKey) {
		
		try {
		
			String firstLine = Arrays.toString(sourceAlphabet.asCharArray());
			String secondLine = Arrays.toString(targetKey.asCharArray());
		
			String thirdLine = "null";
			String fourthLine = "null";
			String fifthLine =  "null";
			
			int correctness = 0;
			if (bestIndividual != null) {
				thirdLine = Arrays.toString(bestIndividual.getAlphabet().asCharArray());
			
				fourthLine = "[";
				for (int i = 0; i < targetKey.size(); i++) {
					char targetChar = targetKey.getChar(i);
					char ourChar = bestIndividual.getAlphabet().getChar(i);
				
					if (targetChar == ourChar) {
						fourthLine += "1, ";
						correctness++;
					} else {
						fourthLine += "0, "; 
					}
				}
				fourthLine += "]";
				fifthLine = "fitness: " + bestIndividual.getFitness();
			}
		
			String result = firstLine + System.lineSeparator()
				+ secondLine + System.lineSeparator()
				+ thirdLine + System.lineSeparator() 
				+ fourthLine + System.lineSeparator()
				+ fifthLine + System.lineSeparator() 
				+ "generations: " + generationCounter + System.lineSeparator()
				+ "stable: " + stableCounter + System.lineSeparator()
				+ "correct: " + correctness;
				
			return result;
		} catch (NullPointerException e) {
			return "----";
		}
	}
}
