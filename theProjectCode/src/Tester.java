
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

		Dictionary testDict=new Dictionary();
		System.out.println(testDict.partsMap.get("en-de").get("place"));
		
				

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
		// translateFile()
		System.out.println("translate Rotkappchen");
		testTrans.translateFile("Rotkäppchen.txt", "Rotkäppchen_en.txt", "de-en");
		
		System.out.println("translate rock_climbing_explained");
		testTrans.translateFile("rock_climbing_explained.txt", "rock_climbing_explained_de.txt", "en-de");
		
		System.out.println("translate not existing file");
		testTrans.translateFile("not_existing_file.txt", "output.txt", "en-de");
		
		System.out.println("translateFile and not specifying an output file");
		testTrans.translateFile("rock_climbing_explained.txt", "", "en-de");
		
		System.out.println("translateFile with wrong direction");
		testTrans.translateFile("rock_climbing_explained.txt", "rock_climbing_explained_de.txt", "german - english");
		// TODO how to handle directions that do not exist yet? Shall we asks to create a new dictionary?

	}
}
