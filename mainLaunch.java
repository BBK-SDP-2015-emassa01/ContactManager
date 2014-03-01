/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.Set;
/**
 *
 * @author Esha
 */
public class mainLaunch {
    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException{
        mainLaunch mainScript = new mainLaunch();
        mainScript.launch();
        
        
    }
    
    public void launch() throws FileNotFoundException, ParseException, IOException {
         ContactManagerImpl contactManager = new ContactManagerImpl();
         System.out.println("\n\nChecking method 'addNewContact()'");
         contactManager.addNewContact("Esha", "Good");
         contactManager.addNewContact( "Manoj", "Friendly");
         contactManager.addNewContact( "Mum", "Employee of the Month");
         contactManager.addNewContact( "Dad", "Hardworking");
         contactManager.addNewContact("Raju", "Bossy");
         contactManager.addNewContact("Raju", "Bossy");
         
         Meeting p = new PastMeetingImpl(1, contactManager.getContactSet(), new GregorianCalendar(2011, 02, 29, 15, 30), "Good");
         System.out.println(p instanceof Meeting);
         System.out.println(p instanceof PastMeetingImpl);
         System.out.println(p instanceof PastMeeting);
         System.out.println(p instanceof FutureMeeting);
         
         //contactManager.flush();
         
         System.out.println("\n\nChecking method 'addFutureMeeting()'");
         contactManager.addFutureMeeting(contactManager.getContactSet(), new GregorianCalendar(2014, 02, 29, 15, 30));
         
         System.out.println("\n\nChecking method 'getFutureMeetingList()'");
         Contact Esha = new ContactImpl(2, "Esha", "OKworking");
         contactManager.getContactSet().add(Esha);
         System.out.println(contactManager.getFutureMeetingList(Esha).toString());
         
         System.out.println("\n\nChecking method 'getPastMeeting()'");
         
         Meeting pastMeeting = new PastMeetingImpl(1235, contactManager.getContactSet(), new GregorianCalendar(2012, 01, 02), "Productive" );
         contactManager.addNewPastMeeting(contactManager.getContactSet(), new GregorianCalendar(2012, 01, 02), "Productive");
         contactManager.addNewPastMeeting(contactManager.getContactSet(), new GregorianCalendar(2012, 01, 02), "Productive");
         //System.out.println(pastMeeting.getNotes());
         
         contactManager.getPastMeeting(339870630);
         System.out.println(pastMeeting.getId());
         
       // contactManager.flush();
        
    }
    
}
