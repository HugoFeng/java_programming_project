package javaProject;

import rpnCalculator.RPNCalculator;
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

import java.util.LinkedList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

/**
 * @author Huiyu Yang
 * @version 1.0
 * Calculator is a class using different algorithms and 
 * achieving expression of a calculator.
 *
 */

@SuppressWarnings("serial")
public class Calculator extends JFrame {

	private JPanel contentPanel;
	private JPanel rpn;
	private JPanel classic;
	private JTextField textField;
	private StringCalculator strCal;
	private RPNCalculator rpnCal;
	
	private LinkedList<JButton> numberButtonList;
	private LinkedList<JButton> operatorButtonList;
	private LinkedList<JButton> rpnButtonList;
	
	private AddtextListenerClass addTextListner;
	private ClassicOpButtonListenerClass opButtonListner;
	private RpnListenerClass rpnButtonListner;
	
	public Calculator() {
		
		numberButtonList = new LinkedList<JButton>();
		operatorButtonList = new LinkedList<JButton>();
		rpnButtonList = new LinkedList<JButton>();
		
		
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
		displayPanel.setBounds(10, 10, 344, 73);
		contentPanel.add(displayPanel);
		displayPanel.setLayout(null);
		
		//add text field to the display panel
		textField = new JTextField();
		textField.setText("0");
		textField.setBounds(10, 6, 243, 57);
		displayPanel.add(textField);
		textField.setColumns(10);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		this.strCal = new StringCalculator();
		this.isOpKeyPushed = false;
			
		this.rpnCal = new RPNCalculator();	
		
			
		//add common panel to the content panel	
		JPanel commonButton = new JPanel();
		commonButton.setBounds(10, 82, 257, 169);
		contentPanel.add(commonButton);
		commonButton.setLayout(null);
		
		
		//new button listener
		addTextListner = new AddtextListenerClass();
		opButtonListner = new ClassicOpButtonListenerClass(strCal);
		rpnButtonListner = new RpnListenerClass();
		
		
		
		//add num1
		JButton button1 = new JButton("1");
		button1.setBounds(15, 38, 48, 23);
		commonButton.add(button1);
		this.numberButtonList.add(button1);
		
		//add num2
		JButton button2 = new JButton("2");
		this.numberButtonList.add(button2);
		button2.setBounds(78, 38, 48, 23);
		commonButton.add(button2);
		
		//add num3
		JButton button3 = new JButton("3");
		this.numberButtonList.add(button3);
		button3.setBounds(141, 38, 48, 23);
		commonButton.add(button3);
		
		//add num4
		JButton button4 = new JButton("4");
		this.numberButtonList.add(button4);
		button4.setBounds(15, 71, 48, 23);
		commonButton.add(button4);
		
		//add num5
		JButton button5 = new JButton("5");
		this.numberButtonList.add(button5);
		button5.setBounds(78, 71, 48, 23);
		commonButton.add(button5);
		
		//add num6
		JButton button6 = new JButton("6");
		this.numberButtonList.add(button6);
		button6.setBounds(141, 71, 48, 23);
		commonButton.add(button6);
		
		//add num7
		JButton button7 = new JButton("7");
		this.numberButtonList.add(button7);
		button7.setBounds(15, 104, 48, 23);
		commonButton.add(button7);
		
		//add num8
		JButton button8 = new JButton("8");
		this.numberButtonList.add(button8);
		button8.setBounds(78, 104, 48, 23);
		commonButton.add(button8);
		
		//add num9
		JButton button9 = new JButton("9");
		this.numberButtonList.add(button9);
		button9.setBounds(141, 104, 48, 23);
		commonButton.add(button9);
		
		
		//add num0
		JButton button0 = new JButton("0");
		this.numberButtonList.add(button0);
		button0.setBounds(15, 137, 111, 23);
		commonButton.add(button0);
		
		
		
		//add decimal
		JButton buttonDecimal = new JButton(".");
		this.numberButtonList.add(buttonDecimal);
		buttonDecimal.setBounds(141, 137, 48, 23);
		commonButton.add(buttonDecimal);

		

		//add plus
		JButton buttonPlus = new JButton("+");
		buttonPlus.setBounds(204, 38, 48, 23);
		commonButton.add(buttonPlus);
		operatorButtonList.add(buttonPlus);
		rpnButtonList.add(buttonPlus);
		
		//add minus
		JButton buttonMinus = new JButton("-");
		buttonMinus.setBounds(204, 71, 48, 23);
		commonButton.add(buttonMinus);
		operatorButtonList.add(buttonMinus);
		rpnButtonList.add(buttonMinus);
		
		//add multiply
		JButton buttonMultiply = new JButton("*");
		buttonMultiply.setBounds(204, 104, 48, 23);
		commonButton.add(buttonMultiply);
		operatorButtonList.add(buttonMultiply);
		rpnButtonList.add(buttonMultiply);
		
		//add divide
		JButton buttonDivide = new JButton("/");
		buttonDivide.setBounds(204, 137, 48, 23);
		commonButton.add(buttonDivide);
		operatorButtonList.add(buttonDivide);
		rpnButtonList.add(buttonDivide);
		
		//add power
		JButton buttonPower = new JButton("^");
		buttonPower.setBounds(141, 5, 48, 23);
		commonButton.add(buttonPower);
		operatorButtonList.add(buttonPower);
		rpnButtonList.add(buttonPower);
		
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
				textField.setText("0");
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
		buttonSci.setBounds(10, 72, 48, 23);
		classic.add(buttonSci);
		operatorButtonList.add(buttonSci);
		
		//add left bracket
		JButton buttonLeftBracket = new JButton("(");
		buttonLeftBracket.setBounds(10, 134, 48, 23);
		classic.add(buttonLeftBracket);
		operatorButtonList.add(buttonLeftBracket);
		
		//add right bracket
		JButton buttonRightBracket = new JButton(")");
		buttonRightBracket.setBounds(10, 103, 48, 23);
		classic.add(buttonRightBracket);
		operatorButtonList.add(buttonRightBracket);
		
		//add equal
		JButton buttonEqual = new JButton("=");
		buttonEqual.setBounds(10, 10, 48, 56);
		classic.add(buttonEqual);
		operatorButtonList.add(buttonEqual);
		
		//add panel of RPN
		rpn = new JPanel();
		rpn.setBounds(270, 82, 84, 169);
		contentPanel.add(rpn);
		rpn.setVisible(false);
		
		//add Enter to the RPN panel
		JButton btnEnter = new JButton("ENTER");
		btnEnter.setBounds(7, 86, 73, 73);
		rpn.add(btnEnter);
		btnEnter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					rpnCal.getRes(textField.getText());
					textField.setText("0");
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, exception.getMessage());
					textField.setText("0");
				}
			}
		});
		
		
		
		//add Swap to the RPN panel
		JButton btnSwap = new JButton("SWAP");
		btnSwap.setBounds(7, 10, 73, 66);
		rpn.setLayout(null);
		rpn.add(btnSwap);
		btnSwap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					textField.setText(rpnCal.swap(textField.getText()));
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, exception.getMessage());
					textField.setText("0");
				}

			}
		});
		
		
		
		
		ButtonGroup group = new ButtonGroup();
		//add radio button classic to the display panel
		JRadioButton classicRadioButton = new JRadioButton("Classic");
		classicRadioButton.setSelected(true);
		classicRadioButton.setBounds(259, 3, 79, 20);
		displayPanel.add(classicRadioButton);
		classicRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rpn.setVisible(false);
				classic.setVisible(true);
				setClassicAlgoEnvironment();
			}
		});
		group.add(classicRadioButton);
		
		//add radio button RPN to the display panel
		JRadioButton rpnRadioButton = new JRadioButton("RPN");
		rpnRadioButton.setBounds(259, 49, 79, 20);
		displayPanel.add(rpnRadioButton);
		rpnRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rpn.setVisible(true);
				classic.setVisible(false);
				setRpnEnvironment();
			}
		});
		group.add(rpnRadioButton);
		
		
		JRadioButton advancedRadioButton = new JRadioButton("Advanced");
		advancedRadioButton.setBounds(259, 26, 83, 20);
		displayPanel.add(advancedRadioButton);
		advancedRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rpn.setVisible(false);
				classic.setVisible(true);
				setAdvancedAlgoEnvironment();
			}
		});
		group.add(advancedRadioButton);
	}
	
	private void clearListeners(JButton button) {
		for(ActionListener al:button.getActionListeners())
				button.removeActionListener(al);
	}
	
	private void setClassicAlgoEnvironment(){
		for (JButton button : operatorButtonList) {
			clearListeners(button);
			button.addActionListener(opButtonListner);
		}
		for (JButton button : numberButtonList) {
			clearListeners(button);
			button.addActionListener(addTextListner);
		}
	}
	
	private void setAdvancedAlgoEnvironment(){
		for (JButton button : operatorButtonList) {
			clearListeners(button);
			if(!button.getText().equals("="))
				
				button.addActionListener(addTextListner);
			else
				button.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent event) {
						String expression = textField.getText()+"=";
						try {
							String result = StringCalculator.getResultFromExpression(expression);
							textField.setText(result);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
							textField.setText("0");
							
						}
					}
				});
		}
		
		for (JButton button : numberButtonList) {
			clearListeners(button);
			button.addActionListener(addTextListner);
		}
		
		
	}
	
	private void setRpnEnvironment(){
		for (JButton button : numberButtonList) {
			clearListeners(button);
			button.addActionListener(addTextListner);
		}
		
		for (JButton button : rpnButtonList){
			clearListeners(button);
			button.addActionListener(rpnButtonListner);
		}
		
		
	}
	
