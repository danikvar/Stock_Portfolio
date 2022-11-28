package stockdataa.view.TextFieldView;

import java.awt.event.ActionListener;


public interface WithTextField {

  void addActionListener(ActionListener listener);

  String getInput();

  void setHintMess(String message);


  void clearField();


  void resetFocus();
}
