import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
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
	 * @param fileName the file name of the dictionary
	 */
	public void loadDictionaryFromFile(String fileName) {

	}

	/**
	 * Saves both the english end the german
	 * 
	 * @param fileName
	 */
	public void saveDictionaryToFile(String fileName) {

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
	
	public String translate(String input, String direction) {
		return dictionary.translate(input, direction);
	}
}
