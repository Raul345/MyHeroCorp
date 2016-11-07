package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 19-Oct-16.
 */
public class CampaignContact {
    String row_id;
    String camp_name;
    String opty_id;

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public String getCamp_name() {
        return camp_name;
    }

    public void setCamp_name(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getOpty_id() {
        return opty_id;
    }

    public void setOpty_id(String opty_id) {
        this.opty_id = opty_id;
    }

    public CampaignContact(String row_id, String camp_name, String opty_id) {
        this.row_id = row_id;
        this.camp_name = camp_name;
        this.opty_id = opty_id;
    }
}
