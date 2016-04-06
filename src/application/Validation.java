package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class Validation {

    public static EventHandler<KeyEvent> validNo(final Integer maxLength) {
        return e -> {
            TextField tf = (TextField) e.getSource();
            if (tf.getText().length() >= maxLength) {
                e.consume();
            }
            if(e.getCharacter().matches("[0-9.]")){
                if(tf.getText().contains(".") && e.getCharacter().matches("[.]")){
                    e.consume();
                }else if(tf.getText().length() == 0 && e.getCharacter().matches("[.]")){
                    e.consume();
                }
            }else{
                e.consume();
            }
        };
    }
}
