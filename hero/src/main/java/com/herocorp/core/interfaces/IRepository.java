package com.herocorp.core.interfaces;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.database.Cursor;

public interface IRepository<T> {
	
	public long insert(T entity) throws Exception;
	public int update(T entity, String whereClause, String[] whereArgs) throws Exception;
	public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws Exception;
	public int deleteRow(String whereClause, String[] whereArgs) throws Exception;
	public int deleteTable() throws Exception;
	public T getRecord(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception;  
	public ArrayList<T> getRecords(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception;

	public ContentValues constructContentValues(T entity) throws Exception;
	public ArrayList<T> constructEntity(Cursor mCursor) throws Exception;
	public ArrayList<ContentProviderOperation> constructOperation(ArrayList<T> entityList) throws Exception;
}