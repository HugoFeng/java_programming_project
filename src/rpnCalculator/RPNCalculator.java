package rpnCalculator;

import java.util.Stack;

public class RPNCalculator {
// create a stack to store numbers
	private Stack<Double> stack1;

	public RPNCalculator() {
		stack1 = new Stack<Double>();
	}

	
	
	public String getRes(String rpn) {
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
						System.out.println("infinite");
					} else {
						stack1.push(n2 / n1);
					}
					break;
				case '%':
					stack1.push(n2 % n1);
				case '^':
					stack1.push(Math.pow(n2, n1));
					
				}
			} 
			// if the number of the numbers in stack is not enough for operate
			else {
				return "not enough numbers for calculating";
			}
		}
		return String.valueOf(stack1.peek());
	}


	public String swap(String rpn){
		double temp;
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
		if (rpn == "+" || rpn == "-" || rpn == "*" || rpn == "/"||rpn == "%" || rpn=="^")
			return true;
		return false;
	}


}
