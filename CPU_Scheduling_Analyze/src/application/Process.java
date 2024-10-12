package application;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class Process {

	static int ProcessNumber;
	int pid;
	int remainingTime;
	int priority;
	int arrivalTime;
	int CPUBurst;
	int startTime; //  -1 = has not start
	int turnaround;
	int finishTime; //  -1 = has not finished yet
	int waitingTime;

	SimpleIntegerProperty pid1;
	SimpleIntegerProperty CPUBurst1;
	SimpleIntegerProperty arrivalTime1;
	SimpleIntegerProperty priority1;
	SimpleIntegerProperty StartTime1;
	SimpleIntegerProperty TA1;

	public Integer getPid1() {
		return pid1.get();
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public Integer getArrivalTime1() {
		return arrivalTime1.get();
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getCPUBurst1() {
		return CPUBurst1.get();
	}

	public void setCPUBurst(int cPUBurst) {
		CPUBurst = cPUBurst;
	}

	public Integer getPriority1() {
		return priority1.get();
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Integer getStartTime1() {
		return this.startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getTA1() {
		return this.turnaround;
	}

	public void setTA1(SimpleIntegerProperty tA1) {
		TA1 = tA1;
	}

	public void setTurnaround(int turnaround) {
		this.turnaround = turnaround;
	}

	public int getTurnaround() { return turnaround; }

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Process() {
		Random rand = new Random();
		this.CPUBurst = rand.nextInt(30) + 1;
		this.priority = rand.nextInt(5) + 1;
		this.pid = ProcessNumber;
		ProcessNumber++;
		this.arrivalTime = rand.nextInt(ProcessNumber) + 1;

		pid1 = new SimpleIntegerProperty(pid);
		CPUBurst1 = new SimpleIntegerProperty(this.CPUBurst);
		arrivalTime1 = new SimpleIntegerProperty(this.arrivalTime);
		priority1 = new SimpleIntegerProperty(this.priority);

		this.startTime = -1;
		this.finishTime = -1;
		this.remainingTime = this.CPUBurst;
		TA1 = new SimpleIntegerProperty();
		StartTime1 = new SimpleIntegerProperty();

	}

	public void resetProcess() { // function to reset a process (re-initialize it)
		 this.startTime = -1;
		 this.finishTime = -1;ُ
		 this.remainingTime = this.CPUBurst ;
		 this.turnaround = -1 ;
		 this.waitingTime = -1 ;

	}

	public void ToString() {
		System.out.println(ProcessNumber + "\n" + CPUBurst + "\n" + arrivalTime);
	}

}
