package games;

import java.util.Arrays;

/**
 * <h4>Mastermind class</h4>
 * <p>
 * The second combination Game
 * Here, the attribute colors represent the number of colors that there might be in the combination.
 * </p>
 * @author Guillaume FRANCOIS
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
	 * Compare two arrays and give the number of common points
	 * There are two loops if there are duplicates
	 * @param tryy represent the try of the player
	 * @param select represent the combination the player has to find
	 * @return the number of good color in the player try
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


	/**
	 *
	 * @param tryy represent the try of the player
	 * @param select represent the combination the player has to find
	 * @return the number of good position the player has
	 */
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


	/**
	 * Void method which print the number of good position and good color the player has
	 * @param proposition_tab
	 */
	public void response(int proposition_tab[]) {
		super.response(proposition_tab);
		int position = goodPosition(proposition_tab, 0);
		int color = goodColor(proposition_tab, 0);
		System.out.print(position + " good position(s) and " + color + " good color(s).");

	}


	/**
	 * First part of AI's script to find the good combination
	 * In this method, the AI try color by color and don't use positions info
	 * If there's x good color(s) it'll add x colors to the array and then do it again with the next color
	 * The method stock every try of the AI in a two-dimensional array called memory
	 * @return memory array
	 * @see Mastermind#AI()
	 * @see Mastermind#findLastLine(int[][])
	 */
	private int[][] findColor() {
		int i = 1;
		int color = 0;
		int[][] memory = new int[nb_try+4][combination_size];
		fillArray(memory[0], color);
		int nb_color = goodColor(memory[0], 1);

		while (nb_color == 0) {// AI fill the combination till it finds one goodColor minimum
			color++;
			fillArray(memory[0], color);
			nb_color = goodColor(memory[0], 1);
		}

		while (i < this.colors + 1 && nb_color != combination_size && i < memory.length) {
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


	/**
	 * Take the memory array and return the line where the findColor() method stops
	 * @see Mastermind#findColor()
	 * @param memory
	 * @return
	 */
	private int findLastLine(int[][] memory) {
		int i = 0;
		int[] testLine = new int[combination_size];
		fillArray(testLine, -1);

		while (!Arrays.equals(testLine, memory[i])) {
			i++;
		}
		return i;
	}


	/**
	 * The second part of AI's script to find the good combination
	 * It takes the memory array filled by @see {@link Mastermind#findColor()}
	 * With the previous method the AI has all good color but not in good position
	 * Then this method will switch the colors one by one and if there's one more good position,
	 * it will pass to the next cell
	 * @return the two-dimensional array memory
	 */
	protected int[][] AI() {
		int memory[][] = findColor();
		int i = findLastLine(memory) - 1;
		int j = 0;
		int nbPosition = goodPosition(memory[i], 1);
		int temp_nbPosition = nbPosition;
		int[] tempCombination = new int[combination_size];
		copyArray(tempCombination, memory[i]);
		int k = 1;

		while (i < memory.length && !Arrays.equals(AIcombination, memory[i-1])) {
			if (tempCombination[k] == -1) {
				k++;
			}

			else {
				copyArray(memory[i], memory[i-1]);
				memory[i][j] = tempCombination[k];
				nbPosition = goodPosition(memory[i], 1);

				if (nbPosition == temp_nbPosition) {
					k++;
				}

				else if (nbPosition == temp_nbPosition + 1) {
					j++;
					temp_nbPosition = nbPosition;
					tempCombination[k] = -1;
					k = 0;
				}

				else if (nbPosition == temp_nbPosition-1) {
					k++;
					temp_nbPosition = nbPosition;
				}
				i++;
			}
		}
		return memory;
	}

}