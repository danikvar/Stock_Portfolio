package stockdataa.view.ButtonsAndTextsView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TBD extends JFrame implements Texts {

  public TBD(String tag) {
    super(tag);

  }


  @Override
  public void addActionListener (ActionListener listener) {

  }


  @Override
  public String getInput () {

    return null;
  }


  @Override
  public void outMess (String outToUser) {

  }


  @Override
  public void clean () {

  }


  @Override
  public void focus () {
    this.setFocusable(true);
    this.requestFocus();
  }
}
