package com.parse.motors;

public class sparepart {
    private int id;
    private String price,desc,stock;

    public sparepart(String price, String desc, String stock) {
        this.id = id;
        this.price = price;
        this.desc = desc;
        this.stock = stock;
    }


    public String getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getStock() {
        return stock;
    }


}
