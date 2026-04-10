public class Payment {
    private String id;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String userEmail;

    public Payment(String id, double amount, PaymentMethod method, PaymentStatus status, String userEmail) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", method=" + method +
                ", status=" + status +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
