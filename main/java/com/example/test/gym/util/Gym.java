package com.example.test.gym.util;

public class Gym {

    private int gymId;

    private String gymName;

    private String address;

    private String gymImage;

    public String getGymImage() {
        return gymImage;
    }

    public void setGymImage(String gymImage) {
        this.gymImage = gymImage;
    }

    public int getGymId() {
        return gymId;
    }

    public void setGymId(int gymId) {
        this.gymId = gymId;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gym(int gymId, String gymName, String address, String gymImage) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.address = address;
        this.gymImage = gymImage;
    }
}
