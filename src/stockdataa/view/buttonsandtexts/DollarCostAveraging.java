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
 * This represents the class with methods to implement Dollar cost averaging strategy through GUI.
 */
public class DollarCostAveraging extends JFrame implements Texts {

  private JTextField daysInvF;
  private JTextField sDateF;
  private JTextField eDateF;
  private JTextField propMapF;
  private JTextField amountF;
  private JTextField commFeeF;
  private JButton home;
  private JButton invest;
  private JLabel output;

  /**
   * This is the method to implement the GUI of Dollar cost averaging.
   * @param tag is the respective tag for button assignment.
   */

  public DollarCostAveraging(String tag) {
    super(tag);
    JLabel daysInvest = new JLabel("Enter the number of days of Investment");
    daysInvF = new JTextField(20);
    JLabel sDate = new JLabel("Enter the start date");
    sDateF = new JTextField(20);
    JLabel eDate = new JLabel("Enter the end date");
    eDateF = new JTextField(20);
    JLabel propMap = new JLabel("(Ticker, Proportion)");
    propMapF = new JTextField(20);
    JLabel commFee = new JLabel("Enter the commission Fee");
    commFeeF = new JTextField(20);
    JLabel amount = new JLabel("Enter the investment amount");
    amountF = new JTextField(20);
    invest = new JButton("Invest");
    invest.setActionCommand("inv proceed");
    home = new JButton("Home");
    home.setActionCommand("dc home");
    output = new JLabel("");
    JLabel output4 = new JLabel("If you would like to perform DCA only once"
            + " on the start date, set the number of days to 0.");
    JLabel output2 = new JLabel("You may leave the end date blank for endless strategy.");
    JLabel output3 = new JLabel("You may add a past start date but not more than 1 full time"
            + "frame in the past.");
    JLabel output5 = new JLabel("Your proportions/weights must be entered as:"
            + " (stock_ticker1, weight1);(ticker2, weight2),..."
            + "where tickers must be present in your portfolio.");
    JLabel output6 = new JLabel("The stock weights entered must be between [0,1] and"
            + " must sum in total to 1.0. Any tickers missing from the mapping input string"
            + " will be assigned a proportional weight of 0.");
    JPanel hintPanel = new JPanel();
    hintPanel.setLayout(new GridLayout(6, 1));
    hintPanel.add(output);
    hintPanel.add(output4);
    hintPanel.add(output2);
    hintPanel.add(output3);
    hintPanel.add(output5);
    hintPanel.add(output6);

    JPanel upper = new JPanel();
    JPanel upper1 = new JPanel();
    JPanel upper2 = new JPanel();
    JPanel upper3 = new JPanel();
    JPanel upper4 = new JPanel();
    JPanel upper5 = new JPanel();

    JPanel buttonPanel = new JPanel();

    upper.add(daysInvest);
    upper.add(daysInvF);
    upper1.add(sDate);
    upper1.add(sDateF);
    upper2.add(eDate);
    upper2.add(eDateF);
    upper3.add(propMap);
    upper3.add(propMapF);
    upper4.add(commFee);
    upper4.add(commFeeF);
    upper5.add(amount);
    upper5.add(amountF);

    JPanel up = new JPanel();
    up.setLayout(new GridLayout(6,1));
    up.add(upper);
    up.add(upper1);
    up.add(upper2);
    up.add(upper3);
    up.add(upper4);
    up.add(upper5);

    buttonPanel.add(invest);
    buttonPanel.add(home);

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
    invest.addActionListener(listener);
    home.addActionListener(listener);

  }

  /**
   * This method is used to get input from the user.
   *
   * @return the input given by the user.
   */

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(daysInvF.getText());
    sb.append("\n");
    sb.append(sDateF.getText());
    sb.append("\n");
    sb.append(eDateF.getText());
    sb.append("\n");
    sb.append(propMapF.getText());
    sb.append("\n");
    sb.append(amountF.getText());
    sb.append("\n");
    sb.append(commFeeF.getText());
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
    daysInvF.setText("");
    amountF.setText("");
    commFeeF.setText("");
    sDateF.setText("");
    eDateF.setText("");
    propMapF.setText("");

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
