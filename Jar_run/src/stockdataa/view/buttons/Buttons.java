package stockdataa.view.buttons;

import java.awt.event.ActionListener;

/**
 * This is the interface for all the buttons used in the GUI.
 */
public interface Buttons {

  /**
   * This method is to add action listener.
   *
   * @param listener is the object of ActionListener.
   */


  void addActionListener(ActionListener listener);

  /**
   * Focus set.
   */


  void focus();


}
