package com.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SignUpController extends SignInController {

	@FXML private TextField accountField;
	@FXML private PasswordField passwordField;
	@FXML private PasswordField reEnterField;
	@FXML private Button signUpButton;

	private ObservableList<TextField> textFields;
	private Scanner fileIn;


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		signUpButton.setDisable(true);

		textFields = FXCollections.observableArrayList(accountField, passwordField, reEnterField);

		textFields.forEach(textField -> textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
			if(accountField.getText().trim().equals("") || passwordField.getText().equals("")
					|| reEnterField.getText().equals(""))
				signUpButton.setDisable(true);
			else signUpButton.setDisable(false);
		}));
	}

	public void signUp(Event event) throws Exception {
		String account = accountField.getText().trim();
		String password = passwordField.getText();
		String reEnter = reEnterField.getText();

		try {
			fileIn = new Scanner(Paths.get("test3.sql"));

			StringBuilder command = new StringBuilder(fileIn.nextLine());
			command = command.insert(35, account);
			ResultSet result = getStatement().executeQuery(command.toString());
			result.next();

			if(!reEnter.equals(password)) {
				Alert errorAlert = new Alert(Alert.AlertType.ERROR,
						"Mật khẩu nhập lại phải trùng với mật khẩu ban đầu");
				errorAlert.setTitle("Đăng ký");
				errorAlert.setHeaderText("Đăng ký không thành công");
				errorAlert.showAndWait();
			} else if(!result.getString(1).equals("")) {
				Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Tài khoản đã tồn tại");
				errorAlert.setTitle("Đăng ký");
				errorAlert.setHeaderText("Đăng ký không thành công");
				errorAlert.showAndWait();
			}
		} catch (Exception e) {
			if(e.getMessage().contains("empty result set")) {
				fileIn = new Scanner(Paths.get("test2.sql"));
				StringBuilder command = new StringBuilder(fileIn.nextLine());
				command = command.insert(45, account).insert(49 + account.length(), password);
				getStatement().executeUpdate(command.toString());

				Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
				infoAlert.setTitle("Đăng ký");
				infoAlert.setTitle("Đăng ký thành công");
				infoAlert.showAndWait();
			}
		}
	}

	@Override
	public void addListenerCurrScene() {
		getCurrScene().setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER && !signUpButton.isDisable()) {
				try {
					signUp(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
