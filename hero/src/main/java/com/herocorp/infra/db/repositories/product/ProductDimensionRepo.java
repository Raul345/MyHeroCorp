package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductDimensionModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductDimensionTable;

import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductDimensionRepo extends BaseRepository<ProductDimensionModel> {

    public ProductDimensionRepo(Context context) {
        super(context, ProductDimensionTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductDimensionModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductDimensionTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductDimensionTable.Cols.LENGTH, entity.getLength());
        values.put(ProductDimensionTable.Cols.WIDTH, entity.getWidth());
        values.put(ProductDimensionTable.Cols.HEIGHT, entity.getHeight());
        values.put(ProductDimensionTable.Cols.SADDLE_HEIGHT, entity.getSaddleHeight());

        values.put(ProductDimensionTable.Cols.WHEEL_BASE, entity.getWheelBase());
        values.put(ProductDimensionTable.Cols.KERB_WEIGHT, entity.getKerbWeight());
        values.put(ProductDimensionTable.Cols.FUEL_TANK_CAPACITY, entity.getFuelTankCapacity());
        values.put(ProductDimensionTable.Cols.GROUND_CLEARANCE, entity.getGroundClearance());
        values.put(ProductDimensionTable.Cols.RESERVE, entity.getReserve());

        values.put(ProductDimensionTable.Cols.MAX_PAYLOAD, entity.getProductID());

        return values;
    }

    @Override
    public ArrayList<ProductDimensionModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductDimensionModel> dimensionModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductDimensionTable.Cols._ID) != -1) {

                        dimensionModels.add(
                                new ProductDimensionModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.LENGTH)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.WIDTH)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.HEIGHT)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.SADDLE_HEIGHT)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.WHEEL_BASE)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.GROUND_CLEARANCE)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.FUEL_TANK_CAPACITY)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.RESERVE)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.KERB_WEIGHT)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductDimensionTable.Cols.MAX_PAYLOAD))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return dimensionModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductDimensionModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductDimensionTable.Cols.PRODUCT_ID});

        for (ProductDimensionModel dimensionModel : entityList) {

            if(objects.contains(dimensionModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductDimensionTable.Cols.PRODUCT_ID+ "=?", new String[]{String.valueOf(dimensionModel.getProductID())})
                                .withValue(ProductDimensionTable.Cols.LENGTH, dimensionModel.getLength())
                                .withValue(ProductDimensionTable.Cols.WIDTH, dimensionModel.getWidth())
                                .withValue(ProductDimensionTable.Cols.HEIGHT, dimensionModel.getHeight())
                                .withValue(ProductDimensionTable.Cols.SADDLE_HEIGHT, dimensionModel.getSaddleHeight())
                                .withValue(ProductDimensionTable.Cols.WHEEL_BASE, dimensionModel.getWheelBase())
                                .withValue(ProductDimensionTable.Cols.GROUND_CLEARANCE, dimensionModel.getGroundClearance())
                                .withValue(ProductDimensionTable.Cols.FUEL_TANK_CAPACITY, dimensionModel.getFuelTankCapacity())
                                .withValue(ProductDimensionTable.Cols.MAX_PAYLOAD, dimensionModel.getMaxPayload())
                                .withValue(ProductDimensionTable.Cols.RESERVE, dimensionModel.getReserve())
                                .withValue(ProductDimensionTable.Cols.KERB_WEIGHT, dimensionModel.getKerbWeight())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductDimensionTable.Cols.PRODUCT_ID, dimensionModel.getProductID())
                            .withValue(ProductDimensionTable.Cols.LENGTH, dimensionModel.getLength())
                            .withValue(ProductDimensionTable.Cols.WIDTH, dimensionModel.getWidth())
                            .withValue(ProductDimensionTable.Cols.HEIGHT, dimensionModel.getHeight())
                            .withValue(ProductDimensionTable.Cols.SADDLE_HEIGHT, dimensionModel.getSaddleHeight())
                            .withValue(ProductDimensionTable.Cols.WHEEL_BASE, dimensionModel.getWheelBase())
                            .withValue(ProductDimensionTable.Cols.GROUND_CLEARANCE, dimensionModel.getGroundClearance())
                            .withValue(ProductDimensionTable.Cols.FUEL_TANK_CAPACITY, dimensionModel.getFuelTankCapacity())
                            .withValue(ProductDimensionTable.Cols.MAX_PAYLOAD, dimensionModel.getMaxPayload())
                            .withValue(ProductDimensionTable.Cols.RESERVE, dimensionModel.getReserve())
                            .withValue(ProductDimensionTable.Cols.KERB_WEIGHT, dimensionModel.getKerbWeight())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;
    }
}
