package com.fansen.phr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fansen.phr.PhrSchemaContract;

/**
 * Created by Yihui on 2015/10/16.
 */
public class FsPhrDB extends SQLiteOpenHelper{
    private final static String DATABASE_NAME = "FSPHR.db";
    private final static int DATABASE_VERSION = 20;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private SQLiteDatabase database = null;

    //sql for create person table
    private final static String SQL_CREATE_PERSON = "CREATE TABLE " + PhrSchemaContract.PersonTable.TABLE_NAME + "(" +
            PhrSchemaContract.PersonTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_BIRDAY + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_GENDER + TEXT_TYPE + ")";

    //sql for create encounter table
    private final static String SQL_CREATE_ENCOUNTER = "CREATE TABLE " + PhrSchemaContract.EncounterTable.TABLE_NAME + "(" +
            PhrSchemaContract.EncounterTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DISCHARGE_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_HISTORICAL_PROBLEMS + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PHYSICAL_EXAM + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PERSON_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PRIMARY_DIAGNOSIS_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + INT_TYPE + ")";

    //TODO modify
    private final static String SQL_CREATE_MEDICATION_ORDER = "CREATE TABLE " + PhrSchemaContract.MedicationOrderTable.TABLE_NAME + "(" +
            PhrSchemaContract.MedicationOrderTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_MED_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY + REAL_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY_UNIT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE + REAL_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE_UNIT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL + REAL_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL_UNIT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_TIMES + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ROUTE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_START_TIME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_PRN + INT_TYPE + ")";

    private final static String SQL_CREATE_DIAGNOSIS = "CREATE TABLE " + PhrSchemaContract.DiagnosisTable.TABLE_NAME + "(" +
            PhrSchemaContract.DiagnosisTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_PRIMARY_INDICATOR + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_STATUS + TEXT_TYPE + ")";

    private final static String SQL_CREATE_CHIEF_COMPLAINTS = "CREATE TABLE " + PhrSchemaContract.ChiefComplaintTable.TABLE_NAME + "(" +
            PhrSchemaContract.ChiefComplaintTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_COMPLAINT_ENT_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_SYMPTOM + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION_UNIT + TEXT_TYPE + ")";

    private final static String SQL_CREATE_CLINICAL_DOCUMENT = "CREATE TABLE " + PhrSchemaContract.ClinicalDocumentTable.TABLE_NAME + "(" +
            PhrSchemaContract.ClinicalDocumentTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_ENT_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_LEGAL_AUTHENTICATION_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_CREATING_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_AUTHENTICATION_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_TYPE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_THUMBNAIL_IMAGE_URI + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_IMAGE_URI + TEXT_TYPE + ")";

    //sql for create org table
    private final static String SQL_CREATE_ORGANIZATION = "CREATE TABLE " + PhrSchemaContract.OrganizationTable.TABLE_NAME + "(" +
            PhrSchemaContract.OrganizationTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME + TEXT_TYPE + ")";


    //sql for create department
    private final static String SQL_CREATE_DEPARTMENT = "CREATE TABLE " + PhrSchemaContract.DepartmentTable.TABLE_NAME + "(" +
            PhrSchemaContract.DepartmentTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME + TEXT_TYPE + ")";



    private final static String SQL_CREATE_DICT_DIAGNOSIS = "CREATE TABLE " + PhrSchemaContract.DictDiagnosisTable.TABLE_NAME + "(" +
            PhrSchemaContract.DictDiagnosisTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_DESC + TEXT_TYPE + ")";

    private final static String SQL_CREATE_DICT_MEDICATION = "CREATE TABLE " + PhrSchemaContract.MedicationDictTable.TABLE_NAME + "(" +
            PhrSchemaContract.MedicationDictTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC + TEXT_TYPE + ")";

    private final static String SQL_CREATE_PHYSICIAN = "CREATE TABLE " + PhrSchemaContract.PhysicianTable.TABLE_NAME + "(" +
            PhrSchemaContract.PhysicianTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_ID + TEXT_TYPE + ")";


    //start for version 18
    private final static String SQL_CREATE_DI_REPORT = "CREATE TABLE "+ PhrSchemaContract.DiagnosticImagingReportTable.TABLE_NAME + "(" +
            PhrSchemaContract.DiagnosticImagingReportTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_FINDINGS + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RESULT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RECOMMENDATION + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_MODALITY + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_ENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_RPDEF_KEY + INT_TYPE + ")";

    private final static String SQL_CREATE_DIAGNOSTIC_IMAGE = "CREATE TABLE " + PhrSchemaContract.DiagnosticImageTable.TABLE_NAME + "("+
            PhrSchemaContract.DiagnosticImageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_DIR_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_IMAGE_URI + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_THUMBNAIL_IMAGE_URI + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_CREATING_DATE + TEXT_TYPE +")";

    private final static String SQL_CREATE_IMAGING_OBR = "CREATE TABLE " + PhrSchemaContract.ImagingObservationTable.TABLE_NAME + "("+
            PhrSchemaContract.ImagingObservationTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_VALUE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_DI_REPORT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_DEF_KEY + INT_TYPE +")";

    private final static String SQL_OBR_DEF = "CREATE TABLE "+ PhrSchemaContract.ObservationDefTable.TABLE_NAME + "("+
            PhrSchemaContract.ObservationDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NORMAL_RANGE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE_SYSTEM_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE_SYSTEM_OID + TEXT_TYPE +")";

