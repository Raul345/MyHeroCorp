package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Gallery Data Model Class.
 * Created by JitainSharma on 25/06/16.
 */
public class ProductGalleryModel extends BaseEntity {

    private int _ID;

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("gallery_img")
    private String galleryImg;

    @JsonProperty("gallery_img_text")
    private String galleryImgText;

    public ProductGalleryModel(){}

    public ProductGalleryModel(int _ID, int productID, String galleryImg,
                               String galleryImgText){

        this._ID = _ID;
        this.productID = productID;
        this.galleryImg = galleryImg;
        this.galleryImgText = galleryImgText;
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

    public String getGalleryImg() {
        return galleryImg;
    }

    public void setGalleryImg(String galleryImg) {
        this.galleryImg = galleryImg;
    }

    public String getGalleryImgText() {
        return galleryImgText;
    }

    public void setGalleryImgText(String galleryImgText) {
        this.galleryImgText = galleryImgText;
    }
}
