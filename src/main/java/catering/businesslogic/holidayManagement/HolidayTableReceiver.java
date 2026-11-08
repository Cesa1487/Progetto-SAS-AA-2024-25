package catering.businesslogic.holidayManagement;

public interface HolidayTableReceiver {

    public void updateAddHolidayRequest(HolidayTable holidayTable,HolidayRequest request);

    public void updateChangedHolidayRequest(HolidayTable holidayTable, int index, HolidayRequest request);

    public void updateDeletedHolidayRequest(HolidayTable holidayTable, int index);

}