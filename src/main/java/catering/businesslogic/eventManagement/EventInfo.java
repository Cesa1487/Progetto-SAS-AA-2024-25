package catering.businesslogic.eventManagement;
import java.util.ArrayList;
import java.util.List;
import catering.persistence.*;
import java.sql.*;

public class EventInfo {
    private int numGuests;
    private int eventId;
    private ArrayList<String> locations = new ArrayList<String>();
    private ArrayList<String> typeOfService = new ArrayList<String>();
    private StaffEventInfo currentStaffEventInfo;
    private ArrayList<Note> notes = new ArrayList<Note>();
    private ArrayList<Note> description = new ArrayList<Note>();

    public EventInfo(int eventId, int numGuests, List<String> locations, List<String> typeOfService, List<Note> notes, List<Note> description){
        this.description.addAll(description);
        this.notes.addAll(notes);
        this.locations.addAll(locations);
        this.typeOfService.addAll(typeOfService);
        this.numGuests = numGuests;
        this.eventId = eventId;
    }

    public EventInfo(){
    }

    public int getEventId() {
        return eventId;
    }

    //Notes
    public List<Note> getNotes(){
        return this.notes;
    }
    public Note getNote(int index){
        return this.notes.get(index);
    }
    public void addNotes(Note p){
        this.notes.add(p);
    }
    public void setNotes(int index, Note p){
        this.notes.set(index, p);
    }
    public void deleteNotes(int index){
        this.notes.remove(index);
    }
    //misc
    public void setNumGuests(int i ){
        this.numGuests = i;
    }
    public int getNumGuests(){
        return numGuests;
    }

    public List<String> getLocations() {
        return locations;
    }
    public String getLocation(int index){
        return locations.get(index);
    }
    public void setLocations(int index, String s){
        locations.set(index, s);
    }
    public void addLocations(String s){
        locations.add(s);
    }
    public void deleteLocations(int index){
        locations.remove(index);
    }
    public List<Note> getDescriptions(){
        return description;
    }
    public Note getDescription(int index){
        return description.get(index);
    }
    public void addDescription(Note note){
        description.add(note);
    }
    public void setDescription(int index, Note note){
        this.description.set(index, note);
    }
    public void deleteDescription(int index){
        this.description.remove(index);
    }
    //service
    public List<String> getTypeOfServices(){
        return typeOfService;
    }
    public String getTypeOfService(int index){
        return typeOfService.get(index);
    }
    public void setTypeOfService(int index, String service){
        this.typeOfService.set(index, service);
    }
    public void addTypeOfService(String service){
        this.typeOfService.add(service);
    }
    public void deleteTypeOfService(int index){
        this.typeOfService.remove(index);
    }
    //staff
    public StaffEventInfo getStaffEventInfo(){
        return currentStaffEventInfo;
    }


    /** DATABASE OPERATIONS */

