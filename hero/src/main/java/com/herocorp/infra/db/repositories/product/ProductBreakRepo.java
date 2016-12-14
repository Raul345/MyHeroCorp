package com.herocorp.infra.db.repositories.product;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.herocorp.core.models.ProductBreakModel;
import com.herocorp.infra.db.repositories.BaseRepository;
import com.herocorp.infra.db.tables.schemas.products.ProductBreakTable;

import java.util.ArrayList;

/**
 * Created by JitainSharma on 17/06/16.
 */
public class ProductBreakRepo extends BaseRepository<ProductBreakModel> {

    public ProductBreakRepo(Context context) {
        super(context, ProductBreakTable.CONTENT_URI);
    }

    @Override
    public ContentValues constructContentValues(ProductBreakModel entity) throws Exception {
        super.constructContentValues(entity);

        ContentValues values = new ContentValues();
        if (null == entity) return values;

        values.put(ProductBreakTable.Cols.PRODUCT_ID, entity.getProductID());
        values.put(ProductBreakTable.Cols.FRONT_DISC, entity.getFrontDisc());
        values.put(ProductBreakTable.Cols.FRONT_DRUM, entity.getFrontDrum());
        values.put(ProductBreakTable.Cols.RARE_DISK, entity.getRareDisk());
        values.put(ProductBreakTable.Cols.RARE_DRUM, entity.getRareDrum());

        return values;
    }

    @Override
    public ArrayList<ProductBreakModel> constructEntity(Cursor mCursor) throws Exception {
        super.constructEntity(mCursor);

        ArrayList<ProductBreakModel> breakModels = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(ProductBreakTable.Cols._ID) != -1) {

                        breakModels.add(
                                new ProductBreakModel(
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductBreakTable.Cols._ID)),
                                        mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductBreakTable.Cols.PRODUCT_ID)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductBreakTable.Cols.FRONT_DISC)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductBreakTable.Cols.FRONT_DRUM)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductBreakTable.Cols.RARE_DISK)),
                                        mCursor.getString(mCursor.getColumnIndexOrThrow(ProductBreakTable.Cols.RARE_DRUM))
                                ));
                    }
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }
        return breakModels;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<ProductBreakModel> entityList) throws Exception {
        super.constructOperation(entityList);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        //Get all category keys from the database
        ArrayList<Integer> objects = getTableID(new String[]{ProductBreakTable.Cols.PRODUCT_ID});

        for (ProductBreakModel breakModel : entityList) {

            if(objects.contains(breakModel.getProductID())){
                //Already Contains, Update it
                operations.add(
                        ContentProviderOperation.newUpdate(uri)
                                .withSelection(ProductBreakTable.Cols.PRODUCT_ID+ "=?", new String[]{String.valueOf(breakModel.getProductID())})
                                .withValue(ProductBreakTable.Cols.FRONT_DISC, breakModel.getFrontDisc())
                                .withValue(ProductBreakTable.Cols.FRONT_DRUM, breakModel.getFrontDrum())
                                .withValue(ProductBreakTable.Cols.RARE_DISK, breakModel.getRareDisk())
                                .withValue(ProductBreakTable.Cols.RARE_DRUM, breakModel.getRareDrum())
                                .withYieldAllowed(true)
                                .build());

                continue;
            }

            //New record, Insert it
            operations.add(
                    ContentProviderOperation.newInsert(uri)
                            .withValue(ProductBreakTable.Cols.PRODUCT_ID, breakModel.getProductID())
                            .withValue(ProductBreakTable.Cols.FRONT_DISC, breakModel.getFrontDisc())
                            .withValue(ProductBreakTable.Cols.FRONT_DRUM, breakModel.getFrontDrum())
                            .withValue(ProductBreakTable.Cols.RARE_DISK, breakModel.getRareDisk())
                            .withValue(ProductBreakTable.Cols.RARE_DRUM, breakModel.getRareDrum())
                            .withYieldAllowed(true)
                            .build());

        }

        return operations;
    }
}
