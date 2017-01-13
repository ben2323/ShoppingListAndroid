package com.example.bental.studentsapp2.model;


/**
 * Created by Ben on 07/12/2016.
 */

public class Student {
    public Student(String address, String name, String id, String phone,boolean checked, int image){

    }
    public Student(String address, String name, String id, String phone,boolean checked, int image, CustomSimpleDate birthDate, CustomSimpleDate birthTime){
        this.address = address;
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.image = image;
        this.isChecked = checked;
        this.birthDate = birthDate;
        this.birthTime = birthTime;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    String name;
    String id;
    String phone;
    String address;
    int image;
    boolean isChecked;
    CustomSimpleDate birthDate;
    CustomSimpleDate birthTime;

    public CustomSimpleDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(CustomSimpleDate birthDate) {
        this.birthDate = birthDate;
    }

    public CustomSimpleDate getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(CustomSimpleDate birthTime) {
        this.birthTime = birthTime;
    }



}
