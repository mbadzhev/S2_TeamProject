import java.io.IOException;
import java.sql.Date;

public class Tester {
	public static void main(String[] args) {
		System.out.println("start Translation.");
		Tester tester = new Tester();
		tester.testTranslateFile();
//		tester.testDictionary();
		//tester.testTranslator();
		System.out.println("Translation done.");
//		try {
//			trans.loadDictionary("fr-en");
//			trans.translateFile("french.txt","frenchout.txt", "fr-en");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
	}

	public void testDictionary() {
		Dictionary testDic = new Dictionary();
		//Shouldn't test. Translator should
		System.out.println(testDic.translate("hello", "en-de"));
		System.out.println(testDic.translate("hello", "en-pl"));
		testDic.closeDictionary();
	}



	public void testTranslateFile() {
		Translator testTrans = new Translator();

		// translateFile()

		System.out.println("translate Rotkappchen");
		try {
			System.out.println(
					"words per second: " + testTrans.translateFile("Rotkäppchen.txt", "Rotkäppchen_en.txt", "de-en"));
		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

//		System.out.println("translate rock_climbing_explained");
//		try {
//			System.out.println("words per second: " + testTrans.translateFile("rock_climbing_explained.txt","rock_climbing_explained_de.txt", "en-de"));
//		} catch (IOException | DirectionException e) {
//			System.out.println(e.getMessage());
//		}
//
//		System.out.println("translate not existing file");
//		try {
//			testTrans.translateFile("not_existing_file.txt", "output.txt", "en-de");
//		} catch (IOException | DirectionException e) {
//			System.out.println(e.getMessage());
//		}
//
//		System.out.println("translateFile and not specifying an output file");
//		try {
//			testTrans.translateFile("rock_climbing_explained.txt", "", "en-de");
//		} catch (IOException | DirectionException e) {
//			System.out.println(e.getMessage());
//		}
//
//		System.out.println("translateFile with wrong direction");
//		try {
//			testTrans.translateFile("rock_climbing_explained.txt", "rock_climbing_explained_de.txt",
//					"german - english");
//		} catch (IOException | DirectionException e) {
//			System.out.println(e.getMessage());
//		}
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

		
		// TODO how to handle directions that do not exist yet? Shall we asks to create
		// a new dictionary?
		testTrans.removeFromDictionary("hund", "de-en");

		System.out.println(testTrans.displayDictionary("en-de"));
	}
}
