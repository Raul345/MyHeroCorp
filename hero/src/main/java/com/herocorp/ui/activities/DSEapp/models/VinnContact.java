package com.herocorp.ui.activities.DSEapp.models;

import java.util.ArrayList;

/**
 * Created by rsawh on 19-Oct-16.
 */
public class VinnContact {
    static ArrayList fst_name = new ArrayList();
    static ArrayList last_name = new ArrayList();
    static ArrayList cell_ph_num = new ArrayList();
    static ArrayList gender = new ArrayList();
    static ArrayList email_addr = new ArrayList();
    static ArrayList state = new ArrayList();
    static ArrayList district = new ArrayList();
    static ArrayList tehsil = new ArrayList();
    static ArrayList city = new ArrayList();
    static ArrayList x_con_seq_no = new ArrayList();
    static ArrayList x_hmgl_card_num = new ArrayList();
    static ArrayList last_srvc_dt = new ArrayList();
    static ArrayList next_srvc_due_dt = new ArrayList();
    static ArrayList ins_policy_num = new ArrayList();
    static ArrayList x_ins_exp_dt = new ArrayList();
    static ArrayList model_cd = new ArrayList();
    static ArrayList serial_num = new ArrayList();
    static ArrayList primary_dealer_name = new ArrayList();
    static ArrayList attrib_42 = new ArrayList();
    static ArrayList prod_attrib02_CD = new ArrayList();
    static ArrayList desc_text = new ArrayList();
    static ArrayList first_sale_dt = new ArrayList();
    static ArrayList ins_policy_co = new ArrayList();
    static ArrayList x_hmgl_card_points = new ArrayList();
    static ArrayList age = new ArrayList();
    static ArrayList addr = new ArrayList();
    static ArrayList addr_line_2 = new ArrayList();

    ArrayList model = new ArrayList();
    ArrayList dealername = new ArrayList();
    ArrayList variant = new ArrayList();
    ArrayList colour = new ArrayList();
    ArrayList vinno = new ArrayList();
    ArrayList purchasedate = new ArrayList();
    ArrayList skudesc = new ArrayList();

    ArrayList cardno = new ArrayList();
    ArrayList currentpoints = new ArrayList();
    ArrayList lastservicedate = new ArrayList();
    ArrayList nextservicedate = new ArrayList();
    ArrayList expirydate = new ArrayList();
    ArrayList policyno = new ArrayList();
    ArrayList insuranceco = new ArrayList();


