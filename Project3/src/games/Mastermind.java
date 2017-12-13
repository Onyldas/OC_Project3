package games;

import java.util.Arrays;

public class Mastermind extends Game {

	// --------------------------Instance Attributes------------------------
	private int colors;

	// -----------------------------Constructors----------------------------
	public Mastermind() {
		super();
		colors = 6;
	}

	public Mastermind(int combination_size, int nb_try, int colors) {
		super(combination_size, nb_try);
		this.colors = colors;
	}
	// -------------------------------Getters-------------------------------

	// -------------------------------Setters-------------------------------

	// -------------------------------Methods-------------------------------
	private int[] selectPlayer(int select) {
		if (select == 0) {
			return this.combination;
		} else {
			return this.AIcombination;
		}
	}

	private int goodColor(int tryy[], int select) {
		int[] selectedCombination = selectPlayer(select);
		int color = 0;
		int color2 = 0;
		for (int i = 0; i < this.combination_size; i++) {
			for (int j = 0; j < this.combination_size; j++) {
				if (selectedCombination[i] == tryy[j]) {
					color++;
					j = combination_size - 1;
				}
			}
		}
		for (int i = 0; i < this.combination_size; i++) {
			for (int j = 0; j < this.combination_size; j++) {
				if (selectedCombination[j] == tryy[i]) {
					color2++;
					j = combination_size - 1;
				}
			}
		}
		return Math.min(color, color2);
	}

	private int goodPosition(int tryy[], int select) {
		int[] selectedCombination = selectPlayer(select);
		int position = 0;
		for (int i = 0; i < this.combination_size; i++) {
			if (selectedCombination[i] == tryy[i]) {
				position++;
			}
		}
		return position;
	}

	public void response(int proposition_tab[]) {
		super.response(proposition_tab);
		int position = goodPosition(proposition_tab, 0);
		int color = goodColor(proposition_tab, 0);
		System.out.print(position + " good position(s) and " + color + " good color(s).");

	}

	private int[][] findColor() {
		int i = 1;
		int color = 0;
		int[][] memory = new int[nb_try+2][combination_size];
		fillArray(memory[0], color);
		int nb_color = goodColor(memory[0], 1);
		while (nb_color == 0) {// AI fill the combination till it finds one goodColor minimum
			color++;
			fillArray(memory[0], color);
			nb_color = goodColor(memory[0], 1);
		}
		while (i < this.colors + 1 && nb_color != combination_size && nb_try > 0) {
			color++;
			nb_color = goodColor(memory[i - 1], 1);
			for (int j = 0; j < nb_color; j++) {
				memory[i][j] = memory[i - 1][j];
			}
			for (int j = nb_color; j < combination_size; j++) {
				memory[i][j] = color;
			}
			i++;
			nb_try--;
		}
		fillArray(memory[i], -1);
		return memory;
	}

	private int findLastLine(int[][] memory) {
		int i = 0;
		int[] testLine = new int[combination_size];
		fillArray(testLine, -1);
		while (!Arrays.equals(testLine, memory[i])) {
			i++;
		}
		return i;
	}

	public int[][] findPosition() {
		int memory[][] = findColor();
		int i = findLastLine(memory);
		int j = 0;
		int nbPosition = goodPosition(memory[i-1], 1);
		int temp_nbPosition = nbPosition;
		int[] tempCombination = new int[combination_size];
		copyArray(tempCombination, memory[i-1]);
		System.out.println(i);
		int k = 1;
		while (nb_try > 0 && !Arrays.equals(AIcombination, memory[i-1])) {
			if (tempCombination[k] == -1) {
				k++;
			} else {
				copyArray(memory[i], memory[i-1]);
				memory[i][j] = tempCombination[k];
				nbPosition = goodPosition(memory[i], 1);
				if (nbPosition == temp_nbPosition) {
					k++;
				} else if (nbPosition == temp_nbPosition + 1) {
					j++;
					temp_nbPosition = nbPosition;
					tempCombination[k] = -1;
					k = 0;
				} else if (nbPosition == temp_nbPosition-1) {
					k++;
					temp_nbPosition = nbPosition;
				}
				i++;
				nb_try--;
			}
		}
		return memory;
	}
	
	public void defender() {

		System.out.println("Choose the combination [" + this.combination_size + "] the AI has to find : ");
		int[] AICombi = testInput();
		SetAICombination(AICombi);
		int[][] memory = findPosition();
		int k = -1;
		do {
			k++;
			printTab(memory[k]);
			System.out.println();
		} while (!(Arrays.equals(this.AIcombination, memory[k])) && (k < memory.length));
		endGame(memory[k], "AI");
}
	
	public void duel() {
		boolean YourTurn = true;
		boolean FirstTurn = true;
		int[] temp = null;
		int i = 0;
		System.out.println("Choose the combination [" + this.combination_size + "] the AI has to find : ");
		int[] AICombi = testInput();
		SetAICombination(AICombi);
		int[][] memory = findPosition();

		while ((i < memory.length) && !(Arrays.equals(this.combination, temp)) && !(Arrays.equals(this.AIcombination, memory[i]))) {
			if (YourTurn) {
				System.out.print("Proposition : ");
				temp = testInput();
				response(temp);
				System.out.println();
				YourTurn = false;
			} else {
				System.out.print("------------------- AI propose : ");
				if(FirstTurn) {// to print the first step of the AI
					FirstTurn = false;
				}
				else {
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
	
//	public void test() {
//		int combi[] = { 3, 6, 3, 1, 2 };
//		SetAICombination(combi);
//		int[][] memory = findPosition();
//		for (int line[] : memory) {
//			printTab(line);
//			System.out.println();
//		}
//	}

}
