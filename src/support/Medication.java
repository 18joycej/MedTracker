package support;

public class Medication {
private String name;
private String doseage;
private int urgencyState; //1: Low, 2: Medium, 3: High,
private int timeSetting; //1: Specific time, 2: Multiple times, 3: Time intervals
private int dateSetting; //1: Daily, 2: Specific days of week, 3: Day Intervals, 4: Time intervals
private String[] selectDays;
private int dayInterval;
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
		if(timeSetting==1) specificTime=Integer.parseInt(xTimes);
		if(timeSetting==2) {
			String[] temp=xTimes.split(";");
			multipleTimes=new int[temp.length];
			for(int i=0;i<multipleTimes.length;i++) {
				multipleTimes[i]=Integer.parseInt(temp[i]);
			}
		}
		if(timeSetting==3) timeIntervals=Integer.parseInt(xTimes);
		if(dateSetting==1) dayInterval=1;
		if(dateSetting==2) selectDays=xDays.split(";");
		if(dateSetting==3) dayInterval=Integer.parseInt(xDays);
	}
}
