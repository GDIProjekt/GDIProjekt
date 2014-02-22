package de.tudarmstadt.gdi1.project.test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;

public class AlphabetImplTest {

	AlphabetImpl alphabet;
	
	@Before
	public void setUp() throws Exception {
		
		LinkedList<Character> list = new LinkedList<Character>();
		
		list.add('a');
		list.add('b');
		list.add('c');
		
		alphabet = new AlphabetImpl(list);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void iteratorTest() {
		
		int i = 0;
		
		for (Character c : alphabet) {
			
			assertTrue(alphabet.getChar(i) == c);
			
			i++;
		}
		
		assertEquals(alphabet.size(), i);
	}

}
