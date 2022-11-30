package stockdataa.view.ButtonsAndTextsView;

import java.awt.event.ActionListener;


public interface Texts {

  void addActionListener(ActionListener listener);

  String getInput();

  void outMess(String outToUser);


  void clean();


  void focus();
}
