package com.example.dormease;

public class Dataholder {
    String name, id, room, building, complaint;

    public Dataholder(String name, String id, String room, String building, String complaint) {
        this.name = name;
        this.id = id;
        this.room = room;
        this.building = building;
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
