package catering.businesslogic.holidayManagement;

import java.util.*;
import java.time.LocalDate;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Event;
import catering.businesslogic.user.User;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.staffManagement.*;
import catering.businesslogic.availabilityManagement.*;
import catering.businesslogic.eventManagement.EventInfo;
import catering.businesslogic.eventManagement.EventInfoManager;
import catering.businesslogic.shift.ShiftManager;
import catering.util.Pair;

public class HolidayTableManager {

    private ArrayList<HolidayTableReceiver> eventReceivers;
    private HolidayTable currentHolidayTable;

    public HolidayTableManager(){
        eventReceivers = new ArrayList<HolidayTableReceiver>();
        currentHolidayTable = HolidayTable.getInstance();
    }

    public void addReceiver(HolidayTableReceiver htr){
        if(!eventReceivers.contains(htr))
            eventReceivers.add(htr);
    }

    public void removeReceiver(HolidayTableReceiver htr){
        eventReceivers.remove(htr);
    }

    private void notifyAddHolidayRequest(HolidayRequest request){
        for(HolidayTableReceiver htr : eventReceivers){
            htr.updateAddHolidayRequest(currentHolidayTable,request);
        }
    }

    private void notifyChangedHolidayRequest(int index,HolidayRequest request){
        for(HolidayTableReceiver htr : eventReceivers){
            htr.updateChangedHolidayRequest(currentHolidayTable, index, request);
        }
    }

    private void notifyDeletedHolidayRequest(int index){
        for(HolidayTableReceiver htr : eventReceivers){
            htr.updateDeletedHolidayRequest(currentHolidayTable,index);
        }
    }

    public void addHolidayRequest(HolidayRequest request){
        if(request.getStaffMember().getPermanent()){
            currentHolidayTable.addHolidayRequest(request);
            notifyAddHolidayRequest(request);
        }
    }

    public void setHolidayRequest(int index, HolidayRequest request){
        notifyChangedHolidayRequest(index,request);
        currentHolidayTable.setHolidayRequest(index, request);
    }

    public void deleteHolidayRequest(int index){
        notifyDeletedHolidayRequest(index);
        currentHolidayTable.deleteHolidayRequest(index);
    }

    public void acceptOrRefuseHoliday(HolidayRequest holidayRequest, String state) throws UseCaseLogicException {
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();

        if(user == null || !user.isOwner()){
            throw new UseCaseLogicException("Utente non autorizzato: solo il proprietario può gestire le ferie.");
        }
        if(currentHolidayTable == null){
            throw new UseCaseLogicException("Errore di sistema: Tabella Ferie non trovata.");
        }

        int index = currentHolidayTable.getRequests().indexOf(holidayRequest);
        if (index == -1) {
            throw new UseCaseLogicException("Richiesta di ferie non trovata.");
        }

        if (state.equalsIgnoreCase("accepted")) {
            StaffMember staff = holidayRequest.getStaffMember();
            LocalDate start = holidayRequest.getStartDate();
            LocalDate end = holidayRequest.getEndDate();

            ShiftManager shiftManager = application.getShiftManager();
            if (shiftManager != null && shiftManager.hasShiftsInPeriod(staff, start, end)) {
                throw new UseCaseLogicException("Impossibile accettare: Il dipendente ha turni assegnati nel periodo richiesto.");
            }

            EventInfoManager eventInfoMgr = application.getEventInfoManager();
            List<EventInfo> staffEvents = eventInfoMgr.getEventInfoPartecipated(staff);

            for (EventInfo ei : staffEvents) {
                Event evt = Event.loadById(ei.getEventId());

                if (evt != null && evt.getDateStart() != null) {
                    LocalDate eventDate = evt.getDateStart().toLocalDate();

                    if (!eventDate.isBefore(start) && !eventDate.isAfter(end)) {
                        throw new UseCaseLogicException("Impossibile accettare: Il dipendente ha un ruolo assegnato per l'evento '" + evt.getName() + "' del " + eventDate);
                    }
                }
            }

            StaffMemberManager staffManager = application.getStaffMemberManager();
            staffManager.setCurrentStaffMember(staff);
            staffManager.acceptHolidayRequest(holidayRequest);

            application.getAvailabilityTableManager().revokeAvailabilityForHoliday(staff);

            deleteHolidayRequest(index);

        } else if (state.equalsIgnoreCase("refused")) {
            deleteHolidayRequest(index);
        } else {
            throw new UseCaseLogicException("Stato non valido (usa 'accepted' o 'refused')");
        }
    }

    public HolidayTable getHolidayRequests(){
        return this.currentHolidayTable;
    }

    public HolidayRequest getHolidayRequest(int index){
        return this.currentHolidayTable.getHolidayRequest(index);
    }

    public Pair<HolidayRequest, List<Availability>> getHolidayRequestInfo(int index){
        HolidayRequest hr = this.currentHolidayTable.getHolidayRequest(index);
        List<Availability> availabilityList = CatERing.getInstance().getAvailabilityTableManager().getAvailabilityOfStaffMember(hr.getStaffMember());
        return new Pair<HolidayRequest,List<Availability>>(hr, availabilityList);
    }
}