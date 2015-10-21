package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Department;
import com.fansen.phr.service.IDepartmentService;

/**
 * Created by 310078142 on 2015/10/20.
 */
public class DepartmentServiceLocalImpl extends BaseServiceLocal implements IDepartmentService{
    //private FsPhrDB fsPhrDB = null;

    public DepartmentServiceLocalImpl(Context context){
        super(context);
    }

    @Override
    public long addDepartment(Department department) {
        long key = department.getDepartment_key();
        String name = department.getName();

        if(key > 0){
            return key;
        } else {
            Cursor c = fsPhrDB.query(PhrSchemaContract.DepartmentTable.TABLE_NAME, new String[]{PhrSchemaContract.DepartmentTable._ID}, PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME + "=?", new String[]{department.getName()});
            c.moveToFirst();

            long id = 0;

            if(c.getCount()>0)
                id = c.getLong(c.getColumnIndex(PhrSchemaContract.DepartmentTable._ID));
            else{
                ContentValues values = new ContentValues();
                values.put(PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME, department.getName());
                id = fsPhrDB.insert(PhrSchemaContract.DepartmentTable.TABLE_NAME, values);
            }

            c.close();

            return id;
        }
    }

    @Override
    public Department getDepartmentByKey(long key) {
        return null;
    }
}
