package sqliteDataBase.Model;

import java.util.Date;

public class ChargeStatistic {
	private int id;
	private int year, month, day;
	private Double inSum;
	private Double outSum;
	
	public ChargeStatistic(int id,int year,int month,int day,Double inSum,Double outSum){
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		this.inSum = inSum;
		this.outSum = outSum;
	}
	
	public ChargeStatistic(int year,int month,int day,Double inSum,Double outSum){
		this.year = year;
		this.month = month;
		this.day = day;
		 this.inSum = inSum;
		 this.outSum = outSum;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
//	public Date getDate(){
//		return date;
//	}
//	public void setDate(Date date){
//		this.date = date;
//	}
	public String getDataStr(){
		return String.format("%04d", year)+"��"+String.format("%02d", month)+"��"+String.format("%02d", day)+"��";
	}
	public Double getInSum(){
		return inSum;
	}
	public void setInSum(Double inSum){
		this.inSum = inSum;
	}
	public Double getOutSum(){
		return outSum;
	}
	public void setOutSum(Double outSum){
		this.outSum = outSum;
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
	
}
