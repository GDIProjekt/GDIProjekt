package de.tudarmstadt.gdi1.project.alphabet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;

/**
 * 
 * @author .., .., .., Laurin Strelow
 *
 */
public class DistributionImpl implements Distribution {

	
	private Alphabet alphabet;
	
	/**
	 * Eine map die erst unter den ngrammen unterscheidet und dann 
	 * */
	private HashMap<Integer, HashMap<String, Double>> ngramMap;
	
	/**
	 * 
	 * @param source
	 * @param text
	 * @param ngramsize
	 */
	public DistributionImpl(Alphabet source, String text, int ngramsize) {
		alphabet = source;
		
		ngramMap = new HashMap<Integer, HashMap<String, Double>>(ngramsize);
		
		String normalizedText = alphabet.normalize(text); //?
		
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

	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}

	@Override
	public String getByRank(int length, int rank) {

		rank--; ///der rank fängt bei 1 an
		
		if (!ngramMap.containsKey(length))
			throw new IllegalArgumentException("invalid length");
		
		List<String> list = getSorted(length);
		
		return list.get(rank);
	}

}
