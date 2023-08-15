package User;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Administrator extends ProfessionalUser {

	private String nickname;
	private String password;

	public Administrator(String nickname, String password, String realName, String surname, int age, String mailAddress,
			ImageIcon profilePhoto, ArrayList<Image> uploadedPhotos) {
		super(nickname, password, realName, surname, age, mailAddress, profilePhoto, uploadedPhotos);
	}
	public Administrator(String nickname, String password) {
		super(nickname,password);
	}
	
	public void removeAnyPhoto() {
//		this method removes photo from owner's profile page and discovery page
	}

}
