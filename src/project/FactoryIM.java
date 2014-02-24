package de.tudarmstadt.gdi1.project;

import java.util.Collection;
import java.util.List;

import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;
import de.tudarmstadt.gdi1.project.alphabet.DistributionImpl;
import de.tudarmstadt.gdi1.project.alphabet.DictionaryImpl;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipherImpl;
import de.tudarmstadt.gdi1.project.utils.UtilsImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.CaesarImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.KeywordMonoalphabeticCipherImpl;


import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.PolyalphabeticCipherImpl;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.vigenere.VigenereImpl;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCpaNpaCryptanalysisImpl;
import de.tudarmstadt.gdi1.project.analysis.caesar.CaesarCryptanalysisImpl;

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
        return new DistributionImpl(source, text, ngramsize);
    }
    
    public Alphabet getAlphabetInstance(Collection<Character> characters) {
        return new AlphabetImpl(characters);
    }
    public Dictionary getDictionaryInstance(Alphabet alphabet, String text) {
        return new DictionaryImpl(alphabet, text);
    }

    public MonoalphabeticCipher getMonoalphabeticCipherInstance(Alphabet source, Alphabet dest) {
        return new MonoalphabeticCipherImpl(source, dest);
    }
    public Caesar getCaesarInstance(int key, Alphabet alphabet) {
        return new CaesarImpl(key, alphabet);
    }
    public KeywordMonoalphabeticCipher getKeywordMonoalphabeticCipherInstance(String key, Alphabet alphabet) {
        return new KeywordMonoalphabeticCipherImpl(key, alphabet);
    }
    public PolyalphabeticCipher getPolyalphabeticCipherInstance(Alphabet source, Alphabet... dest) {
        return new PolyalphabeticCipherImpl(source,dest);
    }
    public Vigenere getVigenereCipherInstance(String key, Alphabet alphabet) {
        return new VigenereImpl (key, alphabet);
    }
    public CaesarCryptanalysis getCaesarCryptanalysisInstance() {
        return new CaesarCryptanalysisImpl();
    }
    public MonoalphabeticCpaNpaCryptanalysis getMonoalphabeticCpaNpaCryptanalysis() {
        return new MonoalphabeticCpaNpaCryptanalysisImpl();
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
        return new UtilsImpl();
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
        return  null;
    }
    public ValidateDecryptionOracle getValidateDecryptionOracle(Distribution distribution, Dictionary dictionary) {
        return null;
    }
    
    public Individual getIndividualInstance(Alphabet alphabet, double fitness) {
        return null;
    }

}
