package pkg;

import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.*;

/*
 * UML:
 * Time
 * 
 * - hours : int
 * - minutes : int
 * 
 * + Time()
 * + Time(int h, int m)
 * + Time(String h)
 * 
 * + getHours() : int
 * + setHours(int h) : void
 * + getMinutes() : int
 * + setHours(int m) : void
 * 
 */

public class Time implements Serializable {

	private static final long serialVersionUID = -1558032971213082568L;
	
	private int hours;
	private int minutes;
	
	
	// constructors
	/**
	 * 
	 */
	public Time() {
		this.hours = 0;
		this.minutes = 0;
	}
	
	public Time(int m) throws Exception {
		setMinutes(m);
	}
	
	public Time(int h, int m) throws Exception {
		setHours(h);
		setMinutes(m); // handles minutes > 59
	}
	
	public Time(String hoursMinutes) throws Exception {
		int h, m;
		String option1 = "\\d\\d:\\d\\d";
		String option2 = "\\d\\d[Hh]\\d\\d[Mm]";
		
		if (Pattern.matches(option1, hoursMinutes)) {
			h = Integer.parseInt(hoursMinutes.substring(0, 2));
			m = Integer.parseInt(hoursMinutes.substring(3, 5));
		}
		else if (Pattern.matches(option2, hoursMinutes)) {
			h = Integer.parseInt(hoursMinutes.substring(0, 2));
			m = Integer.parseInt(hoursMinutes.substring(3, 5));
		}
		else {
			throw new Exception("INVALID_TIME_STRING - [ " + hoursMinutes + " ]");
		}
		
		setHours(h);
		setMinutes(m); // handles minutes > 59
	}
	
	
	
	// getters and setters
	
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) throws Exception {
		if (hours < 0) {
			throw new Exception("BAD_TIME_HOURS");
		}
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) throws Exception {
		if (minutes < 0) {
			throw new Exception("BAD_TIME_MINUTES");
		}
		
		// appropriately update hours if minutes > 59
		this.hours += minutes / 60; // integer division to get hours
		this.minutes = minutes % 60; // get leftover minutes
	}
	
	
	// other methods
	public int inMinutes() {
		return (60 * this.hours) + minutes; 
	}
	
	@Override
	public String toString() {
		// return "X hours, X minutes" with proper grammar for 1's (i.e. 1 hour, 2 hours)
		String h = this.hours + ( (this.hours == 1) ? " hour, " : " hours, ");
		String m = this.minutes + ( (this.minutes == 1) ? " minute" : " minutes");
		return h + m;
	}

	
	// like String.compareTo()
	public int compareTo(Time t) {
		if (this.inMinutes() == t.inMinutes()) {
			return 0;
		}
		// > 0 if this is greater than t; else < 0 if this is less than t
		else {
			return this.inMinutes() - t.inMinutes();
		}
	}
	
	// like String.equals()
	public boolean equals(Time t) {
		if (this.inMinutes() == t.inMinutes()) {
			return true;
		}
		else {
			return false;
		}
	}
	

}
