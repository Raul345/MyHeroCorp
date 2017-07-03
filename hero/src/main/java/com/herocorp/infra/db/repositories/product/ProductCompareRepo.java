package com.herocorp.infra.db.repositories.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductCompareTable;
import com.herocorp.infra.db.tables.schemas.products.ProductCompareTable;

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

        values.put(ProductCompareTable.Cols._ID, entity.getId());
        values.put(ProductCompareTable.Cols.PRODUCT_ID, entity.getProductId());
        values.put(ProductCompareTable.Cols.IMAGE_NAME, entity.getImageName());
        values.put(ProductCompareTable.Cols.CREATED_DATE, entity.getDate());

        return values;
    }

    public ArrayList<ProductCompareModel> getAllCompareImages(int productId)throws Exception {
        return getRecords(null, ProductCompareTable.Cols.PRODUCT_ID+"=?", new String[]{String.valueOf(productId)}, null);
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
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductCompareTable.Cols._ID)),
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
}
