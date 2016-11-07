package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * UserTable Class
 * Holds the Table Revisions and the Table Schema.
 * Created by JitainSharma on 10/06/16.
 */
public class UserTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_user";
    public static final String PATH = "heroapp_user";
    public static final int PATH_TOKEN = 1;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String USER_ID = "id_user";
        public static final String AGENCY_CODE = "agency_code";
        public static final String USER_NAME = "user_name";
        public static final String PASSWORD = "password";
        public static final String STATUS = "status";
        public static final String USER_TYPE = "user_type";
        public static final String USER_ROLE = "user_role";
    }

    public UserTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * UserTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.USER_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.AGENCY_CODE + " text not null, "
                            + Cols.USER_NAME + " text not null, "
                            + Cols.PASSWORD + " text not null, "
                            + Cols.STATUS + " INTEGER not null,"
                            + Cols.USER_TYPE + " INTEGER not null,"
                            + Cols.USER_ROLE + " INTEGER not null"
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