package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductFeatureModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductFeatureTable;

import java.util.ArrayList;

/**
 * ProductFeatureRepo
 * Created by JitainSharma on 12/06/16.
 */
public class ProductFeatureRepo extends BaseRepository<ProductFeatureModel> {

    public ProductFeatureRepo(Context context) {
        super(context, ProductFeatureTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductFeatureModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductFeatureTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductFeatureTable.Cols.FEATURE_IMG, entity.getFeatureImg());
        values.put(ProductFeatureTable.Cols.FEATURE_IMG_TEXT, entity.getFeatureImgText());

        return values;
    }

    @Override
    public ArrayList<ProductFeatureModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductFeatureModel> productFeatureModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductFeatureTable.Cols._ID) != -1) {

                        productFeatureModels.add(
                                new ProductFeatureModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductFeatureTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductFeatureTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductFeatureTable.Cols.FEATURE_IMG)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductFeatureTable.Cols.FEATURE_IMG_TEXT))
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
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductFeatureModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductFeatureTable.Cols.PRODUCT_ID});

        for (ProductFeatureModel featureModel : entityList) {

            if(objects.contains(featureModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductFeatureTable.Cols.PRODUCT_ID+ "=? and "+ProductFeatureTable.Cols.FEATURE_IMG+" = ?", new String[]{String.valueOf(featureModel.getProductID()), featureModel.getFeatureImg()})
                                .withValue(ProductFeatureTable.Cols.FEATURE_IMG, featureModel.getFeatureImg())
                                .withValue(ProductFeatureTable.Cols.FEATURE_IMG_TEXT, featureModel.getFeatureImgText())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductFeatureTable.Cols.PRODUCT_ID, featureModel.getProductID())
                            .withValue(ProductFeatureTable.Cols.FEATURE_IMG, featureModel.getFeatureImg())
                            .withValue(ProductFeatureTable.Cols.FEATURE_IMG_TEXT, featureModel.getFeatureImgText())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}
