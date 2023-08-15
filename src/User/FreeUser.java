package User;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class FreeUser extends User{

	public FreeUser(String nickname, String password, String realName, String surname, int age, String mailAddress,
			ImageIcon profilePhoto, ArrayList<Image> uploadedPhotos) {
		super(nickname, password, realName, surname, age, mailAddress, profilePhoto, uploadedPhotos);
	}
	
	public FreeUser(String nickname, String password) {
		super(nickname,password);
	}
	
	@Override
	public void blurImage() {
	}

	@Override
	public void sharpenImage() {
	}

	@Override
	public String getUserType() {
		return "Free";
		
	}
	

}
