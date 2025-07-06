package com.maternacare;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.Reader;

public class SimpleTest {
    public static void main(String[] args) {
        try {
            System.out.println("Testing JSON data structure...");

            Gson gson = new Gson();
            Reader reader = new FileReader("maternal_records.json");
            JsonArray records = JsonParser.parseReader(reader).getAsJsonArray();

            System.out.println("Found " + records.size() + " records in JSON file");

            for (int i = 0; i < records.size(); i++) {
                JsonObject record = records.get(i).getAsJsonObject();
                String patientId = record.get("patientId").getAsString();
                String lastName = record.get("lastName").getAsString();
                String firstName = record.get("firstName").getAsString();

                System.out.println("\nRecord " + (i + 1) + ": " + patientId + " - " + lastName + ", " + firstName);

                if (record.has("pregnancyHistory")) {
                    JsonElement pregnancyHistoryElement = record.get("pregnancyHistory");
                    if (pregnancyHistoryElement.isJsonArray()) {
                        JsonArray pregnancyHistory = pregnancyHistoryElement.getAsJsonArray();
                        System.out.println("  Pregnancy History count: " + pregnancyHistory.size());

                        for (int j = 0; j < pregnancyHistory.size(); j++) {
                            JsonObject history = pregnancyHistory.get(j).getAsJsonObject();
                            System.out.println("    Pregnancy #" + history.get("pregnancyNumber").getAsInt());
                            System.out.println("      Delivery Type: " + history.get("deliveryType").getAsString());
                            System.out.println("      Gender: " + history.get("gender").getAsString());
                            System.out.println("      Place: " + history.get("placeOfDelivery").getAsString());
                            System.out.println("      Year: " + history.get("yearDelivered").getAsInt());
                            System.out.println("      Attended By: " + history.get("attendedBy").getAsString());
                            System.out.println("      Status: " + history.get("status").getAsString());
                            System.out.println("      Birth Date: " + history.get("birthDate").getAsString());
                            System.out.println("      TT Injection: " + history.get("ttInjection").getAsString());
                        }
                    } else {
                        System.out.println("  Pregnancy History is not an array");
                    }
                } else {
                    System.out.println("  No pregnancy history field");
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}