package models;

import java.time.LocalDate;

public class Claim {
    private String claimId;
    private String patientName;
    private String diagnosisCode;
    private double amount;
    private ClaimStatus status;
    private String providerId;
    private LocalDate submittedDate;
    private String remarks;

    public Claim(String claimId, String patientName, String diagnosisCode,
                 double amount, String providerId) {
        this.claimId = claimId;
        this.patientName = patientName;
        this.diagnosisCode = diagnosisCode;
        this.amount = amount;
        this.providerId = providerId;
        this.status = ClaimStatus.PENDING;
        this.submittedDate = LocalDate.now();
        this.remarks = "";
    }

    // Getters
    public String getClaimId()       { return claimId; }
    public String getPatientName()   { return patientName; }
    public String getDiagnosisCode() { return diagnosisCode; }
    public double getAmount()        { return amount; }
    public ClaimStatus getStatus()   { return status; }
    public String getProviderId()    { return providerId; }
    public LocalDate getSubmittedDate() { return submittedDate; }
    public String getRemarks()       { return remarks; }

    // Setters
    public void setStatus(ClaimStatus status) { this.status = status; }
    public void setRemarks(String remarks)    { this.remarks = remarks; }

    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-15s | %-12s | %-10.2f | %-10s | %-12s |",
            claimId, patientName, diagnosisCode, amount, status, submittedDate
        );
    }
}
