package catering.businesslogic.staffManagement;
import java.util.regex.Pattern;

import java.sql.*;
import catering.persistence.*;

public class Contact{

    private int contactId;
    private String name;
    private String surname;
    private String email;
    private String whatsapp;
    private String address;
    private String fiscalCode;

    public Contact(String name, String surname, String emailOrWhatsapp){
        this.name = name;
        this.surname = surname;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String regexNumber = "^\\+?[0-9\\s\\-()]{7,20}$";
        if(Pattern.matches(regex, emailOrWhatsapp))
            this.email = emailOrWhatsapp;
        else
            if(Pattern.matches(regexNumber, emailOrWhatsapp))
                this.whatsapp = emailOrWhatsapp;
    }

    public Contact(){
    }

    public Contact(String name, String surname, String email, String whatsapp, String address, String fiscalCode){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.whatsapp = whatsapp;
        this.address = address;
        this.fiscalCode = fiscalCode;
    }

    public int getContactId(){
        return contactId;
    }

    @Override
    public boolean equals(Object comp){
        if(!(comp instanceof Contact))
            return false;
        Contact contact = (Contact) comp;

        boolean nameAndSurname = contact.name.equals(name) && contact.surname.equals(surname);
        boolean equalsEmail = true;
        boolean equalsWhatsapp = true;
        boolean equalsAddress = true;
        boolean equalsFiscalCode = true;
        
        if(email != null)
            equalsEmail = contact.email.equals(email);
        if(whatsapp != null)
            equalsWhatsapp = contact.whatsapp.equals(whatsapp);
        if(address!=null)
            equalsAddress = contact.address.equals(address);
        if(fiscalCode != null)
            equalsFiscalCode = contact.fiscalCode.equals(fiscalCode);

        return nameAndSurname && equalsEmail && equalsWhatsapp && equalsAddress && equalsFiscalCode;    
    }
   
    public void setId(int i){
        contactId = i;
    }
    public int getId(){
        return contactId;
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    /*----------*/
    public String getSurname(){
        return this.surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    /*----------*/
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    /*----------*/
    public String getWhatsapp(){
        return this.whatsapp;
    }
    public void setWhatsapp(String whatsapp){
        this.whatsapp = whatsapp;
    }
    /*----------*/
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    /*----------*/
    public String getFiscalCode(){
        return this.fiscalCode;
    }
    public void setFiscalCode(String fiscalCode){
        this.fiscalCode = fiscalCode;
    } 

    public boolean containsAddressAndFiscalCode(){
        if(fiscalCode == null || address == null)
            return false;
        /*
        String regexCodiceFiscale = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
        String regexIndirizzo = "^(Via|Viale|Piazza|Corso|Largo)\\s+[A-ZÀ-Úa-zà-ú'’\\-\\s]+\\s+\\d+[A-Za-z]?$";
        if(Pattern.matches(regexCodiceFiscale, this.fiscalCode) && Pattern.matches(regexIndirizzo, this.address))
            return true;
        return false;
        */
       return true;
    }

    public String toString(){
        return "contactId: "+contactId + " | name: " + name + " | surname: " + surname +" | email:" +  email + " | whatsapp: " + whatsapp + " | address: " +address + " | fiscalCode: " + fiscalCode;
    }

    /** DATABASE OPERATIONS */
    public static Contact load(int id){
        String query = "SELECT `name`, `surname`, `email`,`whatsapp`, `address`, `fiscalCode`, `userId` FROM Contacts WHERE `userId` = ?";
        Contact ret = new Contact();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                ret.contactId = rs.getInt("userId");
                ret.name = rs.getString("name");
                ret.surname = rs.getString("surname");
                ret.email = rs.getString("email");
                ret.whatsapp = rs.getString("whatsapp");
                ret.address = rs.getString("address");
                ret.fiscalCode = rs.getString("fiscalCode");
            }
        }, id);

        return ret;
    }

    public static void save(int id,Contact contact){
        String query = "INSERT INTO Contacts(`name`,`surname`,`email`,`whatsapp`,`address`,`fiscalCode`,`userId`) VALUES (?,?,?,?,?,?,?)";
        PersistenceManager.executeUpdate(query,contact.name,contact.surname,contact.email,contact.whatsapp,contact.address,contact.fiscalCode,id);
    }
}