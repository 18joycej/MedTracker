package display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch05.queues.LinkedUnbndQueue;
import data.FileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import support.Medication;
import ch05.queues.*;
public class GUI {

	private static String path = "src\\data\\info.txt";
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
		List<String> medicalList = new ArrayList<String>();
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
		Button remove = new Button();
		remove.setText("Remove");
		remove.setLayoutX(440);
		remove.setLayoutY(50);
		remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.removePage(xStage);
			}
		});
		Text t = new Text("Home Menu");
		t.setLayoutX(200);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		Text logText=new Text ("Log here:");
		logText.setLayoutX(300);
		logText.setLayoutY(100);
		logText.setFont(new Font(18));
		TextField itemName = new TextField();
		itemName.setLayoutX(270);
		itemName.setLayoutY(130);
		itemName.setText("Name of item");
		TextField summary = new TextField();
		summary.setLayoutX(270);
		summary.setLayoutY(200);
		summary.setMinSize(120, 50);
		summary.setText("Enter notes");
		TextField amounts = new TextField();
		amounts.setLayoutX(270);
		amounts.setLayoutY(160);
		amounts.setText("Enter doseage/reading/reps done");
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		LinkedUnbndQueue<String> medsRaw = null;
		Calendar today= Calendar.getInstance();
		int dayOfWeek=today.get(Calendar.DAY_OF_WEEK);
		try {
			medsRaw=filer.readFromFile();
			ArrayList<LinkedUnbndQueue<Medication>> medsFixed = new ArrayList<LinkedUnbndQueue<Medication>>(1440);
			for(int i=0;i<1440;i++) {
				medsFixed.add(new LinkedUnbndQueue<Medication>());
			}
			while(!medsRaw.isEmpty()) {
				System.out.println("Run");
				String[] temp1 = medsRaw.dequeue().split("\\$");
				System.out.println("Check");
				Medication temp2 = new Medication(temp1[0],temp1[1],Integer.parseInt(temp1[2]),Integer.parseInt(temp1[3]),Integer.parseInt(temp1[4]),temp1[5],temp1[6],1);
				if (temp2.getDateSetting()==1) {
					if(temp2.getTimeSetting()==1) {
						medsFixed.get(temp2.getSpecificTime()).enqueue(temp2);
					}
					else {
						for(int i=0;i<temp2.getMultipleTimes().length;i++) {
							medsFixed.get(temp2.getMultipleTimes()[i]).enqueue(temp2);
						}
					}
				}
				else {
					boolean checker=false;
						for(int i=0;i<temp2.getSelectDays().length;i++) {
							if(temp2.getSelectDays()[i]==dayOfWeek) {
								checker=true;
							}
						}
						if(checker==true) {
							if(temp2.getTimeSetting()==1) {
								medsFixed.get(temp2.getSpecificTime()).enqueue(temp2);
							}
							else {
								for(int i=0;i<temp2.getMultipleTimes().length;i++) {
									medsFixed.get(temp2.getMultipleTimes()[i]).enqueue(temp2);
								}
							}
						}
				}
			}
		File printFile = new File("src\\data\\printable.txt");
		printFile.delete();
		try {
			printFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printableWriter=new PrintWriter(new FileOutputStream(printFile));
		for(int i=0;i<medsFixed.size();i++) {
			if(!medsFixed.get(i).isEmpty()) {
				Medication temp=medsFixed.get(i).dequeue();
				if (temp.getTimeSetting()==1) {
					medicalList.add(temp.toString());
					printableWriter.println(temp.toString());
				}
				else {
					medicalList.add(temp.toString(i));
					printableWriter.println(temp.toString(i));
				}
			}
		}
		printableWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		Button log = new Button();
		log.setText("Log");
		log.setLayoutX(270);
		log.setLayoutY(250);
		log.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				File logFile = new File("src\\data\\log.txt");
				if(!logFile.exists()) {
					try {
						logFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					PrintWriter temporary=new PrintWriter(new FileOutputStream(logFile));
					Calendar timeAndDate=Calendar.getInstance();
					temporary.println(""+timeAndDate.get(Calendar.MONTH)+"/"+timeAndDate.get(Calendar.DAY_OF_MONTH)+"/"+timeAndDate.get(Calendar.YEAR)+": "+itemName.getText()+", "+amounts.getText()+", "+summary.getText());
					temporary.close();
					Thread.sleep(3000);
					GUI.home(xStage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		ObservableList<String> obsList = FXCollections.observableArrayList(medicalList);
		ListView<String> graphList = new ListView<String>(obsList);
		ScrollPane list = new ScrollPane();
		list.setLayoutY(80);
		list.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		list.setHbarPolicy(ScrollBarPolicy.NEVER);
		list.setPrefSize(260, 250);
		list.setContent(graphList);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(add);
		root.getChildren().add(remove);
		root.getChildren().add(t);
		root.getChildren().add(list);
		root.getChildren().add(logText);
		root.getChildren().add(summary);
		root.getChildren().add(amounts);
		root.getChildren().add(itemName);
		root.getChildren().add(log);
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
	public static void addMedPage(Stage xStage) {
		Text t = new Text();
		t.setLayoutX(180);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		t.setText("Add Medication");
		TextField field = new TextField();
		field.setLayoutX(20);
		field.setLayoutY(150);
		field.setText("Name of medication");
		TextField doset = new TextField();
		doset.setLayoutX(20);
		doset.setLayoutY(200);
		doset.setText("Amount per dosage");
		TextField timet = new TextField();
		timet.setLayoutX(300);
		timet.setLayoutY(150);
		timet.setText("Timing");
		TextField urgent = new TextField();
		urgent.setLayoutX(20);
		urgent.setLayoutY(250);
		urgent.setText("Urgency as a number");
		TextField times = new TextField();
		times.setLayoutX(300);
		times.setLayoutY(200);
		times.setText("Enter the time setting");
		TextField dates = new TextField();
		dates.setLayoutX(300);
		dates.setLayoutY(240);
		dates.setText("Enter the date setting");
		TextField days = new TextField();
		days.setLayoutX(300);
		days.setLayoutY(270);
		days.setText("If setting 2, enter the days to take it, separated by ;");
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String name = field.getText();
				String dose = doset.getText();
				int urgency = Integer.parseInt(urgent.getText());
				int timeSetting = Integer.parseInt(times.getText());
				int dateSetting = Integer.parseInt(dates.getText());
				String dayz = days.getText();
				String time = timet.getText();
				try {
					System.out.println("name: "+name+" dose: "+dose+" urgency: "+urgency+" timeSetting: "+timeSetting+" dateSetting: "+dateSetting+" dayz: "+dayz+" time: "+time);
					if(dateSetting==2){
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 1));
					}
					else if(dateSetting==1) {
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 1));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GUI.home(xStage);
			}
		});
		Button cancel = new Button();
		cancel.setText("Cancel");
		cancel.setLayoutX(20);
		cancel.setLayoutY(20);
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.home(xStage);
			}
		});
		Button medication = new Button();
		medication.setText("Medication");
		medication.setLayoutX(30);
		medication.setLayoutY(80);
		medication.setPrefWidth(110);
		Button exercise = new Button();
		exercise.setText("Exercise");
		exercise.setLayoutX(140);
		exercise.setLayoutY(80);
		exercise.setPrefWidth(110);
		exercise.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addExercisePage(xStage);
			}
		});
		Button reading = new Button();
		reading.setText("Medical Reading");
		reading.setLayoutX(250);
		reading.setLayoutY(80);
		reading.setPrefWidth(110);
		reading.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addReadingPage(xStage);
			}
		});
		Button other = new Button();
		other.setText("Exercise");
		other.setLayoutX(360);
		other.setLayoutY(80);
		other.setPrefWidth(110);
		other.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addOtherPage(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(save);
		root.getChildren().add(cancel);
		root.getChildren().add(medication);
		root.getChildren().add(exercise);
		root.getChildren().add(reading);
		root.getChildren().add(other);
		root.getChildren().add(t);
		root.getChildren().add(field);
		root.getChildren().add(doset);
		root.getChildren().add(timet);
		root.getChildren().add(urgent);
		root.getChildren().add(times);
		root.getChildren().add(dates);
		root.getChildren().add(days);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}

	public static void addExercisePage(Stage xStage) {
		Text t = new Text();
		t.setLayoutX(180);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		t.setText("Add Excersise");
		TextField field = new TextField();
		field.setLayoutX(20);
		field.setLayoutY(150);
		field.setText("Name of exercise");
		TextField doset = new TextField();
		doset.setLayoutX(20);
		doset.setLayoutY(200);
		doset.setText("Reps");
		TextField timet = new TextField();
		timet.setLayoutX(300);
		timet.setLayoutY(150);
		timet.setText("Timing");
		TextField urgent = new TextField();
		urgent.setLayoutX(20);
		urgent.setLayoutY(250);
		urgent.setText("Urgency as a number");
		TextField times = new TextField();
		times.setLayoutX(300);
		times.setLayoutY(200);
		times.setText("Enter the time setting");
		TextField dates = new TextField();
		dates.setLayoutX(300);
		dates.setLayoutY(240);
		dates.setText("Enter the date setting");
		TextField days = new TextField();
		days.setLayoutX(300);
		days.setLayoutY(270);
		days.setText("If setting 2, enter the days to take it, separated by ;");
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String name = field.getText();
				String dose = doset.getText();
				int urgency = Integer.parseInt(urgent.getText());
				int timeSetting = Integer.parseInt(times.getText());
				int dateSetting = Integer.parseInt(dates.getText());
				String dayz = days.getText();
				String time = timet.getText();
				try {
					System.out.println("name: "+name+" dose: "+dose+" urgency: "+urgency+" timeSetting: "+timeSetting+" dateSetting: "+dateSetting+" dayz: "+dayz+" time: "+time);
					if(dateSetting==2){
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 2));
					}
					else if(dateSetting==1) {
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 2));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GUI.home(xStage);
			}
		});
		Button cancel = new Button();
		cancel.setText("Cancel");
		cancel.setLayoutX(20);
		cancel.setLayoutY(20);
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.home(xStage);
			}
		});
		Button medication = new Button();
		medication.setText("Medication");
		medication.setLayoutX(30);
		medication.setLayoutY(80);
		medication.setPrefWidth(110);
		medication.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addMedPage(xStage);
			}
		});
		Button exercise = new Button();
		exercise.setText("Exercise");
		exercise.setLayoutX(140);
		exercise.setLayoutY(80);
		exercise.setPrefWidth(110);
		Button reading = new Button();
		reading.setText("Medical Reading");
		reading.setLayoutX(250);
		reading.setLayoutY(80);
		reading.setPrefWidth(110);
		reading.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addReadingPage(xStage);
			}
		});
		Button other = new Button();
		other.setText("Exercise");
		other.setLayoutX(360);
		other.setLayoutY(80);
		other.setPrefWidth(110);
		other.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addOtherPage(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(save);
		root.getChildren().add(cancel);
		root.getChildren().add(medication);
		root.getChildren().add(exercise);
		root.getChildren().add(reading);
		root.getChildren().add(other);
		root.getChildren().add(t);
		root.getChildren().add(field);
		root.getChildren().add(doset);
		root.getChildren().add(timet);
		root.getChildren().add(urgent);
		root.getChildren().add(times);
		root.getChildren().add(dates);
		root.getChildren().add(days);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}

	public static void addReadingPage(Stage xStage) {
		Text t = new Text();
		t.setLayoutX(180);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		t.setText("Add Reading");
		TextField field = new TextField();
		field.setLayoutX(20);
		field.setLayoutY(150);
		field.setText("Name of biological reading");
		TextField doset = new TextField();
		doset.setLayoutX(20);
		doset.setLayoutY(200);
		doset.setText("Notes");
		TextField timet = new TextField();
		timet.setLayoutX(300);
		timet.setLayoutY(150);
		timet.setText("Timing");
		TextField urgent = new TextField();
		urgent.setLayoutX(20);
		urgent.setLayoutY(250);
		urgent.setText("Urgency as a number");
		TextField times = new TextField();
		times.setLayoutX(300);
		times.setLayoutY(200);
		times.setText("Enter the time setting");
		TextField dates = new TextField();
		dates.setLayoutX(300);
		dates.setLayoutY(240);
		dates.setText("Enter the date setting");
		TextField days = new TextField();
		days.setLayoutX(300);
		days.setLayoutY(270);
		days.setText("If setting 2, enter the days to take it, separated by ;");
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String name = field.getText();
				String dose = doset.getText();
				int urgency = Integer.parseInt(urgent.getText());
				int timeSetting = Integer.parseInt(times.getText());
				int dateSetting = Integer.parseInt(dates.getText());
				String dayz = days.getText();
				String time = timet.getText();
				try {
					System.out.println("name: "+name+" dose: "+dose+" urgency: "+urgency+" timeSetting: "+timeSetting+" dateSetting: "+dateSetting+" dayz: "+dayz+" time: "+time);
					if(dateSetting==2){
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 3));
					}
					else if(dateSetting==1) {
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 3));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GUI.home(xStage);
			}
		});
		Button cancel = new Button();
		cancel.setText("Cancel");
		cancel.setLayoutX(20);
		cancel.setLayoutY(20);
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.home(xStage);
			}
		});
		Button medication = new Button();
		medication.setText("Medication");
		medication.setLayoutX(30);
		medication.setLayoutY(80);
		medication.setPrefWidth(110);
		medication.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addMedPage(xStage);
			}
		});
		Button exercise = new Button();
		exercise.setText("Exercise");
		exercise.setLayoutX(140);
		exercise.setLayoutY(80);
		exercise.setPrefWidth(110);
		exercise.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addExercisePage(xStage);
			}
		});
		Button reading = new Button();
		reading.setText("Medical Reading");
		reading.setLayoutX(250);
		reading.setLayoutY(80);
		reading.setPrefWidth(110);
		Button other = new Button();
		other.setText("Exercise");
		other.setLayoutX(360);
		other.setLayoutY(80);
		other.setPrefWidth(110);
		other.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addOtherPage(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(save);
		root.getChildren().add(cancel);
		root.getChildren().add(medication);
		root.getChildren().add(exercise);
		root.getChildren().add(reading);
		root.getChildren().add(other);
		root.getChildren().add(t);
		root.getChildren().add(field);
		root.getChildren().add(doset);
		root.getChildren().add(timet);
		root.getChildren().add(urgent);
		root.getChildren().add(times);
		root.getChildren().add(dates);
		root.getChildren().add(days);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}

	public static void addOtherPage(Stage xStage) {
		Text t = new Text();
		t.setLayoutX(180);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		t.setText("Add Other");
		TextField field = new TextField();
		field.setLayoutX(20);
		field.setLayoutY(150);
		field.setText("Name of activity");
		TextField doset = new TextField();
		doset.setLayoutX(20);
		doset.setLayoutY(200);
		doset.setText("Notes");
		TextField timet = new TextField();
		timet.setLayoutX(300);
		timet.setLayoutY(150);
		timet.setText("Timing");
		TextField urgent = new TextField();
		urgent.setLayoutX(20);
		urgent.setLayoutY(250);
		urgent.setText("Urgency as a number");
		TextField times = new TextField();
		times.setLayoutX(300);
		times.setLayoutY(200);
		times.setText("Enter the time setting");
		TextField dates = new TextField();
		dates.setLayoutX(300);
		dates.setLayoutY(240);
		dates.setText("Enter the date setting");
		TextField days = new TextField();
		days.setLayoutX(300);
		days.setLayoutY(270);
		days.setText("If setting 2, enter the days to take it, separated by ;");
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String name = field.getText();
				String dose = doset.getText();
				int urgency = Integer.parseInt(urgent.getText());
				int timeSetting = Integer.parseInt(times.getText());
				int dateSetting = Integer.parseInt(dates.getText());
				String dayz = days.getText();
				String time = timet.getText();
				try {
					System.out.println("name: "+name+" dose: "+dose+" urgency: "+urgency+" timeSetting: "+timeSetting+" dateSetting: "+dateSetting+" dayz: "+dayz+" time: "+time);
					if(dateSetting==2){
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 4));
					}
					else if(dateSetting==1) {
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time, 4));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GUI.home(xStage);
			}
		});
		Button cancel = new Button();
		cancel.setText("Cancel");
		cancel.setLayoutX(20);
		cancel.setLayoutY(20);
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.home(xStage);
			}
		});
		Button medication = new Button();
		medication.setText("Medication");
		medication.setLayoutX(30);
		medication.setLayoutY(80);
		medication.setPrefWidth(110);
		medication.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addMedPage(xStage);
			}
		});
		Button exercise = new Button();
		exercise.setText("Exercise");
		exercise.setLayoutX(140);
		exercise.setLayoutY(80);
		exercise.setPrefWidth(110);
		exercise.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addExercisePage(xStage);
			}
		});
		Button reading = new Button();
		reading.setText("Medical Reading");
		reading.setLayoutX(250);
		reading.setLayoutY(80);
		reading.setPrefWidth(110);
		reading.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.addReadingPage(xStage);
			}
		});
		Button other = new Button();
		other.setText("Exercise");
		other.setLayoutX(360);
		other.setLayoutY(80);
		other.setPrefWidth(110);
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(save);
		root.getChildren().add(cancel);
		root.getChildren().add(medication);
		root.getChildren().add(exercise);
		root.getChildren().add(reading);
		root.getChildren().add(other);
		root.getChildren().add(t);
		root.getChildren().add(field);
		root.getChildren().add(doset);
		root.getChildren().add(timet);
		root.getChildren().add(urgent);
		root.getChildren().add(times);
		root.getChildren().add(dates);
		root.getChildren().add(days);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
	public static void removePage(Stage xStage) {
		Text t = new Text();
		t.setLayoutX(180);
		t.setLayoutY(45);
		t.setFont(new Font(20));
		t.setText("Remove");
		List<String> medicalList = new ArrayList<String>();
		LinkedUnbndQueue<String> medsRaw = null;
		ArrayList<String> names=new ArrayList<String>();
		try {
			medsRaw=filer.readFromFile();
			LinkedUnbndQueue<Medication> medsFixed = new LinkedUnbndQueue<Medication>();
			while(!medsRaw.isEmpty()) {
				String[] temp1 = medsRaw.dequeue().split("\\$");
				Medication temp2 = new Medication(temp1[0],temp1[1],Integer.parseInt(temp1[2]),Integer.parseInt(temp1[3]),Integer.parseInt(temp1[4]),temp1[5],temp1[6],1);
				if(temp2.getTimeSetting()==1) {
					medsFixed.enqueue(temp2);
				}
				else {
					medsFixed.enqueue(temp2);
				}
			}
		while(!medsFixed.isEmpty()) {
				Medication temp=medsFixed.dequeue();
				if (temp.getTimeSetting()==1) {
					medicalList.add(temp.getName());
				}
				else {
					medicalList.add(temp.getName());
				}
			}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
	e1.printStackTrace();
	}
		for(int i=0;i<medicalList.size();i++) {
			names.add(""+(i+1)+". "+medicalList.get(i));
		}
		ObservableList<String> fullList = FXCollections.observableArrayList(names);
		final ComboBox<String> selectableList=new ComboBox<String>(fullList);
		selectableList.setLayoutX(180);
		selectableList.setLayoutY(150);
		TextField field = new TextField();
		field.setLayoutX(20);
		field.setLayoutY(150);
		field.setText("Number of item");
		Button delete = new Button();
		delete.setText("Delete");
		delete.setLayoutX(440);
		delete.setLayoutY(20);
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					filer.deleteFromFile(Integer.parseInt(field.getText()));
					GUI.home(xStage);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Button cancel = new Button();
		cancel.setText("Cancel");
		cancel.setLayoutX(20);
		cancel.setLayoutY(20);
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUI.home(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 80);
		AnchorPane root = new AnchorPane(rec);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(delete);
		root.getChildren().add(cancel);
		root.getChildren().add(t);
		root.getChildren().add(field);
		root.getChildren().add(selectableList);
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
}
