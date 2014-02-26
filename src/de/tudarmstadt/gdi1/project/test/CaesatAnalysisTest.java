package de.tudarmstadt.gdi1.project.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.alphabet.DistributionImpl;
import de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.ceaser.CaesarCryptanalysisImpl;
import de.tudarmstadt.gdi1.project.cipher.monoalphabetic.CaesarImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.Caesar;

public class CaesatAnalysisTest {

	Alphabet defaultAlphabet;
	Alphabet smallAlphabet;
	
	@Before
	public void setUp() throws Exception {
		defaultAlphabet = TemplateTestUtils.getDefaultAlphabet();
		smallAlphabet = TemplateTestUtils.getMinimalAlphabet();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void simpleTest() {
		
		String plainText ="Lange war der Begriff Kryptographie gleichbedeutend mit Verschlüsselung, der Umwandlung einer Information von einem lesbaren Zustand (Klartext) in scheinbaren Unsinn (Geheimtext). Entschlüsselung bedeutet das Gegenteil, also das Umwandeln eines Geheimtextes in einen verständlichen Klartext. Eine Chiffre bezeichnet hierbei eine Methode zum Ver- oder Entschlüsseln. Das detaillierte Vorgehen einer Chiffre wird in jedem Schritt von sowohl dem Algorithmus und dem Schlüssel kontrolliert. Letzterer ist ein geheimer Parameter, der idealerweise nur den kommunizierenden Parteien bekannt ist, er wird speziell für einen Ver- und Entschlüsselungsvorgang gewählt. Als Kryptosystem wird die Gesamtheit aller möglichen Elemente, wie Klartexte, Geheimtexte, Schlüssel und Verschlüsselungsalgorithmen bezeichnet, die zusammen das System ergeben. Schlüssel sind wichtig, da Chiffren ohne variable Schlüssel leicht gebrochen werden können, auch wenn nur der Geheimtext bekannt ist. Solche Verschlüsselungsmethoden sind daher nutzlos für die meisten Zwecke. In der Geschichte wurden Chiffren oft direkt zum Verschlüsseln genutzt, ohne zusätzliche Verfahren, wie z. B. Authentifizierung oder Integritätsprüfung.Im umgangssprachlichen Gebrauch kann der Begriff Code sowohl eine Verschlüsselungsmethode als auch die Geheimhaltung einer Bedeutung bezeichnen. Jedoch ist er in der Kryptographie spezifischer definiert und bedeutet hier, dass ein Teil eines Klartextes durch ein bestimmtes Codewort ersetzt wird (Beispiel: Igel ersetzt Angriff in der Dämmerung). Codes werden in der heutigen Kryptographie weitestgehend nicht mehr benutzt, außer bei einzelnen Bezeichnungen (z. B. Operation Overlord), da gut gewählte Chiffren sowohl praktischer als auch sicherer als die besten Codes und darüber hinaus auch besser an Computer angepasst sind.";
		plainText = defaultAlphabet.normalize(plainText);
		
		Distribution distribution = new DistributionImpl(defaultAlphabet, plainText, 1);
		
		final int key = 15;
		
		Caesar caeser = new CaesarImpl(defaultAlphabet, key);
		String cipherText = caeser.encrypt(plainText);
		
		CaesarCryptanalysis crypt = new CaesarCryptanalysisImpl();
		
		int resolvedKey = crypt.knownCiphertextAttack(cipherText, distribution);
		
		assertEquals(key, resolvedKey);
	}

	@Test
	public void testStandartDistribution() {
		
		String plainText ="Lange war der Begriff Kryptographie gleichbedeutend mit Verschlüsselung, der Umwandlung einer Information von einem lesbaren Zustand (Klartext) in scheinbaren Unsinn (Geheimtext). Entschlüsselung bedeutet das Gegenteil, also das Umwandeln eines Geheimtextes in einen verständlichen Klartext. Eine Chiffre bezeichnet hierbei eine Methode zum Ver- oder Entschlüsseln. Das detaillierte Vorgehen einer Chiffre wird in jedem Schritt von sowohl dem Algorithmus und dem Schlüssel kontrolliert. Letzterer ist ein geheimer Parameter, der idealerweise nur den kommunizierenden Parteien bekannt ist, er wird speziell für einen Ver- und Entschlüsselungsvorgang gewählt. Als Kryptosystem wird die Gesamtheit aller möglichen Elemente, wie Klartexte, Geheimtexte, Schlüssel und Verschlüsselungsalgorithmen bezeichnet, die zusammen das System ergeben. Schlüssel sind wichtig, da Chiffren ohne variable Schlüssel leicht gebrochen werden können, auch wenn nur der Geheimtext bekannt ist. Solche Verschlüsselungsmethoden sind daher nutzlos für die meisten Zwecke. In der Geschichte wurden Chiffren oft direkt zum Verschlüsseln genutzt, ohne zusätzliche Verfahren, wie z. B. Authentifizierung oder Integritätsprüfung.Im umgangssprachlichen Gebrauch kann der Begriff Code sowohl eine Verschlüsselungsmethode als auch die Geheimhaltung einer Bedeutung bezeichnen. Jedoch ist er in der Kryptographie spezifischer definiert und bedeutet hier, dass ein Teil eines Klartextes durch ein bestimmtes Codewort ersetzt wird (Beispiel: Igel ersetzt Angriff in der Dämmerung). Codes werden in der heutigen Kryptographie weitestgehend nicht mehr benutzt, außer bei einzelnen Bezeichnungen (z. B. Operation Overlord), da gut gewählte Chiffren sowohl praktischer als auch sicherer als die besten Codes und darüber hinaus auch besser an Computer angepasst sind.";
		plainText = defaultAlphabet.normalize(plainText);
		
		Distribution standartDistribution = new StandartDistribution();
		
		final int key = 9;
		
		Caesar caeser = new CaesarImpl(defaultAlphabet, key);
		String cipherText = caeser.encrypt(plainText);
		
		CaesarCryptanalysis crypt = new CaesarCryptanalysisImpl();
		
		int resolvedKey = crypt.knownCiphertextAttack(cipherText, standartDistribution);
		
		assertEquals(key, resolvedKey);
	}
	
	@Test
	public void testStandartDistributionSmallText() {
		
		String plainText = "Der patrizischen Familie der Julier entstammend";
		plainText = defaultAlphabet.normalize(plainText);
		
		Distribution standartDistribution = new StandartDistribution();
		
		final int key = 6;
		
		Caesar caeser = new CaesarImpl(defaultAlphabet, key);
		String cipherText = caeser.encrypt(plainText);
		
		CaesarCryptanalysis crypt = new CaesarCryptanalysisImpl();
		
		int resolvedKey = crypt.knownCiphertextAttack(cipherText, standartDistribution);
		
		assertEquals(key, resolvedKey);		
		
	}
	
	
	private class StandartDistribution implements Distribution {

		HashMap<String, Double> map = new HashMap<String, Double>();
		
		public StandartDistribution() {
			map = new HashMap<String, Double>();
			
			map.put("a", 6.5);
			map.put("b", 1.9);
			map.put("c", 2.7);
			map.put("d", 5.1);
			map.put("e", 17.4);
			map.put("f", 1.7);
			map.put("g", 3.0);
			map.put("h", 4.8);
			map.put("i", 7.6);
			map.put("j", 0.3);
			map.put("k", 1.4);
			map.put("l", 3.4);
			map.put("m", 2.5);
			map.put("n", 9.8);
			map.put("o", 2.6);
			map.put("p", 0.7);
			map.put("q", 0.01);
			map.put("r", 7.0);
			map.put("s", 7.3);
			map.put("t", 6.2);
			map.put("u", 4.3);
			map.put("v", 0.8);
			map.put("w", 1.9);
			map.put("x", 0.03);
			map.put("y", 0.04);
			map.put("z", 1.1);
			
		}
		
		@Override
		public List<String> getSorted(int length) {
			Comparator<String> comp = new Comparator<String>(){
				@Override
				
				public int compare(String key1, String key2) {
							
					Double val1 = map.get(key1);
					Double val2 = map.get(key2);
				
				 	return val2.compareTo(val1);
				 }
			};
		
			ArrayList<String> list = new ArrayList<String>(map.keySet());
			
			Collections.sort(list, comp);
				 
			return list;
		}

		@Override
		public double getFrequency(String key) {
			return map.get(key);
		}

		@Override
		public Alphabet getAlphabet() {
			return TemplateTestUtils.getDefaultAlphabet();
		}

		@Override
		public String getByRank(int length, int rank) {
			return null; //nicht gebraucht
		}
		
	}
	
	
}
