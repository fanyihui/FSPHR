package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.service.IMedicationDictService;

/**
 * Created by 310078142 on 2015/11/4.
 */
public class MedicationDictServiceLocalImpl extends BaseServiceLocal implements IMedicationDictService{
    public MedicationDictServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public int addMedicationDict(MedicationDict medicationDict) {
        String name = medicationDict.getName();
        String code = medicationDict.getCode();
        String spec = medicationDict.getSpec();

        String sql = "select "+
                PhrSchemaContract.MedicationDictTable._ID+
                " from "+PhrSchemaContract.MedicationDictTable.TABLE_NAME+
                " where "+
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME +"='"+ name +"'"+
                " and " +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC +"='"+ spec+"'";

        Cursor c = fsPhrDB.rawQuery(sql);
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0) {
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.MedicationDictTable._ID));
        } else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME, name);
            values.put(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE, code);
            values.put(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC, spec);

            id = (int) fsPhrDB.insert(PhrSchemaContract.MedicationDictTable.TABLE_NAME, values);
        }

        c.close();
        fsPhrDB.closeDatabase();

        return id;
    }
}
