package games;

import java.util.Arrays;

/**
 * <h4>Mastermind class</h4>
 * <p>
 * The second combination Game 
 * Here, the attribute colors represent the number of colors that there might be in the combination.
 * </p>
 * @author Guillaume
 * @version 4.7
 */
public class Mastermind extends Game {

	// --------------------------Instance Attributes------------------------
	private int colors;

	// -----------------------------Constructors----------------------------
	/**
	 * Construction of the Mastermind game 
	 * It needs three parameters :
	 * @param combination_size
	 * @param nb_try
	 * @param colors
	 * Here we use super method
	 * The combination/AIcombination are created with random method.
	 * @see Game#randomCombination
	 * @see Game#Game(int, int)
	 */
	public Mastermind(int combination_size, int nb_try, int colors) {
		super(combination_size, nb_try);
		this.colors = colors;
	}

	// -------------------------------Methods-------------------------------
	/**
	 * @param select
	 * @return the combination of the AI or the user
	 */
	private int[] selectPlayer(int select) {
		if (select == 0) {
			return this.combination;
		} else {
			return this.AIcombination;
		}
	}
	
	/**
	 * Compare to arrays and return the number of
	 * @param tryy
	 * @param select
	 * @return
	 */
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
		while (i < this.colors + 1 && nb_color != combination_size && i < nb_try+2) {
			color++;
			nb_color = goodColor(memory[i - 1], 1);
			for (int j = 0; j < nb_color; j++) {
				memory[i][j] = memory[i - 1][j];
			}
			for (int j = nb_color; j < combination_size; j++) {
				memory[i][j] = color;
			}
			i++;
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

	protected int[][] AI() {
		int memory[][] = findColor();
		int i = findLastLine(memory);
		int j = 0;
		int nbPosition = goodPosition(memory[i-1], 1);
		int temp_nbPosition = nbPosition;
		int[] tempCombination = new int[combination_size];
		copyArray(tempCombination, memory[i-1]);
		System.out.println(i);
		int k = 1;
		while (i < nb_try+2 && !Arrays.equals(AIcombination, memory[i-1])) {
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
	

}
