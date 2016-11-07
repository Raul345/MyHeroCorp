package com.herocorp.infra.db.repositories.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.herocorp.core.models.ProductModel;
import com.herocorp.core.models.ProductRotationModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.db.tables.schemas.products.ProductRotationTable;

import java.util.ArrayList;

/**
 * Created by EvilGod on 9/3/2016.
 */
public class ProductRotationRepo extends BaseRepository<ProductRotationModel> {

    public ProductRotationRepo(Context context) {
        super(context, ProductRotationTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductRotationModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductRotationTable.Cols._ID, entity.getId());
        values.put(ProductRotationTable.Cols.PRODUCT_ID, entity.getProductId());
        values.put(ProductRotationTable.Cols.IMAGE_NAME, entity.getImageName());
        values.put(ProductRotationTable.Cols.CREATED_DATE, entity.getDate());

        return values;
    }

    public ArrayList<ProductRotationModel> getAllRotationImages(int productId)throws Exception {
        return getRecords(null, ProductRotationTable.Cols.PRODUCT_ID+"=?", new String[]{String.valueOf(productId)}, null);
    }

    @Override
    public ArrayList<ProductRotationModel> getRecords(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {
        return constructEntity(contextWk.get().getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder));
    }

    @Override
    public ArrayList<ProductRotationModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductRotationModel> productRotationModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductRotationTable.Cols._ID) != -1) {

                        productRotationModels.add(
                                new ProductRotationModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductRotationTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductRotationTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductRotationTable.Cols.IMAGE_NAME)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductRotationTable.Cols.CREATED_DATE))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productRotationModels;
    }
}
