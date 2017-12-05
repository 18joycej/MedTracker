package display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {

	public static void home(Stage xStage) {
		xStage.setTitle("Medicine Tracker");
		Button print = new Button();
		print.setText("Print");
		print.setLayoutX(20);
		print.setLayoutY(20);
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Printing...");
			}
		});
		Button add = new Button();
		add.setText("Add");
		add.setLayoutX(440);
		add.setLayoutY(20);
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.addMedPage(xStage);
			}
		});
		Text t = new Text("Home Menu");
		t.setLayoutX(200);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(print);
		root.getChildren().add(add);
		root.getChildren().add(t);
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
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
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
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
	public static void addMedPage(Stage xStage) {
		Button enter = new Button();
		enter.setText("Start");
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Start!");
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
	public static void pageFive(Stage xStage) {
		Button enter = new Button();
		enter.setText("Start");
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Start!");
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
	public static void pageSix(Stage xStage) {
		Button enter = new Button();
		enter.setText("Start");
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Start!");
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
	public static void pageSeven(Stage xStage) {
		Button enter = new Button();
		enter.setText("Start");
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Start!");
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
}
