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

/**
 * This class represents the methods used to buy stocks using GUI.
 */


public class BuyStock extends JFrame implements Texts {

  private JLabel output;
  private JLabel hint2;
  private JLabel ticker;
  private JLabel date;
  private JButton buy;
  private JButton home;

  private JTextField entryFieldTicker;
  private String pName;
  private JTextField dateEntryF;

  /**
   * This is the method to buy stocks using the GUI.
   * @param tag respective tag.
   */
  public BuyStock(String tag) {
    super(tag);
    JPanel stockSymbolPanel = new JPanel();
    ticker = new JLabel("Enter the Stock Symbol\n");
    entryFieldTicker = new JTextField(20);
    stockSymbolPanel.add(ticker);
    stockSymbolPanel.add(entryFieldTicker);


    pName = "";
    JPanel datePanel = new JPanel();
    date = new JLabel("Transaction String\n");
    dateEntryF = new JTextField(20);
    datePanel.add(date);
    datePanel.add(dateEntryF);

   

    //TODO: Finish updating the buy
    JPanel hintPanel = new JPanel();
    output = new JLabel("Enter the date, number of stock bought, and commission " +
            "fee for each transaction in the " +
            "(YYYY-MM-DD, x.x, y.y);(...);... format.");
    hint2 = new JLabel("Where x.x is the # of shares bought," +
            " and y.y is the commission fee paid on the transaction. For multiple" +
            " transactions make sure each is followed by a semicolon. " +
            "Keep in mind that buying fractional shares is not allowed.");
    hintPanel.setLayout(new GridLayout(3, 1));
    hintPanel.add(output);
    hintPanel.add(hint2);

    JPanel buttonPanel = new JPanel();
    buy = new JButton("Buy Stocks");
    buy.setActionCommand("buy");
    home = new JButton("Home");
    home.setActionCommand("buy stock home");
    buttonPanel.add(buy);
    buttonPanel.add(home);

    JPanel guiUnit = new JPanel();
    guiUnit.setLayout(new GridLayout(4, 1));
    guiUnit.add(stockSymbolPanel);
    guiUnit.add(datePanel);
    guiUnit.add(hintPanel);

    this.add(guiUnit, BorderLayout.PAGE_START);
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
    buy.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * This method is to get input from the user.
   * @return the input given by the user.
   */

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(entryFieldTicker.getText());
    sb.append("\n");
    sb.append(dateEntryF.getText());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * This method is to print a message to the user.
   * @param outToUser is the string that is to be printed.
   */

  @Override
  public void outMess(String outToUser) {
    output.setText(outToUser);
    hint2.setText("");
  }

  /**
   * Cleans the fields.
   */

  @Override
  public void clean() {
    entryFieldTicker.setText("");
    dateEntryF.setText("");
  }

  /**
   * Focuses on the screen.
   */

  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();
  }

}
