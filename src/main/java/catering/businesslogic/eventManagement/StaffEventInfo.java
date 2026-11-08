package catering.businesslogic.eventManagement;

import catering.businesslogic.staffManagement.*;
import catering.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;

public class StaffEventInfo {

    private int eventId;

    private int numberOfServers;
    private int numberOfKitchenStaff;

    private String location;
    private int numberOfGuests;
    private String serviceType;

    private ArrayList<ContactedStaff> contactedStaff = new ArrayList<ContactedStaff>();
    private ArrayList<Role> roles = new ArrayList<Role>();

    public StaffEventInfo(){

    }

    public int getEventId(){
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getNumberOfServers(){
        return this.numberOfServers;
    }
    public void setNumberOfServers(int i){
        numberOfServers = i;
    }

    public int getNumberOfKitchenStaff(){
        return this.numberOfKitchenStaff;
    }
    public void setNumberOfKitchenStaff(int i){
        numberOfKitchenStaff = i;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getServiceType() {
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    // --- Gestione Roles ---
    public ArrayList<Role> getRoles(){
        return this.roles;
    }
    public Role getRole(int index){
        return this.roles.get(index);
    }

    public void setRole(int index, Role role){
        Role oldRole = roles.get(index);
        boolean isOldCook = oldRole.isCook();
        boolean isNewCook = role.isCook();

        if (isOldCook != isNewCook) {
            if (isNewCook) {
                numberOfServers--;
                numberOfKitchenStaff++;
            } else {
                numberOfServers++;
                numberOfKitchenStaff--;
            }
        }
        roles.set(index, role);
    }

    public void addRole(Role role){
        if(role.isCook()){
            numberOfKitchenStaff++;
        }
        else{
            numberOfServers++;
        }
        roles.add(role);
    }

    public void deleteRole(int index){
        if(roles.get(index).isCook()){
            numberOfKitchenStaff--;
        }
        else{
            numberOfServers--;
        }
        roles.remove(index);
    }

    public ArrayList<ContactedStaff> getContactedStaffs(){
        return contactedStaff;
    }

    public ContactedStaff getContactedStaff(int index){
        return contactedStaff.get(index);
    }

    public void addContactedStaff(ContactedStaff cont){
        this.contactedStaff.add(cont);
    }

    public void deleteContactedStaff(int index){
        this.contactedStaff.remove(index);
    }

    public void setEndDateContactedStaff(int index, LocalDate endDate){
        contactedStaff.get(index).setEndDate(endDate);
    }

    public void setPositionContactedStaff(int index, String position){
        contactedStaff.get(index).setPosition(position);
    }


    public boolean containsStaffMember(StaffMember staffMember){
        for(Role r: roles){
            if(r.getContact() != null && r.getContact().equals(staffMember))
                return true;
        }
        return false;
    }

    public List<Role> getRoleStaffMember(StaffMember staffMember){
        List<Role> toRet = new ArrayList<>();
        for(Role r: roles){
            if(r.getContact() != null && r.getContact().equals(staffMember))
                toRet.add(r);
        }
        return toRet;
    }


    public static StaffEventInfo load(int id){
        String query = "SELECT eventId, numberOfServers, numberOfKitchenStaff, location, numberOfGuests, serviceType FROM StaffEventInfos WHERE eventId = ?";
        StaffEventInfo ret = new StaffEventInfo();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                ret.eventId = rs.getInt("eventId");
                ret.numberOfServers = rs.getInt("numberOfServers");
                ret.numberOfKitchenStaff = rs.getInt("numberOfKitchenStaff");

                ret.location = rs.getString("location");
                ret.numberOfGuests = rs.getInt("numberOfGuests");
                ret.serviceType = rs.getString("serviceType");

                ret.contactedStaff = ContactedStaff.load(id);
                ret.roles = Role.load(id);
            }
        }, id);

        return ret;
    }

    public static void updateNumberOfServers(StaffEventInfo staffEventInfo, int i){
        String query = "UPDATE StaffEventInfos SET `numberOfServers` = ? WHERE `eventId` = ?";
        PersistenceManager.executeUpdate(query, i, staffEventInfo.eventId);
    }
    public static void updateNumberOfKitchenStaff(StaffEventInfo staffEventInfo, int i){
        String query = "UPDATE StaffEventInfos SET `numberOfKitchenStaff` = ? WHERE `eventId` = ?";
        PersistenceManager.executeUpdate(query, i, staffEventInfo.eventId);
    }

