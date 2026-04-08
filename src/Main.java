import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentService paymentService = new PaymentService();

        try {
            System.out.println("Enter payment amount: ");
            double amount = scanner.nextDouble();


            System.out.println("Choose payment method: 1 = CARD, 2 = CASH, 3 = TRANSFER");
            int methodInput = scanner.nextInt();

            PaymentMethod method;

            if (methodInput == 1) {
                method = PaymentMethod.CARD;
            } else if (methodInput == 2) {
                method = PaymentMethod.CASH;
            }else {
                method = PaymentMethod.TRANSFER;
            }
            Payment payment = paymentService.createPayment(amount, method);
            paymentService.proccessPayment(payment);

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());

        }
        scanner.close();


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

        Payment firstPayment = paymentService.getPaymentHistory().get(0);

        try {
            paymentService.refundPayment(firstPayment);
            System.out.println("After refaund" + firstPayment);
        } catch (IllegalArgumentException e) {
            System.out.println("Refund error" + e.getMessage());
        }
    }
}

