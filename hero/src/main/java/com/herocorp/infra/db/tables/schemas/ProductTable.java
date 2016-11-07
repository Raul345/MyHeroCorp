package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * ProductTable
 * Created by JitainSharma on 10/06/16.
 */
public class ProductTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product";
    public static final String PATH = "heroapp_product";
    public static final int PATH_TOKEN = 8;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String PRODUCT_ID = "id_product";
        public static final String CATEGORY_ID = "category_id";
        public static final String PRODUCT_NAME = "product_name";
        public static final String PRODUCT_TAG = "product_tag";
        public static final String PRODUCT_DETAILS = "product_deatils";

        public static final String PRODUCT_ICON = "product_icon"; // productImage
        public static final String PRODUCT_TYPE = "product_type";
        public static final String PRODUCT_ORDER = "product_order";
        public static final String PRODUCT_STATUS = "product_status";
        public static final String HP_CREATED_BY = "hp_created_by";

        public static final String HP_CREATED_DATE = "hp_created_date";
        public static final String HP_UPDATED_DATE = "hp_updated_date";

    }

    public ProductTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.PRODUCT_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.CATEGORY_ID + " INTEGER not null, "
                            + Cols.PRODUCT_NAME + " text not null, "
                            + Cols.PRODUCT_TAG + " text, "
                            + Cols.PRODUCT_DETAILS + " text, "

                            + Cols.PRODUCT_ICON + " text not null, "
                            + Cols.PRODUCT_TYPE + " INTEGER DEFAULT 1 not null, "
                            + Cols.PRODUCT_ORDER + " INTEGER, "
                            + Cols.PRODUCT_STATUS + " INTEGER, "
                            + Cols.HP_CREATED_BY + " INTEGER, "

                            + Cols.HP_CREATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                            + Cols.HP_UPDATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "

                            + "FOREIGN KEY(" + Cols.CATEGORY_ID + ") REFERENCES " +
                            ProductCategoryTable.TABLE_NAME + "(" + ProductCategoryTable.Cols.CATEGORY_ID + ") ON DELETE CASCADE"

                            + ");";

            @Override
            public int getRevisionNumber() {
                return 1;
            }

            @Override
            public void applyRevision(SQLiteDatabase database) {
                database.execSQL(TABLE_CREATE);
            }
        });
    }
}