package de.tudarmstadt.gdi1.project.analysis.vigenere;

import de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis;
import de.tudarmstadt.gdi1.project.alphabet.*;
import java.util.ArrayList;
import java.util.List;


public class VigenereCryptanalysisImpl implements VigenereCryptanalysis{

	// das is die richtige
		private int laenge; // mindestlaenge der Sequenz
		
		
		public VigenereCryptanalysisImpl () {
			laenge = 3;
		}
		
		
		/**		
		 * Erhaelt eine Zahl als Eingabe und gibt
		 * eine Liste von Teilern dieser Zahl aus
		 * @param zahl Die Zahl deren Teiler ermittelt werden sollen
		 * @return List<Integer> Liste der Teiler der Zahl
		 */
		public List<Integer> teilerEinerZahl (int zahl) {
			List<Integer> listoe = new ArrayList<Integer>();
				for(int i=1; i<=zahl; i++) {
					if (zahl%i==0) {
						listoe.add(i);
					}
				}
			return listoe;
		}
		

		/**
		 * Hilfsprozedur 10.2.2
		 * gibt den kleinsten Wert einer Liste(Integer) aus 
		 * @param list zu bearbeitende Liste
		 * @return Integer p das kleinste Element der Liste
		 */
		public int kleinstesElement (List<Integer> listoe) {
			int p = 0;
			for (int i = 0; i <= listoe.size()-1; i ++) {
				if (i == 0) {
					p = listoe.get(i);
				} else
					if (listoe.get(i) <= p) {
					p = listoe.get(i);
				}
			}
			return p;
		}
		
		
		/**
		 * Hilfsprozedur für groessterTeiler 10.2.2
		 * ueberprueft ob alle Werte einer List(Integer) durch p teilbar sind
		 * falls ja wird p ausgegeben, falls nein 0
		 * @param listoe 
		 * @param l
		 * @param p
		 * @return Integer l | 0
		 */
		public int help (List<Integer> listoe, int l, int p) {
			if (listoe.get(l)%p == 0 && l == listoe.size() - 1) {
				return p;
			}
			else
			if (listoe.get(l)%p == 0) {
				 return help(listoe, l + 1, p);
			} else
			return 0;
		}
		
		
		/**
		 * 10.2.2
		 * ueberprueft ein Liste(Integer) und gibt den groessten gemeinsammen Teiler
		 * der Liste aus
		 * @param list
		 * @return i
		 */
		public int groessterTeiler (List<Integer> list) {
			int p = kleinstesElement(list);
			for (int i = p; i >= 0; i --) {
					if (help(list, 0, i) != 0) {
						return i;
				}
			}
			return 1;
		}
		
		
		/** Hilfsprozedur fuer distance 10.2.3
		 * 
		 * @param klartext
		 * @param laenge
		 * @param plp
		 * @return
		 */
		public int check (String text, int laenge, int plp) {
			String sequence = text.substring(plp, laenge + plp);
			String n = text.substring(sequence.length() + plp, text.length());
			if (n.contains(sequence)) {
					for (int p = 0; p <= n.length(); p ++) {
						if(sequence.equals(n.substring(p, laenge + p))) {
							return p + laenge;
						}		
				} return 0;
			} else
				return -1;
		}
		
		
		
		/**
		 * gibt alle Distanzen aus
		 * @param text
		 * @return list <Integer>
		 */
		public List<Integer> distance (String text) {
				List<Integer> listoe = new ArrayList<Integer>();
			for (int l = laenge; l <= text.length()/2; l ++) {
				for (int i = 0; i <= text.length() - l; i ++) {
					if (check(text, l, i) != -1) {
						listoe.add(check(text, l, i));
					} 
				}
			} return listoe;
		}
		
		/**
		 * 10.2.4
		 *  erhaelt einen String (Chiffretext) und gibt alle Teiler des groessten gemeinsammen Teilers
		 * aller Distanzen des Strings aus
		 * @param chiffretext
		 * @return List<Integer>
		 */
		public List<Integer> schluessel (String chiffretext) {
			return teilerEinerZahl(groessterTeiler(distance(chiffretext)));
		}
		
		/**
		 * Attack to determine all possible length of the used key based on a given
		 * ciphertext.
		 * 
		 * @param ciphertext
		 *            the ciphertext
		 * @return the possible key lengths (in ascending order)
		 */
		public List<Integer> knownCiphertextAttack (String chiffretext) {
			return schluessel(chiffretext);
		}
		
