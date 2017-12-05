package support;

public class Medication {
private String name;
private String doseage;
private int urgencyState; //1: Low, 2: Medium, 3: High,
private int timeSetting; //1: Specific time, 2: Multiple times
private int dateSetting; //1: Daily, 2: Specific days of week
private int[] selectDays;
private boolean daily;
private int specificTime;
private int timeIntervals;
private int[] multipleTimes;
	public Medication(String xName, String xDoseage, int xUrgency, int xTimeSetting, int xDateSetting,
	String xDays, String xTimes) {
		name=xName;
		doseage=xDoseage;
		urgencyState=xUrgency;
		timeSetting=xTimeSetting;
		dateSetting=xDateSetting;
		//*************************
		if(timeSetting==1) specificTime=Integer.parseInt(xTimes);
		if(timeSetting==2) {
			String[] temp=xTimes.split(";");
			multipleTimes=new int[temp.length];
			for(int i=0;i<multipleTimes.length;i++) {
				multipleTimes[i]=Integer.parseInt(temp[i]);
			}
		}
		if(dateSetting==1) daily=true;
		if(dateSetting==2) {
			String[] temp=xDays.split(";");
			selectDays=new int[temp.length];
			for(int i=0;i<selectDays.length;i++) {
				selectDays[i]=Integer.parseInt(temp[i]);
			}
		}
	}
	public String getName() {
		return name;
	}
	public String getDoseage() {
		return doseage;
	}
	public int getUrgency() {
		return urgencyState;
	}
	public int getTimeSetting() {
		return timeSetting;
	}
	public int getDateSetting() {
		return dateSetting;
	}
	public int[] getSelectDays() {
		return selectDays;
	}
	public boolean getDayInterval() {
		return daily;
	}
	public int getSpecificTime() {
		return specificTime;
	}
	public int getTimeIntervals() {
		return timeIntervals;
	}
	public int[] getMultipleTimes() {
		return multipleTimes;
	}
}