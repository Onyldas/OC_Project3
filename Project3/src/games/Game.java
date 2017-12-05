package games;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Game implements Challenger {

	protected static Logger logger = LogManager.getLogger();
	
	Scanner sc = new Scanner(System.in);
	// Instance attributes
	protected int combination_size;
	protected int nb_try;
	protected int[] combination;

	// Constructors
	public Game() {
		this.combination_size = 4;
		this.nb_try = 20;
		this.combination = randomCombination();
		logger.info("Default game built. " + nb_try +" try.");
	}

	public Game(int combination_size, int nb_try) {
		this.combination_size = combination_size;
		this.nb_try = nb_try;
		this.combination = randomCombination();
		logger.info("Game built. " + nb_try +" try.");
	}
	
	//-------------------------------Getters-----------------------------------
	public int GetCombination_size() {
		return this.combination_size;
	}
	public int GetNb_try() {
		return this.nb_try;
	}
	public int[] GetCombination() {
		return this.combination;
	}
	
	//-------------------------------Setters-----------------------------------
	public void SetCombination_size(int combi_size) {
		this.combination_size = combi_size;
	}
	public void SetNb_try(int _nb_try) {
		this.nb_try = _nb_try;
	}
	public void SetCombination(int[] combi) {
		this.combination = combi;
	}
	

	// ------------------------------Methods-----------------------------------
	public int[] randomCombination() { // Creation of the combination the player/IA has to find when the object is created
		int combination[] = new int[combination_size];
		for (int k = 0; k < combination_size; k++) {
			combination[k] = (int) (Math.random() * (double) (10 - 1)) + 1;
		}
		return combination;
	}

	public static void printTab(int[] tab) {
		System.out.print("[");
		for (int i = 0; i < tab.length; i++) {
			System.out.print(tab[i]);
			if (i != tab.length - 1) {
				System.out.print(",");
			}
		}
		System.out.print("]");
	}

	public int[] tryy() throws CombinationException {
		int proposition = sc.nextInt();

		if (((int) Math.log10(proposition) + 1) != combination_size) {// if the user enter more or less numbers that he's supposed to
			logger.error("The user's combination doesn't respect the combination size" + combination_size);
			throw new CombinationException(combination_size);
		} 
		
		else {
			int[] proposition_tab = new int[combination_size];
			for (int k = 0; k < combination_size; k++) {
				proposition_tab[combination_size - 1- k] = (int) (proposition % Math.pow(10, (k + 1)) / Math.pow(10, k));
			}
			
			return proposition_tab;
		}

	}
	
	
	
	
	
	public void response() {
		System.out.print(" -> Response : ");
	}

}
