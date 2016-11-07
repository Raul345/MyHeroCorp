package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductTransmissionTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductTransmissionTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_transmission";
    public static final String PATH = "heroapp_product_transmission";
    public static final int PATH_TOKEN = 15;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";

        public static final String TC_CLUTCH = "tc_clutch";
        public static final String TC_GEAR_BOX = "tc_gear_box";
        public static final String TC_CHASSIS_TYPE = "tc_chassis_type";

    }

    public ProductTransmissionTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductTransmissionTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER UNIQUE not null, "

                            + Cols.TC_CLUTCH + " text, "
                            + Cols.TC_GEAR_BOX + " text, "
                            + Cols.TC_CHASSIS_TYPE + " text, "

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