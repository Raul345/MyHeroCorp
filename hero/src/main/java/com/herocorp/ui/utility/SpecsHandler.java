package com.herocorp.ui.utility;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herocorp.core.models.ProductBreakModel;
import com.herocorp.core.models.ProductDimensionModel;
import com.herocorp.core.models.ProductElectricalModel;
import com.herocorp.core.models.ProductEngineModel;
import com.herocorp.core.models.ProductSuspensionModel;
import com.herocorp.core.models.ProductTransmissionModel;
import com.herocorp.core.models.ProductTyreModel;
import com.herocorp.infra.db.repositories.product.ProductBreakRepo;
import com.herocorp.infra.db.repositories.product.ProductDimensionRepo;
import com.herocorp.infra.db.repositories.product.ProductElectricalRepo;
import com.herocorp.infra.db.repositories.product.ProductEngineRepo;
import com.herocorp.infra.db.repositories.product.ProductSuspensionRepo;
import com.herocorp.infra.db.repositories.product.ProductTransmissionRepo;
import com.herocorp.infra.db.repositories.product.ProductTyreRepo;
import com.herocorp.infra.db.tables.schemas.products.ProductEngineTable;
import com.herocorp.infra.db.tables.schemas.products.ProductFeatureTable;
import com.herocorp.infra.db.tables.schemas.products.ProductTransmissionTable;

import java.util.ArrayList;

/**
 * Created by EvilGod on 6/19/2016.
 */
public class SpecsHandler {

    private Context context;
    private int productId;
    private int otherProductId;

    private ProductEngineRepo engineRepo;
    private ProductTransmissionRepo transmissionRepo;
    private ProductSuspensionRepo suspensionRepo;
    private ProductBreakRepo breakRepo;
    private ProductTyreRepo tyreRepo;
    private ProductElectricalRepo electricalRepo;
    private ProductDimensionRepo dimensionRepo;

    public SpecsHandler(Context context, int productId, int otherProductId) {
        this.context = context;
        this.productId = productId;
        this.otherProductId = otherProductId;
    }

    private void addRowToLayout(LinearLayout containerLayout, String title, String detailMain, String detailOther) {

        if(detailMain != null && detailMain.trim().length() > 0 || detailOther != null && detailOther.trim().length() > 0) {

            if(detailMain != null && detailMain.trim().length()==0)
                detailMain = "N/A";

            CustomViewParams customViewParams = new CustomViewParams(context);
            CustomTypeFace typeFace = new CustomTypeFace(context);

            LinearLayout detailLayout = new LinearLayout(context);
            detailLayout.setOrientation(LinearLayout.HORIZONTAL);
            detailLayout.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView titleText = new TextView(context);
            titleText.setTextColor(Color.GRAY);
            titleText.setText(title);

            TextView detailText = new TextView(context);
            detailText.setText(detailMain);
            detailText.setTextColor(Color.WHITE);

            if (detailOther != null) {

                if(detailOther.trim().length() == 0)
                    return;
                
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                detailLayout.addView(detailText);
                detailLayout.addView(titleText);

                detailOther = detailOther.trim().length() > 0 ? detailOther : "N/A";

                TextView detailTextOther = new TextView(context);
                detailTextOther.setText(detailOther);
                detailTextOther.setTextColor(Color.WHITE);
                detailLayout.addView(detailTextOther);

                titleText.setLayoutParams(param);
                titleText.setGravity(Gravity.CENTER);
                detailText.setLayoutParams(param);
                detailTextOther.setLayoutParams(param);

                customViewParams.setTextViewCustomParams(detailTextOther, new int[]{15, 4, 15, 4}, new int[]{15, 5, 15, 5}, 35, typeFace.gillSans, 0);

            } else {
                detailLayout.addView(titleText);
                detailLayout.addView(detailText);
            }

            customViewParams.setTextViewCustomParams(titleText, new int[]{15, 4, 15, 4}, new int[]{0, 0, 0, 0}, 39, typeFace.gillSans, 1);
            customViewParams.setHeightAndWidth(titleText, 0, 350);

            customViewParams.setTextViewCustomParams(detailText, new int[]{15, 4, 15, 4}, new int[]{15, 5, 15, 5}, 35, typeFace.gillSans, 0);

            containerLayout.addView(detailLayout);
            customViewParams.setMarginAndPadding(detailLayout, new int[]{30, 15, 30, 15}, new int[]{0, 0, 0, 0}, detailLayout.getParent());
            //customViewParams.setHeightAndWidth(detailLayout, 70, 0);

            TextView separator = new TextView(context);
            separator.setBackgroundColor(Color.GRAY);
            separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            containerLayout.addView(separator);
            customViewParams.setHeightAndWidth(separator, 3, 0);
        }
    }

