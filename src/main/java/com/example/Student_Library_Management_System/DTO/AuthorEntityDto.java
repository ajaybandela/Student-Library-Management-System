package com.example.Student_Library_Management_System.DTO;

public class AuthorEntityDto {

    // This is just an object that will be used to take Request from postMan


    // It will contain parameters that we want to send from postMan

    //it is not here because we don't want to send it from postman

    private String name;

    private int age;

    private String country;

    private double rating;

    public AuthorEntityDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
