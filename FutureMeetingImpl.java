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
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
    
    private int id;
    
    private String notes;//notes about what happened at the meeting

    FutureMeetingImpl(int id, Set<Contact> contacts, Calendar date){
        super(id, contacts, date);
    }
    
    public int getId(){
        return this.id;
    }
}
