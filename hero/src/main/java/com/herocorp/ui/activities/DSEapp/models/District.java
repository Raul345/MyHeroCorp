package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 30-Sep-16.
 */
public class District {
    String id;
    String district;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String toString() {
        return district;
    }

    public District(String id, String district) {

        this.id = id;
        this.district = district;
    }
}
