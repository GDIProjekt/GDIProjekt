package de.tudarmstadt.gdi1.project.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;

/**
 * 
 * @author .., .., .., Laurin Strelow
 *
 */
public class AlphabetImplTest {
	
	Alphabet alph;
	Alphabet randomAlphabet;
	String validString;
	String invalidString;
	
	@Before
	public void setUp() throws Exception {
		
		
		alph = new AlphabetImpl(Arrays.asList('a', 'b', 'c'));
		validString = "abbabcabbcababc";
		invalidString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
				"Duis vel mi pulvinar, euismod lorem eget, viverra enim. Cras quis pharetra. ";
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testIterator() {
		
		int i = 0;
		
		for (Character c : alph) {
			assertTrue(alph.getChar(i) == c);
			i++;
		}
		
		assertEquals(alph.size(), i);
	}

	@Test
	public void testCharacterArray() {
		
		char[] array = alph.asCharArray();
		
		assertEquals(alph.getChar(0), array[0]);
		assertEquals(alph.getChar(1), array[1]);
		assertEquals(alph.getChar(2), array[2]);
	}
	
	
	@Test(expected = Exception.class)
	public void testTemplateAlphabetDuplicateEntry() {
		TemplateTestCore.getFactory().getAlphabetInstance(Arrays.asList('a', 'a'));
	}

	@Test
	public void testTemplateAlphabetPosition() {
		assertEquals(0, alph.getIndex('a'));
		assertEquals(1, alph.getIndex('b'));
		assertEquals(2, alph.getIndex('c'));
		assertEquals(-1, alph.getIndex('d'));
	}

	@Test
	public void testTemplateAlphabetSize() {
		assertEquals(3, alph.size());
	}

	@Test
	public void testTemplateAlphabetContains() {
		assertTrue(alph.contains('a'));
		assertTrue(alph.contains('b'));
		assertTrue(alph.contains('c'));
		assertFalse(alph.contains('d'));
	}

	@Test
	public void testTemplateAlphabetAllows() {
		assertTrue(alph.allows(validString));
		assertFalse(alph.allows(invalidString));
	}

	@Test
	public void testTemplateAlphabetNormalize() {
		String normalizedValidString = alph.normalize(validString);
		assertEquals(validString, normalizedValidString);
		String normalizedInvalidString = alph.normalize(invalidString);
		assertEquals("accacaaaaa", normalizedInvalidString);
	}

	@Test
	public void testTemplateAlphabetIterator() {
		Iterator<Character> alphIterator = alph.iterator();
		assertTrue(alphIterator.hasNext());
		assertEquals(new Character('a'), alphIterator.next());
		assertTrue(alphIterator.hasNext());
		assertEquals(new Character('b'), alphIterator.next());
		assertTrue(alphIterator.hasNext());
		assertEquals(new Character('c'), alphIterator.next());
		assertFalse(alphIterator.hasNext());
		assertEquals(3, alph.size());
	}
	
}