    public static void updateLocation(StaffEventInfo staffEventInfo, String location){
        String query = "UPDATE StaffEventInfos SET `location` = ? WHERE `eventId` = ?";
        PersistenceManager.executeUpdate(query, location, staffEventInfo.eventId);
    }

    public static void updateNumberOfGuests(StaffEventInfo staffEventInfo, int guests){
        String query = "UPDATE StaffEventInfos SET `numberOfGuests` = ? WHERE `eventId` = ?";
        PersistenceManager.executeUpdate(query, guests, staffEventInfo.eventId);
    }

    public static void updateServiceType(StaffEventInfo staffEventInfo, String serviceType){
        String query = "UPDATE StaffEventInfos SET `serviceType` = ? WHERE `eventId` = ?";
        PersistenceManager.executeUpdate(query, serviceType, staffEventInfo.eventId);
    }


    public static void updateModifiedRole(StaffEventInfo staffEventInfo, int index, Role role){
        Integer contactId = (role.getContact() != null) ? role.getContactId() : null;

        String query = "UPDATE Roles SET `position` = ?, `kitchen` = ?, `staffMemberId` = ?, `eventId` = ? WHERE `eventId` = ? AND `staffMemberId` = ?";
        PersistenceManager.executeUpdate(query, role.getPosition(), role.getKitchen(), contactId, role.getEventId(), role.getEventId(), contactId);
    }

    public static void updateAddedRole(StaffEventInfo staffEventInfo, Role role){
        Integer contactId = (role.getContact() != null) ? role.getContactId() : null;
        String query = "INSERT INTO Roles(position, kitchen, staffMemberId, eventId ) VALUES (?, ?, ?, ?)";
        PersistenceManager.executeUpdate(query, role.getPosition(), role.getKitchen(), contactId, role.getEventId());
    }

    public static void updateDeletedRole(StaffEventInfo staffEventInfo, int index){
        Role toDelete = staffEventInfo.getRole(index);
        Integer contactId = (toDelete.getContact() != null) ? toDelete.getContactId() : null;
        String query = "DELETE FROM Roles WHERE `eventId` = ? AND `staffMemberId` = ?";
        PersistenceManager.executeUpdate(query, toDelete.getEventId(), contactId);
    }

    public static void updateAddedContactedStaff(StaffEventInfo staffEventInfo, ContactedStaff cont){
        String query = "INSERT INTO ContactedStaffs(dateRequest, dateEnd, position, eventId , contactId) VALUES (?, ?, ?, ?, ?)";
        PersistenceManager.executeUpdate(query, cont.getRequestDate(), cont.getEndDate(), cont.getPosition(), cont.getEventId(), cont.getContact().getContactId());
    }

    public static void updateDeleteContactedStaff(StaffEventInfo staffEventInfo, int index){
        ContactedStaff toDelete = staffEventInfo.getContactedStaff(index);
        String query = "DELETE FROM ContactedStaffs WHERE `eventId` = ? AND `contactId` = ?";
        PersistenceManager.executeUpdate(query, toDelete.getEventId(), toDelete.getContact().getContactId());
    }

    public static void updateEndDateContactedStaff(StaffEventInfo staffEventInfo, int index, LocalDate endDate){
        ContactedStaff toUpdate = staffEventInfo.getContactedStaff(index);
        String query = "UPDATE ContactedStaffs SET `dateEnd` = ? WHERE `eventId` = ? AND `contactId` = ?";
        PersistenceManager.executeUpdate(query, endDate, toUpdate.getEventId(), toUpdate.getContact().getContactId());
    }

    public static void updatePositionContactedStaff(StaffEventInfo staffEventInfo, int index, String position){
        ContactedStaff toUpdate = staffEventInfo.getContactedStaff(index);
        String query = "UPDATE ContactedStaffs SET `position` = ? WHERE `eventId` = ? AND `contactId` = ?";
        PersistenceManager.executeUpdate(query, position, toUpdate.getEventId(), toUpdate.getContact().getContactId());
    }
}