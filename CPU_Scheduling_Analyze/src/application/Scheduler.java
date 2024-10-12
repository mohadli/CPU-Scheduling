package application;

import java.util.*;

public class Scheduler {

	public static ArrayList<Process> Processes = new ArrayList<Process>();
	public static ArrayList<Process> ReadyQueue ;
	public static int TimeQuantum;
	public static Process FindLeastRemainingTimeProcess() {
		int minRemainingTime = Integer.MAX_VALUE;
		Process leastRemainingTimeProcess = null;
		for (Process p : ReadyQueue) {
			if (p.remainingTime < minRemainingTime) {
				leastRemainingTimeProcess = p;
				minRemainingTime = p.remainingTime;
			}
		}
		return leastRemainingTimeProcess;
	}
	public static Process findHighestPriorityProcess(int time) {
		int highestpriority = -1;
		Process highestpriorityprocess = null;
		for (Process p : ReadyQueue) {
			if (p.priority > highestpriority) {
				highestpriorityprocess = p;
				highestpriority = p.priority;
			}
			}
		return highestpriorityprocess;
	}
	public static void checkProcessesArrival(int time) {
		for (Process p : Processes)
			if (p.arrivalTime == time)
				ReadyQueue.add(p);
	}
	public static boolean allProcessesFinished() {
		for (Process p : Processes)
			if (p.finishTime == -1)
				return false;
		return true;
	}
	public static void addToGanttChart(ArrayList<Integer> ganttChart, Process currentlyRunning) { // function to add a
		if (currentlyRunning != null)
		{
			ganttChart.add(currentlyRunning.pid);

		} else
			ganttChart.add(-1);
	}
	// ----------------------------------------------------------------------------------------------------
	//  main SRTF method
	public static ArrayList<Integer> SRTF() {
		int time = 0;
		Process currentlyRunning = null;
		ReadyQueue = new ArrayList<Process>(100);
		ArrayList<Integer> ganttChart = new ArrayList<Integer>();
		while (!allProcessesFinished()) {
			checkProcessesArrival(time);
			if (currentlyRunning != null)
				currentlyRunning.remainingTime--;
			Process leastRemainingTimeProcess = FindLeastRemainingTimeProcess();
			currentlyRunning = leastRemainingTimeProcess;
			if (currentlyRunning != null && currentlyRunning.startTime == -1)
				currentlyRunning.startTime = time;
			if (currentlyRunning != null && currentlyRunning.remainingTime == 0) {
				currentlyRunning.finishTime = time;
				currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;
				currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.CPUBurst;
				int currentlyRunningIdx = ReadyQueue.indexOf(currentlyRunning);
				ReadyQueue.remove(currentlyRunningIdx);
				currentlyRunning = null;
				leastRemainingTimeProcess = FindLeastRemainingTimeProcess();
				currentlyRunning = leastRemainingTimeProcess;
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time; }
			addToGanttChart(ganttChart, currentlyRunning); // Gantt Chart
			timeUnit(); time++;
		}
		Report.calculate("PSJF");
		return ganttChart;
	}
	// -----------------------------------------------------------------------------------------------------------------
	//   Round Robin Scheduling Algorithm
	public static ArrayList<Integer> RR() {
		int time = 0;
		Process currentlyRunning = null;
		int remainingTimeQuantum;
		int i = 0;

		ReadyQueue = new ArrayList<Process>(100);
		ArrayList<Integer> ganttChart = new ArrayList<Integer>();
		remainingTimeQuantum = TimeQuantum;
		while (!allProcessesFinished()) {
			checkProcessesArrival(time);
			if (currentlyRunning != null) {
				remainingTimeQuantum--;
				currentlyRunning.remainingTime--;
			}
			if (currentlyRunning != null && currentlyRunning.remainingTime == 0) {
				currentlyRunning.finishTime = time;
				currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;
				currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.CPUBurst;
				int currentlyRunningIdx = ReadyQueue.indexOf(currentlyRunning);
				ReadyQueue.remove(currentlyRunningIdx);
				currentlyRunning = null;
				if (ReadyQueue.size() != 0) {
					currentlyRunning = ReadyQueue.get(currentlyRunningIdx % ReadyQueue.size());
					remainingTimeQuantum = TimeQuantum;
					if (currentlyRunning != null && currentlyRunning.startTime == -1)
						currentlyRunning.startTime = time;
				}
			}
			if ((currentlyRunning == null || remainingTimeQuantum == 0) && ReadyQueue.size() != 0) {
				i = (i + 1) % ReadyQueue.size();
				currentlyRunning = ReadyQueue.get(i);
				remainingTimeQuantum = TimeQuantum;
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time;
			}
			addToGanttChart(ganttChart, currentlyRunning);
			timeUnit(); time++;
		}
		Report.calculate("RR");
		return ganttChart;
	}
	// --------------------------------------------------------------------------------------------------------------
	// Before SJF , this method is used to find the process with the
	public static Process findLeastBurstTimeProcess() {
		int minBurstTime = Integer.MAX_VALUE;
		Process leastBurstTimeProcess = null;
		for (Process p : ReadyQueue) {
			if (p.CPUBurst < minBurstTime) {
				leastBurstTimeProcess = p;
				minBurstTime = p.CPUBurst;
			}
		}
		return leastBurstTimeProcess;
	}
	public static ArrayList<Integer> SJF() {
		int time = 0;
		Process currentlyRunning = null;
		ReadyQueue = new ArrayList<Process>(100);
		ArrayList<Integer> ganttChart = new ArrayList<Integer>();
		while (!allProcessesFinished()) {
			checkProcessesArrival(time);
			if (currentlyRunning == null) {
				Process leastBurstTimeProcess = findLeastBurstTimeProcess();
				int leastBursTimetProcessIdx = ReadyQueue.indexOf(leastBurstTimeProcess);
				if (leastBursTimetProcessIdx != -1)
					currentlyRunning = ReadyQueue.remove(leastBursTimetProcessIdx);
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time;
			}
			if (currentlyRunning != null && time == currentlyRunning.CPUBurst + currentlyRunning.startTime) {
				currentlyRunning.finishTime = time;
				currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;
				currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.CPUBurst;
				currentlyRunning = null;
				Process leastBurstTimeProcess = findLeastBurstTimeProcess();
				int leastBursTimetProcessIdx = ReadyQueue.indexOf(leastBurstTimeProcess);
				if (leastBursTimetProcessIdx != -1)
					currentlyRunning = ReadyQueue.remove(leastBursTimetProcessIdx);
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time;
			}
			addToGanttChart(ganttChart, currentlyRunning);
			timeUnit(); time++;
		}
		Report.calculate("SJF");
		return ganttChart;
	}
	// ===============================================================================================================================
	// The Implementation of FCFS
	public static ArrayList<Integer> FCFS() {
		ArrayList<Integer> ganttChart = new ArrayList<Integer>();
			Collections.sort(Processes, (Object o1, Object o2) -> {
				if (((Process) o1).getArrivalTime1() == ((Process) o2).getArrivalTime1()) {
					return 0;
				} else if (((Process) o1).getArrivalTime1() < ((Process) o2).getArrivalTime1()) {
					return -1;
				} else {
					return 1;
				}
			});
			Processes.get(0).setStartTime(Processes.get(0).getArrivalTime1());
			Processes.get(0).setFinishTime(Processes.get(0).getArrivalTime1() + Processes.get(0).getCPUBurst1());
			for (int i = 1; i < Processes.size(); i++) {
				Processes.get(i).setStartTime(Processes.get(i - 1).getFinishTime());
				Processes.get(i).setFinishTime(Processes.get(i).getStartTime1() + Processes.get(i).getCPUBurst1());
			}
			for (Process p : Processes) {
				p.setWaitingTime(p.getStartTime1() - p.getArrivalTime1());
				p.setTurnaround(p.getWaitingTime() + p.getCPUBurst1());
			}

			for (Process p : Processes)
				addToGanttChart(ganttChart, p);

/*		int time = 0;
		Process currentlyRunning = null;
		ReadyQueue = new ArrayList<Process>();
		while (!allProcessesFinished()) {
			checkProcessesArrival(time);
			if (currentlyRunning == null && (ReadyQueue.size() != 0)) {
				currentlyRunning = ReadyQueue.get(0);
				if (currentlyRunning != null)
					currentlyRunning.startTime = time;
			}
			if (currentlyRunning != null) {
				currentlyRunning.remainingTime--;
				if (currentlyRunning != null && currentlyRunning.remainingTime == 0) {
					currentlyRunning.finishTime = time;
					currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;
					currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.CPUBurst + 1;
					ReadyQueue.remove(0);
					currentlyRunning = null;
					if (ReadyQueue.size() != 0)
						currentlyRunning = ReadyQueue.get(0);
					if (currentlyRunning != null)
						currentlyRunning.startTime = time;
				}
			}
			addToGanttChart(ganttChart, currentlyRunning);
				timeUnit(); time++;
		}*/

		Report.calculate("FCFS");
		return ganttChart;
	}
	// ====================================================================================================]
	// method to display And test if the gannts chart is correct
	public static void displayGanttChart(ArrayList<Integer> ganttChart) { // function to display Gantt Chart

		for (int i = 0; i < ganttChart.size(); i++) {
			System.out.printf("%3d  |  ", i);
		}
		System.out.println("");
		for (Integer integer : ganttChart) {
			if (integer.equals(-1))
				System.out.printf("  NOP  |  ");
			else
				System.out.printf("  %3d |  ", integer);
		}
	}
	// priority Implementation
	public static ArrayList<Integer> Priority() {
		int time = 0;
		Process currentlyRunning = null;
		ReadyQueue = new ArrayList<Process>(100);
		ArrayList<Integer> ganttChart = new ArrayList<Integer>();
		while (!allProcessesFinished()) {
			checkProcessesArrival(time);
			if (currentlyRunning == null) {
				Process highestPriorityProcess = findHighestPriorityProcess(time);
				int highestPriorityIndx = ReadyQueue.indexOf(highestPriorityProcess);
				if (highestPriorityIndx != -1)
					currentlyRunning = ReadyQueue.remove(highestPriorityIndx);
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time;
			}
			if (currentlyRunning != null && time == currentlyRunning.CPUBurst + currentlyRunning.startTime) {
					currentlyRunning.finishTime = time;
				currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;
				currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.CPUBurst;
				currentlyRunning = null;
				Process highestPriorityProcess = findHighestPriorityProcess(time);
				int highestPriorityIndx = ReadyQueue.indexOf(highestPriorityProcess);
				if (highestPriorityIndx != -1)
					currentlyRunning = ReadyQueue.remove(highestPriorityIndx);
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time;
			}
			addToGanttChart(ganttChart, currentlyRunning);
			timeUnit(); time++;
		}
		Report.calculate("Priority");
		return ganttChart;
	}
	// ===============================================================================================================================
	// this is the main PP method
		public static ArrayList<Integer> PP() {
			int time = 0;
			Process currentlyRunning = null;
			ReadyQueue = new ArrayList<Process>(100);
			ArrayList<Integer> ganttChart = new ArrayList<Integer>();
			while (!allProcessesFinished()) {
				checkProcessesArrival(time);
				if (currentlyRunning != null)
					currentlyRunning.remainingTime--;
				Process leastRemainingTimeProcess = findHighestPriorityProcess(time);
				currentlyRunning = leastRemainingTimeProcess;
				if (currentlyRunning != null && currentlyRunning.startTime == -1)
					currentlyRunning.startTime = time;
				if (currentlyRunning != null && currentlyRunning.remainingTime == 0) {
					currentlyRunning.finishTime = time;
					currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;
					currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.CPUBurst;
					int currentlyRunningIdx = ReadyQueue.indexOf(currentlyRunning);
					ReadyQueue.remove(currentlyRunningIdx);
					currentlyRunning = null;
					leastRemainingTimeProcess = findHighestPriorityProcess(time);
					currentlyRunning = leastRemainingTimeProcess;
					if (currentlyRunning != null && currentlyRunning.startTime == -1)
						currentlyRunning.startTime = time;
				}
				addToGanttChart(ganttChart, currentlyRunning);
				timeUnit(); time++;
			}
			Report.calculate("PP");
			return ganttChart;
		}
		// -----------------------------------------------------------------------------------------------------------------
		public static void timeUnit()
		{
			for (int i = 0, t = 0; i < 10000; i++)
				if (i / 2 == 0)
					t = i / 2;
				else
					t = 2 * i;
		}
}
