package exceptions;

public class doubleChars extends Exception{
	 public doubleChars (){
	        
 	}
 	
 	public doubleChars(String message){
 		super(message);
 	}
 	
 	public doubleChars (Throwable cause){
 		super(cause);
 	}
 	
 	public doubleChars (String message, Throwable cause){
 		super (message, cause);
 	}
 
}
