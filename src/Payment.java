public class Payment {
    private String id;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment(String id, double amount, PaymentMethod method) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING;
    }

    public Payment(String id, double amount, PaymentMethod method, PaymentStatus status) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.status =status;
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

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "payment{" + "id=' " +id +
                '\'' + ", ammount=" +
                amount + ", method=" + method +
                ", status=" + status + '}';
    }
}
