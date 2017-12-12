package games;

public class CombinationException extends Exception{

	private static final long serialVersionUID = 1L; //The serializable class CombinationException must declare a static final serialVersionUID field of type long

	public CombinationException(int combination_size) {
		System.out.println("You must enter " + combination_size + " numbers between 1 and 9");
	}

}
