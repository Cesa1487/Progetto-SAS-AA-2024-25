package catering.businesslogic.eventManagement;

import java.time.LocalDate;
import java.util.ArrayList;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.staffManagement.ContactedStaff;
import catering.businesslogic.staffManagement.Contact;
import catering.businesslogic.staffManagement.StaffMember;
import catering.businesslogic.user.User;

public class StaffEventInfoManager {

    private StaffEventInfo currentStaffEventInfo;
    private ArrayList<StaffEventInfoReceiver> eventReceivers = new ArrayList<>();

    // --- 1. createStaffEventInfo (Creazione Scheda) ---
    public void createStaffEventInfo(Event event) throws UseCaseLogicException {
        checkAuthorization();
        this.currentStaffEventInfo = new StaffEventInfo();
        this.currentStaffEventInfo.setEventId(event.getId());
    }

    public StaffEventInfo getCurrentStaffEventInfo() {
        return currentStaffEventInfo;
    }

    public void setCurrentStaffEventInfo(StaffEventInfo currStaffEventInfo) {
        this.currentStaffEventInfo = currStaffEventInfo;
    }

    // --- 2. impostaInfoEvento (Passo 2 e Estensioni 2a/2b) ---
    public void impostaInfoEvento(int numKitchen, int numSala, String location, int numGuests, String serviceType) throws UseCaseLogicException {
        checkAuthorization();

        if (currentStaffEventInfo == null) {
            throw new UseCaseLogicException("Nessuna scheda evento selezionata.");
        }

        // Imposta i valori
        this.currentStaffEventInfo.setNumberOfKitchenStaff(numKitchen);
        this.currentStaffEventInfo.setNumberOfServers(numSala);

        // CORREZIONE QUI: Usa setNumberOfGuests invece di setNumGuests
        this.currentStaffEventInfo.setLocation(location);
        this.currentStaffEventInfo.setNumberOfGuests(numGuests);
        this.currentStaffEventInfo.setServiceType(serviceType);

        // Notifica aggiornamenti
        notifyNumberOfKitchenStaff();
        notifyNumberOfServers();
    }

    // --- 3. defineRole (Passo 3: Definizione Ruolo) ---
    public void defineRole(String position, boolean isKitchen) throws UseCaseLogicException {
        checkAuthorization();

        // Crea un ruolo "vuoto" (senza staff assegnato)
        Role newRole = new Role(currentStaffEventInfo.getEventId(), position, null, isKitchen);

        this.currentStaffEventInfo.addRole(newRole);
        notifyAddedRole(newRole);

        // Aggiorna i conteggi in base al tipo di ruolo aggiunto
        if(isKitchen) notifyNumberOfKitchenStaff();
        else notifyNumberOfServers();
    }

    // --- 5. assignRole (Passo 5: Assegnazione Personale) ---
    public void assignRole(int roleIndex, StaffMember staff) throws UseCaseLogicException {
        checkAuthorization();

        if (roleIndex < 0 || roleIndex >= currentStaffEventInfo.getRoles().size()) {
            throw new UseCaseLogicException("Indice ruolo non valido");
        }

        // CONTROL: Verifica se la persona è in ferie (dal tuo Contratto p.22)
        // Nota: Idealmente dovresti passare la data dell'evento a isOnHoliday
        if (staff.isOnHoliday()) {
            throw new UseCaseLogicException("Il membro dello staff è in ferie o non disponibile.");
        }

        // CONTROL: Verifica se la persona è già assegnata in questo evento
        if (currentStaffEventInfo.containsStaffMember(staff)) {
            throw new UseCaseLogicException("Il membro dello staff è già assegnato a un ruolo in questo evento.");
        }

        Role role = currentStaffEventInfo.getRole(roleIndex);
        role.setContact(staff); // Assegna il membro dello staff

        notifyModifiedRoles(roleIndex, role);
    }

