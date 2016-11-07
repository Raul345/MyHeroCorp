package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Data Model Class.
 * Created by JitainSharma on 12/06/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProductModel extends BaseEntity {

    @JsonProperty("productId")
    private int productID;

    @JsonProperty("categoryId")
    private int categoryID;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productTag")
    private String productTag;

    @JsonProperty("productDetails")
    private String productDetails;

    @JsonProperty("productImage")
    private String productIcon;

    @JsonProperty("productType")
    private int productType;

    @JsonProperty("productOrder")
    private int productOrder;

    @JsonProperty("productStatus")
    private int productStatus;

    @JsonProperty("hpCreatedBy")
    private int hpCreatedBy;

    @JsonProperty("hpCreatedDate")
    private long hpCreatedDate;

    @JsonProperty("hpUpdatedDate")
    private long hpUpdatedDate;

    public ProductModel(){}

    public ProductModel(int productID, int categoryID, String productName, String productTag,
                        String productDetails, String productIcon, int productType){

        this.productID = productID;
        this.categoryID = categoryID;

        this.productName = productName;
        this.productTag = productTag;
        this.productDetails = productDetails;
        this.productIcon = productIcon;
        this.productType = productType;

    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(int productOrder) {
        this.productOrder = productOrder;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getHpCreatedBy() {
        return hpCreatedBy;
    }

    public void setHpCreatedBy(int hpCreatedBy) {
        this.hpCreatedBy = hpCreatedBy;
    }

    public long getHpCreatedDate() {
        return hpCreatedDate;
    }

    public void setHpCreatedDate(long hpCreatedDate) {
        this.hpCreatedDate = hpCreatedDate;
    }

    public long getHpUpdatedDate() {
        return hpUpdatedDate;
    }

    public void setHpUpdatedDate(long hpUpdatedDate) {
        this.hpUpdatedDate = hpUpdatedDate;
    }
}