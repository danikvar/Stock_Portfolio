package stockdataa.view.ButtonsView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TBD1 extends JFrame implements Buttons {


  public TBD1(String tag) {
    super(tag);

  }


  @Override
  public void addActionListener (ActionListener listener) {
    
  }


  @Override
  public void focus () {
    this.setFocusable(true);
    this.requestFocus();
  }

}
