package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * ProductCategoryTable
 * Created by JitainSharma on 10/06/16.
 */
public class ProductCategoryTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_category";
    public static final String PATH = "heroapp_product_category";
    public static final int PATH_TOKEN = 6;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String CATEGORY_ID = "id_category";
        public static final String CATEGORY_NAME = "category_name";
        public static final String CC_RANGE_FROM = "cc_range_from";
        public static final String CC_RANGE_TO = "cc_range_to";
        public static final String UPDATED_DATE = "updated_date";
    }

    public ProductCategoryTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductCategoryTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.CATEGORY_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.CATEGORY_NAME + " text not null, "
                            + Cols.CC_RANGE_FROM + " FLOAT not null, "
                            + Cols.CC_RANGE_TO + " FLOAT not null, "
                            + Cols.UPDATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
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