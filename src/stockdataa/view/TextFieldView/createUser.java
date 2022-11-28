package stockdataa.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class createUser extends JFrame implements WithTextField {
  private JTextField userNameTxt;
  private JLabel hint;
  private JLabel userNameLabel;
  private JButton create;
  private JButton main;
  
  private JTextField filePathTxt;
  private JLabel filePatheLabel;
  

  /**
   * Constructor of CreatPortfolio, it initialize the view including label, text fields and
   * buttons.
   * @param caption caption.
   */
  public createUser(String caption) {
    super(caption);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel hintPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    userNameLabel = new JLabel("Enter a unique User Name: ");
    userNameTxt = new JTextField(20);
    
    filePatheLabel = new JLabel("Enter File path: ");
    filePathTxt = new JTextField(25);
    
    create = new JButton("create");
    main = new JButton("home");
    create.setActionCommand("create");
    main.setActionCommand("create portfolio home");
    hint = new JLabel("");
    panel.add(userNameLabel);
    panel.add(userNameTxt);
    
    panel2.add(filePatheLabel);
    panel2.add(filePathTxt);
    
    hintPanel.add(hint);
    buttonPanel.add(create);
    buttonPanel.add(main);
    this.add(panel, BorderLayout.PAGE_START);
    this.add(panel2, BorderLayout.CENTER);
//    this.add(hintPanel, BorderLayout.WEST);
    this.add(buttonPanel, BorderLayout.PAGE_END);
   
    this.setVisible(true);
    this.pack();
  }

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(userNameTxt.getText());
    sb.append("\n");
    sb.append(filePathTxt.getText());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    main.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
//  @Override
//  public String getInput() {
//    return userNameTxt.getText();
//  }

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
	  userNameTxt.setText("");
	  filePathTxt.setText("");
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
