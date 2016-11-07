package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductColorModelTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductColorModelTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_color_model";
    public static final String PATH = "heroapp_product_color_model";
    public static final int PATH_TOKEN = 12;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String IMAGE_NAME = "image_name";
        public static final String IMG_COLOR_ICON = "img_color_icon";
        public static final String IMG_COLOR_CODE = "img_color_code";

    }

    public ProductColorModelTable() {
        //Revision 1
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
                            + Cols.IMG_COLOR_ICON + " text, "
                            + Cols.IMG_COLOR_CODE + " text, "

                            + "UNIQUE("+Cols.PRODUCT_ID+","+ Cols.IMAGE_NAME+") ON CONFLICT REPLACE, "

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