package services;

import interfaces.Payable;
import models.Claim;
import models.ClaimStatus;
import models.Payment;

import java.util.*;

public class PaymentService implements Payable {

    private Map<String, Payment> payments = new HashMap<>();
    private ClaimService claimService;
    private int paymentCounter = 1;

    public PaymentService(ClaimService claimService) {
        this.claimService = claimService;
    }

    @Override
    public void processPayment(String claimId, String paymentMethod) {
        Claim claim = claimService.getClaim(claimId);

        if (claim.getStatus() != ClaimStatus.APPROVED)
            throw new IllegalStateException("Only APPROVED claims can be paid. Current status: " + claim.getStatus());

        String paymentId = "PAY" + String.format("%03d", paymentCounter++);
        Payment payment  = new Payment(paymentId, claimId, claim.getAmount(), paymentMethod);
        payments.put(paymentId, payment);

        claim.setStatus(ClaimStatus.PAID);
        System.out.println("💰 Payment processed! Payment ID: " + paymentId +
                           " | Amount: $" + claim.getAmount() +
                           " | Method: " + paymentMethod);
    }

    @Override
    public void viewPaymentHistory() {
        if (payments.isEmpty()) { System.out.println("No payments recorded."); return; }
        System.out.println("\n| Payment ID | Claim ID   | Amount ($) | Method       | Date         |");
        System.out.println("|------------|------------|------------|--------------|--------------|");
        payments.values().forEach(System.out::println);
        System.out.println();
    }

    public double getTotalPaid() {
        return payments.values().stream().mapToDouble(Payment::getAmount).sum();
    }
}
