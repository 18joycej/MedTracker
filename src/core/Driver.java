package core;


import display.GUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Driver extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver.launch(args);
	}

	public void start(Stage mainStage) {
		mainStage.setTitle("Medicine Tracker");
		mainStage.setResizable(false);
		Button enter = new Button();
		enter.setText("Start");
		enter.setLayoutX(240);
		enter.setLayoutY(165);
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.setPath();
				GUI.home(mainStage);
			}
		});
		Pane root = new Pane();
		root.getChildren().add(new Rectangle(500, 350, Color.ORANGERED));
		root.getChildren().add(enter);
		mainStage.setScene(new Scene(root, 500, 350));
		mainStage.show();
	}

}