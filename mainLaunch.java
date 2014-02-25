/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.FileNotFoundException;
import java.util.GregorianCalendar;
import java.util.Set;
/**
 *
 * @author Esha
 */
public class mainLaunch {
    public static void main(String[] args) throws FileNotFoundException{
        mainLaunch mainScript = new mainLaunch();
        mainScript.launch();
    }
    
    public void launch() throws FileNotFoundException {
         ContactManagerImpl contactManager = new ContactManagerImpl();
         System.out.println("\n\nChecking method 'addNewContact()'");
         contactManager.addNewContact("Esha", "Good");
         contactManager.addNewContact( "Manoj", "Friendly");
         contactManager.addNewContact( "Mum", "Employee of the Month");
         contactManager.addNewContact( "Dad", "Hardworking");
         contactManager.addNewContact("Raju", "Bossy");
         
         //contactManager.flush();
         
         System.out.println("\n\nChecking method 'addFutureMeeting()'");
         contactManager.addFutureMeeting(contactManager.contactSet, new GregorianCalendar(2014, 25, 02));
         
         System.out.println("\n\nChecking method 'getFutureMeetingList()'");
         Contact Esha = new ContactImpl(2, "Esha", "OKworking");
         contactManager.contactSet.add(Esha);
         contactManager.getFutureMeetingList(Esha);
         
         System.out.println("\n\nChecking method 'getPastMeeting()'");
         contactManager.getPastMeeting(2); //no meeting
         Meeting pastMeeting = new PastMeetingImpl(1235, contactManager.contactSet, new GregorianCalendar(2014, 23, 02), "Productive" );
         contactManager.getPastMeeting(1235);
         
        contactManager.flush();
        
    }
    
}
