package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductCategoryModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.ProductCategoryTable;

import java.util.ArrayList;

/**
 * Product Category Repository Class.
 * Created by JitainSharma on 12/06/16.
 */
public class ProductCategoryRepo extends BaseRepository<ProductCategoryModel> {

    public ProductCategoryRepo(Context context) {
        super(context, ProductCategoryTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductCategoryModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductCategoryTable.Cols.CATEGORY_ID, entity.getCategoryID());
        values.put(ProductCategoryTable.Cols.CATEGORY_NAME, entity.getCategoryName());
        values.put(ProductCategoryTable.Cols.CC_RANGE_FROM, entity.getCcRangeFrom());
        values.put(ProductCategoryTable.Cols.CC_RANGE_TO, entity.getCcRangeTo());

        return values;
    }

    @Override
    public ArrayList<ProductCategoryModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductCategoryModel> categories = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductCategoryTable.Cols.CATEGORY_ID) != -1) {

                        categories.add(
                                new ProductCategoryModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductCategoryTable.Cols.CATEGORY_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductCategoryTable.Cols.CATEGORY_NAME)),
                                        mCursor.getFloat(mCursor.getColumnIndexOrThrow(ProductCategoryTable.Cols.CC_RANGE_FROM)),
                                        mCursor.getFloat(mCursor.getColumnIndexOrThrow(ProductCategoryTable.Cols.CC_RANGE_TO)),
                                        mCursor.getLong(mCursor.getColumnIndexOrThrow(ProductCategoryTable.Cols.UPDATED_DATE))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return categories;

    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductCategoryModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductCategoryTable.Cols.CATEGORY_ID});

        for (ProductCategoryModel categoryModel : entityList) {

            if(objects.contains(categoryModel.getCategoryID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductCategoryTable.Cols.CATEGORY_ID+ "=?", new String[]{String.valueOf(categoryModel.getCategoryID())})
                                .withValue(ProductCategoryTable.Cols.CATEGORY_NAME, categoryModel.getCategoryName())
                                .withValue(ProductCategoryTable.Cols.CC_RANGE_FROM, categoryModel.getCcRangeFrom())
                                .withValue(ProductCategoryTable.Cols.CC_RANGE_TO, categoryModel.getCcRangeTo())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductCategoryTable.Cols.CATEGORY_ID, categoryModel.getCategoryID())
                            .withValue(ProductCategoryTable.Cols.CATEGORY_NAME, categoryModel.getCategoryName())
                            .withValue(ProductCategoryTable.Cols.CC_RANGE_FROM, categoryModel.getCcRangeFrom())
                            .withValue(ProductCategoryTable.Cols.CC_RANGE_TO, categoryModel.getCcRangeTo())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}