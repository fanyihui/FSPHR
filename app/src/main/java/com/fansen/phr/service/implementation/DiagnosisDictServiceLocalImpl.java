package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.service.IDiagnosisDictService;

/**
 * Created by 310078142 on 2015/10/20.
 */
public class DiagnosisDictServiceLocalImpl extends BaseServiceLocal implements IDiagnosisDictService{
    public DiagnosisDictServiceLocalImpl(Context context){
        super(context);
    }

    @Override
    public int addDiagnosisDict(DictDiagnosis dictDiagnosis) {
        String name = dictDiagnosis.getName();
        Cursor c = fsPhrDB.query(PhrSchemaContract.DictDiagnosisTable.TABLE_NAME, new String[]{PhrSchemaContract.DictDiagnosisTable._ID}, PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME + "=?", new String[]{dictDiagnosis.getName()});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.DepartmentTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME, name);
            values.put(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE, name);
            id = (int) fsPhrDB.insert(PhrSchemaContract.DictDiagnosisTable.TABLE_NAME, values);
        }

        c.close();

        return id;
    }
}