/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
/**
 * Class to manage contacts and meetings
 * @author Esha
 */
public class ContactManagerImpl implements ContactManager {
    Set<Contact> contactSet;
    private List<Meeting> meetingList;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    
    // Structures to store meetingIDs and contactSets
    Map<Integer, Meeting> meetingIDMap;
    Map<Integer, Contact> contactIDMap;
    Map<Integer,Set< Contact>> meetingIDAndContactSet;

    public ContactManagerImpl() throws FileNotFoundException, ParseException{
        contactSet = new HashSet<Contact>(); 
        meetingList = new ArrayList<Meeting>(); 
        date = new GregorianCalendar();
        meetingIDMap = new HashMap<Integer, Meeting>(); //ids to meetings
        contactIDMap = new HashMap<Integer, Contact>(); //ids to contacts
        meetingIDAndContactSet = new HashMap<Integer,Set<Contact>>(); //links a meeting ID to a set of contacts.
        
        //bufferedReader must be called from within try/catch statement - to catch any IOException
        File contactFile = new File("contacts.csv");
        if (!contactFile.exists()){
            throw new FileNotFoundException("File not found.");
        }   
           
        //buffered reader to read the file
        try {
            FileReader file = new FileReader("contacts.csv");
	    BufferedReader buffer = new BufferedReader(file);
				
            //set booleans for processing
            boolean contacts = false;
            boolean meetings = false;
            
            //blank line that will provide itself as the output from the line found in 
            String line = "";
            while ( (line = buffer.readLine()) != null){ 
                String[] lineItemsArray = line.split(",");
                
                if (lineItemsArray[0].equals("Contact_ID")){
                    System.out.println(lineItemsArray[0]);
                    contacts = true;
                    meetings = false;
                } else if (lineItemsArray[0].equals("Meeting_ID")){
                System.out.println(lineItemsArray[0]);
                    contacts = false;
                    meetings = true;
                }
                else {
                    if(contacts){
                        String contactName = lineItemsArray[1];
                        int contactID = Integer.parseInt(lineItemsArray[0]);
                        String contactNotes = lineItemsArray[2];
                        
                        Contact thisContact = new ContactImpl(contactID, contactName,contactNotes );
                        contactSet.add(thisContact);
                        contactIDMap.put(thisContact.getId(), thisContact);
                        
                                
                    }
                    if(meetings){
                        
                        Set<Contact> meetingContacts = new HashSet<Contact>();
                        
                        int meetingID = Integer.parseInt(lineItemsArray[0]);
                        
                        //parse and construct calendar object
                        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z YYYY");
                        Date date = format.parse(lineItemsArray[1]);
                        Calendar calDate = new GregorianCalendar();
                        calDate.setTime(date);
                        
                        String thisMeetingContacts = lineItemsArray[2];
                        String MeetingContacts = thisMeetingContacts.replaceAll(";", ",");
                        
                        meetingContacts = getContacts(MeetingContacts);
                        
                        String meetingNotes = "";
                    
                        Calendar dateNow = new GregorianCalendar();
                        if (calDate.after(dateNow)){
                        //construct FutureMeetingImpl without notes
                            Meeting futureMeeting = new FutureMeetingImpl(meetingID, meetingContacts, calDate);
                        }
                        
                        if (calDate.before(dateNow)){
                        //construct PastMeetingImpl with notes
                            meetingNotes = lineItemsArray[3];
                            Meeting pastMeeting = new PastMeetingImpl(meetingID, meetingContacts, calDate, meetingNotes);
                        }
                    }
                }
            }

            //must close this once complete
            file.close();
	    buffer.close();
        }
        catch(IOException e){
            System.out.println("An error has occurred");
        }	
	}
    
