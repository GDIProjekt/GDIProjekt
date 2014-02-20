package aufgabe5;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Eine Implementierung f�r das Alphabet Interface.
 * 
 * @author ....,..,..., Laurin Strelow
 *
 */
public class AlphabetImpl implements Alphabet{

	 private ArrayList<Character> list;
	 
	    /**
	    *Konstruktor fuer ein Alphabet
	    *@param chars Eine Collection in der die Buchstaben als Character �bergeben werden.
	    **/
	    public AlphabetImpl (Collection <Character> chars) {
	    	
	        super();
	        list = new ArrayList<Character>();
	        
	        for (Character c : chars) {
	            if (list.contains(c)) 
	              throws new IllegalArgumentException("Character only once in Alphabet....");
	        }
	    }
	    
	    public int getIndex(char chr) {
	    	int result = list.indexOf(chr);
	    	if (result == -1)
	    		throws new IllegalArgumentExecption("Charcter is not in Alphabet");
	    	else 
	    		return result;
	    }
	    
	    
	    public char getChar(int index) {
	    	
	    	//throws automatically a index out of bounds exception if index is >= size() or < 0
	        return  list.get(index);
	    }
	    
	    public int size() {
	            return list.size();
	    }
	    
	    public boolean contains(char chr) {
	            return list.contains(chr);
	    }
	    
	    public boolean allows(String word) {	       
	            char[] chars = word.toCharArray();
	            
	            for (char c : chars) {
	                if (!contains(c))
	                    return false;
	            }
	            return true;
	    }
	    
	     public String normalize(String input)  {
	            char[] chars = input.toCharArray();	            
	            String result = "";
	            
	            for (char c : chars) {
	                if (contains(c)) {
	                    
	                    result += c;
	                }
	            }     
	            
	            return result;
	    }
	 
	    public char[] asCharArray() {
	        
	    	char[] result = new char[size()];
	    	
	    	int i = 0;
	    	for (Character c : list) {
	    		result[i] = getChar(i);
	    		
	    		i++;
	    	}
	    	
	    	return result;
	    }
	    
	    
	    public Iterator<Character> iterator()  {
	    	return new AlphabetIterator(this);
	    }
	    
	    /**
	     * Klasse f�r die Implementierug des Iterator Infterfaces, wird gebraucht f�r das Iterable Interface vom Alphabet.
	     * @author ..., .., .., Laurin Strelow
	     *
	     */
	    private class AlphabetIterator implements Iterator<Character>{
	    
	        int index;
	    
	        AlphabetImpl alphabet;
	    
	        /**
	         * Konstruktor f�r den Alphabet Iterator
	         * @param alphabet Das AlphabetImpl �ber das iteriert werden soll.
	         */
	        public AlphabetIterator(AlphabetImpl alphabet) {
	           this.alphabet = alphabet;
	           index = 0;
	           
	        }
	    
	        @Override
	        public boolean hasNext() {
	            return (index < alphabet.size());
	        }
	    
	        @Override
	        public Character next() {
	            
	            if (index < alphabet.size())
	                return alphabet.getChar(index++);
	            else
	                return null;
	        }
	    
	        @Override
	        public void remove() {
	        	//TODO ???
	        }
	    
	    }
}
