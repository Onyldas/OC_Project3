package games;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Choose your game : \n1 - Plus or Minus\n2 - Mastermind\n");
		int choice = sc.nextInt();
		if (choice == 1) {
			PoM game = new PoM();
			int temp[]= new int[game.combination_size];
			int count = 0;
			int nb_tryy = 7;
			while (count < nb_tryy && !(Arrays.equals(game.combination,temp))) {
				try {
					temp = game.tryy();
					game.response(temp);
					count++;
				} catch (CombinationException e) {
				} catch (InputMismatchException e2) {e2.getMessage();count=11;
				} finally {
					System.out.println();
				}
			}
			if (Arrays.equals(game.combination,temp))
			{
				System.out.println("\nYou win !");
			}
			else { System.out.println("Game Over !");}
			
			PoM gameIA = new PoM(4,6);
			Game.printTab(gameIA.combination);
			System.out.println();
			gameIA.ia();
		}
		
		else {
			
		}*/
		logger.debug("msg de debogage");
	    logger.info("msg d'information");
	    logger.warn("msg d'avertissement");
	    logger.error("msg d'erreur");
	    logger.fatal("msg d'erreur fatale");  
		
	}
	
}
/*
 * If the user enter a number which begin by one or many 0 the exception CombinationException is catched 
 * (because of the log10 in line 50 in Game.java class)
 */
