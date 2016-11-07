package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductTyreTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductTyreTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_tyres";
    public static final String PATH = "heroapp_product_tyres";
    public static final int PATH_TOKEN = 18;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";

        public static final String RIM_FRONT = "rim_front";
        public static final String RIM_REAR = "rim_rear";
        public static final String TYRE_FRONT = "tyre_front";
        public static final String TYRE_REAR = "tyre_rear";

    }

    public ProductTyreTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductTyreTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.PRODUCT_ID + " INTEGER UNIQUE not null, "

                            + Cols.RIM_FRONT + " text, "
                            + Cols.RIM_REAR + " text, "
                            + Cols.TYRE_FRONT + " text, "
                            + Cols.TYRE_REAR + " text, "

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