package aufgabe61;

import kaese.Aufgabe52;

public class MonoalphabeticCipherImpl extends Aufgabe61{
	
	protected Aufgabe52 src;
	protected Aufgabe52 dest;	
		
	

    public MonoalphabeticCipherImpl (Aufgabe52 src, Aufgabe52 dest) {
        
        //Alphabete müssen gleich lang sein
        
        if (src.size() != dest.size())
            throw new AlphabetNotSameSizeException();
        
            this.src = src;
            this.dest = dest;
    }
	
	public char translate(char chr, int i){
		return src.getChar(dest.getIndex(chr));
	}
	
	public char reverseTranslate(char chr, int i){
		return dest.getChar(src.getIndex(chr));
	}
	
    public class AlphabetNotSameSizeException extends Exception {
    	
    	public AlphabetNotSameSizeException (){
        try{
        	
        }
        catch{
        	
        }
        finally{
        	
        }
    	}
    }
}
