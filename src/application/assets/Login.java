package application.assets;

import application.DBConnection;
import application.Validation;
import application.slidemenu.SlideMenuController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    DBConnection db = new DBConnection("Data.sqlite");

    public GridPane loginGrid() {

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
        userTextField.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(20));
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwd = new PasswordField();
        grid.add(pwd, 1, 2);

        Button btn = new Button("Sign in");
        btn.disableProperty().bind(
                Bindings.isEmpty(userTextField.textProperty()).or(Bindings.isEmpty(pwd.textProperty()))
        ); //disable sign in button when 2 fields are empty
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        btn.setOnAction(e -> {
            String findUName = "SELECT EmpUName,EmpID FROM Employee WHERE EmpUName='" + userTextField.getText() + "';";
            //language=SQLite
            String UserMatch = "SELECT EmpUName,EmpID FROM Employee WHERE EmpUName='" + userTextField.getText() +
                    "' AND EmpPwd='"+ pwd.getText() + "';";
            try {
                ResultSet rs = db.executeQuery(findUName);
                if (!rs.next()){
                    Alert UNameNotFound = new Alert(Alert.AlertType.WARNING);
                    UNameNotFound.setTitle("Username not found");
                    UNameNotFound.setHeaderText("Username not found!");
                    UNameNotFound.setContentText("Username not found! Please contact the administrator of the" +
                            "system to add your username and password.");
                    UNameNotFound.showAndWait();
                } else {
                    rs = db.executeQuery(UserMatch);
                    if (!rs.next()){
                        Alert NotMatch = new Alert(Alert.AlertType.WARNING);
                        NotMatch.setTitle("Warning");
                        NotMatch.setHeaderText("Login Error");
                        NotMatch.setContentText("Username or password do not match.");
                        NotMatch.showAndWait();
                        return;
                    }

                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                StackPane rootPane = new StackPane();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/slidemenu/" +
                        "slidemenu.fxml"));
                Parent root = loader.load();
                SlideMenuController controller = loader.<SlideMenuController>getController();
                //submenu_RNF.setManaged(false);
                rootPane.getChildren().addAll(root);
                Scene mainScene = new Scene(rootPane);
                Stage mainStage = new Stage();
                mainStage.setMinWidth(800);
                mainStage.setMinHeight(600);
                mainStage.setScene(mainScene);
                mainStage.show();
                Stage prevStage = (Stage) btn.getScene().getWindow();
                prevStage.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        db.closeCon();
        return grid;
    }
}
