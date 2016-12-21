package com.herocorp.ui.activities.DSEapp.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rsawh on 05-Dec-16.
 */

public class Dateformatter {
    public static String dateformat1(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("MM/dd/yyyy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateformat2(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("dd-MMM-yyyy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateformat3(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("dd-MMM-yyyy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateformat4(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("dd-MMM-yy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
