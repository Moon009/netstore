package com.cqut.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdGenertor {
	public static String genOrdersNum(){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String s = df.format(date);
		return s+System.nanoTime();
	}
}
