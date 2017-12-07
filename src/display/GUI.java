package display;

import java.io.IOException;
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
import javafx.scene.control.CheckBox;
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
		Button print = new Button();
		print.setText("Print");
		print.setLayoutX(20);
		print.setLayoutY(20);
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.printable(xStage, medicalList);
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
				String[] temp1 = medsRaw.dequeue().split("\\$");
				System.out.println(temp1[0]);
				System.out.println(temp1.length);
				Medication temp2 = new Medication(temp1[0],temp1[1],Integer.parseInt(temp1[2]),Integer.parseInt(temp1[3]),Integer.parseInt(temp1[4]),temp1[5],temp1[6]);
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
		for(int i=0;i<medsFixed.size();i++) {
			if(!medsFixed.get(i).isEmpty()) {
				Medication temp=medsFixed.get(i).dequeue();
				if (temp.getTimeSetting()==1) {
					medicalList.add(temp.toString());
				}
				else {
					System.out.println("Check1");
					for(int r=0;r<temp.getMultipleTimes().length;r++) {
						System.out.println("Check");
						medicalList.add(temp.toString(r));
					}
					break;
				}
			}
		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		ObservableList<String> obsList = FXCollections.observableArrayList(medicalList);
		ListView<String> graphList = new ListView<String>(obsList);
		ScrollPane list = new ScrollPane();
		list.setLayoutY(80);
		list.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		list.setPrefSize(500, 250);
		list.setContent(graphList);
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

	public static void printable(Stage xStage, List<String> xList) {
		Button back = new Button();
		ObservableList<String> obsListm = FXCollections.observableArrayList();
		ObservableList<String> obsLista = FXCollections.observableArrayList();
		ObservableList<String> obsListe = FXCollections.observableArrayList();
		ObservableList<String> obsListn = FXCollections.observableArrayList();
		while(xList.size()!=0){
			String xString = xList.remove(0);
			String tString = xString.toString();
			xString = xString.substring(0, xString.lastIndexOf("$"));
			xString = xString.substring(xString.lastIndexOf("$")+1, xString.length());
			int parse = Integer.parseInt(xString);
			if(parse>=600 && parse<1200) {
				obsListm.add(tString);
			}
			else if(parse<=1200 && parse<1700) {
				obsLista.add(tString);
			}
			else if(parse>=1700 && parse<2100) {
				obsListe.add(tString);
			}
			else {
				obsListn.add(tString);
			}
		}
		ListView<String> listm = new ListView<String>(obsListm);
		ListView<String> lista = new ListView<String>(obsLista);
		ListView<String> liste = new ListView<String>(obsListe);
		ListView<String> listn = new ListView<String>(obsListn);
		Text t = new Text();
		t.setText("Printable");
		t.setFont(new Font(20));
		t.setLayoutX(200);
		t.setLayoutY(45);
		back.setText("Back");
		back.setLayoutX(20);
		back.setLayoutY(20);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GUI.home(xStage);
			}
		});
		Rectangle rec = new Rectangle(0, 0, 500, 60);
		AnchorPane root = new AnchorPane(rec);
		Text morning = new Text("Morning");
		morning.setLayoutX(40);
		morning.setLayoutY(70);
		Text afternoon = new Text("Afternoon");
		afternoon.setLayoutX(290);
		afternoon.setLayoutY(70);
		Text evening = new Text("Evening");
		evening.setLayoutX(40);
		evening.setLayoutY(210);
		Text night = new Text("Night");
		night.setLayoutX(290);
		night.setLayoutY(210);
		Text time1 = new Text(40, 90, "Time");
		Text time2 = new Text(290, 90, "Time");
		Text time3 = new Text(40, 230, "Time");
		Text time4 = new Text(290, 230, "Time");
		Text med1 = new Text(80, 90, "Medication");
		Text med2 = new Text(330, 90, "Medication");
		Text med3 = new Text(80, 230, "Medication");
		Text med4 = new Text(330, 230, "Medication");
		Text amount1 = new Text(160, 90, "Amount");
		Text amount2 = new Text(410, 90, "Amount");
		Text amount3= new Text(160, 230, "Amount");
		Text amount4 = new Text(410, 230, "Amount");
		ScrollPane panem = new ScrollPane(listm);
		listm.setPrefSize(200, 40);
		listm.setLayoutX(40);
		listm.setLayoutY(125);
		ScrollPane panea = new ScrollPane(lista);
		lista.setPrefSize(200, 40);
		lista.setLayoutX(290);
		lista.setLayoutY(125);
		ScrollPane panee = new ScrollPane(liste);
		liste.setPrefSize(200, 40);
		liste.setLayoutX(40);
		liste.setLayoutY(265);
		ScrollPane panen = new ScrollPane(listn);
		listn.setPrefSize(200, 40);
		listn.setLayoutX(290);
		listn.setLayoutY(265);
		CheckBox check1 = new CheckBox();
		check1.setLayoutX(230);
		check1.setLayoutY(90);
		CheckBox check2 = new CheckBox();
		check2.setLayoutX(480);
		check2.setLayoutY(90);
		CheckBox check3 = new CheckBox();
		check3.setLayoutX(230);
		check3.setLayoutY(230);
		CheckBox check4 = new CheckBox();
		check4.setLayoutX(480);
		check4.setLayoutY(230);
		rec.setFill(Color.ORANGERED);
		root.getChildren().add(back);
		root.getChildren().add(t);
		root.getChildren().add(morning);
		root.getChildren().add(afternoon);
		root.getChildren().add(evening);
		root.getChildren().add(night);
		root.getChildren().add(time1);
		root.getChildren().add(time2);
		root.getChildren().add(time3);
		root.getChildren().add(time4);
		root.getChildren().add(med1);
		root.getChildren().add(med2);
		root.getChildren().add(med3);
		root.getChildren().add(med4);
		root.getChildren().add(amount1);
		root.getChildren().add(amount2);
		root.getChildren().add(amount3);
		root.getChildren().add(amount4);
		root.getChildren().add(check1);
		root.getChildren().add(check2);
		root.getChildren().add(check3);
		root.getChildren().add(check4);
		root.getChildren().add(listm);
		root.getChildren().add(lista);
		root.getChildren().add(liste);
		root.getChildren().add(listn);
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
		times.setText("Number of times per day");
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
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time));
					}
					else if(dateSetting==1) {
					filer.saveToFile(new Medication(name, dose, urgency, timeSetting, dateSetting, dayz, time));
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
		t.setText("Add Exercise");
		TextField field = new TextField();
		field.setLayoutX(20);
		field.setLayoutY(140);
		field.setText("Type in an exercise");
		TextField urgency = new TextField();
		urgency.setLayoutX(20);
		urgency.setLayoutY(170);
		urgency.setText("Type in the urgency as a number");
		TextField timing = new TextField();
		timing.setLayoutX(340);
		timing.setLayoutY(140);
		timing.setText("Enter the time for the exercise");
		TextField reps = new TextField();
		reps.setLayoutX(340);
		reps.setLayoutY(170);
		reps.setText("Enter the number of reps");
		TextField days = new TextField();
		days.setLayoutX(340);
		days.setLayoutY(200);
		days.setText("Enter the days separated by ;");
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//filer.saveToFile();
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
		root.getChildren().add(urgency);
		root.getChildren().add(timing);
		root.getChildren().add(reps);
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
		field.setLayoutY(100);
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//filer.saveToFile();
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
		field.setLayoutY(100);
		Button save = new Button();
		save.setText("Save");
		save.setLayoutX(440);
		save.setLayoutY(20);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//filer.saveToFile();
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
		xStage.setScene(new Scene(root, 500, 350));
		xStage.show();
	}
}
