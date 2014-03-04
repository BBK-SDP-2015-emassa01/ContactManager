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
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

/**
 * Class to manage contacts and meetings
 *
 * @author Esha
 */
public class ContactManagerImpl implements ContactManager {

    private Set<Contact> contactSet;
    private List<Meeting> meetingList;
    private Calendar date;

    // Structures to store meetingIDs and contactSets
    private Map<Integer, Meeting> meetingIDMap;
    private Map<Integer, Contact> contactIDMap;
    private Map<Integer, List< Meeting>> contactIDAndMeetingList;

    public ContactManagerImpl() throws FileNotFoundException, ParseException, IOException {
        contactSet = new HashSet<Contact>();
        meetingList = new ArrayList<Meeting>();
        date = new GregorianCalendar();
        meetingIDMap = new HashMap<Integer, Meeting>(); //ids to meetings
        contactIDMap = new HashMap<Integer, Contact>(); //ids to contacts
        contactIDAndMeetingList = new HashMap<Integer, List<Meeting>>(); //links a contact ID to a list of meetings.

        //bufferedReader must be called from within try/catch statement - to catch any IOException
        File path = new File("contacts.txt");
        File contactFile = new File(path.getAbsolutePath());
        if (contactFile.exists()) {
            System.out.println("File found!");
            checkIfFileExists();
        }
        if (!contactFile.exists()) {
            System.out.println("File not found.");

        }
    }