    public ArrayList getvindetail(String firstname, String lastname, String mobile, String states, String districts) {
        vinno.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                vinno.add(serial_num.get(i));
            }
        }
        return vinno;
    }

    public ArrayList getmodeldetail(String firstname, String lastname, String mobile, String states, String districts) {
        model.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                model.add(model_cd.get(i));
            }
        }
        return model;
    }

    public ArrayList getdealerdetail(String firstname, String lastname, String mobile, String states, String districts) {
        dealername.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                dealername.add(primary_dealer_name.get(i));
            }
        }
        return dealername;
    }

    public ArrayList getvariantdetail(String firstname, String lastname, String mobile, String states, String districts) {
        variant.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                variant.add(attrib_42.get(i));
            }
        }
        return variant;
    }

    public ArrayList getcolourdetail(String firstname, String lastname, String mobile, String states, String districts) {
        colour.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                colour.add(prod_attrib02_CD.get(i));
            }
        }
        return colour;
    }

    public ArrayList getpur_datedetail(String firstname, String lastname, String mobile, String states, String districts) {
        purchasedate.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                purchasedate.add(first_sale_dt.get(i));
            }
        }
        return purchasedate;
    }

    public ArrayList getskudetail(String firstname, String lastname, String mobile, String states, String districts) {
        skudesc.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                skudesc.add(desc_text.get(i));
            }
        }
        return skudesc;
    }

    public ArrayList getcardnodetail(String firstname, String lastname, String mobile, String states, String districts) {
        cardno.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                cardno.add(x_hmgl_card_num.get(i));
            }
        }
        return cardno;
    }

    public ArrayList getCurrentpointsdetail(String firstname, String lastname, String mobile, String states, String districts) {
        currentpoints.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                currentpoints.add(x_hmgl_card_points.get(i));
            }
        }
        return currentpoints;
    }

    public ArrayList getLastservicedatedetail(String firstname, String lastname, String mobile, String states, String districts) {
        lastservicedate.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                lastservicedate.add(last_srvc_dt.get(i));
            }
        }
        return lastservicedate;
    }

    public ArrayList getNextservicedatedetail(String firstname, String lastname, String mobile, String states, String districts) {
        nextservicedate.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                nextservicedate.add(next_srvc_due_dt.get(i));
            }
        }
        return nextservicedate;
    }

    public ArrayList getPolicynodetail(String firstname, String lastname, String mobile, String states, String districts) {
        policyno.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                policyno.add(ins_policy_num.get(i));
            }
        }
        return policyno;
    }

    public ArrayList getExpirydatedetail(String firstname, String lastname, String mobile, String states, String districts) {
        expirydate.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                expirydate.add(x_ins_exp_dt.get(i));
            }
        }
        return expirydate;
    }

    public ArrayList getInsurancecodetail(String firstname, String lastname, String mobile, String states, String districts) {
        insuranceco.clear();
        for (int i = 0; i < fst_name.size(); i++) {
            if (fst_name.get(i).equals(firstname) && last_name.get(i).equals(lastname) && cell_ph_num.get(i).equals(mobile) && state.get(i).equals(states)
                    && district.get(i).equals(districts)) {
                insuranceco.add(ins_policy_co.get(i));
            }
        }
        return insuranceco;
    }


    public void setFst_name(String fst_name) {
        this.fst_name.add(fst_name);
    }

  /*  public String getFst_name() {
        return fst_name;
    }*/

    public void setLast_name(String last_name) {
        this.last_name.add(last_name);
    }

      /* public String getLast_name() {
           return last_name;
       }*/

    public void setCell_ph_num(String cell_ph_num) {
        this.cell_ph_num.add(cell_ph_num);
    }

    /*   public String getCell_ph_num() {
           return cell_ph_num;
       }*/

    public void setAge(String age) {
        this.age.add(age);
    }

      /* public String getAge() {
           return age;
       }*/

      /* public String getGender() {
           return gender;
       }*/

    public void setGender(String gender) {
        this.gender.add(gender);
    }

    public void setEmail_addr(String email_addr) {
        this.email_addr.add(email_addr);
    }

     /*  public String getEmail_addr() {
           return email_addr;
       }*/

    public void setState(String state) {
        this.state.add(state);
    }

      /* public String getState() {
           return state;
       }*/

    public void setDistrict(String district) {
        this.district.add(district);
    }

      /* public String getDistrict() {
           return district;
       }*/

    public void setTehsil(String tehsil) {
        this.tehsil.add(tehsil);
    }

     /*  public String getTehsil() {
           return tehsil;
       }*/

      /* public String getCity() {
           return city;
       }*/

    public void setCity(String city) {
        this.city.add(city);
    }

    public void setX_con_seq_no(String x_con_seq_no) {
        this.x_con_seq_no.add(x_con_seq_no);
    }

      /* public String getX_con_seq_no() {
           return x_con_seq_no;
       }*/

    public void setX_hmgl_card_num(String x_hmgl_card_num) {
        this.x_hmgl_card_num.add(x_hmgl_card_num);
    }

      /* public String getX_hmgl_card_num() {
           return x_hmgl_card_num;
       }*/

    public void setLast_srvc_dt(String last_srvc_dt) {
        this.last_srvc_dt.add(last_srvc_dt);
    }

      /* public String getLast_srvc_dt() {
           return last_srvc_dt;
       }*/

    public void setNext_srvc_due_dt(String next_srvc_due_dt) {
        this.next_srvc_due_dt.add(next_srvc_due_dt);
    }

       /*public String getNext_srvc_due_dt() {
           return next_srvc_due_dt;
       }*/

     /*  public String getIns_policy_num() {
           return ins_policy_num;
       }*/

    public void setIns_policy_num(String ins_policy_num) {
        this.ins_policy_num.add(ins_policy_num);
    }

    public void setX_ins_exp_dt(String x_ins_exp_dt) {
        this.x_ins_exp_dt.add(x_ins_exp_dt);
    }

      /* public String getX_ins_exp_dt() {
           return x_ins_exp_dt;
       }*/

    public void setModel_cd(String model_cd) {
        this.model_cd.add(model_cd);
    }

      /* public String getModel_cd() {
           return model_cd;
       }*/

     /*  public void setSerial_num(String serial_num) {
           this.serial_num = serial_num;
       }*/

      /* public String getSerial_num() {
           return serial_num;
       }*/

    public void setPrimary_dealer_name(String primary_dealer_name) {
        this.primary_dealer_name.add(primary_dealer_name);
    }

      /* public String getPrimary_dealer_name() {
           return primary_dealer_name;
       }*/

    /* public String getAttrib_42() {
         return attrib_42;
     }
*/
    public void setAttrib_42(String attrib_42) {
        this.attrib_42.add(attrib_42);
    }

    public void setProd_attrib02_CD(String prod_attrib02_CD) {
        this.prod_attrib02_CD.add(prod_attrib02_CD);
    }

      /* public String getProd_attrib02_CD() {
           return prod_attrib02_CD;
       }*/

    public void setDesc_text(String desc_text) {
        this.desc_text.add(desc_text);
    }

     /*  public String getDesc_text() {
           return desc_text;
       }*/


    public void setFirst_sale_dt(String first_sale_dt) {
        this.first_sale_dt.add(first_sale_dt);
    }

     /*  public String getFirst_sale_dt() {
           return first_sale_dt;
       }*/

    public void setIns_policy_co(String ins_policy_co) {
        this.ins_policy_co.add(ins_policy_co);
    }

      /* public String getIns_policy_co() {
           return ins_policy_co;
       }*/

    public void setX_hmgl_card_points(String x_hmgl_card_points) {
        this.x_hmgl_card_points.add(x_hmgl_card_points);
    }

      /* public String getX_hmgl_card_points() {
           return x_hmgl_card_points;
       }*/

     /*  public String getAddr() {
           return addr;
       }*/

    public void setAddr(String addr) {
        this.addr.add(addr);
    }

    public void setAddr_line_2(String addr_line_2) {
        this.addr_line_2.add(addr_line_2);
    }

    /*    public String getAddr_line_2() {
            return addr_line_2;
        }
    */
    public VinnContact(String fst_name,
                       String last_name,
                       String cell_ph_num,
                       String gender,
                       String email_addr,
                       String state,
                       String district,
                       String tehsil,
                       String city,
                       String x_con_seq_no,
                       String x_hmgl_card_num,
                       String last_srvc_dt,
                       String next_srvc_due_dt,
                       String ins_policy_num,
                       String x_ins_exp_dt,
                       String model_cd,
                       String serial_num,
                       String primary_dealer_name,
                       String attrib_42,
                       String prod_attrib02_CD,
                       String desc_text,
                       String first_sale_dt,
                       String ins_policy_co,
                       String x_hmgl_card_points,
                       String age,
                       String addr,
                       String addr_line_2) {
        this.fst_name.add(fst_name);
        this.last_name.add(last_name);
        this.cell_ph_num.add(cell_ph_num);
        this.gender.add(gender);
        this.email_addr.add(email_addr);
        this.state.add(state);
        this.district.add(district);
        this.tehsil.add(tehsil);
        this.city.add(city);
        this.x_con_seq_no.add(x_con_seq_no);
        this.x_hmgl_card_num.add(x_hmgl_card_num);
        this.last_srvc_dt.add(last_srvc_dt);
        this.next_srvc_due_dt.add(next_srvc_due_dt);
        this.ins_policy_num.add(ins_policy_num);
        this.x_ins_exp_dt.add(x_ins_exp_dt);
        this.model_cd.add(model_cd);
        this.serial_num.add(serial_num);
        this.primary_dealer_name.add(primary_dealer_name);
        this.attrib_42.add(attrib_42);
        this.prod_attrib02_CD.add(prod_attrib02_CD);
        this.desc_text.add(desc_text);
        this.first_sale_dt.add(first_sale_dt);
        this.ins_policy_co.add(ins_policy_co);
        this.x_hmgl_card_points.add(x_hmgl_card_points);
        this.age.add(age);
        this.addr.add(addr);
        this.addr_line_2.add(addr_line_2);
    }

    public VinnContact() {
    }
}
