package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductColorModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductColorModelTable;

import java.util.ArrayList;

/**
 * Product Color Model Table Repository class.
 * Created by JitainSharma on 12/06/16.
 */
public class ProductColorModelRepo extends BaseRepository<ProductColorModel> {

    public ProductColorModelRepo(Context context) {
        super(context, ProductColorModelTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductColorModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductColorModelTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductColorModelTable.Cols.IMAGE_NAME, entity.getImageName());
        values.put(ProductColorModelTable.Cols.IMG_COLOR_CODE, entity.getImgColorCode());
        values.put(ProductColorModelTable.Cols.IMG_COLOR_ICON, entity.getImgColorIcon());

        return values;
    }

    @Override
    public ArrayList<ProductColorModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductColorModel> productColorModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductColorModelTable.Cols._ID) != -1) {

                        productColorModels.add(
                                new ProductColorModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductColorModelTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductColorModelTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductColorModelTable.Cols.IMAGE_NAME)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductColorModelTable.Cols.IMG_COLOR_ICON)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductColorModelTable.Cols.IMG_COLOR_CODE))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productColorModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductColorModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductColorModelTable.Cols.PRODUCT_ID});

        for (ProductColorModel colorModel : entityList) {

            if(objects.contains(colorModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductColorModelTable.Cols.PRODUCT_ID+ "=? and "+ProductColorModelTable.Cols.IMAGE_NAME+"=?", new String[]{String.valueOf(colorModel.getProductID()), colorModel.getImageName()})
                                .withValue(ProductColorModelTable.Cols.IMAGE_NAME, colorModel.getImageName())
                                .withValue(ProductColorModelTable.Cols.IMG_COLOR_ICON, colorModel.getImgColorIcon())
                                .withValue(ProductColorModelTable.Cols.IMG_COLOR_CODE, colorModel.getImgColorCode())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductColorModelTable.Cols.PRODUCT_ID, colorModel.getProductID())
                            .withValue(ProductColorModelTable.Cols.IMAGE_NAME, colorModel.getImageName())
                            .withValue(ProductColorModelTable.Cols.IMG_COLOR_ICON, colorModel.getImgColorIcon())
                            .withValue(ProductColorModelTable.Cols.IMG_COLOR_CODE, colorModel.getImgColorCode())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}
