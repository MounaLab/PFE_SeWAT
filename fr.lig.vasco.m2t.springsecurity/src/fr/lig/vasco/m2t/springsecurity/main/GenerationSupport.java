package fr.lig.vasco.m2t.springsecurity.main;

import java.sql.Timestamp;

public class GenerationSupport {
	
	public String getCurrentTime(){
	    java.util.Date date = new java.util.Date();
	    Timestamp ts = new Timestamp(date.getTime());
	    return ts.toString();
	}
}
