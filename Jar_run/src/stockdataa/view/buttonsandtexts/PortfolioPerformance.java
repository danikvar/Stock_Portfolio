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

/**
 * This represents the class with methods required to implement the portfolio performance GUI.
 */

public class PortfolioPerformance extends JFrame implements Texts {

  private JTextField sDateF;
  private JTextField eDateF;
  private JButton home;
  private JButton plot;
  private JLabel output;

  /**
   * This is the method used to create the portfolio performance screen in GUI.
   *
   * @param tag is the respective ta for button assignment.
   */
  public PortfolioPerformance(String tag) {
    super(tag);
    JLabel sDate = new JLabel("Enter the start date");
    sDateF = new JTextField(20);
    JLabel eDate = new JLabel("Enter the end date");
    eDateF = new JTextField(20);
    plot = new JButton("Plot Graph");
    plot.setActionCommand("plotting");
    home = new JButton("Home");
    home.setActionCommand("pp home");
    output = new JLabel("");

    JPanel upper = new JPanel();
    JPanel upper1 = new JPanel();


    JPanel buttonPanel = new JPanel();


    upper.add(sDate);
    upper.add(sDateF);
    upper1.add(eDate);
    upper1.add(eDateF);

    buttonPanel.add(plot);
    buttonPanel.add(home);

    JPanel hintPanel = new JPanel();

    hintPanel.add(output);

    JPanel up = new JPanel();
    up.setLayout(new GridLayout(3,1));
    up.add(upper);
    up.add(upper1);

    this.add(up, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * This method is to add action listener.
   *
   * @param listener is the object of ActionListener.
   */


  @Override
  public void addActionListener(ActionListener listener) {
    home.addActionListener(listener);
    plot.addActionListener(listener);

  }

  /**
   * This method is used to get input from the user.
   *
   * @return the input given by the user.
   */

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();

    sb.append(sDateF.getText());
    sb.append("\n");
    sb.append(eDateF.getText());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * This method is to print a message to the user.
   *
   * @param outToUser is the string that is to be printed.
   */

  @Override
  public void outMess(String outToUser) {
    output.setText(outToUser);

  }

  /**
   * Cleans the text fields.
   */

  @Override
  public void clean() {
    sDateF.setText("");
    eDateF.setText("");

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
