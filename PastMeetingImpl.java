/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;

import java.util.Calendar;
import java.util.Set;

/**
 *
 * @author Esha
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
    
    private String notes;//notes about what happened at the meeting
    private int id;
    
    PastMeetingImpl(int id, Set<Contact> contacts, Calendar date, String notes){
        super(id, contacts, date);
        this.notes = notes;
    }

    
    public String getNotes(){
        if (this.notes.equals("")||this.notes==null ){
            return "";
        }
    return this.notes;
    }
    
//    public void addNotes(String note){
//        this.notes = this.notes + note;
//    }
    
    
}
