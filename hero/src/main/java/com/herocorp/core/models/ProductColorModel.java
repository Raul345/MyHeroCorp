package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Color's Data Model
 * Created by JitainSharma on 12/06/16.
 */
public class ProductColorModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("image_name")
    private String imageName;

    @JsonProperty("img_color_icon")
    private String imgColorIcon;

    @JsonProperty("img_color_code")
    private String imgColorCode;

    public ProductColorModel(){}

    public ProductColorModel(int _ID, int productID, String imageName,
                             String imgColorIcon, String imgColorCode){

        this._ID = _ID;
        this.productID = productID;
        this.imageName = imageName;
        this.imgColorCode = imgColorCode;
        this.imgColorIcon = imgColorIcon;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImgColorIcon() {
        return imgColorIcon;
    }

    public void setImgColorIcon(String imgColorIcon) {
        this.imgColorIcon = imgColorIcon;
    }

    public String getImgColorCode() {
        return imgColorCode;
    }

    public void setImgColorCode(String imgColorCode) {
        this.imgColorCode = imgColorCode;
    }
}
