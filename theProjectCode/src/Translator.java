import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * this class handles translations and provides wrapper methods to handle the
 * Dictionary class.
 * 
 * @author group 1
 *
 */
public class Translator {
	private Dictionary dictionary;

	/**
	 * creates an instance of Translator
	 */
	public Translator() {
		dictionary = new Dictionary();
	}

	/**
	 * Opens file, translates and outputs to another file
	 * 
	 * @param filename            name of file to translate
	 * @param translationFilename name of file to output translation
	 * @param direction           the direction to translate the file
	 * @throws FileNotFoundException thrown if the file couldn't be found
	 * @throws DirectionException    thrown if there is a problem with the
	 *                               direction. the getMessage() to get more
	 *                               information about the problem
	 * @return words translated per second
	 */
	public double translateFile(String filename, String translationFilename, String direction)
			throws DirectionException, FileNotFoundException {

		long startTime = System.nanoTime();
		int wordCount = 0;
		// check if the direction is loaded in the dictionary
		if (!dictionary.getLoadedDictionaries().contains(direction)) {
			throw new DirectionException("the direction " + direction + " does not exist");
		}
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		PrintWriter writer = new PrintWriter(new FileOutputStream(translationFilename));
		try {
			while (reader.ready()) {
				String line = reader.readLine();

				if (line.equals("")) {
					continue;
				}
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {

					writer.print(dictionary.translate(words[i], direction) + " ");

					wordCount++;
				}
				writer.println();
			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		double elapsedTime = (System.nanoTime() - startTime) * 0.000000001;
		return wordCount / elapsedTime;
	}

	/**
	 * loads a dictionary from a file. If the file doesn't exists, a new file will
	 * be created.
	 * 
	 * @param direction the direction of the dictionary in the file
	 * @throws IOException exceptions passed from the Dictionary class
	 */
	public void loadDictionary(String direction) throws IOException, Exception {
		dictionary.loadDictionary(direction);
	}

	/**
	 * Saves all dictionaries to a files
	 * 
	 */
	public void saveDictionaryToFile() {
		dictionary.saveDictionary();
	}

	/**
	 * translates a string from one language into an other. For example,
	 * translate("dog", "en", "de") would return "Hund".
	 * 
	 * @param input     the text to be translated
	 * @param direction direction
	 * @return the translated text
	 * @throws Exception unable to translate (probably wrong direction)
	 */
	public String translate(String input, String direction) throws Exception {
		String[] lines = input.split("\n");
		String trans = "";
		for (int j = 0; j < lines.length; j++) {
			String[] words = lines[j].split(" ");
			for (int i = 0; i < words.length; i++) {
				trans = trans.concat(dictionary.translate(words[i], direction) + " ");
			}
			trans = trans.concat("\n");
		}
		return trans;
	}

	/**
	 * removes a word or a sentence from the dictionary
	 * 
	 * @param text      the word or sentence to be removed.
	 * @param direction the dictionary the word should be removed from.
	 */
	public void removeFromDictionary(String text, String direction) {
		dictionary.removeFromDictionary(text, direction);
	}

	/**
	 * returns a whole dictionary as a String
	 * 
	 * @param direction the dictionary to display
	 * @return a dictionary as a String
	 */
	public String displayDictionary(String direction) {
		return dictionary.displayDictionary(direction);
	}

	/**
	 * detects a language of a given String
	 * 
	 * @param text the String to detect the language from
	 * @return the language of the String
	 */
	public String detectLanguage(String text) {
		return dictionary.detectLanguage(text);
	}

	/**
	 * get the status of the field automaticAdding.
	 * 
	 * @return the status of the field automaticAdding.
	 */
	public boolean getAutomaticAdding() {
		return dictionary.getAutomaticAdding();
	}

	/**
	 * toggles the state of automaticAdding
	 */
	public void toggleAutomaticAdding() {
		dictionary.toggleAutomaticAdding();
	}

	/**
	 * adds key-value to dictionary
	 * 
	 * @param key       the key to add to the dictionary
	 * @param value     the translation of the key
	 * @param direction the dictionary to add the translation to
	 */
	public void addToDictionary(String key, String value, String direction) {
		dictionary.addToDictionary(key, value, direction);
	}

	/**
	 * Returns all loaded dictionaries
	 * 
	 * @return all currently loaded dictionaries
	 */
	public ArrayList<String> getLoadedDictionaries() {
		return dictionary.getLoadedDictionaries();
	}

	/**
	 * Returns all supported dictionaries (even if not currently loaded)
	 * 
	 * @return all supported dictionaries
	 */
	public ArrayList<String> getSupportedDirections() {
		return dictionary.getSupportedDirections();
	}
}
