package games;
import java.util.Scanner;

public class Game implements Challenger {
	
	Scanner sc = new Scanner(System.in);
	// Instance attributes
	protected int combination_size;
	protected int nb_try;
	protected int[] combination;

	// Constructors
	public Game() {
		this.combination_size = 4;
		this.nb_try = 20;
		this.combination = randomCombination();
	}

	public Game(int combination_size, int nb_try) {
		this.combination_size = combination_size;
		this.nb_try = nb_try;
		this.combination = randomCombination();
	}

	// Methods
	public int[] randomCombination() { // Creation of the combination the player/IA has to find when the object is created
		int combination[] = new int[combination_size];
		for (int k = 0; k < combination_size; k++) {
			combination[k] = (int) (Math.random() * (double) (10 - 0)) + 0;
		}
		return combination;
	}

	public static void printTab(int[] tab) {
		System.out.print("[");
		for (int i = 0; i < tab.length; i++) {
			System.out.print(tab[i]);
			if (i != tab.length - 1) {
				System.out.print(",");
			}
		}
		System.out.print("]");
	}

	public int[] tryy() {
		System.out.print("Proposition : ");
		int proposition = sc.nextInt();
		int [] proposition_tab = new int[combination_size];
		for (int k=0; k<combination_size;k++) {
			proposition_tab[combination_size-1-k] = (int) (proposition % Math.pow(10, (k+1))/Math.pow(10, k)); 
		}
		return proposition_tab;
	}
	
	public void response() {
		System.out.print(" -> Réponse : ");
	}
	
	public void tryy(int[] tab) {
		
	}
}
