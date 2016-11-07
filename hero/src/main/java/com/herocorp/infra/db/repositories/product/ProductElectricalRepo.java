package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductElectricalModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductElectricalTable;

import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductElectricalRepo extends BaseRepository<ProductElectricalModel> {

    public ProductElectricalRepo(Context context) {
        super(context, ProductElectricalTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductElectricalModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductElectricalTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductElectricalTable.Cols.BATTERY, entity.getBattery());
        values.put(ProductElectricalTable.Cols.HEAD_LAMP, entity.getHeadLamp());
        values.put(ProductElectricalTable.Cols.PILOT_LAMP, entity.getPilotLamp());
        values.put(ProductElectricalTable.Cols.POSITION_LAMP, entity.getPositionLamp());

        values.put(ProductElectricalTable.Cols.TAIL_LAMP, entity.getTailLamp());
        values.put(ProductElectricalTable.Cols.TURN_LAMP, entity.getTurnLamp());

        return values;
    }

    @Override
    public ArrayList<ProductElectricalModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductElectricalModel> electricalModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductElectricalTable.Cols._ID) != -1) {

                        electricalModels.add(
                                new ProductElectricalModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.BATTERY)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.HEAD_LAMP)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.TAIL_LAMP)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.TURN_LAMP)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.POSITION_LAMP)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductElectricalTable.Cols.PILOT_LAMP))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return electricalModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductElectricalModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductElectricalTable.Cols.PRODUCT_ID});

        for (ProductElectricalModel electricalModel : entityList) {

            if (objects.contains(electricalModel.getProductID())) {
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductElectricalTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(electricalModel.getProductID())})
                                .withValue(ProductElectricalTable.Cols.BATTERY, electricalModel.getBattery())
                                .withValue(ProductElectricalTable.Cols.TURN_LAMP, electricalModel.getTurnLamp())
                                .withValue(ProductElectricalTable.Cols.HEAD_LAMP, electricalModel.getHeadLamp())
                                .withValue(ProductElectricalTable.Cols.POSITION_LAMP, electricalModel.getPositionLamp())
                                .withValue(ProductElectricalTable.Cols.PILOT_LAMP, electricalModel.getPilotLamp())
                                .withValue(ProductElectricalTable.Cols.TAIL_LAMP, electricalModel.getTailLamp())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductElectricalTable.Cols.PRODUCT_ID, electricalModel.getProductID())
                            .withValue(ProductElectricalTable.Cols.BATTERY, electricalModel.getBattery())
                            .withValue(ProductElectricalTable.Cols.TURN_LAMP, electricalModel.getTurnLamp())
                            .withValue(ProductElectricalTable.Cols.HEAD_LAMP, electricalModel.getHeadLamp())
                            .withValue(ProductElectricalTable.Cols.POSITION_LAMP, electricalModel.getPositionLamp())
                            .withValue(ProductElectricalTable.Cols.PILOT_LAMP, electricalModel.getPilotLamp())
                            .withValue(ProductElectricalTable.Cols.TAIL_LAMP, electricalModel.getTailLamp())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;
    }
}
