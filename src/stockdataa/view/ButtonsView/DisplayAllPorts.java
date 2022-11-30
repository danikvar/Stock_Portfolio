package stockdataa.view.ButtonsView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DisplayAllPorts extends JFrame implements Buttons {

  private JLabel label;
  private JButton home;
  private List<String> list;


  public DisplayAllPorts(String tag, List<String> list) {
    super(tag);
    this.list = list;
    this.setPreferredSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String str = "Following are all portfolios: " + "<br/>";
    for (String s : list) {
      str = str + s;
      str = str + "<br/>";
    }
    str = "<html>"+ str + "</html>";
    label = new JLabel(str);
    panel.add(label, BorderLayout.PAGE_START);

    home = new JButton("Home");
    home.setActionCommand("show all portfolios home");
    buttonPanel.add(home, BorderLayout.SOUTH);
    this.add(panel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();
  }


  @Override
  public void addActionListener(ActionListener listener) {
    home.addActionListener(listener);
  }


  @Override
  public void focus() {
    this.setFocusable(true);
    this.requestFocus();
  }




}
