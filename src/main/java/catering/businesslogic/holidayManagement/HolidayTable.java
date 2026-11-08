package catering.businesslogic.holidayManagement;

import catering.businesslogic.staffManagement.StaffMember;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HolidayTable {
    // Singleton
    private static HolidayTable singleInstance;

    public static HolidayTable getInstance() {
        if (singleInstance == null) {
            singleInstance = new HolidayTable();
        }
        return singleInstance;
    }

    private ArrayList<HolidayRequest> requests = new ArrayList<>();

    private HolidayTable() {
        loadHolidayRequests();
    }

    public ArrayList<HolidayRequest> getRequests() {
        return requests;
    }

    public HolidayRequest getHolidayRequest(int index) {
        return requests.get(index);
    }

    public void addHolidayRequest(HolidayRequest request) {
        requests.add(request);
    }

    public void deleteHolidayRequest(int index) {
        requests.remove(index);
    }

    public void setHolidayRequest(int index, HolidayRequest request) {
        requests.set(index, request);
    }

    private void loadHolidayRequests() {
        String query = "SELECT * FROM HolidayRequests";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int staffId = rs.getInt("staffMemberId");
                StaffMember applicant = StaffMember.load(staffId);
                LocalDate start = LocalDate.parse(rs.getString("fromDate"));
                LocalDate end = LocalDate.parse(rs.getString("toDate"));

                String status = rs.getString("status");
                if (status == null) status = "PENDING"; // Default fallback

                HolidayRequest req = new HolidayRequest(new catering.businesslogic.staffManagement.DateSpan(start, end), applicant, status);
                requests.add(req);
            }
        });
    }

    public static void updateAddHolidayRequest(HolidayTable holidayTable, HolidayRequest request) {
        String query = "INSERT INTO HolidayRequests (staffMemberId, fromDate, toDate, status) VALUES (?, ?, ?, ?)";
        PersistenceManager.executeUpdate(query,
                request.getStaffMember().getContactId(),
                request.getStartDate().toString(),
                request.getEndDate().toString(),
                request.getStatus()
        );
    }

    public static void updateChangedHolidayRequest(HolidayTable holidayTable, int index, HolidayRequest request) {
    }

    public static void updateDeletedHolidayRequest(HolidayTable holidayTable, int index) {
        HolidayRequest req = holidayTable.getHolidayRequest(index);
        String query = "DELETE FROM HolidayRequests WHERE staffMemberId = ? AND fromDate = ? AND toDate = ?";
        PersistenceManager.executeUpdate(query, req.getStaffMember().getContactId(), req.getStartDate().toString(), req.getEndDate().toString());
    }
}