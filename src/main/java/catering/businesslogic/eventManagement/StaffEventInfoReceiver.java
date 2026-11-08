package catering.businesslogic.eventManagement;

import catering.businesslogic.staffManagement.ContactedStaff;
import java.time.LocalDate;

public interface StaffEventInfoReceiver {

    public void updateNumberOfServers(StaffEventInfo staffEventInfo,int i);
    public void updateNumberOfKitchenStaff(StaffEventInfo staffEventInfo,int i);
    public void updateModifiedRole(StaffEventInfo staffEventInfo, int index,Role role);
    public void updateAddedRole(StaffEventInfo staffEventInfo,Role role);
    public void updateDeletedRole(StaffEventInfo staffEventInfo,int index);
    public void updateAddedContactedStaff(StaffEventInfo staffEventInfo,ContactedStaff cont);
    public void updateDeleteContactedStaff(StaffEventInfo staffEventInfo,int index);
    public void updateEndDateContactedStaff(StaffEventInfo staffEventInfo, int index, LocalDate endDate);
    public void updatePositionContactedStaff(StaffEventInfo staffEventInfo, int index, String position);
    public void updateLocation(StaffEventInfo sei, String location);
    public void updateNumberOfGuests(StaffEventInfo sei, int guests);
    public void updateServiceType(StaffEventInfo sei, String type);

}