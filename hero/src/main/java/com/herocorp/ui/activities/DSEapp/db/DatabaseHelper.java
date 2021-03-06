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
import com.herocorp.ui.activities.DSEapp.models.Pitch;
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
    private static final String TABLE_CONTACTFOLLOWUP = "contact_followup";
    private static final String TABLE_PITCH = "pitch_logic";


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
        public static final String PRIORITY = "priority";
        public static final String RURAL_URBAN = "rural_urban";
        public static final String OCCUPATION = "occupation";
        public static final String USAGE = "usage";
        public static final String TWO_WHEELER_TYPE = "two_wheeler_type";
        public static final String SALES_PITCH_NO = "sales_pitch_no";
    }

    public static class Cols_Pitch {
        public static final String ID = "id";
        public static final String GENDER = "gender";
        public static final String AGE = "age";
        public static final String OCCUPATION = "occupation";
        public static final String EXISTING_OWNERSHIP = "existing_ownership";
        public static final String USAGE = "intended_usage";
        public static final String AREA = "urban_rural";
        public static final String CITY = "city";
        public static final String IMG_PATH = "img_path";
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
        public static final String REMARKS = "reason";
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
        public static final String FOLLOWUP_DONE= "followup_done";
        public static final String SYNC_STATUS = "sync_status";
    }

    public static class Cols_newenquiry {
        public static final String CONTACT_NO = "contact_no";
        public static final String REG_NO = "registration_no";
    }


    public static class Cols_contactfollowup {
        public static final String X_MODEL_INTERESTED = "x_model_interested";
        public static final String EXPCTD_DT_PURCHASE = "expctd_dt_purchase";
        public static final String ENQUIRY_ID = "enquiry_id";
        public static final String FOLLOW_DATE = "follow_date";
        public static final String ENQUIRY_ENTRY_DATE = "enquiry_entry_date";
        public static final String REMARKS = "remarks";
        public static final String FOLLOWUP_STATUS = "followup_status";
    }

    // Todo table create statement
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
            + Cols_followup.PRIORITY + " text not null, "
            + Cols_followup.TWO_WHEELER_TYPE + " text not null, "
            + Cols_followup.RURAL_URBAN + " text not null, "
            + Cols_followup.OCCUPATION + " text not null, "
            + Cols_followup.USAGE + " text not null, "
            + Cols_followup.SALES_PITCH_NO + " text not null, "
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


    public static final String CREATE_CONTACT_FOLLOWUP = "create table "
            + TABLE_CONTACTFOLLOWUP + "("
            + Cols_contactfollowup.X_MODEL_INTERESTED + " text not null, "
            + Cols_contactfollowup.EXPCTD_DT_PURCHASE + " text not null, "
            + Cols_contactfollowup.ENQUIRY_ID + " text not null, "
            + Cols_contactfollowup.FOLLOW_DATE + " text not null, "
            + Cols_contactfollowup.ENQUIRY_ENTRY_DATE + " text not null, "
            + Cols_contactfollowup.REMARKS + " text not null, "
            + Cols_contactfollowup.FOLLOWUP_STATUS + " text not null "
            + ");";

    public static final String CREATE_PITCH_LOGIC = "create table "
            + TABLE_PITCH + "("
            + Cols_Pitch.ID + " text not null, "
            + Cols_Pitch.GENDER + " text not null, "
            + Cols_Pitch.AGE + " text not null, "
            + Cols_Pitch.OCCUPATION + " text not null, "
            + Cols_Pitch.EXISTING_OWNERSHIP + " text not null, "
            + Cols_Pitch.USAGE + " text not null, "
            + Cols_Pitch.AREA + " text not null, "
            + Cols_Pitch.CITY + " text not null, "
            + Cols_Pitch.IMG_PATH + " text not null "
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
        db.execSQL(CREATE_CONTACT_FOLLOWUP);
        db.execSQL(CREATE_PITCH_LOGIC);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTFOLLOWUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PITCH);
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
        List<Close_followup> all_records = getCloseFollowup();
        int flag = 0;
        for (Close_followup record : all_records) {
            if (record.getDms_enquiryid().equalsIgnoreCase(followup.getDms_enquiryid()))
                flag = 1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (flag == 0) {
            values.put(Cols_closefollowup.REASON, followup.getReason());
            values.put(Cols_closefollowup.SUB_REASON, followup.getSubreason());
            values.put(Cols_closefollowup.EXIST_MAKE, followup.getExistmake());
            values.put(Cols_closefollowup.EXIST_MODEL, followup.getExistmodel());
            values.put(Cols_closefollowup.REMARKS, followup.getRemarks());
            values.put(Cols_closefollowup.USER_ID, followup.getUser_id());
            values.put(Cols_closefollowup.DMS_ENQUIRYID, followup.getDms_enquiryid());
            values.put(Cols_closefollowup.SYNC_STATUS, followup.getSync_status());

            // Inserting Row
            db.insert(TABLE_CLOSEFOLLOWUP, null, values);
        } else {
            values.put(Cols_closefollowup.REASON, followup.getReason());
            values.put(Cols_closefollowup.SUB_REASON, followup.getSubreason());
            values.put(Cols_closefollowup.EXIST_MAKE, followup.getExistmake());
            values.put(Cols_closefollowup.EXIST_MODEL, followup.getExistmodel());
            values.put(Cols_closefollowup.REMARKS, followup.getRemarks());
            values.put(Cols_closefollowup.USER_ID, followup.getUser_id());
            values.put(Cols_closefollowup.SYNC_STATUS, followup.getSync_status());

            db.update(TABLE_CLOSEFOLLOWUP, values, Cols_closefollowup.DMS_ENQUIRYID + " = ?",
                    new String[]{String.valueOf(followup.getDms_enquiryid())});
        }
        db.close();

    }

    public void add_next_followup(Next_Followup followup) {
        List<Next_Followup> all_records = getNextFollowup();
        int flag = 0;
        for (Next_Followup record : all_records) {
            if (record.getDms_enquiryid().equalsIgnoreCase(followup.getDms_enquiryid()))
                flag = 1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (flag == 0) {
            values.put(Cols_nextfollowup.DATE, followup.getDate());
            values.put(Cols_nextfollowup.REMARKS, followup.getRemarks());
            values.put(Cols_nextfollowup.FOLLOW_DATE, followup.getFollowdate());
            values.put(Cols_nextfollowup.USER_ID, followup.getUser_id());
            values.put(Cols_nextfollowup.DMS_ENQUIRYID, followup.getDms_enquiryid());
            values.put(Cols_nextfollowup.SYNC_STATUS, followup.getSync_status());
            // Inserting Row
            db.insert(TABLE_CLOSEFOLLOWUP, null, values);
        } else {
            values.put(Cols_nextfollowup.DATE, followup.getDate());
            values.put(Cols_nextfollowup.REMARKS, followup.getRemarks());
            values.put(Cols_nextfollowup.FOLLOW_DATE, followup.getFollowdate());
            values.put(Cols_nextfollowup.USER_ID, followup.getUser_id());
            values.put(Cols_nextfollowup.SYNC_STATUS, followup.getSync_status());
            // Inserting Row
            db.update(TABLE_NEXTFOLLOWUP, values, Cols_nextfollowup.DMS_ENQUIRYID + " = ?",
                    new String[]{String.valueOf(followup.getDms_enquiryid())});
        }
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


    public void add_pitch(Pitch pitch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cols_Pitch.ID, pitch.getId());
        values.put(Cols_Pitch.GENDER, pitch.getGender());
        values.put(Cols_Pitch.AGE, pitch.getAge());
        values.put(Cols_Pitch.OCCUPATION, pitch.getOccupation());
        values.put(Cols_Pitch.EXISTING_OWNERSHIP, pitch.getOwnership());
        values.put(Cols_Pitch.AREA, pitch.getArea());
        values.put(Cols_Pitch.USAGE, pitch.getUsage());
        values.put(Cols_Pitch.CITY, pitch.getCity());
        values.put(Cols_Pitch.IMG_PATH, pitch.getImg_path());
        db.insert(TABLE_PITCH, null, values);
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

    public void addcontactfollowup(Followup followup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Cols_contactfollowup.X_MODEL_INTERESTED, followup.getX_model_interested());
        values.put(Cols_contactfollowup.EXPCTD_DT_PURCHASE, followup.getExpcted_date_purchase());
        values.put(Cols_contactfollowup.ENQUIRY_ID, followup.getEnquiry_id());
        values.put(Cols_contactfollowup.FOLLOW_DATE, followup.getFollow_date());
        values.put(Cols_contactfollowup.ENQUIRY_ENTRY_DATE, followup.getEnquiry_entry_date());
        values.put(Cols_contactfollowup.REMARKS, followup.getFollowup_comments());
        values.put(Cols_contactfollowup.FOLLOWUP_STATUS, followup.getFollowup_status());

        // Inserting Row
        db.insert(TABLE_CONTACTFOLLOWUP, null, values);
        db.close();
    }

    public void update_contactfollowup(String followup_status, String followupdate, String
            followupcomment, String enquiryid) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Cols_contactfollowup.FOLLOWUP_STATUS, followup_status);
            values.put(Cols_contactfollowup.FOLLOW_DATE, followupdate);
            values.put(Cols_contactfollowup.REMARKS, followupcomment);
            db.update(TABLE_CONTACTFOLLOWUP, values, Cols_contactfollowup.ENQUIRY_ID + " = ?",
                    new String[]{String.valueOf(enquiryid)});
            // Inserting Row
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Followup> getContactFollowup(String id) {
        if (id.equalsIgnoreCase(""))
            id = "0";

        List<Followup> followups = new ArrayList<Followup>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTFOLLOWUP + " where " + Cols_contactfollowup.ENQUIRY_ID + " = '" + id + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Followup t = new Followup();

                t.setX_model_interested(c.getString((c.getColumnIndex(Cols_contactfollowup.X_MODEL_INTERESTED))));
                t.setExpcted_date_purchase(c.getString((c.getColumnIndex(Cols_contactfollowup.EXPCTD_DT_PURCHASE))));
                t.setEnquiry_id(c.getString((c.getColumnIndex(Cols_contactfollowup.ENQUIRY_ID))));
                t.setFollow_date(c.getString((c.getColumnIndex(Cols_contactfollowup.FOLLOW_DATE))));
                t.setEnquiry_entry_date(c.getString((c.getColumnIndex(Cols_contactfollowup.ENQUIRY_ENTRY_DATE))));
                t.setFollowup_status(c.getString((c.getColumnIndex(Cols_contactfollowup.FOLLOWUP_STATUS))));
                followups.add(t);
            } while (c.moveToNext());
        }
        return followups;
    }

    public void clearcontactfollowup_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACTFOLLOWUP);
        closeDB();
    }

    public void delete_contactfollowup(String enquiryid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTFOLLOWUP, Cols_contactfollowup.ENQUIRY_ID + " = ?",
                new String[]{String.valueOf(enquiryid)});
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

    public void clearfollowup_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FOLLOWUP);
        closeDB();
    }

    public void clearmakemodel_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MAKE);
        db.execSQL("DELETE FROM " + TABLE_MODEL);
        closeDB();
    }

    public void clearpitch() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PITCH);
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
        closeDB();
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
        closeDB();
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
        closeDB();
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

    public void update_followup(String followup_status, String followupdate, String
            followupcomment, String enquiryid) {
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


    public List<Close_followup> getCloseFollowup() {
        List<Close_followup> followups = new ArrayList<Close_followup>();
        String selectQuery = "SELECT * FROM " + TABLE_CLOSEFOLLOWUP;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Close_followup t = new Close_followup();
                t.setReason(c.getString((c.getColumnIndex(Cols_closefollowup.REASON))));
                t.setSubreason(c.getString((c.getColumnIndex(Cols_closefollowup.SUB_REASON))));
                t.setExistmake(c.getString((c.getColumnIndex(Cols_closefollowup.EXIST_MAKE))));
                t.setExistmodel(c.getString((c.getColumnIndex(Cols_closefollowup.EXIST_MODEL))));
                t.setRemarks(c.getString((c.getColumnIndex(Cols_closefollowup.REMARKS))));
                t.setUser_id(c.getString((c.getColumnIndex(Cols_closefollowup.USER_ID))));
                t.setDms_enquiryid(c.getString((c.getColumnIndex(Cols_closefollowup.DMS_ENQUIRYID))));
                followups.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return followups;
    }

    public List<Pitch> getAllPitch() {
        List<Pitch> logic = new ArrayList<Pitch>();
        String selectQuery = "SELECT * FROM " + TABLE_PITCH;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Pitch t = new Pitch();
                t.setId(c.getString((c.getColumnIndex(Cols_Pitch.ID))));
                t.setGender(c.getString((c.getColumnIndex(Cols_Pitch.GENDER))));
                t.setAge(c.getString((c.getColumnIndex(Cols_Pitch.AGE))));
                t.setOccupation(c.getString((c.getColumnIndex(Cols_Pitch.OCCUPATION))));
                t.setOwnership(c.getString((c.getColumnIndex(Cols_Pitch.EXISTING_OWNERSHIP))));
                t.setUsage(c.getString((c.getColumnIndex(Cols_Pitch.USAGE))));
                t.setArea(c.getString((c.getColumnIndex(Cols_Pitch.AREA))));
                t.setCity(c.getString((c.getColumnIndex(Cols_Pitch.CITY))));
                t.setImg_path(c.getString((c.getColumnIndex(Cols_Pitch.IMG_PATH))));
                logic.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return logic;
    }

    public List<Pitch> getPitchRow(String id) {
        List<Pitch> logic = new ArrayList<Pitch>();
        String selectQuery = "SELECT * FROM " + TABLE_PITCH + " where " + Cols_Pitch.ID + " = " + id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Pitch t = new Pitch();
                t.setId(c.getString((c.getColumnIndex(Cols_Pitch.ID))));
                t.setGender(c.getString((c.getColumnIndex(Cols_Pitch.GENDER))));
                t.setAge(c.getString((c.getColumnIndex(Cols_Pitch.AGE))));
                t.setOccupation(c.getString((c.getColumnIndex(Cols_Pitch.OCCUPATION))));
                t.setOwnership(c.getString((c.getColumnIndex(Cols_Pitch.EXISTING_OWNERSHIP))));
                t.setUsage(c.getString((c.getColumnIndex(Cols_Pitch.USAGE))));
                t.setArea(c.getString((c.getColumnIndex(Cols_Pitch.AREA))));
                t.setCity(c.getString((c.getColumnIndex(Cols_Pitch.CITY))));
                t.setImg_path(c.getString((c.getColumnIndex(Cols_Pitch.IMG_PATH))));
                logic.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return logic;
    }


    public List<Next_Followup> getNextFollowup() {
        List<Next_Followup> followups = new ArrayList<Next_Followup>();
        String selectQuery = "SELECT * FROM " + TABLE_NEXTFOLLOWUP;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Next_Followup t = new Next_Followup();
                t.setRemarks(c.getString((c.getColumnIndex(Cols_nextfollowup.REMARKS))));
                t.setDate(c.getString((c.getColumnIndex(Cols_nextfollowup.DATE))));
                t.setFollowdate(c.getString((c.getColumnIndex(Cols_nextfollowup.FOLLOW_DATE))));
                t.setUser_id(c.getString((c.getColumnIndex(Cols_nextfollowup.USER_ID))));
                t.setDms_enquiryid(c.getString((c.getColumnIndex(Cols_nextfollowup.DMS_ENQUIRYID))));
                followups.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return followups;
    }

    public void delete_closefollowup(String enquiryid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLOSEFOLLOWUP, Cols_closefollowup.DMS_ENQUIRYID + " = ?",
                new String[]{String.valueOf(enquiryid)});
        db.close();
    }

    public void delete_nextfollowup(String enquiryid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEXTFOLLOWUP, Cols_nextfollowup.DMS_ENQUIRYID + " = ?",
                new String[]{String.valueOf(enquiryid)});
        db.close();
    }


    //with additional parameters
    public void addNewfollowup(Followup followup) {
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
        values.put(Cols_followup.PRIORITY, followup.getPriority());
        values.put(Cols_followup.TWO_WHEELER_TYPE, followup.getTwo_wheeler_type());
        values.put(Cols_followup.RURAL_URBAN, followup.getRural_urban());
        values.put(Cols_followup.OCCUPATION, followup.getOccupation());
        values.put(Cols_followup.USAGE, followup.getUsage());
        values.put(Cols_followup.SALES_PITCH_NO, followup.getSales_pitch_no());
        // Inserting Row
        db.insert(TABLE_FOLLOWUP, null, values);
        db.close();
    }

    public List<Followup> getNewAllFollowups() {
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

                t.setPriority(c.getString((c.getColumnIndex(Cols_followup.PRIORITY))));
                t.setTwo_wheeler_type(c.getString((c.getColumnIndex(Cols_followup.TWO_WHEELER_TYPE))));
                t.setRural_urban(c.getString((c.getColumnIndex(Cols_followup.RURAL_URBAN))));
                t.setOccupation(c.getString((c.getColumnIndex(Cols_followup.OCCUPATION))));
                t.setUsage(c.getString((c.getColumnIndex(Cols_followup.USAGE))));
                t.setSales_pitch_no((c.getString((c.getColumnIndex(Cols_followup.SALES_PITCH_NO)))));

                followups.add(t);
            } while (c.moveToNext());
        }
        closeDB();
        return followups;
    }

    public void addnew_next_followup(Next_Followup followup) {
        List<Next_Followup> all_records = getNextFollowup();
        int flag = 0;
        for (Next_Followup record : all_records) {
            if (record.getDms_enquiryid().equalsIgnoreCase(followup.getDms_enquiryid()))
                flag = 1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (flag == 0) {
            values.put(Cols_nextfollowup.DATE, followup.getDate());
            values.put(Cols_nextfollowup.REMARKS, followup.getRemarks());
            values.put(Cols_nextfollowup.FOLLOW_DATE, followup.getFollowdate());
            values.put(Cols_nextfollowup.USER_ID, followup.getUser_id());
            values.put(Cols_nextfollowup.DMS_ENQUIRYID, followup.getDms_enquiryid());
            values.put(Cols_nextfollowup.SYNC_STATUS, followup.getSync_status());
            values.put(Cols_nextfollowup.FOLLOWUP_DONE, followup.getFollowup_done());
            // Inserting Row
            db.insert(TABLE_CLOSEFOLLOWUP, null, values);
        } else {
            values.put(Cols_nextfollowup.DATE, followup.getDate());
            values.put(Cols_nextfollowup.REMARKS, followup.getRemarks());
            values.put(Cols_nextfollowup.FOLLOW_DATE, followup.getFollowdate());
            values.put(Cols_nextfollowup.USER_ID, followup.getUser_id());
            values.put(Cols_nextfollowup.FOLLOWUP_DONE, followup.getFollowup_done());
                       values.put(Cols_nextfollowup.SYNC_STATUS, followup.getSync_status());
            // Inserting Row
            db.update(TABLE_NEXTFOLLOWUP, values, Cols_nextfollowup.DMS_ENQUIRYID + " = ?",
                    new String[]{String.valueOf(followup.getDms_enquiryid())});
        }
        db.close();

    }

}