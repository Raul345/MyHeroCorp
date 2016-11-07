package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductTyreModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("rim Front")
    private String rimFront;

    @JsonProperty("rim Rear")
    private String rimRear;

    @JsonProperty("tyre Front")
    private String tyreFront;

    @JsonProperty("tyre Rear")
    private String tyreRear;

    public ProductTyreModel(){}
    public ProductTyreModel(int _ID, int productID, String rimFront,
                            String rimRear, String tyreFront, String tyreRear){

        this._ID = _ID;
        this.productID = productID;
        this.rimFront = rimFront;
        this.rimRear = rimRear;
        this.tyreFront = tyreFront;
        this.tyreRear = tyreRear;
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

    public String getRimFront() {
        return rimFront;
    }

    public void setRimFront(String rimFront) {
        this.rimFront = rimFront;
    }

    public String getRimRear() {
        return rimRear;
    }

    public void setRimRear(String rimRear) {
        this.rimRear = rimRear;
    }

    public String getTyreFront() {
        return tyreFront;
    }

    public void setTyreFront(String tyreFront) {
        this.tyreFront = tyreFront;
    }

    public String getTyreRear() {
        return tyreRear;
    }

    public void setTyreRear(String tyreRear) {
        this.tyreRear = tyreRear;
    }
}
