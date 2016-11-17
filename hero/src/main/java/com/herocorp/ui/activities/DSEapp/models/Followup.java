package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 21-Sep-16.
 */
public class Followup {

    String first_name;
    String last_name;
    String cell_ph_no;
    String age;
    String gender;
    String email_addr;
    String state;
    String district;
    String tehsil;
    String city;
    String x_con_seq_no;
    String x_model_interested;
    String expcted_date_purchase;
    String x_exchange_required;
    String x_finance_required;
    String exist_vehicle;
    String followup_comments;
    String enquiry_id;
    String follow_date;
    String enquiry_entry_date;
    String dealer_bu_id;
    String close_status;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getCell_ph_no() {
        return cell_ph_no;
    }

    public void setCell_ph_no(String cell_ph_no) {
        this.cell_ph_no = cell_ph_no;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getEmail_addr() {
        return email_addr;
    }

    public void setEmail_addr(String email_addr) {
        this.email_addr = email_addr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getX_con_seq_no() {
        return x_con_seq_no;
    }

    public void setX_con_seq_no(String x_con_seq_no) {
        this.x_con_seq_no = x_con_seq_no;
    }


    public String getX_model_interested() {
        return x_model_interested;
    }

    public void setX_model_interested(String x_model_interested) {
        this.x_model_interested = x_model_interested;
    }


    public String getExpcted_date_purchase() {
        return expcted_date_purchase;
    }

    public void setExpcted_date_purchase(String expcted_date_purchase) {
        this.expcted_date_purchase = expcted_date_purchase;

    }


    public String getX_exchange_required() {
        return x_exchange_required;
    }

    public void setX_exchange_required(String x_exchange_required) {
        this.x_exchange_required = x_exchange_required;
    }


    public String getX_finance_required() {
        return x_finance_required;
    }

    public void setX_finance_required(String x_finance_required) {
        this.x_finance_required = x_finance_required;
    }


    public String getExist_vehicle() {
        return exist_vehicle;
    }

    public void setExist_vehicle(String exist_vehicle) {
        this.exist_vehicle = exist_vehicle;
    }


    public String getFollowup_comments() {
        return followup_comments;
    }

    public void setFollowup_comments(String followup_comments) {
        this.followup_comments = followup_comments;
    }


    public String getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(String enquiry_id) {
        this.enquiry_id = enquiry_id;
    }


    public String getFollow_date() {
        return follow_date;
    }

    public void setFollow_date(String follow_date) {
        this.follow_date = follow_date;
    }


    public String getEnquiry_entry_date() {
        return enquiry_entry_date;
    }

    public void setEnquiry_entry_date(String enquiry_entry_date) {
        this.enquiry_entry_date = getEnquiry_entry_date();
    }


    public String getDealer_bu_id() {
        return dealer_bu_id;
    }

    public void setDealer_bu_id(String dealer_bu_id) {
        this.dealer_bu_id = dealer_bu_id;
    }

    public String getClose_status() {
        return close_status;
    }

    public void setClose_status(String close_status) {
        this.close_status = close_status;
    }


    public Followup(String first_name, String last_name, String cell_ph_no, String age, String gender, String email_addr, String state,
                    String district, String tehsil, String city, String x_con_seq_no, String x_model_interested, String expcted_date_purchase,
                    String x_exchange_required, String x_finance_required, String exist_vehicle, String followup_comments, String enquiry_id,
                    String follow_date, String enquiry_entry_date, String dealer_bu_id
    ) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.cell_ph_no = cell_ph_no;
        this.age = age;
        this.gender = gender;
        this.email_addr = email_addr;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.city = city;
        this.x_con_seq_no = x_con_seq_no;
        this.x_model_interested = x_model_interested;
        this.expcted_date_purchase = expcted_date_purchase;
        this.x_exchange_required = x_exchange_required;
        this.x_finance_required = x_finance_required;
        this.exist_vehicle = exist_vehicle;
        this.followup_comments = followup_comments;
        this.enquiry_id = enquiry_id;
        this.follow_date = follow_date;
        this.enquiry_entry_date = enquiry_entry_date;
        this.dealer_bu_id = dealer_bu_id;
    }

    public Followup(String first_name, String last_name, String cell_ph_no, String age, String gender, String email_addr, String state,
                    String district, String tehsil, String city, String x_con_seq_no, String x_model_interested, String expcted_date_purchase,
                    String x_exchange_required, String x_finance_required, String exist_vehicle, String followup_comments, String enquiry_id,
                    String follow_date, String enquiry_entry_date, String dealer_bu_id, String close_status
    ) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.cell_ph_no = cell_ph_no;
        this.age = age;
        this.gender = gender;
        this.email_addr = email_addr;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.city = city;
        this.x_con_seq_no = x_con_seq_no;
        this.x_model_interested = x_model_interested;
        this.expcted_date_purchase = expcted_date_purchase;
        this.x_exchange_required = x_exchange_required;
        this.x_finance_required = x_finance_required;
        this.exist_vehicle = exist_vehicle;
        this.followup_comments = followup_comments;
        this.enquiry_id = enquiry_id;
        this.follow_date = follow_date;
        this.enquiry_entry_date = enquiry_entry_date;
        this.dealer_bu_id = dealer_bu_id;
        this.close_status = close_status;
    }
    public Followup() {

    }
}
