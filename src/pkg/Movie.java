package pkg;

import java.io.Serializable;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * UML:
 * Movie
 * 
 * - title : String
 * - director : String
 * - genre : String
 * - year : int
 * - time : Time
 * 
 * + Movie()
 * + Movie(String title, String director, String genre, int year, Time length)
 * 
 * + getTitle() : String
 * + setTitle(String t) : void
 * + getDirector() : String
 * + setDirector(String d) : void
 * + getGenre() : String
 * + setGenre(String g) : void
 * + getYear() : int
 * + setYear(int y) : void
 * + getLength() : Time
 * + setLength(Time l) : void
 */


/**
 * @author Nicholas Brunet
 *
 */
public class Movie implements Serializable {

	private static final long serialVersionUID = -6978766134739867659L;
	
	// Fields
	private String title;
	private String director;
	private String genre;
	private int year;
	private Time time;
	private transient int id; // transient: don't serialize this
	private static int numMovies = 0;
	
	// Constructors
	
	public Movie() {
		this.title = "";
		this.director = "";
		this.genre = "";
		this.year = 0;
		this.time = new Time();
		this.id = numMovies;
		++numMovies;
	}
	
	public Movie(String title, String director, String genre, int year, Time length) throws Exception {
		this.setTitle(title);
		this.setDirector(director);
		this.setGenre(genre);
		this.setYear(year);
		this.setTime(length);
		this.id = numMovies;
		++numMovies;
	}
	
	
	
	// getters and setters
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) throws Exception {
		if (! title.equals("")) {
			this.title = title;
		}
		else {
			throw new Exception("EMPTY_TITLE");
		}
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) throws Exception {
		if (! director.equals("")) {
			this.director = director;
		}
		else {
			throw new Exception("EMPTY_DIRECTOR");
		}
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) throws Exception {
		if (! genre.equals("")) {
			this.genre = genre;
		}
		else {
			throw new Exception("EMPTY_GENRE");
		}
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) throws Exception {
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		
		if (year < 1880) {
			throw new Exception("BAD_DATE_EARLY"); // TODO UNCOMMENT
		}
		else if (year > now.get(Calendar.YEAR)) {
			throw new Exception("BAD_DATE_LATE"); // TODO UNCOMMENT
		}
		
		this.year = year;
	}
	
	public Time getTime() {
		return time;
	}
	
	public int getHours() {
		return time.getHours();
	}
	
	public int getMinutes() {
		return time.getMinutes();
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	// other methods
	
	public String toString() {
		return getTitle() + " (" + getYear() + ")";
	}


}
