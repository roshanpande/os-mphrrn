import java.lang.*;
import java.util.*;
class hrrn{    
    //HELPER FUNCTIONS
	static void sortByArrival(Process ob[], int n){//Sort according to arrival time
	       int i, j;       
	       for(i = 0; i < n; i++){
	           for(j = 0; j < n - 1-i; j++){
	               if(ob[j].arrival_time > ob[j+1].arrival_time){
	                   Process temp = ob[j];
	                   ob[j] = ob[j+1];
	                   ob[j+1] = temp;
	               }
	           }
	       }
	    }

    	static void sortByRemainingTime(Process ob[], int n){//Sort according //to arrival time
       int i, j;       
       for(i = 0; i < n; i++){
           for(j = 0; j < n - 1-i; j++){
               if(ob[j].remaining_time < ob[j+1].remaining_time){
                   Process temp = ob[j];
                   ob[j] = ob[j+1];
                   ob[j+1] = temp;
               }
           }
       }
    }

    static boolean readyQueueIsNotEmpty(Process ob[], int n){       
	    for(int i = 0; i < n; i++){
	        if(!ob[i].hasFinished){
	            return true;
	        }
	    }
        return false;
    }

    static void display(Process ob[], int n){       System.out.println("\nid\tArrival\tBurst\tPriority\tStart\tEnd\tTAT\tWT\tRT");
        for(int i = 0; i < n; i++){
            System.out.println(ob[i].id+"\t"+ob[i].arrival_time+"\t"+ob[i].burst_time+"\t"+ob[i].original_priority+"\t\t"+ob[i].start_time+"\t"+ob[i].end_time+"\t"+ob[i].turnaround_time+"\t"+ob[i].wait_time+"\t"+ob[i].response_time);                    
        }
    }
  
  //<-----HRRN WITH PRIORITY & PREEMPTION----->
    static void PMHRRN(Process ob[], int n)
    {
    	double awt = 0.0, atat = 0.0, art = 0.0;
        int time = 0, previous=-1, current = -1;
        while(readyQueueIsNotEmpty(ob, n))
        {
        	
        	updateReadyQueue(ob, n, time);
        	assignPriority(ob,n);
        	current = findMaxPriority(ob, n); 
        	if(ob[current].hasArrived == false)
        	{
        		ob[current].hasArrived = true;
        		ob[current].start_time = time;
        	}
        	if(ob[current].id != previous){//For display purposes, otherwise per unit Process ID will be displayed.
	        	System.out.println("\nAt time t = "+time+", Process "+ob[current].id+" is now in running state");
	        	displayMidExecution(ob, n);
	        	
        	}
        	
        	ob[current].remaining_time--;
        	time++;
        	updateWaitingTime(ob, n, current);
        	if(ob[current].remaining_time == 0)
        	{
        		ob[current].hasFinished = true;
        		ob[current].end_time = time;
        		ob[current].turnaround_time = ob[current].end_time - ob[current].arrival_time;
    			ob[current].response_time = (((float)ob[current].wait_time+(float)ob[current].burst_time)/(float)ob[current].burst_time);
    			atat += ob[current].turnaround_time;
    			awt += ob[current].wait_time;
    			art += ob[current].response_time;
        	}
        	previous = ob[current].id;
        }
        sortByArrival(ob, n);
        display(ob, n);
        awt /= n; atat /= n; art /= n;
        System.out.println("\nA.W.T.: "+awt+"\nA.T.A.T.: "+atat+"\nA.R.T.: "+art);
    }    

	private static void displayMidExecution(Process[] ob, int n) {
		int i;
		System.out.println("ID\tBurst Time\tRemaining\tPriority(E)\tHybrid Priority");
		for(i = 0; i < n; i++)
		{
			if(ob[i].isReady && !ob[i].hasFinished)
			{
				System.out.println(ob[i].id+"\t"+ob[i].burst_time+"\t\t"+ob[i].remaining_time+"\t\t"+ob[i].priority+"\t\t"+ob[i].hybrid_priority);
			}
		}
}
	private static void updateReadyQueue(Process[] ob, int n, int time) {
		int i;
		for(i = 0; i < n; i++)
			if(ob[i].arrival_time <= time)	
ob[i].isReady = true;
    }

	private static int findMaxPriority(Process[] ob, int n) {
		int i,pos = -1;;float max = -1;
		float p =  0.5f;
    	for(i = 0; i < n; i++)
    	{
    		if(ob[i].isReady && !ob[i].hasFinished){
    			ob[i].response_time = (((float)ob[i].wait_time+(float)ob[i].remaining_time)/(float)ob[i].remaining_time);
	    		ob[i].hybrid_priority = ((float)ob[i].response_time * p) + ((1-p)*(float)ob[i].priority);
	    		if(ob[i].hybrid_priority > max){
	    			if(ob[i].hybrid_priority == max){
	    				if(ob[i].arrival_time > ob[pos].arrival_time)
	    					continue;
	    			}
	    			pos = i;
	    			max = ob[i].hybrid_priority;
	    		}
    		}
    	}
    	return pos;
	}

	private static void updateWaitingTime(Process[] ob, int n, int previous ) {
		for(int i = 0; i < n; i++)
		{			
			if (i == previous)	continue;
			if(ob[i].isReady && !ob[i].hasFinished)
			ob[i].wait_time++;
		}	
	}

	private static void assignPriority(Process[] ob, int n) {
    	int i, count = 0, previous = -1;
    	sortByRemainingTime(ob, n);
    	for(i = 0; i < n; i++){
    		if(ob[i].isReady && !ob[i].hasFinished){
    			if(ob[i].remaining_time == previous)
    				ob[i].priority = count;
    			else
    				ob[i].priority = ++count;
    			previous = ob[i].remaining_time;
    			
    			}    			
    		}
	}   
    
    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter number of processes: ");
            int n = sc.nextInt();
            Process p[] = new Process[n];
            for(int i = 0; i < n; i++){
                System.out.println("Process "+(i+1));
                System.out.println("Ënter arrival time, burst time, priority ");
                int arr = sc.nextInt();
                int bur = sc.nextInt();
                int pri = sc.nextInt();
                p[i] = new Process(i+1, arr, bur, pri);
            }
            display(p, n);
            PMHRRN(p, n);
            sc.close();           
    }   
}
class Process{
    int id, priority, original_priority,arrival_time, wait_time, burst_time, start_time, end_time, turnaround_time, remaining_time;
    float hybrid_priority, response_time;
    boolean isReady, hasFinished, hasArrived;
  //Flag = 1 when Remaining time = 0 i.e. process has finished executing
    Process(int id, int a,int b, int p){
        this.id = id;
        this.start_time = 0;
        this.arrival_time = a;
        this.burst_time = b;
        this.original_priority = p;
        this.remaining_time = b;
        this.wait_time = 0;
        this.hasArrived = false;
        this.isReady = false;
        this.hasFinished = false;
        
    }       
}
