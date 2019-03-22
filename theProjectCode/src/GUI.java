import java.awt.Event;
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
			MenuItem menuItem1 = new MenuItem("Add word from dictionary");
			MenuItem menuItem2 = new MenuItem("Remove word from dictionary");
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
		    
		    grid.setGridLinesVisible(true);
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
		    textToTransl.setFont(Font.font(15));

		    TextArea toTransl = new TextArea();
		    
		    Label textTranslation = new Label("Translation:");
		    textTranslation.setFont(Font.font(15));

		    TextArea translatedText = new TextArea();
//		  
		    Button button1 = new Button("Submit");  
			HBox box = new HBox(10);
			box.setAlignment(Pos.BOTTOM_RIGHT);
			box.getChildren().add(button1);

		    grid.add(text1, 0, 0);
		    grid.add(textToTransl, 0, 1);
		    grid.add(toTransl, 0, 2);
		    grid.add(textTranslation, 1, 1);
		    grid.add(translatedText, 1, 2);
		    grid.add(box, 0, 4); 
		
		    Group transl = new Group(grid);
		    Scene scene = new Scene(transl, 1200, 600);
		    VBox vBox = new VBox(menuBar);		    
		  
		    menuItem0.setOnAction(e ->
		    {
		    	if(!transl.getChildren().contains(vBox)) 
		    	{
		    		transl.getChildren().add(vBox);
			    	scene.setRoot(transl);
		    	}
		    	
		    });
		    menuItem.setOnAction(e -> 
		    {
		    	transFile(scene, vBox);
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
		    	if(item1.isSelected() == true)
		    	{
		    		item1.setSelected(false);
		    	}
		    	else
		    	{
		    		item1.setSelected(true);
		    	}
		    });
		    
//		    item2.setOnAction(e -> 
//		    {
//		    	scene.setRoot(group2); 
//		    });
	
		    
		    
