package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * ProductDetailTable
 * Created by JitainSharma on 10/06/16.
 */
public class ProductDetailTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_details";
    public static final String PATH = "heroapp_product_details";
    public static final int PATH_TOKEN = 7;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String PRODUCT_DETAILS_ID = "id_product_details";
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
        public static final String TC_CLUTCH = "tc_clutch";

        public static final String TC_GEAR_BOX = "tc_gear_box";
        public static final String TC_CHASSIS_TYPE = "tc_chassis_type";
        public static final String SP_FRONT = "sp_front";
        public static final String SP_REAR = "sp_rear";
    }

    public ProductDetailTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductDetailTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.PRODUCT_DETAILS_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.PRODUCT_ID + " INTEGER not null, "
                            + Cols.ENGINE_TYPE + " text not null, "
                            + Cols.DISPLACEMENT + " text not null, "
                            + Cols.MAX_POWER + " text, "

                            + Cols.MAX_TORQUE + " text, "
                            + Cols.MAX_SPEED + " text, "
                            + Cols.ER_OIL_CAPACITY + " text not null, "
                            + Cols.ER_OIL_GRADE + " text not null, "
                            + Cols.ER_AIR_FILTRATION + " text not null, "

                            + Cols.ER_ACCELERATION + " text not null, "
                            + Cols.ER_BORE_STROKE + " text not null, "
                            + Cols.ER_CARBURETOR + " text not null, "
                            + Cols.ER_COMPRESSION_RATIO + " text not null, "
                            + Cols.ER_STARTER + " text not null, "

                            + Cols.ER_IGNITION + " text not null, "
                            + Cols.ER_FUEL_SYSTEM + " text not null, "
                            + Cols.ER_FUEL_METERING + " text not null, "
                            + Cols.ER_CYLINDER_ARRANGEMENT + " text not null, "
                            + Cols.TC_CLUTCH + " text not null, "

                            + Cols.TC_GEAR_BOX + " text not null, "
                            + Cols.TC_CHASSIS_TYPE + " text not null, "
                            + Cols.SP_FRONT + " text not null, "
                            + Cols.SP_REAR + " text not null "
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