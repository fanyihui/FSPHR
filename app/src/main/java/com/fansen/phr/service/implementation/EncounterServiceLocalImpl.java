package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.db.FsPhrDB;
import com.fansen.phr.entity.Department;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.utils.TimeFormat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/10/12.
 */
public class EncounterServiceLocalImpl extends BaseServiceLocal implements IEncounterService{
    //private FsPhrDB fsPhrDB = null;

    public EncounterServiceLocalImpl(Context context) {
        //fsPhrDB = FsPhrDB.getInstance(context);
        super(context);
    }

    @Override
    public List<Encounter> getAllEncounters() {
        List<Encounter> encounters = new ArrayList<Encounter>();

        String ent_sql = "select " + PhrSchemaContract.EncounterTable.TABLE_NAME+"."+PhrSchemaContract.EncounterTable._ID + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + "," +
                PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME + "," +
                PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME + "," +
                PhrSchemaContract.OrganizationTable.TABLE_NAME + "," +
                PhrSchemaContract.DepartmentTable.TABLE_NAME +
                " where encounter.org_key=organization._id and encounter.dpt_key=department._id";

        Cursor c = fsPhrDB.rawQuery(ent_sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            int ent_key = c.getInt(c.getColumnIndex("_id"));

            String sql = "Select "+
                    PhrSchemaContract.DiagnosisTable._ID + "," +
                    PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY + "," +
                    PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_PRIMARY_INDICATOR + "," +
                    PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE + "," +
                    PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME + " from " +
                    PhrSchemaContract.DiagnosisTable.TABLE_NAME + "," + PhrSchemaContract.DictDiagnosisTable.TABLE_NAME + " where " +
                    PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY +"=" + PhrSchemaContract.DictDiagnosisTable._ID +" and " +
                    PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY +"=" + ent_key;

            Cursor c1 = fsPhrDB.rawQuery(sql);

            List<Diagnosis> diagnosisList = new ArrayList<>();

            c1.moveToFirst();
            if (!c1.isAfterLast()) {
                Diagnosis diagnosis = new Diagnosis();
                DictDiagnosis dict = new DictDiagnosis();
                dict.setName(c1.getString(c1.getColumnIndex("name")));
                dict.setCode(c1.getString(c1.getColumnIndex("code")));

                diagnosis.setEncounter_key(ent_key);
                diagnosis.setPrimaryIndicator(c1.getInt(c1.getColumnIndex("primary_indicator")));
                diagnosis.setDiagnosis_dict(dict);

                diagnosisList.add(diagnosis);

                c1.moveToNext();
            }

            c1.close();

            Organization org = new Organization();
            Department dept = new Department();
            Encounter encounter = new Encounter();

            org.setOrg_key(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY)));
            org.setOrg_name(c.getString(c.getColumnIndex(PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME)));

            dept.setDepartment_key(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY)));
            dept.setName(c.getString(c.getColumnIndex(PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME)));


            encounter.setDepartment(dept);
            encounter.setOrg(org);
            encounter.setAdmit_date(TimeFormat.format("yyyyMMdd", c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE))));
            encounter.setEncounter_key(ent_key);
            encounter.setDiagnosis(diagnosisList);

            encounters.add(encounter);

            c.moveToNext();
        }

        c.close();

        fsPhrDB.closeDatabase();

        return encounters;
    }

    @Override
    public long addNewEncounter(Encounter encounter) {
        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE, TimeFormat.parseDate(encounter.getAdmit_date(), "yyyyMMdd"));
        values.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY, encounter.getOrg().getOrg_key());
        values.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY, encounter.getDepartment().getDepartment_key());

        long encounter_key = fsPhrDB.insert(PhrSchemaContract.EncounterTable.TABLE_NAME, values);

        return encounter_key;
    }

    @Override
    public boolean updateEncounter(Encounter encounter) {
        return false;
    }

    @Override
    public boolean deleteEncounter(long ent_key) {
        return false;
    }
}
