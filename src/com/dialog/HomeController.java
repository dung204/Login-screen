package com.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;

public class HomeController {

	private Connection connection; //connection to receive from main

	//When clicking sign in
	public void signIn(ActionEvent event) throws Exception {
		//Get current stage
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../frame/SignIn.fxml"));
		Parent root = loader.load();
		SignInController controller = loader.getController();

		//Transmit connection and statement
		controller.setConnection(connection);
		controller.setStatement(connection.createStatement());

		//Set scene and show
		Scene currScene = new Scene(root);
		controller.setCurrScene(currScene);
		controller.addListenerCurrScene();

		stage.setScene(currScene);
		stage.setTitle("Đăng nhập");
		stage.show();
	}

	//Same as login's
	public void signUp(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../frame/SignUp.fxml"));
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