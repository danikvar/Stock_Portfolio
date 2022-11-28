package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BuyStockView extends JFrame implements WithTextField {

  private JLabel hint;
  private JLabel hint2;
  private JLabel stockSymbol;
  private JLabel date;
  private JButton buy;
  private JButton home;

  private JTextField stockSymbolText;
  private String pName;
  private JTextField dateText;

  /**
   * Constructor of BuyStockView, it initialize the view including label, text fields and buttons.
   * @param caption caption.
   */
  public BuyStockView(String caption) {
    super(caption);
    JPanel stockSymbolPanel = new JPanel();
    stockSymbol = new JLabel("Stock Symbol  ");
    stockSymbolText = new JTextField(20);
    stockSymbolPanel.add(stockSymbol);
    stockSymbolPanel.add(stockSymbolText);


    pName = "";
    JPanel datePanel = new JPanel();
    date = new JLabel("Transaction String");
    dateText = new JTextField(20);
    datePanel.add(date);
    datePanel.add(dateText);

    //JPanel portfolioPanel = new JPanel();
    //portfolioName = new JLabel("Portfolio Name");
    //portfolioNameText = new JTextField(20);
    //portfolioPanel.add(portfolioName);
    //portfolioPanel.add(portfolioNameText);

    //TODO: Finish updating the buy
    JPanel hintPanel = new JPanel();
    hint = new JLabel("Enter the date, number of stock bought, and commission " +
            "fee for each transaction in the " +
            "(YYYY-MM-DD, x.x, y.y);(...);... format.");
    hint2 = new JLabel("Where x.x is the # of shares bought," +
            " and y.y is the commission fee paid on the transaction. For multiple" +
            " transactions make sure each is followed by a semicolon. " +
            "Keep in mind that buying fractional shares is not allowed.");
    hintPanel.setLayout(new GridLayout(3, 1));
    hintPanel.add(hint);
    hintPanel.add(hint2);

    JPanel buttonPanel = new JPanel();
    buy = new JButton("buy");
    buy.setActionCommand("buy");
    home = new JButton("home");
    home.setActionCommand("buy stock home");
    buttonPanel.add(buy);
    buttonPanel.add(home);

    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(4, 1));
    composition.add(stockSymbolPanel);
    //composition.add(volumePanel);
    composition.add(datePanel);
    composition.add(hintPanel);

    this.add(composition, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add a provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    buy.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(stockSymbolText.getText());
    sb.append("\n");
    sb.append(dateText.getText());
    sb.append("\n");
    //Don't Need the name
    //sb.append(portfolioNameText.getText());
    //sb.append("\n");
    return sb.toString();
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess(String message) {
    hint.setText(message);
    hint2.setText("");
  }

  /**
   * Clear the text field.
   */
  @Override
  public void clearField() {
    stockSymbolText.setText("");
    dateText.setText("");
  }

  /**
   * Reset focus.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

}
