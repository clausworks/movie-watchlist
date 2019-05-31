package pkg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import java.awt.Insets;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class MovieDetails extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldTitle;
	private JTextField textFieldDirector;
	private JTextField textFieldGenre;
	private JTextField textFieldYear;
	
	Calendar now = Calendar.getInstance();
	private final int THIS_YEAR = now.get(Calendar.YEAR);


	/**
	 * Create the dialog.
	 */
	public MovieDetails() {
		// new movie
		this(new Movie());
	}
	
	public MovieDetails(Movie movie) {
	
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowOpened(WindowEvent e) {
		    	// dim buttons
		    	Main.window.btnEdit.setEnabled(false);
		    	Main.window.btnNewMovie.setEnabled(false);
		    	Main.window.btnDeleteMovie.setEnabled(false);
		    	// disable controls
		    	Main.window.frame.setEnabled(false);
		    }
			
			@Override
		    public void windowClosed(WindowEvent e) {
				// enable controls
				Main.window.frame.setEnabled(true);
				// un-dim buttons
		    	Main.window.btnEdit.setEnabled(true);
		    	Main.window.btnNewMovie.setEnabled(true);
		    	Main.window.btnDeleteMovie.setEnabled(true);
		    }
		});
		
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);

		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPanel.add(lblName, gbc_lblName);


		textFieldTitle = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.gridwidth = 4;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		contentPanel.add(textFieldTitle, gbc_textFieldName);
		textFieldTitle.setColumns(10);
		textFieldTitle.setText(movie.getTitle());


		JLabel lblDirector_1 = new JLabel("Director:");
		GridBagConstraints gbc_lblDirector_1 = new GridBagConstraints();
		gbc_lblDirector_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDirector_1.anchor = GridBagConstraints.EAST;
		gbc_lblDirector_1.gridx = 0;
		gbc_lblDirector_1.gridy = 1;
		contentPanel.add(lblDirector_1, gbc_lblDirector_1);


		textFieldDirector = new JTextField();
		GridBagConstraints gbc_textFieldDirector = new GridBagConstraints();
		gbc_textFieldDirector.gridwidth = 4;
		gbc_textFieldDirector.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDirector.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDirector.gridx = 1;
		gbc_textFieldDirector.gridy = 1;
		contentPanel.add(textFieldDirector, gbc_textFieldDirector);
		textFieldDirector.setColumns(10);
		textFieldDirector.setText(movie.getDirector());


		JLabel lblDirector = new JLabel("Genre:");
		GridBagConstraints gbc_lblDirector = new GridBagConstraints();
		gbc_lblDirector.anchor = GridBagConstraints.EAST;
		gbc_lblDirector.insets = new Insets(0, 0, 5, 5);
		gbc_lblDirector.gridx = 0;
		gbc_lblDirector.gridy = 2;
		contentPanel.add(lblDirector, gbc_lblDirector);


		textFieldGenre = new JTextField();
		GridBagConstraints gbc_textFieldGenre = new GridBagConstraints();
		gbc_textFieldGenre.gridwidth = 4;
		gbc_textFieldGenre.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldGenre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldGenre.gridx = 1;
		gbc_textFieldGenre.gridy = 2;
		contentPanel.add(textFieldGenre, gbc_textFieldGenre);
		textFieldGenre.setColumns(10);
		textFieldGenre.setText(movie.getGenre());


		JLabel lblYear = new JLabel("Year:");
		GridBagConstraints gbc_lblYear = new GridBagConstraints();
		gbc_lblYear.anchor = GridBagConstraints.EAST;
		gbc_lblYear.insets = new Insets(0, 0, 5, 5);
		gbc_lblYear.gridx = 0;
		gbc_lblYear.gridy = 3;
		contentPanel.add(lblYear, gbc_lblYear);


		textFieldYear = new JTextField();
		GridBagConstraints gbc_textFieldYear = new GridBagConstraints();
		gbc_textFieldYear.gridwidth = 4;
		gbc_textFieldYear.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldYear.gridx = 1;
		gbc_textFieldYear.gridy = 3;
		contentPanel.add(textFieldYear, gbc_textFieldYear);
		textFieldYear.setColumns(10);
		textFieldYear.setText(( (Integer)movie.getYear() ).toString());

		JLabel lblRuntime = new JLabel("Runtime:");
		GridBagConstraints gbc_lblRuntime = new GridBagConstraints();
		gbc_lblRuntime.anchor = GridBagConstraints.EAST;
		gbc_lblRuntime.insets = new Insets(0, 0, 5, 5);
		gbc_lblRuntime.gridx = 0;
		gbc_lblRuntime.gridy = 4;
		contentPanel.add(lblRuntime, gbc_lblRuntime);


		JSpinner spinnerHrs = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
		GridBagConstraints gbc_spinnerHrs = new GridBagConstraints();
		gbc_spinnerHrs.anchor = GridBagConstraints.WEST;
		gbc_spinnerHrs.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerHrs.gridx = 1;
		gbc_spinnerHrs.gridy = 4;
		contentPanel.add(spinnerHrs, gbc_spinnerHrs);
		spinnerHrs.setValue((Integer)movie.getTime().getHours());
		// set size of spinner field; from Stack Overflow
		JComponent field_1 = ((JSpinner.DefaultEditor) spinnerHrs.getEditor());
		Dimension prefSize_1 = field_1.getPreferredSize();
		prefSize_1 = new Dimension(50, prefSize_1.height);
		field_1.setPreferredSize(prefSize_1);

		
		JLabel lblNewLabel = new JLabel("hours");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 4;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);

												
		JSpinner spinnerMins = new JSpinner(new SpinnerNumberModel(0, 0, 600, 1));// default, min, max, step
		GridBagConstraints gbc_spinnerMins = new GridBagConstraints();
		gbc_spinnerMins.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMins.gridx = 3;
		gbc_spinnerMins.gridy = 4;
		contentPanel.add(spinnerMins, gbc_spinnerMins);
		spinnerMins.setValue((Integer)movie.getTime().getMinutes());
		// set size of spinner field; from Stack Overflow
		JComponent field_2 = ((JSpinner.DefaultEditor) spinnerMins.getEditor());
		Dimension prefSize_2 = field_2.getPreferredSize();
		prefSize_2 = new Dimension(50, prefSize_2.height);
		field_2.setPreferredSize(prefSize_2);


		JLabel lblNewLabel_1 = new JLabel("minutes");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 4;
		contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		
		

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);


		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					// get user input
					// strings - nothing special
					movie.setTitle(textFieldTitle.getText());
					movie.setDirector(textFieldDirector.getText());
					movie.setGenre(textFieldGenre.getText());

					// year - integer
					int year = Integer.parseInt(textFieldYear.getText());
					movie.setYear(year); // may throw exception if year is out of proper range
					
					// time
					movie.setTime(new Time((Integer)spinnerHrs.getValue(), (Integer)spinnerMins.getValue()));
					
					Main.window.addMovie(movie);
					
					dispose();

				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "The year must be a number between 1880 and " + THIS_YEAR + ".");
				}
				catch (Exception e) {
					if (e.getMessage().equals("BAD_DATE_EARLY")) {
						JOptionPane.showMessageDialog(null, "Earlist date is 1880.");
					}
					else if (e.getMessage().equals("BAD_DATE_LATE")) {
						JOptionPane.showMessageDialog(null, "Latest date is " + THIS_YEAR + ".");
					}
					else if (e.getMessage().equals("BAD_TIME_HOURS")) {
						JOptionPane.showMessageDialog(null, "Hours can't be negative.");
					}
					else if (e.getMessage().equals("BAD_TIME_MINUTES")) {
						JOptionPane.showMessageDialog(null, "MESSAGE");
					}
					else if (e.getMessage().equals("EMPTY_TITLE")) {
						JOptionPane.showMessageDialog(null, "Enter a title.");
					}
					else if (e.getMessage().equals("EMPTY_DIRECTOR")) {
						JOptionPane.showMessageDialog(null, "Enter a director.");
					}
					else if (e.getMessage().equals("EMPTY_GENRE")) {
						JOptionPane.showMessageDialog(null, "Enter a genre.");
					}
					else {
						System.out.println("UNKNOWN ERROR:");
						e.printStackTrace();
					}
				}
				
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		
	}

}
