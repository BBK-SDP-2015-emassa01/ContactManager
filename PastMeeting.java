package ContactManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Esha
 */
/**
 * A meeting that was held in the past.
 * 
* It includes your notes about what happened and what was agreed.
 */
public interface PastMeeting extends Meeting {

    /**
     * Returns the notes from the meeting.
     *     
* If there are no notes, the empty string is returned.
     *     
* @return the notes from the meeting.
     */
    String getNotes();
}
