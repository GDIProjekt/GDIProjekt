package kaese;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Aufgabe52 implements Alphabet{
	
	 private ArrayList<Character> list;
	    /**
	    *Konstruktor für ein Alphabet
	    *@param chars Geordnete Liste der Alphabets
	    **/
	    public Aufgabe52 (Collection <Character> chars) {
	    	
	        super();
	        list = new ArrayList<Character>();
	        
	        for (Character c : chars) {
	            if (!list.contains(c)) {
	              list.add(c);
	        }
	            
	            
	        }
	    }
	    public int getIndex(char chr) {
	            return list.indexOf(chr);
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
	        return null;
	    }
	    
	    
	    public Iterator<Character> iterator()  {
	    	return new AlphabetIterator(this);
	    }
	    
	    
	    class AlphabetIterator implements Iterator<Character>{
	    
	        int index;
	    
	        Aufgabe52 alphabet;
	    
	        public AlphabetIterator(Aufgabe52 alphabet) {
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
