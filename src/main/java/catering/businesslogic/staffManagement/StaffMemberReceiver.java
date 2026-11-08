package catering.businesslogic.staffManagement;

public interface StaffMemberReceiver{
   public void updateChangedName(StaffMember sm, String name);
   public void updateChangedSurname(StaffMember sm, String surname);
   public void updateChangedEmail(StaffMember sm, String email);
   public void updateChangedWhatsapp(StaffMember sm, String whatsapp);

   public void updateChangedAddress(StaffMember sm, String address);
   public void updateChangedFiscalCode(StaffMember sm, String fiscalCode);
   public void updateChangedContact(StaffMember sm, Contact c);
   public void updateChangedPermanent(StaffMember sm, boolean permanent);
   public void updateChangedNumberOfDaysOff(StaffMember sm, int daysOff);

   public void updateAddOnHoliday(StaffMember sm, DateSpan ds);
   public void updateRemoveOldOnHoliday(StaffMember sm, DateSpan ds);
}