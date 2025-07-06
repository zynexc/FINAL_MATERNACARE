package com.maternacare.model;

import java.time.LocalDate;

public class VitalSignsEntry {
    private LocalDate date;
    private String bloodPressure;
    private String pulseRate;
    private String respiratoryRate;
    private String remarks;
    private String aog;
    private String height;
    private String weight;
    private String fundalHeight;
    private String fht;
    private String presentation;
    private String chiefComplaint;
    private LocalDate toComeBack;

    public VitalSignsEntry() {
    }

    public VitalSignsEntry(LocalDate date, String bloodPressure, String pulseRate,
            String respiratoryRate, String remarks, String aog, String height, String weight, 
            String fundalHeight, String fht, String presentation, String chiefComplaint, LocalDate toComeBack) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.pulseRate = pulseRate;
        this.respiratoryRate = respiratoryRate;
        this.remarks = remarks;
        this.aog = aog;
        this.height = height;
        this.weight = weight;
        this.fundalHeight = fundalHeight;
        this.fht = fht;
        this.presentation = presentation;
        this.chiefComplaint = chiefComplaint;
        this.toComeBack = toComeBack;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(String pulseRate) {
        this.pulseRate = pulseRate;
    }

    public String getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(String respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAog() {
        return aog;
    }

    public void setAog(String aog) {
        this.aog = aog;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFundalHeight() {
        return fundalHeight;
    }

    public void setFundalHeight(String fundalHeight) {
        this.fundalHeight = fundalHeight;
    }

    public String getFht() {
        return fht;
    }

    public void setFht(String fht) {
        this.fht = fht;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public LocalDate getToComeBack() {
        return toComeBack;
    }

    public void setToComeBack(LocalDate toComeBack) {
        this.toComeBack = toComeBack;
    }

    @Override
    public String toString() {
        return "VitalSignsEntry{" +
                "date=" + date +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", pulseRate='" + pulseRate + '\'' +
                ", respiratoryRate='" + respiratoryRate + '\'' +
                ", remarks='" + remarks + '\'' +
                ", aog='" + aog + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", fundalHeight='" + fundalHeight + '\'' +
                ", fht='" + fht + '\'' +
                ", presentation='" + presentation + '\'' +
                ", chiefComplaint='" + chiefComplaint + '\'' +
                ", toComeBack=" + toComeBack +
                '}';
    }
}