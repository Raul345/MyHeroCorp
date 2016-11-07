package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * UserDetailTable Stores the user details.
 * Created by JitainSharma on 10/06/16.
 */
public class UserDetailTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_user_details";
    public static final String PATH = "heroapp_user_details";
    public static final int PATH_TOKEN = 2;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String USER_ID = "id_user";
        public static final String USER_DETAIL_ID = "id_user_details";
        public static final String USER_NAME = "name";
        public static final String DOB = "dob";
        public static final String EMAIL = "email";
        public static final String MOBILE_NO = "mobile_no";
        public static final String UPDATED_DATE = "updated_date";
        public static final String CITY_ID = "id_city";
        public static final String PROFILE_PHOTO_ATT = "profile_photo_att";
    }

    public UserDetailTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * UserDetailTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.USER_DETAIL_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.USER_ID + " INTEGER not null, "
                            + Cols.USER_NAME + " text not null, "
                            + Cols.DOB + " INTEGER not null, "
                            + Cols.EMAIL + " text not null,"
                            + Cols.MOBILE_NO + " INTEGER not null,"
                            + Cols.UPDATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                            + Cols.CITY_ID + " INTEGER not null,"
                            + Cols.PROFILE_PHOTO_ATT + " text not null"
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
