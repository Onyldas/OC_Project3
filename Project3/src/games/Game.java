package games;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Game {

	protected static Logger logger = LogManager.getLogger();

	Scanner sc = new Scanner(System.in);
	// --------------------------Instance Attributes------------------------
	protected int combination_size;
	protected int nb_try;
	protected int[] combination;
	protected int[] AIcombination;

	// -----------------------------Constructors----------------------------
	public Game() {
		this.combination_size = 4;
		this.nb_try = 20;
		this.combination = randomCombination();
		this.AIcombination = randomCombination();
		logger.info("Default game built. " + nb_try + " try.");
	}

	public Game(int combination_size, int nb_try) {
		this.combination_size = combination_size;
		this.nb_try = nb_try;
		this.combination = randomCombination();
		this.AIcombination = randomCombination();
		logger.info("Game built. " + nb_try + " try.");
	}

	// -------------------------------Getters-------------------------------
	public int GetCombination_size() {
		return this.combination_size;
	}

	public int GetNb_try() {
		return this.nb_try;
	}

	public int[] GetCombination() {
		return this.combination;
	}

	public int[] GetAICombination() {
		return this.AIcombination;
	}

	// -------------------------------Setters-------------------------------
	public void SetCombination_size(int combi_size) {
		this.combination_size = combi_size;
	}

	public void SetNb_try(int _nb_try) {
		this.nb_try = _nb_try;
	}

	public void SetCombination(int[] combi) {
		this.combination = combi;
	}

	public void SetAICombination(int[] combi) {
		this.AIcombination = combi;
	}

	// -------------------------------Methods-------------------------------
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

	public int[] fillArray(int[] Array, int fill) {
		for (int k = 0; k < Array.length; k++) {
			Array[k] = fill;
		}
		return Array;
	}

	protected int[] tryy() throws CombinationException {
		int proposition = sc.nextInt();

		if (((int) Math.log10(proposition) + 1) != combination_size) {// if the user enter more or less numbers that
																		// he's supposed to
			logger.error("The user's combination doesn't respect the combination size (" + combination_size + ")");
			throw new CombinationException(combination_size);
		}

		else {
			int[] proposition_tab = new int[combination_size];
			for (int k = 0; k < combination_size; k++) {
				proposition_tab[combination_size - 1 - k] = (int) (proposition % Math.pow(10, (k + 1)) / Math.pow(10, k));
				if (proposition_tab[combination_size - 1 - k] == 0) {
					throw new CombinationException(combination_size);
				}
			}
			return proposition_tab;
		}

	}
	
	protected int[] testInput() {
		int[] input = null;
		try {
			input = tryy();
		} catch (InputMismatchException e) {
			logger.error(e);
			input = defaultProposition(input);
			sc.next();
		} catch (CombinationException e2) {
			logger.error(e2);
			input = defaultProposition(input);
		}
		return input;
	}
	
	private int[] defaultProposition(int[] input) {
		input = randomCombination();
		System.out.print("Default Proposition : ");
		printTab(input);
		System.out.println();
		return input;
	}

	protected void response() {
		System.out.print(" -> Response : ");
	}

	protected void endGame(int[] final_combi, String name) {

		if (name == "AI") {
			if (Arrays.equals(this.AIcombination, final_combi)) {
				System.out.println("\n" + name + " wins !");
			} else {
				System.out.println("\n" + name + " loses..");
			}
		} else {
			if (Arrays.equals(this.combination, final_combi)) {
				System.out.println("\n" + name + " win !");
			} else {
				System.out.println("\n" + name + " lose..");
			}
		}
	}

}