//		    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
//		    { 
//		    	@Override 
//		    	public void handle(MouseEvent e) 
//		    	{ 
//		    		scene.setRoot(group2);  
//		    	} 
//		    };   
//		    translate.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		    
		    
		    transl.getChildren().add(vBox);		    
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
		
	}
	
	public void transFile(Scene scene, VBox vBox)
	{
		GridPane grid = new GridPane();
		//grid.setAlignment(Pos.CENTER);
		 
		grid.setGridLinesVisible(true);
		 
		 grid.setHgap(30);
		 grid.setVgap(40);
		 grid.setPadding(new Insets(150, 100, 300, 300));
		 grid.setMinSize(500, 500); 
		    
		 Text title = new Text("Translate a text from file");
		 title.setFont(Font.font("Tahoma", 40));
	
		 Label fileLabel = new Label("Please enter the name of the file:");
		 fileLabel.setFont(Font.font(20));
		 TextField fileName = new TextField();
		 
		 Label translFile = new Label("Please enter the file to save to:");
		 translFile.setFont(Font.font(20));
		 TextField trFileName = new TextField();
		 
		 Label langOption = new Label("Please choose a direction");
		 langOption.setFont(Font.font(20));
		
		 ComboBox<String> comboBox = new ComboBox<>();
		 comboBox.getItems().addAll("English - German", "German - English");
		 
		 Button button = new Button("Submit");  
		 HBox box = new HBox(10);
		 box.setAlignment(Pos.BOTTOM_RIGHT);
		 box.getChildren().add(button);

		 grid.add(title, 0, 0);
		 grid.add(fileLabel, 0, 1);
		 grid.add(fileName, 1, 1);
		 grid.add(translFile, 0, 2);
		 grid.add(trFileName, 1, 2);
		 grid.add(langOption, 0, 3);
		 grid.add(comboBox, 1, 3);
		 grid.add(box, 1, 4);
		 
		 Group transFile = new Group(grid);
		 transFile.getChildren().add(vBox);
		 scene.setRoot(transFile); 
		 
		 EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
			   String direction = "";
			   
			   if(comboBox.getValue().equals("English - German"))
			   {
				   direction = "en-de";
			   }
			   else if (comboBox.getValue().equals("German - English"))
			   {
				   direction = "de-en";
			   }
			   else
			   {
				   //nothing selected message
			   }
				   
			   System.out.println(fileName);
			   translator.translateFile(fileName.getText(), trFileName.getText(), direction);
			   
			   //message 
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	
	public void add(Scene scene, VBox vBox)
	{			
		GridPane grid = new GridPane();
		 
		grid.setGridLinesVisible(true);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(40);
		grid.setPadding(new Insets(150, 200, 300, 300));
		    
		Text title = new Text("Add a new word to the dictionary:");
		title.setFont(Font.font("Tahoma", 40));
	
		Label textToTransl = new Label("Please enter the english word:");
		textToTransl.setFont(Font.font(20));
		TextField toTransl = new TextField();
		    
		Label textTranslation = new Label("Please enter the translation:");
		textTranslation.setFont(Font.font(20));
		TextField translatedText = new TextField();
		 
		Button button = new Button("Submit");  
		HBox box = new HBox(10);
		box.setAlignment(Pos.BOTTOM_RIGHT);
		box.getChildren().add(button);

		grid.add(title, 0, 0, 2, 1);
		grid.add(textToTransl, 0, 1);
		grid.add(toTransl, 1, 1);
		grid.add(textTranslation, 0, 2);
		grid.add(translatedText, 1, 2);
		grid.add(box, 1, 3);
		 
		//added message
		Text message = new Text();
		grid.add(message, 1, 6);
		  
//		HBox gridBox = new HBox(10);
//		gridBox.setAlignment(Pos.BOTTOM_RIGHT);
//		gridBox.getChildren().add(grid);
		
		
		Group add = new Group(grid);
		add.getChildren().add(vBox);
		scene.setRoot(add); 
		 
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		{ 
			@Override 
		    public void handle(MouseEvent e) 
		    { 
		    	message.setFill(Color.FIREBRICK);
		    	message.setFont(Font.font(20));
		    	message.setText("Word added to dictionary");
		    } 
		};   
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		 
	}
	
	public void remove(Scene scene, VBox vBox)
	{	
	    GridPane grid = new GridPane();
		
	    grid.setGridLinesVisible(true);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(40);
		grid.setPadding(new Insets(150, 100, 300, 300));
		grid.setMinSize(200, 200); 
		    
		 Text title = new Text("Remove word from dictionary:");
		 title.setFont(Font.font("Tahoma", 40)); 
	
		 Label textToTransl = new Label("Please enter the word you want to remove:");
		 textToTransl.setFont(Font.font(20));
		 TextField toTransl = new TextField();
		    
//		 Label textTranslation = new Label("Please enter the translation:");
//		 textTranslation.setFont(Font.font(20));
//		 TextField translatedText = new TextField();
		 
		 Button button = new Button("Submit");  
		 HBox box = new HBox(10);
		 box.setAlignment(Pos.BOTTOM_RIGHT);
		 box.getChildren().add(button);

		 grid.add(title, 0, 0, 2, 1);
		 grid.add(textToTransl, 0, 1);
		 grid.add(toTransl, 1, 1);
//		 grid.add(textTranslation, 0, 2);
//		 grid.add(translatedText, 1, 2);
		 grid.add(box, 1, 3); 
	    
	    Group remove = new Group(grid);
	    remove.getChildren().add(vBox);
	    scene.setRoot(remove); 
	    
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
//		    	message.setFill(Color.FIREBRICK);
//		    	message.setFont(Font.font(20));
//		    	message.setText("Word added to dictionary");
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	    
	}
	
	public void loadDic(Scene scene, VBox vBox)
	{
		 GridPane grid = new GridPane();
		 
		 grid.setGridLinesVisible(true);
		 grid.setAlignment(Pos.CENTER);
		 grid.setHgap(50);
		 grid.setVgap(40);
		 grid.setPadding(new Insets(150, 100, 300, 300));
		 grid.setMinSize(200, 200); 
		    
		 Text title = new Text("Load a Dictionary from File:");
		 title.setFont(Font.font("Tahoma", 40)); 
	
		 Label textToTransl = new Label("Please enter the details of the dictionary:");
		 textToTransl.setFont(Font.font(20));
		 TextField toTransl = new TextField();
		    
//		 Label textTranslation = new Label("Please enter the translation:");
//		 textTranslation.setFont(Font.font(20));
//		 TextField translatedText = new TextField();
		 
		 Button button = new Button("Submit");  
		 HBox box = new HBox(10);
		 box.setAlignment(Pos.BOTTOM_RIGHT);
		 box.getChildren().add(button);

		 grid.add(title, 0, 0, 2, 1);
		 grid.add(textToTransl, 0, 1);
		 grid.add(toTransl, 1, 1);
//		 grid.add(textTranslation, 0, 2);
//		 grid.add(translatedText, 1, 2);
		 grid.add(box, 1, 3); 
	
	    Group loadDic = new Group(grid);
	    loadDic.getChildren().add(vBox);
	    scene.setRoot(loadDic); 
	    
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
//		    	message.setFill(Color.FIREBRICK);
//		    	message.setFont(Font.font(20));
//		    	message.setText("Word added to dictionary");
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	    
	    
	}
	
	public void displayDic(Scene scene, VBox vBox)
	{
		 GridPane grid = new GridPane();
		 
		 grid.setGridLinesVisible(true);
		 grid.setAlignment(Pos.CENTER);
		 grid.setHgap(50);
		 grid.setVgap(40);
		 grid.setPadding(new Insets(150, 100, 300, 300));
		 grid.setMinSize(200, 200); 
		    
		 Text title = new Text("Display the Dictionary:");
		 title.setFont(Font.font("Tahoma", 40)); 
	
		 Label textToTransl = new Label("Please enter the details of the dictionary:");
		 textToTransl.setFont(Font.font(20));
		 TextField toTransl = new TextField();
		    
//		 Label textTranslation = new Label("Please enter the translation:");
//		 textTranslation.setFont(Font.font(20));
//		 TextField translatedText = new TextField();
		 
		 Button button = new Button("Submit");  
		 HBox box = new HBox(10);
		 box.setAlignment(Pos.BOTTOM_RIGHT);
		 box.getChildren().add(button);

		 grid.add(title, 0, 0, 2, 1);
		 grid.add(textToTransl, 0, 1);
		 grid.add(toTransl, 1, 1);
//		 grid.add(textTranslation, 0, 2);
//		 grid.add(translatedText, 1, 2);
		 grid.add(box, 1, 3); 
	
	    Group displayDic = new Group(grid);
	    displayDic.getChildren().add(vBox);
	    scene.setRoot(displayDic); 
	    
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
//		    	message.setFill(Color.FIREBRICK);
//		    	message.setFont(Font.font(20));
//		    	message.setText("Word added to dictionary");
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	    
	}
	
	public void saveDic(Scene scene, VBox vBox)
	{
		 GridPane grid = new GridPane();
		 
		 grid.setGridLinesVisible(true);
		 grid.setAlignment(Pos.CENTER);
		 grid.setHgap(50);
		 grid.setVgap(40);
		 grid.setPadding(new Insets(150, 100, 300, 300));
		 grid.setMinSize(200, 200); 
		    
		 Text title = new Text("Save the Dictionary to file:");
		 title.setFont(Font.font("Tahoma", 40)); 
	
		 Label textToTransl = new Label("Please enter the details of the dictionary:");
		 textToTransl.setFont(Font.font(20));
		 TextField toTransl = new TextField();
		    
//		 Label textTranslation = new Label("Please enter the translation:");
//		 textTranslation.setFont(Font.font(20));
//		 TextField translatedText = new TextField();
		 
		 Button button = new Button("Submit");  
		 HBox box = new HBox(10);
		 box.setAlignment(Pos.BOTTOM_RIGHT);
		 box.getChildren().add(button);

		 grid.add(title, 0, 0, 2, 1);
		 grid.add(textToTransl, 0, 1);
		 grid.add(toTransl, 1, 1);
//		 grid.add(textTranslation, 0, 2);
//		 grid.add(translatedText, 1, 2);
		 grid.add(box, 1, 3); 
	
	    Group saveDic = new Group(grid);
	    saveDic.getChildren().add(vBox);
	    scene.setRoot(saveDic); 
	    
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
		 { 
		   @Override 
		   public void handle(MouseEvent e) 
		   { 
//		    	message.setFill(Color.FIREBRICK);
//		    	message.setFont(Font.font(20));
//		    	message.setText("Word added to dictionary");
		   } 
		 };   
		 button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	    
	    
	}
}
