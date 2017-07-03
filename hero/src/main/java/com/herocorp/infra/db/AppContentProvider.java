package com.herocorp.infra.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.herocorp.infra.db.tables.schemas.FAQTable;
import com.herocorp.infra.db.tables.schemas.NewsTable;
import com.herocorp.infra.db.tables.schemas.ProductAttachmentTable;
import com.herocorp.infra.db.tables.schemas.ProductCategoryTable;
import com.herocorp.infra.db.tables.schemas.ProductDetailTable;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.db.tables.schemas.ReportIssueTable;
import com.herocorp.infra.db.tables.schemas.UserDetailTable;
import com.herocorp.infra.db.tables.schemas.UserTable;
import com.herocorp.infra.db.tables.schemas.ValueAddedServicesTable;
import com.herocorp.infra.db.tables.schemas.products.ProductBreakTable;
import com.herocorp.infra.db.tables.schemas.products.ProductColorModelTable;
import com.herocorp.infra.db.tables.schemas.products.ProductCompareTable;
import com.herocorp.infra.db.tables.schemas.products.ProductDimensionTable;
import com.herocorp.infra.db.tables.schemas.products.ProductElectricalTable;
import com.herocorp.infra.db.tables.schemas.products.ProductEngineTable;
import com.herocorp.infra.db.tables.schemas.products.ProductFeatureTable;
import com.herocorp.infra.db.tables.schemas.products.ProductGalleryTable;
import com.herocorp.infra.db.tables.schemas.products.ProductRotationTable;
import com.herocorp.infra.db.tables.schemas.products.ProductSuspensionTable;
import com.herocorp.infra.db.tables.schemas.products.ProductTransmissionTable;
import com.herocorp.infra.db.tables.schemas.products.ProductTyreTable;

/**
 * Content Provider Class.
 * Manages all the Database basic operations.
 * Created by JitainSharma on 09/06/16.
 */
public class AppContentProvider extends ContentProvider {

    //Defines the URI which is not supported by us.
    private static final String UNKNOWN_URI = "Unknown URI";

    private SQLiteDatabase sqlDb;

    @Override
    public boolean onCreate() {

        SQLiteDataHelper dbHelper = SQLiteDataHelper.getInstance(getContext());
        sqlDb = dbHelper.getDatabase();

        sqlDb.execSQL("PRAGMA foreign_keys=ON;");

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {

        return doQuery(sqlDb, uri,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                projection, selection, selectionArgs,
                sortOrder);

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return doInsert(sqlDb,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                uri, values);

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        sqlDb.beginTransaction();

        for (ContentValues cv : values) {
            sqlDb.insert(
                    getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                    null, cv);
        }

        sqlDb.setTransactionSuccessful();
        sqlDb.endTransaction();

        return values.length;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        return doDelete(sqlDb, uri,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                selection, selectionArgs);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return doUpdate(sqlDb, uri,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                selection, selectionArgs, values);

    }

    //
    private Cursor doQuery(SQLiteDatabase db, Uri uri, String tableName, String[] projection,
                           String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        Cursor result = builder.query(db, projection, selection, selectionArgs, sortOrder, null, null);
        result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
    }

    private int doUpdate(SQLiteDatabase db, Uri uri, String tableName, String selection,
                         String[] selectionArgs, ContentValues values) {
        int result = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    private int doDelete(SQLiteDatabase db, Uri uri, String tableName, String selection,
                         String[] selectionArgs) {
        int result = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    private Uri doInsert(SQLiteDatabase db, String tableName, Uri contentUri, ContentValues values) {
        long id = db.insert(tableName, null, values);
        Uri result = contentUri.buildUpon().appendPath(String.valueOf(id)).build();
        getContext().getContentResolver().notifyChange(contentUri, null);
        return result;
    }

    private String getTableName(int token, Uri uri) {

        switch (token) {
            case UserTable.PATH_TOKEN: {
                return UserTable.TABLE_NAME;
            }
            case UserDetailTable.PATH_TOKEN: {
                return UserDetailTable.TABLE_NAME;
            }
            case FAQTable.PATH_TOKEN: {
                return FAQTable.TABLE_NAME;
            }
            case NewsTable.PATH_TOKEN: {
                return NewsTable.TABLE_NAME;
            }
            case ProductAttachmentTable.PATH_TOKEN: {
                return ProductAttachmentTable.TABLE_NAME;
            }
            case ProductCategoryTable.PATH_TOKEN: {
                return ProductCategoryTable.TABLE_NAME;
            }
            case ProductDetailTable.PATH_TOKEN: {
                return ProductDetailTable.TABLE_NAME;
            }
            case ProductTable.PATH_TOKEN: {
                return ProductTable.TABLE_NAME;
            }
            case ReportIssueTable.PATH_TOKEN: {
                return ReportIssueTable.TABLE_NAME;
            }
            case ValueAddedServicesTable.PATH_TOKEN: {
                return ValueAddedServicesTable.TABLE_NAME;
            }

            //Product Sub Tables
            case ProductColorModelTable.PATH_TOKEN: {
                return ProductColorModelTable.TABLE_NAME;
            }
            case ProductFeatureTable.PATH_TOKEN: {
                return ProductFeatureTable.TABLE_NAME;
            }
            case ProductEngineTable.PATH_TOKEN: {
                return ProductEngineTable.TABLE_NAME;
            }
            case ProductTransmissionTable.PATH_TOKEN: {
                return ProductTransmissionTable.TABLE_NAME;
            }
            case ProductSuspensionTable.PATH_TOKEN: {
                return ProductSuspensionTable.TABLE_NAME;
            }
            case ProductBreakTable.PATH_TOKEN: {
                return ProductBreakTable.TABLE_NAME;
            }
            case ProductTyreTable.PATH_TOKEN: {
                return ProductTyreTable.TABLE_NAME;
            }
            case ProductElectricalTable.PATH_TOKEN: {
                return ProductElectricalTable.TABLE_NAME;
            }
            case ProductDimensionTable.PATH_TOKEN: {
                return ProductDimensionTable.TABLE_NAME;
            }
            case ProductGalleryTable.PATH_TOKEN: {
                return ProductGalleryTable.TABLE_NAME;
            }
            case ProductRotationTable.PATH_TOKEN: {
                return ProductRotationTable.TABLE_NAME;
            }
            case ProductCompareTable.PATH_TOKEN: {
                return ProductCompareTable.TABLE_NAME;
            }

            default:
                throw new IllegalArgumentException(UNKNOWN_URI + uri);
        }

    }
}
