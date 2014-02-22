package de.tudarmstadt.gdi1.project.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;


/**
 * Implementierung des Utils Interfaces.
 * @author .., .., .., Laurin Strelow
 *
 */
public class UtilsImpl implements de.tudarmstadt.gdi1.project.utils.Utils {

    public Alphabet shiftAlphabet(Alphabet a, int k) {
        
    	Character[] shiftedChars = new Character[a.size()];
    	
        if (k%a.size() == 0) 
        	return a; //throws new IllegalArgumentException("Shift results in same Alphabet");
        
        for (int i = 0; i < a.size(); i++) {
            int newIndex = Math.abs((a.size() + i - k)%a.size());
            
            shiftedChars[newIndex] = a.getChar(i);
        }
        return new AlphabetImpl(Arrays.asList(shiftedChars));
    }
	
    public Alphabet reverseAlphabet(Alphabet a) {
        
    	Character[] reversedChars = new Character[a.size()];
        for (int i = 0; i < a.size(); i++) {
           
           int newIndex = a.size() - i -1;
           
           reversedChars[newIndex] = a.getChar(i);
        }
       
        return new AlphabetImpl(Arrays.asList(reversedChars));
    }
    
    public Alphabet randomizeAlphabet(Alphabet a) {
        
        Character[] randomizedChars = new Character[a.size()];
    
        Arrays.fill(randomizedChars, null);
        
        Random randomGenerator = new Random();
        
        for (int i = 0; i < a.size(); i++) {
        	
        	int newIndex;
        	
        	do {
        		newIndex = randomGenerator.nextInt(a.size());
        	} while (randomizedChars[newIndex] != null);
        	
        	randomizedChars[newIndex] = a.getChar(i);
        }
        
        return new AlphabetImpl(Arrays.asList(randomizedChars));
    }

	@Override
	public String toDisplay(String ciphertext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, List<String>> ngramize(String text, int... lengths) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsSameCharacters(Alphabet alphabet1, Alphabet alphabet2) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
