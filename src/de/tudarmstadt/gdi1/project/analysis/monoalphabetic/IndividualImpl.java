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

	private Alphabet alphabet;
	private double fitness;

	
	/**
	 * Kostruktor für ein Indiviuum. Es besteht aus
	 * @param alphabet einem Alphabet
	 * @param fitness einem Fitness wert.
	 */
	public IndividualImpl(Alphabet alphabet, double fitness) {
		super();
		this.alphabet = alphabet;
		this.fitness = fitness;
	}

	/**
	 * Konstruktor für ein Individuum. Aus chars wird das Alphabet für das Individuum erstellt. Der Fitness wert wird auf 0 gesetzt.
	 * @param chars Die Array aus dem das Alphabet erstellt wird. Es sollte kein Buchstabe doppelt vorkomme.
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
