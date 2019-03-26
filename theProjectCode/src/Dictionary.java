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
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

public class Dictionary {
	private final static String HOST = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
	private final static String KEY = "trnsl.1.1.20190305T204312Z.dfa16cd3173c9caf.d933df65b77daf7957e97b8e2e6571320a9823a3";
	private HashMap<String,PrintWriter> writers;
	public HashMap<String, HashMap<String, String>> partsMap;
	private HashMap<String,String> dictionaryPaths;
	private ArrayList<String> directions;
	private boolean automaticAdding;

	/**
	 * Reads dictionary files and loads to hashmaps Opens writers to write new words
	 * (so it does not have to open every single time)
	 */
	public Dictionary() {
		automaticAdding=true;
		writers=new HashMap<String,PrintWriter>();
		partsMap = new HashMap<String, HashMap<String, String>>();
		dictionaryPaths=new HashMap<String,String>();
		directions=new ArrayList<String>();
		initialiseDirection("en-de");
		initialiseDirection("de-en");
	}
	/**
	 * Initialises hashmaps for a direction
	 * @param direction
	 */
	public void initialiseDirection(String direction) {
		partsMap.put(direction, new HashMap<String, String>());
		directions.add(direction);
		dictionaryPaths.put(direction, direction+".txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(direction+".txt"));
			String line;
			while (reader.ready()) {
				line = reader.readLine();
				String[] mappings = line.split(" ");
				addToDictionary(mappings[0], mappings[1],direction);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			writers.put(direction,new PrintWriter(new FileOutputStream(new File(dictionaryPaths.get(direction)), true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Given a filename, this function parses a dictionary from that file to HashMAp of direction
	 * If it already exists or it can't load it for whatever reason it throws Exception
	 *
	 * @param filename the file where the dictionary is store in.
	 * @param map      the HashMap to store the dictionary to.
	 * @throws IOException
	 */
	public void loadDictionary(String filename, String direction) throws Exception {
		// Will throw exception if FileReader can't load it or already loaded
		if (!directions.contains(direction)) {
			initialiseDirection(direction);
		}
		else {
			throw new Exception();
		}
		// TODO check if the format of the dictionary is right
		// TODO support UTF-8 / Umlaut
	}

	/**
	 * this function saves all dictionaries using the hashmaps. 
	 * replaces existing files
	 * 
	 */
	public void saveDictionary() {
		for (String direction:directions) {
			for (Map.Entry<String, String> keyValuePair:partsMap.get(direction).entrySet()) {
				writers.get(direction).println(keyValuePair.getKey()+" "+keyValuePair.getValue());
			}
			writers.get(direction).flush();
		}
	}

	/**
	 * translates a single word
	 * 
	 * @param word
	 * @param toGerman toGerman or false toEnglish
	 * @return translation
	 * @throws throws exception if direction is invalid
	 */
	public String translate(String word, String direction) throws Exception {
		// check if the dictionary has the direction
		if (!partsMap.containsKey(direction)) {
			throw new Exception();
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
			writers.get(direction).println(word+" "+translation);
			writers.get(direction).flush();
			partsMap.get(direction).put(word, translation);
			writers.get(getInverseDirection(direction)).println(word+" "+translation);
			writers.get(getInverseDirection(direction)).flush();
			partsMap.get(getInverseDirection(direction)).put(word, translation);
		} catch (Exception e) {
			//Probably will happen if direction is not found
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
		//TODO removes during runtime but consider removing from .txt file too
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
		//TODO save on file
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
	/**
	 * Closes all writers in writers hashmap
	 */
	public void closeDictionary() {
		for (PrintWriter writer:writers.values()) {
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
		// TODO work on this function
		return "I'm a dictionary";
	}
	public boolean getAutomaticAdding() {
		return automaticAdding;
	}
}