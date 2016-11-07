package com.herocorp.ui.activities.DSEapp.models;

import java.util.ArrayList;

/**
 * Created by rsawh on 27-Sep-16.
 */
public class Bikemodel {
    static ArrayList makeid = new ArrayList();
    static ArrayList modelname = new ArrayList();
    static ArrayList reqmodels = new ArrayList();

    public void getId() {
    }


    public void setId(String id) {
        this.makeid.add(id);
    }


    public static ArrayList getModelname(String id) {
        int i = 0;
        reqmodels.clear();
        for (i = 0; i < makeid.size(); i++) {
            if (makeid.get(i).equals(""))
                reqmodels.add(modelname.get(i));
            if (makeid.get(i).equals(id))
                reqmodels.add(modelname.get(i));
        }
        return reqmodels;
    }

    public void setModelname(String modelname) {
        this.modelname.add(modelname);
    }


    public Bikemodel(String id, String modelname) {

        this.makeid.add(id);
        this.modelname.add(modelname);
    }

    public Bikemodel() {

    }

}
