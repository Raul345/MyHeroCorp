package com.herocorp.ui.activities.DSEapp.models;

import java.util.ArrayList;

/**
 * Created by rsawh on 19-Oct-16.
 */
public class EnquiryContact {
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
    String x_model_interested;
    String expctd_dt_purchase;
    String x_exchange_required;
    String x_finance_required;
    String existing_vehicle;
    String followup_comments;
    String enquiry_id;
    String x_test_ride_req;
    String enquiry_entry_date;
    String addr;
    String addr_line_2;
    String make_cd;
    String model_cd;
    String dealer_bu_id;
    String follow_date;
    String sales_pitch_no;
    String priority;
    String occupation;
    String usage;

    static ArrayList fst_names = new ArrayList();
    static ArrayList last_names = new ArrayList();
    static ArrayList cell_ph_nums = new ArrayList();
    static ArrayList genders = new ArrayList();
    static ArrayList email_addrs = new ArrayList();
    static ArrayList states = new ArrayList();
    static ArrayList districts = new ArrayList();


    public static ArrayList enquiryids = new ArrayList();
    public static ArrayList models = new ArrayList();
    public static ArrayList enquirydates = new ArrayList();
    public static ArrayList followupdates = new ArrayList();
    public static ArrayList purchasedates = new ArrayList();
    public static ArrayList salespitch = new ArrayList();
    public static ArrayList priorities = new ArrayList();
    public static ArrayList occupations = new ArrayList();
    public static ArrayList usages = new ArrayList();

    static ArrayList exchanges_reqd = new ArrayList();
    static ArrayList finances_reqd = new ArrayList();
    static ArrayList comments = new ArrayList();
    static ArrayList exist_vehicles = new ArrayList();

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

    public void setX_model_interested(String x_model_interested) {
        this.x_model_interested = x_model_interested;
    }

    public String getX_model_interested() {
        return x_model_interested;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }

    public void setExpctd_dt_purchase(String expctd_dt_purchase) {
        this.expctd_dt_purchase = expctd_dt_purchase;
        this.purchasedates.add(expctd_dt_purchase);
    }


    public String getExpctd_dt_purchase() {
        return expctd_dt_purchase;
    }

    public void setX_exchange_required(String x_exchange_required) {
        this.x_exchange_required = x_exchange_required;
    }

    public String getX_exchange_required() {
        return x_exchange_required;
    }

    public void setX_finance_required(String x_finance_required) {
        this.x_finance_required = x_finance_required;
    }

    public String getX_finance_required() {
        return x_finance_required;
    }

    public void setExisting_vehicle(String existing_vehicle) {
        this.existing_vehicle = existing_vehicle;
    }

    public String getExisting_vehicle() {
        return existing_vehicle;
    }

    public String getFollowup_comments() {
        return followup_comments;
    }

    public void setFollowup_comments(String followup_comments) {
        this.followup_comments = followup_comments;
    }

    public void setEnquiry_id(String enquiry_id) {
        this.enquiry_id = enquiry_id;
        this.enquiryids.add(enquiry_id);
    }

    public String getEnquiry_id() {
        return enquiry_id;
    }

    public void setSales_pitch_no(String sales_pitch_no) {
        this.sales_pitch_no = sales_pitch_no;
    }

    public String getSales_pitch_no() {
        return sales_pitch_no;
    }

    public void setX_test_ride_req(String x_test_ride_req) {
        this.x_test_ride_req = x_test_ride_req;
    }

    public String getX_test_ride_req() {
        return x_test_ride_req;
    }

    public void setEnquiry_entry_date(String enquiry_entry_date) {
        this.enquiry_entry_date = enquiry_entry_date;
        this.enquirydates.add(enquiry_entry_date);
    }

    public String getEnquiry_entry_date() {
        return enquiry_entry_date;
    }


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setAddr_line_2(String addr_line_2) {
        this.addr_line_2 = addr_line_2;
    }

    public String getAddr_line_2() {
        return addr_line_2;
    }

    public void setMake_cd(String make_cd) {
        this.make_cd = make_cd;
    }

    public String getMake_cd() {
        return make_cd;
    }

    public void setModel_cd(String model_cd) {
        this.model_cd = model_cd;
        this.models.add(model_cd);
    }

    public String getModel_cd() {
        return model_cd;
    }

    public String getDealer_bu_id() {
        return dealer_bu_id;
    }

    public void setDealer_bu_id(String dealer_bu_id) {
        this.dealer_bu_id = dealer_bu_id;
    }

