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
    private ArrayList<Contacts> contacts;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    private String name;//of contact
    private String notes; //about contact
    
    
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        
    }
    
    PastMeeting getPastMeeting(int id){
        
    }
    
    FutureMeeting getFutureMeeting(int id){
        
    }
    
    Meeting getMeeting(int id){
        
    }
    
    List<Meeting> getFutureMeetingList(Contact contact){
        
    }
    
    List<Meeting> getFutureMeetingList(Calendar date){
        
    }
    
    List<PastMeeting> getPastMeetingList(Contact contact){
        
    }
    
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
        
    }
    
    public void addMeetingNotes(int id, String text){
        
    }
    
    public void addNewContact(String name, String notes){
        
    }
    
    Set<Contact> getContacts(int... ids){
        
    }
    
    Set<Contact> getContacts(String name){
        
    }
    
    public void flush(){
        
    }
    
    
}
