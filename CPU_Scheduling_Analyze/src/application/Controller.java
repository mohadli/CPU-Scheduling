package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {

	public static int ProcessNumber;
	String method = new String();

	@FXML
	private ComboBox<String> AlgMenu;

	@FXML
	private TextField QuantumField;

	@FXML
	private TextField NumberField;

	@FXML
	private Button GenerateButton;

	@FXML
	private Button ResetButton;

	@FXML
	private Button StartButton;

	@FXML
	private Button ShowButton;

	@FXML
	private Button quit;

	@FXML
	private TextArea area;


	public static ArrayList<Integer> ganttChart = new ArrayList<Integer>();

	@FXML
	void Generate(ActionEvent event) throws IOException {
		Scheduler.Processes.clear();
		Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText());

		FadeTransition ft = new FadeTransition(Duration.millis(1000), GenerateButton);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);

		ft.setOnFinished(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(ProcessNumber + " Processes has been generated ");
			alert.show();
		});
		ft.play();
		ProcessNumber = Integer.parseInt(NumberField.getText());
		//--------------------------------------------------------GENERATE+++++++++++++++++++++++++++++++
		Generator.generateProcess(ProcessNumber);
		reportArea();
	}

	@FXML
	void GetNumber(ActionEvent event) {

		ProcessNumber = Integer.parseInt(NumberField.getText());

	}

	@FXML
	void quit(ActionEvent event) {

		System.exit(0);

	}

	@FXML
	void GetQuantum(ActionEvent event) {
		Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AlgMenu.getItems().addAll("FCFS", "SJF", "SRTF", "RR", "Priority", "PP");
		area.setEditable(false);


	}

	// TODO Auto-generated method stub
	@FXML
	String method(ActionEvent event) {
		method = AlgMenu.getValue().toString();

		if (method.compareTo("RR") == 0) {
			QuantumField.setEditable(true);

		}
		if (method.compareTo("RR") != 0) {
			QuantumField.setEditable(true);

		}
		return method;

	}

	@FXML
	void showTable(ActionEvent event) {

		TableController.Processes.clear();

		for (int i = 0; i < Scheduler.Processes.size(); i++) {
			Process process = Scheduler.Processes.get(i);

			TableController.Processes.add(process);
		}

		Main.PrimaryStage.setScene(Main.showScene);
		Main.PrimaryStage.centerOnScreen();

	}

	@FXML
	void stat(ActionEvent event) {
		Main.PrimaryStage.setScene(Main.Chart);
		Main.PrimaryStage.centerOnScreen();
	}


	@FXML
	void StartButtonPressed(ActionEvent event) {
		ganttChart.clear();
		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Scheduler.Processes.get(i).resetProcess();

		}

		switch (method) {
			case "RR": {
				Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText());
				ganttChart = Scheduler.RR();
				break;
			}
			case "FCFS": {

				ganttChart = Scheduler.FCFS();
				break;
			}
			case "SJF": {

				ganttChart = Scheduler.SJF();
				break;
			}
			case "PP": {

				ganttChart = Scheduler.PP();
				break;
			}
			case "SRTF": {

				ganttChart = Scheduler.SRTF();
				break;
			}

			case "Priority": {

				ganttChart = Scheduler.Priority();
				break;
			}

		}

		StartController.Processes.clear();
		Scheduler.displayGanttChart(ganttChart);

		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Process process = Scheduler.Processes.get(i);

			StartController.Processes.add(process);
		}

		Main.PrimaryStage.setScene(Main.FinalScene);
		Main.PrimaryStage.centerOnScreen();
		//Stage stage = new Stage() ;
		//stage.setScene(Main.FinalScene);
		//stage.show();
	}

	@FXML
	void ResetButtonPressed(ActionEvent event) {
		Scheduler.Processes.clear();
		FadeTransition ft = new FadeTransition(Duration.millis(1000), ResetButton);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);

		ft.play();


		Scheduler.Processes.clear();

		TableController.Processes.clear();
		QuantumField.clear();
		NumberField.clear();

	}

	@FXML
	void ResetButton1Pressed(ActionEvent event) {
		Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText());
			Generator.load();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(Scheduler.Processes.size() + " Processes has been Loaded ");
		alert.show();
		reportArea();
	}
	void reportArea(){
		String ta1 = "", ta2 = "", ta3 = "", ta4 = "", ta5 = "", ta6 = "";
		ganttChart =Scheduler.FCFS();
		ta1 =Report.rResult;
		ChartController.fcfs();
		System.out.println(ta1);

		if(Scheduler.Processes.size()<11000) {
			for (Process p : Scheduler.Processes)
				p.resetProcess();
			ganttChart = Scheduler.Priority();
			ta2 = Report.rResult;
			ChartController.priority();

			for (Process p : Scheduler.Processes)
				p.resetProcess();
			ganttChart = Scheduler.RR();
			ta3 = Report.rResult;
			ChartController.rr();


			for (Process p : Scheduler.Processes)
				p.resetProcess();
			ganttChart = Scheduler.SJF();
			ta4 = Report.rResult;
			ChartController.sjf();


			for (Process p : Scheduler.Processes)
				p.resetProcess();
			ganttChart = Scheduler.SRTF();
			ta5 = Report.rResult;
			ChartController.psjf();


			for (Process p : Scheduler.Processes)
				p.resetProcess();
			ganttChart = Scheduler.PP();
			ta6 = Report.rResult;
			ChartController.pp();

		}
        area.replaceSelection(
				"\n "+ta1
                        +"\n"+ta2
                        +"\n"+ta3
                        +"\n"+ta4
                        +"\n"+ta5
						+"\n"+ta6
                        +"\n\n");
		}

}
