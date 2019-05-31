/*
 * Nicholas Brunet
 * CIS 111
 * 
 * Final Project
 * Due 5/30/19
 * 
 */

package pkg;

import java.awt.EventQueue;

public class Main {
	public static MainWindow window;
	public static MovieDetails newMovieWindow;
	public static MovieDetails editMovieWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
