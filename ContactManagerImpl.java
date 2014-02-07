/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
/**
 *
 * @author Esha
 */
public class ContactManagerImpl {//implements ContactManager {
    private ArrayList<String> contacts;
    private Calendar date; //stored as a 6 digit integer DDMMYY
    private int id;
    
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        this.date = date;
        
        return ;
    }
    
    
}
