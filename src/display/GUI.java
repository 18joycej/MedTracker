package display;

import data.FileReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {

	private static String path;
	private static FileReader filer;
	
	public static void setPath() {
		filer = new FileReader(path);
	}
	public static void setPath(String xPath) {
		path = xPath;
		filer = new FileReader(path);
	}
	public static void home(Stage xStage) {
		xStage.setTitle("Medicine Tracker");
		Button print = new Button();
		print.setText("Print");
		print.setLayoutX(20);
		print.setLayoutY(20);
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.printable(xStage);
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
		ScrollPane list = new ScrollPane();
		list.setLayoutY(80);
		list.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		list.setPrefSize(500, 250);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(print);
		root.getChildren().add(add);
		root.getChildren().add(t);
		root.getChildren().add(list);
		xStage.setScene(new Scene(root, 500, 350));
	}

	public static void readingLog(Stage xStage) {
		Button back = new Button();
		back.setText("Back");
		back.setLayoutX(20);
		back.setLayoutY(20);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.home(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(back);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}

	public static void printable(Stage xStage) {
		Button back = new Button();
		back.setText("Back");
		back.setLayoutX(20);
		back.setLayoutY(20);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.home(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(back);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}

	public static void addMedPage(Stage xStage) {
		Text t = new Text();
		t.setLayoutX(200);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		t.setText("Add Medication");
		TextField field = new TextField();
		field.setLayoutX();
		field.setLayoutY();
		Button save = new Button();
		save.setText("Start");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				filer.saveToFile();
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(enter);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}

	public static void addExercisePage(Stage xStage) {
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

	public static void addReadingPage(Stage xStage) {
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

	public static void addOtherPage(Stage xStage) {
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
