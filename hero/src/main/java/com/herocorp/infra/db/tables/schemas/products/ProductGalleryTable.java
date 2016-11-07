package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductGalleryTable
 * Created by JitainSharma on 25/06/16.
 */
public class ProductGalleryTable  extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_gallery";
    public static final String PATH = "heroapp_product_gallery";
    public static final int PATH_TOKEN = 21;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";

        public static final String GALLERY_IMG = "gallery_img";
        public static final String GALLERY_IMG_TEXT = "gallery_img_text";

    }

    public ProductGalleryTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductGalleryTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER not null, "

                            + Cols.GALLERY_IMG + " text not null, "
                            + Cols.GALLERY_IMG_TEXT + " text, "

                            + "FOREIGN KEY(" + Cols.PRODUCT_ID + ") REFERENCES " +
                            ProductTable.TABLE_NAME + "(" + ProductTable.Cols.PRODUCT_ID + ") ON DELETE CASCADE"

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
