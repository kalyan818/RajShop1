package com.kalyan.rajshop;

public class lists {
    public String itemName;
    public String subItem;
    public String price;

    public lists(){

    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSubItem() {
        return subItem;
    }

    public void setSubItem(String subItem) {
        this.subItem = subItem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public lists(String itemName, String subItem, String price) {
        this.itemName = itemName;
        this.subItem = subItem;
        this.price = price;
    }
}
