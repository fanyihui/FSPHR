package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.BodyPartDef;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.DiagnosticImagingReport;
import com.fansen.phr.entity.RequestedProcedureTypeDef;
import com.fansen.phr.service.IDiagnosticImageService;
import com.fansen.phr.service.IDiagnosticImagingReportService;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/16.
 */
public class DiagnosticImagingReportServiceLocalImpl extends BaseServiceLocal implements IDiagnosticImagingReportService {
    private IDiagnosticImageService diagnosticImageService;

    public DiagnosticImagingReportServiceLocalImpl(Context context) {
        super(context);
        diagnosticImageService = new DiagnosticImageServiceLocalImpl(context);
    }

    @Override
    public List<DiagnosticImagingReport> getDiagnosticImagingReports(long ent_key) {
        List<DiagnosticImagingReport> diagnosticImagingReportList = new ArrayList<>();

        String sql = "select "+
                PhrSchemaContract.DiagnosticImagingReportTable.TABLE_NAME+"."+"_id"+ "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_MODALITY + "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RECOMMENDATION + "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RESULT + "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_FINDINGS + "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RPDEF_KEY + "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY + "," +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_DATE + "," +
                PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME+"."+PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME + " as rp_name," +
                PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME+"."+PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE + " as rp_code," +
                PhrSchemaContract.BodypartDefTable.TABLE_NAME+"."+PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME + " as body_name," +
                PhrSchemaContract.BodypartDefTable.TABLE_NAME+"."+PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE + " as body_code" +
                " from " +
                PhrSchemaContract.DiagnosticImagingReportTable.TABLE_NAME + "," +
                PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME + "," +
                PhrSchemaContract.BodypartDefTable.TABLE_NAME +
                " where " +
                PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY + "=" + PhrSchemaContract.BodypartDefTable.TABLE_NAME+"."+PhrSchemaContract.BodypartDefTable._ID +
                " and " + PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RPDEF_KEY + "=" + PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME+"."+PhrSchemaContract.RequestedProcedureTypeDefTable._ID +
                " and " + PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_ENT_KEY + "=" + ent_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            DiagnosticImagingReport dir = new DiagnosticImagingReport();

            int id = c.getInt(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable._ID));

            BodyPartDef bodyPartDef = new BodyPartDef();
            bodyPartDef.set_id(c.getInt(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY)));
            bodyPartDef.setName(c.getString(c.getColumnIndex("body_name")));
            bodyPartDef.setCode(c.getString(c.getColumnIndex("body_code")));

            RequestedProcedureTypeDef rpType = new RequestedProcedureTypeDef();
            rpType.set_id(c.getInt(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RPDEF_KEY)));
            rpType.setName(c.getString(c.getColumnIndex("rp_name")));
            rpType.setCode(c.getString(c.getColumnIndex("rp_code")));

            dir.setBodypart(bodyPartDef);
            dir.setRequestedProcedureTypeDef(rpType);
            dir.setModality(c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_MODALITY)));
            dir.set_id(id);
            dir.setFindings(c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_FINDINGS)));
            dir.setRecommendation(c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RECOMMENDATION)));
            dir.setResult(c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RESULT)));
            dir.setRequestedProcedureDate(TimeFormat.format("yyyyMMdd", c.getString(c.getColumnIndex(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_DATE))));

            List<DiagnosticImage> diagnosticImages = diagnosticImageService.getDiagnosticImages(id);
            dir.setDiagnosticImages(diagnosticImages);

            //TODO add code here to initial ImagingObservation for the diagnosis in future.

            diagnosticImagingReportList.add(dir);

            c.moveToNext();
        }

        return diagnosticImagingReportList;
    }

    @Override
    public int addDiagnosticImagingReport(long ent_key, DiagnosticImagingReport diagnosticImagingReport) {
        if (diagnosticImagingReport == null){
            return -1;
        }

        String findings = diagnosticImagingReport.getFindings();
        String result = diagnosticImagingReport.getResult();
        String recommendation = diagnosticImagingReport.getRecommendation();
        int rp_type_key = diagnosticImagingReport.getRequestedProcedureTypeDef().get_id();
        String date = TimeFormat.parseDate(diagnosticImagingReport.getRequestedProcedureDate(), "yyyyMMdd");
        String modality = diagnosticImagingReport.getModality();
        int body_part_key = diagnosticImagingReport.getBodypart().get_id();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_ENT_KEY, ent_key);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RPDEF_KEY, rp_type_key);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY, body_part_key);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_DATE, date);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_FINDINGS, findings);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_MODALITY, modality);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RECOMMENDATION, recommendation);
        values.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RESULT, result);

        int _id = (int) fsPhrDB.insert(PhrSchemaContract.DiagnosticImagingReportTable.TABLE_NAME, values);

        return _id;
    }

    @Override
    public void updateDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport) {
        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY, diagnosticImagingReport.getBodypart().get_id());
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RPDEF_KEY, diagnosticImagingReport.getRequestedProcedureTypeDef().get_id());
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RESULT, diagnosticImagingReport.getResult());
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RECOMMENDATION, diagnosticImagingReport.getRecommendation());
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_FINDINGS, diagnosticImagingReport.getFindings());
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_MODALITY, diagnosticImagingReport.getModality());
        cv.put(PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_DATE, TimeFormat.parseDate(diagnosticImagingReport.getRequestedProcedureDate(), "yyyyMMdd"));

        String[] args = {String.valueOf(diagnosticImagingReport.get_id())};

        fsPhrDB.update(PhrSchemaContract.DiagnosticImagingReportTable.TABLE_NAME, cv, "_id=?", args);
    }

    @Override
    public void deleteDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport) {

    }
}
