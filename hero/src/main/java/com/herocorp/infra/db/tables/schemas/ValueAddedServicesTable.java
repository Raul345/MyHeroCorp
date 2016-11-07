package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * ValueAddedServicesTable
 * Created by JitainSharma on 10/06/16.
 */
public class ValueAddedServicesTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_valueadded_services";
    public static final String PATH = "heroapp_valueadded_services";
    public static final int PATH_TOKEN = 11;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String VALUE_ADDED_SERVICES_ID = "id_valueadded_services";
        public static final String TYPE = "type";
        public static final String PARENT_TYPE = "parent_type";
        public static final String HEADING = "heading";
        public static final String CONTENT = "content";

        public static final String HVS_CREATED_DATE = "hvs_created_date";
        public static final String HVS_UPDATED_DATE = "hvs_updated_date";

    }

    public ValueAddedServicesTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ValueAddedServicesTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.VALUE_ADDED_SERVICES_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.TYPE + " INTEGER DEFAULT 1 not null, "
                            + Cols.PARENT_TYPE + " INTEGER DEFAULT 0, "
                            + Cols.HEADING + " text not null, "
                            + Cols.CONTENT + " text not null, "

                            + Cols.HVS_CREATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                            + Cols.HVS_UPDATED_DATE + " INTEGER not null"

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