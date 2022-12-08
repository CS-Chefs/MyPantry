package com.example.mypantry.GroceryList;

public class GroceryItem {

    private String groceryItem, details, id, date;

    public GroceryItem(){

    }

    public GroceryItem(String groceryItem, String details, String id, String date) {
        this.groceryItem = groceryItem;
        this.details = details;
        this.id = id;
        this.date = date;
    }

    public String getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(String groceryItem) {
        this.groceryItem = groceryItem;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
