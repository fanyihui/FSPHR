package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.LabObservation;
import com.fansen.phr.entity.LabReport;
import com.fansen.phr.entity.OrderCodeDef;
import com.fansen.phr.entity.SpecimenTypeCodeDef;
import com.fansen.phr.service.ILabObservationService;
import com.fansen.phr.service.ILabReportRefImageService;
import com.fansen.phr.service.ILabReportService;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by Yihui on 2015/12/1.
 */
public class LabReportServiceLocalImpl extends BaseServiceLocal implements ILabReportService{
    private ILabReportRefImageService labReportRefImageService;
    private ILabObservationService labObservationService;

    public LabReportServiceLocalImpl(Context context) {
        super(context);

        labObservationService = new LabObservationServiceLocalImpl(context);
        labReportRefImageService = new LabReportRefImageServiceLocalImpl(context);
    }

    @Override
    public int addNewLabReport(long ent_key, LabReport labReport) {
        if (labReport == null || ent_key <=0 ){
            return -1;
        }

        int orderCodeKey = labReport.getOrderCode().get_id();
        int specimenCodeKey = labReport.getSpecimenTypeCode().get_id();
        String collectingDate = TimeFormat.parseDate(labReport.getSpecimenCollectedDate(), "yyyyMMdd");
        String reportingDate = TimeFormat.parseDate(labReport.getReportDate(), "yyyyMMdd");

        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ENT_KEY, ent_key);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY, orderCodeKey);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_SPECIMEN_KEY, specimenCodeKey);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_COLLECTED_DATE, collectingDate);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORTING_DATE, reportingDate);

        int id = (int) fsPhrDB.insert(PhrSchemaContract.LabReportTable.TABLE_NAME, cv);

        return id;
    }

    @Override
    public void updateLabReport(LabReport labReport) {
        int id = labReport.get_id();

        int orderCodeKey = labReport.getOrderCode().get_id();
        int specimenCodeKey = labReport.getSpecimenTypeCode().get_id();
        String collectingDate = TimeFormat.parseDate(labReport.getSpecimenCollectedDate(), "yyyyMMdd");
        String reportingDate = TimeFormat.parseDate(labReport.getReportDate(), "yyyyMMdd");

        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY, orderCodeKey);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_SPECIMEN_KEY, specimenCodeKey);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_COLLECTED_DATE, collectingDate);
        cv.put(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORTING_DATE, reportingDate);

        String[] args = {String.valueOf(id)};

        fsPhrDB.update(PhrSchemaContract.LabReportTable.TABLE_NAME, cv, "_id=?", args);
    }

    @Override
    public ArrayList<LabReport> getLabReports(long ent_key) {
        ArrayList<LabReport> labReportList = new ArrayList<>();

        String sql = "select " +
                PhrSchemaContract.LabReportTable.TABLE_NAME +"._id," +
                PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORTING_DATE + "," +
                PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_COLLECTED_DATE + "," +
                PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY +"," +
                PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_SPECIMEN_KEY +"," +
                PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_NAME +" as specimen_name," +
                PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_CODE + " as specimen_code," +
                PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_NAME + " as order_name," +
                PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_CODE + " as order_code " +
                " from " +
                PhrSchemaContract.LabReportTable.TABLE_NAME +","+
                PhrSchemaContract.OrderCodeDefTable.TABLE_NAME +","+
                PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME +
                " where " +
                PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ENT_KEY + "=" +ent_key +
                " and "+ PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_SPECIMEN_KEY + "=" + PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME +"._id"+
                " and "+ PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY + "=" + PhrSchemaContract.OrderCodeDefTable.TABLE_NAME+"._id";

        Cursor c = fsPhrDB.rawQuery(sql);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            LabReport labReport = new LabReport();

            int id = c.getInt(c.getColumnIndex("_id"));
            String reportingDate = c.getString(c.getColumnIndex(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORTING_DATE));
            String collectingDate = c.getString(c.getColumnIndex(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_COLLECTED_DATE));
            int orderCodeKey = c.getInt(c.getColumnIndex(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY));
            int specimenCodeKey = c.getInt(c.getColumnIndex(PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_SPECIMEN_KEY));
            String order_name = c.getString(c.getColumnIndex("order_name"));
            String order_code = c.getString(c.getColumnIndex("order_code"));
            String specimen_name = c.getString(c.getColumnIndex("specimen_name"));
            String specimen_code = c.getString(c.getColumnIndex("specimen_code"));

            OrderCodeDef orderCodeDef = new OrderCodeDef();
            orderCodeDef.set_id(orderCodeKey);
            orderCodeDef.setName(order_name);
            orderCodeDef.setCode(order_code);

            SpecimenTypeCodeDef specimenTypeCodeDef = new SpecimenTypeCodeDef();
            specimenTypeCodeDef.set_id(specimenCodeKey);
            specimenTypeCodeDef.setName(specimen_name);
            specimenTypeCodeDef.setCode(specimen_code);

            ArrayList<LabObservation> labObservations = labObservationService.getLabObservations(id);
            ArrayList<DiagnosticImage> refImages = labReportRefImageService.getLabReportRefImages(id);

            labReport.set_id(id);
            labReport.setOrderCode(orderCodeDef);
            labReport.setSpecimenTypeCode(specimenTypeCodeDef);
            labReport.setReportDate(TimeFormat.format("yyyyMMdd", reportingDate));
            labReport.setSpecimenCollectedDate(TimeFormat.format("yyyyMMdd", collectingDate));
            labReport.setObservations(labObservations);
            labReport.setReferenceImages(refImages);

            labReportList.add(labReport);

            c.moveToNext();
        }

        return labReportList;
    }
}
