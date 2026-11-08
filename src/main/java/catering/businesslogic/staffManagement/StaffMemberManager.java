package catering.businesslogic.staffManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.availabilityManagement.*;
import catering.businesslogic.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import catering.businesslogic.eventManagement.*;
import catering.businesslogic.holidayManagement.HolidayRequest;
import catering.util.*;

public class StaffMemberManager {

    private StaffMember currentStaffMember;
    private ArrayList<StaffMemberReceiver> eventReceivers = new ArrayList<>();

    public void registerPermanentStaffData(StaffMember staff, String address, String fiscalCode) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (user == null || !user.isOwner()) {
            throw new UseCaseLogicException("Operazione non autorizzata: solo il Proprietario può registrare i dati del personale permanente.");
        }

        this.currentStaffMember = staff;

        this.currentStaffMember.setAddress(address);
        notifyChangedAddress(address);

        this.currentStaffMember.setFiscalCode(fiscalCode);
        notifyChangedFiscalCode(fiscalCode);

        this.currentStaffMember.setPermanent(true);
        notifyChangedPermanent(true);
    }


    public void addReceiver(StaffMemberReceiver smr){
        eventReceivers.add(smr);
    }
    public void removeReceiver(StaffMemberReceiver smr){
        eventReceivers.remove(smr);
    }

    private void notifyChangedName(String name){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedName(currentStaffMember, name);
        }
    }
    private void notifyChangedSurname(String surname){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedSurname(currentStaffMember,surname);
        }
    }
    private void notifyChangedEmail(String email){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedEmail(currentStaffMember,email);
        }
    }
    private void notifyChangedWhatsapp(String whatsapp){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedWhatsapp(currentStaffMember,whatsapp);
        }
    }
    private void notifyChangedAddress(String address){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedAddress(currentStaffMember, address);
        }
    }
    private void notifyChangedFiscalCode(String FiscalCode){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedFiscalCode(currentStaffMember, FiscalCode);
        }
    }
    private void notifyChangedContact(Contact contact){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedContact(currentStaffMember, contact);
        }
    }
    private void notifyChangedPermanent(boolean b){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedPermanent(currentStaffMember, b);
        }
    }
    private void notifyAddOnHoliday(DateSpan ds){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateAddOnHoliday(currentStaffMember, ds);
        }
    }
    private void notifyRemoveOldOnHoliday(DateSpan ds){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateRemoveOldOnHoliday(currentStaffMember, ds);
        }
    }
    private void notifyChangedNumberOfDaysOff(int daysOff){
        for(StaffMemberReceiver smr : eventReceivers){
            smr.updateChangedNumberOfDaysOff(currentStaffMember, daysOff);
        }
    }


    public StaffMember getCurrentStaffMember(){
        return currentStaffMember;
    }

    public void setCurrentStaffMember(StaffMember staffMember){
        this.currentStaffMember = staffMember;
    }

    public void setName(String name){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setName(name);
            notifyChangedName(name);
        }
    }
    public String getName(){
        return currentStaffMember.getName();
    }

    public void setSurname(String surname){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setSurname(surname);
            notifyChangedSurname(surname);
        }
    }
    public String getSurname(){
        return currentStaffMember.getSurname();
    }

    public void setEmail(String email){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setEmail(email);
            notifyChangedEmail(email);
        }
    }
    public String getEmail(){
        return currentStaffMember.getEmail();
    }
    public void setWhatsapp(String whatsapp){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setWhatsapp(whatsapp);
            notifyChangedWhatsapp(whatsapp);
        }
    }
    public String getWhatsapp(){
        return currentStaffMember.getWhatsapp();
    }
    public void setAddress(String address){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setAddress(address);
            notifyChangedAddress(address);
        }
    }
    public String getAddress(){
        return currentStaffMember.getAddress();
    }
    public void setFiscalCode(String fiscalCode){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setFiscalCode(fiscalCode);
            notifyChangedFiscalCode(fiscalCode);;
        }
    }
    public String getFiscalCode(){
        return currentStaffMember.getFiscalCode();
    }
    public void setContact(Contact contact){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            contact.setId(currentStaffMember.getContactId());
            notifyChangedContact(contact);
            this.currentStaffMember.setContact(contact);
        }
    }
    public Contact getContact(){
        return currentStaffMember.getContact();
    }
    public void setPermanent(boolean permanent){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.setPermanent(permanent);
            notifyChangedPermanent(permanent);
        }
    }

    public void addOnHoliday(DateSpan ds){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            this.currentStaffMember.addOnHoliday(ds);
            notifyAddOnHoliday(ds);
            removeOldOnHoliday();
        }
    }
    public void removeOldOnHoliday(){
        LocalDate now = LocalDate.now();

        List<DateSpan> toRemove = new ArrayList<>();

        for (DateSpan ds : this.currentStaffMember.getHolidays()) {
            if (ds.getTo().compareTo(now) < 0) {
                toRemove.add(ds);
            }
        }

        for (DateSpan ds : toRemove) {
            this.currentStaffMember.removeOnHoliday(ds);
            notifyRemoveOldOnHoliday(ds);
        }
    }
    public void setNumberOfDaysOff(int daysOff){
        if(daysOff < 0)
            this.currentStaffMember.setNumberOfDaysOff(0);
        else
            this.currentStaffMember.setNumberOfDaysOff(daysOff);
        notifyChangedNumberOfDaysOff(daysOff);
    }
    public int getNumberOfDaysOff(){
        return currentStaffMember.getNumberOfDaysOff();
    }

    public ArrayList<DateSpan> getHolidays(){
        return currentStaffMember.getHolidays();
    }

    public boolean isOnHoliday(){
        return currentStaffMember.isOnHoliday();
    }

    public void acceptHolidayRequest(HolidayRequest hr){

        if(hr.getStaffMember().equals(currentStaffMember)){
            addOnHoliday(hr.getSpan());
            setNumberOfDaysOff(getNumberOfDaysOff() - hr.getSpan().getlength());
            AvailabilityTableManager atm = CatERing.getInstance().getAvailabilityTableManager();

            atm.revokeAvailabilityForHoliday(currentStaffMember);
        }
    }

    public Pair<List<Role>,List<Note>> getStatistics(StaffMember staffMember){
        AvailabilityTableManager atm = CatERing.getInstance().getAvailabilityTableManager();
        List<Role> roleList = new ArrayList<>();
        EventInfoManager eim =  CatERing.getInstance().getEventInfoManager();
        List<EventInfo> eventInfoList = eim.getEventInfoPartecipated(staffMember);
        List<Note> noteList = new ArrayList<>();
        for(EventInfo ei: eventInfoList){
            if(ei.getStaffEventInfo().containsStaffMember(staffMember)){
                noteList.addAll(ei.getNotes());
                roleList.addAll(ei.getStaffEventInfo().getRoleStaffMember(staffMember));
            }
        }
        noteList.add(new Note("NUMERO TOTALE DI DISPONIBILITÀ",String.format("%d",atm.getAvailabilityOfStaffMember(staffMember).size())));
        return new Pair<List<Role>,List<Note>>(roleList, noteList);
    }
}