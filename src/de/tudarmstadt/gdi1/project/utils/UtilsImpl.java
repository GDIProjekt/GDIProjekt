package src.de.tudarmstadt.gdi1.project.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.utils.Utils;

public class UtilsImpl implements Utils {
	/**
	 * Returns the given alphabet shifted by pos positions to the left.
	 * 
	 * @param alphabet
	 *            the alphabet
	 * @param shift
	 *            the number of positions to shift
	 * @return the new shifted alphabet
	 */
    public Alphabet shiftAlphabet(Alphabet a, int k) {
        
    	Character[] shiftedChars = new Character[a.size()];
    	
        if (k %a.size() == 0) 
        	return a; //throws new IllegalArgumentException("Shift results in same Alphabet");
    
        for (int i = 0; i < a.size(); i++) {
            int newIndex = (a.size()+(i-k))%a.size();   
            shiftedChars[newIndex] = a.getChar(i);
        }
        return new AlphabetImpl(Arrays.asList(shiftedChars));
    }
    
    /**
	 * Returns the given alphabet in reverse order (a,b,c,...,x,y,z) ->
	 * (z,y,x,...,c,b,a).
	 * 
	 * @param alphabet
	 *            the alphabet
	 * 
	 * @return a new alphabet with the same characters but in reverse order
	 */
    public Alphabet reverseAlphabet(Alphabet a) {
        
    	Character[] reversedChars = new Character[a.size()];
        for (int i = 0; i < a.size(); i++) {
           
           int newIndex = a.size() - i -1;
           
           reversedChars[newIndex] = a.getChar(i);
        }
       
        return new AlphabetImpl(Arrays.asList(reversedChars));
    }
    
    /**
	 * Given an alphabet, the method returns a new alphabet with characters
	 * randomly shuffled.
	 * 
	 * @param alphabet
	 *            the source alphabet
	 * 
	 * @return a new alphabet containing the same characters as the source
	 *         alphabet but in a random order.
	 */
    public Alphabet randomizeAlphabet(Alphabet a) {
        
        Character[] randomizedChars = new Character[a.size()];
    
        Arrays.fill(randomizedChars, null);
        
        Random randomGenerator = new Random(); //TODO other random generator
        
        for (int i = 0; i < a.size(); i++) {
        	
        	int newIndex;
        	
        	do {
        		newIndex = randomGenerator.nextInt(a.size());
        	} while (randomizedChars[newIndex] != null);
        	
        	randomizedChars[newIndex] = a.getChar(i);
        }
        
        return new AlphabetImpl(Arrays.asList(randomizedChars));
    }
    
    /**
	 * transforms a text into a pretty printable format. This means that the
	 * text has in every line 6 space separated blocks with 10 characters each.
	 * 
	 * So
	 * 
	 * <pre>
	 * loremipsumdolorsitametconsecteturadipiscingelitvivamusquismassaestnuncelitelitdictumvelligulaiddapibuspretiumrisuscrasineuismodnisinu
	 * ncpharetradiamelitiaculishendreritnisitincidunteunullamfeugiatfermentumantequissuscipitestvehiculasitametnuncnonvehiculaenimduisatlib
	 * eroquisduidapibusfermentumuteuduinullamrutrumgravidadolorvelullamcorperleofermentumeuinliberovelitaccumsanvelpulvinarnecsagittisetmet
	 * usnullaanequevitaesemmalesuadaplaceratutegestasmetus
	 * </pre>
	 * 
	 * will be
	 * 
	 * <pre>
	 * loremipsum dolorsitam etconsecte turadipisc ingelitviv amusquisma
	 * ssaestnunc elitelitdi ctumvellig ulaiddapib uspretiumr isuscrasin 
	 * euismodnis inuncphare tradiameli tiaculishe ndreritnis itincidunt 
	 * eunullamfe ugiatferme ntumantequ issuscipit estvehicul asitametnu 
	 * ncnonvehic ulaenimdui satliberoq uisduidapi busferment umuteuduin 
	 * ullamrutru mgravidado lorvelulla mcorperleo fermentume uinliberov 
	 * elitaccums anvelpulvi narnecsagi ttisetmetu snullaaneq uevitaesem 
	 * malesuadap laceratute gestasmetu s
	 * </pre>
	 * 
	 * @param ciphertext
	 *            the text that should be pretty formated
	 * @return the pretty formatted text
	 */
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
		return result + blockstring;
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
	
	/**
	 * Checks if the given alphabets contain the same characters. This means
	 * they are a permutation of each other.
	 * 
	 * @param alphabet1
	 *            the first alphabet
	 * @param alphabet2
	 *            the second alphabet
	 * @return if the alphabets are a permutation of each other
	 */
	@Override
	public boolean containsSameCharacters(Alphabet alphabet1, Alphabet alphabet2) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
