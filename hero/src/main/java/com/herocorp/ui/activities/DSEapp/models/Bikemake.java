package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 27-Sep-16.
 */
public class Bikemake {
    String id;
    String makename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMakename() {
        return makename;
    }

    public void setMakename(String makename) {
        this.makename = makename;
    }

    public String toString() {
        return makename;
    }

    public Bikemake(String id, String makename) {

        this.id = id;
        this.makename = makename;
    }

    public Bikemake() {

    }
}
