package com.herocorp.infra.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.herocorp.infra.db.tables.TableHelper;
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

import java.util.ArrayList;

/**
 * Database Helper Class
 * Holds the SQLite Database connections and
 * manage the database opening, closing, updating functions.
 * Created by JitainSharma on 10/06/16.
 */
public class SQLiteDataHelper {

    //Database Version, Name and Password
    public static final String DB_NAME = "HeroCorp.db";
    public static final int DB_VERSION = 2;
    public static int NEWEST_REVISION = 2;

    private static SQLiteDataHelper ref = null;
    private static SQLiteDatabase sqlDb = null;
    private static String TAG = SQLiteDataHelper.class.getSimpleName();

    /**
     * Get Singleton instance of DataBasePersistanceManager
     * This method performs initialization for {SQLiteDatabase}
     *
     * @param ctx
     * @return
     */
    public static synchronized SQLiteDataHelper getInstance(Context ctx) {

        if (ref == null) {
            ref = new SQLiteDataHelper();
        }

        if (sqlDb == null)
            sqlDb = ref.new DatabaseHelper(ctx, getTablesList()).getWritableDatabase();

        return ref;
    }

    /**
     * Object Clone is not supported
     */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Get SQLite Database instance
     *
     * @return
     */
    public SQLiteDatabase getDatabase() {
        return sqlDb;
    }

    /**
     * SQLite Helper class for creating, updating the database
     * {@link SQLiteOpenHelper}
     *
     * @author admin
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        private ArrayList<TableHelper> tables;

        DatabaseHelper(Context context, ArrayList<TableHelper> tables) {
            super(context, DB_NAME, null, DB_VERSION);
            this.tables = tables;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (TableHelper table : tables)
                table.onCreate(db);
        }

        /**
         * using {@link //http://stephenwan.net/blog/2013/02/15/managing-sqlite-databases-in-android} for
         * Referencing to apply the database changes with proper re-usable system to handle schema,
         * management and upgrade paths for independent tables in a generic way.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG + " onUpdate ", db.toString() + ": Old Version - " + String.valueOf(oldVersion) + ": New Version - " + String.valueOf(newVersion));
            for (TableHelper table : tables)
                table.onUpgrade(db, oldVersion, newVersion);
        }

        /**
         * Close any open database object.
         */
        @Override
        public synchronized void close() {
            if (null != sqlDb)
                sqlDb.close();
            super.close();
        }
    }

    /**
     * Construct ArrayList which
     * consists all the table schema's class
     *
     * @return
     */
    private static ArrayList<TableHelper> getTablesList() {

        ArrayList<TableHelper> tables = new ArrayList<>(0);

        //Add Master Tables
        tables.add(new UserTable());
        tables.add(new UserDetailTable());
        tables.add(new ProductCategoryTable());
        tables.add(new ProductTable());
        tables.add(new ProductDetailTable());
        tables.add(new ProductAttachmentTable());

        tables.add(new ProductBreakTable());
        tables.add(new ProductColorModelTable());
        tables.add(new ProductDimensionTable());
        tables.add(new ProductElectricalTable());
        tables.add(new ProductEngineTable());
        tables.add(new ProductFeatureTable());
        tables.add(new ProductSuspensionTable());
        tables.add(new ProductTransmissionTable());
        tables.add(new ProductTyreTable());
        tables.add(new ProductGalleryTable());

        tables.add(new ValueAddedServicesTable());

        tables.add(new NewsTable());
        tables.add(new FAQTable());
        tables.add(new ReportIssueTable());
        tables.add(new ProductRotationTable());
        tables.add(new ProductCompareTable());

        return tables;
    }

}
