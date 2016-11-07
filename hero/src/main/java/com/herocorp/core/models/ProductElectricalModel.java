package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductElectricalModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("battery")
    private String battery;

    @JsonProperty("head Lamp")
    private String headLamp;

    @JsonProperty("tail Lamp")
    private String tailLamp;

    @JsonProperty("turn Lamp")
    private String turnLamp;

    @JsonProperty("position Lamp")
    private String positionLamp;

    @JsonProperty("pilot Lamp")
    private String pilotLamp;

    public ProductElectricalModel(){}
    public ProductElectricalModel(int _ID, int productID, String battery, String headLamp,
                                  String tailLamp, String turnLamp, String positionLamp,
                                  String pilotLamp){

        this._ID = _ID;
        this.productID = productID;
        this.battery = battery;
        this.headLamp = headLamp;
        this.tailLamp = tailLamp;
        this.turnLamp = turnLamp;
        this.positionLamp = positionLamp;
        this.pilotLamp = pilotLamp;
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

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getHeadLamp() {
        return headLamp;
    }

    public void setHeadLamp(String headLamp) {
        this.headLamp = headLamp;
    }

    public String getTailLamp() {
        return tailLamp;
    }

    public void setTailLamp(String tailLamp) {
        this.tailLamp = tailLamp;
    }

    public String getTurnLamp() {
        return turnLamp;
    }

    public void setTurnLamp(String turnLamp) {
        this.turnLamp = turnLamp;
    }

    public String getPositionLamp() {
        return positionLamp;
    }

    public void setPositionLamp(String positionLamp) {
        this.positionLamp = positionLamp;
    }

    public String getPilotLamp() {
        return pilotLamp;
    }

    public void setPilotLamp(String pilotLamp) {
        this.pilotLamp = pilotLamp;
    }
}
