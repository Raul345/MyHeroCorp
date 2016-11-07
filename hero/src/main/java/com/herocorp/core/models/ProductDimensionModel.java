package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductDimensionModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("length")
    private String length;

    @JsonProperty("width")
    private String width;

    @JsonProperty("height")
    private String height;

    @JsonProperty("saddle Height")
    private String saddleHeight;

    @JsonProperty("wheel Base")
    private String wheelBase;

    @JsonProperty("ground Clearance")
    private String groundClearance;

    @JsonProperty("fuel Tank Capacity")
    private String fuelTankCapacity;

    @JsonProperty("reserve")
    private String reserve;

    @JsonProperty("kerb Weight")
    private String kerbWeight;

    @JsonProperty("max Payload")
    private String maxPayload;

    public ProductDimensionModel(){}

    public ProductDimensionModel(int _ID, int productID, String length, String width,
                                 String height, String saddleHeight, String wheelBase, String groundClearance,
                                 String fuelTankCapacity, String reserve, String kerbWeight, String maxPayload){

        this._ID = _ID;
        this.productID = productID;
        this.length = length;
        this.width = width;
        this.height = height;
        this.saddleHeight = saddleHeight;
        this.wheelBase = wheelBase;
        this.groundClearance = groundClearance;
        this.fuelTankCapacity = fuelTankCapacity;
        this.reserve = reserve;
        this.kerbWeight = kerbWeight;
        this.maxPayload = maxPayload;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSaddleHeight() {
        return saddleHeight;
    }

    public void setSaddleHeight(String saddleHeight) {
        this.saddleHeight = saddleHeight;
    }

    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    public String getGroundClearance() {
        return groundClearance;
    }

    public void setGroundClearance(String groundClearance) {
        this.groundClearance = groundClearance;
    }

    public String getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(String fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public String getKerbWeight() {
        return kerbWeight;
    }

    public void setKerbWeight(String kerbWeight) {
        this.kerbWeight = kerbWeight;
    }

    public String getMaxPayload() {
        return maxPayload;
    }

    public void setMaxPayload(String maxPayload) {
        this.maxPayload = maxPayload;
    }
}
