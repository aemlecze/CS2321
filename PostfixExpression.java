package cs2321;
/* @author Andrew Mleczek
*/
public class PostfixExpression {
	
	/**
	 * Evaluate a postfix expression. 
	 * Postfix expression notation has operands first, following by the operations.
	 * For example:
	 *    13 5 *           is same as 13 * 5 
	 *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6  
	 *    
	 * In this homework, expression in the argument only contains
	 *     integer, +, -, *, / and a space between every number and operation. 
	 * You may assume the result will be integer as well. 
	 * @param <E>
	 * 
	 * @param exp The postfix expression. You may assume the expression is valid. 
	 * @return the result of the expression. 
	 */
	public static <E> int evaluate(String exp) {
		//TO DO: implement this function with the help of Stack
		
				/* IMPOTANT NOTE:  
				 * Since the argument exp is a string, you need to parse the string expression first to get  
				 * operands and operators first. Because we knew there is a space between the operands and operators,
				 * you can use the function string.split(" ") to return an array of tokens in exp. 
				 */
				//the first few lines are simple.  I am just declraring a new list, and a few variables that will be helpful
				LinkedListStack<E> list = new LinkedListStack<E>();
				int spaces = 0;
				//this array, as you suggested, uses the string.split() method and 
				//stores each element in their own space.  First I counted the spaces
				// so I knew the size of the array.
				for (int i = 0; i < exp.length(); i ++) {
					if(exp.charAt(i)== 32) {
						spaces++;
					}
				}
				String [] tokens = new String[spaces];
				Integer N;
				Integer one;
				Integer two;
				Integer total;
					//this loop splits the array by its spaces
					for(int i = 0; i < exp.length(); i ++ ) {
					tokens = exp.split(" ", spaces + 1);
					}
					//this loop is simply for traversing the array
					for (int i = 0; i < tokens.length; i++) {
						//I used a switch, just so I could manually code every case.
						switch (tokens[i]) {
						case "+":
							//If the array index is equal to +, the total line will add them.
							//I had a lot of trouble with casting, but the easiest way was to cast the pop to an integer,
							//because I only ever stored integers into my list, so it will never error.
							one = (Integer) list.pop();
							two = (Integer) list.pop();
							total = (one + two);
							list.push((E) total);
							break;
						case "*":
							//If it is *, it multiplies the elements,
							//and uses the same casting as the last time
							one = (Integer) list.pop();
							two = (Integer) list.pop();
							total = (one * two);
							list.push((E) total);
							break;
						case "/": 
							//The next two are the same, with their respected operators
							one = (Integer) list.pop();
							two = (Integer) list.pop();
							total = (two / one);
							list.push((E) total);
							break;
						case"-":
							one = (Integer) list.pop();
							two = (Integer) list.pop();
							total = (two - one);
							list.push((E) total);
							break;
						default:
							//If it isnt an operator, it has to be a number, 
							//so N will find the value of the string from the array, and push it into the list.
							N = Integer.valueOf(tokens[i]);
							list.push((E) N);
						}	
					}
					//the last casting error I ran into was this.  It wouldnt let me put it all in 
					//the return statement, so I had to cast to an integer before I returned it.
					int g = (Integer) list.pop();
					return g;
	}
				
	
				
				}


