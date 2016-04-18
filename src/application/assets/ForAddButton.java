package application.assets;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;


/*
This class is to show new stages such as for room and facility when pressed add button to show the stage.

I personally think this one is not applicable to add group, because add group member will need extra function

How to use this:

1. FXMLLoader loadfxml = new FXMLLoader(getClass().getResource("path"));
2. AnchorPane pane = new AnchorPane;
3. try {
            pane = loadfxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
4. AnchorPane finalPane = pane

Then you can call with:
new ForAddButton(finalPane, theAddButton)
the add button is to show a new stage to add sth.
 */

public class ForAddButton {
    private AnchorPane finalPane;
    private Button addButton;
    private TextField unique;
    private Label label;

    public ForAddButton(AnchorPane finalPane, Button addButton) {
        this.finalPane = finalPane;
        this.addButton = addButton;

        showStage();
    }

    //method overloading
    public ForAddButton(AnchorPane finalPane, Button addButton, TextField unique) {
        this.finalPane = finalPane;
        this.addButton = addButton;
        this.unique = unique;

        showStage2();
    }

    public ForAddButton(AnchorPane finalPane, Label label){
        this.finalPane = finalPane;
        this.label = label;

        showStage3();
    }

    private void showStage() {
        addButton.setOnMouseClicked(me -> {
            Stage stage = new Stage();

            if (finalPane.getScene() != null) {
                stage.setScene(finalPane.getScene());
            } else {
                Scene scene = new Scene(finalPane);
                stage.setScene(scene);
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
        });
    }

    private void showStage2() {
        addButton.setOnMouseClicked(me -> {
            if (!Objects.equals(unique.getText(), "")) {
                Stage stage = new Stage();

                if (finalPane.getScene() != null) {
                    stage.setScene(finalPane.getScene());
                } else {
                    Scene scene = new Scene(finalPane);
                    stage.setScene(scene);
                }
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                stage.setResizable(false);
                stage.setAlwaysOnTop(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Table Empty");
                alert.setHeaderText("Reservation is not loaded");
                alert.setContentText("No reservation is loaded. Please load a reservation, only then you can " +
                        "modify the table.");
                alert.showAndWait();
            }
        });
    }

    private void showStage3() {
        label.setOnMouseClicked(me -> {
            Stage stage = new Stage();

            if (finalPane.getScene() != null) {
                stage.setScene(finalPane.getScene());
            } else {
                Scene scene = new Scene(finalPane);
                stage.setScene(scene);
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setResizable(false);
        });
    }
}
