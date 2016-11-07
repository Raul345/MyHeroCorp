package com.herocorp.infra.db.tables.schemas;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.herocorp.infra.db.ContentDescriptor;
import com.herocorp.infra.db.tables.TableHelper;
import com.herocorp.infra.db.tables.TableRevision;

/**
 * ProductAttachmentTable
 * Created by JitainSharma on 10/06/16.
 */
public class ProductAttachmentTable extends TableHelper {

    public static final String TABLE_NAME = "heroapp_product_attachment";
    public static final String PATH = "heroapp_product_attachment";
    public static final int PATH_TOKEN = 5;
    public static final Uri CONTENT_URI =
            ContentDescriptor.BASE_URI.buildUpon()
                    .appendPath(PATH).build();

    public static class Cols {

        public static final String PRODUCT_ATTACHMENT_ID = "id_product_attachment";
        public static final String PRODUCT_ID = "product_id";
        public static final String IMG_NAME = "img_name";
        public static final String IMG_TYPE = "img_type";
        public static final String IMG_COLOR_CODE = "img_color_code";
        public static final String IMG_COLOR_ICON = "img_color_icon";
        public static final String FEATURE_TEXT = "feature_text";
        public static final String HPA_STATUS = "hpa_status";
        public static final String HPA_CREATED_DATE = "hpa_created_date";
        public static final String HPA_UPDATED_DATE = "hpa_updated_date";
    }

    public ProductAttachmentTable() {
        //Revision 1
        registerRevision(new TableRevision() {

            /**
             * ProductAttachmentTable Table Schema creation
             */
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.PRODUCT_ATTACHMENT_ID + " INTEGER PRIMARY KEY not null, "
                            + Cols.PRODUCT_ID + " INTEGER not null, "
                            + Cols.IMG_NAME + " text not null, "
                            + Cols.IMG_TYPE + " INTEGER DEFAULT 1 not null, "
                            + Cols.IMG_COLOR_CODE + " text, "
                            + Cols.IMG_COLOR_ICON + " text, "
                            + Cols.FEATURE_TEXT + " text NOT NULL, "
                            + Cols.HPA_STATUS + " INTEGER DEFAULT 1 not null, "
                            + Cols.HPA_CREATED_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                            + Cols.HPA_UPDATED_DATE + " INTEGER not null"
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