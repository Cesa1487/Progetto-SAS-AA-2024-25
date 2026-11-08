package catering.businesslogic.staffManagement;

import java.util.*;
import java.time.*;
import catering.businesslogic.UseCaseLogicException;

import java.sql.*;
import catering.persistence.*;

public class StaffMember {

    private boolean kitchen;
    private boolean permanent;
    private int numberOfDaysOff;
    private ArrayList<DateSpan> onHoliday = new ArrayList<>();
    private Contact contact;

    public StaffMember(boolean permanent, int numberOfDaysOff, Contact contact, boolean kitchen) throws UseCaseLogicException {
        this.permanent = permanent;
        this.kitchen = kitchen;
        this.numberOfDaysOff = numberOfDaysOff;

        this.contact = contact;

        if (permanent && !contact.containsAddressAndFiscalCode()) {
            throw new UseCaseLogicException("Il personale permanente deve avere indirizzo e codice fiscale registrati.");
        }
    }

    public StaffMember() {
    }

    @Override
    public boolean equals(Object comp) {
        return comp instanceof StaffMember && this.contact.equals(((StaffMember)comp).contact);
    }

    public String getName() { return this.contact.getName(); }
    public void setName(String name) { this.contact.setName(name); }

    public String getSurname() { return this.contact.getSurname(); }
    public void setSurname(String surname) { this.contact.setSurname(surname); }

    public String getEmail() { return this.contact.getEmail(); }
    public void setEmail(String email) { this.contact.setEmail(email); }

    public String getWhatsapp() { return this.contact.getWhatsapp(); }
    public void setWhatsapp(String whatsapp) { this.contact.setWhatsapp(whatsapp); }

    public String getAddress() { return this.contact.getAddress(); }
    public void setAddress(String address) { this.contact.setAddress(address); }

    public String getFiscalCode() { return this.contact.getFiscalCode(); }
    public void setFiscalCode(String fiscalCode) { this.contact.setFiscalCode(fiscalCode); }

    public int getContactId() { return getContact().getContactId(); }

    public Contact getContact() { return this.contact; }
    public void setContact(Contact contact) { this.contact = contact; }

    public boolean getPermanent() { return this.permanent; }
    public void setPermanent(boolean permanent) { this.permanent = permanent; }

    public boolean isKitchen() { return this.kitchen; }
    public void setKitchen(boolean kitchen) { this.kitchen = kitchen; }

    public int getNumberOfDaysOff() { return this.numberOfDaysOff; }
    public void setNumberOfDaysOff(int numberOfDaysOff) { this.numberOfDaysOff = numberOfDaysOff; }

    public boolean isOnHoliday(LocalDate date) {
        for (DateSpan ds : onHoliday) {
            if (ds.contains(date)) return true;
        }
        return false;
    }

    public boolean isOnHoliday() {
        return isOnHoliday(LocalDate.now());
    }

    public void addOnHoliday(DateSpan ds) {
        onHoliday.add(ds);
        Collections.sort(onHoliday);
    }

    public ArrayList<DateSpan> getHolidays() {
        return onHoliday;
    }

    public void removeOnHoliday(DateSpan ds) {
        onHoliday.remove(ds);
    }

    public String toString() {
        return contact.toString() + " | Kitchen: " + kitchen + " | Permanent: " + permanent;
    }


    /** DATABASE OPERATIONS */

