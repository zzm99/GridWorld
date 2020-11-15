import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Calculator extends JFrame implements ActionListener {
    // data variable
    private double num1;     // the first number to do compute
    private double num2;     // the second number to do compute
    private int mode = -1;   // get the operation symbol type

    // GUI variable
    private JLabel jlb1, jlb2, jlb3;            // label: operation symbol/ =/ result
    private JTextField jtf1, jtf2;              // textfield: num1 / num2
    private JButton jb1, jb2, jb3, jb4, jb5;    // button: + - * / OK

    public static void main(String[] args) {
        Calculator myCalculator = new Calculator(); // start Calculator
    }

    public Calculator(){
        // set the component:
        // set the component jlb1
        jlb1 = new JLabel(" ");
        jlb1.setHorizontalAlignment(JTextField.CENTER);
        jlb1.setBorder(BorderFactory.createLineBorder(Color.black));
        
        // set the component jlb2
        jlb2 = new JLabel("=");
        jlb2.setHorizontalAlignment(JTextField.CENTER);
        jlb2.setBorder(BorderFactory.createLineBorder(Color.black));
        
        // set the component jlb3
        jlb3 = new JLabel(" ");
        jlb3.setHorizontalAlignment(JTextField.CENTER);
        jlb3.setBorder(BorderFactory.createLineBorder(Color.black));

        // set the component jtf1
        jtf1 = new JTextField(0);
        jtf1.setHorizontalAlignment(JTextField.CENTER);

        // set the component jtf2
        jtf2 = new JTextField(0);
        jtf2.setHorizontalAlignment(JTextField.CENTER);
        
        // set the component jb1
        jb1 = new JButton("+");
        jb1.addActionListener(this);
        
        // set the component jb2
        jb2 = new JButton("-");
        jb2.addActionListener(this);
        
        // set the component jb3
        jb3 = new JButton("*");
        jb3.addActionListener(this);
        
        // set the component jb4
        jb4 = new JButton("/");
        jb4.addActionListener(this);
        
        // set the component jb5
        jb5 = new JButton("OK");
        jb5.addActionListener(this);

        // add the component
        this.add(jtf1);
        this.add(jlb1);
        this.add(jtf2);
        this.add(jlb2);
        this.add(jlb3);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        this.add(jb5);

        // set the window
        this.setLayout(new GridLayout(2,5));
        this.setTitle("Easy Calculator");
        this.setSize(500,200);
        this.setLocation(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // make it visible
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){ // override the actionPerformed
        // set the action of button
        if(e.getActionCommand().equals("+")){
            jlb1.setText("+");
            mode = 0;
        } else if(e.getActionCommand().equals("-")){
            jlb1.setText("-");
            mode = 1;
        } else if(e.getActionCommand().equals("*")){
            jlb1.setText("*");
            mode = 2;
        } else if(e.getActionCommand().equals("/")){
            jlb1.setText("/");
            mode = 3;
        } else if(e.getActionCommand().equals("OK")){
            try {
                num1 = Double.parseDouble(jtf1.getText());
                num2 = Double.parseDouble(jtf2.getText());
                double result = 0.0;
                String s = "";
                if (mode == 0) {
                    result = num1 + num2;
                    s = "" + result;
                }
                else if (mode == 1) {
                    result = num1 - num2;
                    s = "" + result;
                } 
                else if (mode == 2) {
                    result = num1 * num2;
                    s = "" + result;
                }
                else if (mode == 3){
                    // catch the exception like : operation == "/" and num2 == 0
                    if(num2 == 0) {
                        JOptionPane.showMessageDialog(null,"Divisor cannot be zero!", "WARNING", JOptionPane.WARNING_MESSAGE);
                    } else {
                        result = num1 / num2;
                        s = "" + result;
                    }
                }
                else {
                    // mode == -1 : no operation symbol
                    JOptionPane.showMessageDialog(null,"Need a operation symbol!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                jlb3.setText(s);
            } catch (Exception er) {
                // catch other exception unpredictable, like : getText() wrong
                JOptionPane.showMessageDialog(null,"Input invalid!", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            
        }
    }
}