package aufgabe6;

import java.util.ArrayList;

import aufgabe5.AlphabetImpl;
import aufgabe6.MonoalphabeticCipherImpl;
import aufgabe6.UtilsImpl;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.*;
import de.tudarmstadt.gdi1.project.utils.Utils;

/**
 * 
 * @author .., .., .., Laurin Strelow
 * 
 */
public class KeywordMonoalphabeticCipherImpl extends MonoalphabeticCipherImpl implements KeywordMonoalphabeticCipher {

	/**
	 * @param keyWord 
	 * TODO muss das Keyword kleiner gleich als a sein?..
	 * @param a 
	 */
	public KeywordMonoalphabeticCipherImpl(String keyWord, Alphabet a) {
		super(a, createDestAlphabet(keyWord, a));	
	}
	
	
	private static Alphabet createDestAlphabet(String keyWord, Alphabet a) {
		char[] array = keyWord.toCharArray();
		
		ArrayList<Character> list = new ArrayList<Character>();
		
		for (char c : array) {
			list.add(c);
		}
		
		Utils util = new UtilsImpl();
		
		Alphabet reversedAlphabet = util.reverseAlphabet(a);
		
		for (Character c : reversedAlphabet) {
			
			if (!list.contains(c))
				list.add(c);
		}
		
		//TODO generierung des der liste schon abrechen wenn genung buchstaben vorhanden sind.
		
		return new AlphabetImpl(list.subList(0, a.size()));
	}
	
}
