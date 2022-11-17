package com.example.generadordereportes.models;

public class Item {
    String itemCode;
    String itemName;
    String itemRoomCode;

    public Item() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemRoomCode() {
        return itemRoomCode;
    }

    public void setItemRoomCode(String itemRoomCode) {
        this.itemRoomCode = itemRoomCode;
    }

    public Item(String itemCode, String itemName, String itemRoomCode) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemRoomCode = itemRoomCode;
    }
}