    public void checkIfFileExists() throws ParseException, IOException {
        //buffered reader to read the file
        FileReader file = null;
        BufferedReader buffer = null;
        try {
            File path = new File("contacts.txt");
            File contactFile = new File(path.getAbsolutePath());
            file = new FileReader(contactFile);
            buffer = new BufferedReader(file);

            //set booleans for processing
            boolean contacts = false;
            boolean meetings = false;

            //blank line that will provide itself as the output from the line found in 
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] lineItemsArray = line.split(",");

                if (lineItemsArray[0].equals("CONTACT ID NUMBER")) {
                    System.out.println(lineItemsArray[0]);
                    contacts = true;
                    meetings = false;
                } else if (lineItemsArray[0].equals("MEETING ID NUMBER")) {
                    System.out.println(lineItemsArray[0]);
                    contacts = false;
                    meetings = true;
                } else {
                    if ((contacts == true) && (meetings == false)) {
                        System.out.println(lineItemsArray[0]);
                        int contactID = (Integer.parseInt(lineItemsArray[0]));
                        System.out.println(lineItemsArray[0]);
                        String contactName = lineItemsArray[1];
                        System.out.println(lineItemsArray[1]);
                        String contactNotes = lineItemsArray[2];
                        System.out.println(contactNotes);

                        Contact thisContact = new ContactImpl(contactID, contactName, contactNotes);
                        contactSet.add(thisContact);
                        contactIDMap.put(thisContact.getId(), thisContact);

                    }
                    if ((meetings == true) && (contacts == false)) {

                        Set<Contact> meetingContacts;

                        int meetingID = Integer.parseInt(lineItemsArray[0]);
                        System.out.println(meetingID);

                        //parse and construct calendar object
                        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z YYYY");
                        Date date = format.parse(lineItemsArray[1]);

                        Calendar calDate = new GregorianCalendar();
                        calDate.setTime(date);

                        System.out.println("Date/time of meeting to enter: " + calDate.getTime());

                        String thisMeetingContacts = lineItemsArray[2];
                        String[] MeetingContacts = thisMeetingContacts.split(";");

                        int[] MeetingContactsIds = new int[MeetingContacts.length];

                        for (int i = 0; i < MeetingContactsIds.length; i++) {
                            MeetingContactsIds[i] = Integer.parseInt(MeetingContacts[i].trim());
                            System.out.println("Contact ID for meeting (gathered): " + MeetingContacts[i].trim());
                        }
                        meetingContacts = getContacts(MeetingContactsIds);

                        Calendar dateNow = new GregorianCalendar();
                        System.out.println("Current date/time: " + dateNow);

                        if (calDate.after(dateNow)) {
                            //construct FutureMeetingImpl without notes
                            Meeting futureMeeting = new FutureMeetingImpl(meetingID, meetingContacts, calDate);
                            meetingList.add(futureMeeting);
                            meetingIDMap.put(meetingID, futureMeeting);
                            addListOfMeetingsToContact(futureMeeting);
                        }

                        String meetingNotes = "";

                        if (calDate.before(dateNow)) {

                            if (lineItemsArray.length == 3) {
                                meetingNotes = "";
                            }
                            if (lineItemsArray.length == 4) {
                                meetingNotes = lineItemsArray[3];
                            }

                            Meeting pastMeeting = new PastMeetingImpl(meetingID, meetingContacts, calDate, meetingNotes);
                            meetingList.add(pastMeeting);
                            meetingIDMap.put(meetingID, pastMeeting);
                            addListOfMeetingsToContact(pastMeeting);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //must close this once complete
                file.close();
                buffer.close();
            }catch (IOException ex){
                System.out.println("I/O exception. Buffer or File may have been 'null'.");
                ex.printStackTrace();
            } 
        }
    }

    /**
     * adds a meeting to a contact's list of meetings, if it has not already
     * been added..
     *
     * @param m the meeting to add
     *
     */
    public void addListOfMeetingsToContact(Meeting m) {
        List<Meeting> thisList = new ArrayList<Meeting>();
        Set<Contact> mContacts = m.getContacts();
        Iterator<Contact> eachContact = mContacts.iterator();

        //only add meeting if the id is not already added. 
        //At this stage there should not be any duplicates.
        if (!contactIDAndMeetingList.containsKey(m.getId())) {

            while (eachContact.hasNext()) {
                Contact thisContact = eachContact.next();

                if (contactIDAndMeetingList.containsKey(thisContact.getId())) {
                    thisList = contactIDAndMeetingList.get(thisContact.getId());

                    thisList.add(m);
                    contactIDAndMeetingList.put(thisContact.getId(), thisList);
                }
            }
        }
        contactIDAndMeetingList.put(m.getId(), thisList); //link contact id to updatedmeeting list

    }

    /**
     * Removes a meeting from a contact's list of meetings.
     *
     * @param m the meeting to add
     *
     */
    public void removeFromListOfMeetingsForContact(Meeting m) {
        List<Meeting> thisList = new ArrayList<Meeting>();
        Set<Contact> mContacts = m.getContacts();
        Iterator<Contact> eachContact = mContacts.iterator();

        while (eachContact.hasNext()) {
            Contact thisContact = eachContact.next();

            if (contactIDAndMeetingList.containsKey(thisContact.getId())) {
                thisList = contactIDAndMeetingList.get(thisContact.getId());

                thisList.remove(m);
                //contactIDAndMeetingList.put(thisContact.getId(), thisList);
            }
        }
        contactIDAndMeetingList.put(m.getId(), thisList); //link contact id to updatedmeeting list

    }

    /**
     * Add a new meeting to be held in the future.
     *
     * @param contacts a list of contacts that will participate in the meeting
     * @param date the date on which the meeting will take place
     * @return the ID for the meeting
     * @throws IllegalArgumentException if the meeting is set for a time in the
     * past, of if any contact is unknown / non-existent
     *
     */
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        Meeting futureMeeting;
        int generatedID;

        // check that the contacts are not null.
        checkArgumentIsNotNull(contacts);
        //check that the meeting is actually a future meeting (i.e., that time is valid). Use calendar class to validate the date
        this.date = new GregorianCalendar();
        if (date.before(this.date)) {
            throw new IllegalArgumentException("You entered a date in the past. Please try again: ");
        }
        //go through the entire Set of contacts and check that each and every one of them exists

        if (!contactSet.containsAll(contacts)) {
            throw new IllegalArgumentException("Unknown/non-existant contacts. ");
        }

        boolean generatedIDIsTaken = true;

        do {
            Random random = new Random();
            generatedID = random.nextInt(Integer.MAX_VALUE);
            System.out.println("\nAssined Meeting ID NUMBER: \n" + generatedID);
            if (!meetingIDMap.containsKey(generatedID)) {
                generatedIDIsTaken = false;
            }
        } while (generatedIDIsTaken);

        // constructor, after all checks, create a future meeting.
        futureMeeting = new FutureMeetingImpl(generatedID, contacts, date);
        //add meeting to list of meetings
        meetingList.add(futureMeeting);
        meetingIDMap.put(futureMeeting.getId(), futureMeeting);
        addListOfMeetingsToContact(futureMeeting);

        return generatedID;
    }

