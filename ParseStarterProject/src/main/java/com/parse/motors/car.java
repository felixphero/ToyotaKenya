package com.parse.motors;


public class car {
    private int id;
    private String make,model,engine,plate;

    public car(String make, String model, String engine, String plate) {
        //   this.id = id;
        this.make = make;
        this.model = model;
        this.engine = engine;
        this.plate = plate;

    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public String getEngine() {
        return engine;
    }

    public String getPlate() {
        return plate;
    }

}
