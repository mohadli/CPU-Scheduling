package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class ChartController {
    @FXML
    public BarChart<String,Number> barchart ;
    @FXML
    public Button BackButton;
    public CategoryAxis Category;
    public NumberAxis Numbers;
    public static XYChart.Series<String,Number> sch = new XYChart.Series<>();
    public static XYChart.Series<String,Number> wt = new XYChart.Series<>();
    public static XYChart.Series<String,Number> rt = new XYChart.Series<>();
    public static XYChart.Series<String,Number> tt = new XYChart.Series<>();

    public void initialize() {
        sch.setName("Scheduling Time");
        wt.setName("Avg Waiting");
        rt.setName("Avg Response");
        tt.setName("Avg Turnaround");
        barchart.getData().addAll(wt,rt,tt);
    }
    public static void fcfs(){
        sch.getData().add(new XYChart.Data<>("FCFS",Report.schTime));
        wt.getData().add(new XYChart.Data<>("FCFS",Report.avgWT));
        rt.getData().add(new XYChart.Data<>("FCFS",Report.avgRT));
        tt.getData().add(new XYChart.Data<>("FCFS",Report.avgTT));
    }
    public static void priority(){
        sch.getData().add(new XYChart.Data<>("Priority",Report.schTime));
        wt.getData().add(new XYChart.Data<>("Priority",Report.avgWT));
        rt.getData().add(new XYChart.Data<>("Priority",Report.avgRT));
        tt.getData().add(new XYChart.Data<>("Priority",Report.avgTT));
    }
    public static void pp(){
        sch.getData().add(new XYChart.Data<>("PP",Report.schTime));
        wt.getData().add(new XYChart.Data<>("PP",Report.avgWT));
        rt.getData().add(new XYChart.Data<>("PP",Report.avgRT));
        tt.getData().add(new XYChart.Data<>("PP",Report.avgTT));
    }
    public static void  sjf(){
        sch.getData().add(new XYChart.Data<>("SJF",Report.schTime));
        wt.getData().add(new XYChart.Data<>("SJF",Report.avgWT));
        rt.getData().add(new XYChart.Data<>("SJF",Report.avgRT));
        tt.getData().add(new XYChart.Data<>("SJF",Report.avgTT));
    }
    public static void  psjf(){
        sch.getData().add(new XYChart.Data<>("PSJF",Report.schTime));
        wt.getData().add(new XYChart.Data<>("PSJF",Report.avgWT));
        rt.getData().add(new XYChart.Data<>("PSJF",Report.avgRT));
        tt.getData().add(new XYChart.Data<>("PSJF",Report.avgTT));
    }
    public static void rr(){
        sch.getData().add(new XYChart.Data<>("RR",Report.schTime));
        wt.getData().add(new XYChart.Data<>("RR",Report.avgWT));
        rt.getData().add(new XYChart.Data<>("RR",Report.avgRT));
        tt.getData().add(new XYChart.Data<>("RR",Report.avgTT));
    }
    public void Back(ActionEvent actionEvent) {
        Main.PrimaryStage.setScene(Main.MainScene);
        Main.PrimaryStage.centerOnScreen();
    }
}