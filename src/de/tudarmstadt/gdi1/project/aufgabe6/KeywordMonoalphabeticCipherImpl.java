package de.tudarmstadt.gdi1.project.test;

/**
 * 
 * @author .., .., .., Laurin Strelow
 * 
 */
public class KeywordMonoalphabeticCipherImpl implements cipher.substitution.monoalphabetic.KeywordMonoalphabeticCipher extends MonoalphabeticCipherImpl {

	/**
	 * @param keyWord 
	 * TODO muss das Keyword kleiner sein als das a?..
	 * @param a 
	 */
	public KeywordMonoalphabeticCipherImpl(String keyWord, Alphabet a) {
		
		char[] array = keyWord.toCharArray();
		
		ArrayList<Character> list = new ArrayList<Character>();
		
		for (char c : array) {
			list.add(c);
		}
		
		Alphabet reversedAlphabet = Utils.reversedAlphabet(a);
		
		for (Character c in reversedAlphabet) {
			
			if (!list.contains(c))
				list.add(c);
		}
		
		Alphabet dest = new AlphabetImpl(list.subList(0, a.size()));
		
		super(a, dest);
	}
	
	
}
