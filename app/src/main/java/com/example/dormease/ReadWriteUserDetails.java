package com.example.dormease;

public class ReadWriteUserDetails {
    public String stid,building,roomno,userStatus;

    public ReadWriteUserDetails()
    {

    };
    //userstatus = 0 -> student 1 -> admin

    public ReadWriteUserDetails(String textstid,String textbuilding,String textroomno, String userStatus)
    {
        this.stid = textstid;
        this.building = textbuilding;
        this.roomno = textroomno;
        this.userStatus = userStatus;
    }
}
