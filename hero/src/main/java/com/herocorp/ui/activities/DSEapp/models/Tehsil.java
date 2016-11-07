package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 30-Sep-16.
 */
public class Tehsil {
    String id;
    String tehsil;
    String district_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }


    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String toString() {
        return tehsil;
    }

    public Tehsil(String district_id, String id, String tehsil) {
        this.district_id = district_id;
        this.id = id;
        this.tehsil = tehsil;
    }
}
