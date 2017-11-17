package games;

import java.util.InputMismatchException;

public class Main {

	public static void main(String[] args) {
		PoM game = new PoM();
		for (int k = 0; k < 3; k++) {
			try {
				game.response(game.tryy());
			} catch (CombinationException e) {
			} catch (InputMismatchException e2) {e2.getMessage();k=10;
			} finally {
				System.out.println();
			}
		}

	}

}
