
/*
 * TODO LIST
 * - double click to edit
 * - figure out why previous item is still "selected" afer rebuilding JTree
 */

package pkg;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JButton;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;

public class MainWindow {

	// window components
	public JFrame frame;
	public JLabel lblSortBy;
	public JComboBox comboBox;
	public JScrollPane scrollPane;
	public JTree tree;
	public JPanel infoPanel;
	public JLabel lblTitle_value;
	public JLabel lblDirector;
	public JLabel lblGenre;
	public JLabel lblRuntime;
	public JButton btnEdit;
	public JLabel lblDirector_value;
	public JLabel lblGenre_value;
	public JLabel lblRuntime_value;
	public JPanel movieButtonsPanel;
	public JButton btnNewMovie;
	public JButton btnDeleteMovie;

	// movie collection items
	private int numMovies;
	private final int MAX_MOVIES = 100;
	private final String MOVIE_COLLECTION_NAME = "My Movies";
	private final String MOVIE_COLLECTION_FILENAME = "movies.ser";
	private Movie[] movieCollection = new Movie[MAX_MOVIES];

	// tree items
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	private DefaultTreeSelectionModel selectionModel;

	// Random number generator for anything
	private Random generator = new Random();

	/**
	 * Create the application.
	 */
	public MainWindow() throws Exception {
		readMovieCollectionFromFile(MOVIE_COLLECTION_FILENAME);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setMinimumSize(new Dimension(400, 250));
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					writeMovieCollectionToFile(MOVIE_COLLECTION_FILENAME);
				}
				catch (Exception err) {
					err.printStackTrace();
				}
			}
		});

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{57, 259, 274, 0};
		gridBagLayout.rowHeights = new int[]{27, 262, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		lblSortBy = new JLabel("Sort by: ");
		GridBagConstraints gbc_lblSortBy = new GridBagConstraints();
		gbc_lblSortBy.anchor = GridBagConstraints.WEST;
		gbc_lblSortBy.insets = new Insets(5, 5, 5, 5);
		gbc_lblSortBy.gridx = 0;
		gbc_lblSortBy.gridy = 0;
		frame.getContentPane().add(lblSortBy, gbc_lblSortBy);
		
		

		// TODO parameterize JComboBox with <SortMethod>
		comboBox = new JComboBox(SortMethod.values());
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = -1;
				
				// get id of selected movie
//				if (getSelectedMovie() != null) {
//					id = getSelectedMovie().getID();
//				}
//				else {
//					System.out.println("NO SELECTION");
//				}
				
				// sort and update jTree
				sortMovieCollection( (SortMethod)comboBox.getSelectedItem() );	
				rebuildJTree();
				
				// select same movie
//				if (id != -1) {
//					System.out.println(getMoviePath(id));
//					tree.setSelectionPath(getMoviePath(id));
//				}
//				else {
//					System.out.println("NULL PATH");
//				}
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(5, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		frame.getContentPane().add(comboBox, gbc_comboBox);


		// START JTREE CODE

		root = new DefaultMutableTreeNode(MOVIE_COLLECTION_NAME + " (" + numMovies + ")");
		buildJTreeModel(root, movieCollection);

		treeModel = new DefaultTreeModel(root);
		treeModel.setRoot(root);

		selectionModel = new DefaultTreeSelectionModel();
		tree = new JTree(treeModel);
		tree.setSelectionModel(selectionModel);
		tree.setExpandsSelectedPaths(true);

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // only allow one selection at a time

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			// node on tree selected
			public void valueChanged(TreeSelectionEvent e) {

				// get selected node
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

				// a node is selected
				try {
					// is a movie
					if (node.getUserObject() instanceof Movie) {
						btnDeleteMovie.setEnabled(true);
						btnEdit.setEnabled(true);
						Movie m = (Movie)node.getUserObject();
//						lblTitle_value.setText("[" + m.getID() + "]" + m.getTitle() + " (" + m.getYear() + ")"); // DEBUGGIN PURPOSES ONLY
						lblTitle_value.setText(m.getTitle() + " (" + m.getYear() + ")");
						lblDirector_value.setText(m.getDirector());
						lblGenre_value.setText(m.getGenre());
						lblRuntime_value.setText(m.getTime().toString()); 
						// is a header/root
					}
					else {
						btnDeleteMovie.setEnabled(false);
						btnEdit.setEnabled(false);
						lblTitle_value.setText("N/A");
						lblDirector_value.setText("N/A");
						lblGenre_value.setText("N/A");
						lblRuntime_value.setText("N/A");
					}
				}
				// no node is selected
				catch (NullPointerException err) {
					// disable delete button
					btnDeleteMovie.setEnabled(false);
					btnEdit.setEnabled(false);
				}
			}
		});
		
		// remove icons from tree
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);

		// END JTREE CODE


		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		scrollPane.setViewportView(tree);

		infoPanel = new JPanel();
		GridBagConstraints gbc_infoPanel = new GridBagConstraints();
		gbc_infoPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_infoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_infoPanel.gridx = 2;
		gbc_infoPanel.gridy = 1;
		frame.getContentPane().add(infoPanel, gbc_infoPanel);
		GridBagLayout gbl_infoPanel = new GridBagLayout();
		gbl_infoPanel.columnWidths = new int[]{73, 54, 0};
		gbl_infoPanel.rowHeights = new int[]{14, 14, 14, 14, 14, 23, 0};
		gbl_infoPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		infoPanel.setLayout(gbl_infoPanel);

		lblTitle_value = new JLabel("");
		lblTitle_value.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblTitle_value = new GridBagConstraints();
		gbc_lblTitle_value.gridwidth = 2;
		gbc_lblTitle_value.anchor = GridBagConstraints.NORTH;
		gbc_lblTitle_value.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle_value.gridx = 0;
		gbc_lblTitle_value.gridy = 1;
		infoPanel.add(lblTitle_value, gbc_lblTitle_value);

		lblDirector = new JLabel("Director: ");
		GridBagConstraints gbc_lblDirector = new GridBagConstraints();
		gbc_lblDirector.anchor = GridBagConstraints.EAST;
		gbc_lblDirector.insets = new Insets(0, 0, 5, 5);
		gbc_lblDirector.gridx = 0;
		gbc_lblDirector.gridy = 2;
		infoPanel.add(lblDirector, gbc_lblDirector);

		lblDirector_value = new JLabel("");
		GridBagConstraints gbc_lblDirector_value = new GridBagConstraints();
		gbc_lblDirector_value.anchor = GridBagConstraints.WEST;
		gbc_lblDirector_value.insets = new Insets(0, 0, 5, 0);
		gbc_lblDirector_value.gridx = 1;
		gbc_lblDirector_value.gridy = 2;
		infoPanel.add(lblDirector_value, gbc_lblDirector_value);

		lblGenre = new JLabel("Genre: ");
		GridBagConstraints gbc_lblGenre = new GridBagConstraints();
		gbc_lblGenre.anchor = GridBagConstraints.EAST;
		gbc_lblGenre.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenre.gridx = 0;
		gbc_lblGenre.gridy = 3;
		infoPanel.add(lblGenre, gbc_lblGenre);

		lblGenre_value = new JLabel("");
		GridBagConstraints gbc_lblGenre_value = new GridBagConstraints();
		gbc_lblGenre_value.anchor = GridBagConstraints.WEST;
		gbc_lblGenre_value.insets = new Insets(0, 0, 5, 0);
		gbc_lblGenre_value.gridx = 1;
		gbc_lblGenre_value.gridy = 3;
		infoPanel.add(lblGenre_value, gbc_lblGenre_value);

		lblRuntime = new JLabel("Runtime: ");
		GridBagConstraints gbc_lblRuntime = new GridBagConstraints();
		gbc_lblRuntime.anchor = GridBagConstraints.EAST;
		gbc_lblRuntime.insets = new Insets(0, 0, 5, 5);
		gbc_lblRuntime.gridx = 0;
		gbc_lblRuntime.gridy = 4;
		infoPanel.add(lblRuntime, gbc_lblRuntime);

		btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.newMovieWindow = new MovieDetails(getSelectedMovie());
				Main.newMovieWindow.setVisible(true);
			}
		});

		lblRuntime_value = new JLabel("");
		GridBagConstraints gbc_lblRuntime_value = new GridBagConstraints();
		gbc_lblRuntime_value.anchor = GridBagConstraints.WEST;
		gbc_lblRuntime_value.insets = new Insets(0, 0, 5, 0);
		gbc_lblRuntime_value.gridx = 1;
		gbc_lblRuntime_value.gridy = 4;
		infoPanel.add(lblRuntime_value, gbc_lblRuntime_value);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.gridwidth = 2;
		gbc_btnEdit.gridx = 0;
		gbc_btnEdit.gridy = 5;
		infoPanel.add(btnEdit, gbc_btnEdit);

		movieButtonsPanel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frame.getContentPane().add(movieButtonsPanel, gbc_panel);

		btnNewMovie = new JButton("New");
		btnNewMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.newMovieWindow = new MovieDetails(new Movie());
				Main.newMovieWindow.setVisible(true);
			}
		});
		movieButtonsPanel.add(btnNewMovie);

		btnDeleteMovie = new JButton("Delete");
		btnDeleteMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMovie(getSelectedMovie().getID());
			}
		});
		btnDeleteMovie.setEnabled(false);
		movieButtonsPanel.add(btnDeleteMovie);
		
		
		// down here so that the tree event handler can access the window elements
		tree.setSelectionRow(1); // first movie
	}




	// code to deal with JTree


	public void sortMovieCollection(SortMethod s) {

		switch(s) {

		case TITLE:
			for (int i = 0; i < numMovies; ++i) {
				int min = i;
				Movie temp;

				for (int j = i; j < numMovies; ++j) {
					if (movieCollection[j].getTitle().compareTo(movieCollection[min].getTitle()) < 0) {
						min = j;
					}
				}

				// swap minimum with ith element
				temp = movieCollection[i];
				movieCollection[i] = movieCollection[min];
				movieCollection[min] = temp;
			}
			break;


		case DIRECTOR:
			for (int i = 0; i < numMovies; ++i) {
				int min = i;
				Movie temp;

				for (int j = i; j < numMovies; ++j) {
					if (movieCollection[j].getDirector().compareTo(movieCollection[min].getDirector()) < 0) {
						min = j;
					}
				}

				// swap minimum with ith element
				temp = movieCollection[i];
				movieCollection[i] = movieCollection[min];
				movieCollection[min] = temp;
			}
			break;


		case GENRE:
			for (int i = 0; i < numMovies; ++i) {
				int min = i;
				Movie temp;

				for (int j = i; j < numMovies; ++j) {
					if (movieCollection[j].getGenre().compareTo(movieCollection[min].getGenre()) < 0) {
						min = j;
					}
				}

				// swap minimum with ith element
				temp = movieCollection[i];
				movieCollection[i] = movieCollection[min];
				movieCollection[min] = temp;
			}
			break;


		case YEAR:
			for (int i = 0; i < numMovies; ++i) {
				int min = i;
				Movie temp;

				for (int j = i; j < numMovies; ++j) {
					if (movieCollection[j].getYear() < movieCollection[min].getYear()) {
						min = j;
					}
				}

				// swap minimum with ith element
				temp = movieCollection[i];
				movieCollection[i] = movieCollection[min];
				movieCollection[min] = temp;
			}
			break;


		case TIME:
			for (int i = 0; i < numMovies; ++i) {
				int min = i;
				Movie temp;

				for (int j = i; j < numMovies; ++j) {
					if (movieCollection[j].getTime().compareTo(movieCollection[min].getTime()) < 0) {
						min = j;
					}
				}

				// swap minimum with ith element
				temp = movieCollection[i];
				movieCollection[i] = movieCollection[min];
				movieCollection[min] = temp;
			}
			break;


		default:
			System.out.println("DEFAULT");
			break;

		}

		//		System.out.println("\n\nSorted:");
		//		for (int i = 0; i < 5; ++i) {
		//			System.out.println(i + ": " + movieCollection[i].getTitle());
		//		}

	}



	// rebuild tree with a new model
	public void rebuildJTree() {
		DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(new String(MOVIE_COLLECTION_NAME + " (" + numMovies + ")"));
		buildJTreeModel(newRoot, movieCollection);
		treeModel.setRoot(newRoot);
		tree.setSelectionPath(null);
		btnEdit.setEnabled(false);
	}


	// build tree MODEL from array
	public void buildJTreeModel(DefaultMutableTreeNode root, Movie movies[]) {
		DefaultMutableTreeNode header = null;
		DefaultMutableTreeNode item = null;
		Object curHeader = null;
		int i = 0;

		switch (getSelectedSortMethod()) {
		case TITLE:
		case YEAR:
		case TIME:
			for (Movie m : movies) {
				if (m != null) {
					item = new DefaultMutableTreeNode(m);
					root.add(item);
				}
			}
			break;


		case DIRECTOR:

			curHeader = new String(movies[0].getDirector());
			i = 0;
			while (i < numMovies) {

				curHeader = movies[i].getDirector();
				header = new DefaultMutableTreeNode(null);

				// keep looping until the category is different
				while ( i < numMovies && movies[i].getDirector().equals(curHeader) ) {
					item = new DefaultMutableTreeNode(movies[i]);
					header.add(item);
					++i;
				}
				header.setUserObject(curHeader + " (" + header.getChildCount() + ")");
				root.add(header);

			}
			break;


		case GENRE:


			curHeader = new String(movies[0].getGenre());
			i = 0;
			while (i < numMovies) {

				curHeader = movies[i].getGenre();
				header = new DefaultMutableTreeNode(curHeader);

				// keep looping until the category is different
				while ( i < numMovies && movies[i].getGenre().equals(curHeader) ) {
					item = new DefaultMutableTreeNode(movies[i]);
					header.add(item);
					++i;
				}
				header.setUserObject(curHeader + " (" + header.getChildCount() + ")");
				root.add(header);

			}
			break;

		default:
			for (Movie m : movies) {
				if (m != null) {
					item = new DefaultMutableTreeNode(m);
					root.add(item);
				}
			}
			break;
		}
	}


	// get the sort method from the combobox
	public SortMethod getSelectedSortMethod() {
		try {
			return (SortMethod)comboBox.getSelectedItem();
		}
		catch (NullPointerException e) {
			return SortMethod.TITLE;
		}
	}


	public Movie getSelectedMovie() {
		// get selected node
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		// a node is selected
		try {
			// is a movie
			if (node.getUserObject() instanceof Movie) {
				return (Movie)node.getUserObject();
			}
			else {
				return null;
			}
		}
		catch (NullPointerException e) {
			return null;
		}
	}
	
	
	// get the TreePath to the movie with the given ID
	public TreePath getMoviePath(int id) {
		Enumeration<TreeNode> nodes = root.breadthFirstEnumeration();
		
		// get number of rows
		while (nodes.hasMoreElements()) {
			System.out.println(nodes.nextElement());
			DefaultMutableTreeNode node = ( (DefaultMutableTreeNode)nodes.nextElement() );
			Object obj = node.getUserObject();

			if (obj instanceof Movie) {
				if ( ((Movie)obj).getID() == id ) {
					return new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(node));
				}
			}
		}
		
		// if we're at this point, the movie wasn't found
		return null;
	}



	// write movie collection array to file
	public void writeMovieCollectionToFile(String filename) throws Exception {
		List<Movie> movieCollectionSerializable = null;
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;

		try {
			// initialize list and streams
			movieCollectionSerializable = new ArrayList<Movie>();
			fout = new FileOutputStream(filename);
			oout = new ObjectOutputStream(fout);

			// convert movie array to list to serialize
			for (Movie m : movieCollection) {
				if (m != null) {
					movieCollectionSerializable.add(m);
				}
			}

			// write list to file
			oout.writeObject(movieCollectionSerializable);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: could not open filestream.");
		}
		catch (IOException e) {
			System.out.println("Error: could not write movies to file.");
		}

		// close streams
		try {
			fout.flush();

			fout.close();
			oout.close();
		}
		catch (IOException e) {
			System.out.println("Error: could not close filestream or objectstream.");
			System.out.println("Message: [" + e.getMessage() + "]");
		}

	}


	// read movie collection array from file
	public void readMovieCollectionFromFile(String filename) throws Exception {
		List<?> movieCollectionSerializable = null;
		FileInputStream fin;
		ObjectInputStream oin;


		try {
			fin = new FileInputStream(filename);
			oin = new ObjectInputStream(fin);
			movieCollectionSerializable = new ArrayList<Movie>();
			
			// read serialized list into list
			movieCollectionSerializable = (ArrayList<?>)oin.readObject();

			numMovies = movieCollectionSerializable.size();
			
			if (numMovies == 0) {
				initializeMovieCollectionRandomly(10);
				System.out.println("INITIALIZED RANDOMLY.");
			}
			else {
				// initialize contents of movieCollection from file
				for (int i = 0; i < movieCollectionSerializable.size(); ++i) {
					Movie m = new Movie(); // update ID counter
					m = (Movie)movieCollectionSerializable.get(i);
					m.setID(i); // redefine ID so that have consecutive IDs
					movieCollection[i] = m;
					System.out.println(movieCollection[i].getTitle() + ": " + movieCollection[i].getID());
				}
			}
			
			for (Movie m : movieCollection) {
				if (m != null) {
					System.out.println(m.getTitle() + ": " + m.getID());
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: movie file not found. Initializing list randomly.");
			numMovies = 10;
			initializeMovieCollectionRandomly(numMovies); // only if file not found, generate a list randomly
		}
		catch (Exception e) {
			System.out.println("Error: reading movie file failed.");
			System.out.println("[" + e.getMessage() + "]");
			e.printStackTrace();
		}
	}




	// randomly initialize movie list
	public void initializeMovieCollectionRandomly(int numMovies) throws Exception {
		for (int i = 0; i < numMovies; ++i) {
			movieCollection[i] = new Movie();
			movieCollection[i].getYear();
			movieCollection[i].setTitle("Title_Rand_" + generator.nextInt(1000));
			if (i % 2 == 0) movieCollection[i].setDirector("Director_1");
			if (i % 2 == 1) movieCollection[i].setDirector("Director_2");
			movieCollection[i].setGenre("Genre_" + generator.nextInt(5));
			movieCollection[i].setYear(1900 + (generator.nextInt(100)));
			movieCollection[i].setTime(new Time(1, 10*i));
		}
	}



	// add a movie to the list
	public void addMovie(Movie m) {
		boolean movieExists = false;
		
		// see if movie exists
		for (int i = 0; i < numMovies; ++i) {
			// movie already exists, so we will edit its information
			if (m.getID() == movieCollection[i].getID()) {
				movieCollection[i] = m;
				movieExists = true;
			}
		}

		// movie doesn't exist
		if (! movieExists) {
			// there is space for a new movie...
			if (numMovies + 1 < MAX_MOVIES) {
				movieCollection[numMovies] = m;
				++numMovies;
			}
			// ...or not
			else {
				JOptionPane.showMessageDialog(null, "Cannot create movie. Maximum number of movies reached.");
			}
		}

		// update JTree
		sortMovieCollection(getSelectedSortMethod());
		rebuildJTree();

	}
	
	
	
	// delete a movie from the list
	public void deleteMovie(int id) {
		boolean deletedMovie = false;
		
		for (int i = 0; i < numMovies; ++i) {
			if (movieCollection[i].getID() == id) {
				// delete movie
				for (int j = i; j < numMovies - 1; ++j) {
					// overwrite ith element and shift array to the left
					movieCollection[j] = movieCollection[j+1];
				}
				movieCollection[numMovies - 1] = null; // clear redundant last item
				--numMovies;
				deletedMovie = true;
			}
		}
		
		if (deletedMovie) {
			sortMovieCollection(getSelectedSortMethod());
			rebuildJTree();
		}
		else {
			JOptionPane.showMessageDialog(null, "The movie \"" + movieCollection[id].getTitle() + "\" (" + movieCollection[id].getYear() + ") could not be deleted.");
		}
	}

}
