package com.ticket.bus.employeetaskplanner;

public class ModelUser {

    String id;
    String name    ;
    String lastName;
    String avatar  ;
    String nickname;
    String company ;
    String jobTitle;
    String email   ;
    String phone   ;
    String address ;
    String birthday;
    String notes   ;

    public ModelUser(String id, String name, String lastName, String avatar, String nickname, String company, String jobTitle, String email, String phone, String address, String birthday, String notes) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.avatar = avatar;
        this.nickname = nickname;
        this.company = company;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNotes() {
        return notes;
    }
}
