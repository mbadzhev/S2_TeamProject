import java.io.IOException;
import java.sql.Date;

public class Tester {
	Translator translator;

	public static void main(String[] args) {

		Tester tester = new Tester();
		tester.runTestMenu();

	}

	public Tester() {
		translator = new Translator();
	}

	/**
	 * displays all test options
	 */
	public void displayMenuOptions() {
		System.out.println("--- Test Menu ---");
		System.out.println("-1 - exit");
		System.out.println("0 - test all test cases");
		System.out.println("1 - translate words");
		System.out.println("2 - translate sentences");
		System.out.println("3 - translate files");
		System.out.println("4 - remove from dictionary");
		System.out.println("5 - display dictionaries");
		System.out.println("6 - load and save dictionaries");
		System.out.println("7 - setting automaticAdding");
	}

	/**
	 * runs the test menu
	 */
	public void runTestMenu() {
		String option = "";
		boolean keepGoing = true;
		do {
			displayMenuOptions();
			option = IO.getString("enter a test option");
			switch (option) {
			case "-1":
				keepGoing = false;
				break;
			case "0":
				testAllTestCases();
				break;

			case "1":

				testTranslateWords();

				break;

			case "2":
				testTranslateSentences();
				break;
			case "3":
				testTranslateFiles();
				break;
			case "4":
				testRemoveFromDictionary();
				break;

			case "5":
				testDisplayDictionaries();
				break;

			case "6":
				testLoadSave();
				break;

			case "7":
				testAutomaticAdding();
				break;

			case "8":
				break;
			}
		} while (keepGoing);

	}

	/**
	 * runns all test cases
	 */
	public void testAllTestCases() {

		testTranslateWords();
		testTranslateSentences();
		testTranslateFiles();
		testRemoveFromDictionary();
		testDisplayDictionaries();
		testLoadSave();
		testAutomaticAdding();
	}

	/**
	 * test the test section translate words
	 */
	public void testTranslateWords() {
		System.out.println("<<<<< TEST TRANSLATE WORDS ");
		System.out.println("");

		System.out.println("Translate individual word: ");
		try {
			System.out.println(translator.translate("dog", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a name: ");
		try {
			System.out.println(translator.translate("Peter", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a word that doesnâ€™t exist in one language: ");
		try {
			System.out.println(translator.translate("seseseseiii", "de-en"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a word and a special character (e.g. ;,:â€�/â€™): ");
		try {
			System.out.println(translator.translate("say:", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("END TEST TRANSLATE WORDS >>>>>");

	}

	/**
	 * test the test case translate sentences
	 */
	public void testTranslateSentences() {
		System.out.println();
		System.out.println("<<<<< TEST TRANSLATE Sentences ");

		System.out.println("Translate a sentence: ");
		try {
			System.out.println(translator.translate("Ich liebe Java.", "de-en"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a sentence that includes special characters: ");
		try {
			System.out.println(translator.translate("er, sie, es", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("END TEST TRANSLATE Sentences >>>>>");
	}

	/**
	 * test for test case translate files
	 */
	public void testTranslateFiles() {
		System.out.println();
		System.out.println("<<<<< TEST TRANSLATE FILES ");

		System.out.println("Translate a file and display the time in words per second:");
		try {
			System.out.println(
					"words per second: " + translator.translateFile("Rotkäppchen.txt", "Rotkäppchen_en.txt", "de-en")
							+ ", translation done");

		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a file de-en (Rotkappchen): ");
		try {
			System.out.println(
					"words per second: " + translator.translateFile("Rotkäppchen.txt","Rotkäppchen_en.txt", "de-en")
							+ ", translation done");
		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a file en-de (rock_climbing_explained):");
		try {
			System.out.println("words per second: " + translator.translateFile("rock_climbing_explained.txt",
					"rock_climbing_explained_de.txt", "en-de"));
		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Translate a file that cannot be found: ");
		try {
			translator.translateFile("not_existing_file.txt", "output.txt", "en-de");
			System.out.println("translation done");
		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("translateFile and not specifying an output file: ");
		try {
			translator.translateFile("rock_climbing_explained.txt", "", "en-de");
			System.out.println("translation done");
		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("translateFile with wrong direction");
		try {
			translator.translateFile("rock_climbing_explained.txt", "rock_climbing_explained_de.txt",
					"german - english");
			System.out.println("translation done");
		} catch (IOException | DirectionException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("END TEST TRANSLATE FILES >>>>>");
	}

	/**
	 * test for test case translate files
	 */
	public void testRemoveFromDictionary() {
		System.out.println();
		System.out.println("<<<<< TEST REMOVE FROM DICTIONARY ");

		System.out.println("Remove a word from a dictionary: ");
		try {
			translator.removeFromDictionary("hello", "en-de");
			System.out.println("test remove done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Remove a translated word from a dictionary: ");
		try {
			translator.removeFromDictionary("hallo", "en-de");
			System.out.println("test remove done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Remove a word that does not exist in the dictionary: ");
		try {
			translator.removeFromDictionary("seseseseiii", "de-en");
			System.out.println("test remove done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Remove a word from a non-existing dictionary: ");
		try {
			translator.removeFromDictionary("werrdee", "cn-pp");
			System.out.println("test remove done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("END TEST REMOVE FROM DICTIONARY >>>>>");
	}

	/**
	 * test for test case displayDictionaries
	 */
	public void testDisplayDictionaries() {
		System.out.println();
		System.out.println("<<<<< TEST DISPLAY DICTIONARIES ");

		System.out.println("Display a dictionary: ");

		System.out.println(translator.displayDictionary("en-de"));

		System.out.println("Display a not loaded dictionary: ");

		System.out.println(translator.displayDictionary("de-pll"));
		System.out.println("END TEST DISPALY DICTIONARIES >>>>>");
	}

	/**
	 * test for test case setting automaticAdding
	 */
	public void testAutomaticAdding() {
		System.out.println("");
		System.out.println("<<<<< TEST SETTING AUTOMATIC ADDING");

		// TODO empty the dictionary

		System.out.println("Set automaticAdding to true, empty the en-de dictionary and translate ");
		// set automticAdding to true
		if (!translator.getAutomaticAdding()) {
			translator.toggleAutomaticAdding();
		}
		try {
			System.out.println(translator.translate("My mother likes cooking", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Set automaticAdding to false, empty the en-de dictionary and translate ");
		// set automticAdding to false
		if (translator.getAutomaticAdding()) {
			translator.toggleAutomaticAdding();
		}
		try {
			System.out.println(translator.translate("My mother likes cooking", "en-de"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("END TEST SETTING AUTOMATIC ADDING >>>>>");
	}

	/**
	 * test for test case load and save dictionaries
	 */
	public void testLoadSave() {
		System.out.println();
		System.out.println("<<<<< TEST LOAD AND SAVE DICTIONARIES ");

		System.out.println("END TEST LOAD AND SAVE DICTIONARIES >>>>>");
		// TODO implement test plan
	}

}
