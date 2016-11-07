package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductTransmissionModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductTransmissionTable;

import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductTransmissionRepo extends BaseRepository<ProductTransmissionModel> {

    public ProductTransmissionRepo(Context context) {
        super(context, ProductTransmissionTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductTransmissionModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductTransmissionTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductTransmissionTable.Cols.TC_CLUTCH, entity.getTcClutch());
        values.put(ProductTransmissionTable.Cols.TC_GEAR_BOX, entity.getTcGearBox());
        values.put(ProductTransmissionTable.Cols.TC_CHASSIS_TYPE, entity.getTcClutch());

        return values;
    }

    @Override
    public ArrayList<ProductTransmissionModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductTransmissionModel> transmissionModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductTransmissionTable.Cols._ID) != -1) {

                        transmissionModels.add(
                                new ProductTransmissionModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTransmissionTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTransmissionTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTransmissionTable.Cols.TC_CLUTCH)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTransmissionTable.Cols.TC_GEAR_BOX)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTransmissionTable.Cols.TC_CHASSIS_TYPE))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return transmissionModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductTransmissionModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductTransmissionTable.Cols.PRODUCT_ID});

        for (ProductTransmissionModel transmissionModel : entityList) {

            if (objects.contains(transmissionModel.getProductID())) {
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductTransmissionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(transmissionModel.getProductID())})
                                .withValue(ProductTransmissionTable.Cols.TC_CLUTCH, transmissionModel.getTcClutch())
                                .withValue(ProductTransmissionTable.Cols.TC_GEAR_BOX, transmissionModel.getTcGearBox())
                                .withValue(ProductTransmissionTable.Cols.TC_CHASSIS_TYPE, transmissionModel.getTcChassisType())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductTransmissionTable.Cols.PRODUCT_ID, transmissionModel.getProductID())
                            .withValue(ProductTransmissionTable.Cols.TC_CLUTCH, transmissionModel.getTcClutch())
                            .withValue(ProductTransmissionTable.Cols.TC_GEAR_BOX, transmissionModel.getTcGearBox())
                            .withValue(ProductTransmissionTable.Cols.TC_CHASSIS_TYPE, transmissionModel.getTcChassisType())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;
    }
}
