package com.parse.motors;


public class service_data {

    private String regist,date,branch;

    public service_data(String regist, String date, String branch) {
        this.regist = regist;
        this.date = date;
        this.branch = branch;
    }


    public String getRegist() {
        return regist;
    }

    public String getDate() {
        return date;
    }

    public String getBranch() {
        return branch;
    }

}
