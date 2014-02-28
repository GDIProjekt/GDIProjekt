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
 * Implementierung des Distribution Interfaces.
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class DistributionImpl implements Distribution {

	/**
	 * Das Alphabet über dem die Verteilung definiert ist.
	 */
	private Alphabet alphabet;
	
	/**
	 * Eine map die erst unter der Groesse der Gramme unterscheidet und auf eine andere Hashmap abbildet,
	 * die die verschiedene Gramme als String speichert und zu diesen die Häufigkeit von 0.0 bis 1.0
	 * */
	private HashMap<Integer, HashMap<String, Double>> ngramMap;
	
	/**
	 * Konstruktor fuer die Distribution Implementierung
	 * @param source Das Alphabet ueber dem die Haeufigkeitsverteilung erstellt erstellt werden soll
	 * @param text Der Text aus dem die Haeufigkeitsverteilung erstellt werden soll.
					Buchstaben die nicht im Alphabet sind, werden aus dem text geloescht.
	 * @param ngramsize Die Groesse bis zu dem die Gramme erstellt werden soll, d.h. es werden die Gramme von 1 bis ngramsize erstellt.
	 */
	public DistributionImpl(Alphabet source, String text, int ngramsize) {
		alphabet = source;
		
		ngramMap = new HashMap<Integer, HashMap<String, Double>>(ngramsize);
		
		String normalizedText = alphabet.normalize(text);
		
		Utils utils = new UtilsImpl();
		
		int[] ngramNumbers = new int[ngramsize+1];
		for (int i = 1; i <= ngramsize; i++)
			ngramNumbers[i] = i;
		
		//alle n-Gramme, Schluessel i gibt die Liste aller i-Gramme aus
		Map<Integer, List<String>> ngrams = utils.ngramize(normalizedText, ngramNumbers);
		
		for (Integer gramSize : ngrams.keySet()) {
			
			//alle n-Gramme einer Groesse n uebergeben
			List<String> grams = ngrams.get(gramSize);
			
			HashMap<String, Double> gramMap = new HashMap<String, Double>();
			
			for (String gram : grams) {
				//wenn das n-Gramm noch nicht gezaehlt wurde wird es in die Map aller Haeufigkeiten eingefuegt und erhaelt die Haeufigkeit 1.0 
				if (!gramMap.containsKey(gram)) {
					gramMap.put(gram, 1.0);
				//ansonsten wird dieses n-Gramms neu eingefuegt, allerdings mit dem um 1.0 hochgezaehlten Haeufigkeitswert
				} 
				else {
					gramMap.put(gram, gramMap.get(gram) +1.0);
				}
			}
			
			double allGrams = grams.size();
			//Bestimmung der relativen Haeufigkeit des n-Gramms zu allen n-Gramms der Groesse n
			for (String gram : gramMap.keySet()) {
				gramMap.put(gram, gramMap.get(gram)/allGrams);
			}
			
			//Einsetzen aller n-Gramme mit ihren Haeufigkeit, wobei der Schl�ssel den Wert n hat
			ngramMap.put(gramSize, gramMap);
		}
			
	}
	
	
	@Override
	public List<String> getSorted(int length) {
		
		if (!ngramMap.containsKey(length)) //FIXME oder leere Liste zurück geben?
			throw new IllegalArgumentException("invalid length");
		
		final HashMap<String, Double> gramMap = ngramMap.get(length);
		
		/**
		 * Ein kleiner Trick, um die keys nach den values zu sortieren.
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
		
		//wenn die Laenge des keys nicht in der Map aller n-Gramme nicht vorhanden ist wird ein Fehler ausgeworfen
		if (!ngramMap.containsKey(length))
			throw new IllegalArgumentException("invalid length");
		
		HashMap<String, Double> gramMap = ngramMap.get(length);
		
		//wenn der key in der Map der n-Gramme der selben Laenge wie vom key vorhanden ist wird die Haeufigkeit des key ausgegeben
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

		rank--; ///der rank faengt bei 1 an, 
		
		if (!ngramMap.containsKey(length))
			return null;
		
		List<String> list = getSorted(length);
		
		if (rank > list.size() || rank < 0)
			return null;
		
		return list.get(rank);
	}

}
