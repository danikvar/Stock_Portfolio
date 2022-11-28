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


public class BuyStockMoney extends JFrame implements WithTextField {
  private JLabel hint;
  private JLabel stockSymbol;
  private JLabel money;
  private JLabel date;
  private JLabel portfolioName;

  private JButton buy;
  private JButton home;

  private JTextField stockSymbolText;
  private JTextField moneyText;
  private JTextField dateText;
  private JTextField portfolioNameText;


  public BuyStockMoney(String caption) {
    super(caption);
    JPanel stockSymbolPanel = new JPanel();
    stockSymbol = new JLabel("Stock Symbol  ");
    stockSymbolText = new JTextField(20);
    stockSymbolPanel.add(stockSymbol);
    stockSymbolPanel.add(stockSymbolText);

    JPanel volumePanel = new JPanel();
    money = new JLabel("Volume       ");
    moneyText = new JTextField(20);
    volumePanel.add(money);
    volumePanel.add(moneyText);

    JPanel datePanel = new JPanel();
    date = new JLabel("Date yyyy-mm-dd ");
    dateText = new JTextField(20);
    datePanel.add(date);
    datePanel.add(dateText);

    JPanel portfolioPanel = new JPanel();
    portfolioName = new JLabel("Portfolio Name");
    portfolioNameText = new JTextField(20);
    portfolioPanel.add(portfolioName);
    portfolioPanel.add(portfolioNameText);

    JPanel hintPanel = new JPanel();
    hint = new JLabel("");
    hintPanel.add(hint);

    JPanel buttonPanel = new JPanel();
    buy = new JButton("sell");
    buy.setActionCommand("buy stock money buy");
    home = new JButton("home");
    home.setActionCommand("buy stock money home");
    buttonPanel.add(buy);
    buttonPanel.add(home);

    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(4, 1));
    composition.add(stockSymbolPanel);
    composition.add(volumePanel);
    composition.add(datePanel);
    composition.add(portfolioPanel);

    this.add(composition, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }


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
    sb.append(moneyText.getText());
    sb.append("\n");
    sb.append(dateText.getText());
    sb.append("\n");
    sb.append(portfolioNameText.getText());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess(String message) {
    hint.setText(message);
  }

  /**
   * Clear the text field.
   */
  @Override
  public void clearField() {
    stockSymbolText.setText("");
    moneyText.setText("");
    dateText.setText("");
    portfolioNameText.setText("");
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
