
public class Tester {
	public static void main(String[] args) {
		System.out.println("start Translation.");
		Tester tester = new Tester();
		tester.testTranslator();
		System.out.println("Translation done.");
	}

	public void testDictionary() {
		Dictionary testDic = new Dictionary();
//		System.out.println(testDic.detectLanguage("kennen"));
		testDic.closeDictionary();
	}

	public void testTranslator() {
		Translator testTrans = new Translator();
		Dictionary testDict=new Dictionary();
		System.out.println(testDict.partsMap.get("en-de").get("place"));
		testDict.addToDictionary("blergh", "ebeih", "en-de");
		testDict.saveDictionary();
		//System.out.println(testTrans.translate("dog", "en-de"));
		// TODO fix the output
		//System.out.println(testTrans.translate("dog", "fr-es"));
		//testTrans.translateFile("english.txt", "german.txt", "en-de");
//		testTrans.translateFile("new.txt", "new2.txt");
		// needs to close file writer;
//		testTrans.dictionary.closeDictionary();
	}
}
