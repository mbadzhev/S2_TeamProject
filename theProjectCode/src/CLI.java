import java.util.Scanner;

public class CLI
{
	private Translator translator;
	
	Scanner s1 = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		CLI commandLineMenu = new CLI();
		commandLineMenu.initialise();
		while(true)
		{
			commandLineMenu.process();
		}
	}

	public void initialise()
	{
		Translator translator = new Translator();
	}
	
	public void process()
	{
		displayMenu();
				
		//Calls method to validate user input
		int menuOption = IO.getInt("Please select one of the options above:");
	
		switch(menuOption)
		{		
			case 1:
				System.out.println("case 1");
				String userInput = IO.getString("What do you want translated?");
				displayLanguageMenu();
				
				//From what language to translate
				int userChoiceFrom = IO.getInt("Select the language this is in:");
				boolean choiceValidFrom = false;
				while(choiceValidFrom != true)
				{
					String languageFrom;
					
					switch(userChoiceFrom)
					{
						case 1:
							languageFrom = "en";
							choiceValidFrom = true;
							break;
						case 2:
							languageFrom = "de";
							choiceValidFrom = true;
							break;
						default:
							System.out.println("Out of range, please re-enter your choice: ");
							break;
					}
				}
				
				//To what language to translate
				int userChoiceTo = IO.getInt("Select the language do you want this translated in:");
				boolean choiceValidTo = false;
				while(choiceValidTo != true)
				{
					String languageTo;
					
					switch(userChoiceTo)
					{
						case 1:
							languageTo = "en";
							choiceValidTo = true;
							break;
						case 2:
							languageTo = "de";
							choiceValidTo = true;
							break;
						default:
							System.out.println("Out of range, please re-enter your choice: ");
							break;
					}
				}
				//Translate method
				break;
			case 2:
				System.out.println("case 2");
				String fileInput = IO.getString("What is the name of the dictionary you want to load?");
				//translator.loadDictionaryFromFile(fileInput);
				//Translate file method
				break;
			case 3:
				System.out.println("case 3");
				//Display dictionary method
				break;
			case 4:
				System.out.println("case 4");
				String exportName = s1.nextLine();
				//Export dictionary method
				break;
			case 5:
				System.out.println("case 5");
				String importName = s1.nextLine();
				
				//Import dictionary method
				break;
			case 6:
				System.out.println("case 6");
				String addWord = s1.nextLine();
				//Add word dictionary method
				break;
			case 7:
				System.out.println("case 7");
				String removeWord = s1.nextLine();
				//Remove word dictionary method
				break;
			case 8:
				System.out.println("");
				System.out.println("You have chosen to exit");
				System.exit(0);
				break;
			default:
				System.out.println("Out of range, please re-enter your choice: ");
				break;
		}
	}
	
	/**
	 * Holds information about the menu.
	 */
	public void displayMenu()
	{
		System.out.println("");
		System.out.println("1. Translate Input");
		System.out.println("2. Translate File");
		System.out.println("3. Display Dictionary");
		System.out.println("4. Export Dictionary");
		System.out.println("5. Import Dictionary");
		System.out.println("6. Add Word To Dictionary");
		System.out.println("7. Remove Word From Dictionary");
		System.out.println("8. Exit Program");
		System.out.println("");
	}
	
	public void displayLanguageMenu()
	{
		System.out.println("");
		System.out.println("1. English");
		System.out.println("2. German");
		System.out.println("");
	}
}
