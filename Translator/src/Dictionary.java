import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

public class Dictionary {
	private String endbFilename="englishdatabase.txt";
	private String dedbFilename="germandatabase.txt";
	private String host="https://translate.yandex.net/api/v1.5/tr.json/translate?";
	private String key="trnsl.1.1.20190305T204312Z.dfa16cd3173c9caf.d933df65b77daf7957e97b8e2e6571320a9823a3";
	private PrintWriter enwriter;
	private PrintWriter dewriter;
	private HashMap<String,String> englishToGermanDictionary;
	private HashMap<String,String> germanToEnglishDictionary;
	/**
	 * Reads dictionary files and loads to hashmaps
	 * Opens writers to write new words (so it does not have to open every single time)
	 */
	public Dictionary() {
		englishToGermanDictionary=new HashMap<String,String>();
		germanToEnglishDictionary=new HashMap<String,String>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(endbFilename));
			String line;
			while (reader.ready()) {
				line=reader.readLine();
				String[] mappings=line.split(" ");
				englishToGermanDictionary.put(mappings[0], mappings[1]);
			}
			reader.close();
			reader=new BufferedReader(new FileReader(endbFilename));
			while (reader.ready()) {
				line=reader.readLine();
				String[] mappings=line.split(" ");
				germanToEnglishDictionary.put(mappings[0], mappings[1]);
			}
			reader.close();
		}
		catch (Exception e) {
			
		}
		try {
			enwriter=new PrintWriter(new FileOutputStream(endbFilename,true));
			dewriter=new PrintWriter(new FileOutputStream(dedbFilename,true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * translates a single word
	 * @param word
	 * @param toGerman toGerman or false toEnglish
	 * @return translation
	 */
	public String translate(String word, boolean toGerman) {
		if (word.equals("")) {
			return word;
		}
		if (englishToGermanDictionary.containsKey(word)) {
			return englishToGermanDictionary.get(word);
		}
		try {
			URL url=new URL(host+"key="+key+"&text="+word+"&lang="+(toGerman?"en-de":"de-en"));
			InputStream is=url.openStream();
			Scanner s=new Scanner(is);
			String translation=parseJSONTranslation(s.nextLine());
			if (!word.equals(translation)) {
				save(word,translation,toGerman);
			}
			return translation;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * Saves words not already in the dictionary
	 * @param word
	 * @param translation
	 * @param toGerman
	 */
	private void save(String word,String translation,boolean toGerman) {
		if (toGerman) {
			englishToGermanDictionary.put(word, translation);
			if (enwriter!=null) {
				enwriter.append(word+" "+translation+"\n");
				enwriter.flush();
			}
		}
		else {
			germanToEnglishDictionary.put(word, translation);
			if (dewriter!=null) {
				dewriter.append(word+" "+translation+"\n");
				dewriter.flush();
			}
		}
	}
	/**
	 * Parses json translation given by API
	 * @param json
	 * @return value of translation
	 */
	private String parseJSONTranslation(String json) {
		int textIndex=json.lastIndexOf("text")+7;
		int textEndIndex=textIndex+json.substring(textIndex).lastIndexOf("]");
		String translatedWords= json.substring(textIndex,textEndIndex);
		translatedWords.trim();
		translatedWords=translatedWords.substring(1,translatedWords.length()-1);
		return translatedWords;
	}
	/**
	 * parses json language detection given by API
	 * @param json
	 * @return language en or de
	 */
	private String parseJSONDetect(String json) {
		int langIndex=json.lastIndexOf("lang")+8;
		String language=json.substring(langIndex, langIndex+2);
		return language;
	}
	/**
	 * removes key-value from dictionary
	 * @param key
	 */
	public void removeFromDictionary(String key,boolean toEnglishToGerman) {
		if (toEnglishToGerman) {
			englishToGermanDictionary.remove(key);
		}
		else {
			germanToEnglishDictionary.remove(key);
		}
	}
	/**
	 * adds key-value to dictionary
	 * @param key
	 * @param value
	 * @param toEnglishToGerman
	 */
	public void addToDictionary(String key, String value, boolean toEnglishToGerman) {
		if (toEnglishToGerman) {
			englishToGermanDictionary.put(key, value);
		}
		else {
			germanToEnglishDictionary.put(key, value);
		}
	}
	/**
	 * detects language of given word
	 * @param word
	 * @return english or german or not found or both
	 */
	public String detectLanguage(String word) {
		int count=0;
		String ret="not found";
		if (englishToGermanDictionary.containsKey(word)) {
			ret="english";
			count++;
		}
		if (germanToEnglishDictionary.containsKey(word)) {
			ret="german";
			count++;
		}
		if (count==2) {
			return "both";
		}
		else if (count==0) {
			URL url;
			try {
				url = new URL("https://translate.yandex.net/api/v1.5/tr.json/detect?"+"key="+key+"&text="+word);
				InputStream is=url.openStream();
				Scanner s=new Scanner(is);
				String language=parseJSONDetect(s.nextLine());
				if (language.equals("en")) {
					language="english";
				}
				else {
					language="german";
				}
				return language;
			} catch (Exception e) {
			}
		}
		return ret;
	}
	public void closeDictionary() {
		enwriter.close();
		dewriter.close();
	}
}