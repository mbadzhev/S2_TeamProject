
public class Tester {
	public static void main(String[] args) {
		System.out.println("start Translation.");
		Tester tester = new Tester();
		tester.testTranslator();
		System.out.println("Translation done.");
	}

	public void testDictionary() {
		Dictionary testDic = new Dictionary();
		testDic.closeDictionary();
	}

	public void testTranslator() {
		Translator testTrans = new Translator();
		try {
			System.out.println(testTrans.translate("dog", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(testTrans.translate("dog", "fr-es"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		testTrans.translateFile("english.txt", "german.txt", "en-de");

	}
}
