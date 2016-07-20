package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public long addDiagnosisDict(DictDiagnosis dictDiagnosis) {
        SQLiteDatabase db = fsPhrDB.getWritableDatabase();
        db.beginTransaction();

        String name = dictDiagnosis.getName();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME, name);
        values.put(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE, name);
        long id = db.insertWithOnConflict(PhrSchemaContract.DictDiagnosisTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        if (id == -1){
            Cursor c = db.rawQuery("select _id from "+PhrSchemaContract.DictDiagnosisTable.TABLE_NAME +
                    " where "+PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME + "='" + name + "'", null);
            //Cursor c = fsPhrDB.query(PhrSchemaContract.DictDiagnosisTable.TABLE_NAME, new String[]{PhrSchemaContract.DictDiagnosisTable._ID}, PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME + "=?", new String[]{dictDiagnosis.getName()});
            c.moveToFirst();
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.DictDiagnosisTable._ID));
            c.close();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return id;
    }
}
