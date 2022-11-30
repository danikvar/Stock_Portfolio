package stockdataa.view.ButtonsAndTextsView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CheckCBD extends JFrame implements Texts {

  private JLabel dateEntry;
  private JTextField dateEntryF;

  private JButton check;
  private JButton home;

  private JLabel output;

  /**
   * This method is used to check cost basis of the portfolio using GUI.
   * @param tag respective tag.
   */
  public CheckCBD(String tag) {
    super(tag);

    JPanel datePanel = new JPanel();
    dateEntry = new JLabel("Date: yyyy-mm-dd");
    dateEntryF = new JTextField(20);
    datePanel.add(dateEntry);
    datePanel.add(dateEntryF);

    JPanel buttonPanel = new JPanel();
    check = new JButton("Calculate");
    check.setActionCommand("check cbd");
    home = new JButton("Home");
    home.setActionCommand("check cost by date view home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    output = new JLabel("");
    JPanel hintPanel = new JPanel();
    hintPanel.add(output);

    JPanel composite = new JPanel();
    composite.setLayout(new GridLayout(3,2));
    composite.add(datePanel);
    composite.add(hintPanel);

    this.add(composite, BorderLayout.PAGE_START);
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
   * This method is used to get input from the user.
   * @return the input given by the user.
   */
  @Override
  public String getInput() {

    return dateEntryF.getText();
  }

  /**
   * This method is to print a message to the user.
   * @param outToUser is the string that is to be printed.
   */

  @Override
  public void outMess(String outToUser) {

    output.setText(outToUser);
  }

  /**
   * Cleans the fields.
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
