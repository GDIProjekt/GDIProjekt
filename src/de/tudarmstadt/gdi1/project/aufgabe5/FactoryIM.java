package aufgabe5;
import de.tudarmstadt.gdi1.project.Factory;

import java.util.Collection;
import java.util.List;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCpaNpaCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCribCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis;
import de.tudarmstadt.gdi1.project.cipher.enigma.Enigma;
import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;
import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.Caesar;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.KeywordMonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.PolyalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.Vigenere;
import de.tudarmstadt.gdi1.project.utils.Utils;

public class FactoryIM implements Factory{
	
    public Distribution getDistributionInstance(Alphabet source, String text, int ngramsize) {
        return null;
    }
    
    public Alphabet getAlphabetInstance(Collection<Character> characters) {
        return new AlphabetImpl(characters);
    }
    public Dictionary getDictionaryInstance(Alphabet alphabet, String text) {
        return null;
    }

    public MonoalphabeticCipher getMonoalphabeticCipherInstance(Alphabet source, Alphabet dest) {
        return null;
    }
    public Caesar getCaesarInstance(int key, Alphabet alphabet) {
        return null;
    }
    public KeywordMonoalphabeticCipher getKeywordMonoalphabeticCipherInstance(String key, Alphabet alphabet) {
        return null;
    }
    public PolyalphabeticCipher getPolyalphabeticCipherInstance(Alphabet source, Alphabet... dest) {
        return null;
    }
    public Vigenere getVigenereCipherInstance(String key, Alphabet alphabet) {
        return null;
    }
    public CaesarCryptanalysis getCaesarCryptanalysisInstance() {
        return null;
    }
    public MonoalphabeticCpaNpaCryptanalysis getMonoalphabeticCpaNpaCryptanalysis() {
        return null;
    }
    public MonoalphabeticCribCryptanalysis getMonoalphabeticCribCryptanalysisInstance() {
        return null;
    }
    public MonoalphabeticKnownCiphertextCryptanalysis getMonoalphabeticKnownCiphertextCryptanalysisInstance() {
        return null;
    }
    public VigenereCryptanalysis getVigenereCryptanalysisInstance() {
        return null;
    }
    public Utils getUtilsInstance() {
        return null;
    }
    public Enigma getEnigmaInstance(List<Rotor> rotors, PinBoard pinboard, ReverseRotor reverseRotor) {
        return null;
    }
    public PinBoard getPinBoardInstance(Alphabet source, Alphabet destination) {
        return null;
    }
    public Rotor getRotorInstance(Alphabet entryAlph, Alphabet exitAlph, int startPosition) {
        return null;
    }
    public ReverseRotor getReverseRotatorInstance(Alphabet entryAlph, Alphabet exitAlph) {
        return null;
    }
    public Class<? extends SubstitutionCipher> getAbstractSubstitutionCipherClass() {
        return null;
    }
    public ValidateDecryptionOracle getValidateDecryptionOracle(Distribution distribution, Dictionary dictionary) {
        return null;
    }
    
    public Individual getIndividualInstance(Alphabet alphabet, double fitness) {
        return null;
    }

}