    public void handleEngineData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        engineRepo = new ProductEngineRepo(context);
        ProductEngineModel engineModel = null;
        ProductEngineModel engineModelOther = null;
        try {
            ArrayList<ProductEngineModel> engineList = engineRepo.getRecords(null, ProductEngineTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            engineModel = engineList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductEngineModel> engineListOther = engineRepo.getRecords(null, ProductEngineTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(otherProductId)}, null);
                engineModelOther = engineListOther.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (engineModel != null) {

            addRowToLayout(containerLayout, "Type", engineModel.getEngineType(), engineModelOther != null ? engineModelOther.getEngineType() : null);
            addRowToLayout(containerLayout, "Displacement", engineModel.getDisplacement(), engineModelOther != null ? engineModelOther.getDisplacement() : null);
            addRowToLayout(containerLayout, "Acceleration", engineModel.getErAcceleration(), engineModelOther != null ? engineModelOther.getErAcceleration() : null);
            addRowToLayout(containerLayout, "Max Power", engineModel.getMaxPower(), engineModelOther != null ? engineModelOther.getMaxPower() : null);
            addRowToLayout(containerLayout, "Max Torque", engineModel.getMaxTorque(), engineModelOther != null ? engineModelOther.getMaxTorque() : null);
            addRowToLayout(containerLayout, "Max Speed", engineModel.getMaxSpeed(), engineModelOther != null ? engineModelOther.getMaxSpeed() : null);
            addRowToLayout(containerLayout, "Starter", engineModel.getErStarter(), engineModelOther != null ? engineModelOther.getErStarter() : null);
            addRowToLayout(containerLayout, "Air Filtration", engineModel.getErAirFiltration(), engineModelOther != null ? engineModelOther.getErAirFiltration() : null);
            addRowToLayout(containerLayout, "Bore Stroke", engineModel.getErBoreStroke(), engineModelOther != null ? engineModelOther.getErBoreStroke() : null);
            addRowToLayout(containerLayout, "Carburetors", engineModel.getErCarburetor(), engineModelOther != null ? engineModelOther.getErCarburetor() : null);
            addRowToLayout(containerLayout, "Compression Ratio", engineModel.getErCompressionRatio(), engineModelOther != null ? engineModelOther.getErCompressionRatio() : null);
            addRowToLayout(containerLayout, "Cylinders Arrangement", engineModel.getErCylinderArrangement(), engineModelOther != null ? engineModelOther.getErCylinderArrangement() : null);
            addRowToLayout(containerLayout, "Fuel Metering", engineModel.getErFuelMetering(), engineModelOther != null ? engineModelOther.getErFuelMetering() : null);
            addRowToLayout(containerLayout, "Fuel System", engineModel.getErFuelSystem(), engineModelOther != null ? engineModelOther.getErFuelSystem() : null);
            addRowToLayout(containerLayout, "Oil Capacity", engineModel.getErOilCapacity(), engineModelOther != null ? engineModelOther.getErOilCapacity() : null);
            addRowToLayout(containerLayout, "Ignition", engineModel.getErIgnition(), engineModelOther != null ? engineModelOther.getErIgnition() : null);
            addRowToLayout(containerLayout, "Oil Grade", engineModel.getErOilGrade(), engineModelOther != null ? engineModelOther.getErOilGrade() : null);
        }
    }

