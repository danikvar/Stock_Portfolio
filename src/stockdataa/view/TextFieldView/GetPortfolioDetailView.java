package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The class represents the view when users do examine portfolio operation.
 */
public class GetPortfolioDetailView extends JFrame implements WithTextField {
  private JLabel label;
  private JLabel hint;
  private JTextField text;
  private JButton check;
  private JButton home;

  /**
   * Constructor of GetPortfolioDetailView, it initialize the view including label, text
   * fields and buttons.
   * @param caption caption.
   */
  public GetPortfolioDetailView(String caption) {

    super(caption);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    hint = new JLabel("");

    label = new JLabel("Portfolio Name: ");
    text = new JTextField(20);
    panel.add(label);
    panel.add(text);

    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);

    check = new JButton("check");
    check.setActionCommand("get portfolio detail check");
    home = new JButton("home");
    home.setActionCommand("get portfolio detail home");
    buttonPanel.add(check);
    buttonPanel.add(home);
    this.add(panel, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
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
    return text.getText();
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
    text.setText("");
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
