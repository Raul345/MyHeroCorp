package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * FAQTable holds the FAQ details.
 * Created by JitainSharma on 10/06/16.
 */
public class FAQTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_faq";
    public static final String PATH = "heroapp_faq";
    public static final int PATH_TOKEN = 3;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String FAQ_ID = "id_faq";
        public static final String FAQ_HEADING = "faq_heading";
        public static final String FAQ_CONTENT = "faq_content";
        public static final String PARENT_ID = "parent_id";
        public static final String UPDATED_DATE = "updated_date";
    }

    public FAQTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * FAQTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.FAQ_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.FAQ_HEADING + " text not null, "
                            + Cols.FAQ_CONTENT + " text not null, "
                            + Cols.PARENT_ID + " INTEGER, "
                            + Cols.UPDATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
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
