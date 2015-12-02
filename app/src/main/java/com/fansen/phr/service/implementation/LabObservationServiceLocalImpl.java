package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.LabObservation;
import com.fansen.phr.entity.ObservationDef;
import com.fansen.phr.service.ILabObservationService;
import com.fansen.phr.service.ITerminologyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/12/1.
 */
public class LabObservationServiceLocalImpl extends BaseServiceLocal implements ILabObservationService {
    ITerminologyService terminologyService;

    public LabObservationServiceLocalImpl(Context context) {
        super(context);
        terminologyService = new TermilologyServiceLocalImpl(context);
    }

    @Override
    public void addLabObservation(int labReportId, List<LabObservation> labObservations) {
        for (int i=0; i< labObservations.size(); i++){
            LabObservation labObservation = labObservations.get(i);

            //if the id>0 means the observation is existed, so don't need to save to database
            if (labObservation.get_id()>0){
                continue;
            }

            ObservationDef observationDef = labObservation.getObservationDef();
            int observationDefKey = terminologyService.addObservationCode(observationDef);
            observationDef.set_id(observationDefKey);

            String value = labObservation.getValue();
            String unit = labObservation.getUnit();

            ContentValues cv = new ContentValues();
            cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_LAB_REPORT_KEY, labReportId);
            cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY, observationDefKey);
            cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_VALUE, value);
            cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_UNIT, unit);

            int id = (int) fsPhrDB.insert(PhrSchemaContract.LabObservationTable.TABLE_NAME, cv);

            labObservation.set_id(id);
        }
    }

    @Override
    public void updateLabObservation(int labReportId, List<LabObservation> labObservations) {
        for (int i=0; i< labObservations.size(); i++){
            LabObservation labObservation = labObservations.get(i);

            ObservationDef observationDef = labObservation.getObservationDef();
            int observationDefKey = terminologyService.addObservationCode(observationDef);
            observationDef.set_id(observationDefKey);

            String value = labObservation.getValue();
            String unit = labObservation.getUnit();

            int id = labObservation.get_id();

            //if the id>0 means the observation is existed, so update to database
            if (id>0){
                ContentValues cv = new ContentValues();
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_LAB_REPORT_KEY, labReportId);
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY, observationDefKey);
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_VALUE, value);
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_UNIT, unit);

                String[] args = {String.valueOf(id)};

                fsPhrDB.update(PhrSchemaContract.LabObservationTable.TABLE_NAME, cv, "_id=?", args);
            } else {
                ContentValues cv = new ContentValues();
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_LAB_REPORT_KEY, labReportId);
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY, observationDefKey);
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_VALUE, value);
                cv.put(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_UNIT, unit);

                id = (int) fsPhrDB.insert(PhrSchemaContract.LabObservationTable.TABLE_NAME, cv);

                labObservation.set_id(id);
            }
        }
    }

    @Override
    public ArrayList<LabObservation> getLabObservations(int labReportId) {
        ArrayList<LabObservation> labObservations = new ArrayList<>();

        String sql = "select "+
                PhrSchemaContract.LabObservationTable.TABLE_NAME+"._id,"+
                PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY +"," +
                PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_VALUE +"," +
                PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_UNIT + "," +
                PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME + "," +
                PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE + "," +
                PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NORMAL_RANGE +
                " from "+
                PhrSchemaContract.LabObservationTable.TABLE_NAME +"," +
                PhrSchemaContract.ObservationDefTable.TABLE_NAME +
                " where " +
                PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_LAB_REPORT_KEY + "=" + labReportId +
                " and "+PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY +"=" + PhrSchemaContract.ObservationDefTable.TABLE_NAME+"._id";

        Cursor c = fsPhrDB.rawQuery(sql);
        c.moveToFirst();

        while (!c.isAfterLast()){
            LabObservation labObservation = new LabObservation();

            int id = c.getInt(c.getColumnIndex("_id"));
            int code_key = c.getInt(c.getColumnIndex(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY));
            String value = c.getString(c.getColumnIndex(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_VALUE));
            String unit = c.getString(c.getColumnIndex(PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_UNIT));
            String codeName = c.getString(c.getColumnIndex(PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME));
            String code = c.getString(c.getColumnIndex(PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE));
            String range = c.getString(c.getColumnIndex(PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NORMAL_RANGE));

            ObservationDef observationDef = new ObservationDef();
            observationDef.set_id(code_key);
            observationDef.setCode(code);
            observationDef.setName(codeName);
            observationDef.setNormalRange(range);

            labObservation.setObservationDef(observationDef);
            labObservation.setValue(value);
            labObservation.setUnit(unit);
            labObservation.set_id(id);

            labObservations.add(labObservation);

            c.moveToNext();
        }


        return labObservations;
    }
}
