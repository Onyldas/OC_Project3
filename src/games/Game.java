package games;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * <b>Game class represent every games the program will build.</b>
 * <p>
 * A game is represented by 4 attributes :
 * <ul>
 * <li>The length of the combination the player/AI has to find</li>
 * <li>The number of try the player has.</li>
 * <li>The combination the player has to find</li>
 * <li>The combination the AI has to find</li>
 * </ul>
 * </p>
 * <p>
 * Plus, you can chose the mode of the game.
 * </p>
 *
 * @author Guillaume FRANCOIS
 * @version 4.7
 *
 */
public abstract class Game {

	protected static Logger logger = LogManager.getLogger();

	Scanner sc = new Scanner(System.in);
	// --------------------------Instance Attributes------------------------
	protected int combination_size;
	protected int nb_try;
	protected int[] combination;
	protected int[] AIcombination;

	// -----------------------------Constructors----------------------------
	/**
	 * Construction of the game It needs two parameters :
	 *
	 * @param combination_size
	 * @param nb_try
	 *
	 * The combination/AIcombination are created with random method.
	 * @see Game#randomCombination
	 *
	 */
	public Game(int combination_size, int nb_try) {
		this.combination_size = combination_size;
		this.nb_try = nb_try;
		this.combination = randomCombination();
		this.AIcombination = randomCombination();
		logger.info("Game built. " + nb_try + " try.");
	}


	/**
	 * Setter for AIcombination attribute.
	 * @param combi
	 */
	public void SetAICombination(int[] combi) {
		this.AIcombination = combi;
	}

	// -------------------------------Methods-------------------------------
	/**
	 * Creation of the combination the player/IA has to find when the object is created.
	 * @return the combination created
	 */
	public int[] randomCombination() {
		int combination[] = new int[combination_size];

		for (int k = 0; k < combination_size; k++) {
			combination[k] = (int) (Math.random() * (double) (10 - 1)) + 1;
		}

		return combination;
	}


	/**
	 * Print an array.
	 * @param tab
	 */
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


	/**
	 * Copy an array in a new array without link between them.
	 * @param copy
	 * @param array
	 * @return the copy
	 */
	public static int[] copyArray(int[] copy, int array[]) {
		for (int l = 0; l < array.length; l++) {
			copy[l] = array[l];
		}
		return copy;
	}


	/**
	 * Every cell of the array will be fill by the integer "fill".
	 * @param Array
	 * @param fill
	 * @return the filled array
	 */
	public int[] fillArray(int[] Array, int fill) {
		for (int k = 0; k < Array.length; k++) {
			Array[k] = fill;
		}
		return Array;
	}


	/**
	 * This method take the input of the user,
	 * it test if the input is correct (integers, good length @see {@link Game#combination_size}),
	 * and it put the input in an integer array.
	 * @return the array
	 * @throws CombinationException If the user enter more or less numbers that he's supposed to
	 */
	protected int[] tryy() throws CombinationException {
		int proposition = sc.nextInt();

		if (((int) Math.log10(proposition) + 1) != combination_size) {
			logger.error("The user's combination doesn't respect the combination size (" + combination_size + ")");
			throw new CombinationException(combination_size);
		}

		else {
			int[] proposition_tab = new int[combination_size];
			for (int k = 0; k < combination_size; k++) {
				proposition_tab[combination_size - 1
						- k] = (int) (proposition % Math.pow(10, (k + 1)) / Math.pow(10, k));
				if (proposition_tab[combination_size - 1 - k] == 0) {
					throw new CombinationException(combination_size);
				}
			}
			return proposition_tab;
		}

	}


	/**
	 * It test if the input of the user return an exception.
	 * It catch exceptions and ask for an other @see {@link Game#tryy()} while the input is notCorrect.
	 * @return the good input
	 */
	protected int[] testInput() {
		int[] input = null;
		boolean notCorrect = true;
		do {
			try {
				input = tryy();
				notCorrect = false;
			} catch (InputMismatchException e) {
				logger.error("\nIncorrect input ! Try again :\n");
				sc.next();
			} catch (CombinationException e2) {
				logger.error("\nIncorrect input ! Try again :\n");
			}
		} while (notCorrect);

		return input;
	}


	/**
	 * The response to the user input.
	 * It depends of the chosen game.
	 * @param proposition_tab
	 */
	protected void response(int[] proposition_tab) {
		System.out.print(" -> Response : ");
	}


	/**
	 * The first mode :
	 * the user has to find the combination with a limited number of try.
	 * It ends when there's no more try or when the user find the good combination.
	 * @see Game#endGame(int[], String)
	 */
	public void challenger() {
		int temp[] = new int[this.combination_size];

		while (nb_try > 0 && !(Arrays.equals(this.combination, temp))) {
			System.out.print("Proposition : ");
			temp = testInput();
			response(temp);
			nb_try--;
			System.out.println();
		}

		endGame(temp, "You");
	}


	/**
	 * The script the AI will use to find its combination.
	 * It depends of the chosen game.
	 * @return
	 */
	protected int[][] AI() {
		return null;
	}


	/**
	 * The second mode :
	 * the user give a combination the AI will try to find with its script
	 * @see Game#AI()
	 * It ends when there's no more try or when the AI find the good combination.
	 * @see Game#endGame(int[], String)
	 */
	public void defender() {

		System.out.println("Choose the combination [" + this.combination_size + "] the AI has to find : ");
		int[] AICombi = testInput();
		SetAICombination(AICombi);
		int[][] memory = AI();
		int k = -1;

		do {
			k++;
			printTab(memory[k]);
			System.out.println();
			nb_try--;
		} while (!(Arrays.equals(this.AIcombination, memory[k])) && (nb_try > 0));

		endGame(memory[k], "AI");
	}


	/**
	 * The third mode :
	 * the user give a combination the AI will try to find with its script
	 * @see Game#AI()
	 * the user has to find the combination with a limited number of try
	 * they played round by round.
	 * It ends when there's no more try or when the AI or the player find their good combination.
	 * @see Game#endGame(int[], String)
	 */
	public void duel() {
		boolean YourTurn = true;
		boolean FirstTurn = true;
		int[] temp = null;
		int i = 0;
		System.out.println("Choose the combination [" + this.combination_size + "] the AI has to find : ");
		int[] AICombi = testInput();
		SetAICombination(AICombi);
		int[][] memory = AI();

		while ((nb_try > 0) && !(Arrays.equals(this.combination, temp))
				&& !(Arrays.equals(this.AIcombination, memory[i]))) {
			if (YourTurn) {
				System.out.print("Proposition : ");
				temp = testInput();
				response(temp);
				System.out.println();
				YourTurn = false;
			} else {
				System.out.print("------------------- AI propose : ");
				if (FirstTurn) {// to print the first step of the AI
					FirstTurn = false;
				} else {
					i++;
				}
				printTab(memory[i]);
				System.out.println();
				nb_try--;
				YourTurn = true;
			}
		}
		endGame(memory[i], "AI");
		endGame(temp, "You");
	}


	/**
	 * Test if this is the AI or the user who's concerned
	 * Then it test if he/it wins or loses.
	 * Print the result
	 * @param final_combi
	 * @param name
	 */
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
