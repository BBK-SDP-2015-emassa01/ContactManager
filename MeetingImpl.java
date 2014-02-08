/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.util.Calendar;
import java.util.Set;
/**
 *
 * @author Esha
 */
public class MeetingImpl implements Meeting{
    private static int id;
    private Calendar date;
    
    
    /**
     * Meetings have unique IDs so using Set interface for additional Set stipulations,
     * that no two Meeting objects (e.g., m1 and m2) are equal (ie. it is never true 
     * that m1.equals(m2). 
     * **/
    private Set<Contact> contacts;
    
    public MeetingImpl(int id, Set<Contact> contacts, Calendar date){
        setId(id);
        setDate(date);
        setContacts(contacts);
        
    }
    
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Calendar getDate(){
        return this.date;
    }
    
    public void setDate(Calendar date){
        this.date = date;
    }
    
    /**
     * Return the details of people that attended the meeting.
     * The list contains a minimum of one contact (if there were
     * just two people: the user and the contact) and may contain an
     * arbitrary number of them.
     * @return the details of people that attended the meeting.
     */
    public Set<Contact> getContacts(){
        return this.contacts; //check this implementation fits the documentation at a later stage.
    }
    
    public void setContacts(Set<Contact> contacts){
        this.contacts=contacts;
    }
    

}
