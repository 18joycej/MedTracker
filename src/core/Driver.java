package core;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Driver extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	public void start(Stage mainStage) {
		mainStage.setTitle("Medicine Tracker");
		Button enter = new Button();
		enter.setText("Start");
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Start!");
			}
		});
		Pane root = new Pane();
		root.getChildren().add(enter);
		mainStage.setScene(new Scene(root, 300, 250));
		mainStage.show();
	}

}