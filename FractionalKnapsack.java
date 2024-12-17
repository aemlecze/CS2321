package cs2321;
import net.datastructures.PriorityQueue;
import java.util.Comparator;
import cs2321util.DefaultComparator;
import cs2321util.HeapAPQ;
/**
 * @author:
 * @param <E>
 * @param <V>
 * @param <K>
 * @param <E>
 *
 */
public class FractionalKnapsack<E> {

   
	/**
	 * Goal: Choose items with maximum total benefit but with weight at most W.
	 *       You are allowed to take fractional amounts from items.
	 *       
	 * @param items items[i][0] is weight for item i
	 *              items[i][1] is benefit for item i
	 * @param knapsackWeight
	 * @return The maximum total benefit. Please use double type operation. For example 5/2 = 2.5
	 * 		 
	 */
	
	public double MaximumValue(int[][] items, int knapsackWeight) {
		HeapAPQ<Double,Double> heap = new HeapAPQ<Double,Double>();
		double [][] j = new double [items.length][2];
		int count = 1;
		double sum = 0;
		double value = 0;
		int l = items.length;
		DefaultComparator<E> c = new DefaultComparator<E>();
		//add cost, weight to the heap
		for (int i = 0; i < items.length; i++) {
			Double weight = (double)items[i][0];
			Double cost = ((double)items[i][1] / (double)items[i][0]); 
			heap.insert(cost, weight);
		}
		//sort based off cost
		for (int i = 0; i < items.length; i++) {
			j[i][0] = heap.min().getKey();
			j[i][1] = heap.min().getValue();
			heap.removeMin();	
			}
		//maximizes the value
		while ((sum < knapsackWeight)) {
			//while the sum is less than the desired weight, the loop will continue
			//variable r calculates the remainder every iteration
			double r = knapsackWeight - sum;
			//if the weight of the current index plus the already counted weight
			//is less than the knapwackWeight, we will add
			//all of the cost with its weight, because the last index has the most
			// benefit.
			//I added this after I got the auto grader.  I was getting array out of bounds
			//this protects against that.

			if (c.compare((E)(Object)(double)(j[l - count][1] + sum),(E)(Object)(double)knapsackWeight) <= 0 ) {
				//add all the weight to the sum
				sum = sum + j[l - count][1];
				//the value is equal to the cost times the weight.
				value = value + ((double)j[items.length - count][0] * (double)j[items.length - count][1]);
				//to reach the next index, count++
				count++; 
				if (count > l) {
					return value;
				}
			}
			//if the next weight will overflow the sack, we need to add only the weight
			// allowed, which is r.
			else if (j[l - (count)][1] > r) {
				sum = sum + r;
				value = value + (j[items.length - count][0] * r);
			}
			}	
		//when all is done, we have maximized the benefit.
		return value;
}
}