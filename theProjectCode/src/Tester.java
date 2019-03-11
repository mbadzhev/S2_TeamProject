
public class Tester {
	public static void main(String[] args) {
		System.out.println("start Translation.");
		Tester tester=new Tester();
		tester.testTranslator();
		System.out.println("Translation done.");
	}
	public void testDictionary() {
		Dictionary testDic=new Dictionary();
		System.out.println(testDic.detectLanguage("kennen"));
		testDic.closeDictionary();
	}
	public void testTranslator() {
		Translator testTrans=new Translator();
		testTrans.translateFile("new.txt","new2.txt");
		//needs to close file writer;
		testTrans.dictionary.closeDictionary();
	}
}

