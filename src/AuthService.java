import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthService {
    private static final String USERS_FILE = "users.txt";

    private Map<String, User> users = new HashMap<>();
    private Map<String, Session> sessions = new HashMap<>();

    public AuthService() {
        loadUsersFromFile();
    }

    public boolean register(String email, String password) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            return false;
        }

        if (password == null || password.length() < 6) {
            return false;
        }

        if (users.containsKey(email)) {
            return false;
        }

        String hashedPassword = PasswordUtil.hash(password);
        User user = new User(email, hashedPassword);
        users.put(email, user);
        saveUsersToFile();
        return true;
    }

    public String login(String email, String password) {
        User user = users.get(email);

        if (user == null) {
            return null;
        }

        String hashedInput = PasswordUtil.hash(password);

        if (!user.getPassword().equals(hashedInput)) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);

        Session session = new Session(token, email, expiresAt);
        sessions.put(token, session);

        return token;
    }

    public boolean isSessionValid(String token) {
        Session session = sessions.get(token);

        if (session == null) {
            return false;
        }

        if (session.isExpired()) {
            sessions.remove(token);
            return false;
        }

        return true;
    }

    public String getUserEmailByToken(String token) {
        if (!isSessionValid(token)) {
            return null;
        }

        return sessions.get(token).getUserEmail();
    }

    public void logout(String token) {
        sessions.remove(token);
    }

    private void loadUsersFromFile() {
        File file = new File(USERS_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);

                if (parts.length < 2) {
                    continue;
                }

                String email = parts[0];
                String password = parts[1];

                users.put(email, new User(email, password));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.write(user.getEmail() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}