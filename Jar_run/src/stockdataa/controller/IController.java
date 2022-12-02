package stockdataa.controller;

import java.io.FileNotFoundException;

/**
 * Controller interface that performs all operations of the TextController class.
 */

public interface IController {

  /**
   * Controller method that performs all operations through model and view.
   *
   * @throws FileNotFoundException when portfolio file not found.
   */

  void controller() throws FileNotFoundException;


}
