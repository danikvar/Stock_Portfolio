package stockdataa.view.buttonsandtexts;

import java.awt.event.ActionListener;

/**
 * This represents the class TEXTS which has methods to get inputs from the user through GUI.
 */


public interface Texts {

  /**
   * This method helps us in assigning a command to a button.
   *
   * @param listener is the object of ActionListener.
   */

  void addActionListener(ActionListener listener);

  /**
   * This method is to get inputs from the user.
   *
   * @return the input given by the user.
   */

  String getInput();

  /**
   * This method is to print a message to the user.
   *
   * @param outToUser is the string that is to be printed.
   */

  void outMess(String outToUser);

  /**
   * Cleans the text fields.
   */


  void clean();

  /**
   * Focus set.
   */


  void focus();
}
