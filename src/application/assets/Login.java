package application.assets;

import application.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class Login{

    public GridPane loginGrid() {

        DBConnection.getCon("Data.sqlite");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome to \nHotel Management System");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwd = new PasswordField();
        grid.add(pwd, 1, 2);

        Button btn = new Button("Sign in");
        Button btn1 = new Button("Jump"); //temporary jump to another stage button
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        hbBtn.getChildren().add(btn1);
        grid.add(hbBtn, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        btn.setOnAction(e->{
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setText("Sign in button pressed");
        });

        btn1.setOnAction(e->{
            try {
                StackPane rootPane = new StackPane();
                Parent root = FXMLLoader.load(getClass().getResource("/application/slidemenu/slidemenu.fxml"));
                rootPane.getChildren().addAll(root);
                Scene mainScene = new Scene(rootPane);
                Stage mainStage = new Stage();
                mainStage.setMinWidth(800);
                mainStage.setMinHeight(600);
                mainStage.setScene(mainScene);
                mainStage.show();
                Stage prevStage = (Stage) btn1.getScene().getWindow();
                prevStage.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        DBConnection.closeCon();

        return grid;

    }
}
