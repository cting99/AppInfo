package cting.com.appinfo.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cting on 2018/2/9.
 */

public class FileHelper {


    private static final String TAG="cting/appin/filehelper";

    public static boolean exportToFile(String content, String fileName) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            out.write(content.getBytes());
            return true;
        } catch (IOException e) {
            Log.w(TAG, "exportToFile,exception1: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                Log.w(TAG, "exportToFile,exception2: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
