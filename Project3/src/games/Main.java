package games;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		/*PoM game = new PoM();
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
		*/
		PoM gameIA = new PoM(10,4);
		Game.printTab(gameIA.combination);
		System.out.println();
		gameIA.ia();
	}
	
}
/*
 * If the user enter a number which begin by one or many 0 the exception CombinationException is catched 
 * (because of the log10 in line 50 in Game.java class)
 */
