package catering.businesslogic.availabilityManagement;

import catering.businesslogic.staffManagement.StaffMember;

public interface AvailabilityTableReceiver{
    public void updateRevokeAvailability(AvailabilityTable availabilityTable, Availability a);
    public void updateAddAvailability(AvailabilityTable availabilityTable, Availability a);
    public void updateRevokeAvailabilityForHoliday(AvailabilityTable availabilityTable, StaffMember sm);
}