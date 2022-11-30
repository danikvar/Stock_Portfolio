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
 * This represents the class with methods used to sell the stocks through the GUI.
 */
public class SellStock extends JFrame implements Texts {
  private JLabel output;
  private JLabel ticker;
  private JLabel money;
  private JLabel date;
  private JLabel commFee;

  private JButton sell;
  private JButton home;

  private JTextField entryFieldTicker;
  private JTextField moneyText;
  private JTextField dateEntryF;
  private JTextField commFeeText;

  /**
   * This method is used to sell stocks through the GUI.
   * @param tag respective tag for button assignment.
   */
  public SellStock(String tag) {
    super(tag);
    JPanel stockSymbolPanel = new JPanel();
    ticker = new JLabel("Stock Symbol  ");
    entryFieldTicker = new JTextField(20);
    stockSymbolPanel.add(ticker);
    stockSymbolPanel.add(entryFieldTicker);

    JPanel volumePanel = new JPanel();
    money = new JLabel("Share Volume  ");
    moneyText = new JTextField(20);
    volumePanel.add(money);
    volumePanel.add(moneyText);

    JPanel datePanel = new JPanel();
    date = new JLabel("Date yyyy-mm-dd ");
    dateEntryF = new JTextField(20);
    datePanel.add(date);
    datePanel.add(dateEntryF);

    JPanel portfolioPanel = new JPanel();
    commFee = new JLabel("Commission Fee  ");
    commFeeText = new JTextField(20);
    portfolioPanel.add(commFee);
    portfolioPanel.add(commFeeText);

    JPanel hintPanel = new JPanel();
    output = new JLabel("");
    hintPanel.add(output);

    JPanel buttonPanel = new JPanel();
    sell = new JButton("Sell Stocks");
    sell.setActionCommand("buy stock money sell");
    home = new JButton("Home");
    home.setActionCommand("buy stock Main H");
    buttonPanel.add(sell);
    buttonPanel.add(home);

    JPanel guiUnit = new JPanel();
    guiUnit.setLayout(new GridLayout(4, 1));
    guiUnit.add(stockSymbolPanel);
    guiUnit.add(volumePanel);
    guiUnit.add(datePanel);
    guiUnit.add(portfolioPanel);

    this.add(guiUnit, BorderLayout.PAGE_START);
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
    sell.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * This method is used to get input from the user.
   * @return the input given by the user.
   */
  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(entryFieldTicker.getText());
    sb.append("\n");
    sb.append(moneyText.getText());
    sb.append("\n");
    sb.append(dateEntryF.getText());
    sb.append("\n");
    sb.append(commFeeText.getText());
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
  }

  /**
   * Cleans the text fields.
   */
  @Override
  public void clean() {
    entryFieldTicker.setText("");
    moneyText.setText("");
    dateEntryF.setText("");
    commFeeText.setText("");
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
