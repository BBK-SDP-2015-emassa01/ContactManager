/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
/**
 *
 * @author Esha
 */
public class ContactManagerImpl {//implements ContactManager {
    private Set<Contact> contacts;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    
    
    
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        
    }
    
    public PastMeeting getPastMeeting(int id){
        
    }
    
    public FutureMeeting getFutureMeeting(int id){
        
    }
    
    public Meeting getMeeting(int id){
        
    }
    
    public List<Meeting> getFutureMeetingList(Contact contact){
        
    }
    
    public List<Meeting> getFutureMeetingList(Calendar date){
        
    }
    
    public List<PastMeeting> getPastMeetingList(Contact contact){
        
    }
    
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
        
    }
    
    public void addMeetingNotes(int id, String text){
        
    }
    
    public void addNewContact(String name, String notes){
        
    }
    
    public Set<Contact> getContacts(int... ids){
        
    }
    
    public Set<Contact> getContacts(String name){
        
    }
    
    public void flush(){
        
    }
    
    
}
