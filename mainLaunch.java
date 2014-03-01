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
         contactManager.addNewContact("Tiger", "Bark");
         contactManager.addNewContact("Goldy", "Meow");
         contactManager.addNewContact("dragon", "ribbit");
         
         
//         Meeting p = new PastMeetingImpl(1, contactManager.getContactSet(), new GregorianCalendar(2011, 02, 29, 15, 30), "Good");
//         System.out.println(p instanceof Meeting);
//         System.out.println(p instanceof PastMeetingImpl);
//         System.out.println(p instanceof PastMeeting);
//         System.out.println(p instanceof FutureMeeting);
         
         //contactManager.flush();
         
         System.out.println("\n\nChecking method 'addFutureMeeting()'");
         contactManager.addFutureMeeting(2016, contactManager.getContactSet(), new GregorianCalendar(2016, 02, 29, 15, 30));
         contactManager.addFutureMeeting(2017, contactManager.getContactSet(), new GregorianCalendar(2016, 02, 29, 15, 30));
         contactManager.addFutureMeeting(2018, contactManager.getContactSet(), new GregorianCalendar(2016, 02, 29, 15, 30));
         contactManager.addFutureMeeting(2019, contactManager.getContactSet(), new GregorianCalendar(2016, 02, 29, 15, 30));
         System.out.println("HERE IT IS!"+contactManager.getFutureMeetingList(new GregorianCalendar(2016, 02, 29, 15, 30)));
         
         System.out.println("\n\nChecking method 'getFutureMeetingList()'");
         Contact Esha = new ContactImpl(2, "Esha", "OKworking");
         contactManager.getContactSet().add(Esha);
         //System.out.println(contactManager.getFutureMeetingList(Esha).toString());
         
         System.out.println("\n\nChecking method 'getPastMeetingList()'");
         
         Meeting pastMeeting = new PastMeetingImpl(1235, contactManager.getContactSet(), new GregorianCalendar(2012, 01, 02), "Productive" );
         contactManager.getMeetingList().add(pastMeeting);
         contactManager.getMeetingMap().put(1235, pastMeeting);
         System.out.println("constructed pastmeetingimpl");
         contactManager.addNewPastMeeting(contactManager.getContactSet(), new GregorianCalendar(2012, 01, 02), "Productive");
         //contactManager.addNewPastMeeting(contactManager.contactSet, new GregorianCalendar(2012, 01, 02), "Productive");
         //System.out.println(pastMeeting.getNotes());
         System.out.println("done this");
         System.out.println("instance contactset length = "+ contactManager.getContactSet().size());
         System.out.println("instance contactidmap length = "+ contactManager.getContactMap().size());
         System.out.println("getFutureMeeting()"+contactManager.getFutureMeeting(2016));
         System.out.println("meeting ids past" + contactManager.getMeetingList().get(0).getId());
         System.out.println("\n\ngetPastMeeting()"+contactManager.getPastMeeting(2));
         
//         System.out.println("getPastMeeting() number check");
         contactManager.getPastMeeting(339870630);
         System.out.println(pastMeeting.getId());
         
        contactManager.flush();
        
    }
    
}
