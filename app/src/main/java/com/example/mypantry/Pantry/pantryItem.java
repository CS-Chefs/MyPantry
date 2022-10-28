package com.example.mypantry.Pantry;

/**
 * Created by user on 05/09/2016.
 */
public class pantryItem {

    private long id;  // 'long' value type used when longer than int is needed
    private String name;
    private String details;


    // OVERLOAD THE CONTRUCTORS TO ALLOW FOR TESTING
    public pantryItem() {

    }


    public pantryItem(long id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }


    //GETTER AND SETTER METHODS FOR EMPTY CONSTRUCTOR

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String priority) {
        this.details = details;
    }

}