    public static EventInfo load(int id){
        String query = "SELECT eventId, numGuests FROM EventInfos WHERE eventId = ?";
        EventInfo ret = new EventInfo();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                ret.eventId = rs.getInt("eventId");
                ret.numGuests = rs.getInt("numGuests");
                ret.locations = EventInfo.loadLocations(id);
                ret.typeOfService = EventInfo.loadTypeOfService(id);
                ret.notes = EventInfo.loadNotes(id);
                ret.description = EventInfo.loadDescriptions(id);

                ret.currentStaffEventInfo = StaffEventInfo.load(id);
            }
        }, id);

        return ret;
    }

    public static List<EventInfo> loadAll() {
        String query = "SELECT eventId, numGuests FROM EventInfos";
        List<EventInfo> results = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                do{
                    EventInfo info = new EventInfo();
                    int id = rs.getInt("eventId");
                    info.eventId = id;
                    info.numGuests = rs.getInt("numGuests");

                    // Carica campi secondari
                    info.locations = EventInfo.loadLocations(id);
                    info.typeOfService = EventInfo.loadTypeOfService(id);
                    info.notes = EventInfo.loadNotes(id);
                    info.description = EventInfo.loadDescriptions(id);
                    info.currentStaffEventInfo = StaffEventInfo.load(id);

                    results.add(info);
                }while (rs.next());
            }
        });

        return results;
    }

    public static ArrayList<String> loadLocations(int id) {
        String query = "SELECT eventId, location FROM EventInfoLocations WHERE eventId = ?";
        ArrayList<String> ret = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                do{
                    ret.add(rs.getString("location"));
                }while(rs.next());
            }
        }, id);

        return ret;
    }
    public static ArrayList<String> loadTypeOfService(int id){
        String query = "SELECT eventId, typeOfService FROM EventInfoTypeOfServices WHERE eventId = ?";
        ArrayList<String> ret = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(rs.getString("typeOfService"));
                }while(rs.next());
            }
        }, id);

        return ret;
    }
    public static ArrayList<Note> loadNotes(int id){
        String query = "SELECT eventId, title, body FROM EventInfoNotes WHERE eventId = ?";
        ArrayList<Note> ret = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new Note(rs.getString("title"),rs.getString("body")));
                }while(rs.next());
            }
        }, id);

        return ret;
    }
    public static ArrayList<Note> loadDescriptions(int id){
        String query = "SELECT eventId, title, body FROM EventInfoDescriptions WHERE eventId = ?";
        ArrayList<Note> ret = new ArrayList<Note>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new Note(rs.getString("title"),rs.getString("body")));
                }while(rs.next());
            }
        }, id);

        return ret;
    }

    public static void updateNoteAdded(EventInfo eventInfo, Note p){
        String query = "INSERT INTO EventInfoNotes(eventId, title, body) VALUES (?, ?, ?)";
        PersistenceManager.executeUpdate(query, eventInfo.eventId,p.getTitle(),p.getText());
    }
    public static void updateNoteModified(EventInfo eventInfo, int index, Note p){
        String query = "UPDATE EventInfoNotes  SET `title` = ?, `body` = ?  WHERE `eventId` = ? AND `title` = ? AND `body` = ?";
        Note toUpdate = eventInfo.getNote(index);
        PersistenceManager.executeUpdate(query,p.getTitle(),p.getText(),eventInfo.eventId,toUpdate.getTitle(),toUpdate.getText());
    }
    public static void updateNoteDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoNotes WHERE `eventId` = ? AND `title` = ? AND `body` = ?";
        Note toDelete = eventInfo.getNote(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete.getTitle(),toDelete.getText());
    }

    public static void updateNumGuestsModified(EventInfo eventInfo, int i){
        String query = "UPDATE EventInfos SET `numGuests` = ? WHERE `eventId` = ?";
        PersistenceManager.executeUpdate(query,i,eventInfo.eventId);
    }

    public static void updateLocationAdded(EventInfo eventInfo, String s){
        String query = "INSERT INTO EventInfoLocations(eventId, location) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query,eventInfo.eventId,s);
    }
    public static void updateLocationModified(EventInfo eventInfo, int index, String s){
        String query = "UPDATE EventInfoLocations SET `location` = ? WHERE `eventId` = ? AND `location` = ?";
        String toUpdate = eventInfo.getLocation(index);
        PersistenceManager.executeUpdate(query,s,eventInfo.eventId,toUpdate);
    }
    public static void updateLocationDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoLocations WHERE `eventId` = ? AND `location` = ?";
        String toDelete = eventInfo.getLocation(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete);
    }

    public static void updateDescriptionAdded(EventInfo eventInfo, Note p){
        String query = "INSERT INTO EventInfoDescriptions(eventId, title, body) VALUES (?, ?, ?)";
        PersistenceManager.executeUpdate(query, eventInfo.eventId,p.getTitle(),p.getText());
    }
    public static void updateDescriptionModified(EventInfo eventInfo, int index, Note p){
        String query = "UPDATE EventInfoDescriptions  SET `title` = ?, `body` = ?  WHERE `eventId` = ? AND `title` = ? AND `body` = ?";
        Note toUpdate = eventInfo.getDescription(index);
        PersistenceManager.executeUpdate(query,p.getTitle(),p.getText(),eventInfo.eventId,toUpdate.getTitle(),toUpdate.getText());
    }
    public static void updateDescriptionDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoDescriptions WHERE `eventId` = ? AND `title` = ? AND `body` = ?";
        Note toDelete = eventInfo.getDescription(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete.getTitle(),toDelete.getText());
    }

    public static void updateTypeOfServiceAdded(EventInfo eventInfo, String s){
        String query = "INSERT INTO EventInfoTypeOfServices(eventId, typeOfService) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query,eventInfo.eventId,s);
    }
    public static void updateTypeOfServiceModified(EventInfo eventInfo, int index, String s){
        String query = "UPDATE EventInfoTypeOfServices SET `typeOfService` = ? WHERE `eventId` = ? AND `typeOfService` = ?";
        String toUpdate = eventInfo.getTypeOfService(index);
        PersistenceManager.executeUpdate(query,s,eventInfo.eventId,toUpdate);
    }
    public static void updateTypeOfServiceDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoTypeOfServices WHERE `eventId` = ? AND `typeOfService` = ?";
        String toDelete = eventInfo.getTypeOfService(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete);
    }
}