package cs2321;
import net.datastructures.*;
import cs2321util.HeapAPQ;
public class TaskScheduling<K,V> {
	/**
	 * Goal: Perform all the tasks using a minimum number of machines. 
	 * 
	 *       
	 * @param tasks tasks[i][0] iars start time for task i
	 *              tasks[i][1] is end time for task i
	 * @return The minimum number or machines
	 */
   public static int NumOfMachines(int[][] tasks) {
	   //initiate the number of machines at one, because there is one machine by default.
	    int numMachines = 1;
	    //create two PQ's, one that contains start and end, and one for just the end.
		HeapAPQ<Integer, Integer> Start = new HeapAPQ<Integer,Integer>();
		HeapAPQ<Integer, Integer> End = new HeapAPQ<Integer, Integer>();
		//Add all the elements from the array into the PQ in min heap order.
		for (int i = 0; i < tasks.length; i ++) {
			Start.insert(tasks[i][0], tasks[i][1]);
			End.insert(tasks[i][1], null);
		}
		//
		while (!(Start.isEmpty())) {
			//remove the min from start so we can access the next element.
			Start.removeMin();
			//if removing this element leave us without anything, then we have already
			//finished and should return the number.
			if (Start.isEmpty()) {
				return numMachines;
			}
			//if the start time is less than the end time of the lowest available
			//machine, then we need to add another.
			if (Start.min().getKey() < End.min().getKey()) {
				numMachines++;
			}
			//if it isn't, then we add the event, by removing the min, so the next lowest
			//availability is being checked.
			else if (!(End.isEmpty())) {
				End.removeMin();
			}
		}
	  return numMachines;
   }
}
