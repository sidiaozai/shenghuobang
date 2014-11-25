package com.example.shenghuobang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	// �ַ�����������ת����date����
	public static Date strToDate(String style, String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String dateToStr(String style, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		return formatter.format(date);
	}
}
