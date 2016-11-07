package com.herocorp.infra.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles the Image Operations in the App.
 * Created by JitainSharma on 18/06/16.
 */
public class ImageHandler {

    private final String MAIN_DIR = "heroImages";
    private ContextWrapper cw;
    private static ImageHandler imageHandler;
    private static ContentResolver contentResolver;

    private ImageHandler(Context context) {
        cw = new ContextWrapper(context);
        contentResolver = context.getContentResolver();
    }

    public static ImageHandler getInstance(Context context) {

        if (imageHandler == null) {
            imageHandler = new ImageHandler(context);
        }

        return imageHandler;
    }

    public static ImageHandler getInstance() {
        return imageHandler;
    }

    /**
     * Check if file exists or not.
     *
     * @param fileName
     * @return
     */
    public boolean isFileExists(String fileName) {

        //Check if image is already being downloaded
        File directory = cw.getDir(MAIN_DIR, Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directory, fileName);
        return file.exists();
    }

    /**
     * Save the bitmap to the internal storage.
     *
     * @param bitmap
     * @param fileName
     */
    public void saveImage(Bitmap bitmap, String fileName) {

        File directory = cw.getDir(MAIN_DIR, Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directory, fileName);
        String extension = getMimeType(fileName);

        FileOutputStream ostream = null;
        try {
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(
                    (extension.equalsIgnoreCase("png")) ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG,
                    100, ostream);
        } catch (Exception e) {
            e.printStackTrace();
            file.delete();
        } finally {
            if (ostream != null) try {
                ostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load Bitmap from the given image name.
     *
     * @param imageName
     * @return
     * @throws Exception
     */
    public Bitmap loadImageFromStorage(String imageName) throws Exception {

        File path1 = cw.getDir(MAIN_DIR, Context.MODE_PRIVATE);
        File f = new File(path1, imageName);
        return BitmapFactory.decodeStream(new FileInputStream(f));
    }

    private static String getMimeType(String fileName) {
        String filenameArray[] = fileName.split("\\.");
        return filenameArray[filenameArray.length - 1];
    }
}
