package User;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ProfessionalUser extends HobbyistUser {

	public ProfessionalUser(String nickname, String password, String realName, String surname, int age,
			String mailAddress, ImageIcon profilePhoto, ArrayList<Image> uploadedPhotos) {
		super(nickname, password, realName, surname, age, mailAddress, profilePhoto, uploadedPhotos);
	}
	
	public ProfessionalUser(String nickname, String password) {
		super(nickname,password);
	}

	public void applyGrayscaleFilter() {
		// Perform grayscale filtering
	}

	public void applyEdgeDetectionFilter() {
		// Perform edge detection filtering
	}
	@Override
	public String getUserType() {
		return "Professional";
		
	}
}
