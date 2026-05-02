# 📷 PhotoCloud

A feature-rich social media application inspired by Instagram, allowing users to share photos, connect with friends, and interact through likes and comments.

## 🌟 Overview

PhotoCloud is a desktop-based social media platform built with Java that brings the essence of Instagram to your local machine. Share your favorite moments, discover content from other users, and build your online presence with an intuitive and user-friendly interface.

## ✨ Features

- **User Authentication**
  - Secure sign-up and login functionality
  - User profile management
  - Account security

- **Photo Sharing**
  - Upload photos directly from your device
  - Share moments with the community
  - Organize your photo gallery

- **Social Interaction**
  - Like posts from other users
  - Comment on shared content
  - Engage with the community

- **User Profiles**
  - Customize your profile with a profile picture
  - Add and edit your bio
  - View your photo history
  - Browse other user profiles

- **Discovery**
  - Search for specific users
  - Browse content on the Discover Page
  - Find and follow friends

## 🛠️ Tech Stack

- **Language:** Java
- **Architecture:** Desktop Application
- **UI Framework:** Swing (implied from structure)

## 📋 Requirements

- Java Runtime Environment (JRE) 8 or higher
- Java Development Kit (JDK) for building from source

## ⚙️ Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/cago8/PhotoCloud.git
   cd PhotoCloud
   ```

2. **Navigate to the project directory:**
   ```bash
   cd PhotoCloud
   ```

3. **Compile the project:**
   ```bash
   javac -d bin src/**/*.java
   ```

4. **Run the application:**
   ```bash
   java -cp bin Pages.Main
   ```
   Or simply run `src/Pages/Main.java` from your IDE

## 🚀 Getting Started

1. **Create an Account**
   - Launch the application
   - Click on "Sign Up"
   - Enter your credentials and complete registration

2. **Set Up Your Profile**
   - Upload a profile picture
   - Add a bio to describe yourself
   - Customize your profile details

3. **Start Sharing**
   - Navigate to the Discover Page
   - Click "Upload Photo" to share your first photo
   - Add captions or descriptions

4. **Engage with Others**
   - Like posts that interest you
   - Leave comments on photos
   - Search for and follow other users

5. **Manage Your Content**
   - View your photos in your profile
   - Edit or delete your posts
   - Track likes and comments on your content

## 📁 Project Structure

```
PhotoCloud/
├── src/
│   └── Pages/
│       └── Main.java          # Application entry point
├── bin/                        # Compiled class files
├── defaultPicture.png         # Default profile picture
├── user.txt                   # User data storage
├── application_log.txt        # Application logs
└── README.md
```

## 💻 System Architecture

The application follows a layered architecture with:
- **Presentation Layer:** User interface components
- **Business Logic Layer:** Core application functionality
- **Data Layer:** User and post management

## 🔐 Security Features

- Encrypted password storage
- User authentication before accessing features
- Session management to protect user accounts

## 📝 Usage Examples

### Creating an Account
```
1. Launch the application
2. Click "Sign Up"
3. Enter username and password
4. Verify email (if required)
5. Start exploring!
```

### Uploading a Photo
```
1. Click "Upload Photo" on Discover Page
2. Select an image from your device
3. Add optional caption
4. Click "Share"
```

### Interacting with Content
```
1. Browse posts on feed
2. Click heart icon to like
3. Click comment icon to leave feedback
4. View other users' profiles
```

## 🐛 Known Issues

- Application logs may accumulate over time
- Default picture appears for users without profile images

## 🤝 Contributing

We welcome contributions! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📧 Contact & Support

For questions, feedback, or bug reports, please reach out to:
- **Email:** [cagribilginer60@gmail.com](mailto:cagribilginer60@gmail.com)
- **GitHub:** [cago8](https://github.com/cago8)

## 📜 License

This project is open source and available under the MIT License.

## 🎓 Learning Outcomes

PhotoCloud serves as an excellent learning resource for:
- Object-Oriented Programming (OOP) concepts
- GUI development with Java Swing
- Database design and management
- User authentication and session handling
- Software architecture patterns

---

**Made with ❤️ by Çağrı BİLGİNER**

*Happy sharing! 📸*
