package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductEngineModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductEngineTable;

import java.util.ArrayList;

/**
 * ProductEngineRepo
 * Created by JitainSharma on 12/06/16.
 */
public class ProductEngineRepo extends BaseRepository<ProductEngineModel> {

    public ProductEngineRepo(Context context) {
        super(context, ProductEngineTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductEngineModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductEngineTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductEngineTable.Cols.ENGINE_TYPE, entity.getEngineType());

        values.put(ProductEngineTable.Cols.DISPLACEMENT, entity.getDisplacement());
        values.put(ProductEngineTable.Cols.MAX_POWER, entity.getMaxPower());

        values.put(ProductEngineTable.Cols.MAX_TORQUE, entity.getMaxTorque());
        values.put(ProductEngineTable.Cols.MAX_SPEED, entity.getMaxSpeed());

        values.put(ProductEngineTable.Cols.ER_ACCELERATION, entity.getErAcceleration());
        values.put(ProductEngineTable.Cols.ER_AIR_FILTRATION, entity.getErAirFiltration());

        values.put(ProductEngineTable.Cols.ER_BORE_STROKE, entity.getErBoreStroke());
        values.put(ProductEngineTable.Cols.ER_CARBURETOR, entity.getErCarburetor());

        values.put(ProductEngineTable.Cols.ER_COMPRESSION_RATIO, entity.getErCompressionRatio());
        values.put(ProductEngineTable.Cols.ER_CYLINDER_ARRANGEMENT, entity.getErCylinderArrangement());

        values.put(ProductEngineTable.Cols.ER_FUEL_METERING, entity.getErFuelMetering());
        values.put(ProductEngineTable.Cols.ER_FUEL_SYSTEM, entity.getErFuelSystem());

        values.put(ProductEngineTable.Cols.ER_IGNITION, entity.getErIgnition());
        values.put(ProductEngineTable.Cols.ER_OIL_CAPACITY, entity.getErOilCapacity());

        values.put(ProductEngineTable.Cols.ER_OIL_GRADE, entity.getErOilGrade());
        values.put(ProductEngineTable.Cols.ER_STARTER, entity.getErStarter());

        return values;

    }

