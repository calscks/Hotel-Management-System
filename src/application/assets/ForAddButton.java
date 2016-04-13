package application.assets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/*
This class is to show new stages such as for group member, room when pressed add button to show the stage.
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

    public ForAddButton(AnchorPane finalPane, Button addButton){
        this.finalPane = finalPane;
        this.addButton = addButton;

        showStage();
    }

    private void showStage(){
        addButton.setOnMouseClicked(me->{
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

}
