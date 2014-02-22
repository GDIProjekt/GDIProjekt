package aufgabe7;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;

public class DistributionImpl implements Distribution{
	
	String text;
	List<String> charFreq = new ArrayList<String>();
	Alphabet source;
	Map<String,Integer> charsWFreq = new HashMap<String,Integer>();
	Map<String,Integer> charsWFreqSorted = new HashMap<String,Integer>();
	
	@SuppressWarnings("rawtypes")
	private static Map sortByValues( Map unsortMap) {
		 
		@SuppressWarnings("unchecked")
		List list = new LinkedList(unsortMap.entrySet());
 
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
			
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
 
		
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
 
	
	
	public DistributionImpl(Alphabet source, String text, int ngramsize){
		
		this.source = source;
		this.text = text;
		for(int i = 0; i < source.size(); i++){
			int counter = 0;
			String tempchar = "";
			for (int j = 0; j < text.length(); j++){
				
				if (source.contains(text.charAt(j))){
					
					if (text.charAt(j)==source.getChar(i)) 
						counter = counter +1;
				}
			}			
	
			tempchar += source.getChar(i);
			charsWFreq.put(tempchar, counter);
			
			
		}
		charsWFreqSorted = sortByValues(charsWFreq);
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
	public List<String> getSorted(int length){
		List<String> strings = new ArrayList<String>();
		String[] temp = new String[charsWFreqSorted.size()];
	
		int i = charsWFreqSorted.size()-1;
		for (String s : charsWFreqSorted.keySet()){
			temp[i] = s;
			i--;
		}
		for (int j = 0; j < charsWFreqSorted.size(); j++){
			strings.add(temp[j]);
		}
		return strings;
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
	public double getFrequency(String key){
	
		double legitchars = 0;
			for(int i = 0; i < text.length(); i++){
				if (source.contains(text.charAt(i))) legitchars = legitchars+1;
			}
		return (double) charsWFreq.get(key)/legitchars;
	}

	/**
	 * 
	 * @return the alphabet of the distribution
	 */
	public Alphabet getAlphabet(){
		return null;
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
	public String getByRank(int length, int rank){
		return null;
	}
}