		/** 
		 * gibt den vollstaendigen Schluessel zurueck, falls der Schluessel laenger als der Klartext ist
		 * @param chiffretext
		 * @param klartext
		 * @param alphabet
		 * @return
		 */	
		public String translate (String klartext, String chiffretext, Alphabet alphabet) {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < chiffretext.length(); i++) {
				
				if (alphabet.getIndex(klartext.charAt(i)) <= alphabet.getIndex(chiffretext.charAt(i))) {					
					result.append(alphabet.getChar(alphabet.getIndex(chiffretext.charAt(i)) - alphabet.getIndex(klartext.charAt(i))));				
				} 
				else 
					result.append(alphabet.getChar(alphabet.size() - alphabet.getIndex(klartext.charAt(i)) + alphabet.getIndex(chiffretext.charAt(i))));

			}		return result.toString();
		}
		
		

			
		
		/** 
		 * falls sich der Schluessel wiederholt wird der Schluessel ausgegeben
		 * @param text
		 * @return String der Schluessel
		 */
		public String getKey1 (String klartext, String chiffretext, Alphabet alphabet) {
			String text = translate(chiffretext, klartext, alphabet);
			for (int i = 1; i <= text.length() - 1; i++) {
				
				if (text.substring(0, i).length() <= text.substring(i, text.length()).length()){
					
					if (text.charAt(0) == (text.charAt(i))) {
						
						if (text.substring(0, i).equals(text.substring(i, i + text.substring(0, i).length()))) {
							
							if (!(i == 1 && text.charAt(0) == text.charAt(1)))  {
							return text.substring(0, i);
							} 
						}
					}
				}
			}
			return null;
		}
		

		

		/** ueberprueft ob der Schluessel sich teilweise wiederholt und falls ja wird der Schluessel ausgegeben
		 * 10.4.3
		 * @param schluessel
		 * @return String, den Schluessel
		 */
		public String getKey2 (String chiffretext, String klartext, Alphabet alphabet) {
			String text = translate(klartext, chiffretext, alphabet);		
			String schluessel;
			String subschluessel;
			for (int i = 1; i <= text.length() - 1; i++) {
				if (text.charAt(0) == (text.charAt(i))) {
					if (!(i == 1 && text.charAt(0) == text.charAt(1)))  {
						schluessel = text.substring(0, i);
						subschluessel = schluessel;
						if (subschluessel != null) {
							for (int l = 0; l <= i - 1; l ++) {
								if (subschluessel.length() <= text.substring(i, text.length()).length()) {
									if (subschluessel.equals(text.substring(i, i + subschluessel.length()))) {
										return schluessel;
									} else subschluessel = subschluessel.substring(0, subschluessel.length() - 1);
								}	else subschluessel = subschluessel.substring(0, subschluessel.length() - 1);
							}
						}
					}
				}
			}
			return null;	
		}

		/** ueberprueft den ciphertext sowie den plaintext und gibt den key oder einen Teil des keys aus
		 * 
		 * @param ciphertext
		 * @param plaintext
		 * @param alphabet
		 * @return they key, a part of they key
		 */

		@Override
		public String knownPlaintextAttack(String ciphertext, String plaintext, Alphabet alphabet) {
			
			if (getKey1(ciphertext, plaintext, alphabet) != null) {
				return getKey1(ciphertext, plaintext, alphabet);
			}
			
			else if (getKey2(ciphertext, plaintext, alphabet) != null) {
				return getKey2(ciphertext, plaintext, alphabet);
			} 
			else
				return translate(ciphertext, plaintext, alphabet);
		}
		
		

			/**
		 * Attack to determine the used key based on a given ciphertext, a given
		 * distribution on the alphabet and a list of known plaintext cribs.
		 * 
		 * @param ciphertext
		 *            the ciphertext
		 * @param distribution
		 *            the distribution
		 * @param cribs
		 *            the list of substrings known to appear in the plaintext
		 * 
		 * @return the key, a part of the key, or null
		 */
		@Override
		public String knownCiphertextAttack(String ciphertext,
				Distribution distribution, List<String> cribs) {
			// TODO Auto-generated method stub
			return null;
		} 
		
	}