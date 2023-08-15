package User;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public abstract class User {
	private String nickname;
	private String password;
	private String realName;
	private String surname;
	private int age;
	private String mailAddress;
	private ImageIcon profilePhoto;
	private ArrayList<Image> uploadedPhotos;
	public static ArrayList<User> users = new ArrayList<User>();

	   /**
     * Constructs a User object with the specified details.
     *
     * @param nickname        The nickname of the user.
     * @param password        The password of the user.
     * @param realName        The real name of the user.
     * @param surname         The surname of the user.
     * @param age             The age of the user.
     * @param mailAddress     The email address of the user.
     * @param profilePhoto    The profile photo of the user.
     * @param uploadedPhotos  The list of uploaded photos by the user.
     */
	public User(String nickname, String password, String realName, String surname, int age, String mailAddress,
			ImageIcon profilePhoto, ArrayList<Image> uploadedPhotos) {
		this.nickname = nickname;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.age = age;
		this.mailAddress = mailAddress;
		this.profilePhoto = profilePhoto;
		this.uploadedPhotos = uploadedPhotos;
		users.add(this);
	}

	public User(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
	}

	public static ArrayList<User> getUsers() {
		return users;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public ArrayList<Image> getUploadedPhotos() {
		return uploadedPhotos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMailAdress() {
		return mailAddress;
	}

	public void setMailAdress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public ImageIcon getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(ImageIcon profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getNickname() {
		return nickname;
	}

	public abstract String getUserType();

	public abstract void blurImage();

	public abstract void sharpenImage();
}
