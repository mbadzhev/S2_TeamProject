import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

public class Dictionary {
	private final static String HOST = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
	private final static String KEY = "trnsl.1.1.20190305T204312Z.dfa16cd3173c9caf.d933df65b77daf7957e97b8e2e6571320a9823a3";
	private HashMap<String, PrintWriter> writers;
	private HashMap<String, HashMap<String, String>> partsMap;
	private HashMap<String, String> dictionaryPaths;
	private ArrayList<String> directions;
	private ArrayList<String> supportedDirections;
	private boolean automaticAdding;

	/**
	 * Reads dictionary files and loads to hashmaps Opens writers to write new words
	 * (so it does not have to open every single time)
	 */
	public Dictionary() {
		supportedDirections = new ArrayList<String>();
		supportedDirections.add("en-de");
		supportedDirections.add("de-en");
		supportedDirections.add("fr-en");
		supportedDirections.add("en-fr");
		supportedDirections.add("en-es");
		supportedDirections.add("es-en");
		automaticAdding = true;
		writers = new HashMap<String, PrintWriter>();
		partsMap = new HashMap<String, HashMap<String, String>>();
		dictionaryPaths = new HashMap<String, String>();
		directions = new ArrayList<String>();
		try {
			loadDictionary("en-de");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialises hashmaps for a direction
	 * 
	 * @param direction
	 */
	public void initialiseDirection(String direction) {
		partsMap.put(direction, new HashMap<String, String>());
		directions.add(direction);
		dictionaryPaths.put(direction, direction + ".txt");
		File file = new File(direction + ".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		try {
//			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
			String line;
			while (reader.ready()) {
				line = reader.readLine();
				String[] mappings = line.split(">");
				partsMap.get(direction).put(mappings[0], mappings[1]);
			}
			reader.close();
		} catch (Exception e) {
		}
		try {
			writers.put(direction, new PrintWriter(new FileOutputStream(dictionaryPaths.get(direction), true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a filename, this function parses a dictionary from that file to HashMap
	 * of direction. If it already exists or it can't load it for whatever reason it
	 * throws an Exception
	 *
	 * @param filename the file where the dictionary is store in.
	 * @param map      the HashMap to store the dictionary to.
	 * @throws IOException
	 */
	public void loadDictionary(String direction) throws Exception {
		// Will throw exception if FileReader can't load it or already loaded
		if (!directions.contains(direction)) {
			initialiseDirection(direction);
			initialiseDirection(getInverseDirection(direction));
		} else {
			throw new Exception("already loaded");
		}
	}

	/**
	 * this function saves all dictionaries using the hashmaps. replaces existing
	 * files
	 * 
	 */
	public void saveDictionary() {
		for (String direction : directions) {
			try {
				PrintWriter rewriter = new PrintWriter(
						new FileOutputStream(new File(dictionaryPaths.get(direction)), false));
				for (Map.Entry<String, String> keyValuePair : partsMap.get(direction).entrySet()) {
					rewriter.println(keyValuePair.getKey() + ">" + keyValuePair.getValue());
				}
				rewriter.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * translates a single word
	 * 
	 * @param word
	 * @param toGerman toGerman or false toEnglish
	 * @return translation
	 */
	public String translate(String word, String direction) {
		String ret = partsMap.get(direction).get(word);
		if (ret == null) {
			if (automaticAdding) {
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
				}
			}
			System.out.println("happening");
			ret = "";
		}
		return ret;
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
			writers.get(direction).println(word + ">" + translation);
			writers.get(direction).flush();
			partsMap.get(direction).put(word, translation);
			writers.get(getInverseDirection(direction)).println(translation + ">" + word);
			writers.get(getInverseDirection(direction)).flush();
			partsMap.get(getInverseDirection(direction)).put(translation, word);
		} catch (Exception e) {
			// Probably will happen if direction is not found
			e.printStackTrace();
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
		saveDictionary();
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
		writers.get(direction).println(key + ">" + value);
	}

	/**
	 * detects language of given word
	 * 
	 * @param word
	 * @return english or german or not found or both
	 */
	public String detectLanguage(String word) {
		String ret = "not found";
		for (String direction : directions) {
			if (partsMap.get(direction).containsKey(word)) {
				return direction.substring(0, direction.lastIndexOf("-"));
			}
		}
		return ret;
	}

	/**
	 * Closes all writers in writers hashmap
	 */
	public void closeDictionary() {
		for (PrintWriter writer : writers.values()) {
			writer.close();
		}
	}

	/**
	 * returns a whole dictionary as a String
	 * 
	 * @param direction the dictionary to display
	 * @return a dictionary as a String
	 */
	public String displayDictionary(String direction) {
		if (!partsMap.containsKey(direction)) {
			return "";
		}
		String output = "";
		for (Map.Entry<String, String> keyValuePair : partsMap.get(direction).entrySet()) {
			output += keyValuePair.getKey() + " <> " + keyValuePair.getValue() + "\r\n";
		}
		return output;
	}

	public boolean getAutomaticAdding() {
		return automaticAdding;
	}

	public void toggleAutomaticAdding() {
		automaticAdding = !automaticAdding;
	}

	/**
	 * Returns all loaded dictionaries
	 * 
	 * @return
	 */
	public ArrayList<String> getLoadedDictionaries() {
		return new ArrayList<String>(directions);
	}

	/**
	 * Returns all supported dictionaries (even if not currently loaded)
	 * 
	 * @return
	 */
	public ArrayList<String> getSupportedDirections() {
		return new ArrayList<String>(supportedDirections);
	}
}