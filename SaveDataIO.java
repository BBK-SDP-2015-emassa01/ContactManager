/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContactManager;
import java.io.*;
import java.util.Set;
/**
 *
 * @author Esha
 */
public class SaveDataIO {
    
    Set<Contact> contactSet;

    public SaveDataIO(Set<Contact> contactsSet) throws FileNotFoundException {
        this.contactSet = contactsSet;
    }
    
    public void writeSetToFile(String filename) throws IOException{
        filename = "contacts.csv";
        FileWriter write = new FileWriter(filename);
        BufferedWriter bufferWriter = new BufferedWriter(write);
        
        for (Contact s:contactSet){
            write.append(s.getName()+",");
            write.append(s.getId()+",");
            write.append(s.getNotes());
            write.append("\n");
        }

        bufferWriter.close();
    }
    
}
