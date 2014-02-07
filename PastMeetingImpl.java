/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;

/**
 *
 * @author Esha
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
    
    private String notes;//notes about what happened at the meeting
    
    PastMeetingImpl(){
        super();
    }
    
    public String getNotes(){
    return this.notes;
    }
    
}
