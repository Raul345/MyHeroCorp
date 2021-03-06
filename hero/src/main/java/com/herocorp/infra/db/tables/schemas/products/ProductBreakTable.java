package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductBreakTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductBreakTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_breaks";
    public static final String PATH = "heroapp_product_breaks";
    public static final int PATH_TOKEN = 17;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";

        public static final String FRONT_DISC = "front_disc";
        public static final String FRONT_DRUM = "front_drum";
        public static final String RARE_DISK = "rare_disk";
        public static final String RARE_DRUM = "rare_drum";

    }

    public ProductBreakTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductBreakTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER UNIQUE not null, "

                            + Cols.FRONT_DISC + " text, "
                            + Cols.FRONT_DRUM + " text, "
                            + Cols.RARE_DISK + " text, "
                            + Cols.RARE_DRUM + " text, "

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