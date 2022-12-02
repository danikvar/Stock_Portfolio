package stockdataa.view.buttonsandtexts;


import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;



/**
 * This class represents the methods used to create a user using GUI.
 */
public class CreateUser extends JFrame implements Texts {
  private JTextField userNameEntry;
  private JLabel output;
  private JButton create;
  private JButton main;

  private JTextField filePathTxt;


  /**
   * This method is used to create a user using GUI.
   *
   * @param tag respective tag to assign button.
   */
  public CreateUser(String tag) {
    super(tag);
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel hintPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel userName = new JLabel("Enter a unique User Name: ");
    userNameEntry = new JTextField(20);

    JLabel filePatheLabel = new JLabel("Enter File path: ");
    filePathTxt = new JTextField(25);

    create = new JButton("Create User");
    main = new JButton("Home");
    create.setActionCommand("create");
    main.setActionCommand("create portfolio home");
    output = new JLabel("");
    panel.add(userName);
    panel.add(userNameEntry);

    panel2.add(filePatheLabel);
    panel2.add(filePathTxt);
    hintPanel.add(output);

    JPanel com = new JPanel();
    com.setLayout(new GridLayout(2, 1));
    com.add(panel);
    com.add(panel2);
    buttonPanel.add(create);
    buttonPanel.add(main);
    this.add(com, BorderLayout.CENTER);
    this.add(hintPanel, BorderLayout.PAGE_START);

    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setVisible(true);
    this.pack();
  }

  /**
   * This method is used to get input from the user.
   *
   * @return the input given by the user.
   */

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(userNameEntry.getText());
    sb.append("\n");
    sb.append(filePathTxt.getText());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * This method is to add action listener.
   *
   * @param listener is the object of ActionListener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    main.addActionListener(listener);
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
    userNameEntry.setText("");
    filePathTxt.setText("");
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
