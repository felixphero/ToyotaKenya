package com.parse.motors;

import java.util.Date;

public class fuel_data{

    private String regist,fuel,price;
    Date date;

    public fuel_data(String regist, String fuel, String price) {

        this.regist = regist;
        this.fuel = fuel;
        this.price = price;

    }


    public String getRegist() {
        return regist;
    }

    public String getFuel() {
        return fuel;
    }

    public String getPrice() {
        return price;
    }

   // public Date getDate() {
   //     return date;
   // }

}
