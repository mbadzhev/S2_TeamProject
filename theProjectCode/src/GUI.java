import java.awt.Event;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GUI extends Application
{
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			primaryStage.setTitle("*** TRANSLATOR ***");
			
//Menu			
			MenuBar menuBar = new MenuBar();
			menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
			
			//Translate menu option
			Menu translate = new Menu("Translator");
			menuBar.getMenus().add(translate);
			MenuItem menuItem0 = new MenuItem("Translate");
			translate.getItems().add(menuItem0);
			
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
			MenuItem item1 = new MenuItem("Turn on automatic adding of words");
			MenuItem item2 = new MenuItem("Item 2");
			
			settings.getItems().add(item1);
			settings.getItems().add(item2);

//Start page
			Text text = new Text();
		    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50)); 
		    //setting the position of the text 
		    text.setX(250); 
		    text.setY(90); 
		    text.setFill(Color.GREEN);  
		    text.setStrokeWidth(2);
		    text.setStroke(Color.BLUE);
		    text.setText("Translator:");

		    Group root = new Group(text);
		    Scene scene = new Scene(root, 800, 400);
		   
		    VBox vBox = new VBox(menuBar);
		   

		    
//		    translate.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//				   
//				@Override
//				public void handle(MouseEvent event) {
//					scene.setRoot(group2);
//				}
//		    });
		    
		    
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
		    textToTransl.setFont(Font.font(15));

		    TextArea toTransl = new TextArea();
		    //toTransl.setPrefHeight(100);
		    
		    Label textTranslation = new Label("Translation:");
		    textTranslation.setFont(Font.font(15));

		    TextArea translatedText = new TextArea();
		    
//		    grid.setColumnSpan(translatedText, 3);
//	        grid.setRowSpan(translatedText, 2); 
//		  
		    Button button1 = new Button("Submit"); 
		    Button button2 = new Button("Clear");  

		    grid.add(text1, 0, 0);
		    grid.add(textToTransl, 0, 1);
		    grid.add(toTransl, 0, 2);
		    grid.add(textTranslation, 1, 1);
		    grid.add(translatedText, 1, 2);
		    grid.add(button1, 0, 3); 
		    grid.add(button2, 0, 3); 
		
		    Group transl = new Group(grid);
		    
//Add word page
		    
		    Text text2 = new Text();
		    text2.setFont(Font.font("verdana", 30)); 
		    //setting the position of the text 
		    text2.setX(250); 
		    text2.setY(90); 
		    text2.setFill(Color.GREEN);  
		    text2.setStrokeWidth(1);
		    text2.setStroke(Color.BLUE);
		    text2.setText("Add word:");
		
		    Group add = new Group(text2);
		    
		    
		    
		    
		    
		  
		    menuItem0.setOnAction(e ->
		    {
		    	transl.getChildren().add(vBox);
		    	scene.setRoot(transl);
		    });
		    
		    menuItem1.setOnAction(e -> 
		    {
		    	add.getChildren().add(vBox);
		    	scene.setRoot(add); 
		    });
		    
//		    menuItem2.setOnAction(e -> 
//		    {
//		    	scene.setRoot(group2); 
//		    });
//		    
//		    menuItem3.setOnAction(e -> 
//		    {
//		    	scene.setRoot(group2); 
//		    });
//		    
//		    menuItem4.setOnAction(e -> 
//		    {
//		    	scene.setRoot(group2); 
//		    });
//		    
//		    menuItem5.setOnAction(e -> 
//		    {
//		    	scene.setRoot(group2); 
//		    });
//		    
//		    item1.setOnAction(e -> 
//		    {
//		    	scene.setRoot(group2); 
//		    });
//		    
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
		    
		    
		    root.getChildren().add(vBox);
		    
//		    transl.getChildren().add(menuBar);
//		    add.getChildren().add(menuBar);
		    
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
}
