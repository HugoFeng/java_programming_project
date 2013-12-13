
package stringcalculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StringCalculator {
	
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
		public Double run(Double val1, Double val2) {
			// TODO Auto-generated method stub
			if (val2==0) {
				if (val1>0) {
					return Double.POSITIVE_INFINITY;
				}else if (val1<0) {
					return Double.NEGATIVE_INFINITY;
				}else {
					return Double.NaN;
				}
			}
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			return val1 *  Math.pow(10, val2);
		}

		public String toString() {
			return "E";
		}
	}

	Stack<Double> valueStack;
	Stack<String> opStrStack;
	
	public StringCalculator() {
		// TODO Auto-generated constructor stub
		valueStack = new Stack<Double>();
		opStrStack = new Stack<String>();
	}
	
	protected static LinkedList<String> Tokenize(String expreString) {
		Queue<String>tokenized = new LinkedList<String>();
		int start = 0;
		for (int i = 0; i < expreString.length(); i++) {
			char c = expreString.charAt(i);
			if(c == '+'
					|| c == '-' || c == '*' || c == '/' 
					|| c == '(' || c == ')' || c == 'E' 
					|| c == '%' || c == '=') {
				if (start != i)
					tokenized.offer(expreString.substring(start, i));
				tokenized.add(String.valueOf(c));
				start = i + 1;
			}

			if (i == expreString.length() - 1) {
				tokenized.add(expreString.substring(start));
				continue;
			}
		}
		return (LinkedList<String>) tokenized;
	}

	protected Operator operatorFactory(String s) {
		if (s.equals("+")) return new OperatorAdd();
		else if (s.equals("-")) return new OperatorMinus();
		else if (s.equals("*")) return new OperatorMulti();
		else if (s.equals("/")) return new OperatorDevide();
		else if (s.equals("%")) return new OperatorRemain();
		else if (s.equals("E")) return new OperatorSci();
		else return new OperatorPow();
	}
	
	public String input(String s) {
		Double valueDouble = Double.NaN;
		if (isNumeric(s)) valueStack.push(Double.parseDouble(s));
		else if (s.equals("(")) opStrStack.push(s);
		else if (s.equals(")")){
			while (!opStrStack.peek().equals("(")) {
				Operator operator = operatorFactory(opStrStack.pop());
				Double val2 = valueStack.pop();
				Double val1 = valueStack.pop();
				valueDouble = operator.run(val1, val2);
				valueStack.push(valueDouble);
			}
			if (opStrStack.peek().equals("(")) opStrStack.pop(); //  dirty code
		}
		else if (s.equals("=")){
			while (!opStrStack.empty()) {
				Operator operator = operatorFactory(opStrStack.pop());
				Double val2 = valueStack.pop();
				Double val1 = valueStack.pop();
				valueDouble = operator.run(val1, val2);
				valueStack.push(valueDouble);
			}
		}
		else{
			Operator thisOperator = operatorFactory(s);
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
		}else {
			return String.valueOf(valueDouble);
		}
		
	}
	
	private static final boolean isNumeric(final String s) {
		  if (s == null || s.isEmpty()) return false;
		  boolean hasPoint = false;
		  for (int x = 0; x < s.length(); x++) {
		    final char c = s.charAt(x);
		    if (x == 0 && (c == '-') && (s.length()>1)) continue;  // negative
		    if (x > 0 && (c == '.') && !hasPoint) {hasPoint=true; continue;} // check points
		    if ((c >= '0') && (c <= '9')) continue;  // 0 - 9
		    return false; // invalid
		  }
		  return true; // valid
	}
}
