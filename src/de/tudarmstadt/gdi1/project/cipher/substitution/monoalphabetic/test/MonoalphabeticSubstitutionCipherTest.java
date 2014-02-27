package de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import de.tudarmstadt.gdi1.project.Factory;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;

public class MonoalphabeticSubstitutionCipherTest {
	private final Factory f = TemplateTestCore.getFactory();

	@Test (expected = Exception.class)
	public void testMonoalphabeticCipherCharNotAllowed(){
		MonoalphabeticCipher c = f.getMonoalphabeticCipherInstance(f.getAlphabetInstance(Arrays.asList('a', 'b', 'c')), 
				f.getAlphabetInstance(Arrays.asList('x', 'y', 'z')));
		c.encrypt("hi");
	}
	
	@Test
	public void testTemplateMonoalphabeticCipherIdentity2() {
		MonoalphabeticCipher c = f.getMonoalphabeticCipherInstance(f.getAlphabetInstance(Arrays.asList('?', 'd', '!')),
				f.getAlphabetInstance(Arrays.asList('!', '?', 'd')));
		assertEquals("!?d", c.encrypt("?d!"));
		assertEquals("?d!", c.decrypt("!?d"));
	}
	
	@Test
	public void testTemplateMonoalphabeticCipherIdentity() {
		MonoalphabeticCipher c = f.getMonoalphabeticCipherInstance(TemplateTestUtils.getDefaultAlphabet(),
				TemplateTestUtils.getDefaultAlphabet());
		assertEquals("hallowelt", c.encrypt("hallowelt"));
		assertEquals("hallowelt", c.decrypt("hallowelt"));
	}

	@Test
	public void testTemplateMonoalphabeticCipherSmallAlphabet() {
		MonoalphabeticCipher c = f.getMonoalphabeticCipherInstance(f.getAlphabetInstance(Arrays.asList('a', 'b', 'c')),
				f.getAlphabetInstance(Arrays.asList('x', 'y', 'z')));

		assertEquals("xyz", c.encrypt("abc"));
		assertEquals("xxx", c.encrypt("aaa"));
		assertEquals("cba", c.decrypt("zyx"));
		assertEquals("bbb", c.decrypt("yyy"));
		
	}

	@Test
	public void testTemplateMonoalphabeticCipherReflexivity() {
		MonoalphabeticCipher c = f.getMonoalphabeticCipherInstance(TemplateTestUtils.getDefaultAlphabet(),
				f.getAlphabetInstance(Arrays.asList('s', 'b', 'i', 'u', 'r', 'f', 't', 'h', 'c', 'q', 'l', 'k', 'w', 'p', 'o', 'n', 'j',
						'e', 'a', 'g', 'd', 'v', 'm', 'x', 'z', 'y')));
		assertEquals("helloworld", c.decrypt(c.encrypt("helloworld")));
	}
	
	@Test
	public void testTemplateMonoalphabeticCipherReflexivity2() {
		MonoalphabeticCipher c = f.getMonoalphabeticCipherInstance(f.getAlphabetInstance(Arrays.asList('a', 'b', 'c')),
				f.getAlphabetInstance(Arrays.asList('x', 'y', 'z')));
		assertEquals("abc", c.decrypt(c.encrypt("abc")));
	}

}