    @Override
    public ArrayList<ProductEngineModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductEngineModel> productEngineModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductEngineTable.Cols._ID) != -1) {

                        ProductEngineModel productEngineModel = new ProductEngineModel(
                                mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols._ID)),
                                mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.PRODUCT_ID)),
                                mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ENGINE_TYPE))
                        );

                        productEngineModel.setDisplacement(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.DISPLACEMENT)));

                        productEngineModel.setMaxPower(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.MAX_POWER)));
                        productEngineModel.setMaxSpeed(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.MAX_SPEED)));
                        productEngineModel.setMaxTorque(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.MAX_TORQUE)));

                        productEngineModel.setErAcceleration(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_ACCELERATION)));
                        productEngineModel.setErAirFiltration(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_AIR_FILTRATION)));
                        productEngineModel.setErBoreStroke(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_BORE_STROKE)));
                        productEngineModel.setErCarburetor(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_CARBURETOR)));

                        productEngineModel.setErCompressionRatio(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_COMPRESSION_RATIO)));
                        productEngineModel.setErCylinderArrangement(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_CYLINDER_ARRANGEMENT)));
                        productEngineModel.setErFuelMetering(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_FUEL_METERING)));
                        productEngineModel.setErFuelSystem(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_FUEL_SYSTEM)));

                        productEngineModel.setErIgnition(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_IGNITION)));
                        productEngineModel.setErOilCapacity(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_OIL_CAPACITY)));
                        productEngineModel.setErOilGrade(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_OIL_GRADE)));
                        productEngineModel.setErStarter(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductEngineTable.Cols.ER_STARTER)));

                        productEngineModels.add(productEngineModel);
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productEngineModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductEngineModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all product keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductEngineTable.Cols.PRODUCT_ID});

        for (ProductEngineModel engineModel : entityList) {

            if(objects.contains(engineModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductEngineTable.Cols.PRODUCT_ID+ "=?", new String[]{String.valueOf(engineModel.getProductID())})
                                .withValue(ProductEngineTable.Cols.ENGINE_TYPE, engineModel.getEngineType())
                                .withValue(ProductEngineTable.Cols.DISPLACEMENT, engineModel.getDisplacement())
                                .withValue(ProductEngineTable.Cols.MAX_POWER, engineModel.getMaxPower())
                                .withValue(ProductEngineTable.Cols.MAX_SPEED, engineModel.getMaxSpeed())
                                .withValue(ProductEngineTable.Cols.MAX_TORQUE, engineModel.getMaxTorque())

                                .withValue(ProductEngineTable.Cols.ER_STARTER, engineModel.getErStarter())
                                .withValue(ProductEngineTable.Cols.ER_OIL_GRADE, engineModel.getErOilGrade())
                                .withValue(ProductEngineTable.Cols.ER_OIL_CAPACITY, engineModel.getErOilCapacity())
                                .withValue(ProductEngineTable.Cols.ER_IGNITION, engineModel.getErIgnition())
                                .withValue(ProductEngineTable.Cols.ER_ACCELERATION, engineModel.getErAcceleration())
                                .withValue(ProductEngineTable.Cols.ER_AIR_FILTRATION, engineModel.getErAirFiltration())

                                .withValue(ProductEngineTable.Cols.ER_BORE_STROKE, engineModel.getErBoreStroke())
                                .withValue(ProductEngineTable.Cols.ER_CARBURETOR, engineModel.getErCarburetor())
                                .withValue(ProductEngineTable.Cols.ER_COMPRESSION_RATIO, engineModel.getErCompressionRatio())
                                .withValue(ProductEngineTable.Cols.ER_CYLINDER_ARRANGEMENT, engineModel.getErCylinderArrangement())
                                .withValue(ProductEngineTable.Cols.ER_FUEL_METERING, engineModel.getErFuelMetering())
                                .withValue(ProductEngineTable.Cols.ER_FUEL_SYSTEM, engineModel.getErFuelMetering())

                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductEngineTable.Cols.PRODUCT_ID, engineModel.getProductID())
                            .withValue(ProductEngineTable.Cols.ENGINE_TYPE, engineModel.getEngineType())
                            .withValue(ProductEngineTable.Cols.DISPLACEMENT, engineModel.getDisplacement())
                            .withValue(ProductEngineTable.Cols.MAX_POWER, engineModel.getMaxPower())
                            .withValue(ProductEngineTable.Cols.MAX_SPEED, engineModel.getMaxSpeed())
                            .withValue(ProductEngineTable.Cols.MAX_TORQUE, engineModel.getMaxTorque())

                            .withValue(ProductEngineTable.Cols.ER_STARTER, engineModel.getErStarter())
                            .withValue(ProductEngineTable.Cols.ER_OIL_GRADE, engineModel.getErOilGrade())
                            .withValue(ProductEngineTable.Cols.ER_OIL_CAPACITY, engineModel.getErOilCapacity())
                            .withValue(ProductEngineTable.Cols.ER_IGNITION, engineModel.getErIgnition())
                            .withValue(ProductEngineTable.Cols.ER_ACCELERATION, engineModel.getErAcceleration())
                            .withValue(ProductEngineTable.Cols.ER_AIR_FILTRATION, engineModel.getErAirFiltration())

                            .withValue(ProductEngineTable.Cols.ER_BORE_STROKE, engineModel.getErBoreStroke())
                            .withValue(ProductEngineTable.Cols.ER_CARBURETOR, engineModel.getErCarburetor())
                            .withValue(ProductEngineTable.Cols.ER_COMPRESSION_RATIO, engineModel.getErCompressionRatio())
                            .withValue(ProductEngineTable.Cols.ER_CYLINDER_ARRANGEMENT, engineModel.getErCylinderArrangement())
                            .withValue(ProductEngineTable.Cols.ER_FUEL_METERING, engineModel.getErFuelMetering())
                            .withValue(ProductEngineTable.Cols.ER_FUEL_SYSTEM, engineModel.getErFuelMetering())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}
