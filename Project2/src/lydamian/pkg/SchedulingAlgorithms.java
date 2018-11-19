package lydamian.pkg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList; 
import java.util.Queue;

public class SchedulingAlgorithms {

	
	// This method calculates the average turnaround time for FIFO scheduling
	// Algorithm
	double calculateTurnaroundTime(int[] arr) {
		int sum = 0;
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return (double)sum/(double)arr.length;
	}
	
	double calculateTurnaroundTime(ArrayList<JobAnalytics> jobList) {
		int sum = 0;
		for(int i = 0; i < jobList.size(); i++) {
			sum += jobList.get(i).realTime;
		}
		return (double)sum/(double)jobList.size();
	}
	
	// This method builds the analytics given a
	// array of integers (real-times)
	// output: returns a string of Ti, ri...
	String buildAnalytics(int[] arr) {
		String result;
		StringBuilder buildString = new StringBuilder();
		double t = calculateTurnaroundTime(arr);
		t = Math.round(t * 100.0) / 100.0;
		buildString.append(t);
		buildString.append(" ");
		
		for(int i = 0; i < arr.length; i++) {
			buildString.append(arr[i]);
			if(i < arr.length-1) {
				buildString.append(" ");
			}
		}
		result = buildString.toString();
		return result;
		
	}
	
	//overloaded method buildAnalytics for type ArrayList<JobAnalytics>
	String buildAnalytics(ArrayList<JobAnalytics> jobList) {
		String result;
		StringBuilder buildString = new StringBuilder();
		double t = calculateTurnaroundTime(jobList);
		t = Math.round(t * 100.0) / 100.0;
		buildString.append(t);
		buildString.append(" ");
		
		//sort this fucking data structure first.
		Collections.sort(jobList);
		
		for(int i = 0; i < jobList.size(); i++) {
			buildString.append(jobList.get(i).realTime);
			if(i < jobList.size()-1) {
				buildString.append(" ");
			}
		}
		result = buildString.toString();
		return result;
		
	}
	
	// this method calculates in order the real time spend
	// of each process as a string
	String calculateRealTime() {
		return "";
	}
	
	//This method returns the index of an ArrayList<jobs> object
	// where the shortest job resides, breaking ties by order
	// lower order (earlier process) first.
	int getShortestJobIndex(ArrayList<Jobs> jobList) {
		if(jobList == null || jobList.size() == 0) {
			return -1; 
		}
		int length = jobList.size();
		int minIndex = 0;
		
		for(int i = 1; i < length; i++) {
			if(jobList.get(i).serviceTime < jobList.get(minIndex).serviceTime) {
				minIndex = i;
			}
		}
		//System.out.println("shortest time is " + jobList.get(minIndex).serviceTime );
		return minIndex;
	}
	
	//this method checks whether that currentJob is done,
	// given a time, and an job.
	boolean isJobFinished(int startTime, int serviceTime, int currTime) {
		
		if(startTime == -1 || serviceTime < 0 || currTime < 0) {
			System.out.println("error in isJobFinished()");
			return false;
		}
		
		int finishTime = startTime + serviceTime;
		if(finishTime <= currTime) {
			return true;
		}
		return false;
	}
	
	// ====== First in First Out ==============================
	// Jobs are executed on a first come first serve basis
	// It is a non-preemptive scheduling algorithm
	// =========================================================
	public String FIFO(ArrayList<Jobs> originalJobsList) {
		//local variables
 		ArrayList<Jobs> jobsList = new ArrayList<Jobs>(originalJobsList);
		String analytics = "";
		Queue<Integer> q = new LinkedList<>();
		int time = 0;
		Jobs activeJob = null;
		boolean lock = false;
		// Collections.sort(jobsList); //sorting the ArrayList of jobs
		int[] turnaroundTimes = new int[jobsList.size()];
		int timeElapsed = 0;
		int i = 0;
		
		if(originalJobsList.size() < 1) {
			System.out.println("FIFO given an empty string as argument...");
			return "";
		}
		
		// store each turnaround time for each process in the turnaroundTimes
		while(jobsList.size() > 0 || activeJob != null) {
			if(lock == false) { //there is no activeJob running, find new active
				activeJob = jobsList.remove(0);
				lock = true;
				//timeElapsed = 0;
				}
			
			if(lock == true) {
				if(activeJob.arrivalTime <= time) { // finishtime - startime
					turnaroundTimes[i] = (time + activeJob.serviceTime) - (activeJob.arrivalTime);
					time += activeJob.serviceTime;
					lock = false; 
					activeJob = null; 
					i++;
					continue;
				}
			}	
			time++;
	
		}
		
		// calculate average turnaround time and real time of each process
		// averageTurnaroundTime = (finishTime-arrivalTimes)/numberOfProcess's
		analytics = buildAnalytics(turnaroundTimes);
		return analytics;
	} 
	
