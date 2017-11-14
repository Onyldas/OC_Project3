package games;

public class Main {

	public static void main(String[] args) {
		PoM game = new PoM();
		for(int k=0; k<10;k++)
		{
			game.response(game.tryy());
			System.out.println();
		}
		
		
		
	}

}
