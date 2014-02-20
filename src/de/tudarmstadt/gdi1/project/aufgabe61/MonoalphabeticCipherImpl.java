package aufgabe61;

import aufgabe5.AlphabetImpl;

public class MonoalphabeticCipherImpl extends SubstitutionCipherImpl{
	
	protected AlphabetImpl src;
	protected AlphabetImpl dest;	
		
	

    public MonoalphabeticCipherImpl (AlphabetImpl src, AlphabetImpl dest) {
        
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
