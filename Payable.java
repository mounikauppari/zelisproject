package interfaces;

public interface Payable {
    void processPayment(String claimId, String paymentMethod);
    void viewPaymentHistory();
}
