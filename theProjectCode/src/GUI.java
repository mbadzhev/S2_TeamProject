import java.awt.Desktop;
import java.awt.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.stream.DoubleStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font; 
import javafx.scene.text.Text; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GUI extends Application
{
	private Translator translator;
	
	/**
	 * the main method
	 * 
	 * @param args the parameter for the main method
	 */
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	/**
	 * method to start the program
	 */
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			translator = new Translator();
			
			primaryStage.setTitle("*** TRANSLATOR ***");
			
//Menu			
			MenuBar menuBar = new MenuBar();
			menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
			
			//Translate menu option
			Menu translate = new Menu("Translator");
			menuBar.getMenus().add(translate);
			MenuItem menuItem0 = new MenuItem("Translate");
			translate.getItems().add(menuItem0);
			MenuItem menuItem = new MenuItem("Translate File");
			translate.getItems().add(menuItem);
			
			//Add word menu option
			Menu dictionary = new Menu("Dictionary");
			menuBar.getMenus().add(dictionary);
			MenuItem menuItem1 = new MenuItem("Add word to Dictionary");
			MenuItem menuItem2 = new MenuItem("Remove word from Dictionary");
			MenuItem menuItem3 = new MenuItem("Load Dictionary");
			MenuItem menuItem4 = new MenuItem("Display Dictionary");
			MenuItem menuItem5 = new MenuItem("Save Dictionary");
			
			dictionary.getItems().add(menuItem1);
			dictionary.getItems().add(menuItem2);
			dictionary.getItems().add(menuItem3);
			dictionary.getItems().add(menuItem4);
			dictionary.getItems().add(menuItem5);
			
			//Setting menu option
			Menu settings = new Menu("Settings");
			menuBar.getMenus().add(settings);
			
			CheckMenuItem item1 = new CheckMenuItem("Automatic adding of words");
			item1.setSelected(true);
			
			settings.getItems().add(item1);    
		    
//Translator page
			
		    GridPane grid = new GridPane();
		    
		    ColumnConstraints column1 = new ColumnConstraints();
		    column1.setPercentWidth(50);
		    ColumnConstraints column2 = new ColumnConstraints();
		    column2.setPercentWidth(50);
		    
		    grid.getColumnConstraints().addAll(column1, column2);
		    grid.setAlignment(Pos.CENTER);
		    grid.setHgap(50);
		    grid.setVgap(20);
		    grid.setPadding(new Insets(100, 100, 100, 100));
		    grid.setMinSize(200, 200); 
		    
		    Text text1 = new Text("Translator:");
		    text1.setFont(Font.font("Tahoma", 40)); 
	
		    Label textToTransl = new Label("Please enter the text you would like to translate:");
		    textToTransl.setFont(Font.font(20));
		    TextArea toTransl = new TextArea();
		    
		    Label textTranslation = new Label("Translation:");
		    textTranslation.setFont(Font.font(20));
		    TextArea translatedText = new TextArea();
		    
		    Label label = new Label("Please enter the direction:");
		    label.setFont(Font.font(20));
		    ComboBox<String> comboBoxTransl = new ComboBox<>();
			comboBoxTransl.getItems().addAll(translator.getLoadedDictionaries());
		  
		    Button button1 = new Button("Translate");
		    button1.setFont(Font.font(15));
			HBox box = new HBox(10);
			box.setAlignment(Pos.BOTTOM_RIGHT);
			box.getChildren().add(button1);

		    grid.add(text1, 0, 0);
		    grid.add(textToTransl, 0, 1);
		    grid.add(toTransl, 0, 2);
		    grid.add(textTranslation, 1, 1);
		    grid.add(translatedText, 1, 2);
		    grid.add(label, 0, 3);
		    grid.add(comboBoxTransl, 0, 4);
		    grid.add(box, 0, 5); 
		    
		    Text message = new Text();
			message.setFill(Color.FIREBRICK);
			message.setFont(Font.font(20));
			grid.add(message, 0, 5);
		
		    Group transl = new Group(grid);
		    Scene scene = new Scene(transl, 1200, 600);
		    scene.setFill(Color.LIGHTSTEELBLUE);
		    VBox vBox = new VBox(menuBar);	
		    
		    //clicking on "Translate" button
		    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
			{ 
				@Override 
			    public void handle(MouseEvent e) 
			    {					   
					try
					{			
						message.setText("Translating...");
						
						new Thread(new Runnable() {

							@Override
							public void run() {

								try {
									translatedText.setText(translator.translate(toTransl.getText(), comboBoxTransl.getValue()));
									message.setText("Bam. Translated.");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								message.setText("");
							}
							
						}).start();
					}
					catch(Exception e0)
					{
						message.setText("Please fill out all the fields correctly");
					}
			    } 
			};   
			button1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		    
//Menu options when selected
		    menuItem0.setOnAction(e ->
		    {
		    	comboBoxTransl.getItems().clear();
		    	comboBoxTransl.getItems().addAll(translator.getLoadedDictionaries());
		    	
		    	if(!transl.getChildren().contains(vBox)) 
		    	{
		    		transl.getChildren().add(vBox);
			    	scene.setRoot(transl);
		    	} 	
		    });
		    menuItem.setOnAction(e -> 
		    {
		    	transFile(scene, vBox, primaryStage);
		    });
		    
		    menuItem1.setOnAction(e -> 
		    {
		    	add(scene, vBox);
		    });
		    
		    menuItem2.setOnAction(e -> 
		    {
		    	remove(scene, vBox);
		    });
    
		    menuItem3.setOnAction(e -> 
		    {
		    	loadDic(scene, vBox);
		    });
		    
		    menuItem4.setOnAction(e -> 
		    {
		    	displayDic(scene, vBox);
		    });
		    
		    menuItem5.setOnAction(e -> 
		    {
		    	saveDic(scene, vBox); 
		    });
		    
		    item1.setOnAction(e -> 
		    {
		    	translator.toggleAutomaticAdding();
		    });	    
		    
		    transl.getChildren().add(vBox);		    
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * method to handle the user input for translating a file
	 * 
	 * @param scene The scene for the GUI
	 * @param vBox The vBox that includes the menu bar
	 * @param primaryStage The stage of the project
	 */
	
	public void transFile(Scene scene, VBox vBox, Stage primaryStage)
	{
		GridPane grid = new GridPane();
		
		grid.setHgap(15);
		grid.setVgap(30);
		grid.setPadding(new Insets(150, 100, 300, 300));
		grid.setMinSize(500, 500); 
		    
		Text title = new Text("Translate from file");
		title.setFont(Font.font("Tahoma", 40));
	
		Label fileLabel = new Label("Please enter the name of the file:");
		fileLabel.setFont(Font.font(20));
		TextField fileName = new TextField();
		 
		Button button = new Button("Search"); 
		HBox box1 = new HBox(10);
		box1.setAlignment(Pos.BOTTOM_RIGHT);
		box1.getChildren().add(button);
		 
		Label translFile = new Label("Please enter the file to save to:");
		translFile.setFont(Font.font(20));
		TextField trFileName = new TextField();
		 
		Label langOption = new Label("Please choose a direction");
		langOption.setFont(Font.font(20));
		
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(translator.getLoadedDictionaries());
		
		Button button1 = new Button("Translate"); 
		button1.setFont(Font.font(15));
		HBox box = new HBox(10);
		box.setAlignment(Pos.BOTTOM_RIGHT);
		box.getChildren().add(button1);
		
		grid.add(title, 0, 0);
		grid.add(fileLabel, 0, 1);
		grid.add(fileName, 1, 1);
		grid.add(box1, 2, 1);
		grid.add(translFile, 0, 2);
		grid.add(trFileName, 1, 2, 2, 1);
		grid.add(langOption, 0, 3);
		grid.add(comboBox, 1, 3, 2, 1);
		grid.add(box, 2, 4);
		 
		//error/worked message
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setFont(Font.font(20));
		grid.add(message, 0, 4, 2, 1);
		 
		Group transFile = new Group(grid);
		transFile.getChildren().add(vBox);
		scene.setRoot(transFile); 
		 
		FileChooser fileChooser = new FileChooser(); 
		 
		//click on button "Search" to select a file
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{ 
				// get the file selected 
			    File file = fileChooser.showOpenDialog(primaryStage); 
			  
			    if (file != null) 
			    { 
			    	 fileName.setText(file.getAbsolutePath());
			    }     
			} 
		}; 
		button.setOnAction(event); 
		
		//runs when "Translate" button is selected
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent event) 
		   { 			   
			   try
			   {	
				   double time = 0.0;
				   
				   time = translator.translateFile(fileName.getText(), trFileName.getText(), comboBox.getValue());
				   time = Double.parseDouble(new DecimalFormat("##.##").format(time));
				   
				   message.setText("Time for translation in words per second: " + time);
				   message.setFill(Color.GREEN);
				   
				   Button button2 = new Button("Open translation");
				   HBox box2 = new HBox(10);
				   //box2.setAlignment(Pos.BOTTOM_RIGHT);
				   box2.getChildren().add(button2);
				   grid.add(box2, 0, 5);
				   
				   EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
				   { 
					   @Override 
					   public void handle(MouseEvent event) 
					   { 
						   Desktop desktop = Desktop.getDesktop();
						   try 
						   {
							   desktop.open(new File(trFileName.getText()));
						   } 
						   catch (IOException e) 
						   {
							   e.printStackTrace();
						   }
					   } 
					 };   
					 button2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
			   }
			   catch(FileNotFoundException | DirectionException | NullPointerException e)
			   {
				   message.setText("Please fill in all the fields correctly");
			   }
		   } 
		 };   
		 button1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	
	/**
	 * method to handle the user input for adding of new words to a dictionary
	 * 
	 * @param scene The scene for the GUI
	 * @param vBox The vBox that includes the menu bar
	 */
	
	public void add(Scene scene, VBox vBox)
	{			
		GridPane grid = new GridPane();
		 
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(40);
		grid.setPadding(new Insets(150, 200, 300, 300));
		    
		Text title = new Text("Add a new word to the dictionary:");
		title.setFont(Font.font("Tahoma", 40));
	
		Label label1 = new Label("Please enter the word:");
		label1.setFont(Font.font(20));
		TextField toTransl = new TextField();
		    
		Label label2 = new Label("Please enter the translation:");
		label2.setFont(Font.font(20));
		TextField translatedText = new TextField();
		
		Label langOption = new Label("Please choose a dictionary");
		langOption.setFont(Font.font(20));
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(translator.getLoadedDictionaries());
		 
		Button button = new Button("Add");
		button.setFont(Font.font(15));
		HBox box = new HBox(10);
		box.setAlignment(Pos.BOTTOM_RIGHT);
		box.getChildren().add(button);
		
		//added message
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setFont(Font.font(20));

		grid.add(title, 0, 0, 2, 1);
		grid.add(label1, 0, 1);
		grid.add(toTransl, 1, 1);
		grid.add(label2, 0, 2);
		grid.add(translatedText, 1, 2);
		grid.add(langOption, 0, 3);
		grid.add(comboBox, 1, 3);
		grid.add(box, 1, 4);
		grid.add(message, 0, 4, 2, 1);
		
		Group add = new Group(grid);
		add.getChildren().add(vBox);
		scene.setRoot(add); 
		
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		{ 
			@Override 
		    public void handle(MouseEvent e) 
		    { 
				try
				{		
					if(toTransl.getText().equals("") || translatedText.getText().equals(""))
					{
						message.setText("Please fill out all fields");
					}
					else
					{
						translator.addToDictionary(toTransl.getText(), translatedText.getText(), comboBox.getValue());
						
						message.setText("Added to dictionary");
					}
				}
				catch(Exception e1)
				{
					message.setText("Please choose a dictionary");
				}
		    } 
		};   
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	
	/**
	 * method to handle the user input for removing a word from a dictionary
	 * 
	 * @param scene The scene for the GUI
	 * @param vBox The vBox that includes the menu bar
	 */
	
	public void remove(Scene scene, VBox vBox)
	{	
	    GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(40);
		grid.setPadding(new Insets(150, 100, 300, 300));
		grid.setMinSize(200, 200); 
		    
		Text title = new Text("Remove word from dictionary:");
		title.setFont(Font.font("Tahoma", 40)); 
	
		Label label1 = new Label("Please enter the word:");
		label1.setFont(Font.font(20));
		TextField toDelete = new TextField();
		
		Label langOption = new Label("Please choose a dictionary");
		langOption.setFont(Font.font(20));
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(translator.getLoadedDictionaries());
		 
		Button button = new Button("Remove");  
		button.setFont(Font.font(15));
		HBox box = new HBox(10);
		box.setAlignment(Pos.BOTTOM_RIGHT);
		box.getChildren().add(button);
		
		//added message
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setFont(Font.font(20));

		grid.add(title, 0, 0, 2, 1);
		grid.add(label1, 0, 1);
		grid.add(toDelete, 1, 1);
		grid.add(langOption, 0, 2);
		grid.add(comboBox, 1, 2);
		grid.add(box, 1, 3);
		grid.add(message, 0, 3);
		 
	    Group remove = new Group(grid);
	    remove.getChildren().add(vBox);
	    scene.setRoot(remove); 
	    
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
			   try
			   {	
				   if(toDelete.getText().equals(""))
				   {
					   message.setText("Please enter a word");
				   }
				   else
				   {
					   translator.removeFromDictionary(toDelete.getText(), comboBox.getValue());
					   
					   message.setText("Word removed from dictionary");
				   } 
			   }
			   catch(Exception e2)
			   {
				   message.setText("No dictionary chosen");
			   } 
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	    
	}
	
	/**
	 * method to handle the user input for loading a dictionary from file
	 * 
	 * @param scene The scene for the GUI
	 * @param vBox The vBox that includes the menu bar
	 */
	
	public void loadDic(Scene scene, VBox vBox)
	{
		GridPane grid = new GridPane();
		 
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(40);
		grid.setPadding(new Insets(150, 100, 400, 400));
		grid.setMinSize(200, 200); 
		    
		Text title = new Text("Load a Dictionary:");
		title.setFont(Font.font("Tahoma", 40)); 	
		
		Label langOption = new Label("Please choose a dictionary: ");
		langOption.setFont(Font.font(20));
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(translator.getSupportedDirections());
		 
		Button button2 = new Button("Load");
		button2.setFont(Font.font(15));
		HBox box2 = new HBox(10);
		box2.setAlignment(Pos.BOTTOM_RIGHT);
		box2.getChildren().add(button2);

		grid.add(title, 0, 0, 2, 1);
		grid.add(langOption, 0, 1);
		grid.add(comboBox, 1, 1); 
		grid.add(box2, 1, 3); 
	
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setFont(Font.font(20));
		grid.add(message, 0, 3);
		 
	    Group loadDic = new Group(grid);
	    loadDic.getChildren().add(vBox);
	    scene.setRoot(loadDic); 
	    
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
			   try
			   {			   
				   translator.loadDictionary(comboBox.getValue());
				   
				   message.setText("Dictionary loaded from file");
			   }
			   catch(Exception e3)
			   {
				   if(e3.getMessage() != null && e3.getMessage().equals("already loaded"))
				   {
					   message.setText("Dictionary already loaded");
				   }
				   else
				   {
					   message.setText("No dictionary chosen");
				   }
			   }  
		   } 
		 };   
		 button2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	
	/**
	 * method to handle the user input for displaying the contents of a dictionary
	 * 
	 * @param scene The scene for the GUI
	 * @param vBox The vBox that includes the menu bar
	 */
	
	public void displayDic(Scene scene, VBox vBox)
	{
		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(20);
		grid.setPadding(new Insets(100, 100, 350, 350));
		grid.setMinSize(200, 200); 
		    
		Text title = new Text("Display the Dictionary:");
		title.setFont(Font.font("Tahoma", 40)); 

		Label label = new Label("Please choose a dictionary:");
		label.setFont(Font.font(20));
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(translator.getLoadedDictionaries());
		
		TextArea dictionary = new TextArea();
		
		Button button2 = new Button("Display");
		button2.setFont(Font.font(15));
		HBox box2 = new HBox(10);
		box2.setAlignment(Pos.BOTTOM_RIGHT);
		box2.getChildren().add(button2);

		grid.add(title, 0, 0, 2, 1);
		grid.add(label, 0, 1);
		grid.add(comboBox, 1, 1);
		grid.add(dictionary, 0, 3, 2, 1);
		grid.add(box2, 1, 4); 
	
		//the text in the dictionary
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setFont(Font.font(20));
		grid.add(message, 0, 4);
		 
	    Group displayDic = new Group(grid);
	    displayDic.getChildren().add(vBox);
	    scene.setRoot(displayDic); 
	    
	    //runs when "Display" button is selected
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
			   try
			   {					   
				   dictionary.setText(translator.displayDictionary(comboBox.getValue()));
				   
				   message.setText("");
			   }
			   catch(NullPointerException e4)
			   {
				   message.setText("No dictionary chosen");
			   }
		   } 
		 };   
		 button2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	    
	}
	
	/**
	 * method to handle the user input for saving the dictionaries to file
	 * 
	 * @param scene The scene for the GUI
	 * @param vBox The vBox that includes the menu bar
	 */
	
	public void saveDic(Scene scene, VBox vBox)
	{
		 GridPane grid = new GridPane();		 
		 grid.setHgap(50);
		 grid.setVgap(40);
		 grid.setPadding(new Insets(150, 100, 330, 330));
		 grid.setMinSize(200, 200); 
		    
		 Text title = new Text("Save the Dictionaries to file:");
		 title.setFont(Font.font("Tahoma", 40));
		 
		 Button button = new Button("Save");
		 button.setFont(Font.font(25));
		 HBox box = new HBox(10);
		 box.setAlignment(Pos.CENTER);
		 box.getChildren().add(button);

		grid.add(title, 0, 0);
		grid.add(box, 0, 1); 
	
		Text message = new Text();
		message.setFill(Color.FIREBRICK);
		message.setFont(Font.font(20));
		grid.add(message, 0, 2);
		 
	    Group saveDic = new Group(grid);
	    saveDic.getChildren().add(vBox);
	    scene.setRoot(saveDic); 
	    
	    //runs when "Save" button is selected
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
			   try
			   {			   
				   translator.saveDictionaryToFile();
				   
				   message.setText("Dictionaries saved to file");
			   }
			   catch(Exception e5)
			   {
				   message.setText("Dictionary couldn't be saved");
			   }
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
}
