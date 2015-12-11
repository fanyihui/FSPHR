package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.Department;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.entity.Physician;
import com.fansen.phr.service.IDiagnosisService;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/10/12.
 */
public class EncounterServiceLocalImpl extends BaseServiceLocal implements IEncounterService{
    //private FsPhrDB fsPhrDB = null;
    private IDiagnosisService diagnosisService;

    public EncounterServiceLocalImpl(Context context) {
        //fsPhrDB = FsPhrDB.getInstance(context);
        super(context);
        diagnosisService = new DiagnosisServiceLocalImpl(context);
    }

    @Override
    public List<Encounter> getAllEncounters() {
        List<Encounter> encounters = new ArrayList<Encounter>();

        String ent_sql = "select " + PhrSchemaContract.EncounterTable.TABLE_NAME+"."+PhrSchemaContract.EncounterTable._ID + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DISCHARGE_DATE + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + "," +
                PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME + "," +
                PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME + "," +
                PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME + "," +
                PhrSchemaContract.OrganizationTable.TABLE_NAME + "," +
                PhrSchemaContract.PhysicianTable.TABLE_NAME + "," +
                PhrSchemaContract.DepartmentTable.TABLE_NAME +
                " where encounter.org_key=organization._id and " +
                "encounter.dpt_key=department._id and "+
                "encounter.attending_doctor_key=physician._id" +
                " order by "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE +" desc";

        Cursor c = fsPhrDB.rawQuery(ent_sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            int ent_key = c.getInt(c.getColumnIndex("_id"));

            Organization org = new Organization();
            Department dept = new Department();
            Encounter encounter = new Encounter();
            //DictDiagnosis dictDiagnosis = new DictDiagnosis();
            Physician physician = new Physician();

            org.setOrg_key(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY)));
            org.setOrg_name(c.getString(c.getColumnIndex(PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME)));

