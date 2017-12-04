package display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUI {

	public static void pageOne(Stage xStage) {
		xStage.setTitle("Medicine Tracker");
		xStage.setResizable(false);
		Button enter = new Button();
		enter.setText("Print");
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Printing...");
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
	}

	public static void pageTwo(Stage xStage) {
		xStage.setTitle("Medicine Tracker");
		xStage.setResizable(false);
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
		xStage.setScene(new Scene(root, 300, 250));
		xStage.show();
	}
	
	public static void pageThree(Stage xStage) {
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
		xStage.setScene(new Scene(root, 300, 250));
		xStage.show();
	}
}
