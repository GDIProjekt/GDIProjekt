package de.tudarmstadt.gdi1.project.test.alphabet;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.alphabet.DistributionImpl;

/**
 * 
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class TestDistributionImpl {
	
	
	Alphabet simpleAlphabet;
	
	@Before
	public void setUp() throws Exception{
		simpleAlphabet = new AlphabetImpl(Arrays.asList('a', 'b', 'c'));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetSorted() {
		String text = "abcaba";
		Distribution simpleDistribution = new DistributionImpl(simpleAlphabet, text, 3);
		
		List<String> monogram = simpleDistribution.getSorted(1);
		assertEquals("a", monogram.get(0));
		assertEquals("b", monogram.get(1));
		assertEquals("c", monogram.get(2));
		
		List<String> bigram = simpleDistribution.getSorted(2);
		assertEquals("ab", bigram.get(0));
		//ab, bc, ca, ab, ba
		assertTrue(bigram.indexOf("bc") > 0);
		assertTrue(bigram.indexOf("ca") > 0);
		assertTrue(bigram.indexOf("ba") > 0);
		
		List<String> trigram = simpleDistribution.getSorted(3);
		//abc, bca, cab, aba
		assertTrue(trigram.indexOf("abc") >= 0);
		assertTrue(trigram.indexOf("bca") >= 0);
		assertTrue(trigram.indexOf("cab") >= 0);
		assertTrue(trigram.indexOf("aba") >= 0);
	}
	
	@Test
	public void testGetAlphabet() {
		Distribution simpleDistribution = new DistributionImpl(simpleAlphabet, "", 3);
		
		assertTrue(simpleAlphabet.equals(simpleDistribution.getAlphabet()));
	}

	@Test
	public void testGetFrequency() {
		String text = "";
		Distribution simpleDistribution = new DistributionImpl(simpleAlphabet, text, 3);

		//TODO
	}
	
	@Test
	public void testGetByRank() {
		String text = "";
		Distribution simpleDistribution = new DistributionImpl(simpleAlphabet, text, 3);
		
		//TODO
	}
	
}