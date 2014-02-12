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
public class ContactImpl implements Contact {
    private String name;//of contact
    private String notes; //about contact
    private static int id = 1;//contact's id number
    
    public ContactImpl(int id, String name, String notes){
        setID(id);
        setName(name);
        addNotes(notes);
        id++;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getNotes(){
        if (notes ==null){//must return notes even if no notes available (in which case returns emply string).
            return "";
        }
        return this.notes;
    }
    public void addNotes(String note){
        this.notes = note;
    }
    
}
