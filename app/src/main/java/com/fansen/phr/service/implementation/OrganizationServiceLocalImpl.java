package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.service.IOrganizationService;

/**
 * Created by 310078142 on 2015/10/19.
 */
public class OrganizationServiceLocalImpl extends BaseServiceLocal implements IOrganizationService{
    //private FsPhrDB fsPhrDB = null;

    public OrganizationServiceLocalImpl(Context context){
        //fsPhrDB = FsPhrDB.getInstance(context);
        super(context);
    }

    @Override
    public long addOrganization(Organization organization) {
        long key = organization.getOrg_key();
        String name = organization.getOrg_name();

        if(key > 0){
            return key;
        } else {
            Cursor c = fsPhrDB.query(PhrSchemaContract.OrganizationTable.TABLE_NAME, new String[]{PhrSchemaContract.OrganizationTable._ID}, PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME + "=?", new String[]{organization.getOrg_name()});
            c.moveToFirst();

            long id = 0;

            if(c.getCount()>0)
                id = c.getLong(c.getColumnIndex(PhrSchemaContract.OrganizationTable._ID));
            else{
                ContentValues values = new ContentValues();
                values.put(PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME, organization.getOrg_name());
                id = fsPhrDB.insert(PhrSchemaContract.OrganizationTable.TABLE_NAME, values);
            }

            c.close();

            return id;
        }
    }

    @Override
    public Organization getOrgbyKey(int key) {
        return null;
    }
}
