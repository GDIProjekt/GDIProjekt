package de.tudarmstadt.gdi1.project.alphabet;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Eine Implementierung fuer das Alphabet Interface.
 * 
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class AlphabetImpl implements Alphabet{

	 private ArrayList<Character> list;
	 
	    /**
	    * Konstruktor fuer ein Alphabet
	    * @param chars Eine Collection von Character, kein Character darf doppelt vorkommen.
	    **/
	    public AlphabetImpl (Collection <Character> chars) {
	        super();
	        
	        list = new ArrayList<Character>();
	        
	        for (Character c : chars) {
	            if (list.contains(c)) 
	              throw new IllegalArgumentException("Character only once in Alphabet....");
	            else
	            	list.add(c);
	        }
	    }
	    
	    @Override
	    public int getIndex(char chr) {
	    	return list.indexOf(chr);
	    }
	    
	    @Override
	    public char getChar(int index) {
	    	//throws automatically a index out of bounds exception if index is >= size() or < 0
	        return  list.get(index);
	    }
	    
	    @Override
	    public int size() {
	            return list.size();
	    }
	    
	    @Override
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
	    
	    @Override
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
	 
	    @Override
	    public char[] asCharArray() {
	        
	    	char[] result = new char[list.size()];
	    	
	    	int i = 0;
	    	
	    	for (i = 0; i < list.size(); i++) {
	    		result[i] = getChar(i);
	    	}
	    	
	    	return result;
	    }
	    
	    @Override
	    public boolean equals(Object obj) {
	    	
	    	if (obj instanceof Alphabet) {
	    		Alphabet alphabet = (Alphabet) obj;
	    		if (alphabet.size() != size())
	    			return false;
	    		
	    		for (int i = 0; i < size(); i++) {
	    			if (alphabet.getChar(i) != getChar(i))
	    				return false;
	    		}
	    		return true;
	    	} else {
	    		return super.equals(obj);
	    	}
	    }
	    
	    @Override
	    public Iterator<Character> iterator()  {
	    	return new AlphabetIterator(this);
	    }
	    
	    /**
	     * Klasse fuer die Implementierug des Iterator Infterfaces, wird gebraucht fuer das Iterable Interface vom Alphabet.
	     * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
	     *
	     */
	    private class AlphabetIterator implements Iterator<Character>{
	    
	        private int index;
	    
	        private AlphabetImpl alphabet;
	    
	        /**
	         * Konstruktor fuer den Alphabet Iterator
	         * @param alphabet Das AlphabetImpl ueber das iteriert werden soll.
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
	        	throw new UnsupportedOperationException();
	        	/**Man kann den Inhalt des Alphabets nachtraeglich nicht mehr aendern, daher die Exception*/
	        }
	    
	    }
}
