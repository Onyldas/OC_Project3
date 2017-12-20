package games;


/**
 * <h4>Plus or Minus class</h4>
 * <p>The first combination Game </p>
 * @author Guillaume
 * @version 4.7
 */
public class PoM extends Game {

	// -----------------------------Constructors----------------------------
	
	/**
	 * Construction of the PoM game 
	 * It needs two parameters :
	 * 
	 * @param combination_size
	 * @param nb_try
	 * 
	 * Here we use super method
	 * The combination/AIcombination are created with random method.
	 * @see Game#randomCombination
	 * @see Game#Game(int, int)
	 */
	public PoM(int combination_size, int nb_try) {
		super(combination_size, nb_try);
	}

	// -------------------------------Methods-------------------------------
	
	/**
	 * Here the response method print + or - just below the input of the player 
	 * to indicate if he has to put a higher or smaller number
	 */
	public void response(int proposition_tab[]) {
		super.response(proposition_tab);
		for (int k = 0; k < combination_size; k++) {
			if (proposition_tab[k] < this.combination[k]) {
				System.out.print("+");
			} else if (proposition_tab[k] > this.combination[k]) {
				System.out.print("-");
			} else {
				System.out.print("=");
			}
		}
	}

	
	/**
	 * The script the AI uses to find the combination
	 * It uses the dichotomy method and begins with 5555.. combination
	 * If it's higher to 5 the AI will try 7 ((5 + 9) /2) etc..
	 * It put every try (which are arrays int[]) of the AI in a big array called memory (which is int[][])
	 * A line of memory correspond to a try of the AI
	 * @return memory
	 */
	protected int[][] AI() {
		int[][] memory = new int[nb_try][combination_size];
		memory[0] = fillArray(memory[0], 5);
		
		for (int i = 1; i < nb_try; i++) {
			for (int j = 0; j < combination_size; j++) {
				int inf = 1;
				int sup = 9;
				
				if (memory[i - 1][j] < AIcombination[j]) {
					inf = memory[i - 1][j];
					if (sup - inf == 1) {
						memory[i][j] = sup;
					} else {
						memory[i][j] = (sup + inf) / 2;
					}
				} 
				
				else if (memory[i - 1][j] > AIcombination[j]) {
					sup = memory[i - 1][j];
					if (i > 1 && memory[i - 2][j] < AIcombination[j]) {
						inf = memory[i - 2][j];
					} // case 4
					if (sup - inf == 1) {
						memory[i][j] = inf;
					} else {
						memory[i][j] = (sup + inf) / 2;
					}
				} 
				
				else {// if it's a good number we don't touch it
					memory[i][j] = memory[i - 1][j];
				}
			}
		}
		return memory;
	}
}