    //for testing
    public int addFutureMeeting(int id, Set<Contact> contacts, Calendar date) {
        Meeting futureMeeting = new FutureMeetingImpl(id, contacts, date);
        //add meeting to list of meetings
        meetingList.add(futureMeeting);
        meetingIDMap.put(futureMeeting.getId(), futureMeeting);
        addListOfMeetingsToContact(futureMeeting);
        return id;
    }

    /**
     * Returns the PAST meeting with the requested ID, or null if it there is
     * none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID
     * happening in the future
     */
    public PastMeeting getPastMeeting(int id) {
        PastMeeting result = null;
        //if there are no meetings in the list.
        if (meetingList.isEmpty()) {
            System.out.println("The meeting list is currently empty.");
            return null;
        }
        //throw exception if a future meeting contains that id number
        Meeting meeting = meetingIDMap.get(id);
        if (meeting instanceof FutureMeeting) {
            throw new IllegalArgumentException("The id is already used for a future meeting.");
        } else if (meeting instanceof PastMeeting) {
            result = (PastMeetingImpl) meeting;
            System.out.println("Retrieving past meeting..");
        }
        return result;
    }

    /**
     * Returns the FUTURE meeting with the requested ID, or null if there is
     * none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID
     * happening in the past
     */
    public FutureMeeting getFutureMeeting(int id) {
        FutureMeeting result = null;
        //if there are no meetings in the list.
        if (meetingList.isEmpty()) {
            System.out.println("The meeting list is currently empty.");
            return null;
        }
        Meeting meeting = meetingIDMap.get(id);
        if (meetingIDMap.containsKey(id)) {
            System.out.println("Found meeting: " + id);
        }
        if (meeting instanceof PastMeeting) {
            throw new IllegalArgumentException("The id is already used for a past meeting.");
        } else if (meeting instanceof FutureMeeting) {
            result = (FutureMeetingImpl) meeting;
            System.out.println("Retrieving future meeting..");

        }
        return result;
    }

    /**
     * Returns the meeting with the requested ID, or null if it there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     */
    public Meeting getMeeting(int id) {
        Meeting result = null;
        //if there are no meetings in the list.
        if (meetingList.isEmpty()) {
            System.out.println("The meeting list is currently empty.");
            return null;
        } else {
            //return the meeting with the given id
            if (meetingIDMap.containsKey(id)) {
                System.out.println("Found meeting: " + id);
                result = meetingIDMap.get(id);

            }
        }
        return result;
    }

    /**
     * Returns the list of future meetings scheduled with this contact.
     *
     * If there are none, the returned list will be empty. Otherwise, the list
     * will be chronologically sorted and will not contain any duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe
     * empty).
     * @throws IllegalArgumentException if the contact does not exist
     */
    public List<Meeting> getFutureMeetingList(Contact contact) {
        Calendar dateToday = new GregorianCalendar();
        //create a list of future meetings to return
        List<Meeting> listOfFutureMeetings = new ArrayList<Meeting>();
        //possibly use a tree for chronological order
        //Set<Meeting> chronologicalTreeFutureMeetings = new TreeSet<Meeting>();

        //check that the contact exists
        if (!contactSet.contains(contact)) {
            throw new IllegalArgumentException("This contact does not exist. ");
        }
        if (contactIDAndMeetingList.containsKey(contact.getId())) {
            List<Meeting> contactMeetings = null;
            contactMeetings = contactIDAndMeetingList.get(contact.getId());
            for (int i = 0; i < contactMeetings.size(); i++) {
                if (contactMeetings.get(i).getDate().after(dateToday)) {
                    listOfFutureMeetings.add(contactMeetings.get(i));
                }
            }
        }
        return listOfFutureMeetings;
    }

