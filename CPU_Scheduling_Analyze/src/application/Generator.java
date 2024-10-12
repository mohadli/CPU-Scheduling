package application;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Generator {
    public static void generateProcess(int n) throws IOException {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            Process p = new Process();
            Scheduler.Processes.add(p);
        }
        String s="";
        for (Process p : Scheduler.Processes) {
            s = p.getPid1()+ " " + p.getCPUBurst1() + " " + p.getArrivalTime1()+ " " + p.getPriority1();
            res.append(s).append("\n");
        }
        FileWriter writer = new FileWriter("input.txt");
        writer.write(String.valueOf(res));
        Process.ProcessNumber=0;
    }
    public static void load() {
        Scheduler.Processes.clear();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage primaryStage = new Stage();
        File file = fileChooser.showOpenDialog(primaryStage);
        String s = "", ress = "";
        int arrive = 0, pr = 0, id = 0, burst = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((s = reader.readLine()) != null) {
                String[] split = s.split("\\s+");
                id = Integer.parseInt(split[0]);
                burst = Integer.parseInt(split[1]);
                arrive = Integer.parseInt(split[2]);
                pr = Integer.parseInt(split[3]);
                Process pp = new Process();
                pp.setArrivalTime(arrive);
                pp.setCPUBurst(burst);
                pp.setPriority(pr);
                pp.setPid(id);
                Scheduler.Processes.add(pp);
                System.out.println(id);

            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: Bad Input File");
        }


    }
}
