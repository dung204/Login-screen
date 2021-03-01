package com.dialog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Trang chủ");
		stage.setResizable(false);

		//Import scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../frame/Home.fxml"));
		Parent root = loader.load();

		//Set scene and show
		stage.setScene(new Scene(root));
		stage.show();

		try {
			//Connect to SQL
			String serverURL = "jdbc:mysql://localhost:3306/hodung";
			String username = "root";
			String password = "anhdung0123";

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(serverURL, username, password);

			//Transmiss connection to home scene
			HomeController controller = loader.getController();
			controller.setConnection(connection);
		} catch (Exception e) { //Handle exception when get disconnected
			System.out.println("Oops, error !!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
