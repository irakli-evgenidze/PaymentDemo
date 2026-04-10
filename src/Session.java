import java.time.LocalDateTime;
public class Session {
    private String token;
    private String userEmail;
    private LocalDateTime expiresAt;

    public Session(String token, String userEmail, LocalDateTime expiresAt) {
        this.token = token;
        this.userEmail = userEmail;
        this.expiresAt = expiresAt;
    }
    public String getToken() {
        return token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
