package de.tudarmstadt.gdi1.project.test.cipher;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.*;
import de.tudarmstadt.gdi1.project.cipher.monoalphabetic.MonoalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;

/**
 * 
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class TestMonoalphabeticCipherImpl {

	
	Alphabet plainAlphabet;
	Alphabet cipherAlphabet;
	
	@Before
	public void setUp() throws Exception {
		plainAlphabet = new AlphabetImpl(Arrays.asList('a', 'b', 'c', 'd'));
		cipherAlphabet = new AlphabetImpl(Arrays.asList('b', 'c', 'd', 'a'));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorNullFirst() {
		new MonoalphabeticCipherImpl(null, cipherAlphabet);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorNullSecond() {
		new MonoalphabeticCipherImpl(plainAlphabet, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotSameSize() {
		
		Alphabet tooShort = new AlphabetImpl(Arrays.asList('a'));
		new MonoalphabeticCipherImpl(tooShort, plainAlphabet);
	}
	
	@Test
	public void testIdentity() {
		
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(plainAlphabet, plainAlphabet);	
		for (char c : plainAlphabet) {
			
			assertEquals(c, cipher.translate(c, 0));
			assertEquals(c, cipher.reverseTranslate(c, 0));
		}
	}
	
	@Test
	public void testTranslate() {
		
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(plainAlphabet, cipherAlphabet);
		for (int i = 0; i < plainAlphabet.size(); i++) {
			assertEquals(cipherAlphabet.getChar(i), cipher.translate(plainAlphabet.getChar(i), 0));
			assertEquals(plainAlphabet.getChar(i), cipher.reverseTranslate(cipherAlphabet.getChar(i), i));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTranslateException() {
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(plainAlphabet, cipherAlphabet);
		cipher.translate('x', 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testReverseTranslateException() {
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(plainAlphabet, cipherAlphabet);
		cipher.reverseTranslate('y', 0);		
	}
	
	
	@Test
	public void testEncryptDecypt() {
		
		MonoalphabeticCipher cipher = new MonoalphabeticCipherImpl(plainAlphabet, cipherAlphabet);
		String plainText = "abcddcba";
		
		String cipherText = cipher.encrypt(plainText);
		
		assertEquals(plainText, cipher.decrypt(cipherText));
		
	}

}
