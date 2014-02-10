/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.*;
/**
 *
 * @author Esha
 */
public class SaveDataIO {
    
    //create output stream
    OutputStream contactManagerContacts;

    public SaveDataIO() throws FileNotFoundException {
        this.contactManagerContacts = new FileOutputStream("contacts.txt");
    }
    
}
