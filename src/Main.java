import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentService paymentService = new PaymentService();

        while (true){
            System.out.println("/n=== Payment System ===");
            System.out.println("1. Create Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Find Payment");
            System.out.println("4. Refund Payment");
            System.out.println("5. Exit");

            System.out.println("Choose option: ");
            int choice = scanner.nextInt();

            try {
                if (choice ==1) {
                    System.out.println("Enter amount: ");
                    double amount = scanner.nextDouble();

                    System.out.println("Choose methood: 1 = CARD, 2 = CASH, 3 = TRANSFER");
                    int methodInput = scanner.nextInt();

                    PaymentMethod method;
                    if (methodInput == 1) {
                        method = PaymentMethod.CARD;
                    } else if (methodInput == 2) {
                        method = PaymentMethod.CASH;
                    } else {
                        method = PaymentMethod.TRANSFER;
                    }

                    Payment payment = paymentService.createPayment(amount, method);
                    paymentService.processPayment(payment);

                    System.out.println("Created: " + payment);
                } else if (choice == 2) {
                    for (Payment p : paymentService.getPaymentHistory()) {
                        System.out.println(p);
                    }

                } else if (choice == 3) {
                    System.out.println("Enter payment ID: ");
                    String id = scanner.next();

                    Payment found = paymentService.findPaymentById(id);

                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Not found");
                    }

                } else if (choice == 4) {
                    System.out.println("Enter payment ID to refund");
                    String id = scanner.next();

                    Payment payment = paymentService.findPaymentById(id);

                    if (payment != null) {
                        paymentService.refundPayment(payment);
                        System.out.println("Refunded: " + payment);

                    } else {
                        System.out.println("Payment not found");
                    }

                } else if (choice == 5) {
                    System.out.println("Exiting...");
                    break;

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();



    try {
        Payment payment1 = paymentService.createPayment(250.50, PaymentMethod.CARD);
        paymentService.processPayment(payment1);

        Payment payment2 = paymentService.createPayment(1500, PaymentMethod.TRANSFER);
        paymentService.processPayment(payment2);

        Payment payment3 = paymentService.createPayment(-10, PaymentMethod.CASH);
        paymentService.processPayment(payment3);
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