    private final static String SQL_CREATE_RP_TYPE_DEF = "CREATE TABLE " + PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME + "("+
            PhrSchemaContract.RequestedProcedureTypeDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE_SYSTEM_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE_SYSTEM_OID + TEXT_TYPE +")";

    //end of version 18

    /*
    * Following SQL scripts are used for upgrading db schema
    * */
    /*
    private final static String SQL_ADD_HISTORICAL_PROBLEMS_COLUMN = "alter table "+PhrSchemaContract.EncounterTable.TABLE_NAME +
            " add "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_HISTORICAL_PROBLEMS + TEXT_TYPE;
    private final static String SQL_ADD_PHYSICAL_EXAM_COLUMN = "alter table "+PhrSchemaContract.EncounterTable.TABLE_NAME +
            " add "+PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PHYSICAL_EXAM + TEXT_TYPE;
    */


    /**
     * version 19 add a column in diagnostic imaging report to refer to bodypart definition table.
     * Add a new table for the body part definition
     * */

    private final static String SQL_CREATE_BODY_PART_DEF = "CREATE TABLE " + PhrSchemaContract.BodypartDefTable.TABLE_NAME + "("+
            PhrSchemaContract.BodypartDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_OID + TEXT_TYPE +")";

    private final static String SQL_ADD_BODY_PART_DEF_KEY = "alter table "+PhrSchemaContract.DiagnosticImagingReportTable.TABLE_NAME +
            " add "+PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY + INT_TYPE;

    private final static String SQL_ADD_DIAGNOSTIC_IMAGE_DIR_KEY = "alter table "+PhrSchemaContract.DiagnosticImageTable.TABLE_NAME +
            " add "+PhrSchemaContract.DiagnosticImageTable.COLUMN_NAME_DI_DIR_KEY + INT_TYPE;


    private static FsPhrDB instance = null;

    private FsPhrDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static FsPhrDB getInstance(Context context){
        if (instance == null){
            return new FsPhrDB(context);
        } else{
            return instance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO add code here to create database tables and their relationships
        db.execSQL(SQL_CREATE_PERSON);
        db.execSQL(SQL_CREATE_ENCOUNTER);
        db.execSQL(SQL_CREATE_ORGANIZATION);
        db.execSQL(SQL_CREATE_DEPARTMENT);
        db.execSQL(SQL_CREATE_DIAGNOSIS);
        db.execSQL(SQL_CREATE_DICT_DIAGNOSIS);
        db.execSQL(SQL_CREATE_PHYSICIAN);
        db.execSQL(SQL_CREATE_CHIEF_COMPLAINTS);
        db.execSQL(SQL_CREATE_DICT_MEDICATION);
        db.execSQL(SQL_CREATE_MEDICATION_ORDER);
        db.execSQL(SQL_CREATE_CLINICAL_DOCUMENT);
        //version 18
        db.execSQL(SQL_CREATE_DIAGNOSTIC_IMAGE);
        db.execSQL(SQL_CREATE_DI_REPORT);
        db.execSQL(SQL_CREATE_IMAGING_OBR);
        db.execSQL(SQL_OBR_DEF);
        db.execSQL(SQL_CREATE_RP_TYPE_DEF);
        //version 19
        db.execSQL(SQL_CREATE_BODY_PART_DEF);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade for version 19
        db.execSQL(SQL_CREATE_BODY_PART_DEF);
        db.execSQL(SQL_ADD_BODY_PART_DEF_KEY);
        //version 20
        db.execSQL(SQL_ADD_DIAGNOSTIC_IMAGE_DIR_KEY);

        /*upgrade for version 18
        db.execSQL(SQL_CREATE_DIAGNOSTIC_IMAGE);
        db.execSQL(SQL_CREATE_DI_REPORT);
        db.execSQL(SQL_CREATE_IMAGING_OBR);
        db.execSQL(SQL_OBR_DEF);
        db.execSQL(SQL_CREATE_RP_TYPE_DEF);
        end of version 18*/

        //db.execSQL(SQL_ADD_HISTORICAL_PROBLEMS_COLUMN);
        //db.execSQL(SQL_ADD_PHYSICAL_EXAM_COLUMN);

        /*db.execSQL("DROP TABLE "+PhrSchemaContract.PersonTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.EncounterTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.OrganizationTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.DepartmentTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.DiagnosisTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.DictDiagnosisTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.PhysicianTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.ChiefComplaintTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.MedicationOrderTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.MedicationDictTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.ClinicalDocumentTable.TABLE_NAME);

        onCreate(db);*/
    }

    public long insert(String table, ContentValues values){
        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(table, null, values);

        db.close();

        return id;
    }

    public void update(String table, ContentValues values, String column, String[] args){
        SQLiteDatabase db = getWritableDatabase();

        db.update(table, values, column, args);

        db.close();
    }

    public Cursor query(String tableName, String[] returnColumns, String where, String[] args){
        if(database == null ){
            database = getReadableDatabase();
        } else {
            if (!database.isOpen()){
                database = getReadableDatabase();
            }
        }

        Cursor c = database.query(tableName, returnColumns, where, args, null, null, null);

        return c;
    }

    public Cursor rawQuery(String sql){
        if(database == null ){
            database = getReadableDatabase();
        } else {
            if (!database.isOpen()){
                database = getReadableDatabase();
            }
        }

        Cursor c = database.rawQuery(sql, null);

        return c;
    }

    public void closeDatabase(){
        if(database != null){
            database.close();
        }
    }
}
