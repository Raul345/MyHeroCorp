package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.herocorp.core.models.ProductSuperFeatureModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductSuperFeatureTable;

import java.util.ArrayList;

/**
 * Created by rsawh on 15-Jul-17.
 */

public class ProductSuperFeatureRepo extends BaseRepository<ProductSuperFeatureModel> {

    public ProductSuperFeatureRepo(Context context) {
        super(context, ProductSuperFeatureTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductSuperFeatureModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductSuperFeatureTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductSuperFeatureTable.Cols.FEATURE_IMG, entity.getFeatureImg());
        values.put(ProductSuperFeatureTable.Cols.FEATURE_IMG_TEXT, entity.getFeatureImgText());

        return values;
    }

    @Override
    public ArrayList<ProductSuperFeatureModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductSuperFeatureModel> productFeatureModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductSuperFeatureTable.Cols._ID) != -1) {

                        productFeatureModels.add(
                                new ProductSuperFeatureModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductSuperFeatureTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductSuperFeatureTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductSuperFeatureTable.Cols.FEATURE_IMG)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductSuperFeatureTable.Cols.FEATURE_IMG_TEXT))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productFeatureModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductSuperFeatureModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductSuperFeatureTable.Cols.PRODUCT_ID});

        for (ProductSuperFeatureModel featureModel : entityList) {

            if(objects.contains(featureModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductSuperFeatureTable.Cols.PRODUCT_ID+ "=? and "+ProductSuperFeatureTable.Cols.FEATURE_IMG+" = ?", new String[]{String.valueOf(featureModel.getProductID()), featureModel.getFeatureImg()})
                                .withValue(ProductSuperFeatureTable.Cols.FEATURE_IMG, featureModel.getFeatureImg())
                                .withValue(ProductSuperFeatureTable.Cols.FEATURE_IMG_TEXT, featureModel.getFeatureImgText())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductSuperFeatureTable.Cols.PRODUCT_ID, featureModel.getProductID())
                            .withValue(ProductSuperFeatureTable.Cols.FEATURE_IMG, featureModel.getFeatureImg())
                            .withValue(ProductSuperFeatureTable.Cols.FEATURE_IMG_TEXT, featureModel.getFeatureImgText())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}

