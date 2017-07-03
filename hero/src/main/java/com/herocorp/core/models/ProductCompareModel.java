package com.herocorp.core.models;

/**
 * Created by rsawh on 23-Jun-17.
 */

public class ProductCompareModel extends BaseEntity {

    private int id;
    private int productId;
    private String imageName;
    private String date;

    public ProductCompareModel(int id, int productId, String imageName, String date) {
        this.id = id;
        this.productId = productId;
        this.imageName = imageName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageName() {
        if(imageName == null) {
            return "";
        }
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDate() {
        if(date == null) {
            return "";
        }
        return date;
    }

    public void setDate(String date) {
        if(date == null) {
            Long tsLong = System.currentTimeMillis() / 1000;
            this.date = tsLong.toString();
        } else  {
            date = date;
        }
    }
}
