package stringcalculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xuyang Feng
 * @version 1.0
 * StringCalculator is a class offers expression tokenization and calculation. 
 * The following operators are supported:
 * 		Basic: +, -, *, /
 * 		Scientific: ^, E, %
 * 		Syntax: (, )
 *
 */
public class StringCalculator {

	// This is a self-defined exception used when an expression syntax error is detected.
	@SuppressWarnings("serial")
	public class ExpressionSyntaxError extends Exception {

		public ExpressionSyntaxError() {
			super("Expression Syntax Error!");
		}

		public ExpressionSyntaxError(String message) {
			super(message);
		}

	}

	// The super class of all operators
	// easier to manage or add new operators
	protected abstract class Operator {
		public final int level;		// Define the priority of an operator

		protected Operator(int l) {
			level = l;
		}

		// All operators have the run method
		public abstract Double run(Double val1, Double val2);

		// Recognize each operator by the toString() methond
		public abstract String toString();
	}

	protected class OperatorAdd extends Operator {
		protected OperatorAdd() {
			super(1);
		}

		@Override
		public Double run(Double val1, Double val2) {
			return val1 + val2;
		}

		public String toString() {
			return "+";
		}

	}

	protected class OperatorMinus extends Operator {
		protected OperatorMinus() {
			super(1);
		}

		@Override
		public Double run(Double val1, Double val2) {
			return val1 - val2;
		}

		public String toString() {
			return "-";
		}
	}

	protected class OperatorMulti extends Operator {
		protected OperatorMulti() {
			super(2);
		}

		@Override
		public Double run(Double val1, Double val2) {
			return val1 * val2;
		}

		public String toString() {
			return "*";
		}
	}

	protected class OperatorDevide extends Operator {
		protected OperatorDevide() {
			super(2);
		}

		@Override
		public Double run(Double val1, Double val2) throws ArithmeticException{
			if(val2 == 0) throw new ArithmeticException("Devided by zero!");
			return val1 / val2;
		}

		public String toString() {
			return "/";
		}
	}

	protected class OperatorRemain extends Operator {
		protected OperatorRemain() {
			super(2);
		}

		@Override
		public Double run(Double val1, Double val2) {
			return val1 % val2;
		}

		public String toString() {
			return "%";
		}
	}

	protected class OperatorPow extends Operator {
		protected OperatorPow() {
			super(3);
		}

		@Override
		public Double run(Double val1, Double val2) {
			return Math.pow(val1, val2);
		}

		public String toString() {
			return "^";
		}
	}

	protected class OperatorSci extends Operator {
		protected OperatorSci() {
			super(2);
		}

		@Override
		public Double run(Double val1, Double val2) {
			return val1 * Math.pow(10, val2);
		}

		public String toString() {
			return "E";
		}
	}

	Stack<Double> valueStack;
	Stack<String> opStrStack;

	// Constructor, initialize stacks to store history states
	public StringCalculator() {
		valueStack = new Stack<Double>();
		opStrStack = new Stack<String>();
	}

	// Simple factory method design pattern, return an Operator instance according to the input string
	protected Operator operatorFactory(String s) {
		if (s.equals("+"))
			return new OperatorAdd();
		else if (s.equals("-"))
			return new OperatorMinus();
		else if (s.equals("*"))
			return new OperatorMulti();
		else if (s.equals("/"))
			return new OperatorDevide();
		else if (s.equals("%"))
			return new OperatorRemain();
		else if (s.equals("E"))
			return new OperatorSci();
		else if (s.equals("^"))
			return new OperatorPow();
		else
			return null;
	}
	
