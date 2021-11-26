package com.jwd.fShop.dao.domain;

import java.sql.Date;
import java.sql.Time;

public class User {
    private final int id;
    private final String userName;
    private final String hashPass;
    private final Date registrationDate;
    private final Time registrationTime;
    private final int role;

    User(Builder builder){
        this.id = builder.id;
        this.userName = builder.userName;
        this.hashPass = builder.hashPass;
        this.registrationDate = builder.registrationDate;
        this.registrationTime = builder.registrationTime;
        this.role = builder.role;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getHashPass() {
        return hashPass;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Time getRegistrationTime() {
        return registrationTime;
    }

    public int getRole() {
        return role;
    }

    public static class Builder{
        private int id;
        private String userName;
        private String hashPass;
        private Date registrationDate;
        private Time registrationTime;
        private int role;

        public Builder(final User user){
            id = user.id;
            userName = user.userName;
            hashPass = user.hashPass;
            registrationDate = user.registrationDate;
            registrationTime = user.registrationTime;
            role = user.role;
        }
        public Builder(){
            id = -1;
            userName = "";
            hashPass = "";
            registrationDate = new Date(0);
            registrationTime = new Time(0);
        }


        public User build(){
            return new User(this);
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setHashPass(String hashPass) {
            this.hashPass = hashPass;
            return this;
        }

        public Builder setRegistrationDate(Date registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public Builder setRegistrationTime(Time registrationTime) {
            this.registrationTime = registrationTime;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setRole(int role) {
            this.role = role;
            return this;
        }
    }
}
