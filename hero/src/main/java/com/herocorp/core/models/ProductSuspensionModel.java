package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductSuspensionModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("front")
    private String spFront;

    @JsonProperty("rear")
    private String spRear;

    public ProductSuspensionModel(){}
    public ProductSuspensionModel(int _ID, int productID, String spFront, String spRear){

        this._ID = _ID;
        this.productID = productID;
        this.spFront = spFront;
        this.spRear = spRear;
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

    public String getSpFront() {
        return spFront;
    }

    public void setSpFront(String spFront) {
        this.spFront = spFront;
    }

    public String getSpRear() {
        return spRear;
    }

    public void setSpRear(String spRear) {
        this.spRear = spRear;
    }
}
