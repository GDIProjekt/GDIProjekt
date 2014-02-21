package aufgabe61;


import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.*;
import de.tudarmstadt.gdi1.project.utils.Utils;

public class CaesarImpl extends MonoalphabeticCipherImpl implements Caesar{
	
	public CaesarImpl(int key, Alphabet src) {
		super(src, createDestAlphabet(src, key));
	}
	
	private static Alphabet createDestAlphabet(Alphabet src, int key) {
		Utils util = new UtilsImpl();
		
		return util.shiftAlphabet(src, key);
	}
	
}
