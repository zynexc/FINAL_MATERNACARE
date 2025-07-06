package com.maternacare;

import com.maternacare.model.MaternalRecord;
import com.maternacare.model.PregnancyHistory;
import com.maternacare.service.MaternalRecordService;

import java.sql.SQLException;
import java.util.List;

public class TestDataLoading {
    public static void main(String[] args) {
        System.out.println("Testing data loading...");

        MaternalRecordService service = new MaternalRecordService();
        try {
            List<MaternalRecord> records = service.loadRecords();

            System.out.println("Loaded " + records.size() + " records");

            for (MaternalRecord record : records) {
                System.out.println("\nRecord: " + record.getPatientId() + " - " +
                        record.getFullName());

                if (record.getPregnancyHistory() != null) {
                    System.out.println("  Pregnancy History count: " + record.getPregnancyHistory().size());

                    for (PregnancyHistory history : record.getPregnancyHistory()) {
                        System.out.println("    Pregnancy #" + history.getPregnancyNumber());
                        System.out.println("      Delivery Type: " + history.getDeliveryType());
                        System.out.println("      Gender: " + history.getGender());
                        System.out.println("      Place: " + history.getPlaceOfDelivery());
                        System.out.println("      Year: " + history.getYearDelivered());
                        System.out.println("      Attended By: " + history.getAttendedBy());
                        System.out.println("      Status: " + history.getStatus());
                        System.out.println("      Birth Date: " + history.getBirthDate());
                        System.out.println("      TT Injection: " + history.getTtInjection());
                    }
                } else {
                    System.out.println("  No pregnancy history");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}