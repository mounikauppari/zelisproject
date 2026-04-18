package services;

import interfaces.Approvable;
import models.Claim;
import models.ClaimStatus;
import models.Provider;

import java.util.*;

public class ClaimService implements Approvable {

    private Map<String, Claim>    claims    = new HashMap<>();
    private Map<String, Provider> providers = new HashMap<>();
    private int claimCounter = 1;

    // ── Providers ────────────────────────────────────────────────────────────

    public void addProvider(Provider provider) {
        providers.put(provider.getProviderId(), provider);
        System.out.println("✅ Provider registered: " + provider.getName());
    }

    public Provider getProvider(String providerId) {
        return providers.get(providerId);
    }

    public void listProviders() {
        if (providers.isEmpty()) {
            System.out.println("No providers registered.");
            return;
        }
        System.out.println("\n===== Registered Providers =====");
        providers.values().forEach(System.out::println);
    }

    // ── Claims ────────────────────────────────────────────────────────────────

    public String submitClaim(String patientName, String diagnosisCode,
                              double amount, String providerId) {
        // Validation
        if (amount <= 0) throw new IllegalArgumentException("Claim amount must be positive.");
        if (!providers.containsKey(providerId))
            throw new IllegalArgumentException("Provider ID not found: " + providerId);

        String claimId = "CLM" + String.format("%03d", claimCounter++);
        Claim claim = new Claim(claimId, patientName, diagnosisCode, amount, providerId);
        claims.put(claimId, claim);
        System.out.println("✅ Claim submitted successfully! Claim ID: " + claimId);
        return claimId;
    }

    @Override
    public void approveClaim(String claimId, String remarks) {
        Claim claim = getClaim(claimId);
        if (claim.getStatus() != ClaimStatus.PENDING)
            throw new IllegalStateException("Only PENDING claims can be approved.");
        claim.setStatus(ClaimStatus.APPROVED);
        claim.setRemarks(remarks);
        System.out.println("✅ Claim " + claimId + " APPROVED.");
    }

    @Override
    public void rejectClaim(String claimId, String remarks) {
        Claim claim = getClaim(claimId);
        if (claim.getStatus() != ClaimStatus.PENDING)
            throw new IllegalStateException("Only PENDING claims can be rejected.");
        claim.setStatus(ClaimStatus.REJECTED);
        claim.setRemarks(remarks);
        System.out.println("❌ Claim " + claimId + " REJECTED. Reason: " + remarks);
    }

    public void viewAllClaims() {
        if (claims.isEmpty()) { System.out.println("No claims found."); return; }
        printClaimHeader();
        claims.values().forEach(System.out::println);
        System.out.println();
    }

    public void viewClaimsByStatus(ClaimStatus status) {
        System.out.println("\n===== Claims with status: " + status + " =====");
        printClaimHeader();
        claims.values().stream()
              .filter(c -> c.getStatus() == status)
              .forEach(System.out::println);
        System.out.println();
    }

    public Claim getClaim(String claimId) {
        Claim claim = claims.get(claimId);
        if (claim == null)
            throw new NoSuchElementException("Claim not found: " + claimId);
        return claim;
    }

    public Collection<Claim> getAllClaims() { return claims.values(); }

    // ── Analytics ─────────────────────────────────────────────────────────────

    public void showAnalytics() {
        if (claims.isEmpty()) { System.out.println("No data available."); return; }

        long total    = claims.size();
        long approved = claims.values().stream().filter(c -> c.getStatus() == ClaimStatus.APPROVED || c.getStatus() == ClaimStatus.PAID).count();
        long rejected = claims.values().stream().filter(c -> c.getStatus() == ClaimStatus.REJECTED).count();
        long pending  = claims.values().stream().filter(c -> c.getStatus() == ClaimStatus.PENDING).count();
        long paid     = claims.values().stream().filter(c -> c.getStatus() == ClaimStatus.PAID).count();

        double totalAmount = claims.values().stream().mapToDouble(Claim::getAmount).sum();
        double avgAmount   = totalAmount / total;
        double approvalRate = (double) approved / total * 100;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         CLAIMS ANALYTICS DASHBOARD   ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Total Claims       : %-15d ║%n", total);
        System.out.printf( "║  Pending            : %-15d ║%n", pending);
        System.out.printf( "║  Approved           : %-15d ║%n", approved);
        System.out.printf( "║  Rejected           : %-15d ║%n", rejected);
        System.out.printf( "║  Paid               : %-15d ║%n", paid);
        System.out.printf( "║  Total Amount ($)   : %-15.2f ║%n", totalAmount);
        System.out.printf( "║  Average Amount ($) : %-15.2f ║%n", avgAmount);
        System.out.printf( "║  Approval Rate (%%) : %-15.2f ║%n", approvalRate);
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void printClaimHeader() {
        System.out.println("\n| Claim ID   | Patient Name    | Diagnosis    | Amount ($) | Status     | Date         |");
        System.out.println("|------------|-----------------|--------------|------------|------------|--------------|");
    }
}
