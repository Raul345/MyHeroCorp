package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * Created by rsawh on 15-Jul-17.
 */

public class ProductSuperFeatureTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_superfeature";
    public static final String PATH = "heroapp_product_superfeature";
    public static final int PATH_TOKEN = 26;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String FEATURE_IMG = "feature_img";
        public static final String FEATURE_IMG_TEXT = "feature_img_text";

    }

    public ProductSuperFeatureTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductSuperFeatureTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER not null, "
                            + Cols.FEATURE_IMG + " text not null, "
                            + Cols.FEATURE_IMG_TEXT + " text, "

                            + "UNIQUE("+ Cols.PRODUCT_ID+","+ Cols.FEATURE_IMG+") ON CONFLICT REPLACE, "

                            + "FOREIGN KEY("+ Cols.PRODUCT_ID +") REFERENCES "+
                            ProductTable.TABLE_NAME +"("+ ProductTable.Cols.PRODUCT_ID +") ON DELETE CASCADE"

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
