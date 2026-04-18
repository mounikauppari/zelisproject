import models.*;
import services.*;
import utils.FileHandler;

import java.util.Scanner;

public class Main {

    static ClaimService   claimService   = new ClaimService();
    static PaymentService paymentService = new PaymentService(claimService);
    static Scanner        scanner        = new Scanner(System.in);

    public static void main(String[] args) {

        // Seed some providers
        claimService.addProvider(new Provider("P001", "Dr. Ravi Kumar",   "Cardiology",   "9876543210"));
        claimService.addProvider(new Provider("P002", "Dr. Sneha Reddy",  "Neurology",    "9123456780"));
        claimService.addProvider(new Provider("P003", "City Hospital",    "General",      "9001234567"));

        System.out.println("\n🏥 Welcome to Healthcare Claims Payment Tracker");
        System.out.println("   Built with Core Java | Zelis-style Project\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter choice: ");
            System.out.println();

            try {
                switch (choice) {
                    case 1  -> submitClaim();
                    case 2  -> claimService.viewAllClaims();
                    case 3  -> approveClaim();
                    case 4  -> rejectClaim();
                    case 5  -> processPayment();
                    case 6  -> paymentService.viewPaymentHistory();
                    case 7  -> claimService.showAnalytics();
                    case 8  -> viewByStatus();
                    case 9  -> claimService.listProviders();
                    case 10 -> FileHandler.exportClaimsToCSV(claimService.getAllClaims());
                    case 0  -> { System.out.println("👋 Goodbye!"); running = false; }
                    default -> System.out.println("⚠️  Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("⚠️  Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // ── Menu ──────────────────────────────────────────────────────────────────

    static void printMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        CLAIMS MANAGEMENT SYSTEM      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Submit New Claim                 ║");
        System.out.println("║  2. View All Claims                  ║");
        System.out.println("║  3. Approve a Claim                  ║");
        System.out.println("║  4. Reject a Claim                   ║");
        System.out.println("║  5. Process Payment                  ║");
        System.out.println("║  6. View Payment History             ║");
        System.out.println("║  7. Analytics Dashboard              ║");
        System.out.println("║  8. Filter Claims by Status          ║");
        System.out.println("║  9. List Providers                   ║");
        System.out.println("║ 10. Export Claims to CSV             ║");
        System.out.println("║  0. Exit                             ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    static void submitClaim() {
        System.out.print("Patient Name     : "); String patient = scanner.nextLine().trim();
        System.out.print("Diagnosis Code   : "); String code    = scanner.nextLine().trim();
        double amount = getDoubleInput("Claim Amount ($) : ");
        claimService.listProviders();
        System.out.print("Provider ID      : "); String pid = scanner.nextLine().trim();
        claimService.submitClaim(patient, code, amount, pid);
    }

    static void approveClaim() {
        System.out.print("Claim ID  : "); String id = scanner.nextLine().trim();
        System.out.print("Remarks   : "); String rm = scanner.nextLine().trim();
        claimService.approveClaim(id, rm);
    }

    static void rejectClaim() {
        System.out.print("Claim ID  : "); String id = scanner.nextLine().trim();
        System.out.print("Reason    : "); String rm = scanner.nextLine().trim();
        claimService.rejectClaim(id, rm);
    }

    static void processPayment() {
        System.out.print("Claim ID        : "); String id = scanner.nextLine().trim();
        System.out.print("Payment Method  : "); String pm = scanner.nextLine().trim();
        paymentService.processPayment(id, pm);
    }

    static void viewByStatus() {
        System.out.println("Statuses: PENDING | APPROVED | REJECTED | PAID");
        System.out.print("Enter status: ");
        String s = scanner.nextLine().trim().toUpperCase();
        claimService.viewClaimsByStatus(ClaimStatus.valueOf(s));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) { scanner.next(); System.out.print(prompt); }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) { scanner.next(); System.out.print(prompt); }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}
