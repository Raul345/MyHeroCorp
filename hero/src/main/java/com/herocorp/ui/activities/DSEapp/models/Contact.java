package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 21-Oct-16.
 */
public class Contact {
    String fst_name;
    String last_name;
    String cell_ph_num;
    String age;
    String gender;
    String email_addr;
    String state;
    String district;
    String tehsil;
    String city;
    String x_con_seq_no;


    public void setFst_name(String fst_name) {
        this.fst_name = fst_name;
    }

    public String getFst_name() {
        return fst_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setCell_ph_num(String cell_ph_num) {
        this.cell_ph_num = cell_ph_num;
    }

    public String getCell_ph_num() {
        return cell_ph_num;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail_addr(String email_addr) {
        this.email_addr = email_addr;
    }

    public String getEmail_addr() {
        return email_addr;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getTehsil() {
        return tehsil;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setX_con_seq_no(String x_con_seq_no) {
        this.x_con_seq_no = x_con_seq_no;
    }

    public String getX_con_seq_no() {
        return x_con_seq_no;
    }


    public Contact(String fst_name,
                   String last_name,
                   String cell_ph_num,
                   String age,
                   String gender,
                   String email_addr,
                   String state,
                   String district,
                   String tehsil,
                   String city,
                   String x_con_seq_no
    ) {

        this.fst_name = fst_name;
        this.last_name = last_name;
        this.cell_ph_num = cell_ph_num;
        this.age = age;
        this.gender = gender;
        this.email_addr = email_addr;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.city = city;
        this.x_con_seq_no=x_con_seq_no;

    }
}


