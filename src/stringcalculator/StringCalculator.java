package stringcalculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xuyang Feng
 * @version 1.0
 * StringCalculator is a class offers expression tokenization and calculation. 
 * The following operators are supported:
 * 		Basic: +, -, *, /, (, )
 * 		Scientific: ^, E
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

	// The super class of 
	protected abstract class Operator {
		public final int level;

		protected Operator(int l) {
			level = l;
		}

		public abstract Double run(Double val1, Double val2);

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

	public StringCalculator() {
		valueStack = new Stack<Double>();
		opStrStack = new Stack<String>();
	}

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

	public String input(String s) throws ExpressionSyntaxError,
			ArithmeticException {
		Double valueDouble = Double.NaN;
		if (isNumeric(s))
			valueStack.push(Double.parseDouble(s));
		else if (s.equals("("))
			opStrStack.push(s);
		else if (s.equals(")")) {
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
		} else if (s.equals("=")) {
			if (valueStack.size() < 2)
				throw new ExpressionSyntaxError();
			while (!opStrStack.empty()) {
				Operator operator = operatorFactory(opStrStack.pop());
				if (valueStack.size() < 2)
					throw new ExpressionSyntaxError();
				Double val2 = valueStack.pop();
				Double val1 = valueStack.pop();
				valueDouble = operator.run(val1, val2);
				valueStack.push(valueDouble);
			}
		} else {
			Operator thisOperator = operatorFactory(s);
			if(thisOperator == null) throw new ExpressionSyntaxError();
			while (!opStrStack.empty()
					&& !opStrStack.peek().equals("(")
					&& operatorFactory(opStrStack.peek()).level >= thisOperator.level) {
				Operator preOperator = operatorFactory(opStrStack.pop());
				Double val2 = valueStack.pop();
				Double val1 = valueStack.pop();
				valueDouble = preOperator.run(val1, val2);
				valueStack.push(valueDouble);
			}
			opStrStack.push(s);
		}

		if (valueDouble.isNaN()) {
			return "";
		} else {
			return String.valueOf(valueDouble);
		}

	}

	public static String getResultFromExpression(String expression)
			throws ArithmeticException, ExpressionSyntaxError {
		StringCalculator expreessionCalculator = new StringCalculator();
		Queue<String> expressionQueue = Tokenize(expression);
		String output = null;
		while (!expressionQueue.isEmpty()) {
			// System.out.println("Token: " + expressionQueue.peek());
			output = expreessionCalculator.input(expressionQueue.poll());
		}
		return output;
	}

	protected static LinkedList<String> Tokenize(String expreString) {
		Queue<String> tokenized = new LinkedList<String>();
		int start = 0;
		boolean previousArgumentIsNullOrLeftBracket = true;
		for (int i = 0; i < expreString.length(); i++) {
			char c = expreString.charAt(i);
			if (c == '+' || (!previousArgumentIsNullOrLeftBracket && c == '-')
					|| c == '*' || c == '/' || c == '(' || c == ')' || c == 'E'
					|| c == '%' || c == '=') {
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

	private static final boolean isNumeric(final String s) {
		if (s == null || s.isEmpty())
			return false;
		boolean hasPoint = false;
		for (int x = 0; x < s.length(); x++) {
			final char c = s.charAt(x);
			if (x == 0 && (c == '-') && (s.length() > 1))
				continue; // negative
			if (x > 0 && (c == '.') && !hasPoint) {
				hasPoint = true;
				continue;
			} // check points
			if ((c >= '0') && (c <= '9'))
				continue; // 0 - 9
			return false; // invalid
		}
		return true; // valid
	}
}
