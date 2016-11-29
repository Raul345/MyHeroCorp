package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 28-Nov-16.
 */

public class LocalEnquiry {
    String contact_no;
    String reg_no;

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getReg_no() {
        return reg_no;
    }

    public LocalEnquiry(String contact_no, String reg_no) {
        this.contact_no = contact_no;
        this.reg_no = reg_no;
    }
    public LocalEnquiry() {

    }
}
