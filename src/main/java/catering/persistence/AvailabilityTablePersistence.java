package catering.persistence;

import catering.businesslogic.availabilityManagement.*;
import catering.businesslogic.staffManagement.StaffMember;

public class AvailabilityTablePersistence implements AvailabilityTableReceiver {

    @Override
    public void updateAddAvailability(AvailabilityTable at, Availability a) {
        AvailabilityTable.updateAddAvailability(a);
    }

    @Override
    public void updateRevokeAvailability(AvailabilityTable at, Availability a) {
        AvailabilityTable.updateRevokeAvailability(a);
    }

    @Override
    public void updateRevokeAvailabilityForHoliday(AvailabilityTable at, StaffMember sm) {
        AvailabilityTable.updateRevokeAvailabilityForHoliday(sm);
    }
}