            dept.setDepartment_key(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY)));
            dept.setName(c.getString(c.getColumnIndex(PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME)));

            //dictDiagnosis.setKey(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PRIMARY_DIAGNOSIS_KEY)));
            //dictDiagnosis.setCode(c.getString(c.getColumnIndex(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE)));
            //dictDiagnosis.setName(c.getString(c.getColumnIndex(PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME)));

            physician.setPhysicianKey(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY)));
            physician.setPhysicianName(c.getString(c.getColumnIndex(PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME)));

            encounter.setProblem_description(c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC)));
            encounter.setChiefComplaint(c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT)));
            encounter.setDepartment(dept);
            encounter.setOrg(org);
            encounter.setAdmit_date(TimeFormat.format("yyyyMMdd", c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE))));
            encounter.setEncounter_key(ent_key);
            //encounter.setPrimaryDiagnosis(dictDiagnosis);
            encounter.setAttendingDoctor(physician);

            ArrayList<Diagnosis> diagnosises = diagnosisService.getDiagnosis(ent_key);
            encounter.setDiagnosisList(diagnosises);

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
        //values.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PRIMARY_DIAGNOSIS_KEY, encounter.getPrimaryDiagnosis().getKey());
        values.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY, encounter.getAttendingDoctor().getPhysicianKey());

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

    @Override
    public void updateProblemsDescription(long ent_key, String problemsDescription) {
        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC, problemsDescription);

        String[] args = {String.valueOf(ent_key)};

        fsPhrDB.update(PhrSchemaContract.EncounterTable.TABLE_NAME, cv, "_id=?", args);
    }

    @Override
    public String getProblemsDescription(long ent_key) {
        String sql = "select "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME +
                " where _id="+ent_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        String desc = c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC));

        c.close();

        fsPhrDB.closeDatabase();

        return desc;
    }

    @Override
    public void updateHistoricalProblems(long ent_key, String historicalProblems) {
        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_HISTORICAL_PROBLEMS, historicalProblems);

        String[] args = {String.valueOf(ent_key)};

        fsPhrDB.update(PhrSchemaContract.EncounterTable.TABLE_NAME, cv, "_id=?", args);
    }

    @Override
    public void updatePhysicalExamDetails(long ent_key, String physicalExamDetails) {
        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PHYSICAL_EXAM, physicalExamDetails);

        String[] args = {String.valueOf(ent_key)};

        fsPhrDB.update(PhrSchemaContract.EncounterTable.TABLE_NAME, cv, "_id=?", args);
    }

    @Override
    public String getHistoricalProblems(long ent_key) {
        String sql = "select "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_HISTORICAL_PROBLEMS +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME +
                " where _id="+ent_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        String desc = c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_HISTORICAL_PROBLEMS));

        c.close();

        fsPhrDB.closeDatabase();

        return desc;
    }

    @Override
    public String getPhysicalExamDetails(long ent_key) {
        String sql = "select "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PHYSICAL_EXAM +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME +
                " where _id="+ent_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        String desc = c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PHYSICAL_EXAM));

        c.close();

        fsPhrDB.closeDatabase();

        return desc;
    }

    @Override
    public String getChiefComplaint(long ent_key) {
        String sql = "select "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME +
                " where _id="+ent_key;

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        String desc = c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT));

        c.close();

        fsPhrDB.closeDatabase();

        return desc;
    }

    @Override
    public void updateChiefComplaint(long ent_key, String chiefComplain) {
        ContentValues cv = new ContentValues();
        cv.put(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT, chiefComplain);

        String[] args = {String.valueOf(ent_key)};

        fsPhrDB.update(PhrSchemaContract.EncounterTable.TABLE_NAME, cv, "_id=?", args);
    }

    @Override
    public Encounter getLatestEncounter() {
        String ent_sql = "select " + PhrSchemaContract.EncounterTable.TABLE_NAME+"."+PhrSchemaContract.EncounterTable._ID + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DISCHARGE_DATE + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + "," +
                PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + "," +
                PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME + "," +
                PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME + "," +
                PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME +
                " from " + PhrSchemaContract.EncounterTable.TABLE_NAME + "," +
                PhrSchemaContract.OrganizationTable.TABLE_NAME + "," +
                PhrSchemaContract.PhysicianTable.TABLE_NAME + "," +
                PhrSchemaContract.DepartmentTable.TABLE_NAME +
                " where encounter.org_key=organization._id and " +
                "encounter.dpt_key=department._id and "+
                "encounter.attending_doctor_key=physician._id"+
                " order by "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE +" desc LIMIT 1";;

        Cursor c = fsPhrDB.rawQuery(ent_sql);

        if (c.getCount() <=0){
            return null;
        }

        c.moveToFirst();

        int ent_key = c.getInt(c.getColumnIndex("_id"));

        Organization org = new Organization();
        Department dept = new Department();
        Encounter encounter = new Encounter();
        Physician physician = new Physician();

        org.setOrg_key(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY)));
        org.setOrg_name(c.getString(c.getColumnIndex(PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME)));

        dept.setDepartment_key(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY)));
        dept.setName(c.getString(c.getColumnIndex(PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME)));

        physician.setPhysicianKey(c.getInt(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY)));
        physician.setPhysicianName(c.getString(c.getColumnIndex(PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME)));

        encounter.setProblem_description(c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC)));
        encounter.setChiefComplaint(c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT)));
        encounter.setDepartment(dept);
        encounter.setOrg(org);
        encounter.setAdmit_date(TimeFormat.format("yyyyMMdd", c.getString(c.getColumnIndex(PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE))));
        encounter.setEncounter_key(ent_key);
        encounter.setAttendingDoctor(physician);

        ArrayList<Diagnosis> diagnosises = diagnosisService.getDiagnosis(ent_key);
        encounter.setDiagnosisList(diagnosises);

        c.close();

        fsPhrDB.closeDatabase();

        return encounter;
        //encounters.add(encounter);
    }
}
