package catering.businesslogic.availabilityManagement;

import catering.businesslogic.staffManagement.StaffMember;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityTable {
    private static AvailabilityTable singleInstance;

    public static AvailabilityTable getInstance() {
        if (singleInstance == null) {
            singleInstance = new AvailabilityTable();
        }
        return singleInstance;
    }

    private ArrayList<Availability> availabilities = new ArrayList<>();

    private AvailabilityTable() {
        loadAvailability();
    }

    public void addAvailability(Availability availability) {
        availabilities.add(availability);
    }

    public void revokeAvailability(Availability availability) {
        availabilities.remove(availability);
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public Availability getAvailability(int index) {
        return availabilities.get(index);
    }

    public List<Availability> getAvailabilityOfStaffMember(StaffMember staffMember) {
        List<Availability> result = new ArrayList<>();
        for (Availability a : availabilities) {
            if (a.getStaffMember().equals(staffMember)) {
                result.add(a);
            }
        }
        return result;
    }

    public void revokeAvailabilityForHoliday(StaffMember sm) {
        availabilities.removeIf(a -> a.getStaffMember().equals(sm) && sm.isOnHoliday(a.getDate()));
    }

    private void loadAvailability() {
        String query = "SELECT * FROM AvailabilityTable";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int staffId = rs.getInt("staffMemberId");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                StaffMember sm = StaffMember.load(staffId);
                availabilities.add(new Availability(date, sm));
            }
        });
    }

    public static void updateAddAvailability(Availability a) {
        String query = "INSERT INTO AvailabilityTable (date, staffMemberId) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query, a.getDate().toString(), a.getStaffMember().getContactId());
    }

    public static void updateRevokeAvailability(Availability a) {
        String query = "DELETE FROM AvailabilityTable WHERE date = ? AND staffMemberId = ?";
        PersistenceManager.executeUpdate(query, a.getDate().toString(), a.getStaffMember().getContactId());
    }

    public static void updateRevokeAvailabilityForHoliday(StaffMember sm) {

    }
}