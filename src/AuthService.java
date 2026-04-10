import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class AuthService {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Session> sessions = new HashMap<>();

    public boolean register(String email, String password) {
        if (users.containsKey(email)) {
            return false;
        }

        users.put(email, new User(email, password));
        return true;
    }

    public String login(String email, String password) {
        User user = users.get(email);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAT = LocalDateTime.now().plusMinutes(10);

        Session session = new Session(token, email, expiresAT);
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
}
