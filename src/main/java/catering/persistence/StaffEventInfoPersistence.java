package catering.persistence;

import catering.businesslogic.staffManagement.ContactedStaff;
import catering.businesslogic.eventManagement.*;
import java.time.LocalDate;

public class StaffEventInfoPersistence implements StaffEventInfoReceiver {

    public void updateNumberOfServers(StaffEventInfo staffEventInfo, int i){
        StaffEventInfo.updateNumberOfServers(staffEventInfo, i);
    }
    public void updateNumberOfKitchenStaff(StaffEventInfo staffEventInfo, int i){
        StaffEventInfo.updateNumberOfKitchenStaff(staffEventInfo, i);
    }

    public void updateLocation(StaffEventInfo staffEventInfo, String location){
        StaffEventInfo.updateLocation(staffEventInfo, location);
    }
    public void updateNumberOfGuests(StaffEventInfo staffEventInfo, int guests){
        StaffEventInfo.updateNumberOfGuests(staffEventInfo, guests);
    }
    public void updateServiceType(StaffEventInfo staffEventInfo, String type){
        StaffEventInfo.updateServiceType(staffEventInfo, type);
    }
    // --------------------

    public void updateModifiedRole(StaffEventInfo staffEventInfo, int index, Role role){
        StaffEventInfo.updateModifiedRole(staffEventInfo, index, role);
    }
    public void updateAddedRole(StaffEventInfo staffEventInfo, Role role){
        StaffEventInfo.updateAddedRole(staffEventInfo, role);
    }
    public void updateDeletedRole(StaffEventInfo staffEventInfo, int index){
        StaffEventInfo.updateDeletedRole(staffEventInfo, index);
    }
    public void updateAddedContactedStaff(StaffEventInfo staffEventInfo, ContactedStaff cont){
        StaffEventInfo.updateAddedContactedStaff(staffEventInfo, cont);
    }
    public void updateDeleteContactedStaff(StaffEventInfo staffEventInfo, int index){
        StaffEventInfo.updateDeleteContactedStaff(staffEventInfo, index);
    }
    public void updateEndDateContactedStaff(StaffEventInfo staffEventInfo, int index, LocalDate endDate){
        StaffEventInfo.updateEndDateContactedStaff(staffEventInfo, index, endDate);
    }
    public void updatePositionContactedStaff(StaffEventInfo staffEventInfo, int index, String position){
        StaffEventInfo.updatePositionContactedStaff(staffEventInfo, index, position);
    }
}