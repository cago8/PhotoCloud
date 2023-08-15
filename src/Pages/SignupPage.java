package Pages;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.desktop.UserSessionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import User.User;
import User.FreeUser;
import User.HobbyistUser;
import User.ProfessionalUser;

/**
 *
 * @author cago
 */
public class SignupPage extends javax.swing.JFrame {

	/**
	 * The signup page of the application. Allows users to create a new account.
	 */
	public SignupPage() {
		initComponents();
	}

	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		jLabel_Nickname = new javax.swing.JLabel();
		jLabel_Password = new javax.swing.JLabel();
		jLabel_Name = new javax.swing.JLabel();
		jLabel_Surname = new javax.swing.JLabel();
		jLabel_Age = new javax.swing.JLabel();
		jLabel_Email = new javax.swing.JLabel();
		jTextField_Nickname = new javax.swing.JTextField();
		jTextField_Name = new javax.swing.JTextField();
		jTextField_Surname = new javax.swing.JTextField();
		jTextField_Age = new javax.swing.JTextField();
		jTextField_Email = new javax.swing.JTextField();
		jPasswordField_Password = new javax.swing.JPasswordField();
		jButton_ProfilePhoto = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jButton_Signup = new javax.swing.JButton();
		jButton_LoginPage = new javax.swing.JButton();
		jRadioButton_Free = new javax.swing.JRadioButton();
		jRadioButton_Hobbyist = new javax.swing.JRadioButton();
		jRadioButton_Professional = new javax.swing.JRadioButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel_Nickname.setText("Nickname");

		jLabel_Password.setText("Password");

		jLabel_Name.setText("Name");

		jLabel_Surname.setText("Age");

		jLabel_Age.setText("Surname");

		jLabel_Email.setText("E-mail");

		jPasswordField_Password.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jPasswordField_PasswordActionPerformed(evt);
			}

			private void jPasswordField_PasswordActionPerformed(ActionEvent evt) {
			}
		});

		jButton_ProfilePhoto = new JButton(new ImageIcon("defaultPicture.png"));

