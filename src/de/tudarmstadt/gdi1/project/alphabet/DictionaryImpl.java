package de.tudarmstadt.gdi1.project.alphabet;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.alphabet.Dictionary;

import java.util.Scanner;
import java.util.Iterator;
import java.util.List;



import java.util.TreeSet;


public class DictionaryImpl implements Dictionary {

Alphabet alphabet;
	
	List<String> words;
	
	/**
	 * Konstruktor fÃ¼r die Implementierung des Dictionary Interfaces.
	 * @param alphabet Alphabet auf dem das Dictionary  TODO
	 * @param text
	 */
	public DictionaryImpl(Alphabet alphabet,String text) {
		this.alphabet = alphabet;
		
		TreeSet<String> sortedWords = new TreeSet<String>();
		
		Scanner scanner = new Scanner(text);
		scanner.useDelimiter("[,!?.\\s]+");
		
		while(scanner.hasNext()) {
			String s = scanner.next();
			
			if (alphabet.allows(s))
				sortedWords.add(s);
		}
		
		words = new ArrayList<String>(sortedWords);
		
		scanner.close();
	}
	
	@Override
	public Iterator<String> iterator() {
		return words.iterator();
	}

	@Override
	public boolean contains(String word) {
		return words.contains(word);
	}

	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}

	@Override
	public int size() {
		return words.size();
	}

	@Override
	public String get(int index) {
		return words.get(index);
	}


}
