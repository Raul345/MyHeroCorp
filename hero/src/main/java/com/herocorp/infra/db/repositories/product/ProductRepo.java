package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.ProductTable;

import java.util.ArrayList;

/**
 * Product Table Repository Class.
 * Created by JitainSharma on 12/06/16.
 */
public class ProductRepo extends BaseRepository<ProductModel> {

    public ProductRepo(Context context) {
        super(context, ProductTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductTable.Cols.CATEGORY_ID, entity.getCategoryID());
        values.put(ProductTable.Cols.PRODUCT_NAME, entity.getProductName());
        values.put(ProductTable.Cols.PRODUCT_ICON, entity.getProductIcon());
        values.put(ProductTable.Cols.PRODUCT_DETAILS, entity.getProductDetails());

        values.put(ProductTable.Cols.PRODUCT_TAG, entity.getProductTag());
        values.put(ProductTable.Cols.PRODUCT_TYPE, entity.getProductType());

        return values;

    }

    @Override
    public ArrayList<ProductModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductModel> productModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductTable.Cols.PRODUCT_ID) != -1) {

                        productModels.add(
                                new ProductModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTable.Cols.PRODUCT_ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTable.Cols.CATEGORY_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTable.Cols.PRODUCT_NAME)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTable.Cols.PRODUCT_TAG)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTable.Cols.PRODUCT_DETAILS)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductTable.Cols.PRODUCT_ICON)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductTable.Cols.PRODUCT_TYPE))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productModels;

    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all product keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductTable.Cols.PRODUCT_ID});

        for (ProductModel productModel : entityList) {

            if(objects.contains(productModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductTable.Cols.PRODUCT_ID+ "=?", new String[]{String.valueOf(productModel.getProductID())})
                                .withValue(ProductTable.Cols.CATEGORY_ID, productModel.getCategoryID())
                                .withValue(ProductTable.Cols.PRODUCT_NAME, productModel.getProductName())
                                .withValue(ProductTable.Cols.PRODUCT_TAG, productModel.getProductTag())
                                .withValue(ProductTable.Cols.PRODUCT_DETAILS, productModel.getProductDetails())
                                .withValue(ProductTable.Cols.PRODUCT_ICON, productModel.getProductIcon())
                                .withValue(ProductTable.Cols.PRODUCT_TYPE, productModel.getProductType())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductTable.Cols.PRODUCT_ID, productModel.getProductID())
                            .withValue(ProductTable.Cols.CATEGORY_ID, productModel.getCategoryID())
                            .withValue(ProductTable.Cols.PRODUCT_NAME, productModel.getProductName())
                            .withValue(ProductTable.Cols.PRODUCT_TAG, productModel.getProductTag())
                            .withValue(ProductTable.Cols.PRODUCT_DETAILS, productModel.getProductDetails())
                            .withValue(ProductTable.Cols.PRODUCT_ICON, productModel.getProductIcon())
                            .withValue(ProductTable.Cols.PRODUCT_TYPE, productModel.getProductType())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}
