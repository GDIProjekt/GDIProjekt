package de.tudarmstadt.gdi1.project.alphabet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Implementierung des Dictionary Interfaces.
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class DictionaryImpl implements Dictionary {
	
	/**
	 * Das im Konstruktor uebergebene Alphabet
	 */
	Alphabet alphabet;
	
	/**
	 * Eine Liste aller Woerter aus dem im Konstruktor uebergebenen Text
	 */
	List<String> words;
	
	/**
	 * Konstruktor fuer die Implementierung des Dictionary Interfaces.
	 * @param alphabet Alphabet ueber dem das Dictionary erstellt werden soll.
	 * @param text Der Text aus dem das Dictionary erstellt werden soll; Woerter werden mit Leerzeichen, ',', '!', '?' getrennt.
	 */
	public DictionaryImpl(Alphabet alphabet,String text) {
		this.alphabet = alphabet;
		
		//sortiert die eingefügten Wörter direkt alphabetisch.
		TreeSet<String> sortedWords = new TreeSet<String>();
		
		Scanner scanner = new Scanner(text);
		
		//Trennungszeichen zwischen den Wörter, also entweder Kommata, !, ?, Leerezeichen
		// Das + ist ein "oder".
		scanner.useDelimiter("[,!?.\\s]+");
		
		while(scanner.hasNext()) {
			String s = scanner.next();
			
			if (alphabet.allows(s))
				sortedWords.add(s);
		}
		
		//wir erstellen eine Liste um auf den Index zuzugreifen.
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