    public void writeContactsCSV(){
        try{
        File contactFile = new File("contacts.csv");
        if (!contactFile.exists()){
            contactFile.createNewFile();
        }
        FileWriter fileWrite = new FileWriter("contacts.csv");
        BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
        
        //write to file the contacts and meetings
        //contacts have an ID a Name and Notes and are stored in the contactSet.
        
        //Write headers:
        System.out.println("Contact_ID, Contact_Name, Contact_Notes");
        fileWrite.write("Contact_ID, Contact_Name, Contact_Notes,\n");
        
        if (contactSet == null){
            throw new NullPointerException("Your contact list is empty.");
        }
        
        String contactDataEntry = "";
        for (Contact c:contactSet){
            contactDataEntry = c.getId()+","+ c.getName() +","+ c.getNotes() +"\n";
            System.out.println(contactDataEntry);
            fileWrite.write(contactDataEntry+ "\n");
        }
        
        //meetings have an ID a Date and Contacts. Notes are associated with PastMeeting. Meetings are stored in the meetingList.
        
        //Write headers:
        fileWrite.write("Meeting_ID, Meeting_Date, Meeting_Attendees, Meeting_Notes, \n");
        System.out.println("Meeting_ID, Meeting_Date, Meeting_Attendees, Meeting_Notes, \n");
        if (meetingList == null){
            throw new NullPointerException("Your contact list is empty.");
        }
        String meetingDataEntry = "";
        for (Meeting m:meetingList){
            meetingDataEntry = m.getId()+","+ m.getDate().getTime()+",";
            System.out.println(meetingDataEntry);
        
            Object[] contactListForDataEntry;
            Object thisContact;
            //Get contactSet and convert to Array, and String to print in contacts.csv. 
            Set<Contact> workingContacts = m.getContacts();
            contactListForDataEntry = workingContacts.toArray();

            for (int i = 0; i <contactListForDataEntry.length;i++ ){
            thisContact = contactListForDataEntry[i];
            Contact c = (Contact) thisContact;
            int thisContactName = c.getId();
            
            meetingDataEntry = meetingDataEntry+thisContactName+";";
            if (m instanceof PastMeeting){
                PastMeeting pMeeting = (PastMeeting) m;
                String notes = pMeeting.getNotes();
                if (notes!=null){
                meetingDataEntry = meetingDataEntry+ ","+notes;
                }
            }
            else if (m instanceof FutureMeeting){
                //do nothing
                meetingDataEntry = meetingDataEntry;
            }
        }
        fileWrite.write(meetingDataEntry);
        }
        fileWrite.close();
        bufferWrite.close();
        }catch (IOException e){
            System.out.println("An error has occurred");
        }
    }
    
            
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
        Meeting futureMeeting;
        int generatedID = 0;
        boolean generatedIDIsNotTaken = false;
        // check that the contacts are not null.
        checkArgumentIsNotNull(contacts);
        //check that the meeting is actually a future meeting (i.e., that time is valid). Use calendar class to validate the date
        this.date = new GregorianCalendar();
        if (date.before(this.date)){
            throw new IllegalArgumentException ("You entered a date in the past. Please try again: ");
        }
        //go through the entire Set of contacts and check that each and every one of them exists

            if (!contactSet.containsAll(contacts)){
                throw new IllegalArgumentException ("Unknown/non-existant contacts. ");
        }
                
