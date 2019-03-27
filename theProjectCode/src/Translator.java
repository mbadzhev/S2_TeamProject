import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Translator {
	private Dictionary dictionary;

	public Translator() {
		dictionary = new Dictionary();
	}

	/**
	 * Opens file, translates and outputs to another file
	 * 
	 * @param filename            name of file to translate
	 * @param translationFilename name of file to output translation
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
				String[] words=line.split(" ");
				for (int i=0;i<words.length;i++) {
					writer.print(dictionary.translate(words[i], direction)+" ");
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
	 * loads a dictionary from a file
	 * 
	 * @param direction the direction of the dictionary in the file
	 * @throws IOException
	 */
	public void loadDictionary(String direction) throws IOException, Exception {
		dictionary.loadDictionary(direction);
		// TODO method to check if direction is valid.
	}

	/**
	 * Saves given direction to .txt
	 * 
	 * @direction
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
		String[] lines=input.split("\n");
		String trans="";
		for (int j=0;j<lines.length;j++) {
			String[] words=lines[j].split(" ");
			for (int i=0;i<words.length;i++) {
				trans=trans.concat(dictionary.translate(words[i], direction)+" ");
			}
			trans=trans.concat("\n");
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

	public boolean getAutomaticAdding() {
		return dictionary.getAutomaticAdding();
	}
	public void toggleAutomaticAdding() {
		dictionary.toggleAutomaticAdding();
	}
	/**
	 * adds key-value to dictionary
	 * 
	 * @param key
	 * @param value
	 * @param toEnglishToGerman
	 */
	public void addToDictionary(String key, String value, String direction) {
		dictionary.addToDictionary(key,value,direction);
	}
	/**
	 * Returns all loaded dictionaries
	 * @return
	 */
	public ArrayList<String> getLoadedDictionaries() {
		return dictionary.getLoadedDictionaries();
	}
	/**
	 * Returns all supported dictionaries (even if not currently loaded)
	 * @return
	 */
	public ArrayList<String> getSupportedDirections() {
		return dictionary.getSupportedDirections();
	}
}
