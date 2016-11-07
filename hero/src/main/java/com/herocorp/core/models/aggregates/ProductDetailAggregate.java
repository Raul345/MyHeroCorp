package com.herocorp.core.models.aggregates;

import com.herocorp.core.models.BaseEntity;
import com.herocorp.core.models.ProductBreakModel;
import com.herocorp.core.models.ProductColorModel;
import com.herocorp.core.models.ProductDimensionModel;
import com.herocorp.core.models.ProductElectricalModel;
import com.herocorp.core.models.ProductEngineModel;
import com.herocorp.core.models.ProductFeatureModel;
import com.herocorp.core.models.ProductGalleryModel;
import com.herocorp.core.models.ProductSuspensionModel;
import com.herocorp.core.models.ProductTransmissionModel;
import com.herocorp.core.models.ProductTyreModel;
import com.herocorp.infra.db.tables.schemas.products.ProductBreakTable;

import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductDetailAggregate extends BaseEntity {

    private int productID;

    private ProductTyreModel tyreModel;
    private ProductTransmissionModel transmissionModel;
    private ProductBreakModel breakModel;
    private ArrayList<ProductColorModel> colorModelList;
    private ProductDimensionModel dimensionModel;
    private ProductElectricalModel electricalModel;
    private ProductEngineModel engineModel;
    private ProductSuspensionModel suspensionModel;
    private ArrayList<ProductFeatureModel> featureModelList;
    private ArrayList<ProductGalleryModel> galleryModelList;

    public ProductDetailAggregate(){};

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public ProductTyreModel getTyreModel() {
        return tyreModel;
    }

    public void setTyreModel(ProductTyreModel tyreModel) {
        this.tyreModel = tyreModel;
    }

    public ProductTransmissionModel getTransmissionModel() {
        return transmissionModel;
    }

    public void setTransmissionModel(ProductTransmissionModel transmissionModel) {
        this.transmissionModel = transmissionModel;
    }

    public ProductBreakModel getBreakModel() {
        return breakModel;
    }

    public void setBreakModel(ProductBreakModel breakModel) {
        this.breakModel = breakModel;
    }

    public ArrayList<ProductColorModel> getColorModel() {
        return colorModelList;
    }

    public void setColorModel(ArrayList<ProductColorModel> colorModelList) {
        this.colorModelList = colorModelList;
    }

    public ProductDimensionModel getDimensionModel() {
        return dimensionModel;
    }

    public void setDimensionModel(ProductDimensionModel dimensionModel) {
        this.dimensionModel = dimensionModel;
    }

    public ProductElectricalModel getElectricalModel() {
        return electricalModel;
    }

    public void setElectricalModel(ProductElectricalModel electricalModel) {
        this.electricalModel = electricalModel;
    }

    public ProductEngineModel getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(ProductEngineModel engineModel) {
        this.engineModel = engineModel;
    }

    public ProductSuspensionModel getSuspensionModel() {
        return suspensionModel;
    }

    public void setSuspensionModel(ProductSuspensionModel suspensionModel) {
        this.suspensionModel = suspensionModel;
    }

    public ArrayList<ProductFeatureModel> getFeatureModel() {
        return featureModelList;
    }

    public void setFeatureModel(ArrayList<ProductFeatureModel> featureModelList) {
        this.featureModelList = featureModelList;
    }

    public ArrayList<ProductGalleryModel> getGalleryModelList() {
        return galleryModelList;
    }

    public void setGalleryModelList(ArrayList<ProductGalleryModel> galleryModelList) {
        this.galleryModelList = galleryModelList;
    }
}
