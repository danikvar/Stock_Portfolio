package stockdataa.view.buttonsandtexts;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * This class represents the methods used to check cost basis using GUI.
 */
public class CheckCB extends JFrame implements Texts {

  
  

  private JButton check;
  private JButton home;

  private JLabel output;


  /**
   * This method is used to check cost basis using GUI.
   * @param tag respective tag.
   */

  public CheckCB(String tag) {
    super(tag);

    JPanel buttonPanel = new JPanel();
    check = new JButton("Calculate");
    check.setActionCommand("check cb");
    home = new JButton("Home");
    home.setActionCommand("check cost view home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    output = new JLabel("Please click on the calculate button");
    JPanel hintPanel = new JPanel();
    hintPanel.add(output);

    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(500,500));
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
   * @return the input given b the user.
   */


  @Override
  public String getInput() {
    JTextField portfolioNameText = null;
    return portfolioNameText.getText();
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
   * Clean the fields.
   */


  @Override
  public void clean() {
    //Empty
  }

  /**
   * Set focus.
   */


  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();
  }

}
