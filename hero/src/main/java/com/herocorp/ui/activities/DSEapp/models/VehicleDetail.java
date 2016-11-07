package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 24-Oct-16.
 */
public class VehicleDetail {
    String x_hmgl_card_num;
    String last_srvc_dt;
    String next_srvc_due_dt;
    String ins_policy_num;
    String x_ins_exp_dt;
    String model_cd;
    String serial_num;
    String primary_dealer_name;
    String attrib_42;
    String prod_attrib02_CD;
    String desc_text;
    String first_sale_dt;
    String ins_policy_co;
    String x_hmgl_card_points;


    public void setX_hmgl_card_num(String x_hmgl_card_num) {
        this.x_hmgl_card_num = x_hmgl_card_num;
    }

    public String getX_hmgl_card_num() {
        return x_hmgl_card_num;
    }

    public void setLast_srvc_dt(String last_srvc_dt) {
        this.last_srvc_dt = last_srvc_dt;
    }

    public String getLast_srvc_dt() {
        return last_srvc_dt;
    }

    public void setNext_srvc_due_dt(String next_srvc_due_dt) {
        this.next_srvc_due_dt = next_srvc_due_dt;
    }

    public String getNext_srvc_due_dt() {
        return next_srvc_due_dt;
    }

    public String getIns_policy_num() {
        return ins_policy_num;
    }

    public void setIns_policy_num(String ins_policy_num) {
        this.ins_policy_num = ins_policy_num;
    }

    public void setX_ins_exp_dt(String x_ins_exp_dt) {
        this.x_ins_exp_dt = x_ins_exp_dt;
    }

    public String getX_ins_exp_dt() {
        return x_ins_exp_dt;
    }

    public void setModel_cd(String model_cd) {
        this.model_cd = model_cd;
    }

    public String getModel_cd() {
        return model_cd;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setPrimary_dealer_name(String primary_dealer_name) {
        this.primary_dealer_name = primary_dealer_name;
    }

    public String getPrimary_dealer_name() {
        return primary_dealer_name;
    }

    public String getAttrib_42() {
        return attrib_42;
    }

    public void setAttrib_42(String attrib_42) {
        this.attrib_42 = attrib_42;
    }

    public void setProd_attrib02_CD(String prod_attrib02_CD) {
        this.prod_attrib02_CD = prod_attrib02_CD;
    }

    public String getProd_attrib02_CD() {
        return prod_attrib02_CD;
    }

    public void setDesc_text(String desc_text) {
        this.desc_text = desc_text;
    }

    public String getDesc_text() {
        return desc_text;
    }


    public void setFirst_sale_dt(String first_sale_dt) {
        this.first_sale_dt = first_sale_dt;
    }

    public String getFirst_sale_dt() {
        return first_sale_dt;
    }

    public void setIns_policy_co(String ins_policy_co) {
        this.ins_policy_co = ins_policy_co;
    }

    public String getIns_policy_co() {
        return ins_policy_co;
    }

    public void setX_hmgl_card_points(String x_hmgl_card_points) {
        this.x_hmgl_card_points = x_hmgl_card_points;
    }

    public String getX_hmgl_card_points() {
        return x_hmgl_card_points;
    }

    public VehicleDetail(String model_cd, String primary_dealer_name, String attrib_42,
                         String prod_attrib02_CD, String first_sale_dt, String serial_num,
                         String desc_text, String x_hmgl_card_num, String x_hmgl_card_points, String last_srvc_dt,
                         String next_srvc_due_dt, String x_ins_exp_dt, String ins_policy_num, String ins_policy_co) {

        this.x_hmgl_card_num = x_hmgl_card_num;
        this.last_srvc_dt = last_srvc_dt;
        this.next_srvc_due_dt = next_srvc_due_dt;
        this.ins_policy_num = ins_policy_num;
        this.x_ins_exp_dt = x_ins_exp_dt;
        this.model_cd = model_cd;
        this.serial_num = serial_num;
        this.primary_dealer_name = primary_dealer_name;
        this.attrib_42 = attrib_42;
        this.prod_attrib02_CD = prod_attrib02_CD;
        this.desc_text = desc_text;
        this.first_sale_dt = first_sale_dt;
        this.ins_policy_co = ins_policy_co;
        this.x_hmgl_card_points = x_hmgl_card_points;

    }
}

