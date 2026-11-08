package catering.businesslogic.eventManagement;

public  class Note {
    private String title;
    private String text;

    public Note(String title, String text){
        this.title = title;
        this.text = text;
    }
    
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getText(){
        return this.text;
    }
    
    public void setText(String text){
        this.text = text;;
    }
    public String toString(){
        return "Title: "+title+"\nText: "+text;
        
    }
}