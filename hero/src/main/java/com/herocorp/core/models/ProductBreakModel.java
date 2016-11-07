package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Break Data Model Class.
 * Created by JitainSharma on 17/06/16.
 */
public class ProductBreakModel extends BaseEntity{

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("front Disc")
    private String frontDisc;

    @JsonProperty("front Drum")
    private String frontDrum;

    @JsonProperty("rare Disk")
    private String rareDisk;

    @JsonProperty("rare Drum")
    private String rareDrum;

    public ProductBreakModel(){}

    public ProductBreakModel(int _ID, int productID, String frontDisc, String frontDrum,
                        String rareDisk, String rareDrum){

        this._ID = _ID;
        this.productID = productID;
        this.frontDisc = frontDisc;

        this.frontDrum = frontDrum;
        this.rareDisk = rareDisk;
        this.rareDrum = rareDrum;

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

    public String getFrontDisc() {
        return frontDisc;
    }

    public void setFrontDisc(String frontDisc) {
        this.frontDisc = frontDisc;
    }

    public String getFrontDrum() {
        return frontDrum;
    }

    public void setFrontDrum(String frontDrum) {
        this.frontDrum = frontDrum;
    }

    public String getRareDisk() {
        return rareDisk;
    }

    public void setRareDisk(String rareDisk) {
        this.rareDisk = rareDisk;
    }

    public String getRareDrum() {
        return rareDrum;
    }

    public void setRareDrum(String rareDrum) {
        this.rareDrum = rareDrum;
    }
}
