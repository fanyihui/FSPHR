package com.fansen.phr.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 310078142 on 2015/11/10.
 */
public class FileUtil {
    public static File createClinicalDocImageFile(Context context, long encounter_key) throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = encounter_key+"_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(null);

        File encounter_dir = new File(storageDir.getPath()+"/"+encounter_key);
        if(!encounter_dir.exists()){
            encounter_dir.mkdir();
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                encounter_dir      /* directory */
        );

        return image;
    }
}
