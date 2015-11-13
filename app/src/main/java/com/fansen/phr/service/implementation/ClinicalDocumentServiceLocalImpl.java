package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.db.FsPhrDB;
import com.fansen.phr.entity.ClinicalDocument;
import com.fansen.phr.entity.Physician;
import com.fansen.phr.service.IClinicalDocumentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/10.
 */
public class ClinicalDocumentServiceLocalImpl extends BaseServiceLocal implements IClinicalDocumentService{
    public ClinicalDocumentServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public int addClinicalDocument(long ent_key, ClinicalDocument clinicalDocument) {
        if (clinicalDocument == null){
            return -1;
        }

        String imagePath = clinicalDocument.getCaptureImageUri();
        String thumbnailImagePath = clinicalDocument.getThumbnailImageUri();
        String type = clinicalDocument.getDocumentType();
        String creatingDate = clinicalDocument.getCreatingDate();
        Physician authentication = clinicalDocument.getLegalAuthentication();
        String authDate = clinicalDocument.getAuthenticationDateTime();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_ENT_KEY, ent_key);
        values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_IMAGE_URI, imagePath);
        values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_THUMBNAIL_IMAGE_URI, thumbnailImagePath);
        values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_TYPE, type);
        if(authentication != null){
            values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_LEGAL_AUTHENTICATION_KEY, authentication.getPhysicianKey());
            values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_AUTHENTICATION_DATE, authDate);
        }
        values.put(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_CREATING_DATE, creatingDate);

        int cd_key = (int) fsPhrDB.insert(PhrSchemaContract.ClinicalDocumentTable.TABLE_NAME, values);

        return cd_key;
    }

    @Override
    public List<ClinicalDocument> getClinicalDocuments(long ent_key, String documentType) {
        List<ClinicalDocument> clinicalDocuments = new ArrayList<>();

        String sql = "select "+
                PhrSchemaContract.ClinicalDocumentTable.TABLE_NAME+"."+"_id"+ "," +
                PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_IMAGE_URI + "," +
                PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_THUMBNAIL_IMAGE_URI + "," +
                PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_TYPE + "," +
                PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_CREATING_DATE +
                " from " +
                PhrSchemaContract.ClinicalDocumentTable.TABLE_NAME +
                " where " +
                PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_TYPE + "='" + documentType+"'" +
                " and "+PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_ENT_KEY + "=" + ent_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            ClinicalDocument clinicalDocument = new ClinicalDocument();
            clinicalDocument.setDocumentType(c.getString(c.getColumnIndex(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_TYPE)));
            clinicalDocument.setCaptureImageUri(c.getString(c.getColumnIndex(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_IMAGE_URI)));
            clinicalDocument.setThumbnailImageUri(c.getString(c.getColumnIndex(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_THUMBNAIL_IMAGE_URI)));
            clinicalDocument.set_id(c.getInt(c.getColumnIndex(PhrSchemaContract.ClinicalDocumentTable._ID)));
            clinicalDocument.setCreatingDate(c.getString(c.getColumnIndex(PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_CREATING_DATE)));

            clinicalDocuments.add(clinicalDocument);

            c.moveToNext();
        }

        c.close();

        fsPhrDB.closeDatabase();

        return clinicalDocuments;
    }

    @Override
    public void updateClinicalDocument(ClinicalDocument clinicalDocument) {

    }
}
