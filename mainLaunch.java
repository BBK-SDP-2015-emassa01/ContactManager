/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Scanner;

/**
 *
 * @author Esha
 */
public class mainLaunch {
    private HashMap<Integer, Meeting> meetingID;
   // private HashMap<Set<Contact>, Calendar> id;
    
    private Set<Contact> contacts;
    private Set<Meeting> meetings;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    
    public static void main(String[] args) throws IOException{
        mainLaunch mainScript = new mainLaunch();
        mainScript.launch();
    }
    
    public void launch() throws FileNotFoundException, IOException{
        System.out.println("Please choose/enter your login:");
        Scanner username = new Scanner(System.in);
        String login = username.nextLine();

        Organiser loginName = new Organiser();

        loginName.menuAction(loginName.displayMenu());
        
        
    }
    
}