                while(!generatedIDIsNotTaken){
                Random random = new Random();
                generatedID = random.nextInt();
                generatedID= Math.abs(generatedID);
                System.out.println("\nAssined Meeting ID NUMBER: \n" + generatedID);
                if (!meetingList.contains(generatedID)){
                        generatedIDIsNotTaken = true;
                    }
            // constructor, after all checks, create a future meeting.
            futureMeeting = new FutureMeetingImpl(generatedID, contacts, date);
            //add meeting to list of meetings
            meetingList.add(futureMeeting);
            meetingIDMap.put(futureMeeting.getId(), futureMeeting );
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
        //if there are no meetings in the list.
        if (meetingList.isEmpty()){
            System.out.println("The meeting list is currently empty.");
            return null;
        }
        //iterate through the list of meetings
        for (int i = 0; i<meetingList.size();i++){
        if (meetingList.get(i).getId()==id){
            //throw exception if a future meeting contains that id number
            if (meetingList.get(i) instanceof FutureMeeting){
            throw new IllegalArgumentException("The id is already used for a future meeting.");
            }
            else if (meetingList.get(i) instanceof PastMeeting){
                PastMeeting toReturn = (PastMeeting) meetingList.get(i);
                return toReturn;
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
        //if there are no meetings in the list.
        if (meetingList.isEmpty()){
            System.out.println("The meeting list is currently empty.");
            return null;
        }
        //iterate through the list of meeting
        for (int i = 0; i <meetingList.size(); i++){
            if (meetingList.get(i).getId()==id){
                //throw IllegalArgumentException if there is a meeting with that id in the past
                if (meetingList.get(i) instanceof PastMeeting){
                throw new IllegalArgumentException("The id is already used for a past meeting.");
            }
            } else if (meetingList.get(i) instanceof FutureMeeting){
                FutureMeeting toReturn = (FutureMeeting) meetingList.get(i);
                return toReturn;
            }
        }
        //otherwise return null.
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
        Calendar dateToday = new GregorianCalendar();
        //create a list of future meetings to return
        List<Meeting> listOfFutureMeetings = new ArrayList<Meeting>(); 
        //Set<Meeting> chronologicalTreeFutureMeetings = new TreeSet<Meeting>();
        List<Meeting> chronoOrderFutureMeetings = null;
        //check that the contact exists
        if(!contactSet.contains(contact)){
            throw new IllegalArgumentException("This contact does not exist. ");
        }
        
        /* Now get the list of meetings and search within each meeting (at index 'i') for the contacts of those individual meetings.
        *  If the contacts of those meetings contains the searched for contact, add the meeting to the list of future meetings for that contact.
        */
        for (int i = 0; i <meetingList.size(); i++){
            if (meetingList.get(i).getDate().after(dateToday)){
            Set<Contact> contactsForTempMeeting = meetingList.get(i).getContacts();
            if (contactsForTempMeeting.contains(contact)){
                listOfFutureMeetings.add(meetingList.get(i));
                //chronologicalTreeFutureMeetings.add(meetingList.get(i));
                //chronoOrderFutureMeetings = new ArrayList<Meeting>(chronologicalTreeFutureMeetings);
                chronoOrderFutureMeetings = new ArrayList<Meeting>(listOfFutureMeetings);
            }
            }
        }
        System.out.println(chronoOrderFutureMeetings.toString());
        for (int i = 0; i <meetingList.size(); i++){
            System.out.println("ID: "+meetingList.get(i).getId());
            System.out.println("Contacts: "+meetingList.get(i).getContacts());
        }
        
        // else, if no meetings scheduled -- will return null.
        return chronoOrderFutureMeetings;  
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
        //create a list of future meetings to return
        List<Meeting> listOfFutureMeetings = new ArrayList<Meeting>();
        
        /* Now get the list of meetings and search within each meeting (at index 'i') for the contacts of those individual meetings.
        *  If the contacts of those meetings contains the searched for contact, add the meeting to the list of future meetings for that contact.
        */
        for (int i = 0; i <meetingList.size(); i++){
            if (meetingList.get(i).getDate().after(date)){
                listOfFutureMeetings.add(meetingList.get(i));
            }
        }
        // else, if no meetings scheduled -- will return null.
        return listOfFutureMeetings;
    }
    
    /**
    * Returns the list of past meetings in which this contact has participated.
    *
    * If there are none, the returned list will be empty. Otherwise,
    * the list will be chronologically sorted and will not contain any
    * duplicates.
    *
    * @param contact one of the user’s contacts
    * @return the list of past meeting(s) scheduled with this contact (maybe empty).
    * @throws IllegalArgumentException if the contact does not exist
    */
    public List<PastMeeting> getPastMeetingList(Contact contact){
        Calendar dateToday = new GregorianCalendar();
        //create a list of past meetings to return
        //List<Meeting> listOfPastMeetings = new ArrayList<Meeting>(); 
        Set<PastMeeting> chronologicalTreePastMeetings = new TreeSet<PastMeeting>();
        List<PastMeeting> chronoOrderPastMeetings = null;
        //check that the contact exists
        if(!contactSet.contains(contact)){
            throw new IllegalArgumentException("This contact does not exist. ");
        }
        
        /* Now get the list of meetings and search within each meeting (at index 'i') for the contacts of those individual meetings.
        *  If the contacts of those meetings contains the searched for contact, add the meeting to the list of past meetings for that contact.
        */
        for (int i = 0; i <meetingList.size(); i++){
            if (meetingList.get(i).getDate().after(dateToday)){
            Set<Contact> contactsForTempMeeting = meetingList.get(i).getContacts();
            if (contactsForTempMeeting.contains(contact)){
                //listOfPastMeetings.add(meetingList.get(i));
                PastMeeting meetingToAdd = (PastMeeting) meetingList.get(i);
                chronologicalTreePastMeetings.add(meetingToAdd);
                chronoOrderPastMeetings = new ArrayList<PastMeeting>(chronologicalTreePastMeetings);
            }
            }
        }
        // else, if no meetings scheduled -- will return null.
        return chronoOrderPastMeetings;  
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
        //make a new pastMeeting id number
        int generatePastMeetingID = 0;
        checkArgumentIsNotNull(contacts);
        checkArgumentIsNotNull(date);
        checkArgumentIsNotNull(text);

                    //go through the entire Set of contacts and check that each and every one of them exists
                    if (!contactSet.containsAll(contacts)){
                        throw new IllegalArgumentException("One (possibly more) of the contacts entered does not exist.");
                    }
                    
                    this.date = new GregorianCalendar();
                    if(this.date.before(date)){
                        System.out.println("You need to enter a time in the past to add a new past meeting.");
                        throw new IllegalArgumentException("Try again: ");
                    }
                
                //create boolean to check the meetingID does not exist.
                boolean generatedPastMeetingIDNotTaken = false;
                
                while(!generatedPastMeetingIDNotTaken){
                    Random random = new Random();
                    generatePastMeetingID = random.nextInt();
                    generatePastMeetingID= Math.abs(generatePastMeetingID);
                    for (Meeting m: meetingList){
                    if (m.getId()!=generatePastMeetingID){
                        generatedPastMeetingIDNotTaken = true;
                    }
                    }
                }
                //contruct a new past meeting with the ID, contacts, date and notes
                Meeting pastMeeting = new PastMeetingImpl(generatePastMeetingID, contacts, date, text);
                //add meeting to the meeting list.
                meetingList.add(pastMeeting);
                meetingIDMap.put(pastMeeting.getId(), pastMeeting);
    }

    public void checkArgumentIsNotNull(Set<Contact> contacts){
        if (contacts==null){
            throw new NullPointerException("Please enter the contacts. "); 
        } 
    }
    public void checkArgumentIsNotNull(Calendar date){
        if (date==null){
            throw new NullPointerException("Please enter a date for the meeting."); 
        }
    }
    public void checkArgumentIsNotNull(String text){
        if (text==null){
            throw new NullPointerException("Please enter a note for the meeting: "); 
        }
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
        //check there are notes to add.
        if (text==null){
            throw new NullPointerException("Please enter a note for the past meeting: "); 
        }
        //iterate through meeting list to find meeting id
        for (Meeting m:meetingList){
           if (m.getId()==id){
               
               //get current date and compare it to meeting date
               Calendar dateNow = new GregorianCalendar();
               if (m.getDate().after(dateNow)){
                   throw new IllegalStateException ("That meeting is set for a date in the future "
                           + "so cannot be converted into a future meeting yet. ");
               } 

               /* If I use the interface to construct here, I do not have the 'addNotes' method available. 
               *  Why? Is there an dependency between casting when using interfacees to construct?
               *  What happens if 'm' is already a pastMeeting, will there be an 'unable to cast' error?
               */
               //else cast to PastMeetingImpl to be able to addNotes
               PastMeetingImpl convertToPastMeeting = (PastMeetingImpl) m;
               convertToPastMeeting.addNotes(text);  
               
           } else throw new IllegalArgumentException("That meeting ID does not exist. ");  
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
        int contactID = 0;
        boolean contactIdIsTaken = false;
        checkArgumentIsNotNull(name);
        checkArgumentIsNotNull(notes);
            
            while (!contactIdIsTaken){
                Random idNumber = new Random();
                contactID = idNumber.nextInt();
                contactID= Math.abs(contactID);
                System.out.println("Assined " + name + " ID NUMBER: \n" + contactID);
                
                if (!contactSet.contains(contactID)){
                    contactIdIsTaken= true;
                }
                
            }
            Contact newContact = new ContactImpl(contactID, name, notes);
            contactSet.add(newContact);
            contactIDMap.put(newContact.getId(), newContact);
    }
    
    /**
    * Returns a list containing the contacts that correspond to the IDs.
    *
    * @param ids an arbitrary number of contact IDs
    * @return a list containing the contacts that correspond to the IDs.
    * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
    */
    public Set<Contact> getContacts(int... ids){
        boolean foundId = false;
        Set<Contact> theseContacts = null;
        
        //check that all the id's entered exist.
        for (int checkedId:ids){
            for (Contact checkContactId: contactSet){
                if (checkContactId.getId() == checkedId){
                foundId = true;
                } else throw new IllegalArgumentException("The id: "+ checkedId + "does not correspond to a real contact. ");
            } 
        }
        
        /*(...) in method means it can take a variable number of type 'int' values in its signature, 
        * and will store them as ab array ready to iterate through.
        */ 
        for (int id:ids){
        for (Contact c: contactSet){
            if (c.getId() == id){
            theseContacts.add(c);
            } 
        }
        } 
        return theseContacts;
    }
    
    /**
    * Returns a list with the contacts whose name contains that string.
    *
    * @param name the string to search for
    * @return a list with the contacts whose name contains that string.
    * @throws NullPointerException if the parameter is null
    */
    public Set<Contact> getContacts(String name){
        
        Set<Contact> theseContacts = new HashSet<Contact>();;
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
        writeContactsCSV();

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
    
    public Set<Contact> getContactSet(){
        return this.contactSet;
    }

}
