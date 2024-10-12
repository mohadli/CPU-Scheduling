package application;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StartController implements Initializable {

    @FXML
    private TableView<Process> Table;

    @FXML
    private TextField field;

    @FXML
    private TableColumn<Process, Integer> IDField;

    @FXML
    private TableColumn<Process, Integer>BurstField;

    @FXML
    private TableColumn<Process, Integer>ProrityField;

    @FXML
    private TableColumn<Process, Integer> StartField;

    @FXML
    private TableColumn<Process, Integer> EndField;

    @FXML
    private TableColumn<Process, Integer> TAField;

    @FXML
    private TableColumn<Process, Integer> WaitField;
    
    
    @FXML
    private ScrollPane processpane;
    
    @FXML
    private  HBox pane;

    @FXML
    private Button Back;

    @FXML
    private Button Show;
    
   
    @FXML
    private Circle c1;

    @FXML
    private Circle c2;

    @FXML
    private Circle c3;
    
    
    double TA ; 

    
    public static ObservableList<Process> Processes = FXCollections.observableArrayList() ;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		// TODO Auto-generated method stub
		
		IDField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("pid1"));
		ProrityField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("priority1"));
		BurstField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("CPUBurst1"));
		StartField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("StartTime1"));
		EndField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("FinishTime"));
		TAField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("TA1"));
		WaitField.setCellValueFactory(new PropertyValueFactory<Process,Integer>("waitingTime"));
		Table.setItems(Processes);
		}
	
    @FXML
    void back(ActionEvent event) {
    	Main.PrimaryStage.setScene(Main.MainScene);
    	Main.PrimaryStage.centerOnScreen();
    }
    
    void setRotation (Circle c , int angel , int duration) {
    	RotateTransition rotate = new RotateTransition(Duration.seconds(duration) , c) ; 
    	rotate.setAutoReverse(true);
    	rotate.setByAngle(angel); 
    	rotate.setCycleCount(2);
    	rotate.setRate(3);
    	rotate.play();
    } 
    

    @FXML
    void show(ActionEvent event)  {
    	
    	setRotation (c2,360,3);
    	setRotation (c2,180,3);
    	
    	
    	pane.getChildren().clear();
    	RotateTransition rt = new RotateTransition(Duration.millis(1000), c1);
    	
    	rt.setCycleCount(4);
    	rt.setByAngle(125);
    	rt.setOnFinished(e->{

        	Random rand = new Random();
        	for (int i = 0 ; i < Controller.ganttChart.size() ; i ++) {
            StackPane pane1= new StackPane() ;
        	Rectangle rectangle = new Rectangle(70,70);
        	Label label = new Label( );
        	if (Controller.ganttChart.get(i)== -1 ) label.setText( "NOP" ); 
        	else label.setText(Scheduler.Processes.get(i).startTime +"\n" + "P" +  Controller.ganttChart.get(i).toString());
        	rectangle.setFill(new Color( rand.nextDouble(),rand.nextDouble(),rand.nextDouble()  , 1 ));
        	label.setTextFill(Color.WHITE );
        	pane1.getChildren().addAll(rectangle,label) ;
        	pane.getChildren().add(pane1) ; 
        	
        

        	
        	
        	processpane.setContent(pane); }
        	
    	});
    	rt.play();
    	
    	double sum =0 ;    	
    	for (int i =0 ; i < Scheduler.Processes.size();i ++ ) {
    		
    		
    		sum += Scheduler.Processes.get(i).turnaround ; 
    		
    		
    		
    	} 
    	TA = sum/ Scheduler.Processes.size() ; 
    	
    	field.setText(String.valueOf(TA)) ;
    }
    
}
			
			
			
			

	