    /**
     * Returns the list of meetings that are scheduled for, or that took place
     * on, the specified date
     *
     * If there are none, the returned list will be empty. Otherwise, the list
     * will be chronologically sorted and will not contain any duplicates.
     *
     * @param date the date
     * @return the list of meetings
     */
    public List<Meeting> getFutureMeetingList(Calendar date) {
        //create a list of future meetings to return
        List<Meeting> listOfFutureMeetings = new ArrayList<Meeting>();

        /* Now get the list of meetings and search within each meeting (at index 'i') for the contacts of those individual meetings.
         *  If the contacts of those meetings contains the searched for contact, add the meeting to the list of future meetings for that contact.
         */
        for (int i = 0; i < meetingList.size(); i++) {
            if (meetingList.get(i).getDate().equals(date)) {
                listOfFutureMeetings.add(meetingList.get(i));
            }
        }
        // else, if no meetings scheduled -- will return null.
        return listOfFutureMeetings;
    }

    /**
     * Returns the list of past meetings in which this contact has participated.
     *
     * If there are none, the returned list will be empty. Otherwise, the list
     * will be chronologically sorted and will not contain any duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of past meeting(s) scheduled with this contact (maybe
     * empty).
     * @throws IllegalArgumentException if the contact does not exist
     */
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        Calendar dateToday = new GregorianCalendar();
        //create a list of past meetings to return
        List<PastMeeting> listOfPastMeetings = new ArrayList<PastMeeting>();

