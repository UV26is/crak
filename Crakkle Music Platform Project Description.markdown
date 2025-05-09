# Crakkle Music Platform Project Description

## 1. Project Overview

**Crakkle Music** is a music streaming and social networking platform designed to provide a high-quality music experience through seamless interaction between a frontend web interface and a backend database. The platform supports three user roles—**Listener**, **Musician**, and **Admin**—catering to music listening, content creation, and platform management, respectively. Core functionalities include:

- Song playback and metadata display (e.g., lyrics, genre).
- Playlist creation and management.
- Social interactions such as commenting and following musicians.
- Dynamic charts based on song plays and downloads.
- Advertisement support for monetization.
- Comprehensive platform management by admins.

The system aims to deliver a robust, scalable, and socially engaging music platform with synthesized song data from across the web.

---

## 2. User Roles and Functions

### 2.0 General User
- **Registration**: Users register using an email address and password, providing personal details like date of birth, gender, and nationality.
- **Login**: Users log in with their credentials and can update personal information or change passwords post-login.

### 2.1 Listener
- **Registration**: Selects "Listener" role during registration, receiving a unique Listener ID. Music preferences are collected to enable personalized recommendations.
- **Music Experience**:
  - Browse and play songs, view metadata (lyrics, genre, play/download counts).
  - Create, edit, and manage playlists.
  - View dynamic charts (e.g., top songs by plays or downloads).
  - Receive song ads for music discovery.
  - Download songs for offline listening.
- **Social Interaction**:
  - Comment on songs to share opinions.
  - Follow favorite musicians.
  - View popular comments to gauge public sentiment.
- **Paid Feature**: Subscribe to a membership to remove ads.

### 2.2 Musician
- **Registration**: Registers as a user and applies to become a musician, receiving a Musician ID upon admin approval.
- **Content Management**:
  - Upload songs with metadata (e.g., lyrics, genre, title) and specify creative roles (e.g., composer, vocalist).
  - View song statistics (plays, downloads, comments).
- **Community Features**: Post comments to engage with listeners and gather fan feedback.

### 2.3 Administrator (Admin)
- **Account Management**: Logs in with an Admin ID and password, with the option to change the password.
- **Content Management**:
  - View and manage all listener and musician information.
  - Review musician applications and verify identities.
  - Review and edit songs, comments, and advertisements to maintain platform quality.
- **Resource Management**:
  - Upload, update, and delete advertisement details (e.g., title, cost, duration).
  - Manage song metadata (e.g., lyrics, genres).

---

## 3. Technical Architecture

### Frontend
- **Technology**: Built with HTML, JavaScript, and **Vue.js (Vue 3)** to create an interactive, user-friendly web interface.
- **Features**: Displays music playback controls, song reviews, dynamic charts, and advertisements.

### Backend
- **Technology**: Developed using **Java (Spring Boot)**, connected to a **MySQL** database.
- **Functionality**: Handles data storage, processing, and efficient querying to support frontend interactions.

### Database
- **Type**: Relational database (**MySQL**).
- **Main Tables**:
  - **User**: Stores user details (e.g., email, password, user type).
  - **Song**: Stores song metadata (e.g., name, play count, lyrics).
  - **Comment**: Stores comment details (e.g., content, timestamp, song ID).
  - **Playlist**: Manages playlists and associated songs.
  - **Musician**: Tracks musician information and creative roles.
  - **Advertisement**: Manages ad details (e.g., title, cost).
  - **Genre**: Categorizes songs by genre.
- **Chart Implementation**:
  - Dynamic charts are generated using SQL queries, e.g.:
    ```sql
    SELECT song_id, name FROM Song ORDER BY total_views DESC LIMIT 10;
    SELECT song_id, name FROM Download ORDER BY download_count DESC LIMIT 10;
    ```

---

## 4. System Architecture

The system is divided into **eight modules**, each handling specific functionalities:

1. **User Management Module**:
   - Handles registration, login, permission control, and personal profile management.
   - Assigns permissions based on user type (Listener or Musician).

2. **Music Resource Module**:
   - Manages song metadata, playlist creation, genre categorization, and search functionality.
   - Recommends trending songs based on play and download counts.

3. **Playback Function Module**:
   - Provides core playback features (progress adjustment, volume control, playback modes).
   - Supports offline downloads and customizable playback orders (e.g., shuffle, loop).

4. **Advertising Push Module**:
   - Manages ad display, timing, and placement to enhance visibility and user engagement.

5. **Foreground Music System**:
   - Frontend interface for user registration, login, music browsing, and playlist management.
   - Categorizes music by styles and genres for easy navigation.

6. **Data Statistics Module**:
   - Analyzes user behavior and song statistics to support recommendations and management decisions.

7. **System Support Module**:
   - Ensures system stability through database read-write separation, caching, and data backup.

8. **Administrator Operation Module**:
   - Enables admins to manage users, permissions, ads, and system logs, and handle errors.

---

## 5. Assumptions

- Song play and download counts are available across all platforms and updated in real-time.
- Each user (Listener/Musician) has a unique ID, with encrypted passwords for security.
- Concurrent user actions (e.g., commenting, playlist creation) do not cause conflicts.
- Admin edits to songs/ads do not conflict with user/musician operations.
- Song metadata (e.g., genres) is specified by musicians or admins post-upload.
- Advertisements have fixed positions, scroll sequentially, and are costed based on placement duration.

---

## 6. Future Plans

