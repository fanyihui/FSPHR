package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.service.IDiagnosticImageService;
import com.fansen.phr.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/19.
 */
public class DiagnosticImageServiceLocalImpl extends BaseServiceLocal implements IDiagnosticImageService{
    public DiagnosticImageServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public void addDiagnosticImages(int dir_key, List<DiagnosticImage> diagnosticImages) {
        for (int i=0; i<diagnosticImages.size(); i++){
            DiagnosticImage diagnosticImage = diagnosticImages.get(i);

            //if the id>0 means the image is existed, so don't need to save to database
            if (diagnosticImage.get_id()>0){
                continue;
            }

            String imagePath = diagnosticImage.getCaptureImageUri();
            String thumPath = diagnosticImage.getThumbnailImageUri();
            String date = diagnosticImage.getCreatingDate();

            ContentValues cv = new ContentValues();
            cv.put(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_DIR_KEY, dir_key);
            cv.put(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_IMAGE_URI, imagePath);
            cv.put(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_THUMBNAIL_IMAGE_URI, thumPath);
            cv.put(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_CREATING_DATE, date);

            int id = (int) fsPhrDB.insert(PhrSchemaContract.DiagnosticImageTable.TABLE_NAME, cv);

            diagnosticImage.set_id(id);
        }
    }

    @Override
    public List<DiagnosticImage> getDiagnosticImages(int dir_key) {
        List<DiagnosticImage> diagnosticImages = new ArrayList<>();
        String sql = "select " +
                PhrSchemaContract.DiagnosticImageTable._ID + "," +
                PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_CREATING_DATE + "," +
                PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_THUMBNAIL_IMAGE_URI + "," +
                PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_IMAGE_URI +
                " from " + PhrSchemaContract.DiagnosticImageTable.TABLE_NAME +
                " where " + PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_DIR_KEY +"="+dir_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            DiagnosticImage diagnosticImage = new DiagnosticImage();

            int id = c.getInt(c.getColumnIndex(PhrSchemaContract.DiagnosticImageTable._ID));
            String date = c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_CREATING_DATE));
            String imageUri = c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_IMAGE_URI));
            String thumUri = c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_THUMBNAIL_IMAGE_URI));
            Bitmap bitmap = FileUtil.getBitmap(thumUri);

            diagnosticImage.set_id(id);
            diagnosticImage.setCreatingDate(date);
            diagnosticImage.setCaptureImageUri(imageUri);
            diagnosticImage.setThumbnailImageUri(thumUri);
            diagnosticImage.setThumbnailImage(FileUtil.decodeBitmapToBytes(bitmap));

            diagnosticImages.add(diagnosticImage);

            c.moveToNext();
        }

        return diagnosticImages;
    }
}