    // --- 6. contactOccasionalStaff (Passo 6: Contatta Occasionale) ---
    public void contactOccasionalStaff(Contact contact, LocalDate requestDate, LocalDate deadline) throws UseCaseLogicException {
        checkAuthorization();

        // Usa il costruttore ridotto di ContactedStaff
        ContactedStaff cs = new ContactedStaff(currentStaffEventInfo.getEventId(), contact, requestDate, deadline);

        this.currentStaffEventInfo.addContactedStaff(cs);
        notifyAddedContactedStaff(cs);
    }

    // --- Metodi di gestione generici (Delete, Getters) ---

    public void deleteRole(int index) throws UseCaseLogicException {
        checkAuthorization();
        notifyDeletedRole(index);
        currentStaffEventInfo.deleteRole(index);
        notifyNumberOfServers();
        notifyNumberOfKitchenStaff();
    }

    public void deleteContactedStaff(int index) throws UseCaseLogicException {
        checkAuthorization();
        notifyDeleteContactedStaff(index);
        currentStaffEventInfo.deleteContactedStaff(index);
    }

    public void setEndDateContactedStaff(int index, LocalDate localDate) throws UseCaseLogicException {
        checkAuthorization();
        currentStaffEventInfo.setEndDateContactedStaff(index, localDate);
        notifyEndDateContactedStaff(index, localDate);
    }

    public void setPositionContactedStaff(int index, String position) throws UseCaseLogicException {
        checkAuthorization();
        currentStaffEventInfo.setPositionContactedStaff(index, position);
        notifyPositionContactedStaff(index, position);
    }

    // Getters
    public Role getRole(int index) { return currentStaffEventInfo.getRole(index); }
    public ArrayList<Role> getRoles() { return currentStaffEventInfo.getRoles(); }
    public int getNumberOfServers() { return currentStaffEventInfo.getNumberOfServers(); }
    public int getNumberOfKitchenStaff() { return currentStaffEventInfo.getNumberOfKitchenStaff(); }
    public ArrayList<ContactedStaff> getContactedStaffs() { return currentStaffEventInfo.getContactedStaffs(); }
    public ContactedStaff getContactedStaff(int index) { return currentStaffEventInfo.getContactedStaff(index); }
    public int getEventId() { return currentStaffEventInfo.getEventId(); }


    // --- Gestione Receivers (Observer Pattern) ---

    public void addReceiver(StaffEventInfoReceiver seir) { eventReceivers.add(seir); }
    public void removeReceiver(StaffEventInfoReceiver seir) { eventReceivers.remove(seir); }

    private void notifyNumberOfServers() {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateNumberOfServers(currentStaffEventInfo, currentStaffEventInfo.getNumberOfServers());
    }
    private void notifyNumberOfKitchenStaff() {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateNumberOfKitchenStaff(currentStaffEventInfo, currentStaffEventInfo.getNumberOfKitchenStaff());
    }
    private void notifyModifiedRoles(int index, Role role) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateModifiedRole(currentStaffEventInfo, index, role);
    }
    private void notifyAddedRole(Role role) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateAddedRole(currentStaffEventInfo, role);
    }
    private void notifyDeletedRole(int index) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateDeletedRole(currentStaffEventInfo, index);
    }
    private void notifyAddedContactedStaff(ContactedStaff cont) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateAddedContactedStaff(currentStaffEventInfo, cont);
    }
    private void notifyDeleteContactedStaff(int index) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateDeleteContactedStaff(currentStaffEventInfo, index);
    }
    private void notifyEndDateContactedStaff(int index, LocalDate endDate) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updateEndDateContactedStaff(currentStaffEventInfo, index, endDate);
    }
    private void notifyPositionContactedStaff(int index, String position) {
        for (StaffEventInfoReceiver seir : eventReceivers) seir.updatePositionContactedStaff(currentStaffEventInfo, index, position);
    }

    // --- Helper Sicurezza ---
    private void checkAuthorization() throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (user == null) {
            throw new UseCaseLogicException("Utente non loggato");
        }
        if (!user.isOwner() && !user.isOrganizer()) {
            throw new UseCaseLogicException("Utente non autorizzato (Richiesto: Organizzatore o Proprietario)");
        }
    }
}