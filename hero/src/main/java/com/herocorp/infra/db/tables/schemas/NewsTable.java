package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * NewsTable holds the news data.
 * Created by JitainSharma on 10/06/16.
 */
public class NewsTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_news";
    public static final String PATH = "heroapp_news";
    public static final int PATH_TOKEN = 4;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String NEWS_ID = "id_news";
        public static final String HEADING = "heading";
        public static final String CONTENT = "content";
        public static final String IMG_ICON = "img_icon";
        public static final String IMG_LARGE = "img_large";
        public static final String STATUS = "status";
        public static final String CREATED_BY = "created_by";
        public static final String CREATED_DATE = "created_date";
        public static final String UPDATED_DATE = "updated_dated";
    }

    public NewsTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * NewsTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.NEWS_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.HEADING + " text not null, "
                            + Cols.CONTENT + " text not null, "
                            + Cols.IMG_ICON + " text, "
                            + Cols.IMG_LARGE + " text, "
                            + Cols.STATUS + " INTEGER DEFAULT 1 not null, "
                            + Cols.CREATED_BY + " text, "
                            + Cols.CREATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                            + Cols.UPDATED_DATE + " INTEGER not null"
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
