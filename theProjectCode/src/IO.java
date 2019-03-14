
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to receive user input from the command line. There are methods for
 * receiving some primitive datatypes as well as Strings. To receive user input,
 * call a getDatatype() function as you would call any static method.
 * 
 * <pre>
 * int myNumber = IO.getInt("please enter an integer");
 * </pre>
 * 
 * The example above will display the user the message
 * <code>please enter an integer</code>, gives the user the possibility to enter
 * input and then returns the value the user entered as an integer. If input is
 * invalid, the user will be asked to enter a value again until he enters valid
 * input. To avoid asking continuously for input if input is invalid, set the
 * second parameter of a getDatatype() function to false.
 * 
 * <pre>
 * int myNumber = IO.getInt("please enter an integer", false);
 * </pre>
 * 
 * This will only ask the user one time to enter input and throws an exception
 * if the input is invalid. Only deals with String, boolean, int and double.
 * 
 * 
 * @author Bjarne Kopplin
 * @version 1.0
 *
 */
public class IO {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final String[] TRUE_INPUT = { "y", "yes", "true", "t", "ja", "j", "1" }; // for getBoolean()
	private static final String[] FALSE_INPUT = { "n", "no", "false", "f", "nein", "0" }; // for getBoolean()
	private static final String CURSOR_SYMBOL = ">";

	/**
	 * allows the user to enter text and return it as String. (<em>There is no
	 * method getString that repeats asking if input is invalid.)</em>
	 * 
	 * @param message the message that asks the user to enter data
	 * @return user input as String
	 */
	public static String getString(String message) {
		System.out.println(message);
		System.out.print(CURSOR_SYMBOL);
		try {
			return reader.readLine();
		} catch (IOException e) {

			return e.getMessage();
		}
	}

	/**
	 * transforms user input into an int. Asks for input until user enters valid
	 * input.
	 * 
	 * @param message the String that should be displayed before the user can enter.
	 * @return value the user entered as int.
	 */
	public static int getInt(String message) {
		return getInt(message, true);
	}

	/**
	 * transforms user input into int.
	 * 
	 * @param message      the String that should be displayed before the user can
	 *                     enter.
	 * @param repeatAsking if true: asks for input until user enters valid input. If
	 *                     false: asks user once for input.
	 * @return value the user entered as int.
	 */
	public static int getInt(String message, boolean repeatAsking) {
		String str = getString(message);
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
			return temp;
		} catch (Exception e) {
			if (repeatAsking == true) {
				return getInt("Invalid Input. Please enter a number!", true);
			} else {
				return Integer.parseInt(str);
			}

		}
	}

	/**
	 * transforms user input into an boolean.
	 * 
	 * @param message the String that should be displayed before the user can enter.
	 * @return value the user entered as boolean.
	 */
	public static boolean getBoolean(String message) {
		String str = getString(message + " (Y/n)");
		for (String candidate : TRUE_INPUT) {
			if (str.equals(candidate)) {
				return true;
			}
		}
		for (String candidate : FALSE_INPUT) {
			if (str.equals(candidate)) {
				return true;
			}
		}
		// if String neither corresponds to true nor false
		return getBoolean("please enter true or false");
	}

	/**
	 * transforms user input into double. Asks for input until user enters valid
	 * input.
	 * 
	 * @param message the String that should be displayed before the user can enter.
	 * @return value the user entered as double.
	 */
	public static double getDouble(String message) {
		return getDouble(message, true);
	}

	/**
	 * transforms user input into double.
	 * 
	 * @param message      the String that should be displayed before the user can
	 *                     enter.
	 * @param repeatAsking if true: asks for input until user enters valid input. If
	 *                     false: asks user once for input.
	 * @return value the user entered as double.
	 */
	public static double getDouble(String message, boolean repeatAsking) {
		String str = getString(message);
		double temp = 0;
		try {
			temp = Double.parseDouble(str);
			return temp;
		} catch (Exception e) {
			if (repeatAsking == true) {
				return getDouble("Invalid Input. Please enter a number!", true);
			} else {
				return Double.parseDouble(str);
			}

		}
	} // end of getDouble

} // end of class
