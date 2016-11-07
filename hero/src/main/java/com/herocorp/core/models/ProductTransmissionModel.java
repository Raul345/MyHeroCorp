package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductTransmissionModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("clutch")
    private String tcClutch;

    @JsonProperty("gear Box")
    private String tcGearBox;

    @JsonProperty("chassis Type")
    private String tcChassisType;

    public ProductTransmissionModel(){}
    public ProductTransmissionModel(int _ID, int productID, String tcClutch,
                                    String tcGearBox, String tcChassisType){

        this._ID = _ID;
        this.productID = productID;
        this.tcClutch = tcClutch;
        this.tcGearBox = tcGearBox;
        this.tcChassisType = tcChassisType;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTcClutch() {
        return tcClutch;
    }

    public void setTcClutch(String tcClutch) {
        this.tcClutch = tcClutch;
    }

    public String getTcGearBox() {
        return tcGearBox;
    }

    public void setTcGearBox(String tcGearBox) {
        this.tcGearBox = tcGearBox;
    }

    public String getTcChassisType() {
        return tcChassisType;
    }

    public void setTcChassisType(String tcChassisType) {
        this.tcChassisType = tcChassisType;
    }
}
