package de.tudarmstadt.gdi1.project.test.cipher.enigma;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import de.tudarmstadt.gdi1.project.Factory;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.Enigma;
import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;

/**
 * Testet die Enigma Implmentierung
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class TestEnigma {
	private Factory f = TemplateTestCore.getFactory();

	@Test
	public void twoRotorsTest(){
		Alphabet rotor1Alphabet = f.getAlphabetInstance(Arrays.asList(
				'b', 'c', 'a', 'd'));
		Alphabet rotor2Alphabet = f.getAlphabetInstance(Arrays.asList('c', 'b', 'd', 'a'));
		
		Rotor r1 = f.getRotorInstance(TemplateTestUtils.getMinimalAlphabet(), rotor1Alphabet, 0);
		Rotor r2 = f.getRotorInstance(TemplateTestUtils.getMinimalAlphabet(), rotor2Alphabet, 0);
		List<Rotor> rotorList = new LinkedList<>();
		rotorList.add(r1);
		rotorList.add(r2);
		PinBoard pb = f.getPinBoardInstance(TemplateTestUtils.getMinimalAlphabet(), TemplateTestUtils.getMinimalAlphabet());
		ReverseRotor rr = f.getReverseRotatorInstance(TemplateTestUtils.getMinimalAlphabet(), TemplateTestUtils.getReversedMinimalAlphabet());
		Enigma e = f.getEnigmaInstance(rotorList, pb, rr);
		assertEquals(e.encrypt("abcdabcd"), "ccaadabc");
	}
}
