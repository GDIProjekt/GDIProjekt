package de.tudarmstadt.gdi1.project.alphabet;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;

public class DistributionImpl implements Distribution{
	
private Alphabet alphabet;
	
	/**
	 * Eine map die erst unter den ngrammen unterscheidet und dann 
	 * */
	private HashMap<Integer, HashMap<String, Double>> ngramMap;
	
	/**
	 * Konstruktor für DistributionImpl
	 * @param source
	 * @param text
	 * @param ngramsize
	 */
	public DistributionImpl(Alphabet source, String text, int ngramsize) {
		alphabet = source;
		
		ngramMap = new HashMap<Integer, HashMap<String, Double>>(ngramsize);
		
		String normalizedText = alphabet.normalize(text);
		
		Utils utils = new UtilsImpl();
		
		int[] ngramNumbers = new int[ngramsize+1];
		for (int i = 1; i <= ngramsize; i++)
			ngramNumbers[i] = i;
		
		Map<Integer, List<String>> ngrams = utils.ngramize(normalizedText, ngramNumbers);
		
		for (Integer gramSize : ngrams.keySet()) {
			
			List<String> grams = ngrams.get(gramSize);
			
			HashMap<String, Double> gramMap = new HashMap<String, Double>();
			
			for (String gram : grams) {
				if (!gramMap.containsKey(gram)) {
					gramMap.put(gram, 1.0);
				} else {
					gramMap.put(gram, gramMap.get(gram) +1.0);
				}
			}
			
			double allGrams = grams.size();
			for (String gram : gramMap.keySet()) {
				gramMap.put(gram, gramMap.get(gram)/allGrams);
			}
			
			ngramMap.put(gramSize, gramMap);
		}
			
	}
	
	/**
	 * retrieve all the ngrams of the given length from all the learned strings,
	 * sorted by their frequency
	 * 
	 * @param length
	 *            the ngram length, so 1 means only a character 2 stands for
	 *            bigrams and so on.
	 * @return a descending sorted list that contains all the ngrams sorted by
	 *         their frequency
	 */
	@Override
	public List<String> getSorted(int length) {
		
		if (!ngramMap.containsKey(length))
			throw new IllegalArgumentException("invalid length");
		
		final HashMap<String, Double> gramMap = ngramMap.get(length);
		
		/**
		 * Ein kleiner Trick, um die keys nach dem values zu sortieren.
		 */
		Comparator<String> comp = new Comparator<String>(){
			@Override
			public int compare(String key1, String key2) {
				
				Double val1 = gramMap.get(key1);
				Double val2 = gramMap.get(key2);
				
				int result = val2.compareTo(val1);
				
				/**
				 * Damit besteht man alle Template Distribution Tests.
				 * Die Keys die den gleichen Wert haben, werden nach den key selbst (alphabetisch) sortiert.
				 */
				if (result == 0)
					return key1.compareTo(key2);
				else
					return result;
			}
		};
		
		ArrayList<String> list = new ArrayList<String>(gramMap.keySet());
		Collections.sort(list, comp);
	    
	    return list;
	}
	
	/**
	 * Gets the frequency to a given key. If the key is longer than the created
	 * ngrams or if the key was never seen the frequency is 0.
	 * 
	 * @param key
	 *            the character, bigram, trigram,... we want the frequency for
	 * @return the frequency of the given character, bigram, trigram,... in all
	 *         the learned texts
	 */
	@Override
	public double getFrequency(String key) {
		
		int length = key.length();
		
		if (!ngramMap.containsKey(length))
			throw new IllegalArgumentException("invalid key length");
		
		HashMap<String, Double> gramMap = ngramMap.get(length);
		
		if (gramMap.containsKey(key)) {
			return gramMap.get(key);
		} else if (alphabet.allows(key)){
			return 0;
		} else {
			throw new IllegalArgumentException("Key is not part of alphabet");
		}
	}
	
	/**
	 * 
	 * @return the alphabet of the distribution
	 */
	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}
	
	/**
	 * retrieves the string with its learned frequency from the distribution, by
	 * its size and frequency rank.
	 * 
	 * @param length
	 *            the size of the ngram
	 * @param rank
	 *            the rank where we want to look at (1 = highest rank)
	 * @return the ngram of the given size that is on the given rank in its
	 *         distribution or null if the ngramsize is bigger than the maximum
	 *         learned ngram size or the rank is higher than the number of
	 *         learned ngrams
	 */
	@Override
	public String getByRank(int length, int rank) {

		rank--; ///der rank fÃ¤ngt bei 1 an
		
		if (!ngramMap.containsKey(length))
			return null;
		
		List<String> list = getSorted(length);
			if (rank > list.size()) return null;
			
		return list.get(rank);
	}

}
