package de.tudarmstadt.gdi1.project.alphabet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

import exceptions.doubleChars;

public class AlphabetImpl implements Alphabet{

	 private List<Character> list;
	 doubleChars dc = new doubleChars();
	 
	    /**
	    *Konstruktor für ein Alphabet
	    *@param chars Geordnete Liste der Alphabets
	    **/	
	    public AlphabetImpl (Collection <Character> chars) {
	    	
	        super();
	        list = new ArrayList<Character>();
	      
	        	for (Character c : chars) {
	        		if (!list.contains(c)) {
	        			list.add(c);
	        		}
	        		else {
	        			throw new IllegalArgumentException("Error: At least one character in given alphabet appears twice.");	        		
	        		}
	        	}
	       
	        }
	    
	    
	    public int getIndex(char chr) {
	    	if (list.contains(chr))
	            return list.indexOf(chr);
	    	else return -1;
	        }
	    
	    
	    public char getChar(int index) {
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
	    	char[] result = new char[list.size()];
	    	
	    	for (int i = 0; i<list.size(); i++){
	    		result[i] = list.get(i);
	    	}
	    	return result;
	    }
	    
	    
	    public Iterator<Character> iterator()  {
	    	return new AlphabetIterator(this);
	    }
	    
	    
	    class AlphabetIterator implements Iterator<Character>{
	    
	        int index;
	    
	        AlphabetImpl alphabet;
	    
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
	        }
	    
	    }
}