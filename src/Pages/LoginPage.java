package Pages;

import User.User;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import User.Administrator;
import User.FreeUser;
import User.HobbyistUser;
import User.ProfessionalUser;
import User.User;

/**
 *
 * @author cago
 */
public class LoginPage extends javax.swing.JFrame {

	/**
	The LoginPage class represents the user login page of the application.
	It allows users to enter their nickname and password to log in.
	Users can also sign up for a new account or access the administrator account.
	 */
	public LoginPage() {
		// Load user data from file
		String filePath = "user.txt";
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Read each line of the file and create corresponding user 
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("%"); // Split line by %
				BufferedImage img = null;
				String realPath = "./src/User/User Files/" + tokens[0];
				File pathFile = new File(realPath);
				File[] folders = pathFile.listFiles();
				String folderPath = "./src/User/User Files/" + tokens[0] + "/profilePhoto.jpeg";
				img = ImageIO.read(new File(folderPath));
				Image imge = ((Image) img);
				ImageIcon imgIcon = new ImageIcon(imge);
				ArrayList<Image> uploadedPhotos = new ArrayList<Image>();

				for (File folder : folders) {
					if (!folder.getName().equals("profilePhoto.jpeg")) {
						Image ana = ImageIO.read(folder);
						if (ana != null) {
							uploadedPhotos.add(ana);
						}
					}
				}
				// Create user objects 
				if (tokens[tokens.length - 1].equals("Free")) {
					boolean x = true;
					for (User users : User.users) {
						if (users.getNickname().equals(tokens[0])) {
							x = false;
						}
					}
					if (x) {
						FreeUser user = new FreeUser(tokens[0], tokens[1], tokens[2], tokens[3],
								Integer.parseInt(tokens[4]), tokens[5], imgIcon, uploadedPhotos);
					}
				}
				if (tokens[tokens.length - 1].equals("Hobbyist")) {
					boolean x = true;
					for (User users : User.users) {
						if (users.getNickname().equals(tokens[0])) {
							x = false;
						}
					}
					if (x) {
						HobbyistUser user = new HobbyistUser(tokens[0], tokens[1], tokens[2], tokens[3],
								Integer.parseInt(tokens[4]), tokens[5], imgIcon, uploadedPhotos);
					}
				}
				if (tokens[tokens.length - 1].equals("Professional")) {
					boolean x = true;
					for (User users : User.users) {
						if (users.getNickname().equals(tokens[0])) {
							x = false;
						}
					}
					if (x) {
						ProfessionalUser user = new ProfessionalUser(tokens[0], tokens[1], tokens[2], tokens[3],
								Integer.parseInt(tokens[4]), tokens[5], imgIcon, uploadedPhotos);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		initComponents();
	}

	private void initComponents() {

		jLabel_Nickname = new javax.swing.JLabel();
		jLabel_Password = new javax.swing.JLabel();
		jTextField_Nickname = new javax.swing.JTextField();
		jButton_Signup = new javax.swing.JButton();
		jButton_Login = new javax.swing.JButton();
		jPassword_Password = new javax.swing.JPasswordField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel_Nickname.setText("Nickname");

		jLabel_Password.setText("Password");

		jButton_Signup.setText("Signup");
		jButton_Signup.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton_SignupMouseClicked(evt);
			}
		});

		jButton_Login.setText("Login");
		jButton_Login.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton_LoginMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(77, 77, 77)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addGroup(layout.createSequentialGroup().addComponent(jButton_Signup)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton_Login))
						.addComponent(jTextField_Nickname, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
						.addComponent(jLabel_Password).addComponent(jLabel_Nickname).addComponent(jPassword_Password))
				.addContainerGap(69, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(76, 76, 76).addComponent(jLabel_Nickname)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField_Nickname, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel_Password)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPassword_Password, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(38, 38, 38)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton_Signup).addComponent(jButton_Login))
						.addContainerGap(65, Short.MAX_VALUE)));

		pack();
	}

	/**
	 * Event handler for the Login button click.
	 * Authenticates the user by checking the entered nickname and password against the user data.
	 * If the login is successful, it opens the DiscoverPage for the user.
	 *
	 * @param evt The MouseEvent triggered by clicking the Login button.
	 */
	private void jButton_LoginMouseClicked(java.awt.event.MouseEvent evt) {
		String filePath = "user.txt"; // File path of the user data
		boolean hlp = true; // Flag to indicate if the user was found
		String pwdinput = new String(jPassword_Password.getPassword()); // Get the entered password as a string,


		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			String pwdinp = new String(jPassword_Password.getPassword()); // Get the entered password as a string

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("%"); // Split the line by '%' delimiter to get the nickname and password
				if (tokens[0].equals(jTextField_Nickname.getText()) && tokens[1].equals(pwdinput)) {
					// Check if the entered nickname and password match the user data
					hlp = false;
					for (User user : User.users) {
						if (user.getNickname().equals(tokens[0])) {
							DiscoverPage dscPage = new DiscoverPage(user); // Open the DiscoverPage for the user
							try {
								BaseLogger logger = BaseLogger.info();
								// Log application start
								DateTimeFormatter detailedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
								LocalDateTime now = LocalDateTime.now();  
								logger.log("Application started at: " + detailedDate.format(now) + " Nickname: " + user.getNickname());
							} catch (IOException e1) {
								BaseLogger errorLogger = BaseLogger.error();
								errorLogger.logError("Exception occurred: " + e1.getMessage());
							}
							dispose(); // Close the LoginPage
						}
					}
				} else if (jTextField_Nickname.getText().equals("admin") && pwdinp.equals("adminadmin")) {
					// If the entered nickname and password are "admin", create an Administrator user
					hlp = false;
					Administrator user = new Administrator(jTextField_Nickname.getText(), tokens[1]);
				}
			}

			if (hlp) {
				JOptionPane.showMessageDialog(null, "There is no such user."); // Show an error message if the user is not found
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Event handler for the Signup button click.
	 * Opens the SignupPage for user registration and disposes the current LoginPage.
	 *
	 * @param evt The MouseEvent triggered by clicking the Signup button.
	 */
	private void jButton_SignupMouseClicked(java.awt.event.MouseEvent evt) {
		SignupPage.main(null); // Open the SignupPage for user registration
		dispose(); // Close the current LoginPage
	}


	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LoginPage().setVisible(true);
			}
		});
	}

	// Variables declaration 
	private javax.swing.JButton jButton_Login;
	private javax.swing.JButton jButton_Signup;
	private javax.swing.JLabel jLabel_Nickname;
	private javax.swing.JLabel jLabel_Password;
	private javax.swing.JPasswordField jPassword_Password;
	private javax.swing.JTextField jTextField_Nickname;
	// End of variables declaration
}