class RpnListenerClass implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		String oldStr = e.getActionCommand();
		try {
			textField.setText(rpnCal.getRes(oldStr));
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
			textField.setText("0");
		}
	}
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
class AddtextListenerClass implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		if(isOpKeyPushed){
			textField.setText("");	
			isOpKeyPushed = false;
		}
		
		if(textField.getText().equals("0") && !e.getActionCommand().equals("."))
			textField.setText(e.getActionCommand());
		else
			textField.setText(textField.getText()+e.getActionCommand());
		
	}
}

class ClassicOpButtonListenerClass implements ActionListener{
	private StringCalculator strCal;
	
	public ClassicOpButtonListenerClass(StringCalculator strCal) {
		super();
		this.strCal = strCal;
	}

	public void actionPerformed(ActionEvent e){
		isOpKeyPushed = true;
		String oldStr = textField.getText();
		JButton btn = (JButton) e.getSource();
		OperationKey opKey = new OperationKey();
		
		try{
			String newStr = opKey.exec(strCal, oldStr, btn.getText());
			if (!newStr.equals("")) {
				textField.setText(newStr);
			}
		}
		
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
			textField.setText("0");
			strCal = new StringCalculator();
		}
	}
	
}

class SignToggleListener implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		
		String oldStr = textField.getText();
		
		if(!oldStr.equals("0")){
			if(oldStr.charAt(0)=='-'){
				oldStr = oldStr.substring(1);
			}
			else{
				oldStr = "-"+oldStr;
			}
		}
		textField.setText(oldStr);
	}
}

}
