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
		while (count < nb_try && !(Arrays.equals(this.combination,temp))) {
			try {
				System.out.print("Proposition : ");
				temp = tryy();
				response(temp);
				count++;
			} catch (CombinationException e) {
			} catch (InputMismatchException e2) { logger.fatal("Not an integer !");
			} finally {
				System.out.println();
			}
		}
		if (Arrays.equals(this.combination,temp)) { System.out.println("\nYou win !"); }
		
		else { System.out.println("\nGame Over !"); }
	}
	
	
	public int[][] ia(int combination[]) {// using dichotomy method
		int [][] memory = new int [nb_try][combination_size];
		for (int k = 0; k < combination_size; k++) {// IA begin with 5555... combination
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
		return memory;
	}
	
	public void defender() {
		try {
			System.out.println("Choose the combination [" + this.combination_size + "] the IA has to find : ");
			int [] IaCombi = this.tryy();
			SetCombination(IaCombi);
			int[][] memory = ia(IaCombi);
			int k = -1 ;
			do {
				k++;
				printTab(memory[k]);
				System.out.println();
			} while (!(Arrays.equals(this.combination,memory[k])) && k<nb_try);
			
			if (Arrays.equals(this.combination,memory[k])) { System.out.println("IA win !"); }
			else { System.out.println("IA loose !"); }
			
		} catch (CombinationException e) {
			logger.error(e,e);
		}
	}
	
	
	public void duel() throws CombinationException {
		boolean YourTurn = true;
		int [] temp = null;
		int count = 0;
		System.out.println("Choose the combination [" + this.combination_size + "] the IA has to find : ");
		int [] IaCombi = tryy();
		int[][] memory = ia(IaCombi);
		
		while ((count < nb_try) && !(Arrays.equals(this.combination,temp)) && !(Arrays.equals(IaCombi,memory[count]))) {
			if (YourTurn) {
				System.out.print("Proposition : ");
				temp = tryy();
				response(temp);
				System.out.println();
				YourTurn = false;
			}
			else {
				System.out.print("------------------- AI propose : ");
				printTab(memory[count]);
				System.out.println();
				YourTurn = true;
				count++;
			}
		}
		if (Arrays.equals(this.combination,temp)) { System.out.println("\nYou win !"); }
		else if (Arrays.equals(IaCombi,memory[count])) { System.out.println("\nIA win !"); }
		else { System.out.println("\nYou both lose."); }
	}
	
}
