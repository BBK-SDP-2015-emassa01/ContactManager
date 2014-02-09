/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;


/**
 *
 * @author Esha
 */
public class Main {
    private Set<Contact> contacts;
    private Set<Meeting> meetings;
    private Calendar date; 
    private int id;
    private String text;//notes about meeting
    
    String newline = System.getProperty("line.separator");
    
    //public static void main(String[] args) throws IOException{
        Main mainScript = new Main();
        mainScript.launch();
    //}
    
    public void launch() throws FileNotFoundException, IOException{
    
    try {
    // load the file
    File configFile = new File("contacts.txt");
    FileInputStream inStream = new FileInputStream(configFile);

    Properties config = new Properties();
    config.load(inStream);
    
    Date current = new Date();
    config.setProperty("runtime", current.toString());
    // save the properties file
    FileOutputStream outStream = new FileOutputStream(configFile);
    
    write(outStream, "Esha Yesno");//test
    write(outStream, "Esha Noyes");//test
    write(outStream, "Esha Maybeso");//test
    config.store(outStream, "Modified");
    inStream.close();
    config.list(System.out);
    } catch (IOException ioe) {
    System.out.println("IO error " + ioe.getMessage());
    }
    
    

    
    
    
}
    public void write(FileOutputStream stream, String output) throws IOException {
        output = output + newline;
    
        byte[] data = output.getBytes();
        stream.write(data, 0, data.length);
    }
}

