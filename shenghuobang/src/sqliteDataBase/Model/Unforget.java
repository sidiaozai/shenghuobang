package sqliteDataBase.Model;

import java.util.Date;

public class Unforget {
	private int id;
	//private String time;
	private int year,month,day;
	private int hour,minute,second;
	private String name;
	private String soundFileName;
	
	public Unforget(int year,int month,int day,int hour,int minute,int second,String name, String soundFileName){
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.name = name;
		this.soundFileName = soundFileName;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public int getYear(){
		return year;
	}
	public void setYear(int year){
		this.year = year;
	}
	
	public int getMonth(){
		return month;
	}
	public void setMonth(int month){
		this.month = month;
	}
	
	public int getDay(){
		return day;
	}
	public void setDay(int day){
		this.day = day;
	}
	
	public int getHour(){
		return hour;
	}
	public void setHour(int hour){
		this.hour = hour;
	}
	
	public int getMinute(){
		return this.minute;
	}
	public void setMinute(int minute){
		this.minute = minute;
	}
	public int getSecond(){
		return this.second;
	}
	public void setSecond(int second){
		this.second = second;
	} 
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getSoundFileName(){
		return soundFileName;
	}
	public void setSoundFileName(String soundFileName){
		this.soundFileName = soundFileName;
	}
	

}
