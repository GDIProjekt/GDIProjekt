package de.tudarmstadt.gdi1.project.test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tudarmstadt.gdi1.project.test.alphabet.TemplateAlphabetTests;
import de.tudarmstadt.gdi1.project.test.alphabet.TemplateDictionaryTests;
import de.tudarmstadt.gdi1.project.test.alphabet.TemplateDistributionTests;
import de.tudarmstadt.gdi1.project.test.alphabet.TestAlphabetImpl;
import de.tudarmstadt.gdi1.project.test.alphabet.TestDistributionImpl;
import de.tudarmstadt.gdi1.project.test.analysis.TemplateCaesarCryptanalysisTests;
import de.tudarmstadt.gdi1.project.test.analysis.TemplateValidationDecryptionOracle;
import de.tudarmstadt.gdi1.project.test.analysis.TemplateVigenereCryptanalysisTests;
import de.tudarmstadt.gdi1.project.test.analysis.monoalphabetic.TemplateIndividualTests;
import de.tudarmstadt.gdi1.project.test.analysis.monoalphabetic.TemplateMonoalphabeticCpaNpaCryptanalysisTests;
import de.tudarmstadt.gdi1.project.test.cipher.TestCaesarAnalysis;
import de.tudarmstadt.gdi1.project.test.cipher.TestMonoalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.test.cipher.TestSubstitutionsCipherImpl;
import de.tudarmstadt.gdi1.project.test.cipher.enigma.TemplateEnigmaTest;
import de.tudarmstadt.gdi1.project.test.cipher.enigma.TemplatePinBoardTest;
import de.tudarmstadt.gdi1.project.test.cipher.enigma.TemplateReverseRotorTest;
import de.tudarmstadt.gdi1.project.test.cipher.enigma.TemplateRotorTest;
import de.tudarmstadt.gdi1.project.test.cipher.enigma.TestEnigma;
import de.tudarmstadt.gdi1.project.test.cipher.substitution.TemplateCaesarTests;
import de.tudarmstadt.gdi1.project.test.cipher.substitution.TemplateKeywordMonoalphabeticCipherTests;
import de.tudarmstadt.gdi1.project.test.cipher.substitution.TemplateMonoalphabeticCipherTests;
import de.tudarmstadt.gdi1.project.test.cipher.substitution.TemplatePolyalphabeticCipherTests;
import de.tudarmstadt.gdi1.project.test.cipher.substitution.TemplateSubstitutionCipherTests;
import de.tudarmstadt.gdi1.project.test.cipher.substitution.TemplateVigenereCipherTests;
import de.tudarmstadt.gdi1.project.test.utils.TemplateUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
	/* alphabet and co */
	TemplateAlphabetTests.class,
	TemplateDictionaryTests.class,
	TemplateDistributionTests.class,
	
	/* ciphers */
	TemplateCaesarTests.class,
	TemplateKeywordMonoalphabeticCipherTests.class,
	TemplateMonoalphabeticCipherTests.class,
	TemplatePolyalphabeticCipherTests.class,
	TemplateSubstitutionCipherTests.class,
	TemplateVigenereCipherTests.class,
	TemplateMonoalphabeticCpaNpaCryptanalysisTests.class,
	
	/* analysis */
	TemplateCaesarCryptanalysisTests.class,
	TemplateValidationDecryptionOracle.class,
	TemplateVigenereCryptanalysisTests.class,
	TemplateIndividualTests.class,

	
	/* enigma */
	TemplateEnigmaTest.class,
	TemplatePinBoardTest.class,
	TemplateReverseRotorTest.class,
	TemplateRotorTest.class,
	
	/* utils */
	TemplateUtilsTests.class,

	/* own */
	TestAlphabetImpl.class,
	TestDistributionImpl.class,
	
	TestCaesarAnalysis.class,
	TestMonoalphabeticCipherImpl.class,
	TestSubstitutionsCipherImpl.class,
	TestEnigma.class,
	
	TestUtilsImpl.class
	
	})
public class Main {

	@BeforeClass
	public static void initTestCore() {
		TemplateTestCore.FACTORYPATH = "de.tudarmstadt.gdi1.project.FactoryIM";
	}

}
