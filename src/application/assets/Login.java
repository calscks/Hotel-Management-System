package application.assets;

import application.DBConnection;
import application.Validation;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private Button btn = new Button("Sign in");

    public Login() {
        DBConnection db = new DBConnection("Data.sqlite");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome to \nDrizzle Hotel Management\nSystem");
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
        //prevents copy, cut and paste for pwd
        pwd.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown()){
                if (event.getCode() == KeyCode.C || event.getCode() == KeyCode.V || event.getCode() == KeyCode.X){
                    event.consume();
                }
            }
        });
        grid.add(pwd, 1, 2);


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
            String findAdmin = "SELECT * FROM Admin WHERE UName='" + userTextField.getText() + "'";

            String adminMatch = "SELECT * FROM Admin WHERE UName='" + userTextField.getText() +
                    "' AND Pwd='" + pwd.getText() + "';";

            String findUName = "SELECT * FROM Employee WHERE EmpUName='" + userTextField.getText() + "';";
            //language=SQLite
            String UserMatch = "SELECT * FROM Employee WHERE EmpUName='" + userTextField.getText() +
                    "' AND EmpPwd='" + pwd.getText() + "';";

            ResultSet rs = null;
            try {
                rs = db.executeQuery(findAdmin);
                if(rs.next()){
                    rs = db.executeQuery(adminMatch);
                    if (rs.next()){
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText("Login Successful as Admin");
                        success.showAndWait();
                        db.closeCon();
                        adminStage();
                    }
                } else{
                    try {
                        rs = db.executeQuery(findUName); //this checks for whether username exists or not first
                        if (!rs.next()) { //resultset stores our values always on the next row.
                            Alert UNameNotFound = new Alert(Alert.AlertType.WARNING);
                            UNameNotFound.setTitle("Username not found");
                            UNameNotFound.setHeaderText("Username not found!");
                            UNameNotFound.setContentText("Username not found! Please contact the administrator of the" +
                                    " system to add your username and password.");
                            UNameNotFound.showAndWait();
                        } else {
                            rs = db.executeQuery(UserMatch); //then only check for whether uname and pwd match or not
                            if (!rs.next()) {
                                Alert NotMatch = new Alert(Alert.AlertType.ERROR);
                                NotMatch.setTitle("Warning");
                                NotMatch.setHeaderText("Login Error");
                                NotMatch.setContentText("Username or password do not match.");
                                NotMatch.showAndWait();
                            } else {
                                Alert success = new Alert(Alert.AlertType.INFORMATION);
                                success.setTitle("Success");
                                success.setHeaderText("Login Successful");
                                success.showAndWait();
                                LoginData detail = new LoginData();
                                detail.setUname(rs.getString("EmpUName"));
                                detail.setAuth(rs.getString("Authority"));
                                db.closeCon();
                                staffStage();
                            }

                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (SQLException | IOException e1) {
                e1.printStackTrace();
            }

        });

        Stage P = new Stage();

        P.setTitle("Drizzle Hotel Management System");
        P.setScene(new Scene(grid));
        P.show();
    }


    public void staffStage() {
        try {
            StackPane rootPane = new StackPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/slidemenu/" +
                    "slidemenu.fxml"));
            Parent root = loader.load();
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
    }

    private void adminStage() throws IOException {
        StackPane adminPane = new StackPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/assets/admin/admin.fxml"));
        Parent root = loader.load();
        adminPane.getChildren().addAll(root);
        Scene adminScene = new Scene(adminPane);
        Stage adminStage = new Stage();
        adminStage.setScene(adminScene);
        adminStage.show();
        Stage prevStage = (Stage) btn.getScene().getWindow();
        prevStage.close();
    }
}
