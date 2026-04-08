public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();

        Payment payment1 = paymentService.createPayment(250.50, PaymentMethod.CARD);
        paymentService.proccessPayment(payment1);

        Payment payment2 = paymentService.createPayment(1500, PaymentMethod.TRANSFER);
        paymentService.proccessPayment(payment2);

        for (Payment payment : paymentService.getPaymentHistory()) {
            System.out.println(payment);
        }
    }
}