package catering.businesslogic.availabilityManagement;
import java.time.LocalDate;
import catering.businesslogic.staffManagement.StaffMember;

public class Availability implements Comparable<Availability>{
    private LocalDate date;
    private StaffMember staffMember;

    public Availability(LocalDate date, StaffMember staffMember){
        this.date = date;
        this.staffMember = staffMember;
    }

    public LocalDate getDate(){
        return date;
    }
    public StaffMember getStaffMember(){
        return staffMember;
    }

    public int compareTo(Availability a){
        return this.getDate().compareTo(a.getDate());
    }

    public boolean contains(StaffMember sm){
        return this.staffMember.equals(sm);
        
    }

    public String toString(){
        return date + " " + staffMember.getName() + " " + staffMember.getSurname();
    }

}