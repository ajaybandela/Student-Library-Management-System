package com.example.Student_Library_Management_System.DTO;

public class StudentUpdateMobRequestDto {


    private int id;

    private String mobNo;

    // Dto's depends on the API's being called.. add attributes as required

    public StudentUpdateMobRequestDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }
}
