package Pages;


import javax.imageio.ImageIO;
import javax.swing.*;

import User.FreeUser;
import User.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JFrame {
	private JLabel nicknameLabel;
	private JLabel nameLabel;
	private JLabel surnameLabel;
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JLabel profilePhotoLabel;
	private JPanel uploadedPhotosPanel;
	private JScrollPane scrollPane;
	private JButton editButton;
	private JButton returnButton;
	private JButton saveButton;
	private JTextField nicknameField;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField emailField;
	private JPasswordField passwordField;

	/**
	 * Constructs a ProfilePage object for the given user.
	 *
	 * @param clickedUser The user whose profile page is being displayed.
	 * @param currentUser The currently logged-in user.
	 */
	public ProfilePage(User clickedUser, User currentUser) {
		// Set frame properties
		setTitle(clickedUser.getNickname() + "-" + "Profile Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 600));

		// Create and configure components
		nicknameLabel = new JLabel("Nickname: " + clickedUser.getNickname());
		nameLabel = new JLabel("Name: " + clickedUser.getRealName());
		surnameLabel = new JLabel("Surname: " + clickedUser.getSurname());
		emailLabel = new JLabel("Email: " + clickedUser.getMailAdress());
		passwordLabel = new JLabel("Password: " + clickedUser.getPassword());

		// Adding Profile Photo
		profilePhotoLabel = new JLabel(clickedUser.getProfilePhoto());
		ImageIcon profileIcon = (ImageIcon) profilePhotoLabel.getIcon();
		Image profileImage = profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(profileImage);
		profilePhotoLabel.setIcon(resizedIcon);

		scrollPane = new JScrollPane();
		editButton = new JButton("Edit Information");
		saveButton = new JButton("Save");
		returnButton = new JButton("Return to Discover Page");

		nicknameField = new JTextField(clickedUser.getNickname());
		nameField = new JTextField(clickedUser.getRealName());
		surnameField = new JTextField(clickedUser.getSurname());
		emailField = new JTextField(clickedUser.getMailAdress());
		passwordField = new JPasswordField(clickedUser.getPassword());

		uploadedPhotosPanel = new JPanel(new GridLayout(0, 3, 3, 3));
		scrollPane = new JScrollPane(uploadedPhotosPanel);

		// Set scroll pane properties
		scrollPane.setPreferredSize(new Dimension(380, 300));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// Set layout for the frame
		setLayout(new BorderLayout());

		// add uploaded photos to the panel
		for (Image uploadedImages : clickedUser.getUploadedPhotos()) {
			if (uploadedImages != null) {
				Image resizedImage = uploadedImages.getScaledInstance(140, 140, Image.SCALE_SMOOTH);

				// Create a new ImageIcon with the resized image
				ImageIcon Icon = new ImageIcon(resizedImage);

				// Create the JLabel with the resized ImageIcon for the photo
				JLabel imageLabel = new JLabel(Icon);

				// Create a panel to hold the labels
				JPanel labelPanel = new JPanel();
				labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
				labelPanel.add(Box.createHorizontalGlue());
				labelPanel.add(imageLabel);
				uploadedPhotosPanel.add(labelPanel);
				uploadedPhotosPanel.updateUI();
			}
		}
		// Create a panel to hold the profile photo and information
		JPanel profilePanel = new JPanel(new BorderLayout());
		profilePanel.add(profilePhotoLabel, BorderLayout.NORTH);

		// Create a panel to hold the information labels
		JPanel infoPanel = new JPanel(new GridLayout(5, 2));
		infoPanel.add(nicknameLabel);
		infoPanel.add(nicknameField);
		infoPanel.add(nameLabel);
		infoPanel.add(nameField);
		infoPanel.add(surnameLabel);
		infoPanel.add(surnameField);
		infoPanel.add(emailLabel);
		infoPanel.add(emailField);
		infoPanel.add(passwordLabel);
		infoPanel.add(passwordField);

		// Add the information panel and edit button to the profile panel
		profilePanel.add(infoPanel, BorderLayout.CENTER);

		// Add the profile panel to the frame
		add(profilePanel, BorderLayout.NORTH);

		// Add the scroll pane to the frame
		add(scrollPane, BorderLayout.CENTER);

		// Create a panel to hold the edit button and return button
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(editButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(returnButton);

		// Add the button panel to the frame
		add(buttonPanel, BorderLayout.SOUTH);

		editButton.setVisible(isCurrentUserProfile(clickedUser, currentUser));
		saveButton.setVisible(false);
		emailLabel.setVisible(isCurrentUserProfile(clickedUser, currentUser));
		passwordLabel.setVisible(isCurrentUserProfile(clickedUser, currentUser));
		nicknameField.setVisible(false);
		nameField.setVisible(false);
		surnameField.setVisible(false);
		emailField.setVisible(false);
		passwordField.setVisible(false);
		emailLabel.setVisible(false);
		passwordLabel.setVisible(false);
		
		if(isCurrentUserProfile(clickedUser, currentUser)) {
			profilePhotoLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					changeProfilePicture(clickedUser);
				}
			});

		}
		
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				saveButtonWorked(clickedUser);
			}
		});

		editButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				enableEditMode();
			}

		});

		returnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					DiscoverPage dscPage = new DiscoverPage(currentUser);
					dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

		});
		// Pack the frame and make it visible
		pack();
		setVisible(true);
	}


	protected void changeProfilePicture(User currentUser) {
		// Create a file chooser dialog
		JFileChooser fileChooser = new JFileChooser();

		// Set the starting directory to the user's home directory
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		// Show the file chooser dialog and get the user's selection
		int result = fileChooser.showOpenDialog(this);

		// Check if the user selected a file
		if (result == JFileChooser.APPROVE_OPTION) {
			// Get the selected file
			File selectedFile = fileChooser.getSelectedFile();

			// Create an ImageIcon object from the selected file
			ImageIcon newPhoto = new ImageIcon(selectedFile.getAbsolutePath());
			Image newPP = newPhoto.getImage();
			Image profileImage = newPhoto.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(profileImage);
			profilePhotoLabel.setIcon(resizedIcon);
			
			String folderPath = "./src/User/User Files/" + currentUser.getNickname();
			File folder = new File(folderPath);
			String imagePath = folderPath + "/profilePhoto.jpeg";
			File imgFolder = new File(imagePath);
			try {
				ImageIO.write(imageToBufferedImage(newPP), "jpeg", imgFolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			currentUser.setProfilePhoto(newPhoto);
		}

	}

	/**
	 * Checks if two users are the same user by comparing their equality.
	 *
	 * @param a The first user to compare.
	 * @param b The second user to compare.
	 * @return true if the two users are the same user, false otherwise.
	 */
	public boolean isCurrentUserProfile(User a, User b) {
		if (a.equals(b)) {
			return true;
		} else {
			return false;
		}
	}


	public static BufferedImage imageToBufferedImage(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	private void saveButtonWorked(User usr) {
		// Get the updated values from the text fields
		String newNickname = nicknameField.getText();
		String newName = nameField.getText();
		String newSurname = surnameField.getText();
		String newEmail = emailField.getText();
		String newPassword = passwordField.getText();

		// Update the labels with the new values
		nicknameLabel.setText("Nickname: " + newNickname);
		nameLabel.setText("Name: " + newName);
		surnameLabel.setText("Surname: " + newSurname);
		emailLabel.setText("Email: " + newEmail);
		passwordLabel.setText("Password: " + newPassword);
		
		String folderPath = "./src/User/User Files/" + usr.getNickname();
		String newFolderPath = "./src/User/User Files/" + newNickname;
		File userFolder = new File(folderPath);
		File newUserFolder = new File(newFolderPath);
		userFolder.renameTo(newUserFolder);

		// update the user.txt file

		// remove old informations
		try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
			File file = new File("user.txt");
			String line;
			while ((line = reader.readLine()) != null) {
				// Check if the line starts with the specified condition
				if (line.startsWith(usr.getNickname() + "%")) {
					removeLine(line, file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		usr.setNickname(newNickname);
		usr.setRealName(newName);
		usr.setSurname(newSurname);
		usr.setMailAddress(newEmail);
		usr.setPassword(newPassword);

		// Remove the text fields from the panel
		nicknameField.setVisible(false);
		nameField.setVisible(false);
		surnameField.setVisible(false);
		emailField.setVisible(false);
		passwordField.setVisible(false);
		emailLabel.setVisible(false);
		passwordLabel.setVisible(false);

		// Show the edit button
		editButton.setVisible(true);

		// Hide the save button
		saveButton.setVisible(false);
		JOptionPane.showMessageDialog(null, "Changes Saved Successfully");
	}

	private void enableEditMode() {

		editButton.setVisible(false);
		
		emailLabel.setVisible(true);
		passwordLabel.setVisible(true);
		nicknameField.setVisible(false);
		nameField.setVisible(true);
		surnameField.setVisible(true);
		emailField.setVisible(true);
		passwordField.setVisible(true);

		// Show the save button
		saveButton.setVisible(true);
	}
	
	/**
	 * Removes a specific line from a file.
	 *
	 * @param lineContent The content of the line to be removed.
	 * @param file        The file from which the line should be removed.
	 * @throws IOException If an error occurs while removing the line from the file.
	 */
	public void removeLine(String lineContent, File file) throws IOException {
		File temp = new File("_temp_");
		PrintWriter out = new PrintWriter(new FileWriter(temp));
		Files.lines(file.toPath()).filter(line -> !line.contains(lineContent)).forEach(out::println);
		out.flush();
		out.close();
		temp.renameTo(file);
	}
}
