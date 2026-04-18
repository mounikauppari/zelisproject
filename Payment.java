package models;

import java.time.LocalDate;

public class Payment {
    private String paymentId;
    private String claimId;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod;

    public Payment(String paymentId, String claimId, double amount, String paymentMethod) {
        this.paymentId     = paymentId;
        this.claimId       = claimId;
        this.amount        = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate   = LocalDate.now();
    }

    public String getPaymentId()    { return paymentId; }
    public String getClaimId()      { return claimId; }
    public double getAmount()       { return amount; }
    public LocalDate getPaymentDate(){ return paymentDate; }
    public String getPaymentMethod(){ return paymentMethod; }

    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-10s | %-10.2f | %-12s | %-12s |",
            paymentId, claimId, amount, paymentMethod, paymentDate
        );
    }
}
