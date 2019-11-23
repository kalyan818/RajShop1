package com.kalyan.rajshop;

public class Upload1 {
    String type;
    String price;
    String uid;
    String status;

    public Upload1(String type, String price, String uid, String status) {
        this.type = type;
        this.price = price;
        this.uid = uid;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
