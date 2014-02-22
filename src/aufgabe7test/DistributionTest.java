package aufgabe7test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;

public class DistributionTest {
	@Test
	public void testTemplateDistributionCharacterFrequency() {
		String s = "abcdefghijklmnopqrstuvwxyz";
		String t = "ggwellplayedcommendplease";

		Distribution distribution = TemplateTestCore.getFactory().getDistributionInstance(
				TemplateTestUtils.getDefaultAlphabet(), s, 1);
		Distribution distribution2 = TemplateTestCore.getFactory().getDistributionInstance(
				TemplateTestUtils.getDefaultAlphabet(), t, 1);

		for (char c : TemplateTestUtils.getDefaultAlphabet())
			assertEquals(1 / (double) 26, distribution.getFrequency("" + c), 0.001);
		
			assertEquals(5 / (double) 25, distribution2.getFrequency("e"), 0.001);
			assertEquals(2 / (double) 25, distribution2.getFrequency("g"), 0.001);
		
	}


	@Test
	public void testTemplateDistributionCharacterSorted() {
		String text = "aaabbccccd";

		Distribution d = TemplateTestCore.getFactory().getDistributionInstance(
				TemplateTestUtils.getDefaultAlphabet(), text, 1);

		List<String> sortedChars = d.getSorted(1);
		assertEquals("c", sortedChars.get(0));
		assertEquals("a", sortedChars.get(1));
		assertEquals("b", sortedChars.get(2));
		assertEquals("d", sortedChars.get(3));
	}

	@Test
	public void testDistributionIsNormalized() {
		Alphabet alphabet = TemplateTestUtils.getDefaultAlphabet();

		Distribution dis1 = TemplateTestCore.getFactory().getDistributionInstance(alphabet, TemplateTestUtils.ALICE_PLAIN, 2);
		Distribution dis2 = TemplateTestCore.getFactory().getDistributionInstance(alphabet,
				alphabet.normalize("äöü " + TemplateTestUtils.ALICE_PLAIN), 2);

		assertEquals(dis1.getSorted(1).size(), dis2.getSorted(1).size());
		assertEquals(dis1.getSorted(2).size(), dis2.getSorted(2).size());

		for (int i = 1; i <= dis1.getSorted(2).size(); i++)
			assertEquals(dis1.getByRank(2, i), dis2.getByRank(2, i));
	}

}
