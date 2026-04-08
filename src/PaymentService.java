import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentService {
    private List<Payment> paymentHistory = new ArrayList<>();

    public Payment createPayment(double amount, PaymentMethod method) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");

        }


        Payment payment = new Payment(UUID.randomUUID().toString(), amount, method);
        paymentHistory.add(payment);
        return payment;
    }
    public void proccessPayment(Payment payment) {
        if (payment.getAmount() > 1000) {
            payment.setStatus(PaymentStatus.FAILED);
        }else {
            payment.setStatus(PaymentStatus.SUCCESS);
        }
    }
    public List<Payment> getPaymentHistory() {
        return paymentHistory;
    }

    public Payment findPaymentById(String id) {
        for (Payment payment : paymentHistory) {
            if (payment.getId().equals(id)) {

            }
        }
        return null;
    }
}