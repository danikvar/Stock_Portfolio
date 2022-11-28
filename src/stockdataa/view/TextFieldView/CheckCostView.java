package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CheckCostView extends JFrame implements WithTextField {

  private JLabel portfolioNameLabel;
  private JTextField portfolioNameText;

  private JButton check;
  private JButton home;

  private JLabel hint;

  /**
   * Constructor of CheckCostView, it initialize the view including label, text fields and buttons.
   * @param caption caption.
   */
  public CheckCostView(String caption) {
    super(caption);

    JPanel buttonPanel = new JPanel();
    check = new JButton("check");
    check.setActionCommand("check cost view check");
    home = new JButton("home");
    home.setActionCommand("check cost view home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    hint = new JLabel("");
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);

    this.add(hintPanel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
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
    return portfolioNameText.getText();
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
    //nothing to clear
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
