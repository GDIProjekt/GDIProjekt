package de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipherImpl;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

public class PolyalphabeticCipherImpl extends SubstitutionCipherImpl implements
PolyalphabeticCipher {

private Alphabet sourceAlphabet;
private Alphabet[] destAlphabets;

/**
* 
* @param src
* @param dest
*/
public PolyalphabeticCipherImpl(Alphabet src, Alphabet[] dest) {
	sourceAlphabet = src;
	destAlphabets = dest;
}

@Override
public char translate(char chr, int i) {

	int index = sourceAlphabet.getIndex(chr);

	if (index == -1)
		throw new IllegalArgumentException("Character is not in alphabet");

	int alphabetIndex = i%destAlphabets.length;
	
	Alphabet dest = destAlphabets[alphabetIndex];
	
	return dest.getChar(index);
}

@Override
public char reverseTranslate(char chr, int i) {

	int alphabetIndex = i%destAlphabets.length;
	
	Alphabet dest = destAlphabets[alphabetIndex];

	int index = dest.getIndex(chr);

	if (index == -1)
		throw new IllegalArgumentException("Character is not in alphabet");

	return sourceAlphabet.getChar(index);
}

}
