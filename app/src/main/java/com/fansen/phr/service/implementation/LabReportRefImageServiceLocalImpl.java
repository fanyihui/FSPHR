package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.LabObservation;
import com.fansen.phr.entity.ObservationDef;
import com.fansen.phr.service.ILabReportRefImageService;
import com.fansen.phr.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/12/1.
 */
public class LabReportRefImageServiceLocalImpl extends BaseServiceLocal implements ILabReportRefImageService{
    public LabReportRefImageServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public void addLabReportRefImages(int labReportID, List<DiagnosticImage> refImages) {
        for (int i=0; i< refImages.size(); i++){
            DiagnosticImage diagnosticImage = refImages.get(i);

            //if the id>0 means the image is existed, so don't need to save to database
            if (diagnosticImage.get_id()>0){
                continue;
            }

            String imagePath = diagnosticImage.getCaptureImageUri();
            String thumPath = diagnosticImage.getThumbnailImageUri();
            String date = diagnosticImage.getCreatingDate();

            ContentValues cv = new ContentValues();
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_KEY, labReportID);
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_REF_IMAGE_URI, imagePath);
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_THUMBNAIL_IMAGE_URI, thumPath);
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_CREATING_DATE, date);


            int id = (int) fsPhrDB.insert(PhrSchemaContract.LabReportReferenceImageTable.TABLE_NAME, cv);

            diagnosticImage.set_id(id);
        }
    }

    @Override
    public void updateLabReportRefImages(int labReportID, List<DiagnosticImage> refImages) {
        for (int i=0; i< refImages.size(); i++){
            DiagnosticImage diagnosticImage = refImages.get(i);

            String imagePath = diagnosticImage.getCaptureImageUri();
            String thumPath = diagnosticImage.getThumbnailImageUri();
            String date = diagnosticImage.getCreatingDate();

            int id = diagnosticImage.get_id();

            //if the id>0 means the image is existed, so don't need to save to database
            if (id>0){
                continue;
            }

            ContentValues cv = new ContentValues();
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_KEY, labReportID);
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_REF_IMAGE_URI, imagePath);
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_THUMBNAIL_IMAGE_URI, thumPath);
            cv.put(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_CREATING_DATE, date);

            id = (int) fsPhrDB.insert(PhrSchemaContract.LabReportReferenceImageTable.TABLE_NAME, cv);

            diagnosticImage.set_id(id);
        }
    }

    @Override
    public ArrayList<DiagnosticImage> getLabReportRefImages(int labReportId) {
        ArrayList<DiagnosticImage> refImages = new ArrayList<>();

        String sql = "select "+
                PhrSchemaContract.LabReportReferenceImageTable.TABLE_NAME+"._id,"+
                PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_CREATING_DATE +"," +
                PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_THUMBNAIL_IMAGE_URI +"," +
                PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_REF_IMAGE_URI +
                " from "+
                PhrSchemaContract.LabReportReferenceImageTable.TABLE_NAME +
                " where " +
                PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_KEY + "=" + labReportId;

        Cursor c = fsPhrDB.rawQuery(sql);
        c.moveToFirst();

        while (!c.isAfterLast()){
            DiagnosticImage image = new DiagnosticImage();

            int id = c.getInt(c.getColumnIndex("_id"));
            String imagePath = c.getString(c.getColumnIndex(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_REF_IMAGE_URI));
            String thumPath = c.getString(c.getColumnIndex(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_THUMBNAIL_IMAGE_URI));
            Bitmap bitmap = FileUtil.getBitmap(thumPath);
            String date = c.getString(c.getColumnIndex(PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_CREATING_DATE));

            image.set_id(id);
            image.setCaptureImageUri(imagePath);
            image.setThumbnailImageUri(thumPath);
            image.setThumbnailImage(FileUtil.decodeBitmapToBytes(bitmap));

            refImages.add(image);

            c.moveToNext();
        }


        return refImages;
    }
}
