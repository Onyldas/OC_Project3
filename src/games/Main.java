package games;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h4>Main class</h4>
 * <p>
 * In this class we can play the games written in the others classes.
 * </p>
 * @author Guillaume FRANCOIS
 * @version 4.7
 */
public class Main {

	private static Logger logger = LogManager.getLogger();
	static Scanner sc = new Scanner(System.in);

	// -------------------------------Methods-------------------------------

	/**
	 * This method take the parameters of the combination games :
	 * @param combination_size
	 * @param nb_try
	 * @param colors
	 * Then we can chose between the two games
	 * It call the @see Main{@link Main#mode(Game, boolean)} and the @see Main{@link #replay()} method
	 */
	public static void play(int combination_size, int nb_try, int colors, boolean devMod) {
		boolean replay = true;

		do {
			System.out.println("Choose your game : \n1 - Plus or Minus\n2 - Mastermind\n");
			int choice = sc.nextInt();
			if (choice == 1) {
				logger.info("Plus or Minus game chosen.\n");
				PoM game = new PoM(combination_size, nb_try);
				System.out.println(
						"In Plus or Minus game, you have to find a secret combination of numbers between 1 and 9.\n"
								+ "If the script shows a + below your number you have to enter a number higher than your previous one. \n"
								+ "Easy isn't it ?\n");

				mode(game, devMod);
			} else if (choice == 2) {
				logger.info("Mastermind game chosen.\n");
				Mastermind game = new Mastermind(combination_size, nb_try, colors);
				System.out.println("The object of MASTERMIND is to guess a secret code consisting of a series of 10\r\n"
						+ "colored pegs (here, we'll use numbers 0 to 9). Each guest results in feedback narrowing down the possibilities of the\r\n"
						+ "code. The winner is the player who solves his opponent's secret code with fewer\r\n"
						+ "guesses\n");

				mode(game, devMod);
			}

			replay = replay();


		} while (replay);
		sc.close();
	}


	/**
	 * This method called by the @see Main{@link Main#play(int, int, int, boolean)} method
	 * propose the choose between the three mods
	 *	<ul>
	 * <li>@see Game#Challenger()</li>
	 * <li>@see Game#Defender()</li>
	 * <li>@see Game#Duel()</li>
	 * </ul>
	 * @param game
	 */
	public static void mode(Game game, boolean devMod) {
		System.out.println("\nChoose your mod : \n1 - Challenger\n2 - Defender\n3 - Duel");
		int choice = sc.nextInt();

		if (choice == 1) {
			logger.info("Challenger mode chosen. ");
			if(devMod){
				System.out.println("\nDev mod activated :");
				Game.printTab(game.combination);
				System.out.println();
			}
			System.out.println("You have to find the secret combination. Good luck ! \n");
			game.challenger();
		} else if (choice == 2) {
			logger.info("Defender mode chosen.");
			System.out.println("AI has to find your combination ! \n");
			game.defender();
		} else if (choice == 3) {
			if(devMod){
				System.out.println("\nDev mod activated :");
				Game.printTab(game.combination);
				System.out.println();
			}
			logger.info("Duel mode chosen.");
			System.out.println("Both you and AI will try to find each combination. The first will win. \n");
			game.duel();
		}
	}


	/**
	 * While the boolean is true the player replay the game.
	 * @return boolean replay
	 */
	public static boolean replay() {
		boolean replay;
		System.out.println("\nDo you want to replay ? \n1 - Yes\n2 - No");
		int choice = sc.nextInt();

		if (choice == 1) {
			replay = true;
		} else {
			replay = false;
		}

		return replay;
	}


	/**
	 * If the user play the game in programming mod, this method will return true
	 * @param args
	 * @return boolean
	 */
	public static boolean enableDevMod(String [] args) {
		if(args.length == 1 && args[0].equals("-d")) {
			return true;
		}
		else {
			return false;
		}
	}
	// ----------------------------------MAIN-------------------------------
	public static void main(String[] args) {

		boolean devMod = enableDevMod(args);
		int combination_size = 4;
		int nb_try = 10;
		int colors = 8;
		Properties prop = new Properties();

		try {
			InputStream fis = new FileInputStream("config.properties");
			prop.load(fis);

			// Get the properties and cast them to integers
			combination_size = Integer.parseInt(prop.getProperty("combination_size"));
			nb_try = Integer.parseInt(prop.getProperty("nb_try"));
			colors = Integer.parseInt(prop.getProperty("colors"));
			fis.close();

		} catch (Exception e) {
			logger.error("No file find or bad inputs (it must be integers)");

		} finally {
			// Launch the game with properties or default properties
			play(combination_size, nb_try, colors,devMod);
		}

	}

}