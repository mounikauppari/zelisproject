package interfaces;

public interface Approvable {
    void approveClaim(String claimId, String remarks);
    void rejectClaim(String claimId, String remarks);
}
