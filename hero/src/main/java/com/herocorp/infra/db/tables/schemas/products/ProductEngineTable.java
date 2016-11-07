package com.herocorp.infra.db.tables.schemas.products;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;
import com.herocorp.infra.db.tables.schemas.ProductTable;

/**
 * ProductEngineTable
 * Created by JitainSharma on 12/06/16.
 */
public class ProductEngineTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_engine";
    public static final String PATH = "heroapp_product_engine";
    public static final int PATH_TOKEN = 14;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String _ID = "_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String ENGINE_TYPE = "engine_type";
        public static final String DISPLACEMENT = "displacement";
        public static final String MAX_POWER = "max_power";

        public static final String MAX_TORQUE = "max_torque";
        public static final String MAX_SPEED = "max_speed";

        public static final String ER_OIL_CAPACITY = "er_oil_capacity";
        public static final String ER_OIL_GRADE = "er_oil_grade";
        public static final String ER_AIR_FILTRATION = "er_air_filtration";

        public static final String ER_ACCELERATION = "er_acceleration";
        public static final String ER_BORE_STROKE = "er_bore_stroke";
        public static final String ER_CARBURETOR = "er_carburetor";
        public static final String ER_COMPRESSION_RATIO = "er_compression_ratio";
        public static final String ER_STARTER = "er_starter";

        public static final String ER_IGNITION = "er_ignition";
        public static final String ER_FUEL_SYSTEM = "er_fuel_system";
        public static final String ER_FUEL_METERING = "er_fuel_metering";
        public static final String ER_CYLINDER_ARRANGEMENT = "er_cylinder_arrangement";

    }

    public ProductEngineTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductEngineTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + Cols.PRODUCT_ID + " INTEGER UNIQUE not null, "
                            + Cols.ENGINE_TYPE + " text, "
                            + Cols.DISPLACEMENT + " text, "
                            + Cols.MAX_POWER + " text, "

                            + Cols.MAX_TORQUE + " text, "
                            + Cols.MAX_SPEED + " text, "
                            + Cols.ER_OIL_CAPACITY + " text, "
                            + Cols.ER_OIL_GRADE + " text, "
                            + Cols.ER_AIR_FILTRATION + " text, "

                            + Cols.ER_ACCELERATION + " text, "
                            + Cols.ER_BORE_STROKE + " text, "
                            + Cols.ER_CARBURETOR + " text, "
                            + Cols.ER_COMPRESSION_RATIO + " text, "
                            + Cols.ER_STARTER + " text, "

                            + Cols.ER_IGNITION + " text, "
                            + Cols.ER_FUEL_SYSTEM + " text, "
                            + Cols.ER_FUEL_METERING + " text, "
                            + Cols.ER_CYLINDER_ARRANGEMENT + " text, "

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