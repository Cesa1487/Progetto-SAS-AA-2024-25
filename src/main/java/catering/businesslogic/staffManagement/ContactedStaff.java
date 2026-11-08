package catering.businesslogic.staffManagement;

import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.*;

import catering.persistence.*;

public class ContactedStaff {

    private Contact contact;
    private LocalDate requestDate;
    private LocalDate endDate;
    private String position;
    private int eventId;

    public ContactedStaff(int eventId, Contact contact, LocalDate requestDate, LocalDate endDate, String position) {
        this.eventId = eventId;
        this.contact = contact;
        this.requestDate = requestDate;
        this.endDate = endDate;
        this.position = position;
    }

    public ContactedStaff(int eventId, Contact contact, LocalDate requestDate, LocalDate endDate) {
        this(eventId, contact, requestDate, endDate, null);
    }


    public Contact getContact() {
        return this.contact;
    }

    public int getContactId() {
        return this.contact.getContactId();
    }

    public int getEventId() {
        return eventId;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    /** DATABASE OPERATIONS */

    public static ArrayList<ContactedStaff> load(int id) {
        String query = "SELECT `dateRequest`, `dateEnd`, `position`, `eventId`, `contactId` FROM ContactedStaffs WHERE `eventId` = ?";
        ArrayList<ContactedStaff> ret = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                do {
                    ret.add(new ContactedStaff(
                            rs.getInt("eventId"),
                            Contact.load(rs.getInt("contactId")),
                            LocalDate.parse(rs.getString("dateRequest")), // Ora viene usata correttamente dal costruttore
                            LocalDate.parse(rs.getString("dateEnd")),
                            rs.getString("position")
                    ));
                } while(rs.next());
            }
        }, id);

        return ret;
    }
}