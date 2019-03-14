public class CLI
{
	private MenuChoice menu;
	
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
		menu = new MenuChoice();
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
				//Translate method
				break;
			case 2:
				System.out.println("case 2");
				//Read method
				break;
			case 3:
				System.out.println("case 3");
				//Display dictionary method
				break;
			case 4:
				System.out.println("case 4");
				//Export dictionary method
				break;
			case 5:
				System.out.println("case 5");
				//Import dictionary method
				break;
			case 6:
				System.out.println("case 6");
				//Add word dictionary method
				break;
			case 7:
				System.out.println("case 7");
				//Remove word dictionary method
				break;
			case 8:
				System.out.println("");
				System.out.println("You have chosen to exit");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice, please re-enter your choice: ");
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
}
