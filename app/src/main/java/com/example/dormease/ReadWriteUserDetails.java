package com.example.dormease;

public class ReadWriteUserDetails {
    public String name, stid,building,roomno;

    public ReadWriteUserDetails(String textname,String textstid,String textbuilding,String textroomno)
    {
        this.name = textname;
        this.stid = textstid;
        this.building = textbuilding;
        this.roomno = textroomno;
    }
}
