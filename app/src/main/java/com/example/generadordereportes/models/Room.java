package com.example.generadordereportes.models;

public class Room {
    private String roomCode;
    private String roomName;
    private String roomDescription;

    public Room() {
    }

    public Room(String roomCode, String name, String description) {
        this.roomCode = roomCode;
        this.roomName = name;
        this.roomDescription = description;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
}
