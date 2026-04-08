public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();
    try {
        Payment payment1 = paymentService.createPayment(250.50, PaymentMethod.CARD);
        paymentService.proccessPayment(payment1);

        Payment payment2 = paymentService.createPayment(1500, PaymentMethod.TRANSFER);
        paymentService.proccessPayment(payment2);

        Payment payment3 = paymentService.createPayment(-10, PaymentMethod.CASH);
        paymentService.proccessPayment(payment3);
    }catch (IllegalArgumentException e) {
        System.out.println("Error:" + e.getMessage());
    }

        for (Payment payment : paymentService.getPaymentHistory()) {
            System.out.println(payment);
        }

        String searchId = paymentService.getPaymentHistory().get(0).getId();

        Payment found = paymentService.findPaymentById(searchId);

        if (found != null) {
            System.out.println("Found payment:" + found);
        } else {
            System.out.println("Payment not found");
        }
    }
}