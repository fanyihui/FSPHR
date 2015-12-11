package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.service.IDiagnosisService;

import java.util.ArrayList;
import java.util.List;

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
    public int addNewDiagnosis(long ent_key, Diagnosis diagnosis) {
        DictDiagnosis dictDiagnosis = diagnosis.getDiagnosis_dict();
        //int indicator = diagnosis.getPrimaryIndicator();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY, dictDiagnosis.getKey());
        values.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY, ent_key);
        //values.put(PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_PRIMARY_INDICATOR, indicator);

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
}
