package User;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class HobbyistUser extends FreeUser {

	public HobbyistUser(String nickname, String password, String realName, String surname, int age, String mailAddress,
			ImageIcon profilePhoto, ArrayList<Image> uploadedPhotos) {
		super(nickname, password, realName, surname, age, mailAddress, profilePhoto, uploadedPhotos);
	}

	public HobbyistUser(String nickname, String password) {
		super(nickname, password);
	}

	public void changeBrightness() {
		// Perform brightness and contrast adjustments
	}

	public void changeContrast() {
		// Perform brightness and contrast adjustments
	}

	@Override
	public String getUserType() {
		return "Hobbyist";

	}

}
