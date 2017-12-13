package games;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static Logger logger = LogManager.getLogger();

	// -------------------------------Methods-------------------------------
	public static void play(Game game) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose your game : \n1 - Plus or Minus\n2 - Mastermind\n");
		int choice = sc.nextInt();

		if (choice == 1) {
			logger.info("Plus or Minus game chosen.\n");
			System.out.println("In Plus or Minus game, you have to find a secret combination of numbers between 1 and 9.\n"
					+ "If the script shows a + below your number you have to enter a number higher than your previous one. \n"
					+ "Easy isn't it ?");

			System.out.println("Choose your mode : \n1 - Challenger\n2 - Defender\n3 - Duel");
			choice = sc.nextInt();

			if (choice == 1) {
				logger.info("Challenger mode chosen. ");
				Game.printTab(game.combination);
				System.out.println("You have to find the secret combination. Good luck ! \n");
				((PoM) game).challenger();
			} else if (choice == 2) {
				logger.info("Defender mode chosen.");
				System.out.println("AI has to find your combination ! \n");
				((PoM) game).defender();
			} else if (choice == 3) {
				logger.info("Duel mode chosen.");
				System.out.println("Both you and AI will try to find each combination. The first will win. \n");
				((PoM) game).duel();
			}

			play(game);
		}

		else if (choice == 2) {
			logger.info("Mastermind game chosen.\n");
			System.out.println("The object of MASTERMIND is to guess a secret code consisting of a series of 10\r\n"
					+ "colored pegs (here, we'll use numbers 0 to 9). Each guest results in feedback narrowing down the possibilities of the\r\n"
					+ "code. The winner is the player who solves his opponent's secret code with fewer\r\n"
					+ "guesses");

			System.out.println("Choose your mode : \n1 - Challenger\n2 - Defender\n3 - Duel");
			choice = sc.nextInt();

			if (choice == 1) {
				logger.info("Challenger mode chosen. ");
				Game.printTab(game.combination);
				System.out.println("\nYou have to find the secret combination. Good luck ! \n");
				((Mastermind) game).challenger();
			} else if (choice == 2) {
				logger.info("Defender mode chosen. ");
				System.out.println("AI has to find your combination ! \n");
				((Mastermind) game).defender();
			} else if (choice == 3) {
				logger.info("Duel mode chosen. ");
				Game.printTab(game.combination);
				System.out.println("Both you and AI will try to find each combination. The first will win. \n");
				((Mastermind) game).duel();
			}

			play(game);
		}

		else {

		}

		sc.close();
	}
	
	public static void mode() {
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
	}

	// ----------------------------------MAIN-------------------------------
	public static void main(String[] args) {

		int combination_size = 4;
		int nb_try = 10;
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream("config.properties");
			prop.load(fis);

			// Get the properties and cast them to integers
			combination_size = Integer.parseInt(prop.getProperty("combination_size"));
			nb_try = Integer.parseInt(prop.getProperty("nb_try"));

		} catch (Exception e) {
			logger.error(e);

		} finally {
			// Launch the game with properties or default properties
			Game game = new Mastermind(combination_size, nb_try,8);
			play(game);
		}

		
		
		
	}

}
