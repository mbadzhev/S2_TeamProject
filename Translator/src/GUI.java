import java.awt.Event;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
			Menu translate = new Menu("Translate");
			menuBar.getMenus().add(translate);
			
			//Add word menu option
			Menu dictionary = new Menu("Dictionary");
			menuBar.getMenus().add(dictionary);
			MenuItem menuItem1 = new MenuItem("Add word from dictionary");
			MenuItem menuItem2 = new MenuItem("Remove word from dictionary");
			MenuItem menuItem3 = new MenuItem("Load Dictionary");
			MenuItem menuItem4 = new MenuItem("Display Dictionary");
			MenuItem menuItem5 = new MenuItem("Save Dictionary");
			Button button1=new Button("a");
			button1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() 
			{ 
				@Override
	            public void handle(MouseEvent e) 
	            { 
	                System.out.println("worked");
	            } 
	        });
			
			dictionary.getItems().add(menuItem1);
			dictionary.getItems().add(menuItem2);
			dictionary.getItems().add(menuItem3);
			dictionary.getItems().add(menuItem4);
			dictionary.getItems().add(menuItem5);
			
			//Setting menu option
			Menu settings = new Menu("Settings");
			MenuItem item1 = new MenuItem("Turn on automatic adding of words");
			MenuItem item2 = new MenuItem("Item 2");
			menuBar.getMenus().add(settings);
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
		    VBox vBox = new VBox(menuBar);
		    
		    root.getChildren().add(vBox);
		    
			Scene scene = new Scene(root, 800, 400);
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
