package games;

import java.util.Arrays;
import java.util.InputMismatchException;

public class PoM extends Game {

	// Instance Attributes

	// Constructors
	public PoM() {
		super();
	}

	public PoM(int combination_size, int nb_try) {
		super(combination_size, nb_try);
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
	
	public void challenger(){
		int temp[]= new int[this.combination_size];
		int count = 0;
		int nb_tryy = 7;
		while (count < nb_tryy && !(Arrays.equals(this.combination,temp))) {
			try {
				temp = this.tryy();
				this.response(temp);
				count++;
			} catch (CombinationException e) {
			} catch (InputMismatchException e2) { logger.fatal("Not an integer !");
			} finally {
				System.out.println();
			}
		}
		if (Arrays.equals(this.combination,temp))
		{
			System.out.println("\nYou win !");
		}
		else { System.out.println("\nGame Over !");}
	}
	
	
	public void ia() {
		int [][] memory = new int [nb_try][combination_size];
		for (int k = 0; k < combination_size; k++) {
			memory[0][k] = 5;
		}
		for (int i = 1; i < nb_try; i++) {
			for(int j = 0; j < combination_size ; j++) {
				int inf = 1;
				int sup = 9;
				if(memory[i-1][j] < combination[j]) {
					inf = memory[i-1][j];
					if (sup - inf == 1) {
						memory[i][j] = sup;
					}
					else {
						memory[i][j] = (sup+inf)/2;
					}
				}
				else if(memory[i-1][j] > combination[j]) {
					sup = memory[i-1][j];
					if(i>1 && memory[i-2][j] < combination[j]) {inf = memory[i-2][j];} // case 4
					if (sup - inf == 1) {
						memory[i][j] = inf;
					}
					else {
						memory[i][j] = (sup+inf)/2;
					}
				}
				else {
					memory[i][j] = memory[i-1][j];
				}
			}
		}
		
		for (int tryy[] : memory)
		{
			printTab(tryy);
			System.out.println();
		}
	}
}
