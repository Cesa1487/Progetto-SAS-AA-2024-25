package catering.businesslogic.eventManagement;
import catering.businesslogic.staffManagement.StaffMember;

import java.sql.*;
import catering.persistence.*;

import java.util.ArrayList;

public class Role {

    private int eventId;
    private String position;
    private StaffMember staffMember;
    private boolean kitchen;

    public Role(int eventId, String position, StaffMember staffMember, boolean kitchen){
        this.eventId = eventId;
        this.position = position;
        this.staffMember = staffMember;
        this.kitchen = kitchen;
    }

    public Role(int eventId, String position, boolean kitchen){
        this.eventId = eventId;
        this.position = position;
        this.kitchen = kitchen;
        this.staffMember = null;
    }

    public Role(String position){
        this.position = position;
    }

    public String getPosition(){
        return this.position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public StaffMember getContact(){
        return this.staffMember;
    }

    public int getContactId(){
        if (staffMember == null) {
            return 0;
        }
        return staffMember.getContactId();
    }

    public void setContact(StaffMember staffMember){
        this.staffMember = staffMember;
    }

    public boolean getKitchen(){
        return this.kitchen;
    }

    public boolean isCook(){
        return kitchen;
    }

    public boolean isServer(){
        return !kitchen;
    }

    public int getEventId(){
        return eventId;
    }

    /** DATABASE OPERATIONS */

    public static ArrayList<Role> load(int id){
        String query = "SELECT `position`, `kitchen`, `staffMemberId`, `eventId` FROM Roles WHERE `eventId` = ?";
        ArrayList<Role> ret = new ArrayList<Role>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do {
                    int staffId = rs.getInt("staffMemberId");
                    StaffMember sm = null;

                    if (staffId > 0) {
                        sm = StaffMember.load(staffId);
                    }

                    ret.add(new Role(
                            rs.getInt("eventId"),
                            rs.getString("position"),
                            sm,
                            rs.getBoolean("kitchen")
                    ));
                } while(rs.next());
            }
        }, id);

        return ret;
    }

    public String toString(){
        String staffName = (staffMember != null) ? (staffMember.getName() + " " + staffMember.getSurname()) : "NON ASSEGNATO";
        return position + " | " + staffName;
    }
}