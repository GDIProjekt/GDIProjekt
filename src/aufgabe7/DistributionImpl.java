package aufgabe7;

import java.util.List;
import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;

public class DistributionImpl implements Distribution{
	int[] charFrequency;
	List<String> charFreq = new ArrayList<String>();
	Alphabet source;
	
	
	public DistributionImpl(Alphabet source, String text, int ngramsize){
		charFrequency = new int[source.size()];
		this.source = source;
		for(int i = 0; i < source.size(); i++){
			int counter = 0;
			for (int j = 0; j < text.length(); j++){
				
				if (source.contains(text.charAt(j))){
					
					if (text.charAt(j)==source.getChar(i)) 
						counter++;
				}
			}			
			charFrequency[i] = counter;
			
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
	public List<String> getSorted(int length){
		List<String> strings = new ArrayList<String>();
		for (int i = 0; i < charFrequency.length; i++){
			
			for (int j = i+1; j < charFrequency.length; j++){
				if (charFrequency[i] > charFrequency[j]){
				
				}
			}
		
		}
		return null;
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
		char[] chars = key.toCharArray();
		int legitchars = 0;
			for(int i = 0; i<key.length(); i++){
				if (source.contains(key.charAt(i))) legitchars++;
			}
		return charFrequency[source.getIndex(chars[0])]/legitchars;
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
