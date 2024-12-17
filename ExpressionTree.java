package cs2321;
import cs2321.LinkedBinaryTree.Node;
import net.datastructures.*;

public class ExpressionTree {
	
	//Given a binary tree associated with an arithmetic expressions with operators: +,-,*,/ and decimal numbers
	//Evaluate the result and return the result. You may assume the given tree is in correct form. 
	
	//Example 1:
	//    +
	//  /  \
	// 2  4.5
	//
	// (2+4.5) = 6.5
	// Expected output: 6.5
	
	
	//Example 2:
	//
	//        +
	//      /   \
	//    *       *
	//  /  \     /  \
	//  2   -   3   2
	//     / \
	//     5  1 
	//
	// ((2*(5-1))+(3*2)) = 14
	// Expected output: 14
	//
	// Use Double.parseDouble(string) to convert string to double. 
	// For example to convert string "6.5" to double 6.5. 
	//
	//helper method for checking operators.
	public static boolean isOp(String str) {
		return (str.equals("*")) || (str.equals("+")) || (str.equals("-")) || (str.equals("/"));
		}
	
	public static double eval(BinaryTree<String> tree) {
		//I could not use nodes, but positions worked, and positions are nodes!!!
		Position<String> p = tree.root();
		Position<String> pP;
		String res;
		Double left;
		Double right;
		//checks if the current position is an operator
		//if it is it will go down the left
		while ((isOp(p.getElement()))) {
			p = tree.left(p);
			//if it runs out of room, it will check right
			if (tree.isExternal(p)) {
				pP = tree.parent(p);
				p = tree.right(pP);
			}
			//if it is not an operator, then both numbers are ok, and we 
			//can do operations with the parent.
			if (!(isOp(p.getElement()))) {
				pP = tree.parent(p);
				p = tree.right(pP);
			}
		}
		//the parent, named op, will be an operator
		Position<String> op = tree.parent(p);
		if (op.getElement().equals("-")) {
			//left is the value to the left, and right is to the right, 
			//it does the operation domained by the if statement.
			left = Double.parseDouble(tree.left(op).getElement());
			right = Double.parseDouble(tree.right(op).getElement());
			res = String.valueOf(left - right);
			op.setElement(res);
			//the element is now null, so isExternal still works.
			p.setElement(null);
		}
		//the same operations follow for the rest of the operators.
		if (op.getElement().equals("+")) {
			left = Double.parseDouble(tree.left(op).getElement());
			right = Double.parseDouble(tree.right(op).getElement());
			res = String.valueOf(left + right);
			op.setElement(res);
			p.setElement(null);
		}
		if (op.getElement().equals("*")) {
			left = Double.parseDouble(tree.left(op).getElement());
			right = Double.parseDouble(tree.right(op).getElement());
			res = String.valueOf(left * right);
			op.setElement(res);
			p.setElement(null);
		}
		if (op.getElement().equals("/")) {
			left = Double.parseDouble(tree.left(op).getElement());
			right = Double.parseDouble(tree.right(op).getElement());
			res = String.valueOf(left / right);
			op.setElement(res);
			p.setElement(null);
			
		}
		//if the root is still an operator, then the method is not finished
		// and we recursively cycle
		if(isOp(tree.root().getElement())) {
		eval(tree);
		}
		//when it is finally over, the return statement is parsed as a string
		//to a double
		Double d = Double.parseDouble(tree.root().getElement());
		return d;
	}
	
	//Given a binary tree associated with an arithmetic expressions with operators: +,-,*,/ 
	//and decimal numbers or variables
	
	// Generate the expression with parenthesis around all sub expressions except the leave nodes.  
	// You may assume the given tree is in correct form. 
	// Example:
	//        +
	//      /   \
	//    *       *
	//  /  \     /  \
	//  2   -   3   b
	//     / \
	//     a  1 
	//
	// Expected output: ((2*(a-1))+(3*b)) 
	
	public static String toExpression(BinaryTree<String> tree) {
			//I used a lot of the same code to traverse the tree
			String result = "";
			String left;
			String right;
			String op;
			String temp = "";
			Position<String> p = tree.root();
			Position<String> pP;
			while ((isOp(p.getElement()))) {
				p = tree.left(p);
				if (tree.isExternal(p)) {
					pP = tree.parent(p);
					p = tree.right(pP);
				}
				if (!(isOp(p.getElement()))) {
					pP = tree.parent(p);
					p = tree.right(pP);
				}
			}
			//I added the elements to a string, and returned the result.
			Position<String> par = tree.parent(p);
			op = par.getElement();
			left = tree.left(par).getElement();
			right = tree.right(par).getElement();
			result = "(" + left + op + right + temp+ ")";
			temp = result;
			
			return result;
	}
	


}
