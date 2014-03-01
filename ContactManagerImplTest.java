/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Esha
 */
public class ContactManagerImplTest {
    
    
    
    public ContactManagerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addFutureMeeting method, of class ContactManagerImpl.
     */
    @Test
    public void testAddFutureMeeting() throws FileNotFoundException, ParseException, IOException {
        
        System.out.println("addFutureMeeting");
        
//        Set<Contact> contacts = new HashSet<>();
//        Contact a = new ContactImpl(1, "Name:a", "Notes: good");
//        contacts.add(a);
        
        Calendar date = new GregorianCalendar(2016, 02, 02);
        ContactManagerImpl instance = new ContactManagerImpl();
        int expResult = 0000001;
        int result = instance.addFutureMeeting(instance.getContactSet(), date);
//        System.out.println("id" + a.getId());
//        System.out.println("notes" + a.getNotes());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getPastMeeting method, of class ContactManagerImpl.
     */
    @Test
    public void testGetPastMeeting() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getPastMeeting");
        int id = 000001;
        ContactManagerImpl instance = new ContactManagerImpl();
        PastMeeting expResult = null;
        PastMeeting result = instance.getPastMeeting(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getFutureMeeting method, of class ContactManagerImpl.
     */
    @Test
    public void testGetFutureMeeting() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getFutureMeeting");
        int id = 0;
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.addFutureMeeting(instance.getContactSet(), new GregorianCalendar(2016,2,2));
        FutureMeeting expResult = null;
        FutureMeeting result = instance.getFutureMeeting(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getMeeting method, of class ContactManagerImpl.
     */
    @Test
    public void testGetMeeting() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getMeeting");
        int id = 0;
        ContactManagerImpl instance = new ContactManagerImpl();
        Meeting expResult = null;
        Meeting result = instance.getMeeting(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getFutureMeetingList method, of class ContactManagerImpl.
     */
    @Test
    public void testGetFutureMeetingList_Contact() throws FileNotFoundException {
        System.out.println("getFutureMeetingList");
        Contact contact = null;
        ContactManagerImpl instance = new ContactManagerImpl();
        List<Meeting> expResult = null;
        List<Meeting> result = instance.getFutureMeetingList(contact);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getFutureMeetingList method, of class ContactManagerImpl.
     */
    @Test
    public void testGetFutureMeetingList_Calendar() throws FileNotFoundException {
        System.out.println("getFutureMeetingList");
        Calendar date = null;
        ContactManagerImpl instance = new ContactManagerImpl();
        List<Meeting> expResult = null;
        List<Meeting> result = instance.getFutureMeetingList(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getPastMeetingList method, of class ContactManagerImpl.
     */
    @Test
    public void testGetPastMeetingList() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getPastMeetingList");
       
        Contact contact = new ContactImpl(1, "esha", "good");
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.getContactSet().add(contact);
        PastMeeting pm = new PastMeetingImpl(1, instance.getContactSet(), new GregorianCalendar(), "Esah");
        instance.getMeetingList().add(pm);
        List<PastMeeting> expResult = null;
        List<PastMeeting> result = instance.getPastMeetingList(contact);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of addNewPastMeeting method, of class ContactManagerImpl.
     */
    @Test
    public void testAddNewPastMeeting() throws FileNotFoundException, ParseException, IOException {
        System.out.println("addNewPastMeeting");
        
        Calendar date = new GregorianCalendar(2012, 2, 2);
        String text = "test";
        ContactManagerImpl instance = new ContactManagerImpl();
        //Set<Contact> thecontacts = (instance.getContacts());
        instance.addNewPastMeeting(25, instance.getContactSet(), date, text);
        System.out.println("25"+instance.getMeetingMap().get(25));
        instance.addNewPastMeeting(25, instance.getContactSet(), date, text);
        System.out.println("25"+instance.getMeetingMap().get(25));
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of checkArgumentIsNotNull method, of class ContactManagerImpl.
     */
    @Test
    public void testCheckArgumentIsNotNull_Set() throws FileNotFoundException, ParseException, IOException {
        System.out.println("checkArgumentIsNotNull");
        Set<Contact> contacts = new HashSet<Contact>();
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.checkArgumentIsNotNull(contacts);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of checkArgumentIsNotNull method, of class ContactManagerImpl.
     */
    @Test
    public void testCheckArgumentIsNotNull_Calendar() throws FileNotFoundException, ParseException, IOException {
        System.out.println("checkArgumentIsNotNull");
        Calendar date = new GregorianCalendar();
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.checkArgumentIsNotNull(date);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of checkArgumentIsNotNull method, of class ContactManagerImpl.
     */
    @Test
    public void testCheckArgumentIsNotNull_String() throws FileNotFoundException, ParseException, IOException {
        System.out.println("checkArgumentIsNotNull");
        String text = "";
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.checkArgumentIsNotNull(text);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addMeetingNotes method, of class ContactManagerImpl.
     */
    @Test
    public void testAddMeetingNotes() throws FileNotFoundException, ParseException, IOException {
        System.out.println("addMeetingNotes");
        int id = 0;
        String text = "";
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.addMeetingNotes(id, text);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of addNewContact method, of class ContactManagerImpl.
     */
    @Test
    public void testAddNewContact() throws FileNotFoundException, ParseException, IOException {
        System.out.println("addNewContact");
        int id = 12;
        String name = "E";
        String notes = "Good";
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.addNewContact(12, name, notes);
        instance.addNewContact(13, "mum", "ok");
        instance.addNewContact(14, "manoj", "bad");
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of getContacts method, of class ContactManagerImpl.
     */
    @Test
    public void testGetContacts_intArr() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getContacts");
        ContactManagerImpl instance = new ContactManagerImpl();
        Contact esha = new ContactImpl(12, "esha", "good");
        instance.addNewContact(12, "ehsa", "good");
        //instance.addNewContact(13, "mum", "ok");
        //instance.addNewContact(14, "manoj", "bad");
        int[] ids = new int[] {12};
        Set<Contact> expResult = new HashSet<Contact>();
        expResult.add(instance.addNewContact(12, "ehsa", "good"));
        Set<Contact> result = instance.getContacts(ids);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of getContacts method, of class ContactManagerImpl.
     */
    @Test
    public void testGetContacts_String() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getContacts");
        String name = "";
        ContactManagerImpl instance = new ContactManagerImpl();
        Set<Contact> expResult = instance.getContactSet();
        Set<Contact> result = instance.getContacts(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of flush method, of class ContactManagerImpl.
     */
    @Test
    public void testFlush() throws FileNotFoundException, ParseException, IOException {
        System.out.println("flush");
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.flush();
        // TODO review the generated test code and remove the default call to fail.
 
    }

    /**
     * Test of getContactIdFromSet method, of class ContactManagerImpl.
     */
    @Test
    public void testGetContactIdFromSet() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getContactIdFromSet");
        Set<Contact> nameOfContactSetToSearch = null;
        Contact name = null;
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.getContactIdFromSet(nameOfContactSetToSearch, name);
        // TODO review the generated test code and remove the default call to fail.
        //STILL NEED TO REVIEW THIS - ONLY WORKS BECAUSE IT DEFAULTS TO EXP AND RESULT BEING NULL

    }

    /**
     * Test of getContactSet method, of class ContactManagerImpl.
     */
    @Test
    public void testGetContactSet() throws FileNotFoundException, ParseException, IOException {
        System.out.println("getContactSet");
        ContactManagerImpl instance = new ContactManagerImpl();
        Set<Contact> expResult = null;
        Set<Contact> result = instance.getContactSet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of checkIfFileExists method, of class ContactManagerImpl.
     */
    @Test
    public void testCheckIfFileExists() throws Exception {
        System.out.println("checkIfFileExists");
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.checkIfFileExists();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of writeContactsCSV method, of class ContactManagerImpl.
     */
    @Test
    public void testWriteContactsCSV() throws ParseException, IOException {
        System.out.println("writeContactsCSV");
        ContactManagerImpl instance = new ContactManagerImpl();
        instance.writeContactsCSV();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
