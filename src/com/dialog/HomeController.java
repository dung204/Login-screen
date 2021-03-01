package com.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomeController {

	private Connection connection;

	public void signIn(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
		Parent root = loader.load();
		SignInController controller = loader.getController();
		controller.setConnection(connection);
		controller.setStatement(connection.createStatement());

		Scene currScene = new Scene(root);
		controller.setCurrScene(currScene);
		controller.addListenerCurrScene();

		stage.setScene(currScene);
		stage.setTitle("Đăng nhập");
		stage.show();
	}

	public void signUp(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		Parent root = loader.load();
		SignUpController controller = loader.getController();
		controller.setConnection(connection);
		controller.setStatement(connection.createStatement());

		Scene currScene = new Scene(root);
		controller.setCurrScene(currScene);
		controller.addListenerCurrScene();

		stage.setScene(currScene);
		stage.setTitle("Đăng ký");
		stage.show();
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}