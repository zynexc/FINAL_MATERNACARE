package com.maternacare.service;

import com.maternacare.model.MaternalRecord;
import com.maternacare.model.PregnancyHistory;
import com.maternacare.model.ChildDetails;
import com.maternacare.model.VitalSignsEntry;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MaternalRecordService {
    private static final AtomicInteger nextId = new AtomicInteger(1);

    public void saveRecord(MaternalRecord record) throws SQLException {
        System.out.println("=== DEBUG: Starting saveRecord ===");
        System.out.println("DEBUG: Record ID: " + record.getId());
        System.out.println("DEBUG: Patient ID: " + record.getPatientId());
        System.out.println("DEBUG: Full Name: " + record.getFullName());
        System.out.println("DEBUG: Follow-up Vital Signs count: " + 
            (record.getFollowUpVitalSigns() != null ? record.getFollowUpVitalSigns().size() : "null"));
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);
            
            if (record.getId() == 0) {
                System.out.println("DEBUG: Creating new record (INSERT)");
                String sql = "INSERT INTO maternal_records (patient_id, full_name, date_of_birth, husband_name, " +
                           "remarks, form_timestamp, address, purok, contact_number, email, " +
                           "blood_pressure, chief_complaint, pulse_rate, respiratory_rate, last_menstrual_period, " +
                           "expected_delivery_date, para, abortion, living_children, age_of_gestation, weight, height, " +
                           "fetal_heart_tone, presentation, fundal_height, next_appointment, term, preterm, high_risk, " +
                           "barangay_residency_number, manual_gravida) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                setMaternalRecordParameters(pstmt, record);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("DEBUG: INSERT rows affected: " + rowsAffected);
                
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    record.setId(rs.getInt(1));
                    System.out.println("DEBUG: Generated ID: " + record.getId());
                }
            } else {
                System.out.println("DEBUG: Updating existing record (UPDATE)");
                String sql = "UPDATE maternal_records SET patient_id=?, full_name=?, date_of_birth=?, husband_name=?, " +
                           "remarks=?, form_timestamp=?, address=?, purok=?, contact_number=?, " +
                           "email=?, blood_pressure=?, chief_complaint=?, pulse_rate=?, respiratory_rate=?, " +
                           "last_menstrual_period=?, expected_delivery_date=?, para=?, abortion=?, living_children=?, " +
                           "age_of_gestation=?, weight=?, height=?, fetal_heart_tone=?, presentation=?, fundal_height=?, " +
                           "next_appointment=?, term=?, preterm=?, high_risk=?, barangay_residency_number=?, manual_gravida=? " +
                           "WHERE id=?";
                
                pstmt = conn.prepareStatement(sql);
                setMaternalRecordParameters(pstmt, record);
                pstmt.setInt(32, record.getId());
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("DEBUG: UPDATE rows affected: " + rowsAffected);
            }
            
            System.out.println("DEBUG: About to save pregnancy history...");
            savePregnancyHistory(conn, record);
            System.out.println("DEBUG: About to save child details...");
            saveChildDetails(conn, record);
            System.out.println("DEBUG: About to save vital signs...");
            saveVitalSigns(conn, record);
            
            conn.commit();
            System.out.println("DEBUG: Transaction committed successfully");
            
        } catch (SQLException e) {
            System.out.println("DEBUG: SQLException occurred: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                conn.rollback();
                System.out.println("DEBUG: Transaction rolled back");
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        
        System.out.println("=== DEBUG: Finished saveRecord ===");
    }

    public List<MaternalRecord> loadRecords() throws SQLException {
        List<MaternalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM maternal_records ORDER BY id";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                MaternalRecord record = mapResultSetToMaternalRecord(rs);
                record.setPregnancyHistory(loadPregnancyHistory(conn, record.getId()));
                record.setChildDetails(loadChildDetails(conn, record.getId()));
                record.setFollowUpVitalSigns(loadVitalSigns(conn, record.getId()));
                records.add(record);
            }
        }
        
        int maxId = records.stream().mapToInt(MaternalRecord::getId).max().orElse(0);
        nextId.set(maxId + 1);
        
        return records;
    }

    public MaternalRecord getRecordById(int id) throws SQLException {
        String sql = "SELECT * FROM maternal_records WHERE id = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                MaternalRecord record = mapResultSetToMaternalRecord(rs);
                record.setPregnancyHistory(loadPregnancyHistory(conn, id));
                record.setChildDetails(loadChildDetails(conn, id));
                record.setFollowUpVitalSigns(loadVitalSigns(conn, id));
                return record;
            }
        }
        return null;
    }

    public MaternalRecord getRecordByPatientId(String patientId) throws SQLException {
        String sql = "SELECT * FROM maternal_records WHERE patient_id = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                MaternalRecord record = mapResultSetToMaternalRecord(rs);
                record.setPregnancyHistory(loadPregnancyHistory(conn, record.getId()));
                record.setChildDetails(loadChildDetails(conn, record.getId()));
                record.setFollowUpVitalSigns(loadVitalSigns(conn, record.getId()));
                return record;
            }
        }
        return null;
    }

    public void updateRecord(MaternalRecord record) throws SQLException {
        saveRecord(record);
    }

    public void deleteRecord(int id) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM maternal_records WHERE id = ?")) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteRecordByPatientId(String patientId) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM maternal_records WHERE patient_id = ?")) {
            
            pstmt.setString(1, patientId);
            pstmt.executeUpdate();
        }
    }

    public void saveRecords(List<MaternalRecord> records) throws SQLException {
        for (MaternalRecord record : records) {
            saveRecord(record);
        }
    }

    private void setMaternalRecordParameters(PreparedStatement pstmt, MaternalRecord record) throws SQLException {
        int paramIndex = 1;
        pstmt.setString(paramIndex++, record.getPatientId());
        pstmt.setString(paramIndex++, record.getFullName());
        pstmt.setDate(paramIndex++, record.getDateOfBirth() != null ? Date.valueOf(record.getDateOfBirth()) : null);
        pstmt.setString(paramIndex++, record.getHusbandName());
        pstmt.setString(paramIndex++, record.getRemarks());
        pstmt.setTimestamp(paramIndex++, record.getFormTimestamp() != null ? Timestamp.valueOf(record.getFormTimestamp()) : null);
        pstmt.setString(paramIndex++, record.getAddress());
        pstmt.setString(paramIndex++, record.getPurok());
        pstmt.setString(paramIndex++, record.getContactNumber());
        pstmt.setString(paramIndex++, record.getEmail());
        pstmt.setString(paramIndex++, record.getBloodPressure());
        pstmt.setString(paramIndex++, record.getChiefComplaint());
        pstmt.setString(paramIndex++, record.getPulseRate());
        pstmt.setString(paramIndex++, record.getRespiratoryRate());
        pstmt.setDate(paramIndex++, record.getLastMenstrualPeriod() != null ? Date.valueOf(record.getLastMenstrualPeriod()) : null);
        pstmt.setDate(paramIndex++, record.getExpectedDeliveryDate() != null ? Date.valueOf(record.getExpectedDeliveryDate()) : null);
        pstmt.setString(paramIndex++, record.getPara());
        pstmt.setString(paramIndex++, record.getAbortion());
        pstmt.setString(paramIndex++, record.getLivingChildren());
        pstmt.setDouble(paramIndex++, record.getAgeOfGestation());
        pstmt.setDouble(paramIndex++, record.getWeight());
        pstmt.setDouble(paramIndex++, record.getHeight());
        pstmt.setInt(paramIndex++, record.getFetalHeartTone());
        pstmt.setString(paramIndex++, record.getPresentation());
        pstmt.setDouble(paramIndex++, record.getFundalHeight());
        pstmt.setDate(paramIndex++, record.getNextAppointment() != null ? Date.valueOf(record.getNextAppointment()) : null);
        pstmt.setString(paramIndex++, record.getTerm());
        pstmt.setString(paramIndex++, record.getPreterm());
        pstmt.setBoolean(paramIndex++, record.isHighRisk());
        pstmt.setString(paramIndex++, record.getBarangayResidencyNumber());
        pstmt.setString(paramIndex++, record.getGravida());
    }

    private MaternalRecord mapResultSetToMaternalRecord(ResultSet rs) throws SQLException {
        MaternalRecord record = new MaternalRecord();
        record.setId(rs.getInt("id"));
        record.setPatientId(rs.getString("patient_id"));
        record.setFullName(rs.getString("full_name"));
        record.setDateOfBirth(rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null);
        record.setHusbandName(rs.getString("husband_name"));
        record.setRemarks(rs.getString("remarks"));
        record.setFormTimestamp(rs.getTimestamp("form_timestamp") != null ? rs.getTimestamp("form_timestamp").toLocalDateTime() : null);
        record.setAddress(rs.getString("address"));
        record.setPurok(rs.getString("purok"));
        record.setContactNumber(rs.getString("contact_number"));
        record.setEmail(rs.getString("email"));
        record.setBloodPressure(rs.getString("blood_pressure"));
        record.setChiefComplaint(rs.getString("chief_complaint"));
        record.setPulseRate(rs.getString("pulse_rate"));
        record.setRespiratoryRate(rs.getString("respiratory_rate"));
        record.setLastMenstrualPeriod(rs.getDate("last_menstrual_period") != null ? rs.getDate("last_menstrual_period").toLocalDate() : null);
        record.setExpectedDeliveryDate(rs.getDate("expected_delivery_date") != null ? rs.getDate("expected_delivery_date").toLocalDate() : null);
        record.setPara(rs.getString("para"));
        record.setAbortion(rs.getString("abortion"));
        record.setLivingChildren(rs.getString("living_children"));
        record.setAgeOfGestation(rs.getDouble("age_of_gestation"));
        record.setWeight(rs.getDouble("weight"));
        record.setHeight(rs.getDouble("height"));
        record.setFetalHeartTone(rs.getInt("fetal_heart_tone"));
        record.setPresentation(rs.getString("presentation"));
        record.setFundalHeight(rs.getDouble("fundal_height"));
        record.setNextAppointment(rs.getDate("next_appointment") != null ? rs.getDate("next_appointment").toLocalDate() : null);
        record.setTerm(rs.getString("term"));
        record.setPreterm(rs.getString("preterm"));
        record.setHighRisk(rs.getBoolean("high_risk"));
        record.setBarangayResidencyNumber(rs.getString("barangay_residency_number"));
        record.setGravida(rs.getString("manual_gravida"));
        return record;
    }

    private void savePregnancyHistory(Connection conn, MaternalRecord record) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pregnancy_history WHERE maternal_record_id = ?")) {
            pstmt.setInt(1, record.getId());
            pstmt.executeUpdate();
        }
        
        if (record.getPregnancyHistory() != null && !record.getPregnancyHistory().isEmpty()) {
            String sql = "INSERT INTO pregnancy_history (maternal_record_id, pregnancy_number, delivery_type, gender, " +
                        "place_of_delivery, year_delivered, attended_by, status, birth_date, tt_injection) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (PregnancyHistory history : record.getPregnancyHistory()) {
                    pstmt.setInt(1, record.getId());
                    pstmt.setInt(2, history.getPregnancyNumber());
                    pstmt.setString(3, history.getDeliveryType());
                    pstmt.setString(4, history.getGender());
                    pstmt.setString(5, history.getPlaceOfDelivery());
                    pstmt.setInt(6, history.getYearDelivered());
                    pstmt.setString(7, history.getAttendedBy());
                    pstmt.setString(8, history.getStatus());
                    pstmt.setDate(9, history.getBirthDate() != null ? Date.valueOf(history.getBirthDate()) : null);
                    pstmt.setString(10, history.getTtInjection());
                    pstmt.executeUpdate();
                }
            }
        }
    }

    private List<PregnancyHistory> loadPregnancyHistory(Connection conn, int maternalRecordId) throws SQLException {
        List<PregnancyHistory> history = new ArrayList<>();
        String sql = "SELECT * FROM pregnancy_history WHERE maternal_record_id = ? ORDER BY pregnancy_number";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maternalRecordId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                PregnancyHistory ph = new PregnancyHistory();
                ph.setPregnancyNumber(rs.getInt("pregnancy_number"));
                ph.setDeliveryType(rs.getString("delivery_type"));
                ph.setGender(rs.getString("gender"));
                ph.setPlaceOfDelivery(rs.getString("place_of_delivery"));
                ph.setYearDelivered(rs.getInt("year_delivered"));
                ph.setAttendedBy(rs.getString("attended_by"));
                ph.setStatus(rs.getString("status"));
                ph.setBirthDate(rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null);
                ph.setTtInjection(rs.getString("tt_injection"));
                history.add(ph);
            }
        }
        return history;
    }

    private void saveChildDetails(Connection conn, MaternalRecord record) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM child_details WHERE maternal_record_id = ?")) {
            pstmt.setInt(1, record.getId());
            pstmt.executeUpdate();
        }
        
        if (record.getChildDetails() != null && !record.getChildDetails().isEmpty()) {
            String sql = "INSERT INTO child_details (maternal_record_id, delivery_type, gender, place_of_delivery, " +
                        "year_delivered, attended_by, status, birthdate, tetanus_status, tetanus_year) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (ChildDetails child : record.getChildDetails()) {
                    pstmt.setInt(1, record.getId());
                    pstmt.setString(2, child.getDeliveryType());
                    pstmt.setString(3, child.getGender());
                    pstmt.setString(4, child.getPlaceOfDelivery());
                    pstmt.setString(5, child.getYearDelivered());
                    pstmt.setString(6, child.getAttendedBy());
                    pstmt.setString(7, child.getStatus());
                    pstmt.setDate(8, child.getBirthdate() != null ? Date.valueOf(child.getBirthdate()) : null);
                    pstmt.setString(9, child.getTetanusStatus());
                    pstmt.setString(10, child.getTetanusYear());
                    pstmt.executeUpdate();
                }
            }
        }
    }

    private List<ChildDetails> loadChildDetails(Connection conn, int maternalRecordId) throws SQLException {
        List<ChildDetails> children = new ArrayList<>();
        String sql = "SELECT * FROM child_details WHERE maternal_record_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maternalRecordId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ChildDetails child = new ChildDetails();
                child.setDeliveryType(rs.getString("delivery_type"));
                child.setGender(rs.getString("gender"));
                child.setPlaceOfDelivery(rs.getString("place_of_delivery"));
                child.setYearDelivered(rs.getString("year_delivered"));
                child.setAttendedBy(rs.getString("attended_by"));
                child.setStatus(rs.getString("status"));
                child.setBirthdate(rs.getDate("birthdate") != null ? rs.getDate("birthdate").toLocalDate() : null);
                child.setTetanusStatus(rs.getString("tetanus_status"));
                child.setTetanusYear(rs.getString("tetanus_year"));
                children.add(child);
            }
        }
        return children;
    }

    private void saveVitalSigns(Connection conn, MaternalRecord record) throws SQLException {
        System.out.println("=== DEBUG: Starting saveVitalSigns for record ID: " + record.getId() + " ===");
        
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM vital_signs_entries WHERE maternal_record_id = ?")) {
            pstmt.setInt(1, record.getId());
            int deletedRows = pstmt.executeUpdate();
            System.out.println("DEBUG: Deleted " + deletedRows + " existing vital signs entries");
        }
        
        if (record.getFollowUpVitalSigns() != null && !record.getFollowUpVitalSigns().isEmpty()) {
            System.out.println("DEBUG: Found " + record.getFollowUpVitalSigns().size() + " vital signs entries to save");
            
            String sql = "INSERT INTO vital_signs_entries (maternal_record_id, date, blood_pressure, " +
                        "pulse_rate, respiratory_rate, remarks, aog, height, weight, fundal_height, fht, presentation, chief_complaint, to_come_back) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            System.out.println("DEBUG: SQL Query: " + sql);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (VitalSignsEntry vital : record.getFollowUpVitalSigns()) {
                    System.out.println("DEBUG: Saving vital sign entry: " + vital.toString());
                    
                    pstmt.setInt(1, record.getId());
                    pstmt.setDate(2, vital.getDate() != null ? Date.valueOf(vital.getDate()) : null);
                    pstmt.setString(3, vital.getBloodPressure());
                    pstmt.setString(4, vital.getPulseRate());
                    pstmt.setString(5, vital.getRespiratoryRate());
                    pstmt.setString(6, vital.getRemarks());
                    pstmt.setString(7, vital.getAog());
                    pstmt.setString(8, vital.getHeight());
                    pstmt.setString(9, vital.getWeight());
                    pstmt.setString(10, vital.getFundalHeight());
                    pstmt.setString(11, vital.getFht());
                    pstmt.setString(12, vital.getPresentation());
                    pstmt.setString(13, vital.getChiefComplaint());
                    pstmt.setDate(14, vital.getToComeBack() != null ? Date.valueOf(vital.getToComeBack()) : null);
                    
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("DEBUG: Inserted vital sign entry, rows affected: " + rowsAffected);
                }
            }
        } else {
            System.out.println("DEBUG: No vital signs entries to save (null or empty list)");
        }
        
        System.out.println("=== DEBUG: Finished saveVitalSigns ===");
    }

    private List<VitalSignsEntry> loadVitalSigns(Connection conn, int maternalRecordId) throws SQLException {
        List<VitalSignsEntry> vitals = new ArrayList<>();
        String sql = "SELECT * FROM vital_signs_entries WHERE maternal_record_id = ? ORDER BY date";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maternalRecordId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                VitalSignsEntry vital = new VitalSignsEntry();
                vital.setDate(rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null);
                vital.setBloodPressure(rs.getString("blood_pressure"));
                vital.setPulseRate(rs.getString("pulse_rate"));
                vital.setRespiratoryRate(rs.getString("respiratory_rate"));
                vital.setRemarks(rs.getString("remarks"));
                vital.setAog(rs.getString("aog"));
                vital.setHeight(rs.getString("height"));
                vital.setWeight(rs.getString("weight"));
                vital.setFundalHeight(rs.getString("fundal_height"));
                vital.setFht(rs.getString("fht"));
                vital.setPresentation(rs.getString("presentation"));
                vital.setChiefComplaint(rs.getString("chief_complaint"));
                vital.setToComeBack(rs.getDate("to_come_back") != null ? rs.getDate("to_come_back").toLocalDate() : null);
                vitals.add(vital);
            }
        }
        return vitals;
    }
}
