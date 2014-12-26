package sqliteDataBase.Model;

import java.util.Date;

public class Charge {
	private int id;
	private int year,month,day;
	//private String time;
	private Double sum;
	private int type;//0 输入，1输出
	private String des;
	
	public Charge(int id,int year,int month,int day,Double sum,int type,String des){
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		 this.sum = sum;
		 this.type = type;
		 this.des = des;
	}
	
	public Charge(int year,int month,int day,Double sum,int type,String des){
		this.year = year;
		this.month = month;
		this.day = day;
		 this.sum = sum;
		 this.type = type;
		 this.des = des;
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

	public String getDataStr(){
		return String.format("%04d", year)+"年"+String.format("%02d", month)+"月"+String.format("%02d", day)+"日";
	}
	public Double getSum(){
		return sum;
	}
	public void setSum(Double sum){
		this.sum = sum;
	}
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type = type;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		this.des = des;
	}
}
