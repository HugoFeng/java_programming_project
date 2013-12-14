package javaProject;

import stringcalculator.StringCalculator;
import stringcalculator.StringCalculator.ExpressionSyntaxError;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Calculator extends JFrame {

	private JPanel contentPanel;
	private JPanel rpn;
	private JPanel classic;
	private JTextField textField;
	private StringCalculator strCal;
	
	
	public Calculator() {
		
		/*addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
		});*/
		
		//set frame
		setTitle("calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 290);
		setResizable(false);
		setLocationRelativeTo(null);
		
		//add content panel to the frame
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		
		//add a display panel to the content panel
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(10, 10, 344, 62);
		contentPanel.add(displayPanel);
		displayPanel.setLayout(null);
		
		//add text field to the display panel
		textField = new JTextField();
		textField.setText("0");
		textField.setBounds(10, 6, 243, 50);
		displayPanel.add(textField);
		textField.setColumns(10);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		this.strCal = new StringCalculator();
		this.isOpKeyPushed = false;
			
			
		
			
		//add common panel to the content panel	
		JPanel commonButton = new JPanel();
		commonButton.setBounds(10, 82, 257, 169);
		contentPanel.add(commonButton);
		commonButton.setLayout(null);
		
		
		//new button listener
		NumButtonListenerClass numButtonListner = new NumButtonListenerClass();
		OpButtonListenerClass opButtonListner = new OpButtonListenerClass(strCal);
		
		
		//add num1
		JButton button1 = new JButton("1");
		button1.setBounds(15, 38, 48, 23);
		commonButton.add(button1);
		button1.addActionListener(numButtonListner);
		
		//add num2
		JButton button2 = new JButton("2");
		button2.addActionListener(numButtonListner);
		button2.setBounds(78, 38, 48, 23);
		commonButton.add(button2);
		
		//add num3
		JButton button3 = new JButton("3");
		button3.addActionListener(numButtonListner);
		button3.setBounds(141, 38, 48, 23);
		commonButton.add(button3);
		
		//add num4
		JButton button4 = new JButton("4");
		button4.addActionListener(numButtonListner);
		button4.setBounds(15, 71, 48, 23);
		commonButton.add(button4);
		
		//add num5
		JButton button5 = new JButton("5");
		button5.addActionListener(numButtonListner);
		button5.setBounds(78, 71, 48, 23);
		commonButton.add(button5);
		
		//add num6
		JButton button6 = new JButton("6");
		button6.addActionListener(numButtonListner);
		button6.setBounds(141, 71, 48, 23);
		commonButton.add(button6);
		
		//add num7
		JButton button7 = new JButton("7");
		button7.addActionListener(numButtonListner);
		button7.setBounds(15, 104, 48, 23);
		commonButton.add(button7);
		
		//add num8
		JButton button8 = new JButton("8");
		button8.addActionListener(numButtonListner);
		button8.setBounds(78, 104, 48, 23);
		commonButton.add(button8);
		
		//add num9
		JButton button9 = new JButton("9");
		button9.addActionListener(numButtonListner);
		button9.setBounds(141, 104, 48, 23);
		commonButton.add(button9);
		
		
		//add num0
		JButton button0 = new JButton("0");
		button0.addActionListener(numButtonListner);
		button0.setBounds(15, 137, 111, 23);
		commonButton.add(button0);
		
		//add decimal
		JButton buttonDecimal = new JButton(".");
		buttonDecimal.addActionListener(numButtonListner);
		buttonDecimal.setBounds(141, 137, 48, 23);
		commonButton.add(buttonDecimal);
		
		//add plus
		JButton buttonPlus = new JButton("+");
		buttonPlus.addActionListener(opButtonListner);
		buttonPlus.setBounds(204, 38, 48, 23);
		commonButton.add(buttonPlus);
		
		//add minus
		JButton buttonMinus = new JButton("-");
		buttonMinus.addActionListener(opButtonListner);
		buttonMinus.setBounds(204, 71, 48, 23);
		commonButton.add(buttonMinus);
		
		//add multiply
		JButton buttonMultiply = new JButton("*");
		buttonMultiply.addActionListener(opButtonListner);
		buttonMultiply.setBounds(204, 104, 48, 23);
		commonButton.add(buttonMultiply);
		
		//add divide
		JButton buttonDivide = new JButton("/");
		buttonDivide.addActionListener(opButtonListner);
		buttonDivide.setBounds(204, 137, 48, 23);
		commonButton.add(buttonDivide);
		
		//add power
		JButton buttonPower = new JButton("^");
		buttonPower.addActionListener(opButtonListner);
		buttonPower.setBounds(141, 5, 48, 23);
		commonButton.add(buttonPower);
		
		//add sign toggle
		JButton buttonSignToggle = new JButton("±");
		buttonSignToggle.addActionListener(new SignToggleListener());
		buttonSignToggle.setBounds(204, 5, 48, 23);
		commonButton.add(buttonSignToggle);
		
		//add delete
		JButton btnDel = new JButton("<-");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField.getText();
				textField.setText(str.substring(0, str.length()-1));
			}
		});
		btnDel.setBounds(78, 5, 48, 23);
		commonButton.add(btnDel);
		
		//add clear all
		JButton btnCe = new JButton("C");
		btnCe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				strCal = new StringCalculator();
			}
		});
		btnCe.setBounds(15, 5, 48, 23);
		commonButton.add(btnCe);
		
		
		//add panel of classic
		classic = new JPanel();
		classic.setBounds(270, 82, 72, 169);
		contentPanel.add(classic);
		classic.setLayout(null);
		classic.setVisible(true);
		
		//add E
		JButton buttonSci = new JButton("E");
		buttonSci.addActionListener(opButtonListner);
		buttonSci.setBounds(10, 72, 48, 23);
		classic.add(buttonSci);
		
		//add left bracket
		JButton buttonLeftBracket = new JButton("(");
		buttonLeftBracket.addActionListener(opButtonListner);
		buttonLeftBracket.setBounds(10, 134, 48, 23);
		classic.add(buttonLeftBracket);
		
		//add right bracket
		JButton buttonRightBracket = new JButton(")");
		buttonRightBracket.addActionListener(opButtonListner);
		buttonRightBracket.setBounds(10, 103, 48, 23);
		classic.add(buttonRightBracket);
		
		//add equal
		JButton buttonEqual = new JButton("=");
		buttonEqual.addActionListener(opButtonListner);
		buttonEqual.setBounds(10, 10, 48, 56);
		classic.add(buttonEqual);
		
		//add panel of RPN
		rpn = new JPanel();
		rpn.setBounds(270, 82, 84, 169);
		contentPanel.add(rpn);
		rpn.setVisible(false);
		
		//add Enter to the RPN panel
		JButton btnEnter = new JButton("ENTER");
		btnEnter.setBounds(7, 86, 73, 73);
		rpn.add(btnEnter);
		
		//add Swap to the RPN panel
		JButton btnSwap = new JButton("SWAP");
		btnSwap.setBounds(7, 10, 73, 66);
		rpn.setLayout(null);
		rpn.add(btnSwap);
		
		
		ButtonGroup group = new ButtonGroup();
		//add radio button classic to the display panel
		JRadioButton classicRadioButton = new JRadioButton("Classic");
		classicRadioButton.setBounds(259, 5, 67, 23);
		displayPanel.add(classicRadioButton);
		classicRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rpn.setVisible(false);
				classic.setVisible(true);
			}
		});
		group.add(classicRadioButton);
		
		//add radio button swap to the display panel
		JRadioButton rpnRadioButton = new JRadioButton("RPN");
		rpnRadioButton.setBounds(259, 33, 67, 23);
		displayPanel.add(rpnRadioButton);
		rpnRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rpn.setVisible(true);
				classic.setVisible(false);
				
			}
		});
		group.add(rpnRadioButton);
	
	}
	


