package application;

public class Report {
    public static int numOfProcesses = 1;
    public static double schTime = 1;
    public static double throughput = 1;
    public static double utilization =100;
    public static double avgWT = 0;
    public static double avgTT = 0;
    public static double avgRT = 0;
    public static String rResult = "";
    public static void calculate (String method){
        int max=0;
        for (Process p : Scheduler.Processes) {
            if (p.getFinishTime() > max) max = p.finishTime;
        }
        schTime = max; max = 0;
        numOfProcesses = Scheduler.Processes.size();
        throughput = round(numOfProcesses/schTime,4);
        double sum=0;
        for (Process p : Scheduler.Processes){
            sum+=p.waitingTime;
        }
        Report.avgWT = round(sum/numOfProcesses,1);

        sum=0;
        for (Process p : Scheduler.Processes){
            sum+=p.turnaround;
        }
        Report.avgTT = round(sum/numOfProcesses,1);

        sum=0;
        for (Process p : Scheduler.Processes){
            sum+=p.startTime-p.arrivalTime;
        }
        Report.avgRT = round(sum/numOfProcesses,1);

        sum=0;
        for (Process p : Scheduler.Processes){
            sum+=p.getCPUBurst1();
        }
        Report.utilization = round(sum/schTime*100,8);
        rResult = method+
        " -> Report: • N="+numOfProcesses+" • AvgWT="+avgWT
                +" • AvgTT="+avgTT +" • AvgRT:"+avgRT+" • TTime="+schTime+"" +
                " • TP="+throughput+" • Util="+utilization  ;
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
