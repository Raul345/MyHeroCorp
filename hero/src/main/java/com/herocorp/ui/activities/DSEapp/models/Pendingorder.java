package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 21-Sep-16.
 */
public class Pendingorder {
    String order_no;
    String dealer_code;
    String order_date;
    String cust_name;
    String mobile;
    String model_cd;
    String dse_name;
    String reason;
    String campaign;
    String expected_date;
    String financer_name;


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }


    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }


    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }


    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getModel_cd() {
        return model_cd;
    }

    public void setModel_cd(String model_cd) {
        this.model_cd = model_cd;
    }


    public String getDse_name() {
        return dse_name;
    }

    public void setDse_name(String dse_name) {
        this.dse_name = dse_name;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }


    public String getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(String expected_date) {
        this.expected_date = expected_date;
    }


    public String getFinancer_name() {
        return financer_name;
    }

    public void setFinancer_name(String financer_name) {
        this.financer_name = financer_name;
    }



    public Pendingorder(String order_no, String dealer_code, String order_date, String cust_name, String mobile, String model_cd, String dse_name, String reason,
                    String campaign, String expected_date, String financer_name ) {

        this.order_no = order_no;
        this.dealer_code = dealer_code;
        this.order_date = order_date;
        this.cust_name = cust_name;
        this.mobile = mobile;
        this.model_cd = model_cd;
        this.dse_name = dse_name;
        this.reason = reason;
        this.campaign = campaign;
        this.expected_date = expected_date;
        this.financer_name= financer_name;

   }



}
