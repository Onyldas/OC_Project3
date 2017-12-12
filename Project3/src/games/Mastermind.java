package games;

public class Mastermind extends Game {

	// --------------------------Instance Attributes------------------------

	// -----------------------------Constructors----------------------------
	public Mastermind() {
		super();
	}

	public Mastermind(int combination_size, int nb_try) {
		super(combination_size, nb_try);
	}
	// -------------------------------Getters-------------------------------

	// -------------------------------Setters-------------------------------

	// -------------------------------Methods-------------------------------
	private int goodColor(int tryy[]) {
		int color = 0;
		for (int i = 0; i < this.combination_size; i++) {
			for (int j = 0; j < this.combination_size; j++) {
				if (this.combination[j] == tryy[i]) {
					color++;
				}
			}
		}
		return color;
	}

	private int goodPosition(int tryy[]) {
		int position = 0;
		for (int i = 0; i < this.combination_size; i++) {
			if (this.combination[i] == tryy[i]) {
				position++;
			}
		}
		return position;
	}
}
