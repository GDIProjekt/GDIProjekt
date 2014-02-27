package de.tudarmstadt.gdi1.project.analysis.vigenere;
import de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis;
import de.tudarmstadt.gdi1.project.alphabet.*;

import java.util.ArrayList;
import java.util.List;

public class VigenereCryptanalysisImpl implements VigenereCryptanalysis{
	private List<Integer> list = new ArrayList<Integer>();
	private int laenge; // mindestlaenge der Sequenz

	/**
	 * erhaellt eine Zahl als eingabe und gibt
	 * eine Liste von gemeinsammen Teilern dieser Zahl aus
	 * @param zahl
	 * @return list list
	 */
	
	
	public VigenereCryptanalysisImpl () {
		this.laenge = 3;
	}
	
	/** nimmt eine als eingabe Zahl und gibt alle Zahlen als Liste zurueck die 
	 * durch diese Zahl teilbar sind
	 * @param zahl
	 * @return List<Integer>
	 */
	public List<Integer> teiler (int zahl) {
		List<Integer> listoe = new ArrayList<Integer>();
			for(int i=1; i<=zahl; i++) {
				if (zahl%i==0) {
					listoe.add(i);
				}
			}
		return listoe;
	}
	

	/**
	 * gibt den kleinsten Wert einer Liste(Integer) aus 
	 * @param list
	 * @return Integer p
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
	 * ueberprueft ob alle Werte einer List(Integer) durch p teilbar sind
	 * falls ja wird p ausgegeben, falls nein 0
	 * @param i
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
	/** Hilfsprotzedur fuer distance 
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
	

	
		/** Die Methode known CyphertextAnalysis
		 * nicht sicher ob laenge da hinkommt
		 * @param klartext
		 * @param laenge
		 * @return list<Integer>
		 */
	
	public int getlaenge() {
		return laenge;
	}
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
	
	/** erhaellt ein String(Chiffretext) und gibt alle Teiler des groessten gemeinsammen Teilers
	 * aller Distanzen des Strings aus
	 * @param chiffretext
	 * @return List<Integer>
	 */
	public List<Integer> schluessel (String ciphertext) {
		return teiler(groessterTeiler(distance(ciphertext)));
	}
	
	
	
	public List<Integer> knownCipherTextAttack (String chiffretext) {
		return schluessel(chiffretext);
	}
	/** gibt den vollstaendigen Schluessel zurueck, falls der Schluessel laenger als der Klartext ist
	 * @param chiffretext
	 * @param klartext
	 * @param alphabet
	 * @return
	 */	
	
	
	
	public String schuessel (String chiffretext, String klartext, Alphabet alphabet) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < chiffretext.length(); i++) {
			if (alphabet.getIndex(klartext.charAt(i)) <= alphabet.getIndex(chiffretext.charAt(i))) {
				result.append(alphabet.getChar(alphabet.getIndex(chiffretext.charAt(i)) - alphabet.getIndex(klartext.charAt(i)) + 1));
			} else result.append(alphabet.getChar(alphabet.size() - alphabet.getIndex(klartext.charAt(i)) + alphabet.getIndex(chiffretext.charAt(i)) + 1));		
		}
		return result.toString();
	}
	
	public String schluessel1 (String text) {
		int counter = 0;
		String sub = "schluesselnichtenthalten";
		for(int i = 1; i <= text.length()-1; i++) {
			if (counter == 1 && text.charAt(0) == text.charAt(i)) {
				if (sub == text.substring(i, i + sub.length())) {
					return sub;
				}
			} else
				if (text.charAt(0)==(text.charAt(i))) {
					if (text.substring(0,i).equals(text.substring(i, i + text.substring(0,i).length()))) {
						counter = counter + 1;
						sub = text.substring(0,i);
					}
				}
		} return sub;
	}
	
	public String schluessel2 (String text) {
		return null;
	}
	
	public String knownPlaintextAttack(String ciphertext, String plaintext, Alphabet alphabet){
		return null;
	}
	
	public String knownCiphertextAttack(String ciphertext, Distribution distribution, List<String> cribs){
		return null;
	}

	
}
