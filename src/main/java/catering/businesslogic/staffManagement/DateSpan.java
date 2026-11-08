package catering.businesslogic.staffManagement;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class DateSpan implements Comparable<DateSpan>{

    private LocalDate from;
    private LocalDate to;
    private int length;

    public DateSpan(LocalDate from, LocalDate to){
        this.from = from;
        this.to = to;
        length = (int)ChronoUnit.DAYS.between(from, to) + 1; // Aggiungo 1 perchè lo span tra due date uguali è 0, quindi potrei
                                                             // potenzialmente prendermi 0 giorni di vacaza, invece così se inserisco 2
                                                             // date uguali lo span varrà 1, quindi mi prenderò un singolo gioro di vacanza
    }

    public LocalDate getFrom(){
        return from;
    }
    
    public LocalDate getTo(){
        return to;
    }
    
    public int getlength(){
        return length;
    }

    public void setFrom(LocalDate from){
        this.from = from;
        length = (int)ChronoUnit.DAYS.between(from, to) + 1; 
    }
    
    public void setTo(LocalDate to){
        this.to = to;
    }

    public boolean equals(Object comp){ 
        return comp instanceof DateSpan && this.from.equals(((DateSpan)comp).getFrom()) && this.to.equals(((DateSpan)comp).getTo());
    }
    
    public int compareTo(DateSpan comp){
        // date uguali = 0
        if(this.equals(comp))
            return 0;

        if(this.from.equals(comp.from)) // stessa data di inizio, prendo lo span più corto
            return this.length - comp.length;
        else
            return this.from.compareTo(comp.from); // data di inizio diversa, prendo quella più piccola
    }

    public boolean contains(LocalDate ld){
        return this.from.compareTo(ld) <= 0 && this.to.compareTo(ld) >= 0;
    }

    public String toString(){
        return "From: " +from +" -> To: " + to;
    }
  
}