//		// Resize and apply circular mask to profile photo
		ImageIcon profileIcon = (ImageIcon) jButton_ProfilePhoto.getIcon();
		Image profileImage = profileIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(profileImage);
		jButton_ProfilePhoto.setIcon(resizedIcon);

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel1.setText("Profile");
		jLabel1.setToolTipText("");
		jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel2.setText("Photo");
		jLabel2.setToolTipText("");
		jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

		jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("(Optional)");
		jLabel3.setToolTipText("");
		jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

		jButton_Signup.setText("Signup");
		jButton_Signup.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					jButton_SignupMouseClicked(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		jButton_LoginPage.setText("Return to Login Page");
		jButton_LoginPage.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton_LoginPageMouseClicked(evt);
			}
		});

		jButton_ProfilePhoto.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton_ProfilePhotoMouseClicked(evt);
			}
		});

		buttonGroup1.add(jRadioButton_Free);
		jRadioButton_Free.setText("Free Tier");

		buttonGroup1.add(jRadioButton_Hobbyist);
		jRadioButton_Hobbyist.setText("Hobbyist Tier");
		jRadioButton_Hobbyist.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton_HobbyistActionPerformed(evt);
			}

			private void jRadioButton_HobbyistActionPerformed(ActionEvent evt) {

			}
		});

		buttonGroup1.add(jRadioButton_Professional);
		jRadioButton_Professional.setText("Professional Tier");
		jRadioButton_Professional.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton_ProfessionalActionPerformed(evt);
			}

			private void jRadioButton_ProfessionalActionPerformed(ActionEvent evt) {

			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(44, 44, 44)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addContainerGap()
												.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(16, 16, 16)))
						.addComponent(jButton_ProfilePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
								javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(34, 34, 34)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(21, 21, 21)
												.addComponent(jRadioButton_Professional))
										.addGroup(layout.createSequentialGroup().addComponent(jRadioButton_Free)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jRadioButton_Hobbyist)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														115, Short.MAX_VALUE)
												.addComponent(jButton_LoginPage).addComponent(jButton_Signup)))))
				.addGap(44, 44, 44)).addGroup(
						layout.createSequentialGroup().addGap(14, 14, 14)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel_Name).addComponent(jLabel_Age).addComponent(jLabel_Surname)
										.addComponent(jLabel_Email)
										.addComponent(jLabel_Password, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jLabel_Nickname, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jTextField_Surname)
										.addComponent(jTextField_Age, javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jTextField_Email, javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jTextField_Nickname, javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jPasswordField_Password,
												javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jTextField_Name, javax.swing.GroupLayout.Alignment.TRAILING))
								.addGap(69, 69, 69)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel2).addGap(18, 18, 18).addComponent(jLabel3,
												javax.swing.GroupLayout.PREFERRED_SIZE, 23,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jButton_ProfilePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel_Nickname).addComponent(jTextField_Nickname,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel_Password).addComponent(jPasswordField_Password,
										javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel_Name).addComponent(jTextField_Name,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(9, 9, 9)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jTextField_Surname, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel_Age, javax.swing.GroupLayout.PREFERRED_SIZE, 17,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel_Surname)
								.addComponent(jTextField_Age, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel_Email)
								.addComponent(jTextField_Email, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton_LoginPage).addComponent(jButton_Signup)
								.addComponent(jRadioButton_Free).addComponent(jRadioButton_Hobbyist))
						.addGap(4, 4, 4).addComponent(jRadioButton_Professional).addContainerGap()));

		pack();
	}

	/**
	 * Handles the mouse click event for the jButton_LoginPage button. Opens the
	 * LoginPage and disposes the current window.
	 * 
	 * @param evt The MouseEvent that triggered the event.
	 */
	protected void jButton_LoginPageMouseClicked(MouseEvent evt) {
		// Open the LoginPage
		LoginPage.main(null);
		// Dispose the current window
		dispose();
	}

	private void jButton_ProfilePhotoMouseClicked(java.awt.event.MouseEvent evt) {
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
			Image profileImage = newPhoto.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(profileImage);
			jButton_ProfilePhoto.setIcon(resizedIcon);
		}

	}

	private void jButton_SignupMouseClicked(java.awt.event.MouseEvent evt) throws IOException {
		String pwd = new String(jPasswordField_Password.getPassword());
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		String ageText = jTextField_Age.getText();
		boolean isAgeValid = ageText.matches("\\d+");
		Pattern pattern = Pattern.compile(regex);
		Icon pp = jButton_ProfilePhoto.getIcon();
		ImageIcon ppIcon = (ImageIcon) pp;

		Image ppImage1 = ppIcon.getImage();

		ArrayList<Image> uploadedPhotos = new ArrayList<Image>();
		String type = "";

		if (jTextField_Nickname.equals("") || jTextField_Surname.equals("") || jTextField_Name.equals("")
				|| jTextField_Email.equals("") || pwd.equals("")) {
			JOptionPane.showMessageDialog(null, "There are some informations not provided.");
		} else if (pwd.length() < 8) {
			JOptionPane.showMessageDialog(null, "Password should be more than 8 characters.");
		} else if (pwd.length() > 16) {
			JOptionPane.showMessageDialog(null, "Password should be less than 16 characters.");
		} else if (existUser(jTextField_Nickname.getText())) {
			JOptionPane.showMessageDialog(null, "There is a user with this nickname.");
		} else if (!isAgeValid) {
			JOptionPane.showMessageDialog(null, "Age should contain only digits.");
		} else if (Integer.parseInt(jTextField_Age.getText()) < 0) {
			JOptionPane.showMessageDialog(null, "Age has to be greater than 0.");
		} else if (!jTextField_Email.getText().matches(regex)) {
			JOptionPane.showMessageDialog(null, "Email is not valid.");
		} else if (!(jRadioButton_Free.isSelected() || jRadioButton_Hobbyist.isSelected()
				|| jRadioButton_Professional.isSelected())) {
			JOptionPane.showMessageDialog(null, "Select one of the tiers.");

		} else {
			int age = Integer.parseInt(jTextField_Age.getText());
			if (jRadioButton_Free.isSelected()) {
				type = "Free";
				FreeUser user = new FreeUser(jTextField_Nickname.getText(), pwd, jTextField_Name.getText(),
						jTextField_Surname.getText(), age, jTextField_Email.getText(), ppIcon, uploadedPhotos);
				String folderPath = "./src/User/User Files/" + jTextField_Nickname.getText();
				File folder = new File(folderPath);
				folder.mkdir();
				String imagePath = folderPath + "/profilePhoto.jpeg";
				File imgFolder = new File(imagePath);
				try {
					ImageIO.write(imageToBufferedImage(ppImage1), "jpeg", imgFolder);
				} catch (IOException e) {
					e.printStackTrace();
				}
				DiscoverPage dscPage = new DiscoverPage(user);
				dispose();
			} else if (jRadioButton_Hobbyist.isSelected()) {
				type = "Hobbyist";
				HobbyistUser user = new HobbyistUser(jTextField_Nickname.getText(), pwd, jTextField_Name.getText(),
						jTextField_Surname.getText(), age, jTextField_Email.getText(), ppIcon, uploadedPhotos);
				String folderPath = "./src/User/User Files/" + jTextField_Nickname.getText();
				File folder = new File(folderPath);
				folder.mkdir();
				String imagePath = folderPath + "/profilePhoto.jpeg";
				File imgFolder = new File(imagePath);
				try {
					ImageIO.write(imageToBufferedImage(ppImage1), "jpeg", imgFolder);
				} catch (IOException e) {
					e.printStackTrace();
				}
				DiscoverPage dscPage = new DiscoverPage(user);
				dispose();
			} else if (jRadioButton_Professional.isSelected()) {
				type = "Professional";
				ProfessionalUser user = new ProfessionalUser(jTextField_Nickname.getText(), pwd,
						jTextField_Name.getText(), jTextField_Surname.getText(), age, jTextField_Email.getText(),
						ppIcon, uploadedPhotos);
				String folderPath = "./src/User/User Files/" + jTextField_Nickname.getText();
				File folder = new File(folderPath);
				folder.mkdir();
				String imagePath = folderPath + "/profilePhoto.jpeg";
				File imgFolder = new File(imagePath);
				try {
					ImageIO.write(imageToBufferedImage(ppImage1), "jpeg", imgFolder);
				} catch (IOException e) {
					e.printStackTrace();
				}
				DiscoverPage dscPage = new DiscoverPage(user);
				dispose();
			}
			try (FileWriter writer = new FileWriter("user.txt", true)) {
				writer.write(jTextField_Nickname.getText() + "%" + pwd + "%" + jTextField_Name.getText() + "%"
						+ jTextField_Surname.getText() + "%" + age + "%" + jTextField_Email.getText() + "%" + type
						+ "\n");
				writer.close();
			} catch (IOException e) {
				System.out.println("An error occurred while writing to the file: " + e.getMessage());
			}
		}

	}

	public static BufferedImage imageToBufferedImage(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	public boolean existUser(String username) {
		try {
			File file = new File("user.txt");
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				String user = input.nextLine();
				String[] splitted = user.split("%");
				if (splitted[0].equals(username)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(SignupPage.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SignupPage().setVisible(true);
			}
		});
	}

	// Variables declaration
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JButton jButton_Signup;
	private javax.swing.JButton jButton_LoginPage;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel_Age;
	private javax.swing.JLabel jLabel_Email;
	private javax.swing.JLabel jLabel_Name;
	private javax.swing.JLabel jLabel_Nickname;
	private javax.swing.JLabel jLabel_Password;
	private javax.swing.JButton jButton_ProfilePhoto;
	private javax.swing.JLabel jLabel_Surname;
	private javax.swing.JPasswordField jPasswordField_Password;
	private javax.swing.JRadioButton jRadioButton_Free;
	private javax.swing.JRadioButton jRadioButton_Hobbyist;
	private javax.swing.JRadioButton jRadioButton_Professional;
	private javax.swing.JTextField jTextField_Age;
	private javax.swing.JTextField jTextField_Email;
	private javax.swing.JTextField jTextField_Name;
	private javax.swing.JTextField jTextField_Nickname;
	private javax.swing.JTextField jTextField_Surname;
	// End of variables declaration
}
