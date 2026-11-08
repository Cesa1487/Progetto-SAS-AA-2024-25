package catering.businesslogic.eventManagement;

public interface EventInfoReceiver{
    public void updateNoteAdded(EventInfo eventInfo, Note p);
    public void updateNoteModified(EventInfo eventInfo, int index , Note p);
    public void updateNoteDeleted(EventInfo eventInfo, int index);
    public void updateNumGuestsModified(EventInfo eventInfo, int i);
    public void updateLocationAdded(EventInfo eventInfo, String s);
    public void updateLocationModified(EventInfo eventInfo, int index, String s);
    public void updateLocationDeleted(EventInfo eventInfo, int index);
    public void updateDescriptionAdded(EventInfo eventInfo, Note p);
    public void updateDescriptionModified(EventInfo eventInfo, int index, Note p);
    public void updateDescriptionDeleted(EventInfo eventInfo, int index);
    public void updateTypeOfServiceAdded(EventInfo eventInfo, String s);
    public void updateTypeOfServiceModified(EventInfo eventInfo, int index, String s);
    public void updateTypeOfServiceDeleted(EventInfo eventInfo, int index);
}