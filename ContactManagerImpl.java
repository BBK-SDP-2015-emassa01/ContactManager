/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;//may need this import.
/**
 * Class to manage contacts and meetings
 * @author Esha
 */
public class ContactManagerImpl implements ContactManager {
    private Set<Contact> contacts;
    private Set<Meeting> meetings;
    private Set<FutureMeeting> futureMeetings;
    private Set<PastMeeting> PastMeetings;
    private List<Meeting> listMeetings;
    private List<PastMeeting> listPastMeetings;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    
    
    /**
    * Add a new meeting to be held in the future.
    *
    * @param contacts a list of contacts that will participate in the meeting
    * @param date the date on which the meeting will take place
    * @return the ID for the meeting
    * @throws IllegalArgumentException if the meeting is set for a time in the past,
    * of if any contact is unknown / non-existent
    */
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        //check that the meeting is actually a future meeting (i.e., that time is valid). Use calendar class to validate the date
        try{
            if (date.getTime().before(this.date.getTime())){
            System.out.println("Please enter a date in the Future");
            }
        } catch (IllegalArgumentException e){
            System.out.println("Try again: ");
        }
        // constructor
        Meeting futureMeeting = new FutureMeetingImpl(/*MEETING*/id, contacts, date, text);
        return futureMeeting.getId();
    }
    
    /**
    * Returns the PAST meeting with the requested ID, or null if it there is none.
    *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
    */
    public PastMeeting getPastMeeting(int id){
        //if id is not null, (else return null
        //and is a type of past meeting//else throw exception that is future meeting
        //return past meeting name/hashcode
        if(meetings.contains(id)){
            if(id.getFutureMeeting().instanceOf(FutureMeeting)){
                throw new IllegalArgumentException("The id is already used for a future meeting.");
            } return id.getPastMeeting();
            
        } else return null;
    }
    
    /**
    * Returns the FUTURE meeting with the requested ID, or null if there is none.
    *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
    */
    public FutureMeeting getFutureMeeting(int id){
        
    }
    
    /**
    * Returns the meeting with the requested ID, or null if it there is none.
    *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    */
    public Meeting getMeeting(int id){
        
    }
    
    public List<Meeting> getFutureMeetingList(Contact contact){
        
    }
    
    public List<Meeting> getFutureMeetingList(Calendar date){
        
    }
    
    public List<PastMeeting> getPastMeetingList(Contact contact){
        
    }
    
    /**
    * Create a new record for a meeting that took place in the past.
    *
    * @param contacts a list of participants
    * @param date the date on which the meeting took place
    * @param text messages to be added about the meeting.
    * @throws IllegalArgumentException if the list of contacts is
    * empty, or any of the contacts does not exist
    * @throws NullPointerException if any of the arguments is null
    */
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
        
            if(this.date.getTime().after(date.getTime())){
                System.out.println("You need to enter a time in the past to add a new past meeting.");
            }
            else throw new IllegalArgumentException("Try again: ");
            
            Meeting pastMeeting = new PastMeetingImpl(id, contacts, date, text);
            throw new NullPointerException("Value is null. ");
        }
    
    /**
    * Add notes to a meeting.
    *
    * This method is used when a future meeting takes place, and is
    * then converted to a past meeting (with notes).
    *
    * It can be also used to add notes to a past meeting at a later date.
    *
    * @param id the ID of the meeting
    * @param text messages to be added about the meeting.
    * @throws IllegalArgumentException if the meeting does not exist
    * @throws IllegalStateException if the meeting is set for a date in the future
    * @throws NullPointerException if the notes are null
    */
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
