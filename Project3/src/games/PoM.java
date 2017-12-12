package games;

import java.util.Arrays;

public class PoM extends Game {

	// --------------------------Instance Attributes------------------------

	// -----------------------------Constructors----------------------------
	public PoM() {
		super();
	}

	public PoM(int combination_size, int nb_try) {
		super(combination_size, nb_try);
	}

	// -------------------------------Methods-------------------------------
	public void response(int proposition_tab[]) {
		super.response();
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

	private int[][] AI() {// using dichotomy method
		int[][] memory = new int[nb_try][combination_size];
		for (int k = 0; k < combination_size; k++) {// IA begin with 5555... combination
			memory[0][k] = 5;
		}
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
				} else if (memory[i - 1][j] > AIcombination[j]) {
					sup = memory[i - 1][j];
					if (i > 1 && memory[i - 2][j] < AIcombination[j]) {
						inf = memory[i - 2][j];
					} // case 4
					if (sup - inf == 1) {
						memory[i][j] = inf;
					} else {
						memory[i][j] = (sup + inf) / 2;
					}
				} else {
					memory[i][j] = memory[i - 1][j];
				}
			}
		}
		return memory;
	}

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

	public void duel() {
		boolean YourTurn = true;
		int[] temp = null;
		int i = 0;
		System.out.println("Choose the combination [" + this.combination_size + "] the AI has to find : ");
		int[] AICombi = testInput();
		SetAICombination(AICombi);
		int[][] memory = AI();

		while ((nb_try > 0) && !(Arrays.equals(this.combination, temp)) && !(Arrays.equals(this.AIcombination, memory[i]))) {
			if (YourTurn) {
				System.out.print("Proposition : ");
				temp = testInput();
				response(temp);
				System.out.println();
				YourTurn = false;
			} else {
				System.out.print("------------------- AI propose : ");
				printTab(memory[i]);
				System.out.println();
				i++;
				nb_try--;
				YourTurn = true;
			}
		}
		endGame(memory[i], "AI");
		endGame(temp, "You");
	}

}
