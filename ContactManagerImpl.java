/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;//may need this import.
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class to manage contacts and meetings
 * @author Esha
 */
public class ContactManagerImpl implements ContactManager {
    private Set<Contact> contactSet;
    private Set<Meeting> meetingSet; 
    private List<Meeting> meetingList;
    private List<PastMeeting> pastMeetingList;
    private List<FutureMeeting> futureMeetingList;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    //private HashMap<Integer, Meeting> meetingID;
    //private Set<FutureMeeting> futureMeetingSet;
    //private Set<PastMeeting> pastMeetingSet;
            
    /**
    * Add a new meeting to be held in the future.
    *
    * @param contacts a list of contacts that will participate in the meeting
    * @param date the date on which the meeting will take place
    * @return the ID for the meeting
    * @throws IllegalArgumentException if the meeting is set for a time in the past,
    * of if any contact is unknown / non-existent
    **/
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        Meeting futureMeeting = null;
        int generatedID = 0;
        // check that the contacts are not null.
        if (contacts == null){
            throw new IllegalArgumentException("One (possibly more), contact(s) are unknown or non-existant.");
        }
        //check that the meeting is actually a future meeting (i.e., that time is valid). Use calendar class to validate the date
        this.date = new GregorianCalendar();
        if (date.before(this.date)){
            throw new IllegalArgumentException ("You entered a date in the past. Please try again: ");
        }
        //go through the entire Set of contacts and check that each and every one of them exists
        NEEDS BOOLEAN TO CONSIDER IF THE GENERATED ID IS TAKEN
            if (contactSet.containsAll(contacts)){
                Random random = new Random();
                generatedID = random.nextInt();
                
                for (Contact c:contactSet){
                    if(c.getId()!=generatedID){
                        // constructor, after all checks, create a future meeting.
                        futureMeeting = new FutureMeetingImpl(generatedID, contacts, date);
                        
                        /* Still need to consider how this meeting that is being constructed gets 
                        *  onto a list that stores all futureMeetings, to be able to return them later on.
                        AND what happens if the ID is already taken? BOOLEAN
                        */
                        
                    }
                }
            }
            if (generatedID == 0){
                throw new IllegalArgumentException("You have not successfully assigned an ID for that meeting.");
            } else {
                return generatedID;
            }
    }
    
    /**
    * Returns the PAST meeting with the requested ID, or null if it there is none.
    *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
    */
    public PastMeeting getPastMeeting(int id){
        //iterate through the list of meetings
        for (int i = 0; i<futureMeetingList.size();i++){
        if (futureMeetingList.get(i).getId()==id){
            //throw exception if a future meeting contains that id number
            throw new IllegalArgumentException("The id is already used for a future meeting.");
        }
        //iterate through the past meeting list and return the id, if a pastmeeting is found
        for(PastMeeting p: pastMeetingList){
            if(p.getId()==id){
                return p;
            }
        }
        }
        //otherwise return null.
        System.out.println("There is no past meeting with that id number.");
        return null;
    }

                

    
    /**
    * Returns the FUTURE meeting with the requested ID, or null if there is none.
    *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
    */
    public FutureMeeting getFutureMeeting(int id){
        //iterate through the list of pastMeetings to ensure thhere is no meeting with that ID in the past
        for (int i = 0; i <pastMeetingList.size(); i++){
            if (pastMeetingList.get(i).getId()==id){
                //throw IllegalArgumentException if there is a meeting with that id in the past
                throw new IllegalArgumentException("The id is already used for a past meeting.");
            }
            //iterate through the list of future meetings and return the id if a future meeting is found
            for (FutureMeeting f: futureMeetingList){
                if( f.getId()==id){
                    return f;
                }
            }
        }
        System.out.println("There is no future meeting with that id number.");
        return null;
    }
    
    /**
    * Returns the meeting with the requested ID, or null if it there is none.
    *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    */
    public Meeting getMeeting(int id){
        //if there are no meetings in the list.
        if (meetingList.isEmpty()){
            System.out.println("The meeting list is currently empty.");
            return null;
        } else {
            //return the meeting with the given id
        for (Meeting m: meetingList){
        if (m.getId()==id){
         return m;   
        }
        }
        //else return null with a message
        System.out.println("There is no meeting with the requested id.");
      } return null;   
    }
    
    /**
    * Returns the list of future meetings scheduled with this contact.
    *
    * If there are none, the returned list will be empty. Otherwise,
    * the list will be chronologically sorted and will not contain any
    * duplicates.
    *
    * @param contact one of the user’s contacts
    * @return the list of future meeting(s) scheduled with this contact (maybe empty).
    * @throws IllegalArgumentException if the contact does not exist
    */
    public List<Meeting> getFutureMeetingList(Contact contact){
        
    }
    
    /**
    * Returns the list of meetings that are scheduled for, or that took
    * place on, the specified date
    *
    * If there are none, the returned list will be empty. Otherwise,
    * the list will be chronologically sorted and will not contain any
    * duplicates.
    *
    * @param date the date
    * @return the list of meetings
    */
    public List<Meeting> getFutureMeetingList(Calendar date){
        
    }
    
    /**
    * Returns the list of past meetings in which this contact has participated.
    *
    * If there are none, the returned list will be empty. Otherwise,
    * the list will be chronologically sorted and will not contain any
    * duplicates.
    *
    * @param contact one of the user’s contacts
    * @return the list of future meeting(s) scheduled with this contact (maybe empty).
    * @throws IllegalArgumentException if the contact does not exist
    */
    public List<PastMeeting> getPastMeetingList(Contact contact){
        List<PastMeeting> pastMeetingList = null;
        
        for (Contact c: contactSet){
            if(c.equals(contact)){
                int tempId = c.getId();
                
                //then need to search the meetingList to find the meetings that this contact id has participated in.

            }
            
        }
        
       // return pastMeetingList;
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
        
        if (contacts==null|| date==null || text==null){
            throw new NullPointerException("There are no contacts to add. ");
        }
        
        //go through the entire Set of contacts and check that each and every one of them exists
        if (!contactSet.containsAll(contacts)){
            throw new IllegalArgumentException("One (possibly more) of the contacts entered does not exist.");
        }
        
        this.date = new GregorianCalendar();
        if(this.date.before(date)){
                System.out.println("You need to enter a time in the past to add a new past meeting.");
            throw new IllegalArgumentException("Try again: ");
        }
        
        Meeting pastMeeting = new PastMeetingImpl(, contacts, date, text);
        meetingList.add(pastMeeting);
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
        if (text.equals(null)){
            throw new NullPointerException(); 
        }
        try{
        for (Meeting m:meetingSet){
           if (m.getId()==id){
              // check the meetingSet for the id/date and throw exception if the date is in the future.
              //   catch(IllegalStateException e){
               
               m.addNotes(text);
           
           } else { 
               System.out.println("No ID found.");
           } 
        }} catch (IllegalArgumentException e){
            System.out.println("Meeting does not exist.");
        }   
    }
    
    /**
    * Create a new contact with the specified name and notes.
    *
    * @param name the name of the contact.
    * @param notes notes to be added about the contact.
    * @throws NullPointerException if the name or the notes are null
    */
    public void addNewContact(String name, String notes) {
        try{
    ContactImpl newContact = new ContactImpl(name, notes);// Don't know why it isn't compiling using Contact esha = new ContactImpl(name, notes);

    newContact.setID(1);
    newContact.setName(name);
    newContact.addNotes(notes);
    contactSet.add(newContact);
    }catch (NullPointerException e){
        e.printStackTrace();
    }
    try{
    SaveDataIO saveData  = new SaveDataIO(contactSet);
    saveData.writeSetToFile();
    }catch (FileNotFoundException e){
        e.printStackTrace();
    }       catch (IOException ex) {
                Logger.getLogger(ContactManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
    /**
    * Returns a list containing the contacts that correspond to the IDs.
    *
    * @param ids an arbitrary number of contact IDs
    * @return a list containing the contacts that correspond to the IDs.
    * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
    */
    public Set<Contact> getContacts(int... ids){
    Set<Contact> theseContacts = null;
        try{
        for (int id:ids){
        for (Contact c: contactSet){
            if (c.getId() == id){
            theseContacts.add(c);
            }  
        }
        } 
        }catch (NullPointerException e){
            e.printStackTrace();
        }return theseContacts;
        
        
    }
    
    /**
    * Returns a list with the contacts whose name contains that string.
    *
    * @param name the string to search for
    * @return a list with the contacts whose name contains that string.
    * @throws NullPointerException if the parameter is null
    */
    public Set<Contact> getContacts(String name){
        
        Set<Contact> theseContacts = null;
        try{
        for (Contact c: contactSet){
            if (c.getName().contentEquals(name)){
            theseContacts.add(c);
                
            }
        } 
        }catch (NullPointerException e){
            e.printStackTrace();
        }return theseContacts;
        
        
    }
    
    /**
    * Save all data to disk.
    *
    * This method must be executed when the program is
    * closed and when/if the user requests it.
    */

    public void flush(){
        //contactManager.close();

    }
    
    public void getContactIdFromSet(Set<Contact> nameOfContactSetToSearch, Contact name){
        try{
        if (nameOfContactSetToSearch.contains(name)){
            System.out.println("The ID of contact "+name.getName() + " is "+name.getId());
        }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    
}
    
    
}
