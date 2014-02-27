package de.tudarmstadt.gdi1.project.test.alphabet;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;

/**
 * Testet die Alphabet Implementierung.
 * @author .., .., .., Laurin Strelow
 *
 */
public class TestAlphabetImpl {
	

	List<Character> chars;
	
	String validString;
	String invalidString;
	
	@Before
	public void setUp() throws Exception {
		
		chars = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');
		
		validString = "abcdefghhgfedcdba";
		invalidString = "xyzabcqwdxefglhlujohgvkpfyedxcdzbaxyz";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		
		Alphabet alph = new AlphabetImpl(chars); 
		
		assertEquals('a', alph.getChar(0));
		assertEquals('b', alph.getChar(1));
		assertEquals('c', alph.getChar(2));
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructorNull() {
		new AlphabetImpl(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorExecption() {
		List<Character> l = new ArrayList<Character>(chars);
		l.add('a');
		new AlphabetImpl(l);
	}

	@Test
	public void testGetIndex() {
		Alphabet alph = new AlphabetImpl(chars);
		
		for (int i = 0; i < chars.size(); i++) {
			char c = chars.get(i);
			
			assertEquals(c, alph.getChar(i));
			assertEquals(i, alph.getIndex(c));
		}
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testOutOfBoundsTooLow() {
		Alphabet alph = new AlphabetImpl(chars); 
		alph.getChar(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testOutOfBoundTooHigh() {
		Alphabet alph = new AlphabetImpl(chars); 
		alph.getChar(chars.size());
	}
	
	@Test
	public void testSize() {
		
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i = 0; i < 20; i++) {
			
			Alphabet temp = new AlphabetImpl(list);
			assertEquals(i, temp.size());	
			list.add((char) i);
		}
	}
	

	
	@Test
	public void testContains() {
		
		Alphabet alph = new AlphabetImpl(chars);
		
		for (char c : chars) {
			assertTrue(alph.contains(c));
		}
		
		assertFalse(alph.contains('x'));
		assertFalse(alph.contains('y'));
		assertFalse(alph.contains('z'));
	}
	
	@Test
	public void testAllows() {
		
		Alphabet alph = new AlphabetImpl(chars);
		
		assertTrue(alph.allows(validString));
		assertFalse(alph.allows(invalidString));
		
	}
	
	@Test
	public void testNormalize() {
		
		Alphabet alph = new AlphabetImpl(chars);
		
		String normalized = alph.normalize(invalidString);
		
		assertTrue(alph.allows(normalized));
		assertEquals(validString, normalized);
	}
	
	@Test
	public void testIterator() {
		Alphabet alph = new AlphabetImpl(chars); 
		int i = 0;
		
		for (Character c : alph) {
			assertTrue(alph.getChar(i) == c);
			i++;
		}
		
		assertEquals(alph.size(), i);
		
		Alphabet alph2 = new AlphabetImpl(new ArrayList<Character>());
		
		i = 0;
		for (Character c: alph2) {
			i++;
		}
		assertEquals(0, i);
		
	}
	
	@Test
	public void testCharacterArray() {
		Alphabet alph = new AlphabetImpl(chars); 
		char[] array = alph.asCharArray();
		
		for (int i = 0; i < array.length; i++)
			assertEquals(alph.getChar(i), array[i]);
	}
	
	@Test
	public void testEquals() {
		Alphabet alph1 = new AlphabetImpl(chars);
		Alphabet alph2 = new AlphabetImpl(chars);
		
		assertEquals(alph1, alph2);
	}
	
	@Test
	public void testNotEquals() {
		Alphabet alph1 = new AlphabetImpl(chars);
		Alphabet alph2 = new AlphabetImpl(Arrays.asList('x', 'y', 'z'));
		Alphabet alph3 = new AlphabetImpl(Arrays.asList('a', 'v', 'w'));
		
		assertFalse(alph1.equals(alph2));
		assertFalse(alph2.equals(alph3));	
	}
}
