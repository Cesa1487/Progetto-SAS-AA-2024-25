package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.kitchen.KitchenTaskManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.shift.ShiftManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.KitchenTaskPersistence;
import catering.persistence.MenuPersistence;
import catering.persistence.StaffMemberPersistence;
import catering.businesslogic.eventManagement.*;
import catering.businesslogic.holidayManagement.*;
import catering.businesslogic.staffManagement.*;
import catering.businesslogic.availabilityManagement.*;
import catering.persistence.*;



public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private KitchenTaskManager kitchenTaskMgr;
    private ShiftManager shiftMgr;
    //new
    private StaffMemberManager staffMemberMgr;
    private HolidayTableManager holidayTableMgr;
    private StaffEventInfoManager staffEventInfoMgr;
    private EventInfoManager eventInfoMgr;
    private AvailabilityTableManager availabilityTableMgr;

    private MenuPersistence menuPersistence;
    private KitchenTaskPersistence kitchenTaskPersistence;
    // new
    private StaffMemberPersistence staffMemberPersistence;
    private HolidayTablePersistence holidayTablePersistence;
    private StaffEventInfoPersistence staffEventInfoPersistence;
    private EventInfoPersistence eventInfoPersistence;
    private AvailabilityTablePersistence availabilityTablePersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        kitchenTaskMgr = new KitchenTaskManager();
        shiftMgr = new ShiftManager(); // Add this line to initialize ShiftManager
        // new
        staffMemberMgr = new StaffMemberManager();
        holidayTableMgr = new HolidayTableManager();
        staffEventInfoMgr = new StaffEventInfoManager();
        eventInfoMgr = new EventInfoManager();
        availabilityTableMgr = new AvailabilityTableManager();

        menuPersistence = new MenuPersistence();
        kitchenTaskPersistence = new KitchenTaskPersistence();
        // new
        staffMemberPersistence = new StaffMemberPersistence();
        holidayTablePersistence = new HolidayTablePersistence();
        staffEventInfoPersistence = new StaffEventInfoPersistence();
        eventInfoPersistence = new EventInfoPersistence();
        availabilityTablePersistence = new AvailabilityTablePersistence();

        menuMgr.addEventReceiver(menuPersistence);
        kitchenTaskMgr.addEventReceiver(kitchenTaskPersistence);
        // new
        staffMemberMgr.addReceiver(staffMemberPersistence);
        holidayTableMgr.addReceiver(holidayTablePersistence);
        staffEventInfoMgr.addReceiver(staffEventInfoPersistence);
        eventInfoMgr.addReceiver(eventInfoPersistence);
        availabilityTableMgr.addReceiver(availabilityTablePersistence);
                    

    }

    public static void main(String[] args) {
        // Get the singleton instance which initializes all managers
        CatERing app = CatERing.getInstance();

        System.out.println("CatERing application initialized successfully.");

        // Log which managers are available
        System.out.println("Available managers:");
        System.out.println("- Menu Manager: " + (app.getMenuManager() != null ? "OK" : "NOT AVAILABLE"));
        System.out.println("- Recipe Manager: " + (app.getRecipeManager() != null ? "OK" : "NOT AVAILABLE"));
        System.out.println("- User Manager: " + (app.getUserManager() != null ? "OK" : "NOT AVAILABLE"));
        System.out.println("- Event Manager: " + (app.getEventManager() != null ? "OK" : "NOT AVAILABLE"));
        System.out.println("- Kitchen Task Manager: " + (app.getKitchenTaskManager() != null ? "OK" : "NOT AVAILABLE"));
        System.out.println("- Shift Manager: " + (app.getShiftManager() != null ? "OK" : "NOT AVAILABLE"));
    }

    public KitchenTaskManager getKitchenTaskManager() {
        return kitchenTaskMgr; // Return the field that was properly initialized
    }
    // new
    public HolidayTableManager getHolidayTableManager() {
        return holidayTableMgr; // Return the field that was properly initialized
    }
    public StaffEventInfoManager getStaffEventInfoManager() {
        return staffEventInfoMgr; // Return the field that was properly initialized
    }
    public EventInfoManager getEventInfoManager(){
        return eventInfoMgr;
    }
    public StaffMemberManager getStaffMemberManager(){
        return staffMemberMgr;
    }
    public AvailabilityTableManager getAvailabilityTableManager(){
        return availabilityTableMgr;
    }

    public ShiftManager getShiftManager() {
        return shiftMgr;
    }

    public void setShiftManager(ShiftManager shiftMgr) {
        this.shiftMgr = shiftMgr;
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public void setMenuManager(MenuManager menuMgr) {
        this.menuMgr = menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public void setRecipeManager(RecipeManager recipeMgr) {
        this.recipeMgr = recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public void setUserManager(UserManager userMgr) {
        this.userMgr = userMgr;
    }

    public EventManager getEventManager() {
        return eventMgr;
    }

    public void setEventManager(EventManager eventMgr) {
        this.eventMgr = eventMgr;
    }

    public void setKitchenTaskManager(KitchenTaskManager kitchenTaskMgr) {
        this.kitchenTaskMgr = kitchenTaskMgr;
    }

}
