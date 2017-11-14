package games;

public class PoM extends Game {

	// Instance Attributes

	// Constructors
	public PoM() {
		super();
	}

	// Methods
	public void response(int proposition_tab[]) {
		super.response();
		for (int k = 0 ; k < combination_size ; k++){
			if (proposition_tab[k] < this.combination[k]) {
				System.out.print("+");
			}
			else if (proposition_tab[k] > this.combination[k]) {
				System.out.print("-");
			}
			else {
				System.out.print("=");
			}
		}
		
		
	}
}
