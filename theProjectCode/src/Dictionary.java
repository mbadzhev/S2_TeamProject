import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

public class Dictionary {
	private String endbFilename = "englishdatabase.txt";
	private String dedbFilename = "germandatabase.txt";
	private final static String HOST = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
	private final static String KEY = "trnsl.1.1.20190305T204312Z.dfa16cd3173c9caf.d933df65b77daf7957e97b8e2e6571320a9823a3";
	private PrintWriter enwriter;
	private PrintWriter dewriter;
	// TODO remove enwriter and dewriter and make the skaleable
	private PrintWriter theWriter;
	private HashMap<String, HashMap<String, String>> partsMap;

	/**
	 * Reads dictionary files and loads to hashmaps Opens writers to write new words
	 * (so it does not have to open every single time)
	 */
	public Dictionary() {
		partsMap = new HashMap<String, HashMap<String, String>>();
		partsMap.put("en-de", new HashMap<String, String>());
		partsMap.put("de-en", new HashMap<String, String>());

		// create reader and writer objects
		try {
			enwriter = new PrintWriter(new FileOutputStream(endbFilename, true));
			dewriter = new PrintWriter(new FileOutputStream(dedbFilename, true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a filename, this function parses a dictionary from that file to a given
	 * HashMap.
	 *
	 * @param filename the file where the dictionary is store in.
	 * @param map      the HashMap to store the dictionary to.
	 * @throws IOException
	 */
	public void loadDictionary(String filename, String direction) throws IOException {
		HashMap<String, String> map;
		if (partsMap.containsKey(direction)) {
			map = partsMap.get(direction);
		} else {
			// add new HashMap
			map = new HashMap<String, String>();
			partsMap.put(direction, map);
		}
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while (reader.ready()) {
			line = reader.readLine();
			String[] mappings = line.split(" ");
			map.put(mappings[0], mappings[1]);
		}
		reader.close();
		// TODO check if the format of the dictionary is right
		// TODO support UTF-8 / Umlaut
	}

	/**
	 * this function saves a dictionary to a file. The filename of the file will be
	 * the in the form [direction].txt. E.g. saving the direction "de-en" produces a
	 * file called "de-en.txt".
	 * 
	 */
	public void saveDictionary() {

		// TODO complete the code for this function. Use an iterator.
	}

	/**
	 * translates a single word
	 * 
	 * @param word
	 * @param toGerman toGerman or false toEnglish
	 * @return translation
	 */
	public String translate(String word, String direction) {
		// check if the dictionary has the direction
		if (!partsMap.containsKey(direction)) {
			return "-1";
		}
		HashMap<String, String> currentDictionary = partsMap.get(direction);

		if (word.equals("")) {
			return word;
		}
		if (currentDictionary.containsKey(word)) {
			return currentDictionary.get(word);
		}
		try {
			URL url = new URL(HOST + "key=" + KEY + "&text=" + word + "&lang=" + direction);
			InputStream is = url.openStream();
			Scanner s = new Scanner(is);
			String translation = parseJSONTranslation(s.nextLine());
			if (!word.equals(translation)) {
				saveWord(word, translation, direction);
			}
			return translation;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Saves words not already in the dictionary. The words will be added to the
	 * 
	 * @param word
	 * @param translation
	 * @param toGerman
	 */
	private void saveWord(String word, String translation, String direction) {
		try {
			theWriter = new PrintWriter(new FileOutputStream(direction + ".txt", true));
			// TODO remvoe theWriter and create a HashMap of writers for every direction
		} catch (FileNotFoundException e) {
			System.out.println("fail");
		}

		HashMap<String, String> currentDictionary = partsMap.get(direction);

		currentDictionary.put(word, translation);
		if (theWriter != null) {
			theWriter.append(word + " " + translation + "\n");
			theWriter.flush();
		}
//		if (enwriter != null) {
//			enwriter.append(word + " " + translation + "\n");
//			enwriter.flush();
//		}
		try {
			theWriter = new PrintWriter(new FileOutputStream(getInverseDirection(direction) + ".txt", true));
		} catch (FileNotFoundException e) {

		}
		currentDictionary = partsMap.get(getInverseDirection(direction));
		currentDictionary.put(word, translation);
		if (theWriter != null) {
			theWriter.append(word + " " + translation + "\n");
			theWriter.flush();
		}
	}

	/**
	 * determines the inverse direction of a direction. A direction could be "en-de"
	 * or "fr-de". If the direction is "en-de", the inverse will be "de-en". Doesn't
	 * check for direction validity
	 * 
	 * @param direction
	 * @return
	 */
	private String getInverseDirection(String direction) {
		String[] languages = direction.split("-");
		return languages[1] + "-" + languages[0];
	} // TODO check for validity of direction

	/**
	 * Parses json translation given by API
	 * 
	 * @param json
	 * @return value of translation
	 */
	private String parseJSONTranslation(String json) {
		int textIndex = json.lastIndexOf("text") + 7;
		int textEndIndex = textIndex + json.substring(textIndex).lastIndexOf("]");
		String translatedWords = json.substring(textIndex, textEndIndex);
		translatedWords.trim();
		translatedWords = translatedWords.substring(1, translatedWords.length() - 1);
		return translatedWords;
	}

	/**
	 * parses json language detection given by API
	 * 
	 * @param json
	 * @return language en or de
	 */
	private String parseJSONDetect(String json) {
		int langIndex = json.lastIndexOf("lang") + 8;
		String language = json.substring(langIndex, langIndex + 2);
		return language;
	}

	/**
	 * removes key-value from dictionary
	 * 
	 * @param key
	 */
	public void removeFromDictionary(String key, String direction) {
		partsMap.get(direction).remove(key);
	}

	/**
	 * adds key-value to dictionary
	 * 
	 * @param key
	 * @param value
	 * @param toEnglishToGerman
	 */
	public void addToDictionary(String key, String value, String direction) {
		partsMap.get(direction).put(key, value);

	}

	/**
	 * detects language of given word
	 * 
	 * @param word
	 * @return english or german or not found or both
	 */
	public String detectLanguage(String word) {
//		int count = 0;
		String ret = "not found";
//		if (englishToGermanDictionary.containsKey(word)) {
//			ret = "english";
//			count++;
//		}
//		if (germanToEnglishDictionary.containsKey(word)) {
//			ret = "german";
//			count++;
//		}
//		if (count == 2) {
//			return "both";
//		} else if (count == 0) {
//			URL url;
//			try {
//				url = new URL("https://translate.yandex.net/api/v1.5/tr.json/detect?" + "key=" + key + "&text=" + word);
//				InputStream is = url.openStream();
//				Scanner s = new Scanner(is);
//				String language = parseJSONDetect(s.nextLine());
//				if (language.equals("en")) {
//					language = "english";
//				} else {
//					language = "german";
//				}
//				return language;
//			} catch (Exception e) {
//			}
//		}
		return ret;
	}

	public void closeDictionary() {
		enwriter.close();
		dewriter.close();
	}

	/**
	 * returns a whole dictionary as a String
	 * 
	 * @param direction the dictionary to display
	 * @return a dictionary as a String
	 */
	public String displayDictionary(String direction) {
		// TODO work on this function
		return "I'm a dictionary";
	}
}