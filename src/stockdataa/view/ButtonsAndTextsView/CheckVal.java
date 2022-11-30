package stockdataa.view.ButtonsAndTextsView;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;


public class CheckVal extends JFrame implements Texts {

  private JButton check;
  private JButton home;

  private JTextArea output;

  private JLabel output1;


  public CheckVal(String tag) {
    super(tag);

    JPanel portfolioPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    check = new JButton("Calculate");
    check.setActionCommand("check valBD");
    home = new JButton("Home");
    home.setActionCommand("check value view home");
    buttonPanel.add(check);
    buttonPanel.add(home);


    output1 = new JLabel("Please click on the calculate button");




    output = new JTextArea("");
    Font f = new Font("Courier New", Font.BOLD, 20);
    output.setFont(f);
    JPanel hintPanel = new JPanel();
    hintPanel.add(output);
    hintPanel.add(output1);

    this.add(portfolioPanel, BorderLayout.PAGE_START);
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
    return "";
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
   * Clean the text field.
   */
  @Override
  public void clean() {
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
