package catering.businesslogic.holidayManagement;

import catering.businesslogic.staffManagement.*;
import java.time.*;

public class HolidayRequest implements Comparable<HolidayRequest> {

    private DateSpan date;
    private StaffMember staffMember;
    private String status;

    public HolidayRequest(DateSpan date, StaffMember staffMember) {
        this.date = date;
        this.staffMember = staffMember;
        this.status = "PENDING";
    }

    public HolidayRequest(DateSpan date, StaffMember staffMember, String status) {
        this.date = date;
        this.staffMember = staffMember;
        this.status = status;
    }

    public int compareTo(HolidayRequest comp) {
        return this.date.compareTo(comp.date);
    }

    public DateSpan getSpan() {
        return date;
    }

    public LocalDate getStartDate() {
        return date.getFrom();
    }

    public void setStartDate(LocalDate newStartDate) {
        date.setFrom(newStartDate);
    }

    public LocalDate getEndDate() {
        return date.getTo();
    }

    public void setEndDate(LocalDate newEndDate) {
        date.setTo(newEndDate);
    }

    public StaffMember getStaffMember() {
        return staffMember;
    }

    public StaffMember getApplicant() {
        return staffMember;
    }

    public void setStaffMember(StaffMember newStaffMember) {
        staffMember = newStaffMember;
    }

    // Getter/Setter per Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return date.toString() + " | ID Dipendente: " + staffMember.getContactId() + " | Stato: " + status;
    }
}