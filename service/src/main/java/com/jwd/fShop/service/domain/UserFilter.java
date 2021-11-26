package com.jwd.fShop.service.domain;

import java.sql.Date;
import java.sql.Time;

public class UserFilter {
    private static final int DEFAULT_ID = -1;
    private static final String DEFAULT_USER_SUBNAME = "*";
    private static final String DEFAULT_HASH_PASS = "*";
    private static final Date DEFAULT_LOW_DATE = new Date(0);
    private static final Date DEFAULT_HIGH_DATE = new Date(Long.MAX_VALUE);
    private static final Time DEFAULT_LOW_TIME = new Time(0);
    private static final Time DEFAULT_HIGH_TIME = new Time(Long.MAX_VALUE);
    private static final int DEFAULT_ROLE = -1;

    private final int id;
    private final String userSubname;
    private final String subHashPass;
    private final Date lowDate;
    private final Date highDate;
    private final Time lowTime;
    private final Time highTime;
    private final int role;

    UserFilter(final Builder builder){
        this.id = builder.id;
        this.userSubname = builder.userSubname;
        this.subHashPass = builder.subHashPass;
        this.lowDate = builder.lowDate;
        this.highDate = builder.highDate;
        this.lowTime = builder.lowTime;
        this.highTime = builder.highTime;
        this.role = DEFAULT_ROLE;
    }

    public int getId() {
        return id;
    }

    public String getUserSubname() {
        return userSubname;
    }

    public String getSubHashPass() {
        return subHashPass;
    }

    public Date getLowDate() {
        return lowDate;
    }

    public Date getHighDate() {
        return highDate;
    }

    public Time getLowTime() {
        return lowTime;
    }

    public Time getHighTime() {
        return highTime;
    }

    public int getRole() {
        return role;
    }

    public boolean isId(){
        return id == DEFAULT_ID ? false : true;
    }

    public boolean isUserSubname(){
        return userSubname.equals(DEFAULT_USER_SUBNAME) ? false : true;
    }

    public boolean isSubHashPass(){
        return subHashPass.equals(DEFAULT_HASH_PASS) ? false : true;
    }

    public boolean isLowDate(){
        return lowDate.equals(DEFAULT_LOW_DATE) ? false : true;
    }

    public boolean isHighDate(){
        return highDate.equals(DEFAULT_HIGH_DATE) ? false : true;
    }

    public boolean isLowTime(){
        return lowTime.equals(DEFAULT_LOW_TIME) ? false : true;
    }

    public boolean isHighTime(){
        return highTime.equals(DEFAULT_HIGH_TIME) ? false : true;
    }

    public boolean isRole(){
        return role == DEFAULT_ROLE ? false : true;
    }

    public static class Builder{

        private int id;
        private String userSubname;
        private String subHashPass;
        private Date lowDate;
        private Date highDate;
        private Time lowTime;
        private Time highTime;
        private int role;

        public Builder(){
            id = DEFAULT_ID;
            userSubname = DEFAULT_USER_SUBNAME;
            subHashPass = DEFAULT_HASH_PASS;
            lowDate = new Date(DEFAULT_LOW_DATE.getTime());
            highDate = new Date(DEFAULT_HIGH_DATE.getTime());
            lowTime = new Time(DEFAULT_LOW_TIME.getTime());
            highTime = new Time(DEFAULT_HIGH_TIME.getTime());
        }

        public UserFilter build(){
            return new UserFilter(this);
        }

        public Builder setUserSubname(String userSubname) {
            this.userSubname = userSubname;
            return this;
        }

        public Builder setSubHashPass(String subHashPass) {
            this.subHashPass = subHashPass;
            return this;
        }

        public Builder setLowDate(Date lowDate) {
            this.lowDate = lowDate;
            return this;
        }

        public Builder setHighDate(Date highDate) {
            this.highDate = highDate;
            return this;
        }

        public Builder setLowTime(Time lowTime) {
            this.lowTime = lowTime;
            return this;
        }

        public Builder setHighTime(Time highTime) {
            this.highTime = highTime;
            return this;
        }

        public Builder setRole(int role) {
            this.role = role;
            return this;
        }
    }
}
