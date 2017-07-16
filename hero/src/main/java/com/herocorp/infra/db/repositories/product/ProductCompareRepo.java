package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.core.models.ProductModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductCompareTable;
import com.herocorp.infra.db.tables.schemas.products.ProductCompareTable;
import com.herocorp.infra.db.tables.schemas.products.ProductSuperFeatureTable;

import java.util.ArrayList;

/**
 * Created by rsawh on 23-Jun-17.
 */

public class ProductCompareRepo extends BaseRepository<ProductCompareModel> {

    public ProductCompareRepo(Context context) {
        super(context, ProductCompareTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductCompareModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;
        values.put(ProductCompareTable.Cols.PRODUCT_ID, entity.getProductId());
        values.put(ProductCompareTable.Cols.IMAGE_NAME, entity.getProductFeatureImages());
        values.put(ProductCompareTable.Cols.CREATED_DATE, entity.getCreateDate());

        return values;
    }

    public ArrayList<ProductCompareModel> getAllCompareImages(int productId) throws Exception {
        return getRecords(null, ProductCompareTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
    }

    @Override
    public ArrayList<ProductCompareModel> getRecords(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {
        return constructEntity(contextWk.get().getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder));
    }

    @Override
    public ArrayList<ProductCompareModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductCompareModel> productCompareModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductCompareTable.Cols._ID) != -1) {

                        productCompareModels.add(
                                new ProductCompareModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductCompareTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductCompareTable.Cols.IMAGE_NAME)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductCompareTable.Cols.CREATED_DATE))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productCompareModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductCompareModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all product keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductCompareTable.Cols.PRODUCT_ID});

        for (ProductCompareModel productCompareModel : entityList) {

            if (objects.contains(productCompareModel.getProductId())) {
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductCompareTable.Cols.PRODUCT_ID + "=? and " + ProductCompareTable.Cols.IMAGE_NAME + " = ?", new String[]{String.valueOf(productCompareModel.getProductId()), productCompareModel.getProductFeatureImages()})
                                .withValue(ProductCompareTable.Cols.IMAGE_NAME, productCompareModel.getProductFeatureImages())
                                .withValue(ProductCompareTable.Cols.CREATED_DATE, productCompareModel.getCreateDate())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductCompareTable.Cols.PRODUCT_ID, productCompareModel.getProductId())
                            .withValue(ProductCompareTable.Cols.IMAGE_NAME, productCompareModel.getProductFeatureImages())
                            .withValue(ProductCompareTable.Cols.CREATED_DATE, productCompareModel.getCreateDate())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}
