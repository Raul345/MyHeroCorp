package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductSuspensionModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductSuspensionTable;

import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductSuspensionRepo extends BaseRepository<ProductSuspensionModel> {

    public ProductSuspensionRepo(Context context) {
        super(context, ProductSuspensionTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductSuspensionModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductSuspensionTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductSuspensionTable.Cols.SP_FRONT, entity.getSpFront());
        values.put(ProductSuspensionTable.Cols.SP_REAR, entity.getSpRear());

        return values;
    }

    @Override
    public ArrayList<ProductSuspensionModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductSuspensionModel> modelArrayList = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductSuspensionTable.Cols._ID) != -1) {

                        modelArrayList.add(
                                new ProductSuspensionModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductSuspensionTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductSuspensionTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductSuspensionTable.Cols.SP_FRONT)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductSuspensionTable.Cols.SP_REAR))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return modelArrayList;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductSuspensionModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductSuspensionTable.Cols.PRODUCT_ID});

        for (ProductSuspensionModel suspensionModel : entityList) {

            if (objects.contains(suspensionModel.getProductID())) {
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductSuspensionTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(suspensionModel.getProductID())})
                                .withValue(ProductSuspensionTable.Cols.SP_FRONT, suspensionModel.getSpFront())
                                .withValue(ProductSuspensionTable.Cols.SP_REAR, suspensionModel.getSpRear())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductSuspensionTable.Cols.PRODUCT_ID, suspensionModel.getProductID())
                            .withValue(ProductSuspensionTable.Cols.SP_FRONT, suspensionModel.getSpFront())
                            .withValue(ProductSuspensionTable.Cols.SP_REAR, suspensionModel.getSpRear())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;
    }
}
