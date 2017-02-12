package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 09-Feb-17.
 */

public class Pitch {
    String id;
    String gender;
    String age;
    String occupation;
    String ownership;
    String usage;
    String area;
    String img_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public Pitch(String id, String gender, String age, String occupation, String ownership, String usage, String area, String img_path) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.ownership = ownership;
        this.usage = usage;
        this.area = area;
        this.img_path = img_path;
    }

    public Pitch() {
    }
}
