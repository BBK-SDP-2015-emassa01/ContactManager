/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactManager;

import java.util.Comparator;

/**
 *
 * @author Esha
 */
public class SortDate implements Comparator<Meeting> {

    @Override
    public int compare(Meeting o1, Meeting o2) {
        if (!(o1 instanceof Meeting) || !(o2 instanceof Meeting)) {
            throw new ClassCastException("Cannot cast this object to class 'Meeting' to carry out comparison.");
        }
        if (o1.getDate() == null || o2.getDate() == null) {
            throw new NullPointerException("You entered a null Meeting to compare.");
        }
        return o1.getDate().compareTo(o2.getDate());
    }

}
