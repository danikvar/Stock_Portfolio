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


public class SavePortfolio extends JFrame implements WithTextField {
  private JLabel portfolioName;
  private JLabel fileName;
  private JTextField fileNameText;
  private JButton save;
  private JButton home;
  private JLabel hint;

  private String path;

  /**
   * Constructor of SavePortfolio, it initializes the SavePortfolio class.
   * @param caption caption.
   */
  public SavePortfolio(String caption, String path) {
    super(caption);
    this.path = path;
    portfolioName = new JLabel("Current Path: " + this.path);
    fileName = new JLabel("New Path: ");
    fileNameText = new JTextField(20);
    JPanel portfolioPanel = new JPanel();
    portfolioPanel.add(portfolioName);
    JPanel filePanel = new JPanel();
    filePanel.add(fileName);
    filePanel.add(fileNameText);
    save = new JButton("Save");
    home = new JButton("Home");
    save.setActionCommand("save portfolio save");
    home.setActionCommand("save portfolio home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(save);
    buttonPanel.add(home);
    hint = new JLabel();
    hint.setText("If you want to specify a new file path, add it in the box. eg: C:/filename");
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(3,1));
    composition.add(portfolioPanel);
    composition.add(filePanel);
    composition.add(hintPanel);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    save.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput () {
    return fileNameText.getText();
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess (String message) {
    hint.setText(message);
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  public void setPath (String message) {

    this.path = message;
    portfolioName.setText("Current Path: " + message);
  }

  /**
   * Clear all fields.
   */
  @Override
  public void clearField () {
    fileNameText.setText("");
  }

  /**
   * Reset focus.
   */
  @Override
  public void resetFocus () {
    this.setFocusable(true);
    this.requestFocus();
  }
}
