package catering.persistence;

import catering.businesslogic.staffManagement.*;

public class StaffMemberPersistence implements StaffMemberReceiver {

    @Override
    public void updateChangedName(StaffMember sm, String name) {
        StaffMember.updateChangedName(sm, name);
    }

    @Override
    public void updateChangedSurname(StaffMember sm, String surname) {
        StaffMember.updateChangedSurname(sm, surname);
    }

    @Override
    public void updateChangedEmail(StaffMember sm, String email) {
        StaffMember.updateChangedEmail(sm, email);
    }

    @Override
    public void updateChangedWhatsapp(StaffMember sm, String whatsapp) {
        StaffMember.updateChangedWhatsapp(sm, whatsapp);
    }

    @Override
    public void updateChangedAddress(StaffMember sm, String address) {
        StaffMember.updateChangedAddress(sm, address);
    }

    @Override
    public void updateChangedFiscalCode(StaffMember sm, String fiscalCode) {
        StaffMember.updateChangedFiscalCode(sm, fiscalCode);
    }

    @Override
    public void updateChangedContact(StaffMember sm, Contact c) {
        StaffMember.updateChangedContact(sm, c);
    }

    @Override
    public void updateChangedPermanent(StaffMember sm, boolean permanent) {
        StaffMember.updateChangedPermanent(sm, permanent);
    }

    @Override
    public void updateChangedNumberOfDaysOff(StaffMember sm, int daysOff) {
        StaffMember.updateChangedNumberOfDaysOff(sm, daysOff);
    }

    @Override
    public void updateAddOnHoliday(StaffMember sm, DateSpan ds) {
        StaffMember.updateAddOnHoliday(sm, ds);
    }

    @Override
    public void updateRemoveOldOnHoliday(StaffMember sm, DateSpan ds) {
        StaffMember.updateRemoveOldOnHoliday(sm, ds);
    }
}