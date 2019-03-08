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
	private String dbFilename="database.txt";
	private String host="https://translate.yandex.net/api/v1.5/tr.json/translate?";
	private String key="trnsl.1.1.20190305T204312Z.dfa16cd3173c9caf.d933df65b77daf7957e97b8e2e6571320a9823a3";
	private PrintWriter writer;
	private HashMap<String,String> dictionary;
	public Dictionary() {
		dictionary=new HashMap<String,String>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(dbFilename));
			String line;
			while (reader.ready()) {
				line=reader.readLine();
				String[] mappings=line.split(" ");
				dictionary.put(mappings[0], mappings[1]);
			}
			reader.close();
		}
		catch (Exception e) {
			
		}
		try {
			writer=new PrintWriter(new FileOutputStream(dbFilename,true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Dictionary hello=new Dictionary();
		hello.translateFile("english.txt","german.txt");
		hello.writer.close();
	}
	public void translateFile(String filename,String translationFilename) {
		try {
			BufferedReader reader=new BufferedReader(new FileReader(filename));
			PrintWriter writer=new PrintWriter(new FileOutputStream(translationFilename));
			while (reader.ready()) {
				String line=reader.readLine();
				String[] sentences=line.split("[\\.?]");
				for (int i=0;i<sentences.length;i++) {
					sentences[i].trim();
					String[] words=sentences[i].split(" ");
					String translatedSentence="";
					for (int j=0;j<words.length;j++) {
						translatedSentence=translatedSentence.concat(translate(words[j],true))+" ";
					}
					writer.print(translatedSentence+". ");
				}
				writer.println();
			}
			writer.close();
			reader.close();
		}
		catch (Exception e) {
			
		}
	}
	public String translate(String word, boolean toGerman) {
		if (word.equals("")) {
			return word;
		}
		if (dictionary.containsKey(word)) {
			return dictionary.get(word);
		}
		try {
			URL url=new URL(host+"key="+key+"&text="+word+"&lang="+(toGerman?"en-de":"de-en"));
			InputStream is=url.openStream();
			Scanner s=new Scanner(is);
			String translation=parseJSON(s.nextLine());
			if (!word.equals(translation)) {
				save(word,translation);
			}
			return translation;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public void save(String word,String translation) {
		dictionary.put(word, translation);
		if (writer!=null) {
			writer.append(word+" "+translation+"\n");
			writer.flush();
		}
	}
	public String parseJSON(String json) {
		int textIndex=json.lastIndexOf("text")+7;
		int textEndIndex=textIndex+json.substring(textIndex).lastIndexOf("]");
		String translatedWords= json.substring(textIndex,textEndIndex);
		translatedWords.trim();
		translatedWords=translatedWords.substring(1,translatedWords.length()-1);
		/*String[] words=translatedWords.split(",");
		for (int i=0;i<words.length;i++) {
			words[i].trim();
			words[i]=words[i].substring(1,words[i].length()-1);
		}*/
		return translatedWords;
	}
	/*public void saveToDatabase() {
		if (dictionary!=null) {
			try {
				PrintWriter writer=new PrintWriter(new FileOutputStream(dbFilename));
				dictionary.forEach((key,value)->{
					
				});
			}
			catch (Exception e) {
				
			}
		}
	}*/
}