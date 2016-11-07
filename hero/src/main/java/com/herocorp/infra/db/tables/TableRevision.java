package com.herocorp.infra.db.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Interface for holding the Table single version,
 * or revisions, of the table at a given time.
 * Created by JitainSharma on 10/06/16.
 */
public interface TableRevision {
    void applyRevision(SQLiteDatabase database);
    int getRevisionNumber();
}
