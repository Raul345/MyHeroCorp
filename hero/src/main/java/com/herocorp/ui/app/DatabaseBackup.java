package com.herocorp.ui.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Database Backup class
 * //TODO: For testing and Debug and must be commented when releasing
 * @author jsharma
 *
 */
public class DatabaseBackup {

	public DatabaseBackup(Context context){
		backupDB(context);
	}

	private void backupDB(Context context){
		try {
			backupDatabase(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Copy the app db file into the sd card
	 */
	@SuppressLint("SdCardPath")
	private void backupDatabase(Context context) throws IOException {
		//Open your local db as the input stream
		String inFileName = "/data/data/com.herocorp/databases/HeroCorp.db";
		File dbFile = new File(inFileName);
		FileInputStream fis = new FileInputStream(dbFile);

		String outFileName = Environment.getExternalStorageDirectory()+"/HeroCorp.db";
		//Open the empty db as the output stream
		OutputStream output = new FileOutputStream(outFileName);
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer))>0){
			output.write(buffer, 0, length);
		}
		//Close the streams
		output.flush();
		output.close();
		fis.close();
		System.out.println("Database backup for testing:"+inFileName);
	}
}