class OperationKey {
	
	public String exec(StringCalculator cal, String oldString, String newString)
			throws ArithmeticException, ExpressionSyntaxError{
		if (!oldString.equals("")) {
			cal.input(oldString);
		}
		return cal.input(newString);
	}
}
	

private boolean isOpKeyPushed;

class NumButtonListenerClass implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		if(isOpKeyPushed){
			textField.setText("");	
			isOpKeyPushed = false;
		}
		
		
		textField.setText(textField.getText()+e.getActionCommand());
	}
}

class OpButtonListenerClass implements ActionListener{
	private StringCalculator strCal;
	
	public OpButtonListenerClass(StringCalculator strCal) {
		super();
		this.strCal = strCal;
	}

	public void actionPerformed(ActionEvent e){
		isOpKeyPushed = true;
		String oldStr = textField.getText();
		JButton btn = (JButton) e.getSource();
		OperationKey opKey = new OperationKey();
		String newStr = opKey.exec(strCal, oldStr, btn.getText());
		if (!newStr.equals("")) {
			textField.setText(newStr);
		}
		
	}
	
}

class SignToggleListener implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		
		String oldStr = textField.getText();
		if(oldStr.charAt(0)=='-'){
			oldStr = oldStr.substring(1);
		}
		else{
			oldStr = "-"+oldStr;
		}
		textField.setText(oldStr);
	}
}

}

