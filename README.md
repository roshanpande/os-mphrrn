OS Mini Project Abstract 
Topic: Scheduling Policy – Modified Preemptive Highest Response Ratio Next[MPHRRN]
Highest Response Ratio Next (HRRN) 
This scheduling is a non-preemptive discipline, in which the priority of each job is dependent on its estimated run time and the amount of time it has spent waiting. Jobs gain higher priority the longer they wait, which prevents indefinite postponement (process starvation). Also, the jobs that have spent a long time waiting compete against those estimated to have short run times. 
Selection function for HRRN is Response Ratio(R) aka Normalised Turnaround Time (NTAT)
R=NTAT=  (Waiting Time+Service Time)/(Service Time)=1+  (Waiting Time)/(Service Time)…………………..(1)
Normalized turnaround time indicates the relative delay experienced by a process. For longer processes that require more service time, more delay is acceptable.
 A low NTAT value indicates less delay and a high quality level of service for a process; however, a high NTAT value indicates more delay and a poor quality level of service. The lowest and best possible NTAT value is 1.0. This happens when a process doesn’t experience any waiting time.
HRRN prevents indefinite postponements but is neither preemptive nor suitable for priority systems.
 In this project, we will try to modify the standard non preemptive HRRN to accommodate external priority and to include preemption to compare the results with respect to other scheduling policies.
Modified Highest Response Ratio Next (MHRRN)
This scheduling is also non-preemptive like HRRN. But here we also consider the priority of the process. The response ratio, R is considered as the internal priority of a process while the length of service time of that process is considered to be the external priority, E. The hybrid priority of each process is obtained by giving equal weight to both its external and internal priority.
The Hybrid Priority, Hp, of each process is computed as follows:
 Hp= 0.5 * R + 0.5 * E…………………..(2)
Where R is the internal priority of each process and E is the external priority. 
Processes with highest Hp are executed first given that they have arrived.

Design of Preemptive Modified Highest Response Ratio Next (PMHRRN)
The required data (burst times and arrival times of processes) will be taken as input. The length of burst time of a process shall be its external priority while its internal priority shall be determined by the response ratio of each process in the ready queue.
Response ratio, R and hybrid priority, Hp are computed using equations (1) and (2) respectively.
Processes with highest hybrid priority, Hp, will be executed next. After the execution of a burst time, a running process may be preempted if there is another process with a higher hybrid priority. In the event where multiple processes turn up with the highest hybrid priority, the system shall select the process with the earliest arrival time among those processes and then preempt the current running process. If the current running process turns out to be the process with the highest hybrid priority, it continues to run until a process with a higher hybrid priority is found.
PMHRRN Algorithm
1. Start
2. Initialize AWT = 0, ATAT = 0, ART = 0
3. Processes arrive at the ready queue, RQ
4. Processes in RQ are sorted and assigned priority according to burst time
5. Hybrid priority, Hp = 0.5R + 0.5E
6. Are there multiple processes with highest Hp?
If yes, did processes with same highest Hp arrive at same time?
If yes, Pi = any process with highest Hp,
Else Pi = process with earliest arrival time among the processes
Else, Pi = process with highest hybrid priority, Hp
7. Pi executes a burst time
8. Is Remaining Burst Time [Pi] = 0?
If yes, process Pi leaves RQ, calculate WT, R, and TAT of Pi , go to 9
Else go to 4
9. Is RQ = null?
If yes, calculate AWT, ART, ATAT of all processes, go to 10
Else go to 4.
10. STOP

