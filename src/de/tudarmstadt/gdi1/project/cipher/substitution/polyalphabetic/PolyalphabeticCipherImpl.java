package de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipherImpl;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

public class PolyalphabeticCipherImpl extends SubstitutionCipherImpl implements
PolyalphabeticCipher {

private Alphabet sourceAlphabet;
private Alphabet[] destAlphabets;

/**
* Konstruktor der Klasse PolyalphabeticCipherImpl
* @param src
* @param dest
*/
public PolyalphabeticCipherImpl(Alphabet src, Alphabet[] dest) {
	//TODO Prüfen ob alle Alphabete gleich lang sind
	for (Alphabet a: dest){
		if (a == null)
			throw new IllegalArgumentException("Error: At least one destination alphabet is empty.");
		if (a.size() != src.size())
			throw new IllegalArgumentException("Error: At least one destination alphabet doesn't match in size with source alphabet.");
	}
	sourceAlphabet = src;
	destAlphabets = dest;
}

@Override
public char translate(char chr, int i) {

	int index = sourceAlphabet.getIndex(chr);

	if (index == -1)
		throw new IllegalArgumentException("Character is not in source alphabet");

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
		throw new IllegalArgumentException("Character is not in destination alphabet");

	return sourceAlphabet.getChar(index);
}

}
