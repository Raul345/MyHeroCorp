package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductDimensionTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductDimensionTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_dimension";
    public static final String PATH = "heroapp_product_dimension";
    public static final int PATH_TOKEN = 20;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";

        public static final String LENGTH = "length";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";

        public static final String SADDLE_HEIGHT = "saddle_height";
        public static final String WHEEL_BASE = "wheel_base";
        public static final String GROUND_CLEARANCE = "ground_clearance";

        public static final String FUEL_TANK_CAPACITY = "fuel_tank_capacity";
        public static final String RESERVE = "reserve";
        public static final String KERB_WEIGHT = "kerb_weight";
        public static final String MAX_PAYLOAD = "max_payload";

    }

    public ProductDimensionTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductDimensionTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER UNIQUE not null, "

                            + Cols.LENGTH + " text, "
                            + Cols.WIDTH + " text, "
                            + Cols.HEIGHT + " text, "

                            + Cols.SADDLE_HEIGHT + " text, "
                            + Cols.WHEEL_BASE + " text, "
                            + Cols.GROUND_CLEARANCE + " text, "
                            + Cols.FUEL_TANK_CAPACITY + " text, "
                            + Cols.RESERVE + " text, "

                            + Cols.KERB_WEIGHT + " text, "
                            + Cols.MAX_PAYLOAD + " text, "

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