package rpnCalculator;

import java.util.Stack;
import java.lang.Exception;

public class RPNCalculator {
// create a stack to store numbers
	private Stack<Double> stack1;

	public RPNCalculator() {
		stack1 = new Stack<Double>();
	}

	
	
	public String getRes(String rpn) throws Exception {
       // push numbers to stack
		if (RPNCalculator.isNum(rpn)) {
			stack1.push(Double.parseDouble(rpn));
			return String.valueOf(stack1.peek());
		} 
	   // calculate when if it is a operator 
		else if(RPNCalculator.isOperator(rpn)) {
			if (stack1.size() >= 2) {
				Double n1 = stack1.pop();
				Double n2 = stack1.pop();
				char c = rpn.charAt(0);

				switch (c) {
				case '+':
					stack1.push(n1 + n2);
					break;
				case '-':
					stack1.push(n2 - n1);
					break;
				case '*':
					stack1.push(n1 * n2);
					break;
				case '/':
					if (n1 == 0) {
						throw new Exception("Devided by zero!");
					} else {
						stack1.push(n2 / n1);
					}
					break;
				case '^':
					stack1.push(Math.pow(n2, n1));
				}
			} else {	// if the number of the numbers in stack is not enough for operate
				throw new Exception("Not enough arguments!");
			}
		}
		return String.valueOf(stack1.peek());
	}


	public String swap(String rpn) throws Exception{
		double temp;
		if(stack1.empty()) throw new Exception("The argument stack is empty!");
		temp = stack1.pop();
		stack1.push(Double.parseDouble(rpn));
		return String.valueOf(temp);
		
	
	}

	public static boolean isNum(String rpn) {
		char c = rpn.charAt(0);
		if (c >= '0' && c <= '9')
			return true;
		return false;
	}

	public static boolean isOperator(String rpn) {
		if (rpn == "+" || rpn == "-" || rpn == "*" || rpn == "/"|| rpn=="^")
			return true;
		return false;
	}


}
