package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * Created by rsawh on 23-Jun-17.
 */

public class ProductCompareTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_compare_products";
    public static final String PATH = "heroapp_compare_products";
    public static final int PATH_TOKEN = 25;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();
    public static class Cols {

        public static final String _ID = "id_rotation_img";
        public static final String PRODUCT_ID = "product_id";
        public static final String IMAGE_NAME = "image_name";
        public static final String CREATED_DATE = "created_date";
    }

    public ProductCompareTable() {
        //Revision 2
        registerRevision(new TableRevision() {

            /**
             * ProductColorModelTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER not null, "
                            + Cols.IMAGE_NAME + " text not null, "
                            + Cols.CREATED_DATE + " text, "
                            + "UNIQUE (" + Cols.PRODUCT_ID + ", " + Cols.IMAGE_NAME + ")"
                            + ");";
                            /*
                            create table if not exists rotate_table (
                            id_rotation_img INTEGER PRIMARY KEY AUTOINCREMENT,
                            product_id INTEGER not null,
                            image_name text not null,
                            status enum(0,1) NOT NULL,
                            img_type enum(1,2) NOT NULL,
                            created_date date);
                            */


            @Override
            public int getRevisionNumber() {
                return 2;
            }

            @Override
            public void applyRevision(SQLiteDatabase database) {
                database.execSQL(TABLE_CREATE);
            }
        });
    }
}