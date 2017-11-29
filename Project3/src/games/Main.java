package games;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose your game : \n1 - Plus or Minus\n2 - Mastermind\n");
		int choice = sc.nextInt();
		
		if (choice == 1) {
			logger.info("Plus or Minus game chosen.\n");
			PoM game = new PoM();
			
			System.out.println("Choose your mode : \n1 - Challenger\n2 - Defender\n3 - Duel\n");
			choice = sc.nextInt();
			
			if(choice == 1){
				logger.info("Challenger mode chosen.\n");
				game.challenger();
			}
			else if(choice == 2) {
				logger.info("Defender mode chosen.\n");
				game.ia();
			}
			else if(choice == 3) {
				logger.info("Duel mode chosen.\n");
			}
			
		}
		
		else {
			
		} 
		
	    sc.close();
	}
	
}
/*
 * If the user enter a number which begin by one or many 0 the exception CombinationException is catched 
 * (because of the log10 in line 50 in Game.java class)
 */
