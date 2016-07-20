package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.service.IDiagnosisService;

import java.util.ArrayList;

/**
 * Created by 310078142 on 2015/10/20.
 */
public class DiagnosisServiceLocalImpl extends BaseServiceLocal implements IDiagnosisService{

    public DiagnosisServiceLocalImpl(Context context){
        super(context);
    }

    @Override
    public int addNewDiagnosis(Diagnosis diagnosis) {
        long ent_key = diagnosis.getEncounter_key();
        DictDiagnosis dictDiagnosis = diagnosis.getDiagnosis_dict();
        int indicator = diagnosis.getPrimaryIndicator();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY, dictDiagnosis.getKey());
        values.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY, ent_key);
        values.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_PRIMARY_INDICATOR, indicator);

        int id = (int) fsPhrDB.insert(PhrSchemaContract.DiagnosisTable.TABLE_NAME, values);

        return id;
    }

    @Override
    public ArrayList<Diagnosis> getDiagnosis(long encounter_key) {
        ArrayList<Diagnosis> diagnosisList = new ArrayList<>();
        String sql = "select "+
                PhrSchemaContract.DiagnosisTable.TABLE_NAME+"."+PhrSchemaContract.DiagnosisTable._ID +"," +
                PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME +"," +
                PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE+"," +
                PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY +
                " from " + PhrSchemaContract.DiagnosisTable.TABLE_NAME + "," +
                PhrSchemaContract.DictDiagnosisTable.TABLE_NAME +
                " where " + PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY +"=" +encounter_key +
                " and " + PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY +"=" + PhrSchemaContract.DictDiagnosisTable.TABLE_NAME+"._id";

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            Diagnosis diagnosis = new Diagnosis();

            DictDiagnosis dictDiagnosis = new DictDiagnosis();
            dictDiagnosis.setName(c.getString(c.getColumnIndex(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME)));
            dictDiagnosis.setCode(c.getString(c.getColumnIndex(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE)));
            dictDiagnosis.setKey(c.getInt(c.getColumnIndex(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY)));

            diagnosis.setDiagnosis_dict(dictDiagnosis);
            diagnosis.setEncounter_key(encounter_key);
            diagnosis.setDiagnosis_key(c.getInt(c.getColumnIndex(PhrSchemaContract.DiagnosisTable._ID)));

            diagnosisList.add(diagnosis);
            c.moveToNext();
        }

        c.close();
        fsPhrDB.closeDatabase();

        return diagnosisList;
    }

    @Override
    public ArrayList<Diagnosis> addDiagnosisList(long encounter_Key, ArrayList<String> diagnosisValues) {
        SQLiteDatabase db = fsPhrDB.getWritableDatabase();
        db.beginTransaction();

        ArrayList<Diagnosis> diagnosisList = new ArrayList<>();

        for (int i=0; i<diagnosisValues.size();i++){
            DictDiagnosis dictDiagnosis = new DictDiagnosis();
            Diagnosis diagnosis = new Diagnosis();

            String diagValue = diagnosisValues.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME, diagValue);
            contentValues.put(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE, diagValue);

            long key = db.insertWithOnConflict(PhrSchemaContract.DictDiagnosisTable.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            if (key == -1){
                Cursor c = db.rawQuery("select _id from "+PhrSchemaContract.DictDiagnosisTable.TABLE_NAME +
                        " where "+PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME + "='" + diagValue + "'", null);
                c.moveToFirst();
                key = c.getInt(c.getColumnIndex(PhrSchemaContract.DictDiagnosisTable._ID));
                c.close();
            }


            dictDiagnosis.setCode(diagValue);
            dictDiagnosis.setName(diagValue);
            dictDiagnosis.setKey(key);

            diagnosis.setDiagnosis_dict(dictDiagnosis);
            diagnosis.setEncounter_key(encounter_Key);

            ContentValues diagCV = new ContentValues();
            diagCV.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY, key);
            diagCV.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY, encounter_Key);
            long diag_key = db.insert(PhrSchemaContract.DiagnosisTable.TABLE_NAME, null, diagCV);

            diagnosis.setDiagnosis_key(diag_key);
            diagnosisList.add(diagnosis);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return diagnosisList;
    }
}
