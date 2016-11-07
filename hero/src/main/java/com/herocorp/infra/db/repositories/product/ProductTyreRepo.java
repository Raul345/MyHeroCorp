package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductTyreModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductTyreTable;


import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductTyreRepo extends BaseRepository<ProductTyreModel> {

    public ProductTyreRepo(Context context) {
        super(context, ProductTyreTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductTyreModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductTyreTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductTyreTable.Cols.RIM_FRONT, entity.getRimFront());
        values.put(ProductTyreTable.Cols.RIM_REAR, entity.getRimRear());
        values.put(ProductTyreTable.Cols.TYRE_FRONT, entity.getTyreFront());
        values.put(ProductTyreTable.Cols.TYRE_REAR, entity.getTyreRear());

        return values;
    }

    @Override
    public ArrayList<ProductTyreModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductTyreModel> tyreModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductTyreTable.Cols._ID) != -1) {

                        tyreModels.add(
                                new ProductTyreModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTyreTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTyreTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTyreTable.Cols.RIM_FRONT)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTyreTable.Cols.RIM_REAR)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTyreTable.Cols.TYRE_FRONT)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTyreTable.Cols.TYRE_REAR))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return tyreModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductTyreModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductTyreTable.Cols.PRODUCT_ID});

        for (ProductTyreModel tyreModel : entityList) {

            if (objects.contains(tyreModel.getProductID())) {
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductTyreTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(tyreModel.getProductID())})
                                .withValue(ProductTyreTable.Cols.TYRE_FRONT, tyreModel.getTyreFront())
                                .withValue(ProductTyreTable.Cols.TYRE_REAR, tyreModel.getTyreRear())
                                .withValue(ProductTyreTable.Cols.RIM_FRONT, tyreModel.getRimFront())
                                .withValue(ProductTyreTable.Cols.RIM_REAR, tyreModel.getRimRear())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductTyreTable.Cols.PRODUCT_ID, tyreModel.getProductID())
                            .withValue(ProductTyreTable.Cols.TYRE_FRONT, tyreModel.getTyreFront())
                            .withValue(ProductTyreTable.Cols.TYRE_REAR, tyreModel.getTyreRear())
                            .withValue(ProductTyreTable.Cols.RIM_FRONT, tyreModel.getRimFront())
                            .withValue(ProductTyreTable.Cols.RIM_REAR, tyreModel.getRimRear())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;
    }
}
