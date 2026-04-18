package utils;

import models.Claim;

import java.io.*;
import java.util.Collection;

public class FileHandler {

    private static final String FILE_PATH = "claims_report.csv";

    public static void exportClaimsToCSV(Collection<Claim> claims) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println("ClaimID,PatientName,DiagnosisCode,Amount,Status,ProviderID,SubmittedDate,Remarks");
            for (Claim c : claims) {
                writer.printf("%s,%s,%s,%.2f,%s,%s,%s,%s%n",
                    c.getClaimId(),
                    c.getPatientName(),
                    c.getDiagnosisCode(),
                    c.getAmount(),
                    c.getStatus(),
                    c.getProviderId(),
                    c.getSubmittedDate(),
                    c.getRemarks()
                );
            }
            System.out.println("✅ Claims exported to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("❌ Error exporting file: " + e.getMessage());
        }
    }
}
