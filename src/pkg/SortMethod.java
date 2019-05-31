/**
 * 
 */
package pkg;

/**
 * @author Nicholas Brunet
 *
 */
enum SortMethod {
	TITLE("Title"), 
	DIRECTOR("Director"), 
	GENRE("Genre"), 
	YEAR("Year"), 
	TIME("Runtime");
	
	private String searchMethodName;
	
	// constructor
	private SortMethod(String s) {
		this.searchMethodName = s;
	}
	
	@Override
	public String toString() {
		return searchMethodName;
	}
	
	
	// from Stack Overflow
	// returns a string of values 
	public static String[] getNames() {
	    SortMethod[] values = values();
	    String[] names = new String[values.length];

	    for (int i = 0; i < values.length; i++) {
	        names[i] = values[i].toString();
	    }

	    return names;
	}
	
}
