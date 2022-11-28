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

/**
 * This class represents the view when users do examine a portfolio at a given date.
 */
public class GetPortfolioDetailByDateView extends JFrame implements WithTextField {

  private JLabel portfolioName;
  private JLabel date;
  private JTextField portfolioNameText;
  private JTextField dateText;
  private JLabel hint;
  private JButton check;
  private JButton home;

  /**
   * Constructor of GetPortfolioDetailByDateView, it initialize the view including label, text
   * fields and buttons.
   * @param caption caption.
   */
  public GetPortfolioDetailByDateView(String caption) {
    super(caption);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel first = new JPanel();
    portfolioName = new JLabel("Portfolio Name ");
    portfolioNameText = new JTextField(20);
    first.add(portfolioName);
    first.add(portfolioNameText);

    JPanel second = new JPanel();
    this.date = new JLabel("Date yyyy-mm-dd");
    dateText = new JTextField(20);
    second.add(this.date);
    second.add(dateText);

    hint = new JLabel();
    JPanel third = new JPanel();
    third.add(hint);

    JPanel buttonPanel = new JPanel();
    check = new JButton("check");
    check.setActionCommand("get portfolio detail by date check");
    home = new JButton("home");
    home.setActionCommand("get portfolio detail by date home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(2,1));
    composition.add(first);
    composition.add(second);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(third, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    check.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput() {
    return portfolioNameText.getText() + "\n" + dateText.getText();
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
    portfolioNameText.setText("");
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
