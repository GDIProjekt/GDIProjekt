package de.tudarmstadt.gdi1.project.alphabet;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.utils.UtilsImpl;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.utils.Utils;

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
		
		//alle n-Gramme, Schlüssel i gibt die Liste aller i-Gramme aus
		Map<Integer, List<String>> ngrams = utils.ngramize(normalizedText, ngramNumbers);
		
		for (Integer gramSize : ngrams.keySet()) {
			
			//alle n-Gramme einer Größe n übergeben
			List<String> grams = ngrams.get(gramSize);
			
			HashMap<String, Double> gramMap = new HashMap<String, Double>();
			
			for (String gram : grams) {
				//wenn das n-Gramm noch nicht gezählt wurde wird es in die Map aller Häufigkeiten eingefügt und erhält die Häufigkeit 1.0 
				if (!gramMap.containsKey(gram)) {
					gramMap.put(gram, 1.0);
				//ansonsten wird dieses n-Gramms neu eingefügt, allerdings mit dem um 1.0 hochgezählten Häufigkeitswert
				} 
				else {
					gramMap.put(gram, gramMap.get(gram) +1.0);
				}
			}
			
			double allGrams = grams.size();
			//Bestimmung der relativen Häufigkeit des n-Gramms zu allen n-Gramms der Größe n
			for (String gram : gramMap.keySet()) {
				gramMap.put(gram, gramMap.get(gram)/allGrams);
			}
			
			//Einsetzen aller n-Gramme mit ihren Häufigkeit, wobei der Schlüssel den Wert n hat
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
			throw new IllegalArgumentException("Error: Invalid length.");
		
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
		
		//wenn die Länge des keys nicht in der Map aller n-Gramme nicht vorhanden ist wird ein Fehler ausgeworfen
		if (!ngramMap.containsKey(length))
			throw new IllegalArgumentException("Error: Invalid key length.");
		
		HashMap<String, Double> gramMap = ngramMap.get(length);
		
		//wenn der key in der Map der n-Gramme der selben Länge wie vom key vorhanden ist wird die Häufigkeit des key ausgegeben  
		if (gramMap.containsKey(key)) {
			return gramMap.get(key);		
		}
		//falls der key nicht in der Map der n-Gramme der selben Länge wie des keys ist aber vom Alphabet akzeptiert wird ist die
		//Häufigkeit 0
		else if (alphabet.allows(key)){
			return 0;
		} else {
			throw new IllegalArgumentException("Error: Key is not part of alphabet.");
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
		
		if (rank > list.size())
			return null;
			
		return list.get(rank);
	}

}
