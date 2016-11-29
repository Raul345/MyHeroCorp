package com.herocorp.ui.activities.DSEapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.herocorp.infra.db.SQLiteDataHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Close_followup;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.activities.DSEapp.models.LocalEnquiry;
import com.herocorp.ui.activities.DSEapp.models.Next_Followup;
import com.herocorp.ui.activities.DSEapp.models.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsawh on 27-Oct-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    private static SQLiteDataHelper ref = null;
    private static SQLiteDatabase sqlDb = null;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hero.db";

    // Table Names
    private static final String TABLE_FOLLOWUP = "followup";
    private static final String TABLE_MAKE = "make";
    private static final String TABLE_MODEL = "model";
    private static final String TABLE_CLOSEFOLLOWUP = "close_followup";
    private static final String TABLE_NEXTFOLLOWUP = "next_followup";
    private static final String TABLE_STATE = "state";
    private static final String TABLE_ENQUIRY = "enquiry";


    public static class Cols_followup {

        public static final String FST_NAME = "fst_name";
        public static final String LAST_NAME = "last_name";
        public static final String CELL_PH_NUM = "cell_ph_num";
        public static final String AGE = "age";
        public static final String GENDER = "gender";
        public static final String EMAIL_ADDR = "email_addr";
        public static final String STATE = "state";
        public static final String DISTRICT = "district";
        public static final String TEHSIL = "tehsil";
        public static final String CITY = "city";
        public static final String X_CON_SEQ_NUM = "x_con_seq_num";
        public static final String X_MODEL_INTERESTED = "x_model_interested";
        public static final String EXPCTD_DT_PURCHASE = "expctd_dt_purchase";
        public static final String X_EXCHANGE_REQUIRED = "x_exchange_required";
        public static final String X_FINANCE_REQUIRED = "x_finance_required";
        public static final String EXISTING_VEHICLE = "existing_vehicle";
        public static final String FOLLOWUP_COMMENTS = "followup_comments";
        public static final String ENQUIRY_ID = "enquiry_id";
        public static final String FOLLOW_DATE = "follow_date";
        public static final String ENQUIRY_ENTRY_DATE = "enquiry_entry_date";
        public static final String DEALER_BU_ID = "dealer_bu_id";
        public static final String FOLLOWUP_STATUS = "followup_status";
    }

    public static class Cols_make {

        public static final String ID = "id";
        public static final String MAKE_NAME = "make_name";

    }

    public static class Cols_model {

        public static final String MAKE_ID = "make_id";
        public static final String MODEL_NAME = "model_name";

    }

    public static class Cols_state {
        public static final String STATE_ID = "state_id";
        public static final String STATE_NAME = "state_name";
    }


    public static class Cols_closefollowup {
        public static final String REASON = "reason";
        public static final String SUB_REASON = "sub_reason";
        public static final String EXIST_MAKE = "existmake";
        public static final String EXIST_MODEL = "existmodel";
        public static final String USER_ID = "user_id";
        public static final String DMS_ENQUIRYID = "dms_enquiryid";
        public static final String SYNC_STATUS = "sync_status";
    }

    public static class Cols_nextfollowup {
        public static final String DATE = "date";
        public static final String REMARKS = "remarks";
        public static final String FOLLOW_DATE = "followdate";
        public static final String USER_ID = "user_id";
        public static final String DMS_ENQUIRYID = "dms_enquiryid";
        public static final String SYNC_STATUS = "sync_status";
    }

    public static class Cols_newenquiry {
        public static final String CONTACT_NO = "contact_no";
        public static final String REG_NO = "registration_no";
    }

    // Todo table create statement
    private static final String CREATE_TABLE_FOLLOWUP = "create table if not exists "
            + TABLE_FOLLOWUP + "("
            + Cols_followup.FST_NAME + " text not null, "
            + Cols_followup.LAST_NAME + " text not null, "
            + Cols_followup.CELL_PH_NUM + " text not null, "
            + Cols_followup.AGE + " integer not null, "
            + Cols_followup.GENDER + " text not null, "
            + Cols_followup.EMAIL_ADDR + ","
            + Cols_followup.STATE + " text not null, "
            + Cols_followup.DISTRICT + " text not null, "
            + Cols_followup.TEHSIL + ","
            + Cols_followup.CITY + " text not null, "
            + Cols_followup.X_CON_SEQ_NUM + " text not null, "
            + Cols_followup.X_MODEL_INTERESTED + " text not null, "
            + Cols_followup.EXPCTD_DT_PURCHASE + " text not null, "
            + Cols_followup.X_EXCHANGE_REQUIRED + " text not null, "
            + Cols_followup.X_FINANCE_REQUIRED + " text not null, "
            + Cols_followup.EXISTING_VEHICLE + " text not null, "
            + Cols_followup.FOLLOWUP_COMMENTS + ","
            + Cols_followup.ENQUIRY_ID + " text not null, "
            + Cols_followup.FOLLOW_DATE + " text not null, "
            + Cols_followup.ENQUIRY_ENTRY_DATE + " text not null, "
            + Cols_followup.DEALER_BU_ID + " text not null, "
            + Cols_followup.FOLLOWUP_STATUS
            + ");";

    //bikemake table create statement
    private static final String CREATE_TABLE_BIKEMAKE = "create table "
            + TABLE_MAKE + "("
            + Cols_make.ID + " text not null, "
            + Cols_make.MAKE_NAME + " text not null "
            + ");";


    // bikemodel table create statement
    private static final String CREATE_TABLE_BIKEMODEL = "create table "
            + TABLE_MODEL + "("
            + Cols_model.MAKE_ID + " text not null, "
            + Cols_model.MODEL_NAME + " text not null "
            + ");";


    private static final String CREATE_TABLE_CLOSE_FOLLOWUP = "create table "
            + TABLE_CLOSEFOLLOWUP + "("
            + Cols_closefollowup.REASON + ","
            + Cols_closefollowup.SUB_REASON + ","
            + Cols_closefollowup.EXIST_MAKE + ","
            + Cols_closefollowup.EXIST_MODEL + ","
            + Cols_closefollowup.USER_ID + ","
            + Cols_closefollowup.DMS_ENQUIRYID + ","
            + Cols_closefollowup.SYNC_STATUS
            + ");";

    private static final String CREATE_TABLE_NEXT_FOLLOWUP = "create table "
            + TABLE_NEXTFOLLOWUP + "("
            + Cols_nextfollowup.DATE + ","
            + Cols_nextfollowup.REMARKS + ","
            + Cols_nextfollowup.FOLLOW_DATE + ","
            + Cols_nextfollowup.USER_ID + ","
            + Cols_nextfollowup.DMS_ENQUIRYID + ","
            + Cols_nextfollowup.SYNC_STATUS
            + ");";


    public static final String CREATE_STATE = "create table "
            + TABLE_STATE + "("
            + Cols_state.STATE_ID + " integer not null, "
            + Cols_state.STATE_NAME + " text not null "
            + ");";

    public static final String CREATE_ENQUIRY = "create table "
            + TABLE_ENQUIRY + "("
            + Cols_newenquiry.CONTACT_NO + " text not null, "
            + Cols_newenquiry.REG_NO + " text not null "
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_FOLLOWUP);
        db.execSQL(CREATE_TABLE_BIKEMAKE);
        db.execSQL(CREATE_TABLE_BIKEMODEL);
        db.execSQL(CREATE_STATE);
        db.execSQL(CREATE_TABLE_CLOSE_FOLLOWUP);
        db.execSQL(CREATE_TABLE_NEXT_FOLLOWUP);
        db.execSQL(CREATE_ENQUIRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOSEFOLLOWUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEXTFOLLOWUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENQUIRY);
        // create new tables
        onCreate(db);
    }


    //inserting records
    public void addbikemake(Bikemake make) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_make.ID, make.getId());
        values.put(Cols_make.MAKE_NAME, make.getMakename());

        // Inserting Row
        db.insert(TABLE_MAKE, null, values);
        db.close();
    }

    public void addbikemodel(Bike_model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_model.MAKE_ID, model.getMakeid());
        values.put(Cols_model.MODEL_NAME, model.getModelname());

        // Inserting Row
        db.insert(TABLE_MODEL, null, values);
        db.close();
    }

    public void addfollowup(Followup followup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_followup.FST_NAME, followup.getFirst_name());
        values.put(Cols_followup.LAST_NAME, followup.getLast_name());
        values.put(Cols_followup.CELL_PH_NUM, followup.getCell_ph_no());
        values.put(Cols_followup.AGE, followup.getAge());
        values.put(Cols_followup.GENDER, followup.getGender());
        values.put(Cols_followup.EMAIL_ADDR, followup.getEmail_addr());
        values.put(Cols_followup.STATE, followup.getState());
        values.put(Cols_followup.DISTRICT, followup.getDistrict());
        values.put(Cols_followup.TEHSIL, followup.getTehsil());
        values.put(Cols_followup.CITY, followup.getCity());
        values.put(Cols_followup.X_CON_SEQ_NUM, followup.getX_con_seq_no());
        values.put(Cols_followup.X_MODEL_INTERESTED, followup.getX_model_interested());
        values.put(Cols_followup.EXPCTD_DT_PURCHASE, followup.getExpcted_date_purchase());
        values.put(Cols_followup.X_EXCHANGE_REQUIRED, followup.getX_exchange_required());
        values.put(Cols_followup.X_FINANCE_REQUIRED, followup.getX_finance_required());
        values.put(Cols_followup.EXISTING_VEHICLE, followup.getExist_vehicle());
        values.put(Cols_followup.FOLLOWUP_COMMENTS, followup.getFollowup_comments());
        values.put(Cols_followup.ENQUIRY_ID, followup.getEnquiry_id());
        values.put(Cols_followup.FOLLOW_DATE, followup.getFollow_date());
        values.put(Cols_followup.ENQUIRY_ENTRY_DATE, followup.getEnquiry_entry_date());
        values.put(Cols_followup.DEALER_BU_ID, followup.getDealer_bu_id());
        values.put(Cols_followup.FOLLOWUP_STATUS, followup.getFollowup_status());
        // Inserting Row
        db.insert(TABLE_FOLLOWUP, null, values);
        db.close();
    }

    public void add_close_followup(Close_followup followup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_closefollowup.REASON, followup.getReason());
        values.put(Cols_closefollowup.SUB_REASON, followup.getSubreason());
        values.put(Cols_closefollowup.EXIST_MAKE, followup.getExistmake());
        values.put(Cols_closefollowup.EXIST_MODEL, followup.getExistmodel());
        values.put(Cols_closefollowup.USER_ID, followup.getUser_id());
        values.put(Cols_closefollowup.DMS_ENQUIRYID, followup.getDms_enquiryid());
        values.put(Cols_closefollowup.SYNC_STATUS, followup.getSync_status());

        // Inserting Row
        db.insert(TABLE_CLOSEFOLLOWUP, null, values);
        db.close();
    }

    public void add_next_followup(Next_Followup followup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_nextfollowup.DATE, followup.getDate());
        values.put(Cols_nextfollowup.REMARKS, followup.getRemarks());
        values.put(Cols_nextfollowup.FOLLOW_DATE, followup.getFollowdate());
        values.put(Cols_nextfollowup.USER_ID, followup.getUser_id());
        values.put(Cols_nextfollowup.DMS_ENQUIRYID, followup.getDms_enquiryid());
        values.put(Cols_nextfollowup.SYNC_STATUS, followup.getSync_status());

        // Inserting Row
        db.insert(TABLE_CLOSEFOLLOWUP, null, values);
        db.close();
    }

    public void add_state(State state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_state.STATE_ID, state.getId());
        values.put(Cols_state.STATE_NAME, state.getState());
        db.insert(TABLE_STATE, null, values);
        db.close();
    }

    public void add_enquiry(LocalEnquiry enquiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_newenquiry.CONTACT_NO, enquiry.getContact_no());
        values.put(Cols_newenquiry.REG_NO, enquiry.getReg_no());
        db.insert(TABLE_ENQUIRY, null, values);
        db.close();

    }

    public List<LocalEnquiry> getAllEnquiries() {
        List<LocalEnquiry> enquiry = new ArrayList<LocalEnquiry>();
        String selectQuery = "SELECT * FROM " + TABLE_ENQUIRY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LocalEnquiry t = new LocalEnquiry();
                t.setContact_no(c.getString((c.getColumnIndex(Cols_newenquiry.CONTACT_NO))));
                t.setReg_no(c.getString((c.getColumnIndex(Cols_newenquiry.REG_NO))));
                enquiry.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return enquiry;
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    public void cleartables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FOLLOWUP);
        db.execSQL("DELETE FROM " + TABLE_MAKE);
        db.execSQL("DELETE FROM " + TABLE_MODEL);
        closeDB();
    }

    public void clearstate_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STATE);
    }

    public void clearnextfollowup_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NEXTFOLLOWUP);
    }

    public void clearclosefollowup_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CLOSEFOLLOWUP);
    }

    public List<Followup> getAllFollowups() {
        List<Followup> followups = new ArrayList<Followup>();
        String selectQuery = "SELECT * FROM " + TABLE_FOLLOWUP;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Followup t = new Followup();
                t.setFirst_name(c.getString((c.getColumnIndex(Cols_followup.FST_NAME))));
                t.setLast_name(c.getString((c.getColumnIndex(Cols_followup.LAST_NAME))));
                t.setCell_ph_no(c.getString((c.getColumnIndex(Cols_followup.CELL_PH_NUM))));
                t.setAge(c.getString((c.getColumnIndex(Cols_followup.AGE))));

                t.setGender(c.getString((c.getColumnIndex(Cols_followup.GENDER))));
                t.setEmail_addr(c.getString((c.getColumnIndex(Cols_followup.EMAIL_ADDR))));
                t.setState(c.getString((c.getColumnIndex(Cols_followup.STATE))));
                t.setDistrict(c.getString((c.getColumnIndex(Cols_followup.DISTRICT))));

                t.setTehsil(c.getString((c.getColumnIndex(Cols_followup.TEHSIL))));
                t.setCity(c.getString((c.getColumnIndex(Cols_followup.CITY))));
                t.setX_con_seq_no(c.getString((c.getColumnIndex(Cols_followup.X_CON_SEQ_NUM))));
                t.setX_model_interested(c.getString((c.getColumnIndex(Cols_followup.X_MODEL_INTERESTED))));

                t.setExpcted_date_purchase(c.getString((c.getColumnIndex(Cols_followup.EXPCTD_DT_PURCHASE))));
                t.setX_exchange_required(c.getString((c.getColumnIndex(Cols_followup.X_EXCHANGE_REQUIRED))));
                t.setX_finance_required(c.getString((c.getColumnIndex(Cols_followup.X_FINANCE_REQUIRED))));
                t.setExist_vehicle(c.getString((c.getColumnIndex(Cols_followup.EXISTING_VEHICLE))));

                t.setFollowup_comments(c.getString((c.getColumnIndex(Cols_followup.FOLLOWUP_COMMENTS))));
                t.setEnquiry_id(c.getString((c.getColumnIndex(Cols_followup.ENQUIRY_ID))));
                t.setFollow_date(c.getString((c.getColumnIndex(Cols_followup.FOLLOW_DATE))));
                t.setEnquiry_entry_date(c.getString((c.getColumnIndex(Cols_followup.ENQUIRY_ENTRY_DATE))));
                t.setDealer_bu_id(c.getString((c.getColumnIndex(Cols_followup.DEALER_BU_ID))));
                t.setFollowup_status(c.getString((c.getColumnIndex(Cols_followup.FOLLOWUP_STATUS))));

                followups.add(t);
            } while (c.moveToNext());
        }
        return followups;
    }

    public List<Bikemake> getAllBikemakes() {
        List<Bikemake> makes = new ArrayList<Bikemake>();
        String selectQuery = "SELECT * FROM " + TABLE_MAKE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Bikemake t = new Bikemake();
                t.setId(c.getString((c.getColumnIndex(Cols_make.ID))));
                t.setMakename(c.getString((c.getColumnIndex(Cols_make.MAKE_NAME))));
                makes.add(t);
            } while (c.moveToNext());
        }
        return makes;
    }

    public List<Bike_model> getAllBikemodels() {
        List<Bike_model> models = new ArrayList<Bike_model>();
        String selectQuery = "SELECT * FROM " + TABLE_MODEL;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Bike_model t = new Bike_model();
                t.setMakeid(c.getString((c.getColumnIndex(Cols_model.MAKE_ID))));
                t.setModelname(c.getString((c.getColumnIndex(Cols_model.MODEL_NAME))));
                models.add(t);
            } while (c.moveToNext());
        }
        return models;
    }

    public List<State> getAllStates() {
        List<State> states = new ArrayList<State>();
        String selectQuery = "SELECT * FROM " + TABLE_STATE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                State t = new State();
                t.setId(c.getString((c.getColumnIndex(Cols_state.STATE_ID))));
                t.setState(c.getString((c.getColumnIndex(Cols_state.STATE_NAME))));
                states.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return states;
    }

    public void update_followup(String followup_status, String followupdate, String followupcomment, String enquiryid) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Cols_followup.FOLLOWUP_STATUS, followup_status);
            values.put(Cols_followup.FOLLOW_DATE, followupdate);
            values.put(Cols_followup.FOLLOWUP_COMMENTS, followupcomment);
            db.update(TABLE_FOLLOWUP, values, Cols_followup.ENQUIRY_ID + " = ?",
                    new String[]{String.valueOf(enquiryid)});
            // Inserting Row
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete_followup(String enquiryid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOLLOWUP, Cols_followup.ENQUIRY_ID + " = ?",
                new String[]{String.valueOf(enquiryid)});
        db.close();
    }

    public void update_edit_followup(Followup followup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_followup.FST_NAME, followup.getFirst_name());
        values.put(Cols_followup.LAST_NAME, followup.getLast_name());
        values.put(Cols_followup.CELL_PH_NUM, followup.getCell_ph_no());
        values.put(Cols_followup.AGE, followup.getAge());
        values.put(Cols_followup.GENDER, followup.getGender());
        values.put(Cols_followup.EMAIL_ADDR, followup.getEmail_addr());
        values.put(Cols_followup.STATE, followup.getState());
        values.put(Cols_followup.DISTRICT, followup.getDistrict());
        values.put(Cols_followup.TEHSIL, followup.getTehsil());
        values.put(Cols_followup.CITY, followup.getCity());
        values.put(Cols_followup.X_MODEL_INTERESTED, followup.getX_model_interested());
        values.put(Cols_followup.EXPCTD_DT_PURCHASE, followup.getExpcted_date_purchase());
        values.put(Cols_followup.X_EXCHANGE_REQUIRED, followup.getX_exchange_required());
        values.put(Cols_followup.X_FINANCE_REQUIRED, followup.getX_finance_required());
        values.put(Cols_followup.EXISTING_VEHICLE, followup.getExist_vehicle());
        values.put(Cols_followup.ENQUIRY_ID, followup.getEnquiry_id());
        values.put(Cols_followup.FOLLOW_DATE, followup.getFollow_date());
        values.put(Cols_followup.FOLLOWUP_COMMENTS, followup.getFollowup_comments());
        values.put(Cols_followup.FOLLOWUP_STATUS, followup.getFollowup_status());

        db.update(TABLE_FOLLOWUP, values, Cols_followup.ENQUIRY_ID + " = ?",
                new String[]{String.valueOf(followup.getEnquiry_id())});
        // Inserting Row
        db.close();
    }
}