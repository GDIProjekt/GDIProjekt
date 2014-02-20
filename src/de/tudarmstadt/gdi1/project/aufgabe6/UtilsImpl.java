

/**
 * Implementierung des Utils Interfaces.
 * @author .., .., .., Laurin Strelow
 *
 */
public class UtilsImpl extends utils.Utils {

    public Alphabet shiftAlphabet(Alphabet a, int k) {
        
    	Character[] shiftedChars = new Character[a.size()];
    	
        if (k %a.size() == 0) 
        	return a; //throws new IllegalArgumentException("Shift results in same Alphabet");
    
        for (int i = 0; i < a.size(); i++) {
            int newIndex = (i-k)%a.size();   
            shiftedChars[newIndex] = a.getChar(i);
        }
        return AlphabetImpl(Array.asList(shiftedChars));
    }
	
    public Alphabet reverseAlphabet() {
        
    	Character[] reversedChars = new Character[a.size()];
        for (int i = 0; i < a.size(); i++) {
           
           int newIndex = a.size() - i;
           
           reversedChars[newIndex] = a.getChar(i);
        }
       
        return AlphabetImpl(Array.asList(reversedChars));
    }
    
    public Alphabet randomizeAlphabet() {
        
        Character[] randomizedChars = new Chrarcter[a.size()];
    
        Arrays.fill(randomizedChars, null);
        
        Random randomGenerator = new Random();
        
        for (int i = 0; i < a.size(); i++) {
        	
        	int newIndex;
        	
        	do {
        		newIndex = r.nextInt(a.size());
        	} while (randomizedChars[newIndex] != null);
        	
        	randomizedChars[newIndex] = a.getChar(i);
        }
        
        return AlphabetImpl(Array.asList(randomizedChars));
    }
    
}
