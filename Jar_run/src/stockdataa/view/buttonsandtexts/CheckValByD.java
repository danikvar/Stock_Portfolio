package stockdataa.view.buttonsandtexts;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;



/**
 * This represents the class with methods for creating the check value by date sceen in GUI.
 */
public class CheckValByD extends JFrame implements Texts {


  private JTextField dateEntryF;

  private JButton check;
  private JButton home;

  private JTextArea output;

  /**
   * This represents the method to calculate the total value of portfolio by date using GUI.
   * @param tag respective tag.
   */
  public CheckValByD(String tag) {
    super(tag);

    JPanel datePanel = new JPanel();
    JLabel dateEntry = new JLabel("Date: yyyy-mm-dd");
    dateEntryF = new JTextField(20);
    datePanel.add(dateEntry);
    datePanel.add(dateEntryF);

    JPanel buttonPanel = new JPanel();
    check = new JButton("Calculate");
    check.setActionCommand("check val");
    home = new JButton("Home");
    home.setActionCommand("check value by date view home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    output = new JTextArea("");
    Font f = new Font("Courier New", Font.BOLD, 20);
    output.setFont(f);
    JPanel hintPanel = new JPanel();
    hintPanel.add(output);

    JPanel composite = new JPanel();
    composite.setLayout(new GridLayout(2,1));
    composite.add(datePanel);

    this.add(composite, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(500, 500));
    this.setVisible(true);
    this.pack();
  }

  /**
   * This method is to add action listener.
   * @param listener is the object of ActionListener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    check.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * This method is to get input from the user.
   * @return the input given by the user.
   */
  @Override
  public String getInput() {
    String str = dateEntryF.getText();
    return str;
  }

  /**
   * This method is to print a message to the user.
   * @param outToUser is the string that is to be printed.
   */
  @Override
  public void outMess(String outToUser) {

    output.setRows((int) outToUser.lines().count());
    output.setText(outToUser);
  }

  /**
   * Cleans the text field.
   */
  @Override
  public void clean() {
    dateEntryF.setText("");
  }

  /**
   * Focus set.
   */
  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
