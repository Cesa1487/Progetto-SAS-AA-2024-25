package catering.businesslogic.eventManagement;

import java.util.ArrayList;
import java.util.List;
import catering.businesslogic.CatERing;
import catering.businesslogic.user.User;
import catering.businesslogic.staffManagement.*;

public class EventInfoManager{
    
    private EventInfo currentEventInfo;
    private ArrayList<EventInfoReceiver> eventReceivers = new ArrayList<EventInfoReceiver>();
   
    public void setCurrentEventInfo(EventInfo currentEventInfo){
        this.currentEventInfo = currentEventInfo;
    }
    public EventInfo getCurrentEventInfo(){
        return currentEventInfo;
    }

    // event sender methods
    public void addReceiver(EventInfoReceiver eir){
        this.eventReceivers.add(eir);
    }
    public void removeReceiver(EventInfoReceiver eir){
        this.eventReceivers.remove(eir);
    }
    private void notifyNoteAdded(Note p){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNoteAdded(currentEventInfo,p);
        }
    }
    private void notifyNoteModified(int index, Note p){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNoteModified(currentEventInfo, index, p);
        }
    }
    private void notifyNoteDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNoteDeleted(currentEventInfo, index);
        }
    }
    private void notifyNumGuestsModified(int newNumGuest){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNumGuestsModified(currentEventInfo,newNumGuest);
        }
    }
    private void notifyLocationAdded(String location){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateLocationAdded(currentEventInfo, location);
        }
    }
    private void notifyLocationModified(int index, String newLocation){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateLocationModified(currentEventInfo, index, newLocation);
        }
    }
    private void notifyLocationDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateLocationDeleted(currentEventInfo, index);
        }
    }
    private void notifyDescriptionAdded(Note p){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateDescriptionAdded(currentEventInfo, p);
        }
    }
    private void notifyDescriptionModified(int index, Note p){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateDescriptionModified(currentEventInfo, index, p);
        }
    }
    private void notifyDescriptionDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateDescriptionDeleted(currentEventInfo, index);
        }
    }
    private void notifyTypeOfServiceAdded(String s){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateTypeOfServiceAdded(currentEventInfo, s);
        }
    }
    private void notifyTypeOfServiceModified(int index, String newTypeOfService){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateTypeOfServiceModified(currentEventInfo, index, newTypeOfService);
        }
    }
    private void notifyTypeOfServiceDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateTypeOfServiceDeleted(currentEventInfo, index);
        } 
    }

   
    // *operations methods*
    public void addNotes(Note notes){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addNotes(notes);
            notifyNoteAdded(notes);
        }
    }
    public List<Note> getNotes(){
       return currentEventInfo.getNotes();
    }
    public Note getNote(int index){
       return currentEventInfo.getNote(index);
    }
    public void setNotes(int index, Note p){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyNoteModified(index, p);
            currentEventInfo.setNotes(index, p);
        }
    }
    public void deleteNotes(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyNoteDeleted(index);
            currentEventInfo.deleteNotes(index);
        }
    }  	
    public void setNumGuests(int newNumGuest){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.setNumGuests(newNumGuest);
            notifyNumGuestsModified(newNumGuest);
        }
    } 
    public int getNumGuests(){
        return currentEventInfo.getNumGuests();
    }
    public void setLocations(int index, String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyLocationModified(index, s);
            currentEventInfo.setLocations(index, s);
        }
    }
    public List<String> getLocations(){
        return currentEventInfo.getLocations();
    }
    public String getLocation(int index){
        return currentEventInfo.getLocation(index);
    }
    public void addLocations(String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addLocations(s);
            notifyLocationAdded(s);
        }
    }
    public void deleteLocations(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyLocationDeleted(index);
            currentEventInfo.deleteLocations(index);
        }
    } 	
    public void addDescription(Note p){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addDescription(p);
            notifyDescriptionAdded(p);
        }
    }
    public void setDescription(int index, Note p){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyDescriptionModified(index, p);
            currentEventInfo.setDescription(index, p);
        }
    }
    
    public List<Note> getDescriptions(){
        return currentEventInfo.getDescriptions();
    }
    
    public Note getDescription(int index){
        return currentEventInfo.getDescription(index);
    }
    
    public void deleteDescription(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
             notifyDescriptionDeleted(index);
            currentEventInfo.deleteDescription(index);
        }
    }  	
    public void setTypeOfService(int index, String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyTypeOfServiceModified(index, s);
            currentEventInfo.setTypeOfService(index, s);
        }
    }
    public void addTypeOfService(String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addTypeOfService(s);
            notifyTypeOfServiceAdded(s);
        }
    }
    public void deleteTypeOfService(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyTypeOfServiceDeleted(index);
            currentEventInfo.deleteTypeOfService(index);
        }
    }
    public List<String> getTypeOfServices(){
        return currentEventInfo.getTypeOfServices();
    }
    public String getTypeOfService(int index){
        return currentEventInfo.getTypeOfService(index);
    }
    public void setCurrentStaffEventInfo(){
        CatERing application = CatERing.getInstance();
        StaffEventInfoManager seim = application.getStaffEventInfoManager();
        seim.setCurrentStaffEventInfo(currentEventInfo.getStaffEventInfo());
    }

    public List<EventInfo> getEventInfoPartecipated(StaffMember staffmember){
        return EventInfo.loadAll();
    }
}