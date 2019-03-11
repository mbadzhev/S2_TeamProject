import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class Translator {
	public Dictionary dictionary;
	public Translator() {
		dictionary=new Dictionary();
	}
	/**
	 * Opens file, translates and outputs to another file
	 * @param filename name of file to translate
	 * @param translationFilename name of file to output translation
	 */
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
						//may want to change this
						boolean toGerman=dictionary.detectLanguage(words[j]).equals("english")?true:false;
						translatedSentence=translatedSentence.concat(dictionary.translate(words[j],toGerman))+" ";
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
}
