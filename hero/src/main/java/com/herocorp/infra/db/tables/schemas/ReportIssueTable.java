package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * ReportIssueTable
 * Created by JitainSharma on 10/06/16.
 */
public class ReportIssueTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_report_issue";
    public static final String PATH = "heroapp_report_issue";
    public static final int PATH_TOKEN = 9;

    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String REPORT_ISSUE_ID = "id_report_issue";
        public static final String DEALER_CODE = "dealer_code";
        public static final String APP_VERSION = "app_version";
        public static final String ANDROID_VERSION = "android_version";
        public static final String ISSUE = "issue";

        public static final String CREATED_DATE = "created_date";

    }

    public ReportIssueTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ReportIssueTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.REPORT_ISSUE_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.DEALER_CODE + " text not null, "
                            + Cols.APP_VERSION + " text not null, "
                            + Cols.ANDROID_VERSION + " text not null, "
                            + Cols.ISSUE + " text not null, "

                            + Cols.CREATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "

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