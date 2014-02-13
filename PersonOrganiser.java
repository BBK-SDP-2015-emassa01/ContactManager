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
import java.io.*;
/**
 *
 * @author Esha
 */
public class PersonOrganiser {
   ContactManager contactManager = new ContactManagerImpl();
    
    public String addName(){
        String name;
        System.out.println("What is the name of the contact?");
        Scanner aName = new Scanner(System.in);
        name = aName.nextLine();
        return name;
    }
    
    public String addNotes(){
        String notes;
        System.out.println("Please enter your notes: ");
        Scanner aNote = new Scanner(System.in);
        notes = aNote.nextLine();
        return notes;
    }
    
    public void addPerson(){
       // addName();
        //addNotes();
        //use method in ContactManagerImpl
         contactManager.addNewContact(addName(), addNotes()); 
    }

    public void save(){
        contactManager.flush();
    }
    
    
    
}
