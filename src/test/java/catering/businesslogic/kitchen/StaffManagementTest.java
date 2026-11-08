package catering.businesslogic.kitchen;

import static org.junit.jupiter.api.Assertions.*;

import catering.businesslogic.staffManagement.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.availabilityManagement.Availability;
import catering.businesslogic.availabilityManagement.AvailabilityTableManager;
import catering.businesslogic.eventManagement.EventInfo;
import catering.businesslogic.eventManagement.EventInfoManager;
import catering.businesslogic.eventManagement.Note;
import catering.businesslogic.eventManagement.Role;
import catering.businesslogic.eventManagement.StaffEventInfoManager;
import catering.businesslogic.holidayManagement.*;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;
import catering.persistence.*;
import catering.util.Pair;

class SetupUnaVolta {
    private static boolean initialized = false;
    private static CatERing cr = CatERing.getInstance();

    public static void eseguiSetupUnaVolta() {
        if (!initialized) {
            System.out.println("--- SETUP DATI INIZIALI ---");

            String[] userNames = {"Matteo", "Laura", "Alessandro", "Martina", "Davide", "Elisa", "Gabriele", "Valentina", "Federico", "Alessia"};
            String[][] contactData = {
                    {"Matteo", "Bianchi", "matteo.bianchi@email.it", "3291122334", "Via Garibaldi 12, Roma", "BNCMTT89A01H501X"},
                    {"Laura", "Verdi", "laura.verdi@email.it", "3409988776", "Via Verdi 8, Milano", "VRDLRA90B45F205K"},
                    {"Alessandro", "Russo", "alessandro.russo@email.it", "3334455667", "Via dei Mille 21, Firenze", "RSSALS85C12D612T"},
                    {"Martina", "Ferrari", "martina.ferrari@email.it", "3285566778", "Corso Italia 34, Napoli", "FRRMRT92D61F839Y"},
                    {"Davide", "Esposito", "davide.esposito@email.it", "3272233445", "Via Roma 90, Bologna", "SPSDVD87A23C351W"},
                    {"Elisa", "Romano", "elisa.romano@email.it", "3367788990", "Piazza Duomo 2, Palermo", "RMNELS93E45G273U"},
                    {"Gabriele", "Gallo", "gabriele.gallo@email.it", "3451122335", "Viale Europa 15, Torino", "GLLGBR88G12L219Z"},
                    {"Valentina", "Conti", "valentina.conti@email.it", "3472233446", "Via Dante 5, Genova", "CNTVLN94H21M610E"},
                    {"Federico", "De Luca", "federico.deluca@email.it", "3466677889", "Via Mazzini 87, Bari", "DLCFDR91I14E512B"},
                    {"Alessia", "Marchetti", "alessia.marchetti@email.it", "3489988771", "Via Cavour 56, Venezia", "MRCLSS95L30H123F"}
            };

            int[] roles = {4, 2, 2, 1, 0, 0, 0, 3, 3, 3};

            for (int i = 0; i < 10; i++) {
                int id = i + 1;
                PersistenceManager.executeUpdate("INSERT OR REPLACE INTO Users (id, username) VALUES (?, ?)", id, userNames[i]);
                PersistenceManager.executeUpdate("INSERT OR REPLACE INTO UserRoles (user_id, role_id) VALUES (?, ?)", id, roles[i]);
                PersistenceManager.executeUpdate("INSERT OR REPLACE INTO Contacts (userId, name, surname, email, whatsapp, address, fiscalCode) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        id, contactData[i][0], contactData[i][1], contactData[i][2], contactData[i][3], contactData[i][4], contactData[i][5]);
            }

            try {
                Object[][] staffConfig = {
                        {0, true, false, 12}, // Matteo
                        {1, true, false, 25}, // Laura
                        {2, true, false, 7},  // Alessandro
                        {3, true, true, 18},  // Martina
                        {4, true, true, 30},  // Davide
                        {5, false, true, 9},  // Elisa
                        {6, true, true, 15},  // Gabriele
                        {7, false, false, 22},// Valentina
                        {8, true, false, 14}, // Federico
                        {9, false, false, 10} // Alessia
                };

                for (Object[] cfg : staffConfig) {
                    int idx = (int) cfg[0];
                    int userId = idx + 1;
                    Contact c = Contact.load(userId);
                    StaffMember sm = new StaffMember((boolean)cfg[1], (int)cfg[3], c, (boolean)cfg[2]);
                    sm.save();
                }

                HolidayTableManager htm = cr.getHolidayTableManager();
                StaffMember davide = StaffMember.load(5);
                StaffMember federico = StaffMember.load(9);
                StaffMember gabriele = StaffMember.load(7);

                htm.addHolidayRequest(new HolidayRequest(new DateSpan(LocalDate.now().plusMonths(4).plusDays(3), LocalDate.now().plusMonths(4).plusDays(7)), davide));
                htm.addHolidayRequest(new HolidayRequest(new DateSpan(LocalDate.now().plusMonths(4).plusDays(1), LocalDate.now().plusMonths(4).plusDays(9)), federico));
                htm.addHolidayRequest(new HolidayRequest(new DateSpan(LocalDate.now().plusMonths(4).plusDays(5), LocalDate.now().plusMonths(4).plusDays(15)), gabriele));

                AvailabilityTableManager atm = cr.getAvailabilityTableManager();
                cr.getUserManager().fakeLogin("Matteo");
                StaffMember alessia = StaffMember.load(10);

                for(int i=0;i<10;i++) atm.addAvailability(federico, LocalDate.now().plusMonths(4).plusDays(i));
                for(int i=0;i<15;i++) atm.addAvailability(davide, LocalDate.now().plusMonths(4).plusDays(i));
                for(int i=0;i<12;i++) atm.addAvailability(gabriele, LocalDate.now().plusMonths(4).plusDays(i));
                for(int i=0;i<10;i++) atm.addAvailability(alessia, LocalDate.now().plusMonths(4).plusDays(i));
                for(int i=0;i<10;i++) atm.addAvailability(alessia, LocalDate.of(2025, 3, 10).plusDays(i));

            } catch (Exception e) {
                e.printStackTrace();
            }

            EventInfoManager eim = cr.getEventInfoManager();
            StaffMember martina = StaffMember.load(4);

            createEventHelper(1, "Cena Aziendale AlfaGroup", LocalDate.of(2025, 3, 10), martina);
            eim.setCurrentEventInfo(EventInfo.load(1)); eim.addTypeOfService("Cena aziendale");

            createEventHelper(2, "Matrimonio Giulia e Marco", LocalDate.of(2024, 9, 16), martina);
            eim.setCurrentEventInfo(EventInfo.load(2)); eim.addTypeOfService("Matrimonio");

            createEventHelper(3, "Caffè post meeting GreenTech", LocalDate.of(2025, 4, 22), martina);
            eim.setCurrentEventInfo(EventInfo.load(3)); eim.addTypeOfService("Light catering");

            createEventHelper(4, "Gala di Beneficenza Città di Torino", LocalDate.now().plusMonths(4), martina);
            eim.setCurrentEventInfo(EventInfo.load(4)); eim.addTypeOfService("Gala");

            createEventHelper(5, "Lancio Prodotto E-Mobility", LocalDate.now().plusMonths(4).plusDays(10), martina);

            StaffEventInfoManager seim = cr.getStaffEventInfoManager();
            StaffMember alessia = StaffMember.load(10);
            try {
                EventInfo ei1 = EventInfo.load(1);
                if (ei1 != null && ei1.getStaffEventInfo() != null) {
                    seim.setCurrentStaffEventInfo(ei1.getStaffEventInfo());
                    seim.defineRole("Cameriera", false);
                    seim.assignRole(0, alessia);
                }

                EventInfo ei3 = EventInfo.load(3);
                if (ei3 != null && ei3.getStaffEventInfo() != null) {
                    seim.setCurrentStaffEventInfo(ei3.getStaffEventInfo());
                    seim.defineRole("Barista", false);
                    seim.assignRole(0, alessia);
                }

                EventInfo ei4 = EventInfo.load(4);
                if (ei4 != null && ei4.getStaffEventInfo() != null) {
                    seim.setCurrentStaffEventInfo(ei4.getStaffEventInfo());
                    seim.defineRole("Maitre", false);
                    seim.assignRole(0, alessia);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            initialized = true;
        }
    }

    private static void createEventHelper(int id, String name, LocalDate date, StaffMember chef) {

        PersistenceManager.executeUpdate("INSERT OR REPLACE INTO Events (id, name, date_start, date_end, chef_id) VALUES (?, ?, ?, ?, ?)",
                id, name, Date.valueOf(date), Date.valueOf(date), chef.getContactId());

        PersistenceManager.executeUpdate("INSERT OR REPLACE INTO EventInfos (eventId, numGuests) VALUES (?, ?)", id, 100);

        PersistenceManager.executeUpdate("INSERT OR REPLACE INTO StaffEventInfos (eventId, numberOfServers, numberOfKitchenStaff) VALUES (?, ?, ?)", id, 5, 2);
    }
}

@TestMethodOrder(OrderAnnotation.class)
public class StaffManagementTest {

    private CatERing cr = CatERing.getInstance();

    @BeforeAll
    static void init() {
        PersistenceManager.initializeDatabase("database/catering_init_sqlite.sql");
        SetupUnaVolta.eseguiSetupUnaVolta();
    }

    @Test
    @Order(1)
    public void testDefineRole() {
        System.out.println("\n=== TEST 1: AGGIUNTA NUOVO RUOLO ===");

        StaffEventInfoManager seim = cr.getStaffEventInfoManager();

        try {
            EventInfo ei = EventInfo.load(1);
            seim.setCurrentStaffEventInfo(ei.getStaffEventInfo());

            int ruoliIniziali = seim.getCurrentStaffEventInfo().getRoles().size();
            System.out.println("Ruoli presenti inizialmente: " + ruoliIniziali);

            String nomeRuolo = "Sommelier";
            seim.defineRole(nomeRuolo, false);
            System.out.println(">>> Ho aggiunto il ruolo: " + nomeRuolo);

            List<Role> ruoliAggiornati = seim.getCurrentStaffEventInfo().getRoles();
            assertEquals(ruoliIniziali + 1, ruoliAggiornati.size(), "Il numero di ruoli dovrebbe aumentare di 1");

            boolean trovato = false;
            for (Role r : ruoliAggiornati) {
                if (r.getPosition().equals(nomeRuolo)) {
                    trovato = true;
                    break;
                }
            }
            assertTrue(trovato, "Il ruolo 'Sommelier' deve essere presente nella lista");
            System.out.println("Verifica OK: Il ruolo è stato creato e aggiunto correttamente.");

        } catch (Exception e) {
            fail("Errore durante l'aggiunta del ruolo: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testContactOccasionalStaff() {
        System.out.println("\n=== TEST 2: CONTATTARE PERSONALE OCCASIONALE ===");

        UserManager um = cr.getUserManager();
        StaffEventInfoManager seim = cr.getStaffEventInfoManager();

        try {
            um.fakeLogin("Matteo");

            EventInfo ei = EventInfo.load(1);
            seim.setCurrentStaffEventInfo(ei.getStaffEventInfo());

            Contact candidato = Contact.load(2);
            System.out.println("Contatto selezionato: " + candidato.getName() + " " + candidato.getSurname());

            LocalDate oggi = LocalDate.now();
            LocalDate scadenza = oggi.plusDays(3);

            seim.contactOccasionalStaff(candidato, oggi, scadenza);
            System.out.println(">>> Richiesta inviata con successo.");

            boolean trovato = false;
            for (ContactedStaff cs : seim.getContactedStaffs()) {
                if (cs.getContact().getContactId() == candidato.getContactId()) {
                    trovato = true;
                    System.out.println("Verifica OK: Il contatto è presente nella lista 'ContactedStaff'.");
                    assertEquals(oggi, cs.getRequestDate());
                    break;
                }
            }
            assertTrue(trovato, "Il contatto dovrebbe essere nella lista dei contattati");

        } catch (UseCaseLogicException e) {
            fail("Errore durante il test: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testGetStatistics(){
        System.out.println("\n=== TEST 3: STATISTICHE (Alessia) ===");

        User proprietario = User.load("Matteo");
        UserManager um = cr.getUserManager();
        um.setCurrentUser(proprietario);

        StaffMemberManager smm = cr.getStaffMemberManager();

        StaffMember alessiaMarchetti = StaffMember.load(10);
        assertNotNull(alessiaMarchetti);

        Pair<List<Role>, List<Note>> toCheck = smm.getStatistics(alessiaMarchetti);
        assertNotNull(toCheck);

        System.out.println("Ruoli ricoperti:");
        for(Role r : toCheck.getKey()){
            System.out.println("\t" + r.getPosition() + " (Evento ID: " + r.getEventId() + ")");
        }
        System.out.println();

        System.out.println("Note e Info:");
        List<Note> notesAndMore = toCheck.getValue();
        for(Note p : notesAndMore){
            System.out.println("\t[" + p.getTitle() + "] " + p.getText());
        }
    }

    @Test
    @Order(4)
    public void testRegisterPermanentStaffData() {
        System.out.println("\n=== TEST 4: REGISTRAZIONE DATI PERMANENTI ===");

        UserManager um = cr.getUserManager();
        StaffMemberManager smm = cr.getStaffMemberManager();

        try {
            um.fakeLogin("Matteo");
            assertTrue(um.getCurrentUser().isOwner(), "L'utente deve essere proprietario");

            StaffMember elisa = StaffMember.load(2);
            System.out.println("Staff selezionato: " + elisa.getName());
            assertFalse(elisa.getPermanent(), "Elisa non dovrebbe essere ancora permanente");

            String nuovoIndirizzo = "Via Nuova 123, Torino";
            String nuovoCF = "RMNELS93E45G273X";

            smm.registerPermanentStaffData(elisa, nuovoIndirizzo, nuovoCF);
            System.out.println(">>> Dati registrati e promozione a permanente eseguita.");

            assertTrue(elisa.getPermanent(), "Ora dovrebbe essere flaggato come permanente");
            assertEquals(nuovoIndirizzo, elisa.getAddress());
            assertEquals(nuovoCF, elisa.getFiscalCode());
            System.out.println("Verifica OK: I dati sono stati aggiornati correttamente.");

        } catch (UseCaseLogicException e) {
            fail("Errore durante il test: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void testGetInfoDipendente(){
        System.out.println("\n=== TEST 5: INFO DIPENDENTE ===");

        HolidayTableManager htm = cr.getHolidayTableManager();
        HolidayTable ht = htm.getHolidayRequests();
        assertNotNull(ht);
        List<HolidayRequest> hrList = ht.getRequests();
        assertNotNull(hrList);

        System.out.println("Tutte le richieste di Ferie:");
        for(int i=0;i<hrList.size();i++)
            System.out.println("\t"+(i+1)+". "+hrList.get(i));
        System.out.println();

        if(hrList.size() > 0) checkRequestInfo(htm, 0);
        if(hrList.size() > 1) checkRequestInfo(htm, 1);
    }

    private void checkRequestInfo(HolidayTableManager htm, int index) {
        Pair<HolidayRequest,List<Availability>> checkPair = htm.getHolidayRequestInfo(index);
        assertNotNull(checkPair);

        System.out.println("Vacanza richiesta:");
        System.out.println("\t"+checkPair.getKey().getSpan());
        System.out.println("Dipendente: " + checkPair.getKey().getStaffMember().getContact().getName());
        System.out.println("Giorni di ferie rimanenti: "+checkPair.getKey().getStaffMember().getNumberOfDaysOff());
        System.out.println("Disponibilità date:");
        for(Availability av : checkPair.getValue()){
            System.out.println("\t"+av.getDate());
        }
        System.out.println();
    }

    @Test
    @Order(6)
    public void testAcceptHolidayRequest(){
        System.out.println("\n=== TEST 6: ACCETTAZIONE FERIE ===");

        User proprietario = User.load("Matteo");
        UserManager um = cr.getUserManager();
        um.setCurrentUser(proprietario);
        assertTrue(um.getCurrentUser().isOwner());

        HolidayTableManager htm = cr.getHolidayTableManager();
        StaffMemberManager smm = cr.getStaffMemberManager();

        if (htm.getHolidayRequests().getRequests().isEmpty()) {
            System.out.println("Nessuna richiesta ferie da testare.");
            return;
        }

        HolidayRequest req = htm.getHolidayRequest(0);
        System.out.println("Richiesta di ferie da Accettare: "+req);

        StaffMember sm = req.getStaffMember();
        smm.setCurrentStaffMember(sm);

        System.out.println("Dipendente: " + sm.getContact().getName());
        System.out.println("Giorni ferie PRIMA: "+sm.getNumberOfDaysOff());

        try{
            htm.acceptOrRefuseHoliday(req, "accepted");
            System.out.println(">>> Richiesta ACCETTATA con successo.");
        }
        catch(UseCaseLogicException e){
            System.out.println("Errore logica: " + e.getMessage());
        }

        System.out.println("Giorni ferie DOPO: "+sm.getNumberOfDaysOff());
    }
}