	// Input a value or operator, return a value if any operation is executed, else return empty string
	public String input(String s) throws ExpressionSyntaxError,
			ArithmeticException {
		Double valueDouble = Double.NaN;
		if (isNumeric(s))		// Input is a number, push into value stack
			valueStack.push(Double.parseDouble(s));
		else if (s.equals("("))
			opStrStack.push(s);
		else if (s.equals(")")) {		// Input is a right bracket, finish what is inside
			if (opStrStack.empty())
				throw new ExpressionSyntaxError();
			while (!opStrStack.peek().equals("(")) {
				Operator operator = operatorFactory(opStrStack.pop());
				if (valueStack.size() < 2)
					throw new ExpressionSyntaxError();
				Double val2 = valueStack.pop();
				Double val1 = valueStack.pop();
				valueDouble = operator.run(val1, val2);
				valueStack.push(valueDouble);
			}
			opStrStack.pop(); // Pop out "(" in opStrStack
		} else if (s.equals("=")) {		// finish all the calculation
			if (valueStack.size() == 1 && opStrStack.empty()) {		// Only one number, no operation called, return itself
				valueDouble = valueStack.pop();
			} else if (valueStack.size() < 2)		// Not sufficient arguments
				throw new ExpressionSyntaxError();
			else {		// Finish the calculation
				while (!opStrStack.empty()) {
					Operator operator = operatorFactory(opStrStack.pop());
					if (valueStack.size() < 2)
						throw new ExpressionSyntaxError();		// Not sufficient arguments
					Double val2 = valueStack.pop();
					Double val1 = valueStack.pop();
					valueDouble = operator.run(val1, val2);
					valueStack.push(valueDouble);
				}
			}
		} else {		// Input is an operator
			Operator thisOperator = operatorFactory(s);
			if(thisOperator == null) throw new ExpressionSyntaxError();		// Illegal operator detected
			while (!opStrStack.empty()		// Finish some of the operations stored in the operation stack with the same or higher priorities
					&& !opStrStack.peek().equals("(")	// rest of the operations are outside the bracket which means lower priority
					&& operatorFactory(opStrStack.peek()).level >= thisOperator.level) {	// Continue calculating if the rest have the same or higher priority
				Operator preOperator = operatorFactory(opStrStack.pop());
				Double val2 = valueStack.pop();
				Double val1 = valueStack.pop();
				valueDouble = preOperator.run(val1, val2);
				valueStack.push(valueDouble);
			}
			opStrStack.push(s);
		}

		if (valueDouble.isNaN()) {
			return "";		// No operation is executed
		} else {
			return String.valueOf(valueDouble);		// Return operation result
		}

	}

	//	Static method, compute a line of expressions all at once
	//  This line must be finished with "="
	public static String getResultFromExpression(String expression)
			throws ArithmeticException, ExpressionSyntaxError {
		StringCalculator expreessionCalculator = new StringCalculator();
		Queue<String> expressionQueue = Tokenize(expression);
		String output = null;
		while (!expressionQueue.isEmpty()) {
//			 System.out.println("Token: " + expressionQueue.peek());	// For debug usage
			output = expreessionCalculator.input(expressionQueue.poll());
		}
		return output;
	}

	// Static method
	// Parse a line of expressions into arguments and operators in a queue
	protected static LinkedList<String> Tokenize(String expreString) {
		Queue<String> tokenized = new LinkedList<String>();
		int start = 0;
		boolean previousArgumentIsNullOrLeftBracket = true;		//  Distinguish "-" if it's a negative sign in value 
		for (int i = 0; i < expreString.length(); i++) {		//	or an operator
			char c = expreString.charAt(i);
			if (c == '+' || (!previousArgumentIsNullOrLeftBracket && c == '-')
					|| c == '*' || c == '/' || c == '(' || c == ')' || c == 'E'
					|| c == '%' || c == '=' || c == '^') {
				if (start != i) {
					tokenized.offer(expreString.substring(start, i));
					previousArgumentIsNullOrLeftBracket = false;
				}
				tokenized.offer(String.valueOf(c));
				if (c == '(')
					previousArgumentIsNullOrLeftBracket = true;
				start = i + 1;
			}

			if ((i == expreString.length() - 1) && start <= i) {
				tokenized.offer(expreString.substring(start));
			}
		}
		return (LinkedList<String>) tokenized;
	}

	// Parse character by character to decide whether it's a number or not
	private static final boolean isNumeric(final String s) {
		if (s == null || s.isEmpty())
			return false;
		boolean hasPoint = false;
		for (int x = 0; x < s.length(); x++) {
			final char c = s.charAt(x);
			if (x == 0 && (c == '-') && (s.length() > 1))
				continue; // negative
			if (x > 0 && (c == '.') && !hasPoint) {
				hasPoint = true;	// A number can only has one decimal point at most
				continue;
			} // check points
			if ((c >= '0') && (c <= '9'))
				continue; // 0 - 9
			return false; // invalid
		}
		return true; // valid
	}
}
