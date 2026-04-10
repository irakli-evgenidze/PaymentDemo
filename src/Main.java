import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentService paymentService = new PaymentService();
        AuthService authService = new AuthService();

        String currentToken = null;

        while (true) {

            if (currentToken == null || !authService.isSessionValid(currentToken)) {
                if (currentToken != null) {
                    System.out.println("Session expired. Please login again.");
                    currentToken = null;
                }

                System.out.println("\n=== Authentication ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");

                int authChoice = scanner.nextInt();
                scanner.nextLine();

                if (authChoice == 1) {
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    boolean registered = authService.register(email, password);

                    if (registered) {
                        System.out.println("Registered successfully.");
                    } else {
                        System.out.println("Registration failed. User may already exist or input is invalid.");
                    }

                } else if (authChoice == 2) {
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    String token = authService.login(email, password);

                    if (token != null) {
                        currentToken = token;
                        System.out.println("Login successful.");
                        System.out.println("Session valid for 10 minutes.");
                    } else {
                        System.out.println("Invalid email or password.");
                    }

                } else if (authChoice == 3) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid option.");
                }

                continue;
            }

            try {
                String currentUserEmail = authService.getUserEmailByToken(currentToken);

                System.out.println("\n=== Payment System ===");
                System.out.println("Logged in as: " + currentUserEmail);
                System.out.println("1. Create Payment");
                System.out.println("2. View My Payments");
                System.out.println("3. Find My Payment");
                System.out.println("4. Refund My Payment");
                System.out.println("5. Logout");
                System.out.println("6. Exit");
                System.out.print("Choose option: ");

                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();

                    System.out.println("Choose method: 1 = CARD, 2 = CASH, 3 = TRANSFER");
                    int methodInput = scanner.nextInt();

                    PaymentMethod method;
                    if (methodInput == 1) {
                        method = PaymentMethod.CARD;
                    } else if (methodInput == 2) {
                        method = PaymentMethod.CASH;
                    } else if (methodInput == 3) {
                        method = PaymentMethod.TRANSFER;
                    } else {
                        System.out.println("Invalid payment method.");
                        continue;
                    }

                    Payment payment = paymentService.createPayment(amount, method, currentUserEmail);
                    paymentService.processPayment(payment);

                    System.out.println("Created: " + payment);

                } else if (choice == 2) {
                    List<Payment> payments = paymentService.getPaymentsByUser(currentUserEmail);

                    if (payments.isEmpty()) {
                        System.out.println("No payments found.");
                    } else {
                        for (Payment p : payments) {
                            System.out.println(p);
                        }
                    }

                } else if (choice == 3) {
                    System.out.print("Enter payment ID: ");
                    String id = scanner.next();

                    Payment found = paymentService.findPaymentByIdForUser(id, currentUserEmail);

                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Payment not found.");
                    }

                } else if (choice == 4) {
                    System.out.print("Enter payment ID to refund: ");
                    String id = scanner.next();

                    Payment payment = paymentService.findPaymentByIdForUser(id, currentUserEmail);

                    if (payment != null) {
                        paymentService.refundPayment(payment);
                        System.out.println("Refunded: " + payment);
                    } else {
                        System.out.println("Payment not found.");
                    }

                } else if (choice == 5) {
                    authService.logout(currentToken);
                    currentToken = null;
                    System.out.println("Logged out.");

                } else if (choice == 6) {
                    System.out.println("Exiting...");
                    break;

                } else {
                    System.out.println("Invalid option.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}