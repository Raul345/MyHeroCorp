package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductElectricalTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductElectricalTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_electrical";
    public static final String PATH = "heroapp_product_electrical";
    public static final int PATH_TOKEN = 19;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";

        public static final String BATTERY = "battery";
        public static final String HEAD_LAMP = "head_lamp";
        public static final String TAIL_LAMP = "tail_lamp";

        public static final String TURN_LAMP = "turn_lamp";
        public static final String POSITION_LAMP = "position_lamp";
        public static final String PILOT_LAMP = "pilot_lamp";

    }

    public ProductElectricalTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductElectricalTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER UNIQUE not null, "

                            + Cols.BATTERY + " text, "
                            + Cols.HEAD_LAMP + " text, "
                            + Cols.TAIL_LAMP + " text, "

                            + Cols.TURN_LAMP + " text, "
                            + Cols.POSITION_LAMP + " text, "
                            + Cols.PILOT_LAMP + " text, "

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