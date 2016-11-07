package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductGalleryModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductGalleryTable;

import java.util.ArrayList;

/**
 * ProductGalleryRepo
 * Created by JitainSharma on 25/06/16.
 */
public class ProductGalleryRepo  extends BaseRepository<ProductGalleryModel> {

    public ProductGalleryRepo(Context context) {
        super(context, ProductGalleryTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductGalleryModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductGalleryTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductGalleryTable.Cols.GALLERY_IMG, entity.getGalleryImg());
        values.put(ProductGalleryTable.Cols.GALLERY_IMG_TEXT, entity.getGalleryImgText());

        return values;
    }

    @Override
    public ArrayList<ProductGalleryModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductGalleryModel> productGalleryModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductGalleryTable.Cols._ID) != -1) {

                        productGalleryModels.add(
                                new ProductGalleryModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductGalleryTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductGalleryTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductGalleryTable.Cols.GALLERY_IMG)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductGalleryTable.Cols.GALLERY_IMG_TEXT))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return productGalleryModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductGalleryModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductGalleryTable.Cols.PRODUCT_ID});

        for (ProductGalleryModel galleryModel : entityList) {

            if(objects.contains(galleryModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductGalleryTable.Cols.PRODUCT_ID+ "=? and "+ProductGalleryTable.Cols.GALLERY_IMG+" = ?", new String[]{String.valueOf(galleryModel.getProductID()), galleryModel.getGalleryImg()})
                                .withValue(ProductGalleryTable.Cols.GALLERY_IMG, galleryModel.getGalleryImg())
                                .withValue(ProductGalleryTable.Cols.GALLERY_IMG_TEXT, galleryModel.getGalleryImgText())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductGalleryTable.Cols.PRODUCT_ID, galleryModel.getProductID())
                            .withValue(ProductGalleryTable.Cols.GALLERY_IMG, galleryModel.getGalleryImg())
                            .withValue(ProductGalleryTable.Cols.GALLERY_IMG_TEXT, galleryModel.getGalleryImgText())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;

    }
}