    public String getFollow_date() {
        return follow_date;
    }

    public void setFollow_date(String follow_date) {
        this.follow_date = follow_date;
        this.followupdates.add(follow_date);
    }

    public String getEnquiry_id(String firstname, String lastname, String mobile, String state, String district) {
        String enquiryid = "";
        for (int i = 0; i < enquiryids.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                enquiryid = enquiryids.get(i).toString();
            }
        }
        return enquiryid;
    }


    public String getModel_cd(String firstname, String lastname, String mobile, String state, String district) {
        String model = "";
        for (int i = 0; i < enquiryids.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                model = models.get(i).toString();
            }
        }
        return model;
    }

    public String getEnquiry_entry_date(String firstname, String lastname, String mobile, String state, String district) {
        String enquirydate = "";
        for (int i = 0; i < enquiryids.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                enquirydate = enquirydates.get(i).toString();
            }
        }
        return enquirydate;
    }

    public String getExpctd_dt_purchase(String firstname, String lastname, String mobile, String state, String district) {
        String purchasedate = "";
        for (int i = 0; i < enquiryids.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                purchasedate = purchasedates.get(i).toString();
            }
        }
        return purchasedate;
    }

    public String getX_exchange_required(String firstname, String lastname, String mobile, String state, String district) {
        String exchange_reqd = "";
        for (int i = 0; i < exchanges_reqd.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                exchange_reqd = exchanges_reqd.get(i).toString();
            }
        }
        return exchange_reqd;
    }

    public String getX_finance_required(String firstname, String lastname, String mobile, String state, String district) {
        String finance_reqd = "";
        for (int i = 0; i < finances_reqd.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                finance_reqd = finances_reqd.get(i).toString();
            }
        }
        return finance_reqd;
    }

    public String getExisting_vehicle(String firstname, String lastname, String mobile, String state, String district) {
        String exist_vehicle = "";
        for (int i = 0; i < exist_vehicles.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                exist_vehicle = exist_vehicles.get(i).toString();
            }
        }
        return exist_vehicle;
    }

    public String getFollowup_comments(String firstname, String lastname, String mobile, String state, String district) {
        String comment = "";
        for (int i = 0; i < comments.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                comment = comments.get(i).toString();
            }
        }
        return comment;
    }

    public String getFollow_date(String firstname, String lastname, String mobile, String state, String district) {
        String followupdate = "";
        for (int i = 0; i < enquiryids.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                followupdate = followupdates.get(i).toString();
            }
        }
        return followupdate;
    }

    public String getPitch(String firstname, String lastname, String mobile, String state, String district) {
        String pitch = "";
        for (int i = 0; i < salespitch.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                pitch = salespitch.get(i).toString();
            }
        }
        return pitch;
    }

    public String getPrior(String firstname, String lastname, String mobile, String state, String district) {
        String priorty = "";
        for (int i = 0; i < priorities.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                priorty = priorities.get(i).toString();
            }
        }
        return priorty;
    }

    public String getOccup(String firstname, String lastname, String mobile, String state, String district) {
        String occupation = "";
        for (int i = 0; i < occupations.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                occupation = occupations.get(i).toString();
            }
        }
        return occupation;
    }

    public String getUse(String firstname, String lastname, String mobile, String state, String district) {
        String usage = "";
        for (int i = 0; i < usages.size(); i++) {
            if (fst_names.get(i).equals(firstname) && last_names.get(i).equals(lastname) && cell_ph_nums.get(i).equals(mobile) && states.get(i).equals(state)
                    && districts.get(i).equals(district)) {
                usage = usages.get(i).toString();
            }
        }
        return usage;
    }

    public EnquiryContact(String fst_name,
                          String last_name,
                          String cell_ph_num,
                          String age,
                          String gender,
                          String email_addr,
                          String state,
                          String district,
                          String tehsil,
                          String city,
                          String x_con_seq_no,
                          String x_model_interested,
                          String expctd_dt_purchase,
                          String x_exchange_required,
                          String x_finance_required,
                          String existing_vehicle,
                          String followup_comments,
                          String enquiry_id,
                          String x_test_ride_req,
                          String enquiry_entry_date,
                          String addr,
                          String addr_line_2,
                          String make_cd,
                          String model_cd,
                          String dealer_bu_id,
                          String follow_date) {

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
        this.x_con_seq_no = x_con_seq_no;
        this.x_model_interested = x_model_interested;
        this.expctd_dt_purchase = expctd_dt_purchase;
        this.x_exchange_required = x_exchange_required;
        this.x_finance_required = x_finance_required;
        this.existing_vehicle = existing_vehicle;
        this.followup_comments = followup_comments;
        this.enquiry_id = enquiry_id;
        this.x_test_ride_req = x_test_ride_req;
        this.enquiry_entry_date = enquiry_entry_date;
        this.addr = addr;
        this.addr_line_2 = addr_line_2;
        this.make_cd = make_cd;
        this.model_cd = model_cd;
        this.dealer_bu_id = dealer_bu_id;
        this.follow_date = follow_date;

        this.fst_names.add(fst_name);
        this.last_names.add(last_name);
        this.cell_ph_nums.add(cell_ph_num);
        this.genders.add(gender);
        this.email_addrs.add(email_addr);
        this.states.add(state);
        this.districts.add(district);
        this.enquiryids.add(enquiry_id);
        this.models.add(x_model_interested);
        this.enquirydates.add(enquiry_entry_date);
        this.purchasedates.add(expctd_dt_purchase);
        this.followupdates.add(follow_date);

        this.exchanges_reqd.add(x_exchange_required);
        this.finances_reqd.add(x_finance_required);
        this.comments.add(followup_comments);
        this.exist_vehicles.add(existing_vehicle);

    }

    public EnquiryContact() {
    }

    public EnquiryContact(String fst_name,
                          String last_name,
                          String cell_ph_num,
                          String age,
                          String gender,
                          String email_addr,
                          String state,
                          String district,
                          String tehsil,
                          String city,
                          String x_con_seq_no,
                          String x_model_interested,
                          String expctd_dt_purchase,
                          String x_exchange_required,
                          String x_finance_required,
                          String existing_vehicle,
                          String followup_comments,
                          String enquiry_id,
                          String x_test_ride_req,
                          String enquiry_entry_date,
                          String addr,
                          String addr_line_2,
                          String make_cd,
                          String model_cd,
                          String dealer_bu_id,
                          String follow_date,
                          String sales_pitch_no,
                          String priority,
                          String occupation,
                          String usage) {

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
        this.x_con_seq_no = x_con_seq_no;
        this.x_model_interested = x_model_interested;
        this.expctd_dt_purchase = expctd_dt_purchase;
        this.x_exchange_required = x_exchange_required;
        this.x_finance_required = x_finance_required;
        this.existing_vehicle = existing_vehicle;
        this.followup_comments = followup_comments;
        this.enquiry_id = enquiry_id;
        this.x_test_ride_req = x_test_ride_req;
        this.enquiry_entry_date = enquiry_entry_date;
        this.addr = addr;
        this.addr_line_2 = addr_line_2;
        this.make_cd = make_cd;
        this.model_cd = model_cd;
        this.dealer_bu_id = dealer_bu_id;
        this.follow_date = follow_date;
        this.sales_pitch_no = sales_pitch_no;
        this.priority = priority;
        this.occupation = occupation;
        this.usage = usage;

        this.fst_names.add(fst_name);
        this.last_names.add(last_name);
        this.cell_ph_nums.add(cell_ph_num);
        this.genders.add(gender);
        this.email_addrs.add(email_addr);
        this.states.add(state);
        this.districts.add(district);
        this.enquiryids.add(enquiry_id);
        this.models.add(x_model_interested);
        this.enquirydates.add(enquiry_entry_date);
        this.purchasedates.add(expctd_dt_purchase);
        this.followupdates.add(follow_date);
        this.salespitch.add(sales_pitch_no);
        this.priorities.add(priority);
        this.occupations.add(occupation);
        this.usages.add(usage);

        this.exchanges_reqd.add(x_exchange_required);
        this.finances_reqd.add(x_finance_required);
        this.comments.add(followup_comments);
        this.exist_vehicles.add(existing_vehicle);

    }

    public EnquiryContact(int clear) {
        this.fst_names.clear();
        this.last_names.clear();
        this.cell_ph_nums.clear();
        this.genders.clear();
        this.email_addrs.clear();
        this.states.clear();
        this.districts.clear();
        this.enquiryids.clear();
        this.models.clear();
        this.enquirydates.clear();;
        this.purchasedates.clear();;
        this.followupdates.clear();;
        this.salespitch.clear();;
        this.priorities.clear();;
        this.occupations.clear();;
        this.usages.clear();;

        this.exchanges_reqd.clear();;
        this.finances_reqd.clear();;
        this.comments.clear();;
        this.exist_vehicles.clear();;
    }
}


