package com.fansen.phr.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static File createClinicalDocImageThumFile(String orginalFilePath) throws IOException {
        File originalFile = new File(orginalFilePath);
        if (!originalFile.exists()) {
            throw new IOException();
        }

        String fileName = getFileNameNoEx(originalFile.getName());
        String thumFileName = "thumbnail_" + fileName;

        File image = File.createTempFile(
                thumFileName,  /* prefix */
                ".jpg",         /* suffix */
                originalFile.getParentFile()      /* directory */
        );

        return image;
    }

    public static String setThumToFileName(String originalFilePath){

        int separatorIndex = originalFilePath.lastIndexOf(File.separator);
        String fileNameWithExt = (separatorIndex < 0) ? originalFilePath : originalFilePath.substring(separatorIndex + 1, originalFilePath.length());
        String fileFolderName = (separatorIndex < 0) ? originalFilePath : originalFilePath.substring(0,separatorIndex);

        String thumFilePath = fileFolderName + File.separator + "thumbnail_" +fileNameWithExt;

        return thumFilePath;
    }

    public static Bitmap getBitmap(String filePath){
        File file = new File(filePath);
        if (!file.exists()){
            return null;
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 1;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);

        return bitmap;
    }

    public static String saveBitmapToFile(Bitmap bitmap, File tempFile){
        try {
            // 将bitmap转为jpg文件保存
            FileOutputStream fileOut = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tempFile.getAbsolutePath();
    }

    public static void saveBitmapToFile(Bitmap bitmap, String filePath){
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileCopy(String oldFilePath,String newFilePath) throws IOException {
        //如果原文件不存在
        if(fileExists(oldFilePath) == false){
            return false;
        }
        //获得原文件流
        FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
        byte[] data = new byte[1024];
        //输出流
        FileOutputStream outputStream =new FileOutputStream(new File(newFilePath));
        //开始处理流
        while (inputStream.read(data) != -1) {
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();
        return true;
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static byte[] decodeBitmapToBytes(Bitmap bitmap){
        if (bitmap == null){
            return null;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public static Bitmap encodeBytesToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
