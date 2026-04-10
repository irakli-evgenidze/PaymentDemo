import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.*;

public class PaymentService {

    private static final String FILE_NAME = "payments.txt";
    private List<Payment> paymentHistory = new ArrayList<>();

    public PaymentService() {
        loadPaymentsFromFile();
    }

    public Payment createPayment(double amount, PaymentMethod method, String userEmail) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                amount,
                method,
                PaymentStatus.PENDING,
                userEmail
        );

        paymentHistory.add(payment);
        savePaymentsToFile();

        return payment;
    }

    public void processPayment(Payment payment) {
        if (payment.getAmount() > 1000) {
            payment.setStatus(PaymentStatus.FAILED);
        } else {
            payment.setStatus(PaymentStatus.SUCCESS);
        }

        savePaymentsToFile();
    }

    public List<Payment> getPaymentHistory() {
        return paymentHistory;
    }

    public List<Payment> getPaymentsByUser(String userEmail) {
        List<Payment> userPayments = new ArrayList<>();

        for (Payment payment : paymentHistory) {
            if (payment.getUserEmail().equals(userEmail)) {
                userPayments.add(payment);
            }
        }

        return userPayments;
    }

    public Payment findPaymentByIdForUser(String id, String userEmail) {
        for (Payment payment : paymentHistory) {
            if (payment.getId().equals(id) && payment.getUserEmail().equals(userEmail)) {
                return payment;
            }
        }
        return null;
    }

    public void refundPayment(Payment payment) {
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            payment.setStatus(PaymentStatus.REFUNDED);
            savePaymentsToFile();
        } else {
            throw new IllegalArgumentException("Only successful payments can be refunded");
        }
    }

    private void loadPaymentsFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 5) {
                    continue;
                }

                String id = parts[0];
                double amount = Double.parseDouble(parts[1]);
                PaymentMethod method = PaymentMethod.valueOf(parts[2]);
                PaymentStatus status = PaymentStatus.valueOf(parts[3]);
                String userEmail = parts[4];

                Payment payment = new Payment(id, amount, method, status, userEmail);
                paymentHistory.add(payment);
            }
        } catch (IOException e) {
            System.out.println("Error loading payments: " + e.getMessage());
        }
    }

    private void savePaymentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Payment payment : paymentHistory) {
                String line = payment.getId() + "," +
                        payment.getAmount() + "," +
                        payment.getMethod() + "," +
                        payment.getStatus() + "," +
                        payment.getUserEmail();

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving payments: " + e.getMessage());
        }
    }
}