package src.de.tudarmstadt.gdi1.project.cipher.enigma.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import de.tudarmstadt.gdi1.project.Factory;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;

public class PinboardTest {
	private final Factory f = TemplateTestCore.getFactory();
	
	@Test
	public void testTranslate() {
		Alphabet a = TemplateTestUtils.getDefaultAlphabet();
		Alphabet b = TemplateTestUtils.getReversedDefaultAlphabet();
		Alphabet c = TemplateTestUtils.getMinimalAlphabet();
		Alphabet d = f.getAlphabetInstance(Arrays.asList('a', 'c', 'b', 'd'));
		PinBoard pb1 = f.getPinBoardInstance(a, b);
		PinBoard pb2 = f.getPinBoardInstance(c, d);

		for (int i = 0; i < a.size(); i++) {
			assertEquals(b.getChar(i), pb1.translate(a.getChar(i)));
		}
		assertEquals('a', pb2.translate('a'));
		assertEquals('c', pb2.translate('b'));
		assertEquals('b', pb2.translate('c'));
		assertEquals('d', pb2.translate('d'));
	}
	
	@SuppressWarnings("unused")
	@Test (expected = Exception.class)
	public void testErrors(){
		Alphabet a = f.getAlphabetInstance(Arrays.asList('a','b', 'c', 'd'));	
		Alphabet b = f.getAlphabetInstance(Arrays.asList('b','c', 'a', 'd'));
		
		// da das erste source-Zeichen das dritte dest-Zeichen ist abgebildet wird müsste das erste dest-Zeichen
		// auf das dritte source-Zeichen abbilden, was es nicht tut, weshalb ein Fehler beim Instanzeren der PinBoard zu erwarten ist
		PinBoard pb1 = f.getPinBoardInstance(a, b);
		
	}
}
