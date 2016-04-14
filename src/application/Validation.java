package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**please use these validations on any text fields you want.
 * Usage:
 * <code>
 *     theTextField.addEventFilter(KeyEvent.KEY_TYPED, Validation.oneOfTheMethodsBelow( int maxLength));
 * </code>
 * <p>validNo</p>
 * <p>validChar</p>
 * <p>validCharNo</p>*/
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

    public static EventHandler<KeyEvent> validChar(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z ]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validCharNo(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validCharNoCommaDot(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9,.]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validPrice(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Z0-9.]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validCharNoSpace(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9 ]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validForTypeName(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9 -:]")) {
                e.consume();
            }
        };
    }

}
