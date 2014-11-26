
public class main {

	/**
	 * This program prints "Hello" along with the names provided to the function. If no names
	 * are provide, the function will print "Hello World".
	 * 
	 * @param names Names that this function should say hello to
	 */
	public static void main(String[] names) {
		if (names.length == 0) {
			System.out.println("Hello World");
		}
		else {
			for(String name : names) {
				System.out.println("Hello " + name);
			}
		}		
	}
}
