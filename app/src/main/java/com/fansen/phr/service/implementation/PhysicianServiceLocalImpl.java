package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Physician;
import com.fansen.phr.service.IPhysicianService;

import java.lang.ref.SoftReference;

/**
 * Created by 310078142 on 2015/10/27.
 */
public class PhysicianServiceLocalImpl extends BaseServiceLocal implements IPhysicianService{
    public PhysicianServiceLocalImpl(Context context){
        super(context);
    }

    @Override
    public int addPhysician(Physician physician) {
        String name = physician.getPhysicianName();
        String employeeNo = physician.getEmployeeNo();
        Cursor c = fsPhrDB.query(PhrSchemaContract.PhysicianTable.TABLE_NAME, new String[]{PhrSchemaContract.PhysicianTable._ID}, PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME + "=?", new String[]{name});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.PhysicianTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME, name);
            values.put(PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_ID, employeeNo);
            id = (int) fsPhrDB.insert(PhrSchemaContract.PhysicianTable.TABLE_NAME, values);
        }

        c.close();

        return id;
    }
}
