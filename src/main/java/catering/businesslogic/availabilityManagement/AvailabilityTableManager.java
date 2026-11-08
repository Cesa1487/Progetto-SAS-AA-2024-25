package catering.businesslogic.availabilityManagement;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.user.User;
import catering.businesslogic.staffManagement.StaffMember;

public class AvailabilityTableManager {

    private AvailabilityTable currentAvailabilityTable;
    private List<AvailabilityTableReceiver> eventReceivers;

    public AvailabilityTableManager() {
        currentAvailabilityTable = AvailabilityTable.getInstance();
        eventReceivers = new ArrayList<AvailabilityTableReceiver>();
    }

    public void addReceiver(AvailabilityTableReceiver atr) {
        if (!eventReceivers.contains(atr))
            eventReceivers.add(atr);
    }

    public void removeReceiver(AvailabilityTableReceiver atr) {
        eventReceivers.remove(atr);
    }

    private void notifyRevokeAvailability(Availability a) {
        for (AvailabilityTableReceiver atr : eventReceivers) {
            atr.updateRevokeAvailability(currentAvailabilityTable, a);
        }
    }

    private void notifyAddAvailability(Availability a) {
        for (AvailabilityTableReceiver atr : eventReceivers) {
            atr.updateAddAvailability(currentAvailabilityTable, a);
        }
    }

    private void notifyRevokeAvailabilityForHoliday(StaffMember sm) {
        for (AvailabilityTableReceiver atr : eventReceivers) {
            atr.updateRevokeAvailabilityForHoliday(currentAvailabilityTable, sm);
        }
    }

    public void addAvailability(StaffMember staff, LocalDate date) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (user.isOwner() || user.isOrganizer()) {
            Availability newAvailability = new Availability(date, staff);

            currentAvailabilityTable.addAvailability(newAvailability);
            notifyAddAvailability(newAvailability);
        } else {
            throw new UseCaseLogicException("Operazione non autorizzata: solo Proprietario o Organizzatore possono aggiungere disponibilità.");
        }
    }

    public void revokeAvailabilityForHoliday(StaffMember sm) {
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if (user.isOwner()) {
            notifyRevokeAvailabilityForHoliday(sm);
            currentAvailabilityTable.revokeAvailabilityForHoliday(sm);
        }
    }

    public void revokeAvailability(Availability a) {
        notifyRevokeAvailability(a);
        currentAvailabilityTable.revokeAvailability(a);
    }

    public void addAvailability(Availability a) {
        currentAvailabilityTable.addAvailability(a);
        notifyAddAvailability(a);
    }

    public void addAvailability(ArrayList<Availability> avList) {
        for (Availability a : avList) {
            currentAvailabilityTable.addAvailability(a);
            notifyAddAvailability(a);
        }
    }

    public List<Availability> getAvailabilityOfStaffMember(StaffMember staffMember) {
        return currentAvailabilityTable.getAvailabilityOfStaffMember(staffMember);
    }

    public Availability getAvailability(int index) {
        return currentAvailabilityTable.getAvailability(index);
    }

    public List<Availability> getAvailabilities() {
        return currentAvailabilityTable.getAvailabilities();
    }
}