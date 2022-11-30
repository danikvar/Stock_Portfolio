package stockdataa.view.ButtonsAndTextsView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This represents the class with methods to show all users through GUI.
 */
public class ShowAllUsers extends JFrame implements Texts {
  private JTextField userNameEntry;
  private JLabel output;
  private JLabel userName;

  private JLabel label;
  private JButton home;

  private JButton select;
  private List<String> list;


  /**
   * This method is used to show all users using GUI.
   * @param tag respective tag for button assignment.
   * @param list is the list of all users.
   */
  public ShowAllUsers(String tag, List<String> list) {


    super(tag);
    this.list = list;
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel hintPanel = new JPanel();
    output = new JLabel("");
    hintPanel.add(output);

    String str = "Following are all users: " + "<br/>";
    for (String s : list) {
      str = str + s;
      str = str + "<br/>";
    }
    str = "<html>"+ str + "</html>";
    label = new JLabel(str);
    panel.add(label, BorderLayout.PAGE_START);

    userName = new JLabel( "Choose the User Name: \n");
    userNameEntry = new JTextField(20);
    panel1.add(userName);
    panel1.add(userNameEntry);

    select = new JButton("Select User");
    select.setActionCommand(("select user"));
    buttonPanel.add(select,BorderLayout.WEST);
    home = new JButton("Home");
    home.setActionCommand("show all users home");
    buttonPanel.add(home, BorderLayout.SOUTH);
    this.add(panel, BorderLayout.CENTER);
    this.add(panel1, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.add(hintPanel, BorderLayout.EAST);
    this.setVisible(true);
    this.pack();
  }

  /**
   * This method is used to get input from the user.
   * @return the input given by the user.
   */

  @Override
  public String getInput() {
    StringBuilder sb = new StringBuilder();
    sb.append(userNameEntry.getText());
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
   * Clears the fields.
   */

  @Override
  public void clean() {
    //empty
  }

  /**
   * This method is to add action listener.
   * @param listener is the object of ActionListener.
   */
  @Override
  public void addActionListener(ActionListener listener) {

    home.addActionListener(listener);
    select.addActionListener(listener);
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
