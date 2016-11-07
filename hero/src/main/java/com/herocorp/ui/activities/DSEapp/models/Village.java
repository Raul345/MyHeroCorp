package com.herocorp.ui.activities.DSEapp.models;

import java.util.ArrayList;

/**
 * Created by rsawh on 30-Sep-16.
 */
public class Village {
    static ArrayList tehsil_id = new ArrayList();
    static ArrayList id = new ArrayList();
    static ArrayList reqvillage = new ArrayList();
    static ArrayList village = new ArrayList();

    public void getTehsil_id() {
    }

    public void setTehsil_id(String tehsil_id) {
        this.tehsil_id.add(tehsil_id);
    }

    public void setId(String id) {
        this.id.add(id);
    }
    public void getId() {
    }


    public static ArrayList getVillage(String id) {
        int i = 0;
        reqvillage.clear();
        for (i = 0; i < tehsil_id.size(); i++) {
          /* if (tehsil_id.get(i).equals(""))
                reqvillage.add(village.get(i));*/
            if (tehsil_id.get(i).equals(id))
                reqvillage.add(village.get(i));
        }
        return reqvillage;
    }

    public void setVillage(String village) {
        this.village.add(village);
    }


    public Village(String tehsil_id, String id, String village) {

        this.tehsil_id.add(tehsil_id);
        this.id.add(id);
        this.village.add(village);
    }

    public Village() {

    }
}
