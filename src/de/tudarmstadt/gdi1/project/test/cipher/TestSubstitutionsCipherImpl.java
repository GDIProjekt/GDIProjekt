package de.tudarmstadt.gdi1.project.test.cipher;

import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipherImpl;

/**
 * Testet die abstrakte Implementierung des SubstitutionsCipher Interfaces.
 * @author .., .., .., Laurin Strelow
 *
 */
public class TestSubstitutionsCipherImpl {

	SubstitutionCipherImpl mockedCipher;
	
	@Before
	public void setUp() throws Exception {
		mockedCipher = mock(SubstitutionCipherImpl.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNever() {
	
		String cipher = mockedCipher.decrypt("");
		String plain = mockedCipher.encrypt("");
		
		assertNotNull(cipher);
		assertNotNull(plain);
		
		assertEquals(0, cipher.length());
		assertEquals(0, plain.length());
		
		verify(mockedCipher, never()).translate(anyChar(), anyInt());
		verify(mockedCipher, never()).reverseTranslate(anyChar(), anyInt());
	}
	
	@Test
	public void testSingleTime() {
		
		mockedCipher.encrypt("a");
		mockedCipher.decrypt("b");
		
		verify(mockedCipher, times(1)).translate('a', 0);
		verify(mockedCipher, times(1)).translate(anyChar(), anyInt());
		
		verify(mockedCipher, times(1)).reverseTranslate('b', 0);
		verify(mockedCipher, times(1)).reverseTranslate(anyChar(), anyInt());
		
		verifyNoMoreInteractions(mockedCipher);
	}
	
	@Test
	public void testInOrder() {
		
		mockedCipher.encrypt("abc");
		mockedCipher.decrypt("abc");
		
		InOrder inOrder = inOrder(mockedCipher);
		
		inOrder.verify(mockedCipher).translate('a', 0);
		inOrder.verify(mockedCipher).translate('b', 1);
		inOrder.verify(mockedCipher).translate('c', 2);
		
		inOrder.verify(mockedCipher).reverseTranslate('a', 0);
		inOrder.verify(mockedCipher).reverseTranslate('b', 1);
		inOrder.verify(mockedCipher).reverseTranslate('c', 2);
	}

	@Test
	public void testEncryption() {
		
		mockedCipher.encrypt("abbccc");
		
		verify(mockedCipher, times(1)).translate(eq('a'), anyInt());
		verify(mockedCipher, times(2)).translate(eq('b'), anyInt());
		verify(mockedCipher, times(3)).translate(eq('c'), anyInt());
		verify(mockedCipher, times(6)).translate(anyChar(), anyInt());
		
		verify(mockedCipher, never()).reverseTranslate(anyChar(), anyInt());
	}
	
	@Test
	public void testDecryption() {
		
		mockedCipher.decrypt("aaabbc");
		
		verify(mockedCipher, times(3)).reverseTranslate(eq('a'), anyInt());
		verify(mockedCipher, times(2)).reverseTranslate(eq('b'), anyInt());
		verify(mockedCipher, times(1)).reverseTranslate(eq('c'), anyInt());
		verify(mockedCipher, times(6)).reverseTranslate(anyChar(), anyInt());
		
		verify(mockedCipher, never()).translate(anyChar(), anyInt());
	}
	
	@Test
	public void testDecryptionEncryption() {
		
		when(mockedCipher.translate(eq('a'), anyInt())).thenReturn('b');
		when(mockedCipher.translate(eq('b'), anyInt())).thenReturn('c');
		when(mockedCipher.translate(eq('c'), anyInt())).thenReturn('a');
		
		when(mockedCipher.reverseTranslate(eq('a'), anyInt())).thenReturn('c');
		when(mockedCipher.reverseTranslate(eq('b'), anyInt())).thenReturn('a');
		when(mockedCipher.reverseTranslate(eq('c'), anyInt())).thenReturn('b');
		
		String plainText = "aabbccbcabacab";
		String cipherText = "bbccaacabcbabc";
		
		String mockedCipherText = mockedCipher.encrypt(plainText);
		assertEquals(cipherText, mockedCipherText);
		
		String mockedPlainText = mockedCipher.decrypt(cipherText);
		assertEquals(plainText, mockedPlainText);
		
		for (int i = 0; i < plainText.length(); i++) {
			
			verify(mockedCipher, times(1)).translate(plainText.charAt(i), i);
			verify(mockedCipher, times(1)).reverseTranslate(cipherText.charAt(i), i);
		}
		
		verify(mockedCipher, times(plainText.length())).translate(anyChar(), anyInt());
		verify(mockedCipher, times(cipherText.length())).reverseTranslate(anyChar(), anyInt());
		
		verifyNoMoreInteractions(mockedCipher);
	}
}