        //possibly use a tree for chronological order
        //Set<PastMeeting> chronologicalTreePastMeetings = new TreeSet<PastMeeting>();
        //check that the contact exists
        if (!contactSet.contains(contact)) {
            throw new IllegalArgumentException("This contact does not exist. ");
        }
        /* Search within map for each previous meetings with contact
         *  If the contacts of those meetings contains the searched for contact, add the meeting to the list of past meetings for that contact.
         */
        if (contactIDAndMeetingList.containsKey(contact.getId())) {
            List<Meeting> contactMeetings = null;
            contactMeetings = contactIDAndMeetingList.get(contact.getId());
            for (int i = 0; i < contactMeetings.size(); i++) {
                if (contactMeetings.get(i).getDate().before(dateToday)) {
                    PastMeeting toAdd = (PastMeeting) contactMeetings.get(i);
                    listOfPastMeetings.add(toAdd);
                }
            }
        }
        return listOfPastMeetings;
    }

    /**
     * Create a new record for a meeting that took place in the past.
     *
     * @param contacts a list of participants
     * @param date the date on which the meeting took place
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the list of contacts is empty, or any
     * of the contacts does not exist
     * @throws NullPointerException if any of the arguments is null
     */
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        //make a new pastMeeting id number
        int generatePastMeetingID;
        checkArgumentIsNotNull(contacts);
        checkArgumentIsNotNull(date);
        checkArgumentIsNotNull(text);

        //go through the entire Set of contacts and check that each and every one of them exists
        if (!contactSet.containsAll(contacts)) {
            throw new IllegalArgumentException("One (possibly more) of the contacts entered does not exist.");
        }

        this.date = new GregorianCalendar();
        if (this.date.before(date)) {
            System.out.println("You need to enter a time in the past to add a new past meeting.");
            throw new IllegalArgumentException("Try again: ");
        }

        //create boolean to check the meetingID does not exist.
        boolean generatedPastMeetingIDTaken = true;
        do {
            Random random = new Random();
            generatePastMeetingID = random.nextInt(Integer.MAX_VALUE);

            if (!contactIDAndMeetingList.containsKey(generatePastMeetingID)) {
                generatedPastMeetingIDTaken = false;
            }
        } while (generatedPastMeetingIDTaken);

        if (!generatedPastMeetingIDTaken) {
            //contruct a new past meeting with the ID, contacts, date and notes
            Meeting pastMeeting = new PastMeetingImpl(generatePastMeetingID, contacts, date, text);
            //add meeting to the meeting list.
            meetingList.add(pastMeeting);
            meetingIDMap.put(pastMeeting.getId(), pastMeeting);
            addListOfMeetingsToContact(pastMeeting);
        }
    }

    //for testing meeting
    public void addNewPastMeeting(int ID, Set<Contact> contacts, Calendar date, String text) {
        Meeting pastMeeting = new PastMeetingImpl(ID, contacts, date, text);
        //add meeting to the meeting list.
        meetingList.add(pastMeeting);
        meetingIDMap.put(pastMeeting.getId(), pastMeeting);
        addListOfMeetingsToContact(pastMeeting);
    }

    public void checkArgumentIsNotNull(Set<Contact> contacts) {
        if (contacts == null) {
            throw new NullPointerException("Please enter the contacts. ");
        }
    }

    public void checkArgumentIsNotNull(Calendar date) {
        if (date == null) {
            throw new NullPointerException("Please enter a date for the meeting.");
        }
    }

    public void checkArgumentIsNotNull(String text) {
        if (text == null) {
            throw new NullPointerException("Please enter a note for the meeting: ");
        }
    }

    /**
     * Add notes to a meeting.
     *
     * This method is used when a future meeting takes place, and is then
     * converted to a past meeting (with notes).
     *
     * It can be also used to add notes to a past meeting at a later date.
     *
     * @param id the ID of the meeting
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the meeting does not exist
     * @throws IllegalStateException if the meeting is set for a date in the
     * future
     * @throws NullPointerException if the notes are null
     */
    public void addMeetingNotes(int id, String text) {
        //check meeitng exists
        for (int i = 0; i<meetingList.size(); i++){
        if (meetingList.get(i).getId()==id) {
            System.out.println("Found the meeting : " + id);
        } else {
            throw new IllegalArgumentException("That meeting ID does not exist. ");
        }
        }
        //get current date to compare it to meeting date
        Calendar dateNow = new GregorianCalendar();

        //check there are notes to add.
        if (text == null) {
            throw new NullPointerException("Please enter a note for the past meeting: ");
        }
        //Find meeting id
        Meeting meetingToAddNotes = meetingList.get(id);
        if (meetingToAddNotes.getDate().after(dateNow)) {
            throw new IllegalStateException("That meeting is set for a date in the future "
                    + "so cannot be converted into a past meeting, and you cannot add notes yet. ");
        }
        //get all info for the meeting
        int meetingID = meetingToAddNotes.getId();
        Set<Contact> meetingContacts = meetingToAddNotes.getContacts();
        Calendar meetingDate = meetingToAddNotes.getDate();
        String meetingNotes = text;

        //remove meeting from the list
        for (int i = 0; i < meetingList.size(); i++) {
            Meeting m = meetingList.get(i);
            if (m.getId() == id) {
                meetingList.remove(m);
            }
        }

        //remove meeting from the map
        if (meetingIDMap.containsKey(id)) {
            meetingIDMap.remove(id);
        }

        //contruct the past meeting
        Meeting pMeeting = new PastMeetingImpl(meetingID, meetingContacts, meetingDate, meetingNotes);
        meetingList.add(pMeeting);
        meetingIDMap.put(id, pMeeting);
    }

    //for testing
    public Contact addNewContact(int contactID, String name, String notes) {
        Contact newContact = new ContactImpl(contactID, name, notes);
        contactSet.add(newContact);
        contactIDMap.put(newContact.getId(), newContact);
        return newContact;
    }

    /**
     * Create a new contact with the specified name and notes.
     *
     * @param name the name of the contact.
     * @param notes notes to be added about the contact.
     * @throws NullPointerException if the name or the notes are null
     */
    public void addNewContact(String name, String notes) {
        int contactID;

        checkArgumentIsNotNull(name);
        checkArgumentIsNotNull(notes);

        boolean contactIdIsTaken = true;
        do {
            Random idNumber = new Random();
            contactID = idNumber.nextInt(Integer.MAX_VALUE);
            System.out.println("Assined: " + name + " ID NUMBER: " + contactID);
            
            if (!contactSet.contains(contactID)) {
                contactIdIsTaken = false;
            }
        } while (contactIdIsTaken);

        Contact newContact = new ContactImpl(contactID, name, notes);
        contactSet.add(newContact);
        contactIDMap.put(newContact.getId(), newContact);
    }

    /**
     * Returns a list containing the contacts that correspond to the IDs.
     *
     * @param ids an arbitrary number of contact IDs
     * @return a list containing the contacts that correspond to the IDs.
     * @throws IllegalArgumentException if any of the IDs does not correspond to
     * a real contact
     */
    public Set<Contact> getContacts(int... ids) {
        Set<Contact> theseContacts = new HashSet<Contact>();

        //check that all the id's entered exist. 
        for (int i = 0; i < ids.length; i++) {
            if (contactIDMap.containsKey(ids[i])) {
                Contact toAdd = contactIDMap.get(ids[i]);
                theseContacts.add(toAdd);
            } else {
                throw new IllegalArgumentException("The id: " + ids[i] + " does not correspond to a real contact. ");
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
    public Set<Contact> getContacts(String name) {

        checkArgumentIsNotNull(name);

        Set<Contact> theseContacts = new HashSet<Contact>();;

        for (Contact c : contactSet) {
            if (c.getName().equals(name)) {
                theseContacts.add(c);

            }

            if (theseContacts.isEmpty()) {
                throw new NullPointerException("There are no contacts with that name.");
            }
        }
        return theseContacts;
    }

    /**
     * Save all data to disk.
     *
     * This method must be executed when the program is closed and when/if the
     * user requests it.
     */
    public void flush() {
        FileWriter fileWrite = null;
        try {
            File contactFile = new File("contacts.txt");
            if (!contactFile.exists()) {
                boolean createNewFile = contactFile.createNewFile();
            }
            fileWrite = new FileWriter("contacts.txt");

            //write to file the contacts and meetings
            //contacts have an ID a Name and Notes and are stored in the contactSet.
            //Write headers:
            System.out.println("CONTACT ID NUMBER , CONTACT NAME , CONTACT NOTES");
            fileWrite.write("CONTACT ID NUMBER,CONTACT NAME,CONTACT NOTES,");

            if (contactSet == null) {
                throw new NullPointerException("Your contact list is empty.");
            }

            String contactDataEntry = "";
            for (Contact c : contactSet) {
                contactDataEntry = c.getId() + "," + c.getName() + "," + c.getNotes();
//            contactDataEntry = contactIDMap.get(c).getId()+","+ c.getName() +","+ c.getNotes();
                System.out.println(contactDataEntry);
                fileWrite.write("\n" + contactDataEntry);
            }
            fileWrite.write("\n");

            //meetings have an ID a Date and Contacts. Notes are associated with PastMeeting. Meetings are stored in the meetingList.
            //Write headers:
            System.out.println("MEETING ID NUMBER , MEETING DATE , MEETING ATTENDEE (ID NUMBERS) LIST , MEETING NOTES ");
            fileWrite.write("MEETING ID NUMBER,MEETING DATE,MEETING ATTENDEE (ID NUMBERS) LIST,MEETING NOTES");
            if (meetingList == null) {
                throw new NullPointerException("Your contact list is empty.");
            }
            String meetingDataEntry = "";//StringBuffer() does not work when I parse the contacts and meetings from the contacts.txt file at startup.
            for (Meeting m : meetingList) {
                meetingDataEntry = meetingDataEntry +"\n" + m.getId() + "," + m.getDate().getTime() + ",";

                Object[] contactListForDataEntry;
                Object thisContact;
                //Get contactSet and convert to Array, and String to print in contacts.txt. 
                Set<Contact> workingContacts = m.getContacts();
                contactListForDataEntry = workingContacts.toArray();

                for (int i = 0; i < contactListForDataEntry.length; i++) {
                    thisContact = contactListForDataEntry[i];
                    Contact c = (Contact) thisContact;

                    meetingDataEntry = meetingDataEntry + c.getId() + ";";

                }

                if (m instanceof PastMeeting) {
                    PastMeeting pMeeting = (PastMeeting) m;
                    String notes = pMeeting.getNotes();

                    if (notes == null) {
                        notes = "";
                    }

                    meetingDataEntry = meetingDataEntry +"," + notes;
                }
                //co nothing if FutureMeeting
            }
            
            System.out.println("This id: " + meetingDataEntry);
            fileWrite.write(meetingDataEntry + "");
        } catch (IOException e) {
            System.out.println("An error has occurred");
        } finally {
            try {
                fileWrite.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //method not used yet.
    public void getContactIdFromSet(Set<Contact> nameOfContactSetToSearch, Contact name) {
        try {
            if (nameOfContactSetToSearch.contains(name)) {
                System.out.println("The ID of contact " + name.getName() + " is " + name.getId());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //for testing
    public Set<Contact> getContactSet() {
        return this.contactSet;
    }

    public List<Meeting> getMeetingList() {
        return this.meetingList;
    }

    public Map<Integer, Meeting> getMeetingMap() {
        return this.meetingIDMap;
    }

    public Map<Integer, Contact> getContactMap() {
        return this.contactIDMap;
    }

}
