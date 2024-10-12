 package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


 public class Main extends Application {
	
	public static Scene MainScene ; 
	public static Scene showScene ; 
	public static Scene FinalScene ;
	 public static Scene Chart ;
	 public Scene OpeningScene ;
	public static Stage PrimaryStage ; 
	//public static Stage SecondaryStage= new Stage() ; 
	
	@Override	
	public void start(Stage primaryStage) {
		try {
			PrimaryStage = primaryStage ; 
			
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		    MainScene = new Scene(root,1280.0,800);
			MainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Pane Opening = (Pane)FXMLLoader.load(getClass().getResource("OpeningScene.fxml"));
		    OpeningScene = new Scene(Opening,1280.0,800.0);
		    OpeningScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
		    
			Pane show = (Pane)FXMLLoader.load(getClass().getResource("ShowScene.fxml"));
		    showScene = new Scene(show,1280.0,800.0);
		    showScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Pane chartt = (Pane)FXMLLoader.load(getClass().getResource("Chart.fxml"));
			Chart = new Scene(chartt,1280.0,800.0);
			Chart.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			AnchorPane table = (AnchorPane)FXMLLoader.load(getClass().getResource("FinalScene.fxml"));
		    FinalScene = new Scene(table,1280.0,800.0);
		    FinalScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
	

			//------------------------------------------------------------------------------------
			primaryStage.setTitle("CPU Scheduler");
			primaryStage.setScene(OpeningScene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			//SecondaryStage.setScene(FinalScene);
			
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		/*int numberOfProcesses = 10;
		Generator.generateProcess(numberOfProcesses);
		ArrayList<Integer> all = new ArrayList<Integer>() ;
		all = Scheduler.PP();
		Report.calculate("PP");
		System.out.println(Report.rResult);*/
		launch(args);
	}
}
