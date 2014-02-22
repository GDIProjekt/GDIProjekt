package de.tudarmstadt.gdi1.project.alphabet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;

/**
 * 
 * @author .., .., .., Laurin Strelow
 *
 */
public class DistributionImpl implements Distribution {

	
	Alphabet alphabet;
	
	HashMap<String, Double> map;
	
	/**
	 * 
	 * @param source
	 * @param text
	 * @param ngramsize
	 */
	public DistributionImpl(Alphabet source, String text, int ngramsize) {
		alphabet = source;
		
		map = new HashMap<String, Double>();
		
		int[] counters = new int[alphabet.size()];
		
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (alphabet.contains(c)) {
				int index = source.getIndex(c);
				counters[index]++;
			}
		}
		
		for (int i = 0; i < alphabet.size(); i++) {
			Double frequency =  ((double)counters[i])/((double)text.length());
			
			map.put(alphabet.getChar(i) + "", frequency);
		}
		
	}
	
	
	@Override
	public List<String> getSorted(int length) {
		
		/**
		 * Ein kleiner Trick, um die keys nach dem values zu sortieren.
		 */
		Comparator<String> comp = new Comparator<String>(){
			@Override
			public int compare(String key1, String key2) {
				
				Double val1 = map.get(key1);
				Double val2 = map.get(key2);
				
				return val2.compareTo(val1);
			}
		};
		
		ArrayList<String> list = new ArrayList<String>(map.keySet());
		Collections.sort(list, comp);
	    
	    return list;
	}

	@Override
	public double getFrequency(String key) {
		return map.get(key);
	}

	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}

	@Override
	public String getByRank(int length, int rank) {
		// TODO Auto-generated method stub
		return null;
	}

}
