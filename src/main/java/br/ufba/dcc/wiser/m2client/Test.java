package br.ufba.dcc.wiser.m2client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
	
	public static void main(String args[]) {
		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar data = Calendar.getInstance();
		
		System.out.println("Date GetTime: " + form.format(data.getTime()));
		
	}

}
