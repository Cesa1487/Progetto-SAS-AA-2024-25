package catering.persistence;

import catering.businesslogic.eventManagement.*;
public class EventInfoPersistence implements EventInfoReceiver{
    @Override
    public void updateNoteAdded(EventInfo eventInfo, Note p){
        EventInfo.updateNoteAdded(eventInfo,p);
    }
    @Override

    public void updateNoteModified(EventInfo eventInfo, int index , Note p){
        EventInfo.updateNoteModified(eventInfo, index, p);
    }
    @Override
    public void updateNoteDeleted(EventInfo eventInfo, int index){
        EventInfo.updateNoteDeleted(eventInfo, index);
    }
    @Override
    public void updateNumGuestsModified(EventInfo eventInfo, int i){
        EventInfo.updateNumGuestsModified(eventInfo, i);
    }
    @Override
    public void updateLocationAdded(EventInfo eventInfo, String s){
        EventInfo.updateLocationAdded(eventInfo, s);
    }
    @Override
    public void updateLocationModified(EventInfo eventInfo, int index, String s){
        EventInfo.updateLocationModified(eventInfo, index, s);
    }
    @Override
    public void updateLocationDeleted(EventInfo eventInfo, int index){
        EventInfo.updateLocationDeleted( eventInfo,  index);
    }
    @Override
    public void updateDescriptionAdded(EventInfo eventInfo, Note p){
        EventInfo.updateDescriptionAdded( eventInfo,  p);
    }
    @Override
    public void updateDescriptionModified(EventInfo eventInfo, int index, Note p){
        EventInfo.updateDescriptionModified( eventInfo,  index, p);
    }
    @Override
    public void updateDescriptionDeleted(EventInfo eventInfo, int index){
        EventInfo.updateDescriptionDeleted( eventInfo,  index);
    }
    @Override
    public void updateTypeOfServiceAdded(EventInfo eventInfo, String s){
        EventInfo.updateTypeOfServiceAdded( eventInfo,  s);
    }
    @Override
    public void updateTypeOfServiceModified(EventInfo eventInfo, int index, String s){
        EventInfo.updateTypeOfServiceModified( eventInfo,  index,  s);
    }
    @Override
    public void updateTypeOfServiceDeleted(EventInfo eventInfo, int index){
        EventInfo.updateTypeOfServiceDeleted( eventInfo,  index);
    }

}