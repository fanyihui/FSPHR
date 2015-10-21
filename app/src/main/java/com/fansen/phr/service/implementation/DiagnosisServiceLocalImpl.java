package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.service.IDiagnosisService;

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
    public List<Diagnosis> getDiagnosis(long encounter_key) {
        return null;
    }
}
