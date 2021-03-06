package de.tudarmstadt.gdi1.project.analysis.monoalphabetic;

import java.util.ArrayList;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.AlphabetImpl;

/**
 * Implementierung des Individual Interfaces.
 * @author Quoc Thong Huynh, ￼Dennis Kuhn, Moritz Matthiesen, ￼Erik Laurin Strelow
 *
 */
public class IndividualImpl implements Individual {

	/**
	 * Das Alphabet des Individuums.
	 */
	private Alphabet alphabet;
	
	/**
	 * Der fitness Wert dieses Individuums.
	 */
	private double fitness;

	
	/**
	 * Kostruktor fuer ein Individuum. Es besteht aus
	 * @param alphabet einem Alphabet
	 * @param fitness einem Fitnesswert.
	 */
	public IndividualImpl(Alphabet alphabet, double fitness) {
		super();
		this.alphabet = alphabet;
		this.fitness = fitness;
	}


	/**
	 * Konstruktor fuer ein Individuum. Aus chars wird das Alphabet fuer das Individuum erstellt. Der Fitnesswert wird auf 0 gesetzt.
	 * @param chars Das Array aus dem das Alphabet erstellt wird. Es sollte kein Buchstabe doppelt vorkommen.
	 */
	public IndividualImpl(char[] chars) {
		super();
		
		ArrayList<Character> set = new ArrayList<Character>();
		for (int i = 0; i < chars.length; i++) {
			set.add(chars[i]);
		}
		
		this.alphabet = new AlphabetImpl(set);
		this.fitness = 0;
	}
	
	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	
	@Override
	public boolean equals(Object o) {
		
		if (o instanceof Individual) {
			Individual i = (Individual) o;	
			return alphabet.equals(i);
		}
		return o.equals(this);
	}
	
}
