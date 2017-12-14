package support;

public class Medication {
private String name;
private String doseage;
private String urgencyReturn;
private int urgencyState; //1: Low, 2: Medium, 3: High,
private int timeSetting; //1: Specific time, 2: Multiple times
private int dateSetting; //1: Daily, 2: Specific days of week
private int[] selectDays;
private int daily;
private int specificTime;
private int timeIntervals;
private int[] multipleTimes;
private String mode;
	public Medication(String xName, String xDoseage, int xUrgency, int xTimeSetting, int xDateSetting,
	String xDays, String xTimes, int xMode) {
		name=xName;
		doseage=xDoseage;
		urgencyState=xUrgency;
		if(xUrgency==1) {
			urgencyReturn="Low";
		}
		else if(xUrgency==2) {
			urgencyReturn="Medium";
		}
		else {
			urgencyReturn="High";
		}
		timeSetting=xTimeSetting;
		dateSetting=xDateSetting;
		//*************************
		if(timeSetting==1) specificTime=Integer.parseInt(xTimes);
		if(timeSetting==2) {
			String[] temp=xTimes.split("\\;");
			multipleTimes=new int[temp.length];
			for(int i=0;i<multipleTimes.length;i++) {
				multipleTimes[i]=Integer.parseInt(temp[i]);
			}
		}
		if(dateSetting==1) daily=1;
		if(dateSetting==2) {
			String[] temp=xDays.split(";");
			selectDays=new int[temp.length];
			for(int i=0;i<selectDays.length;i++) {
				selectDays[i]=Integer.parseInt(temp[i]);
			}
		}
		if(xMode==1) {
			mode="Med:  ";
		}
		else if(xMode==2) {
			mode="Exr:  ";
		}
		else if(xMode==3) {
			mode="Read:  ";
		}
		else {
			mode="Oth:  ";
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
	public int getDayInterval() {
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
	public String getMode() {
		return mode;
	}
	public String toString() {
		String temp;
		if(specificTime%60<10) {
			temp=mode+specificTime/60+":"+"0"+(specificTime%60)+"   "+name+"   "+doseage+"   "+urgencyReturn;
		}
		else {
			temp=mode+specificTime/60+":"+(specificTime%60)+"   "+name+"   "+doseage+"   "+urgencyReturn;
		}
		return temp;
	}
	public String toString(int referenceNum) {
		String temp;
		if(specificTime%60<10) {
			temp=mode+referenceNum/60+":"+"0"+(referenceNum%60)+"   "+name+"   "+doseage+"   "+urgencyReturn;
		}
		else {
			temp=mode+referenceNum/60+":"+(referenceNum%60)+"   "+name+"   "+doseage+"   "+urgencyReturn;
		}
		return temp;
	}
}