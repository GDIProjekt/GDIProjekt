package de.tudarmstadt.gdi1.project.test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;


public class TestUtilsImpl {

	Utils utils;
	Alphabet alph;
	
	@Before
	public void setUp() throws Exception {
		utils = new UtilsImpl();
		alph = new AlphabetImpl(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'));
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testShift() {
		
		Alphabet alphabet = new AlphabetImpl(Arrays.asList('a', 'b', 'c'));
		Alphabet oneShift = new AlphabetImpl(Arrays.asList('b', 'c', 'a'));
		Alphabet twoShift = new AlphabetImpl(Arrays.asList('c', 'a', 'b'));
		
		assertTrue(oneShift.equals(utils.shiftAlphabet(alphabet, 1)));
		assertTrue(twoShift.equals(utils.shiftAlphabet(alphabet, 2)));
	}
	
	@Test
	public void testDoubleShiftAlphabet() {
		
		for (int i = -300; i < 300; i++) {
			Alphabet shift = utils.shiftAlphabet(alph, i);
			assertTrue(alph.equals(utils.shiftAlphabet(shift, -i)));
		}
		
	}
	
	@Test
	public void testShiftAlphabetIdentity() {
		
		for (int i = 0; i < 200; i+=alph.size()) {
			assertTrue(alph.equals(utils.shiftAlphabet(alph, i)));
		}
		for (int i = 0; i > -200; i-=alph.size()) {
			assertTrue(alph.equals(utils.shiftAlphabet(alph, i)));
		}
	}
	
	@Test
	public void testReverseAlphabet() {
		Alphabet reverse = utils.reverseAlphabet(alph);
		
		assertEquals(alph.size(), reverse.size());
		
		for (int i = 0; i < alph.size(); i++)
			assertEquals(alph.getChar(i), reverse.getChar(alph.size()-i-1));
	}
	
	@Test
	public void testReverseAlphabetIdentity() {
		
		Alphabet reverse = utils.reverseAlphabet(alph);
		assertTrue(alph.equals(utils.reverseAlphabet(reverse)));
	}
	
	@Test
	public void testRandomAlphabet() {
		
		for (int i = 0; i < 20; i++) {
			Alphabet rand = utils.randomizeAlphabet(alph);
			
			assertFalse(alph.equals(rand));
		}
	}
	
	@Test
	public void testNgramSize() {
		
		String text = "Machines take me by surprise with great frequency.";
		
		List<String> expectedBigramList = Arrays.asList("Ma", "ac", "ch", "hi", "in", "ne", "es", "s ", " t", "ta", "ak", "ke", "e ",
		                                                 " m", "me", "e ", " b", "by", "y ", " s", "su", "ur", "rp", "pr", "ri", "is",
		                                                 "se", "e ", " w", "wi", "it", "th", "h ", " g", "gr", "re", "ea", "at", "t ",
		                                                 " f", "fr", "re", "eq", "qu", "ue", "en", "nc", "cy", "y.");
		List<String> expectedTrigramList = Arrays.asList("Mac", "ach", "chi", "hin", "ine", "nes", "es ", "s t", " ta", "tak", "ake", "ke ", "e m", " me", "me ", "e b", " by", "by ", "y s", " su", "sur", "urp", "rpr", "pri", "ris", "ise", "se ", "e w", " wi", "wit", "ith", "th ", "h g", " gr", "gre", "rea", "eat", "at ", "t f", " fr", "fre", "req", "equ", "que", "uen", "enc", "ncy", "cy.");
		
		Map<Integer,List<String>>map = utils.ngramize(text, 2,3);
		
		List<String> bigramList = map.get(2);
		List<String> trigramList = map.get(3);
		
		assertTrue(expectedBigramList.equals(bigramList));
		assertTrue(expectedTrigramList.equals(trigramList));
	}
	
	@Test
	public void testNgramSizeSimple() {
		
		String text = "aaaaa";
		
		Map<Integer,List<String>>map = utils.ngramize(text, 1, 2, 3, 4, 5, 6);
		
		for (int i = 1; i <= 6; i++) {
			List<String> list = map.get(i);
			
			assertEquals(6-i, list.size());
			
			String expected = "";
			for (int j = 6-i; j < 6; j++) {
				expected += "a";
			}
			
			for (String s : list) {
				assertTrue(expected.equals(s));
			}
 		}
	}
	
	@Test
	public void testToDisplay() {
		
	}
	
}