- **Personalized Recommendations**: Suggest songs and musicians based on user preferences, playlists, and listening history.
- **Comment Replies**: Enable threaded comment interactions.
- **Real-Time Notifications**: Push updates for new song releases or musician activities.
- **Data Analytics**: Provide musicians with visualizations of song performance (plays, downloads, comments).
- **Enhanced Recommendations**: Refine algorithms for more accurate music suggestions.

---

## 7. Database Design

### 7.1 Entity-Relationship (ER) Diagram
The ER diagram models the platform’s data structure:
- **User** (supertype): Base entity with attributes like `user_id`, `email`, `password`, `join_date`, `date_of_birth`, `gender`, `nationality`, and `profile_picture`.
- **Subtypes**:
  - **Listener**: Includes `listener_id`, `listener_name`, subscription status, and preferences (genre, language, region, era).
  - **Musician**: Includes `musician_id`, `name`, and creative roles (e.g., composer, vocalist).
- **Other Entities**: `Song`, `Comment`, `Playlist`, `Advertisement`, `Genre`, `Language`, `Region`, `Era`, `Admin`.
- **Relationships**:
  - **1:1**: Admin to User (via `admin_id`).
  - **1:N**: Listener/Musician to User (via `user_id`); Admin manages Users/Songs/Comments/Ads.
  - **M:N**: Listener-Song (Listen_to, Download, Slike), Listener-Playlist (Contain), User-Musician (Follow), Song-Genre (Have), etc.
- **Social Features**: Users follow musicians and comment on songs.
- **Admin Oversight**: Admins manage all platform content and interactions.

### 7.2 Relational Model
The ER diagram is mapped to a relational model with:
- **Tables**: `User`, `Listener`, `Musician`, `Admin`, `Song`, `Playlist`, `Comment`, `Advertisement`, `Genre`, etc.
- **Primary Keys**: `user_id`, `song_id`, `comment_id`, etc.
- **Foreign Keys**: Ensure data integrity (e.g., `Comments.user_id` references `User.user_id`).
- **M:N Relationships**: Implemented via intermediate tables (e.g., `Follow(user_id, musician_id)`).
- **Constraints**: Enforce rules, e.g., `Comments` ensures either `user_id` or `admin_id` is non-null.

### 7.3 Table Creation
MySQL `CREATE TABLE` statements are provided for all entities (e.g., `User`, `Song`, `Comments`). Example:
```sql
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_of_birth DATE,
    gender ENUM('Male', 'Female', 'Other'),
    user_type ENUM('Listener', 'Musician') NOT NULL,
    nationality VARCHAR(100),
    profile_picture VARCHAR(255)
);
```

### 7.4 Test Data
Sample data is inserted to test functionality, e.g.:
```sql
INSERT INTO User (email, password, user_type, date_of_birth, gender, nationality)
VALUES ('listener1@music.com', 'hashed_listener_pass', 'Listener', '1995-05-10', 'Female', 'USA');
```

### 7.5 Indexes
Indexes optimize query performance, e.g.:
```sql
CREATE INDEX idx_song_views ON Song(total_views);
```

### 7.6 Leaderboard Logic
SQL queries generate dynamic charts, e.g.:
```sql
SELECT song_id, sname, total_views FROM Song ORDER BY total_views DESC LIMIT 10;
```

---

## 8. Backend Development

- **Framework**: Spring Boot (Java).
- **Tasks**:
  - Set up Spring Boot project with JPA for database access.
  - Create entity classes and repositories mapping to database tables.
  - Develop RESTful APIs for:
    - User registration, login, and role management.
    - Listener functions (playback, playlists, comments, downloads).
    - Musician functions (song uploads, statistics).
    - Admin functions (user/content management, ad management).
  - Implement file upload for songs, storing files locally or in the cloud.

---

## 9. Frontend Development

- **Framework**: Vue.js (Vue 3).
- **Plan**:
  - **Core Pages**:
    - **Homepage**: Displays song lists, leaderboards, and ads.
    - **Login/Register**: Handles user authentication.
    - **Playlist Page**: Manages playlists.
    - **Song Details**: Shows lyrics, comments, and playback controls.
    - **Musician Page**: Supports song uploads and statistics viewing.
    - **Admin Page**: Manages users and content.
  - **Interactions**: Implement form submissions, API calls, and real-time updates.
  - **Optimization**: Add loading states, error prompts, and responsive design.

---

## 10. Testing and Workflows

- **Backend Testing**: Use JUnit to test APIs (e.g., song uploads, comment creation).
- **Data Interaction Testing**: Verify queries (e.g., leaderboards) and updates (e.g., play counts) with test data.
- **User Workflow Testing**:
  - **Listener**: Register, log in, play songs, comment, manage playlists.
  - **Musician**: Upload songs, view statistics.
  - **Admin**: Review musicians, manage ads.
- **Test Report**: Document passed/failed test cases.

---

## 11. Team Contributions

- **Wang Xinyi**: ER diagram design, entity-relationship diagram, report formatting, content review.
- **Liao Wenqi**: ER diagram, system architecture description, relational model mapping, content review.
- **Zhao Yanqi**: ER diagram, ER diagram description, relational model mapping, content review.
- **Cai Minjia**: ER diagram, system description, architecture diagram, design steps, table statements, content review.

---

## 12. Conclusion

Crakkle Music is a comprehensive platform combining music streaming, social interaction, and robust administration. Its modular architecture, relational database design, and modern tech stack (Vue.js, Spring Boot, MySQL) ensure scalability and user satisfaction. Future enhancements like personalized recommendations and real-time notifications will further elevate the user experience.