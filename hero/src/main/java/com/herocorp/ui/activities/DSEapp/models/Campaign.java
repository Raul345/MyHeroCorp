package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 01-Oct-16.
 */
public class Campaign {
    String dealer_code;
    String dealer_name;
    String camp_id;
    String campaign_name;
    String start_date;
    String end_date;
    String category;
    String status;
    String sub_category;
    String camp_type;
    String camp_source;
    String model;

    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getCamp_id() {
        return camp_id;
    }

    public void setCamp_id(String camp_id) {
        this.camp_id = camp_id;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getCamp_type() {
        return camp_type;
    }

    public void setCamp_type(String camp_type) {
        this.camp_type = camp_type;
    }

    public String getCamp_source() {
        return camp_source;
    }

    public void setCamp_source(String camp_source) {
        this.camp_source = camp_source;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Campaign(String dealer_code,
                    String dealer_name,
                    String camp_id,
                    String campaign_name,
                    String start_date,
                    String end_date,
                    String category,
                    String status,
                    String sub_category,
                    String camp_type,
                    String camp_source,
                    String model) {

        this.dealer_code = dealer_code;
        this.dealer_name = dealer_name;
        this.camp_id = camp_id;
        this.campaign_name = campaign_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.category = category;
        this.status = status;
        this.sub_category = sub_category;
        this.camp_type = camp_type;
        this.camp_source = camp_source;
        this.model = model;

    }
}