    public static StaffMember load(int id) {
        String query = "SELECT `contactId`, `permanent`, `numberOfDaysOff`, `kitchen` FROM StaffMembers WHERE `contactId` = ?";
        StaffMember ret = new StaffMember();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                ret.contact = Contact.load(rs.getInt("contactId"));
                ret.permanent = rs.getBoolean("permanent");
                ret.numberOfDaysOff = rs.getInt("numberOfDaysOff");
                ret.kitchen = rs.getBoolean("kitchen");
                ret.onHoliday = StaffMember.loadOnHoliday(rs.getInt("contactId"));
            }
        }, id);

        return ret;
    }

    public static ArrayList<DateSpan> loadOnHoliday(int id) {
        String query = "SELECT `fromDate`, `toDate` FROM HolidaysOfStaffMember WHERE `staffMemberId` = ?";
        ArrayList<DateSpan> ret = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                do {
                    ret.add(new DateSpan(LocalDate.parse(rs.getString("fromDate")), LocalDate.parse(rs.getString("toDate"))));
                } while (rs.next());
            }
        }, id);
        Collections.sort(ret);
        return ret;
    }

    public void save() {
        String query = "INSERT INTO StaffMembers (contactId, permanent, numberOfDaysOff, kitchen) VALUES (?, ?, ?, ?)";
        PersistenceManager.executeUpdate(query, contact.getContactId(), permanent, numberOfDaysOff, kitchen);
    }

    public static void updateChangedContact(StaffMember staffMember, Contact contact){
        updateChangedName(staffMember, contact.getName());
        updateChangedSurname(staffMember, contact.getSurname());
        updateChangedEmail(staffMember, contact.getEmail());
        updateChangedWhatsapp(staffMember, contact.getWhatsapp());
        updateChangedAddress(staffMember, contact.getAddress());
        updateChangedFiscalCode(staffMember, contact.getFiscalCode());
    }

    public static void updateChangedAddress(StaffMember staffMember, String address) {
        String query = "UPDATE Contacts SET `address` = ? WHERE `userId` = ?";
        PersistenceManager.executeUpdate(query, address, staffMember.getContactId());
    }

    public static void updateChangedFiscalCode(StaffMember staffMember, String fiscalCode) {
        String query = "UPDATE Contacts SET `fiscalCode` = ? WHERE `userId` = ?";
        PersistenceManager.executeUpdate(query, fiscalCode, staffMember.getContactId());
    }

    public static void updateChangedPermanent(StaffMember staffMember, boolean permanent) {
        String query = "UPDATE StaffMembers SET `permanent` = ? WHERE `contactId` = ?";
        PersistenceManager.executeUpdate(query, permanent, staffMember.getContactId());
    }

    public static void updateChangedName(StaffMember staffMember, String name) {
        String query = "UPDATE Contacts SET `name` = ? WHERE `userId` = ?";
        PersistenceManager.executeUpdate(query, name, staffMember.getContactId());
    }
    public static void updateChangedSurname(StaffMember staffMember, String surname) {
        String query = "UPDATE Contacts SET `surname` = ? WHERE `userId` = ?";
        PersistenceManager.executeUpdate(query, surname, staffMember.getContactId());
    }
    public static void updateChangedEmail(StaffMember staffMember, String email) {
        String query = "UPDATE Contacts SET `email` = ? WHERE `userId` = ?";
        PersistenceManager.executeUpdate(query, email, staffMember.getContactId());
    }
    public static void updateChangedWhatsapp(StaffMember staffMember, String whatsapp) {
        String query = "UPDATE Contacts SET `whatsapp` = ? WHERE `userId` = ?";
        PersistenceManager.executeUpdate(query, whatsapp, staffMember.getContactId());
    }

    public static void updateAddOnHoliday(StaffMember staffMember, DateSpan s) {
        String query = "INSERT INTO HolidaysOfStaffMember(fromDate, toDate, staffMemberId) VALUES (?, ?, ?)";
        PersistenceManager.executeUpdate(query, s.getFrom().toString(), s.getTo().toString(), staffMember.getContactId());
    }

    public static void updateRemoveOldOnHoliday(StaffMember staffMember, DateSpan s) {
        String query = "DELETE FROM HolidaysOfStaffMember WHERE fromDate = ? AND toDate = ? AND staffMemberId = ?";
        PersistenceManager.executeUpdate(query, s.getFrom().toString(), s.getTo().toString(), staffMember.getContactId());
    }

    public static void updateChangedNumberOfDaysOff(StaffMember staffMember, int daysOff) {
        String query = "UPDATE StaffMembers SET `numberOfDaysOff` = ? WHERE `contactId` = ?";
        PersistenceManager.executeUpdate(query, daysOff, staffMember.getContactId());
    }
}