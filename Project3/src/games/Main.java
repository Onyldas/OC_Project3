package games;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
	private static Logger logger = LogManager.getLogger();
	
	public static void play(Game game) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose your game : \n1 - Plus or Minus\n2 - Mastermind\n");
		int choice = sc.nextInt();
		
		if (choice == 1) {
			logger.info("Plus or Minus game chosen.\n");
			
			System.out.println("Choose your mode : \n1 - Challenger\n2 - Defender\n3 - Duel\n");
			choice = sc.nextInt();
			
			if(choice == 1){
				logger.info("Challenger mode chosen.\n");
				((PoM) game).challenger();
			}
			else if(choice == 2) {
				logger.info("Defender mode chosen.\n");
				((PoM) game).ia();
			}
			else if(choice == 3) {
				logger.info("Duel mode chosen.\n");
			}
			
			
		}
		
		else {
			
		} 
		
	    sc.close();
	}
	
	public static void main(String[] args) {
		
		
		try {
			InputStream fis = new FileInputStream("config.properties");
			Properties prop = new Properties();
			prop.load(fis);
			
			//Get the properties and cast them to integers
			int combination_size = Integer.parseInt(prop.getProperty("combination_size"));
			int nb_try = Integer.parseInt(prop.getProperty("nb_try"));
			
			//Launch the game with properties
			Game game = new PoM(combination_size, nb_try);
			play(game);
			
			
		} catch (Exception e) {
			logger.error(e,e);
		}
		
		
		
	}
	
}
/*
 * If the user enter a number which begin by one or many 0 the exception CombinationException is catched 
 * (because of the log10 in line 50 in Game.java class)
 */
