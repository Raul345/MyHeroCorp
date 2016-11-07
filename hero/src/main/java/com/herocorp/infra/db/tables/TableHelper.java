package com.herocorp.infra.db.tables;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.herocorp.infra.db.SQLiteDataHelper;

import java.util.TreeMap;

/**
 * TableHelper Class for performing the basic
 * Table Creation and Update Processes
 * By maintaining the transactions we are ensure for
 * stable Database versions each time, even if any exception occured
 * during update process.
 * Created by JitainSharma on 10/06/16.
 */
public class TableHelper {

    public static final String TAG = TableHelper.class.getSimpleName();
    private TreeMap<Integer, TableRevision> revisions = new TreeMap<>();

    /**
     * Create the table by default, Apply the lowest revision and iterate up.
     * Upgrade up to the current version.
     * @param database
     */
    public void onCreate(SQLiteDatabase database) {

        //Create the table by default, Apply the lowest revision and iterate up
        int firstRevision = (Integer)revisions.keySet().toArray()[0];
        revisions.get(firstRevision).applyRevision(database);

        //upgrade up to the current version
        //	onUpgrade(database, firstRevision, DatabaseTableData.DB_VERSION);
    }

    /**
     * Performing upgrade on this table.
     * Iterate from the current version number to the new version.
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        // performing upgrade on this table
        //iterate from the current version number to the new version
        for (int i = oldVersion+1 ; i <= newVersion; i++){
            // if changes were applied at that level, apply them
            // also check to make sure it's not the first version, because
            // that revision would already be applied
            // by the onCreate process
            if(null != revisions.keySet().toArray() ){
                for(int j = 0; j<revisions.keySet().toArray().length;j++){
                    int revisionNumber = ((Integer) revisions.keySet().toArray()[j]);

                    if (revisions.containsKey(i)
                            && revisionNumber == i) {
                        // applying revision for this table
                        // transactionally guarantee each revision
                        database.beginTransaction();
                        try{
                            revisions.get(i).applyRevision(database);
                            database.setTransactionSuccessful();
                        }
                        catch (SQLiteException e){
                            Log.e(TableHelper.class.getSimpleName(),"onUpgrade SQLite", e);
                        }
                        finally{
                            database.endTransaction();
                        }
                    }
                }
            }
        }
    }

    protected void registerRevision(TableRevision revision){
        // registering a revision to this table
        if (revision.getRevisionNumber() > SQLiteDataHelper.NEWEST_REVISION)
            SQLiteDataHelper.NEWEST_REVISION = revision.getRevisionNumber();
        revisions.put(revision.getRevisionNumber(), revision);
    }
}
