package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ProductEngineModel
 * Created by JitainSharma on 12/06/16.
 */
public class ProductEngineModel extends BaseEntity {

    private int _ID;
    private int productID;

    @JsonProperty("type")
    private String engineType;

    @JsonProperty("displacement")
    private String displacement;

    @JsonProperty("max. Power")
    private String maxPower;

    @JsonProperty("max. Torque")
    private String maxTorque;

    @JsonProperty("max. Speed")
    private String maxSpeed;

    @JsonProperty("oil Capacity")
    private String erOilCapacity;

    @JsonProperty("oil Grade")
    private String erOilGrade;

    @JsonProperty("air Filtration")
    private String erAirFiltration;

    @JsonProperty("acceleration")
    private String erAcceleration;

    @JsonProperty("bore x Stroke")
    private String erBoreStroke;

    @JsonProperty("carburettor")
    private String erCarburetor;

    @JsonProperty("compression Ratio")
    private String erCompressionRatio;

    @JsonProperty("starting")
    private String erStarter;

    @JsonProperty("ignition")
    private String erIgnition;

    @JsonProperty("fuel System")
    private String erFuelSystem;

    @JsonProperty("fuel Metering")
    private String erFuelMetering;

    @JsonProperty("cylinder Arrangement")
    private String erCylinderArrangement;

    public ProductEngineModel() {
    }

    public ProductEngineModel(int _ID, int productID, String enginetype) {

        this._ID = _ID;
        this.productID = productID;
        this.engineType = enginetype;
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

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }

    public String getMaxTorque() {
        return maxTorque;
    }

    public void setMaxTorque(String maxTorque) {
        this.maxTorque = maxTorque;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getErOilCapacity() {
        return erOilCapacity;
    }

    public void setErOilCapacity(String erOilCapacity) {
        this.erOilCapacity = erOilCapacity;
    }

    public String getErOilGrade() {
        return erOilGrade;
    }

    public void setErOilGrade(String erOilGrade) {
        this.erOilGrade = erOilGrade;
    }

    public String getErAirFiltration() {
        return erAirFiltration;
    }

    public void setErAirFiltration(String erAirFiltration) {
        this.erAirFiltration = erAirFiltration;
    }

    public String getErAcceleration() {
        return erAcceleration;
    }

    public void setErAcceleration(String erAcceleration) {
        this.erAcceleration = erAcceleration;
    }

    public String getErBoreStroke() {
        return erBoreStroke;
    }

    public void setErBoreStroke(String erBoreStroke) {
        this.erBoreStroke = erBoreStroke;
    }

    public String getErCarburetor() {
        return erCarburetor;
    }

    public void setErCarburetor(String erCarburetor) {
        this.erCarburetor = erCarburetor;
    }

    public String getErCompressionRatio() {
        return erCompressionRatio;
    }

    public void setErCompressionRatio(String erCompressionRatio) {
        this.erCompressionRatio = erCompressionRatio;
    }

    public String getErStarter() {
        return erStarter;
    }

    public void setErStarter(String erStarter) {
        this.erStarter = erStarter;
    }

    public String getErIgnition() {
        return erIgnition;
    }

    public void setErIgnition(String erIgnition) {
        this.erIgnition = erIgnition;
    }

    public String getErFuelSystem() {
        return erFuelSystem;
    }

    public void setErFuelSystem(String erFuelSystem) {
        this.erFuelSystem = erFuelSystem;
    }

    public String getErFuelMetering() {
        return erFuelMetering;
    }

    public void setErFuelMetering(String erFuelMetering) {
        this.erFuelMetering = erFuelMetering;
    }

    public String getErCylinderArrangement() {
        return erCylinderArrangement;
    }

    public void setErCylinderArrangement(String erCylinderArrangement) {
        this.erCylinderArrangement = erCylinderArrangement;
    }
}
