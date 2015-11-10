package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.db.FsPhrDB;
import com.fansen.phr.entity.ChiefComplaint;
import com.fansen.phr.service.IChiefComplaintService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/2.
 */
public class ChiefComplaintServiceLocalImpl extends BaseServiceLocal implements IChiefComplaintService{
    public ChiefComplaintServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public int addComplaint(long encounter_key, ChiefComplaint chiefComplaint) {
        String symptom = chiefComplaint.getSymptom();
        String duration = chiefComplaint.getDuration();
        String unit = chiefComplaint.getDuration_unit();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_COMPLAINT_ENT_KEY, encounter_key);
        values.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_SYMPTOM, symptom);
        values.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION, duration);
        values.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION_UNIT, unit);

        int id = (int) fsPhrDB.insert(PhrSchemaContract.ChiefComplaintTable.TABLE_NAME, values);

        return id;
    }

    @Override
    public void addComplaints(long encounter_key, List<ChiefComplaint> complaints) {

    }

    @Override
    public List<ChiefComplaint> getComplaints(long encounter_key) {
        List<ChiefComplaint> chiefComplaints = new ArrayList<>();
        String sql = "select "+PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_COMPLAINT_ENT_KEY + "," +
                PhrSchemaContract.ChiefComplaintTable._ID +"," +
                PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_SYMPTOM +"," +
                PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION+"," +
                PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION_UNIT +
                " from " + PhrSchemaContract.ChiefComplaintTable.TABLE_NAME +
                " where " + PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_COMPLAINT_ENT_KEY +"=" +encounter_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            ChiefComplaint chiefComplaint = new ChiefComplaint();
            chiefComplaint.setKey(c.getInt(c.getColumnIndex(PhrSchemaContract.ChiefComplaintTable._ID)));
            chiefComplaint.setSymptom(c.getString(c.getColumnIndex(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_SYMPTOM)));
            chiefComplaint.setDuration(c.getString(c.getColumnIndex(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION)));
            chiefComplaint.setDuration_unit(c.getString(c.getColumnIndex(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION_UNIT)));

            chiefComplaints.add(chiefComplaint);

            c.moveToNext();
        }

        c.close();
        fsPhrDB.closeDatabase();

        return chiefComplaints;
    }

    public void updateChiefComplaint(ChiefComplaint chiefComplaint){
        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION_UNIT, chiefComplaint.getDuration_unit());
        cv.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION, chiefComplaint.getDuration());
        cv.put(PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_SYMPTOM, chiefComplaint.getSymptom());

        String[] args = {String.valueOf(chiefComplaint.getKey())};

        fsPhrDB.update(PhrSchemaContract.ChiefComplaintTable.TABLE_NAME, cv, "_id=?", args);
    }
}
