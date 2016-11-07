package com.herocorp.infra.db.repositories;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

import com.herocorp.core.interfaces.IRepository;
import com.herocorp.core.models.BaseEntity;
import com.herocorp.core.models.ProductCategoryModel;
import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.schemas.ProductCategoryTable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Base Repository Class.
 * Handles all the basic CURD operations of the Content Provider.
 * Created by JitainSharma on 09/06/16.
 */
public class BaseRepository<T extends BaseEntity> implements IRepository<T> {

    protected WeakReference<Context> contextWk;
    protected Uri uri;

    public BaseRepository(Context context, Uri uri) {
        contextWk = new WeakReference<>(context);
        this.uri = uri;
    }

    @Override
    public long insert(T entity) throws Exception {
        Uri insertRow =
                contextWk.get().getContentResolver()
                        .insert(uri, constructContentValues(entity));

        if (null == insertRow) return -1;
        return ContentUris.parseId(insertRow);
    }

    @Override
    public int update(T entity, String whereClause, String[] whereArgs) throws Exception {
        return contextWk.get().getContentResolver()
                .update(uri, constructContentValues(entity), whereClause, whereArgs);
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws Exception {
        return contextWk.get().getContentResolver()
                .applyBatch(ContentDescriptor.AUTHORITY, operations);
    }

    @Override
    public int deleteRow(String whereClause, String[] whereArgs) throws Exception {
        return contextWk.get().getContentResolver()
                .delete(uri, whereClause, whereArgs);
    }

    @Override
    public int deleteTable() throws SQLException {
        return contextWk.get().getContentResolver().delete(uri, null, null);
    }

    @Override
    public T getRecord(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {

        ArrayList<T> entityRecords = constructEntity(contextWk.get().getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder));

        if (null == entityRecords || entityRecords.size() == 0) return null;
        return entityRecords.get(0);
    }

    @Override
    public ArrayList<T> getRecords(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {
        return constructEntity(contextWk.get()
                .getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder));
    }

    @Override
    public ContentValues constructContentValues(T entity) throws Exception {
        return null;
    }

    @Override
    public ArrayList<T> constructEntity(Cursor mCursor) throws Exception {
        return null;
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<T> entityList) throws Exception {
        return null;
    }


    public int getDataRows(String selection, String[] selectionArgs) {

        int totalRows = 0;
        Cursor mCursor = null;

        try {
            mCursor =
                    contextWk.get()
                            .getContentResolver()
                            .query(uri, null, selection, selectionArgs, null);
            totalRows = mCursor.getCount();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "getDataRows()", e);
        } finally {
            if (mCursor != null) mCursor.close();
            mCursor = null;
        }

        return totalRows;
    }

    public ArrayList<Integer> getTableID(String[] selectionArgs) {

        int totalRows = 0;
        Cursor mCursor = null;

        ArrayList<Integer> objects = new ArrayList<>(0);

        try {
            mCursor =
                    contextWk.get()
                            .getContentResolver()
                            .query(uri, selectionArgs, null, null, null);

            if (mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(selectionArgs[0]) != -1) {

                        objects.add(mCursor.getInt(mCursor.getColumnIndexOrThrow(selectionArgs[0])));

                    }
                } while (mCursor.moveToNext());
            }

            totalRows = mCursor.getCount();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "getDataRows()", e);
        } finally {
            if (mCursor != null) mCursor.close();
            mCursor = null;
        }

        return objects;
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) throws Exception {
        return contextWk.get().getContentResolver().update(uri,
                values,
                whereClause,
                whereArgs);
    }
}