    public void handleTransmissionData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        transmissionRepo = new ProductTransmissionRepo(context);
        ProductTransmissionModel transmissionModel = null;
        ProductTransmissionModel transmissionModelOther = null;
        try {
            ArrayList<ProductTransmissionModel> transmissionList = transmissionRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            transmissionModel = transmissionList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductTransmissionModel> transmissionListOther = transmissionRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(otherProductId)}, null);
                transmissionModelOther = transmissionListOther.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transmissionModel != null) {

            addRowToLayout(containerLayout, "Chassis Type", transmissionModel.getTcChassisType(), transmissionModelOther != null ? transmissionModelOther.getTcChassisType() : null);
            addRowToLayout(containerLayout, "Clutch", transmissionModel.getTcClutch(), transmissionModelOther != null ? transmissionModelOther.getTcClutch() : null);
            addRowToLayout(containerLayout, "Gear Box", transmissionModel.getTcGearBox(), transmissionModelOther != null ? transmissionModelOther.getTcGearBox() : null);

        }
    }

    public void handleSuspensionData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        suspensionRepo = new ProductSuspensionRepo(context);
        ProductSuspensionModel suspensionModel = null;
        ProductSuspensionModel suspensionModelOther = null;
        try {
            ArrayList<ProductSuspensionModel> suspensionList = suspensionRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            suspensionModel = suspensionList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductSuspensionModel> suspensionListOther = suspensionRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(otherProductId)}, null);
                suspensionModelOther = suspensionListOther.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (suspensionModel != null) {

            addRowToLayout(containerLayout, "Front Suspension", suspensionModel.getSpFront(), suspensionModelOther != null ? suspensionModelOther.getSpFront() : null);
            addRowToLayout(containerLayout, "Rear Suspension", suspensionModel.getSpRear(), suspensionModelOther != null ? suspensionModelOther.getSpRear() : null);

        }
    }

    public void handleBrakesData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        breakRepo = new ProductBreakRepo(context);
        ProductBreakModel brakesModel = null;
        ProductBreakModel brakesModelOther = null;
        try {
            ArrayList<ProductBreakModel> brakesList = breakRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            brakesModel = brakesList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductBreakModel> brakesListOther = breakRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(otherProductId)}, null);
                brakesModelOther = brakesListOther.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (brakesModel != null) {

            addRowToLayout(containerLayout, "Front Disk", brakesModel.getFrontDisc(), brakesModelOther != null ? brakesModelOther.getFrontDisc() : null);
            addRowToLayout(containerLayout, "Rear Disk", brakesModel.getRareDisk(), brakesModelOther != null ? brakesModelOther.getRareDisk() : null);
            addRowToLayout(containerLayout, "Front Drum", brakesModel.getFrontDrum(), brakesModelOther != null ? brakesModelOther.getFrontDrum() : null);
            addRowToLayout(containerLayout, "Rear Drum", brakesModel.getRareDrum(), brakesModelOther != null ? brakesModelOther.getRareDrum() : null);

        }
    }

    public void handleTyresData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        tyreRepo = new ProductTyreRepo(context);
        ProductTyreModel tyresModel = null;
        ProductTyreModel tyresModelOther = null;
        try {
            ArrayList<ProductTyreModel> tyresList = tyreRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            tyresModel = tyresList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductTyreModel> tyresListOther = tyreRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(otherProductId)}, null);
                tyresModelOther = tyresListOther.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tyresModel != null) {

            addRowToLayout(containerLayout, "Front Tyre", tyresModel.getTyreFront(), tyresModelOther != null ? tyresModelOther.getTyreFront() : null);
            addRowToLayout(containerLayout, "Rear Tyre", tyresModel.getTyreRear(), tyresModelOther != null ? tyresModelOther.getTyreRear() : null);
            addRowToLayout(containerLayout, "Front Rim", tyresModel.getRimFront(), tyresModelOther != null ? tyresModelOther.getRimFront() : null);
            addRowToLayout(containerLayout, "Rear Rim", tyresModel.getRimRear(), tyresModelOther != null ? tyresModelOther.getRimRear() : null);

        }
    }

    public void handleElectricalData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        electricalRepo = new ProductElectricalRepo(context);
        ProductElectricalModel electricalModel = null;
        ProductElectricalModel electricalModelOther = null;
        try {
            ArrayList<ProductElectricalModel> electricalList = electricalRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            electricalModel = electricalList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductElectricalModel> electricalListOther = electricalRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(otherProductId)}, null);
                electricalModelOther = electricalListOther.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (electricalModel != null) {

            addRowToLayout(containerLayout, "Head Lamp", electricalModel.getHeadLamp(), electricalModelOther != null ? electricalModelOther.getHeadLamp() : null);
            addRowToLayout(containerLayout, "Tail Lamp", electricalModel.getTailLamp(), electricalModelOther != null ? electricalModelOther.getTailLamp() : null);
            addRowToLayout(containerLayout, "Pilot Lamp", electricalModel.getPilotLamp(), electricalModelOther != null ? electricalModelOther.getPilotLamp() : null);
            addRowToLayout(containerLayout, "Turn Indicator", electricalModel.getTurnLamp(), electricalModelOther != null ? electricalModelOther.getTurnLamp() : null);
            addRowToLayout(containerLayout, "Position Lamp", electricalModel.getPositionLamp(), electricalModelOther != null ? electricalModelOther.getPositionLamp() : null);
            addRowToLayout(containerLayout, "Battery", electricalModel.getBattery(), electricalModelOther != null ? electricalModelOther.getBattery() : null);

        }
    }

    public void handleDimensionsData(LinearLayout containerLayout) {

        containerLayout.removeAllViews();
        dimensionRepo = new ProductDimensionRepo(context);
        ProductDimensionModel dimensionsModel = null;
        ProductDimensionModel dimensionsModelOther = null;
        try {
            ArrayList<ProductDimensionModel> dimensionsList = dimensionRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            dimensionsModel = dimensionsList.get(0);

            if (otherProductId != -1) {
                ArrayList<ProductDimensionModel> dimensionsListOther = dimensionRepo.getRecords(null, ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
                dimensionsModelOther = dimensionsListOther.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dimensionsModel != null) {

            addRowToLayout(containerLayout, "Fuel Tank Capacity", dimensionsModel.getFuelTankCapacity(), dimensionsModelOther != null ? dimensionsModelOther.getFuelTankCapacity() : null);
            addRowToLayout(containerLayout, "Reserve", dimensionsModel.getReserve(), dimensionsModelOther != null ? dimensionsModelOther.getReserve() : null);
            addRowToLayout(containerLayout, "Height", dimensionsModel.getHeight(), dimensionsModelOther != null ? dimensionsModelOther.getHeight() : null);
            addRowToLayout(containerLayout, "Length", dimensionsModel.getLength(), dimensionsModelOther != null ? dimensionsModelOther.getLength() : null);
            addRowToLayout(containerLayout, "Width", dimensionsModel.getWidth(), dimensionsModelOther != null ? dimensionsModelOther.getWidth() : null);
            addRowToLayout(containerLayout, "Ground Clearance", dimensionsModel.getGroundClearance(), dimensionsModelOther != null ? dimensionsModelOther.getGroundClearance() : null);
            addRowToLayout(containerLayout, "Wheel Base", dimensionsModel.getWheelBase(), dimensionsModelOther != null ? dimensionsModelOther.getWheelBase() : null);
            addRowToLayout(containerLayout, "Kerb Weight", dimensionsModel.getKerbWeight(), dimensionsModelOther != null ? dimensionsModelOther.getKerbWeight() : null);
            addRowToLayout(containerLayout, "Saddle Height", dimensionsModel.getSaddleHeight(), dimensionsModelOther != null ? dimensionsModelOther.getSaddleHeight() : null);

        }
    }
}
