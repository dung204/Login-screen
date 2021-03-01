package com.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SignInController implements Initializable {
	private Scene currScene;
	private Connection connection;
	private Statement statement;

	@FXML private TextField accountField;
	@FXML private PasswordField passwordField;
	@FXML private Button signInButton;

	private ObservableList<TextField> textFields;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		signInButton.setDisable(true);

		textFields = FXCollections.observableArrayList(accountField, passwordField);

		textFields.forEach(textField -> textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
			if(passwordField.getText().equals("") || accountField.getText().trim().equals(""))
				signInButton.setDisable(true);
			else signInButton.setDisable(false);
		}));
	}

	public void signIn(Event event) {
		try {
			Scanner fileIn = new Scanner(Paths.get("test.sql"));
			String account = accountField.getText().trim();
			String password = passwordField.getText();

			StringBuilder command = new StringBuilder(fileIn.nextLine());
			command = command.insert(35, account).insert(53 + account.length(), password);
			ResultSet result = statement.executeQuery(command.toString());
			result.next();
			result.getString(1);

			//Handle successful login
			Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
			infoAlert.setTitle("Đăng nhập");
			infoAlert.setHeaderText("Đăng nhập thành công");
			infoAlert.showAndWait();
		} catch (Exception e) { //When encounter error (login failed)
			if(e.getMessage().contains("empty result set")) {
				Alert errorAlert = new Alert(Alert.AlertType.ERROR,
						"Tài khoản hoặc mật khẩu không đúng hoặc không tồn tại");
				errorAlert.setTitle("Đăng nhập");
				errorAlert.setHeaderText("Đăng nhập không thành công");
				errorAlert.showAndWait();
			}
		}
	}

	public void returnButton(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../frame/Home.fxml"));
		Parent root = loader.load();
		HomeController controller = loader.getController();
		controller.setConnection(connection);

		stage.setScene(new Scene(root));
		stage.setTitle("Trang chủ");
		stage.show();
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setCurrScene(Scene currScene) {
		this.currScene = currScene;
	}

	public Scene getCurrScene() {
		return currScene;
	}

	public void addListenerCurrScene() {
		currScene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER && !signInButton.isDisable()) {
				signIn(event);
			}
		});
	}
}
