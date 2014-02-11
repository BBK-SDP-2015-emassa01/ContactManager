/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Esha
 */
public class Organiser {
    private PersonOrganiser user;
    
    private HashMap<Integer, Meeting> meetingID;
    
     /**
      * Displays a menu to ask user for what action they would like to perform.
      * @return int selection to determine how to proceed with the 
      * Organiser application.
      */
     public int displayMenu(){
         System.out.println("Welcome to your personal Contact Manager. \n"
                 + "How would you like to update/query your organiser? \n"
                 + "-->Press '1' for a list of your upcoming meetings.\n"
                 + "-->Press '2' for a list of your previous meetings.\n"
                 + "-->Press '3' for contact details for your next meetings.\n"
                 + "-->Press '4' to add notes about your past meetings.\n"
                 + "-->Press '5' to add notes to a previous meeting.\n"
                 + "-->Press '6' to add a person.\n"
                 + "-->Press '7' to EXIT.");
         Scanner choice = new Scanner(System.in);
         int selection = choice.nextInt();
         return selection;
     }
     
     /**
      * Performs action depending on the user's input.
      * @param selection of the user input
      */
     public void menuAction(int selection){
         PersonOrganiser user = new PersonOrganiser();
         
         switch(selection){
             case 1://user.upcomingMeetings();
                 System.out.println("Thanks!");
                 break;
             case 2://user.previousMeetings();
                 break;
             case 3: //user.contacts();
                 break;
             case 4: //user.meetingNotes();
                 break;
             case 5: user.addNotes();
                 break;
             case 6:
                    user.addPerson();
                 break;
             case 7: //SAVE DATA 
                 //user.save();
                 System.out.println("Thank you for using the Contact Manager.");
                 System.exit(0);
                 break;
             default: throw new IllegalArgumentException ("Something strange happened, try entering a number between 1 and 6.");
         }           
     }
}
