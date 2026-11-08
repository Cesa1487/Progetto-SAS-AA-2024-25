package catering.persistence;

import catering.businesslogic.holidayManagement.*;

public class HolidayTablePersistence implements HolidayTableReceiver{

    public void updateAddHolidayRequest(HolidayTable holidayTable,HolidayRequest request){
        HolidayTable.updateAddHolidayRequest(holidayTable, request);
    }

    public void updateChangedHolidayRequest(HolidayTable holidayTable, int index, HolidayRequest request){
        HolidayTable.updateChangedHolidayRequest(holidayTable,index,request);
    }

    public void updateDeletedHolidayRequest(HolidayTable holidayTable, int index){
        HolidayTable.updateDeletedHolidayRequest(holidayTable, index);
    }


    @Override
    public boolean equals(Object o){
        return o instanceof HolidayTablePersistence;
    }
}