package Pages;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import PhotoEditing.Filters;
import User.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The DiscoverPage class represents the user's discover page, where they can
 * view and interact with photos uploaded by other users. It extends the JFrame
 * class and provides functionality for displaying uploaded photos, searching
 * profiles, uploading images, and navigating to the user's own profile page.
 */
public class DiscoverPage extends JFrame {
	protected static User user;
	private JButton logoutButton;
	private JButton defaultProfileButton;
	private JButton uploadImageButton;
	private JButton searchProfile;
	private JPanel uploadedPhotosPanel;
	private JScrollPane scrollPane;
	private ArrayList<Image> uploadedPhotos = new ArrayList<Image>();
	private int importantNumber;
	private JPanel commentPanel = new JPanel(new BorderLayout());
	private String description;

	/**
	 * Constructs a new DiscoverPage object for the given user.
	 *
	 * @param user the User object representing the current user
	 * @throws IOException
	 */
	public DiscoverPage(User user) throws IOException {
		DiscoverPage.user = user;
		// Set frame properties
		setTitle(user.getNickname() + "-Discover Page");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));

		// Create and configure components
		logoutButton = new JButton("Logout");
		defaultProfileButton = new JButton("My Profile");
		uploadImageButton = new JButton("Upload Image");
		searchProfile = new JButton("Search Profile");
		uploadedPhotosPanel = new JPanel(new GridLayout(0, 3, 3, 3));
		scrollPane = new JScrollPane(uploadedPhotosPanel);

		// Set scroll pane properties
		scrollPane.setPreferredSize(new Dimension(380, 300));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Set layout for the frame
		setLayout(new BorderLayout());

		// Create a panel to hold the defaultProfileButton and uploadImageButton
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(searchProfile);
		bottomPanel.add(uploadImageButton);
		bottomPanel.add(defaultProfileButton);
		bottomPanel.add(logoutButton);
		JLabel profilePictureLabel = new JLabel(new ImageIcon("defaultPicture.png"));
		ImageIcon profileIcon = (ImageIcon) profilePictureLabel.getIcon();
		Image profileImage = profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(profileImage);
		profilePictureLabel.setIcon(resizedIcon);

		// Create a panel to hold the profile picture panel and button panel
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(bottomPanel, BorderLayout.SOUTH);

		for (User usr : User.users) {
			String realPath = "./src/User/User Files/" + usr.getNickname();
			File pathFile = new File(realPath);
			File[] folders = pathFile.listFiles();
			for (File photos : folders) {
				if (!photos.getName().equals("profilePhoto.jpeg")) {
					try {
						Image ana = ImageIO.read(photos);
						if (ana != null) {
							String part = photos.getName().substring(0, photos.getName().lastIndexOf('.'));
							int last = Integer.parseInt(part);
							addPhotoToPanel(ana, usr, last);
						}
					} catch (IOException e) {
						BaseLogger errorLogger = BaseLogger.error();
						errorLogger.logError("Exception occurred: " + e.getMessage());
					}
				}
			}
		}
		// Add components to the frame
		add(centerPanel, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.NORTH);

		// Add event listeners to the buttons
		uploadImageButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				uploadImageButtonMouseClicked(evt, importantNumber);
			}
		});

		searchProfile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				searchProfileButtonMouseClicked(evt);
			}
		});

		defaultProfileButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Open a dialog for editing information
				ProfilePage profile = new ProfilePage(user, user);
				dispose();
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage.main(null);
				dispose();
			}
		});

		// Pack and display the frame
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Handles the mouse click event for the searchProfile button. Opens a new
	 * search frame where the user can search for a profile.
	 *
	 * @param evt the MouseEvent representing the mouse click event
	 */
	protected void searchProfileButtonMouseClicked(MouseEvent evt) {
		JFrame searchFrame = new JFrame("Search Profile");
		searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchFrame.setSize(300, 150);

		// Create a panel to hold the components
		JPanel searchPanel = new JPanel();

		// Create a text field
		JTextField searchTextField = new JTextField();
		searchTextField.setPreferredSize(new Dimension(200, searchTextField.getPreferredSize().height));
		searchPanel.add(searchTextField);

		// Create a search button
		JButton searchButton = new JButton("Search");
		searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				String input = searchTextField.getText();
				for (User usrs : User.users) {
					if (usrs.getNickname().equals(input)) {
						ProfilePage profile = new ProfilePage(usrs, user);
						searchFrame.setVisible(false);
						dispose();
						break;
					} else {
						JOptionPane.showMessageDialog(null, "There is no such a user.");
						break;
					}
				}
			}
		});
		searchPanel.add(searchButton);

		// Add the search panel to the frame
		searchFrame.getContentPane().add(searchPanel);

		// Display the search frame
		searchFrame.setVisible(true);
	}

	/**
	 * Handles the mouse click event for the uploadImageButton. Opens a file chooser
	 * dialog for selecting an image file to upload. Displays a modal dialog for
	 * applying filters to the selected image.
	 *
	 * @param evt             the MouseEvent representing the mouse click event
	 * @param importantNumber an integer value used for hold the information of
	 *                        photos
	 */
	protected void uploadImageButtonMouseClicked(MouseEvent evt, int importantNumber) {
		// Create a file chooser dialog for selecting an image file
		JFileChooser fileChooser = new JFileChooser();

		// Set the starting directory to the user's home directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Select an Image");

		// Show the file chooser dialog and get the user's selection
		int result = fileChooser.showOpenDialog(this);

		// Check if a file was selected
		if (result == JFileChooser.APPROVE_OPTION) {
			// Get the selected file
			File selectedFile = fileChooser.getSelectedFile();

			// Create a modal dialog for applying filters
			JFrame filterDialog = new JFrame("Apply Filters");
			filterDialog.setPreferredSize(new Dimension(400, 700));
			filterDialog.setLayout(new BorderLayout());

			// Load the selected image
			ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
			Image profileImage = imageIcon.getImage().getScaledInstance(400, 350, Image.SCALE_SMOOTH);
			JLabel image = new JLabel(new ImageIcon(profileImage));
			JPanel view = new JPanel(new BorderLayout());

			// Create a panel for filter sliders
			JPanel sliderPanel = new JPanel(new GridLayout(6, 2));

			// Code for creating and configuring the filter sliders

			// Blur slider
			JLabel blurLabel = new JLabel("Blur");
			JSlider blurSlider = new JSlider(0, 10, 0);
			blurSlider.setMajorTickSpacing(1);
			blurSlider.setPaintTicks(true);
			sliderPanel.add(blurLabel);
			sliderPanel.add(blurSlider);

			// Sharpen slider
			JLabel sharpenLabel = new JLabel("Sharpen");
			JSlider sharpenSlider = new JSlider(0, 20, 0);
			sharpenSlider.setMajorTickSpacing(2);
			sharpenSlider.setPaintTicks(true);
			sliderPanel.add(sharpenLabel);
			sliderPanel.add(sharpenSlider);

			// Grayscale slider
			JLabel grayscaleLabel = new JLabel("Grayscale");
			JSlider grayscaleSlider = new JSlider(50, 100, 50);
			grayscaleSlider.setMajorTickSpacing(5);
			grayscaleSlider.setPaintTicks(true);
			sliderPanel.add(grayscaleLabel);
			sliderPanel.add(grayscaleSlider);

			// Edge detection slider
			JLabel edgeLabel = new JLabel("Edge Detection");
			JSlider edgeSlider = new JSlider(0, 5, 0);
			edgeSlider.setMajorTickSpacing(1);
			edgeSlider.setPaintTicks(true);
			sliderPanel.add(edgeLabel);
			sliderPanel.add(edgeSlider);

			// Brightness slider
			JLabel brightnessLabel = new JLabel("Brightness");
			JSlider brightnessSlider = new JSlider(-100, 100, 0);
			brightnessSlider.setMajorTickSpacing(10);
			brightnessSlider.setPaintTicks(true);
			sliderPanel.add(brightnessLabel);
			sliderPanel.add(brightnessSlider);

			// Contrast slider
			JLabel contrastLabel = new JLabel("Contrast");
			JSlider contrastSlider = new JSlider(-6, 6, 0);
			contrastSlider.setMajorTickSpacing(1);
			contrastSlider.setPaintTicks(true);
			sliderPanel.add(contrastLabel);
			sliderPanel.add(contrastSlider);

			// Code for handling visibility of sliders based on user type
			if (user.getUserType().equals("Free")) {
				grayscaleLabel.setVisible(false);
				grayscaleSlider.setVisible(false);
				edgeLabel.setVisible(false);
				edgeSlider.setVisible(false);
				brightnessLabel.setVisible(false);
				brightnessSlider.setVisible(false);
				contrastLabel.setVisible(false);
				contrastSlider.setVisible(false);

			}

			if (user.getUserType().equals("Hobbyist")) {
				brightnessLabel.setVisible(false);
				brightnessSlider.setVisible(false);
				contrastLabel.setVisible(false);
				contrastSlider.setVisible(false);
			}

			JPanel buttonPanel = new JPanel(new FlowLayout());

			// Create and configure the "Add Description" button
			JButton addDescription = new JButton("Add Description");
			buttonPanel.add(addDescription);
			addDescription.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					JFrame descriptionFrame = new JFrame("Add Description");
					descriptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					descriptionFrame.setSize(300, 150);

					// Create a panel to hold the components
					JPanel descriptionPanel = new JPanel();

					// Create a text field
					JTextField descriptionField = new JTextField();
					descriptionField.setPreferredSize(new Dimension(200, descriptionField.getPreferredSize().height));
					descriptionPanel.add(descriptionField);

					JButton add = new JButton("Add");
					add.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent evt) {
							String input = descriptionField.getText();
							description = input;
							JOptionPane.showMessageDialog(null, "Description Added");
							descriptionFrame.setVisible(false);
						}
					});

					descriptionPanel.add(add);

					// Add the search panel to the frame
					descriptionFrame.getContentPane().add(descriptionPanel);

					// Display the search frame
					descriptionFrame.setVisible(true);
					descriptionFrame.toFront();
					descriptionFrame.requestFocus();
				}
			});

			// Upload Photo button
			JButton uploadButton = new JButton("Upload Photo");
			buttonPanel.add(uploadButton);
			uploadButton.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent evt) {
					BufferedImage filteredPhoto = imageToBufferedImage(profileImage);
					int blurValue = blurSlider.getValue();
					int sharpenValue = sharpenSlider.getValue();
					double grayscaleValue = ((double) grayscaleSlider.getValue() / 100);
					int edgeValue = (edgeSlider.getValue());
					double contrastValue = contrastSlider.getValue();
					int brightnessValue = brightnessSlider.getValue();
					filteredPhoto = applyFilters(filteredPhoto, blurValue, sharpenValue, grayscaleValue, edgeValue,
							contrastValue, brightnessValue);
					user.getUploadedPhotos().add(filteredPhoto);

					addPhotoToFolder(user, filteredPhoto);
					filterDialog.setVisible(false);
				}
			});

			// Apply Filters button
			JButton applyButton = new JButton("Apply Filters");

			applyButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					BufferedImage buffered = imageToBufferedImage(profileImage);
					int blurValue = blurSlider.getValue();
					int sharpenValue = sharpenSlider.getValue();
					double grayscaleValue = ((double) grayscaleSlider.getValue() / 100);
					int edgeValue = (edgeSlider.getValue());
					double contrastValue = contrastSlider.getValue();
					int brightnessValue = brightnessSlider.getValue();

					buffered = applyFilters(buffered, blurValue, sharpenValue, grayscaleValue, edgeValue, contrastValue,
							brightnessValue);

					ImageIcon imageIcon = new ImageIcon(buffered);
					JLabel imageLabel = new JLabel(imageIcon);
					view.removeAll();
					view.add(sliderPanel, BorderLayout.CENTER);
					view.add(imageLabel, BorderLayout.NORTH);
					view.add(buttonPanel, BorderLayout.SOUTH);
					filterDialog.repaint();
					filterDialog.revalidate();
				}
			});

			buttonPanel.add(applyButton);

			// Add the slider panel and apply button to the dialog
			view.add(sliderPanel, BorderLayout.CENTER);
			view.add(image, BorderLayout.NORTH);
			view.add(buttonPanel, BorderLayout.SOUTH);
			filterDialog.add(view);
			// Set the size and make the dialog visible
			filterDialog.pack();
			filterDialog.setVisible(true);
		}
	}

	/**
	 * Adds a photo to the user's folder. Saves the image file and creates a
	 * corresponding text file for storing information about the photo.
	 *
	 * @param usr the User object representing the user
	 * @param img the Image object representing the photo to be added
	 */
	public void addPhotoToFolder(User usr, Image img) {
		String folderPath = "./src/User/User Files/" + usr.getNickname();
		for (int i = 0; i < 100; i++) {
			File f = new File(folderPath + String.format("/%d", i) + ".jpeg");
			if (!f.exists()) {
				String imagePath = folderPath + String.format("/%d", i) + ".jpeg";
				File imgFolder = new File(imagePath);
				try {
					// Save the image file
					ImageIO.write(imageToBufferedImage(img), "jpeg", imgFolder);
					// Creates a txt file for storing informations of photos (e.g., like count,
					// dislike count, comments)
					File file = new File(folderPath + "/" + i + ".txt");
					if (!file.exists()) {
						FileWriter fileWriter;
						try {
							fileWriter = new FileWriter(file, true);
							fileWriter.append("0%0%visible");
							fileWriter.append("\n");
							if (description != null) {
								fileWriter.append(description);
								fileWriter.append("\n");
							} else {
								fileWriter.append("***");
								fileWriter.append("\n");
							}
							fileWriter.close();
							addPhotoToPanel(imageToBufferedImage(img), usr, i);
							break;
						} catch (IOException e) {
							BaseLogger errorLogger = BaseLogger.error();
							errorLogger.logError("Exception occurred: " + e.getMessage());
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Handles the mouse click event on the larger view button. Displays a larger
	 * view modal with photo details, including user information, description,
	 * likes, dislikes, and comments.
	 *
	 * @param evt         the MouseEvent representing the mouse click event
	 * @param clickedUser the User object representing the user who posted the photo
	 * @param img         the Image object representing the photo
	 * @throws IOException
	 */
	public void largerViewButtonMouseClicked(MouseEvent evt, User clickedUser, Image img) throws IOException {
		// Get the clicked JPanel
		JButton clickedPanel = (JButton) evt.getSource();
		int importantNumber = Integer.parseInt(clickedPanel.getName());
		// Retrieve the thumbnail JLabel within the clicked panel
		Image prof = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon res = new ImageIcon(prof);

		// Create a larger view modal
		JDialog largerViewModal = new JDialog(this, "Photo Details", true);
		largerViewModal.setPreferredSize(new Dimension(1000, 1000));
		largerViewModal.setLayout(new BorderLayout());
		JButton returnToDiscoverButton = new JButton("Return to Discover Page");

		// Display the thumbnail in the larger view modal
		JLabel largerViewLabel = new JLabel(res);
		largerViewModal.add(largerViewLabel, BorderLayout.CENTER);

		// Create a tabbed pane for photo details, comment posting, and comments
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel photoDetailsPanel = new JPanel(new BorderLayout());

		// Additional photo details
		JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// Profile photo
		JLabel userProfilePhoto = new JLabel(clickedUser.getProfilePhoto());
		userProfilePhoto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				ProfilePage profile = new ProfilePage(clickedUser, user);
			}
		});

		returnToDiscoverButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					DiscoverPage dscPage = new DiscoverPage(user);
					dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// Resize and apply circular mask to profile photo
		ImageIcon profileIcon = (ImageIcon) userProfilePhoto.getIcon();
		Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(profileImage);
		userProfilePhoto.setIcon(resizedIcon);

		// Resize and apply circular mask to profile photo
		userProfilePhoto.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		userProfilePhoto.setOpaque(true);
		userProfilePhoto.setBackground(Color.WHITE);
		userProfilePhoto.setPreferredSize(new Dimension(50, 50));
		userProfilePhoto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		// Create a label for the user's nickname
		JLabel nicknameLabel = new JLabel(clickedUser.getNickname());
		nicknameLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				ProfilePage profile = new ProfilePage(clickedUser, user);
				setVisible(false);
			}
		});
		nicknameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		// Add the profile photo and nickname label to the details panel
		detailsPanel.add(userProfilePhoto);
		detailsPanel.add(nicknameLabel);

		// Create a label for the photo description
		String path = "./src/User/User Files/" + clickedUser.getNickname();
		String absPath = path + "/" + String.valueOf(importantNumber) + ".txt";
		FileReader fileReader;
		JLabel likesLabel = new JLabel();
		JLabel dislikesLabel = new JLabel();
		try {
			fileReader = new FileReader(absPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			String x = bufferedReader.readLine();
			String[] tokens = x.split("%"); // Split line by spaces
			line = bufferedReader.readLine();
			if (!line.equals("***") && !line.equals(null)) {
				JLabel descriptionLabel = new JLabel(line);
				detailsPanel.add(descriptionLabel);
				descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			}
			likesLabel.setText("Likes: " + String.valueOf(tokens[0]));
			dislikesLabel.setText("Dislikes: " + String.valueOf(tokens[1]));
		} catch (IOException e1) {
			BaseLogger errorLogger = BaseLogger.error();
			errorLogger.logError("Exception occurred: " + e1.getMessage());
		}
		// Create a panel for likes and dislikes
		JPanel likesDislikesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// Create like and dislike buttons
		JToggleButton likeButton = new JToggleButton("Like");
		JToggleButton dislikeButton = new JToggleButton("Dislike");

		// Add like and dislike buttons, along with likes and dislikes labels, to the
		// panel
		likesDislikesPanel.add(likeButton);
		likesDislikesPanel.add(likesLabel);
		likesDislikesPanel.add(dislikeButton);
		likesDislikesPanel.add(dislikesLabel);
		detailsPanel.add(likesDislikesPanel);
		detailsPanel.add(returnToDiscoverButton, BorderLayout.EAST);
		largerViewModal.add(detailsPanel, BorderLayout.SOUTH);

		// Create the comment section tab
		JPanel postCommentPanel = new JPanel(new BorderLayout());
		JTextArea postTextArea = new JTextArea(20, 20);
		JButton postCommentButton = new JButton("Post Comment");
		postCommentPanel.add(postTextArea, BorderLayout.CENTER);
		postCommentPanel.add(postCommentButton, BorderLayout.SOUTH);

		// Add the tabs to the tabbed pane
		tabbedPane.addTab("Photo Details", photoDetailsPanel);
		tabbedPane.addTab("Post Comment", postCommentPanel);
		largerViewModal.add(tabbedPane, BorderLayout.NORTH);

		// Create panel for comment view
		tabbedPane.addTab("Comments", commentPanel);

		// Specify the file name
		String fileName = absPath;

		// Read comments from the file and add them to the text area
		JTextArea textArea = readCommentsFromFile(fileName);
		commentPanel.removeAll();
		commentPanel.add(textArea);

		// Attach an ActionListener to the postCommentButton
		postCommentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the entered comment text
				String comment = postTextArea.getText();
				postTextArea.setText(null);
				PrintWriter cmmentWriter;
				try {
					cmmentWriter = new PrintWriter(new FileWriter(absPath, true));
					cmmentWriter.printf("%s: %s%n", user.getNickname(), comment);
					cmmentWriter.close();
					JTextArea textArea = readCommentsFromFile(fileName);
					commentPanel.removeAll();
					commentPanel.add(textArea);
				} catch (IOException d) {
					// TODO Auto-generated catch block
					d.printStackTrace();
				}

				// Display a confirmation message to the user
				JOptionPane.showMessageDialog(largerViewModal, "Comment posted successfully!");
			}
		});

		// Attach an ActionListener to the sendButton
		likeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Update the like count
				int likeCount = 0;
				int dislikeCount = 0;
				String visibility = "";
				try {
					FileReader fileReader = new FileReader(absPath);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String x = bufferedReader.readLine();
					String[] tokens = x.split("%");
					likeCount = Integer.parseInt(tokens[0]);
					dislikeCount = Integer.parseInt(tokens[1]);
					visibility = tokens[2];
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (likeButton.isSelected()) {
					dislikeButton.setEnabled(false); // Disable the dislike button
					dislikeButton.setForeground(Color.GRAY); // Change the text color of the dislike label
					likeCount++;
				} else {
					dislikeButton.setEnabled(true); // Enable the dislike button
					dislikeButton.setForeground(Color.BLACK); // Restore the text color of the dislike label
					likeCount--;
				}
				File file = new File(absPath);
				try {
					FileReader fileReader = new FileReader(absPath);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String x = bufferedReader.readLine();
					String desc = bufferedReader.readLine();
					FileWriter writer = new FileWriter(file);
					BufferedWriter writ = new BufferedWriter(writer);
					String change = likeCount + "%" + dislikeCount + "%" + visibility + "\n";
					writ.write(change);
					writ.write(desc);
					writ.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				likesLabel.setText("Likes: " + likeCount);
			}
		});

		// Attach an ActionListener to the dislikeButton
		dislikeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Update the dislike count
				int likeCount = 0;
				int dislikeCount = 0;
				String visibility = "";
				try {
					FileReader fileReader = new FileReader(absPath);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String x = bufferedReader.readLine();
					String desc = bufferedReader.readLine();
					String[] tokens = x.split("%");
					likeCount = Integer.parseInt(tokens[0]);
					dislikeCount = Integer.parseInt(tokens[1]);
					visibility = tokens[2];
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (dislikeButton.isSelected()) {
					likeButton.setEnabled(false); // Disable the like button
					likeButton.setForeground(Color.GRAY); // Change the text color of the like button
					dislikeCount++;
				} else {
					likeButton.setEnabled(true); // Enable the like button
					likeButton.setForeground(Color.BLACK); // Restore the text color of the like button
					dislikeCount--;
				}
				File file = new File(absPath);
				try {
					FileReader fileReader = new FileReader(absPath);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String x = bufferedReader.readLine();
					String desc = bufferedReader.readLine();
					FileWriter writer = new FileWriter(file);
					BufferedWriter writ = new BufferedWriter(writer);
					String change = likeCount + "%" + dislikeCount + "%" + visibility + "\n";
					writ.write(change);
					writ.write(desc);
					writ.close();
				} catch (IOException ed) {
					ed.printStackTrace();
				}
				dislikesLabel.setText("Dislikes: " + dislikeCount);
			}
		});

		// Set modal properties
		largerViewModal.pack();
		largerViewModal.setLocationRelativeTo(this);
		largerViewModal.setVisible(true);

	}

	/**
	 * Converts an Image object to a BufferedImage object.
	 * 
	 * @param im The Image object to be converted.
	 * @return A BufferedImage object representing the converted image.
	 */
	public static BufferedImage imageToBufferedImage(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	/**
	 * Reads comments from a file and returns them as a JTextArea.
	 * 
	 * @param fileName The name of the file to read comments from.
	 * @return A JTextArea containing the comments read from the file.
	 */
	private JTextArea readCommentsFromFile(String fileName) {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		try (FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			String line;
			StringBuilder comments = new StringBuilder();
			int lineCount = 0;

			while ((line = bufferedReader.readLine()) != null) {
				lineCount++;
				// Skip the first two lines
				if (lineCount <= 2) {
					continue;
				}
				// Append comments to the StringBuilder
				comments.append(line).append("\n");
			}
			// Set the comments to the text area
			textArea.setText(comments.toString());
		} catch (IOException e) {
			System.out.println("An error occurred while reading the file.");
			e.printStackTrace();
		}
		return textArea;
	}

	/**
	 * 
	 * Adds a photo to the panel with the corresponding image, user, and label
	 * number.
	 * 
	 * @param img         The image to be added.
	 * @param usr         The user associated with the photo.
	 * @param labelNumber The label number for the photo.
	 */
	public void addPhotoToPanel(Image img, User usr, int labelNumber) {
		Image resizedImage = img.getScaledInstance(160, 160, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the resized image
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		JButton largerViewButton = new JButton("Larger View");
		largerViewButton.setName(String.valueOf(labelNumber));
		largerViewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					largerViewButtonMouseClicked(evt, usr, img);
				} catch (IOException e) {
					BaseLogger errorLogger;
					try {
						errorLogger = BaseLogger.error();
						errorLogger.logError("Exception occurred: " + e.getMessage());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		// Create the JLabel with the resized ImageIcon for the photo
		JLabel imageLabel = new JLabel(resizedIcon);
		String labelName = String.valueOf(labelNumber);
		imageLabel.setName(labelName);

		// Create the JLabel for the nickname
		JLabel nicknameLabel = new JLabel(usr.getNickname());

		// Create a panel to hold the labels
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(largerViewButton);
		labelPanel.add(imageLabel);
		labelPanel.add(nicknameLabel);
		largerViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nicknameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		uploadedPhotosPanel.add(labelPanel);
		uploadedPhotosPanel.updateUI();

	}

	/**
	 * Applies a series of filters to the given image and returns the resulting
	 * image.
	 * 
	 * @param img             The BufferedImage to which the filters will be
	 *                        applied.
	 * @param blurValue       The value for blurring the image. A higher value
	 *                        produces a stronger blur effect.
	 * @param sharpenValue    The value for sharpening the image. A higher value
	 *                        produces a stronger sharpen effect.
	 * @param grayscaleValue  The value for converting the image to grayscale. 0.0
	 *                        represents black and white, while 1.0 represents the
	 *                        original image.
	 * @param edgeValue       The value for detecting edges in the image. A higher
	 *                        value produces a stronger edge detection effect.
	 * @param contrastValue   The value for adjusting the contrast of the image. A
	 *                        positive value increases contrast, while a negative
	 *                        value decreases contrast.
	 * @param brightnessValue The value for adjusting the brightness of the image. A
	 *                        positive value increases brightness, while a negative
	 *                        value decreases brightness.
	 * @return The resulting BufferedImage after applying the filters.
	 */
	public BufferedImage applyFilters(BufferedImage img, int blurValue, int sharpenValue, double grayscaleValue,
			int edgeValue, double contrastValue, int brightnessValue) {
		// Apply filters to the image
		// Get the values from the sliders
		long a = System.currentTimeMillis();
		img = Filters.blurFilter(img, blurValue);
		long b = System.currentTimeMillis();
		long blurFilterDuration = b - a;
		try {
			BaseLogger logger = BaseLogger.info();
			// Log application start
			String logMessage = "Blurring filter applied to " + img.getSource() + "file,\n took: " + blurFilterDuration
					+ "ms";
			DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
			LocalDateTime now = LocalDateTime.now();
			logger.log(detailedDate.format(now) + logMessage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		long c = System.currentTimeMillis();
		img = Filters.sharpenFilter(img, sharpenValue);
		long d = System.currentTimeMillis();
		long sharpenFilterDuration = d - c;
		try {
			BaseLogger logger = BaseLogger.info();
			// Log application start
			String logMessage = "Sharpening filter applied to " + img.getSource() + "file,\n took: "
					+ sharpenFilterDuration + "ms";
			DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
			LocalDateTime now = LocalDateTime.now();
			logger.log(detailedDate.format(now) + logMessage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		long e = System.currentTimeMillis();
		if (grayscaleValue != 0.5) {
			img = Filters.grayscaleFilter(img, grayscaleValue);
		}
		long f = System.currentTimeMillis();
		if (grayscaleValue != 0.5) {
			img = Filters.grayscaleFilter(img, grayscaleValue);
		}
		long grayscaleFilterDuration = f - e;
		try {
			BaseLogger logger = BaseLogger.info();
			// Log application start
			String logMessage = "Gray Scaling filter applied to " + img.getSource() + "file,\n took: "
					+ grayscaleFilterDuration + "ms";
			DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
			LocalDateTime now = LocalDateTime.now();
			logger.log(detailedDate.format(now) + logMessage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		long g = System.currentTimeMillis();
		if (edgeValue != 0) {
			img = Filters.edgeDetectionFilter(img, edgeValue);
		}
		long h = System.currentTimeMillis();
		long edgeDetectionFilterDuration = h - g;
		try {
			BaseLogger logger = BaseLogger.info();
			// Log application start
			String logMessage = "Edge Detectioning filter applied to " + img.getSource() + "file,\n took: "
					+ edgeDetectionFilterDuration + "ms";
			DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
			LocalDateTime now = LocalDateTime.now();
			logger.log(detailedDate.format(now) + logMessage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		long i = System.currentTimeMillis();
		if (contrastValue != 0) {
			img = Filters.contrastFilter(img, contrastValue);
		}
		long k = System.currentTimeMillis();
		long contrastFilterDuration = k - i;
		try {
			BaseLogger logger = BaseLogger.info();
			// Log application start
			String logMessage = "Contrasting filter applied to " + img.getSource() + "file,\n took: "
					+ contrastFilterDuration + "ms";
			DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
			LocalDateTime now = LocalDateTime.now();
			logger.log(detailedDate.format(now) + logMessage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		long l = System.currentTimeMillis();
		img = Filters.brightnessFilter(img, brightnessValue);
		long m = System.currentTimeMillis();
		long brightnessFilterDuration = m - l;
		try {
			BaseLogger logger = BaseLogger.info();
			// Log application start
			String logMessage = "Brightening filter applied to " + img.getSource() + "file,\n took: "
					+ brightnessFilterDuration + "ms";
			DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
			LocalDateTime now = LocalDateTime.now();
			logger.log(detailedDate.format(now) + logMessage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return img;

	}
}
