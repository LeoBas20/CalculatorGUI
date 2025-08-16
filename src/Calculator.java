import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.util.Arrays;

public class Calculator {

    JFrame frame = new JFrame("Calculator");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel buttonsPanel = new JPanel();


    Color old_Silver = new Color(128, 134, 139);
    Color arsenic = new Color(60, 64, 67);
    Color lightSteelBlue = new Color(176, 196, 222);
    Color black = new Color(28, 28, 28);

    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "Del", "="
    };

    String[] topSymbols = {"AC", "+/-", "%"};
    String[] rightSymbols = {"÷", "*", "-", "+"};
    String[] equalSymbol = {"="};

    String a = "0";
    String operator = null;
    String b = null;

    Calculator() {
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //Center popup
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());


        label.setBackground(black);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.PLAIN, 80));
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setText("0");
        label.setOpaque(true);

        panel.setLayout(new BorderLayout());
        panel.add(label);
        panel.setBorder(BorderFactory.createLineBorder(arsenic, 4));
        frame.add(panel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonsPanel.setBackground(black);
        buttonsPanel.setPreferredSize(new Dimension(370, 450));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setBackground(black);
        wrapper.add(buttonsPanel);
        frame.add(wrapper, BorderLayout.SOUTH);

        for (String value : buttonValues) {
            JButton button = getJButton(value);
            buttonsPanel.add(button);

            button.addActionListener(e -> {
                JButton button1 = (JButton) e.getSource();

                String buttonValue = button1.getText();

                if (Arrays.asList(topSymbols).contains(buttonValue)) {
                    switch (buttonValue) {
                        case "AC" -> {
                            clearAll();
                            label.setText("0");
                        }
                        case "+/-" -> {

                            double num = Double.parseDouble(label.getText());
                            num *= -1;
                            label.setText(zeroDecimal(num));

                        }
                        case "%" -> {

                            double num = Double.parseDouble(label.getText());
                            num /= 100;
                            label.setText(zeroDecimal(num));

                        }
                    }
                } else if (buttonValue.equals("Del")) {
                    String current = label.getText();
                    if (current.length() > 1) {
                        label.setText(current.substring(0, current.length() - 1));
                    } else {
                        label.setText("0");
                    }
                } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {

                    // handle + - * ÷
                    if (operator == null) {
                        a = label.getText();
                        label.setText("0");
                    }
                    operator = buttonValue;


                } else if (Arrays.asList(equalSymbol).contains(buttonValue)) {

                    if (a != null && operator != null) {
                        b = label.getText();
                        double numA = Double.parseDouble(a);
                        double numB = Double.parseDouble(b);

                        switch (operator) {
                            case "+":
                                label.setText(zeroDecimal(numA + numB));
                                break;
                            case "-":
                                label.setText(zeroDecimal(numA - numB));
                                break;
                            case "*":
                                label.setText(zeroDecimal(numA * numB));
                                break;
                            case "÷":
                                if (numB != 0)
                                    label.setText(zeroDecimal(numA / numB));
                                else
                                    label.setText("Error");
                                break;
                        }
                        clearAll();
                    }

                } else { //0-9 Number Buttons
                    if (buttonValue.equals(".")) {
                        if (!label.getText().contains(buttonValue)) {
                            label.setText(label.getText() + buttonValue);
                        }

                    } else if ("0123456789".contains(buttonValue)) {
                        if (label.getText().equals("0") || label.getText().equals("Error")) {
                            label.setText(buttonValue);
                        } else {
                            label.setText(label.getText() + buttonValue);
                        }
                    }
                }
            });
        }
        frame.setVisible(true);
    }

    JButton getJButton(String value) {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setText(value);
        button.setFocusable(false);
        button.setBorder(new LineBorder(black));

        if (Arrays.asList(topSymbols).contains(value)) {
            button.setBackground(old_Silver);
            button.setForeground(black);
        } else if (Arrays.asList(rightSymbols).contains(value)) {
            button.setBackground(old_Silver);
            button.setForeground(black);
        } else if (Arrays.asList(equalSymbol).contains(value)) {
            button.setBackground(lightSteelBlue);
            button.setForeground(black);
        } else { //0-9 Number Buttons
            button.setBackground(arsenic);
            button.setForeground(Color.white);
        }
        return button;
    }

    void clearAll() {
        a = "0";
        operator = null;
        b = null;
    }

    String zeroDecimal(double num) {
        if (num % 1 == 0)
            return Integer.toString((int) num);
        else
            return Double.toString(num);
    }
}
