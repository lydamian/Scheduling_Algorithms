package lydamian.pkg;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	
	//this method takes as input a list of jobs and converts
	// it to an array of Jobs.
	// [in] - string of jobs (arrival time / service time)
	public static ArrayList<Jobs> inputToJobs(String input) {		
		String[] arr = null;
		ArrayList<Jobs> jobs = new ArrayList<Jobs>();

		//check if the input is empty
		if(input == null) {
			System.out.println("input is empty");
			return null;
		}
		arr = input.split(" ");
		
		if(arr.length%2 != 0) {
			System.out.println("there is not an even number of inputs");
		}

		int pid = 0;
		for(int i = 0; i < arr.length-1; i=i+2, pid++) {
			jobs.add(new Jobs(Integer.parseInt(arr[i]), Integer.parseInt(arr[i+1]), pid));
		}
		
		return jobs;
	}
	
	static void displayJob(ArrayList<Jobs> myJobs) {
		System.out.println(" --- Data Structure Object ----");
		for(int i = 0; i < myJobs.size(); i++) {
			System.out.println("Arrival-Time " + myJobs.get(i).arrivalTime + " Service-Time: " + myJobs.get(i).serviceTime + " pid: " + myJobs.get(i).pid);
		}
	}
	
	public static void testing() {
		System.out.println("Damian Ly - 82239081: CS143b Scheduling Algorithm Project2 Starting...");
		Scanner fileReader = null;
		FileWriter fileWriter = null;
		ArrayList<Jobs> jobsList = new ArrayList<Jobs>();
		String fifoResult;
		String sjfResult;
		String srtResult;
		String mlfResult;
		SchedulingAlgorithms schedulingAlgorithms = new SchedulingAlgorithms();
		
		// Read file input and for each line (arrival time and service time)
		// find the turnaround time for each scheduling algorithm and each of
		// the indivial service times
		File inputFile = new File("sample_input.txt").getAbsoluteFile();
		File outputFile = new File("sample_output.txt").getAbsoluteFile();
		
		System.out.println(inputFile.toString());
		
		if( !inputFile.isFile())
		{
			System.out.println("the input file does not exist");
		}

		try {
			fileReader = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			fileWriter = new FileWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter out = new BufferedWriter(fileWriter);
		
		while(fileReader.hasNext()) {
			String jobs = fileReader.nextLine();
			System.out.println(jobs);
			jobsList = inputToJobs(jobs);
			displayJob(jobsList);
			/*
		    for each algorithm: 
			read pairs ari ti from file input.txt on memory stick
			output results into a file nnn.txt, where nnn is your matriculation number, to the same memory stick
			output file should contain 4 separate lines of the form
			T r1 r2 … rn
			 */
			fifoResult = schedulingAlgorithms.FIFO(jobsList);
			sjfResult = schedulingAlgorithms.SJF(jobsList);
			srtResult = schedulingAlgorithms.SRT(jobsList);
			mlfResult = schedulingAlgorithms.MLF(jobsList);
			
			System.out.println("--- displaying result --- ");
			System.out.println(fifoResult);
			System.out.println(sjfResult);
			System.out.println(srtResult);
			System.out.println(mlfResult);
			System.out.println("---- end ---- ");
			
			try {
				fileWriter.write(fifoResult);
				fileWriter.write("\r\n");
				fileWriter.write(sjfResult);
				fileWriter.write("\r\n");
				fileWriter.write(srtResult);
				fileWriter.write("\r\n");
				fileWriter.write(mlfResult);
				fileWriter.write("\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		testing();  
	}
		
}

