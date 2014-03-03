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

    FutureMeetingImpl(int id, Set<Contact> contacts, Calendar date) {
        super(id, contacts, date);
    }
}
