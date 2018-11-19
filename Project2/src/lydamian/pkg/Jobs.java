package lydamian.pkg;

public class Jobs implements Comparable<Jobs> {
	// Class data members
	int arrivalTime;
	int serviceTime;
	int pid;
	
	// Constructors
	Jobs(int arrivalTime, int serviceTime, int pid){
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.pid = pid;
	}
	
	Jobs(int arrivalTime, int serviceTime){
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}
	
	Jobs(){
		this.arrivalTime = 0;
		this.serviceTime = 0; 
	}
	
	// Class methods
	void setJob(int arrivalTime, int serviceTime) {
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}
	
	void setJob(int arrivalTime, int serviceTime, int pid) {
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.pid = pid;
	}
	
	@Override
    public int compareTo(Jobs j)
    {
        return this.arrivalTime - j.arrivalTime;     //Sorts the objects in ascending order
         
        // return s.id - this.id;    //Sorts the objects in descending order
    }
}