	// ====== Shortest Job First ===============================
	// This is a non-preemptive
	// Selects for execution the process with the smallest
	// execution time.
	// =========================================================
	public String SJF(ArrayList<Jobs> originalJobsList) {
		//local variables
		ArrayList<Jobs> jobsList = new ArrayList<Jobs>(originalJobsList);
		ArrayList<Jobs> arrivedJobs = new ArrayList<Jobs>(); // keeps track of the arrivedJobs
		ArrayList<JobAnalytics> turnaroundTimes = new ArrayList<JobAnalytics>();
		Jobs activeJob = null;
		boolean isJob = false;
		int tempIndex;
		String analytics = "";
		int time = 0;
		int activeJobStartTime = -1;
		int activeJobServiceTime = -1;
		
		int j = 0;
		for(time = 0; jobsList.size() > 0 || arrivedJobs.size() > 0 || isJob != false; time++) {
			//add arrivedJobs to ArrayList<queue> - note this maintains stability
			for(int i = 0; i < jobsList.size(); i++) {
				if(jobsList.get(i).arrivalTime <= time) {
					arrivedJobs.add(jobsList.remove(i));
					i = i-1;
				}
			}
			
			if(isJob == false) {
				tempIndex = getShortestJobIndex(arrivedJobs);
				if(tempIndex == -1) { //there are no jobs currently in the arrivedJobs queue
					continue;
				}
				else { //get the shortest job
					activeJob = arrivedJobs.remove(tempIndex);
					activeJobStartTime = time;
					activeJobServiceTime = activeJob.serviceTime;
					isJob = true;
				}
			}
			else { //there is an activeJob
				if(isJobFinished(activeJobStartTime, activeJobServiceTime, time)) { //job is finished, do stuff, else nothing
					isJob = false;
					activeJobStartTime = -1;
					activeJobServiceTime = -1;
					turnaroundTimes.add(new JobAnalytics(activeJob.pid, time - activeJob.arrivalTime));
					j++;
					activeJob = null;
					
					//get next activeJob, if none, then wait.
					tempIndex = getShortestJobIndex(arrivedJobs);
					if(tempIndex == -1) {
						continue;
					}
					else {
						isJob = true;
						activeJob = arrivedJobs.remove(tempIndex);
						activeJobStartTime = time;
						activeJobServiceTime = activeJob.serviceTime;
						
					}
				}
			}
		}
		
		analytics = buildAnalytics(turnaroundTimes);
		
		return analytics;
	}
	
	// ====== Shortest Remaining Time ===========================
	// The preemptive version of the SJN algorithm
	// Processor is allocated the job clostest to completion
	// But it can preempted by a newer ready job with shorter time
	// to completion
	//============================================================
	public String SRT(ArrayList<Jobs> originalJobsList) {
		ArrayList<Jobs> jobsList = new ArrayList<Jobs>(originalJobsList);
		ArrayList<Jobs> arrivedJobList = new ArrayList<Jobs>();
		ArrayList<JobAnalytics> turnaroundTimes = new ArrayList<JobAnalytics>();
		String analytics = "";
		boolean isJob = false; // keeps track whether their is an active Job being run in the cpu
		int activeJobIndex = -1; // index of the activeJob corresponding to arrivedJobList
		int time = 0;
		int tempIndex = -1;
		
		for(time = 0; jobsList.size() > 0 || arrivedJobList.size() > 0 || isJob != false; time++ ) {
			// get all newly arrived Jobs into the arrived Queue
			for(int i = 0; i < jobsList.size(); i++) {
				if(jobsList.get(i).arrivalTime <= time) {
					arrivedJobList.add(jobsList.remove(i));
					i = i-1;
				}
			}
			
			if(isJob == false) { // there is no activeJob
				//find the smallest service time job in arrivedList
				tempIndex = getShortestJobIndex(arrivedJobList);
				if(tempIndex == -1) { // no jobs in arrivedJobList
					continue;
				}
				else {
					activeJobIndex = tempIndex;
					isJob = true;
				}
			}
			else { //there is an activeJob
				// check if the job is finished if not check poll arrivedJobListQueue
				arrivedJobList.get(activeJobIndex).serviceTime--;
				if(arrivedJobList.get(activeJobIndex).serviceTime < 1) { // currently activeJob is finished
					turnaroundTimes.add(new JobAnalytics(arrivedJobList.get(activeJobIndex).pid, time-arrivedJobList.get(activeJobIndex).arrivalTime));
					isJob = false;
					arrivedJobList.remove(activeJobIndex);
					activeJobIndex = -1;
					tempIndex = getShortestJobIndex(arrivedJobList);
					if(tempIndex == -1) {
						continue;
					}
					else {
						activeJobIndex = tempIndex;
						isJob = true;
					}
				}
				else {
					tempIndex = getShortestJobIndex(arrivedJobList);
					if(tempIndex == -1) {
						continue;
					}
					else if(tempIndex == activeJobIndex) {
						continue;
					}
					else {
						activeJobIndex = tempIndex;
					}
				}
			}
			
		}
		
		analytics = buildAnalytics(turnaroundTimes);
		return analytics;
	}
	
	// ======= Multilevel Feedback Queue Scheduling ===============
	// 5 Queue's preemptive if higher process in queue in fifo order (nonpreemptive)
	// ============================================================
	public String MLF(ArrayList<Jobs> jobsList) {
		String analytics = "";
		
		
		
		
		
		return analytics;
	}
	
	 public class JobAnalytics  implements Comparable<JobAnalytics> {
		int pid;
		int realTime;
		
		// Constuctors
		JobAnalytics(){
			pid = -1;
			realTime = 0;
		}
		
		JobAnalytics(int pid, int realTime){
			this.pid = pid;
			this.realTime = realTime;
		}
		
		void setJobAnalytics(int pid, int realTime){
			this.pid = pid;
			this.realTime = realTime;
		}
		
		@Override
		public int compareTo(JobAnalytics j)
	    {
	        return this.pid - j.pid;     //Sorts the objects in ascending order
	         
	    }
		
		
	}
	
}
