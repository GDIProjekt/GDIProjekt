package de.tudarmstadt.gdi1.project.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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

    @Override
    public Alphabet shiftAlphabet(Alphabet a, int k) {
        
    	Character[] shiftedChars = new Character[a.size()];
    	
        if (k%a.size() == 0) 
        	return a; //throws new IllegalArgumentException("Shift results in same Alphabet");
        
        for (int i = 0; i < a.size(); i++) {
        	///Javas Module rechnet bei Zahlen < 0, nicht wie man es erwarten würden, deswegen das doppelete Modulo.
        	int newIndex = ((i-k)%a.size() + a.size())%a.size();
        	
            shiftedChars[newIndex] = a.getChar(i);
        }
        return new AlphabetImpl(Arrays.asList(shiftedChars));
    }
	
    @Override
    public Alphabet reverseAlphabet(Alphabet a) {
        
    	Character[] reversedChars = new Character[a.size()];
        for (int i = 0; i < a.size(); i++) {
           
           int newIndex = a.size() - i -1;
           
           reversedChars[newIndex] = a.getChar(i);
        }
       
        return new AlphabetImpl(Arrays.asList(reversedChars));
    }
    
    @Override
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
		int blocks = 0;
		String result = "";
		String blockstring = "";
		for (int i = 0; i < ciphertext.length(); i++){
			
			if (blockstring.length() < 10){
				blockstring += ciphertext.charAt(i);
			}
			else{
				if (blocks == 5){
					result = result+ blockstring+ System.lineSeparator() ;
					
					blockstring = "" + ciphertext.charAt(i);
					
					blocks = 0;
				}
				else{
					result = result + blockstring + " ";
					blockstring = "" + ciphertext.charAt(i);
					blocks++;
				}
			}
			
		}
		return result;
	}

	@Override
	public Map<Integer, List<String>> ngramize(String text, int... lengths) {
		
		HashMap<Integer, List<String>> result = new HashMap<Integer, List<String>>();
		
		for (int i = 0; i < lengths.length; i++) {
			
			List<String> list = new LinkedList<String>();
			
			for (int j = 0; j < text.length()-lengths[i]+1; j++) {
				
				String subString = text.substring(j, j+lengths[i]);
				
				list.add(subString);
			}
			
			result.put(lengths[i], list);
		}
		
		return result;
	}

	@Override
	public boolean containsSameCharacters(Alphabet alphabet1, Alphabet alphabet2) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
