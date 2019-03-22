import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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
	 */
	public void translateFile(String filename, String translationFilename, String direction) {
		// TODO count time and return
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			PrintWriter writer = new PrintWriter(new FileOutputStream(translationFilename));
			while (reader.ready()) {
				String line = reader.readLine();
				String[] sentences = line.split("[\\.?]");
				for (int i = 0; i < sentences.length; i++) {
					sentences[i].trim();
					String[] words = sentences[i].split(" ");
					String translatedSentence = "";
					for (int j = 0; j < words.length; j++) {
						// may want to change this

						translatedSentence = translatedSentence.concat(dictionary.translate(words[j], direction)) + " ";
					}
					writer.print(translatedSentence + ". ");
				}
				writer.println();
			}
			writer.close();
			reader.close();
		} catch (Exception e) {

		}
	}

	/**
	 * loads a dictionary from a file
	 * 
	 * @param fileName  the file name of the dictionary
	 * @param direction the direction of the dictionary in the file
	 * @throws IOException
	 */
	public void loadDictionaryFromFile(String fileName, String direction) throws IOException {
		dictionary.loadDictionary(fileName, direction);
		// TODO method to check if direction is valid.
	}

	/**
	 * Saves all dictionaries to seperate files in the form [direction].txt.
	 * 
	 * 
	 */
	public void saveDictionaryToFile() {
		dictionary.saveDictionary();
	}

	/**
	 * translates a word from one language into an other. For example,
	 * translate("dog", "en", "de") would return "Hund".
	 * 
	 * @param input          the text to be translated
	 * @param language       the language the text is in
	 * @param targetLanguage the language the text should be translated to.
	 * @return the translated text
	 */
	public String translate(String input, String language, String targetLanguage) {
		return dictionary.translate(input, language + "-" + targetLanguage);
	}

	/**
	 * translates a word from one language into an other. For example,
	 * translate("dog", "en", "de") would return "Hund".
	 * 
	 * @param input    the text to be translated
	 * @param diretion the direction of the translation
	 * @return the translated text
	 */
	public String translate(String input, String direction) {
		return dictionary.translate(input, direction);
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
}
