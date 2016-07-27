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
    private final static int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private SQLiteDatabase database = null;

    //sql for create assigned authority table
    private final static String SQL_CREATE_ASSIGNED_AUTHORITY = "CREATE TABLE "+PhrSchemaContract.AssignedAuthorityTable.TABLE_NAME + "(" +
            PhrSchemaContract.AssignedAuthorityTable._ID + "" + COMMA_SEP +
            PhrSchemaContract.AssignedAuthorityTable.COLUMN_NAME_AA_ID + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.AssignedAuthorityTable.COLUMN_NAME_AA_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.AssignedAuthorityTable.COLUMN_NAME_AA_ORG_KEY + INT_TYPE  + ")";

    //sql for create person table
    private final static String SQL_CREATE_PERSON = "CREATE TABLE " + PhrSchemaContract.PersonTable.TABLE_NAME + "(" +
            PhrSchemaContract.PersonTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_BIRDAY + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_GENDER + TEXT_TYPE + ")";

    //sql for create PersonID table
    private final static String SQL_CREATE_PERSON_ID = "CREATE TABLE "+PhrSchemaContract.PersonIDTable.TABLE_NAME + "(" +
            PhrSchemaContract.PersonIDTable._ID + "" + COMMA_SEP +
            PhrSchemaContract.PersonIDTable.COLUMN_NAME_PERSON_ID + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonIDTable.COLUMN_NAME_PERSON_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonIDTable.COLUMN_NAME_AA_KEY + INT_TYPE  + ")";


    //sql for create patient table
    private final static String SQL_CREATE_PATIENT = "CREATE TABLE " + PhrSchemaContract.PatientTable.TABLE_NAME + "(" +
            PhrSchemaContract.PatientTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.PatientTable.COLUMN_NAME_PERSON_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.PatientTable.COLUMN_NAME_ORG_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.PatientTable.COLUMN_NAME_VIP + TEXT_TYPE + ")";

    //sql for create PatientID table
    private final static String SQL_CREATE_PATIENT_ID = "CREATE TABLE "+PhrSchemaContract.PatientIDTable.TABLE_NAME + "(" +
            PhrSchemaContract.PatientIDTable._ID + "" + COMMA_SEP +
            PhrSchemaContract.PatientIDTable.COLUMN_NAME_PATIENT_ID + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PatientIDTable.COLUMN_NAME_PATIENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.PatientIDTable.COLUMN_NAME_AA_KEY + INT_TYPE  + ")";

    //sql for create encounter table
    private final static String SQL_CREATE_ENCOUNTER = "CREATE TABLE " + PhrSchemaContract.EncounterTable.TABLE_NAME + "(" +
            PhrSchemaContract.EncounterTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PATIENT_CLASS + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DISCHARGE_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PROBLEMS_DESC + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_CHIEF_COMPLAINT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_HISTORICAL_PROBLEMS + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PHYSICAL_EXAM + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PATIENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PRIMARY_DIAGNOSIS_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + INT_TYPE + ")";

    private final static String SQL_CREATE_MEDICATION_ORDER = "CREATE TABLE " + PhrSchemaContract.MedicationOrderTable.TABLE_NAME + "(" +
            PhrSchemaContract.MedicationOrderTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_MED_DICT_KEY + INT_TYPE + COMMA_SEP +
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
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_STATUS + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_NOTES + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_PRN + INT_TYPE + ")";

    private final static String SQL_CREATE_MAR = "CREATE TABLE " + PhrSchemaContract.MARTable.TABLE_NAME + "(" +
            PhrSchemaContract.MARTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_TM + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DOSAGE + REAL_TYPE + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DOSAGE_UNIT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_SCHEDULED_TIME_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARTable.COLUMN_NAME_MAR_ORDER_KEY + INT_TYPE + ")";

    private final static String SQL_CREATE_DIAGNOSIS = "CREATE TABLE " + PhrSchemaContract.DiagnosisTable.TABLE_NAME + "(" +
            PhrSchemaContract.DiagnosisTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_PRIMARY_INDICATOR + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_STATUS + TEXT_TYPE + ")";

    private final static String SQL_CREATE_CHIEF_COMPLAINTS = "CREATE TABLE " + PhrSchemaContract.ChiefComplaintTable.TABLE_NAME + "(" +
            PhrSchemaContract.ChiefComplaintTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_COMPLAINT_ENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_SYMPTOM + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ChiefComplaintTable.COLUMN_NAME_DURATION_UNIT + TEXT_TYPE + ")";

    private final static String SQL_CREATE_CLINICAL_DOCUMENT = "CREATE TABLE " + PhrSchemaContract.ClinicalDocumentTable.TABLE_NAME + "(" +
            PhrSchemaContract.ClinicalDocumentTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ClinicalDocumentTable.COLUMN_NAME_DOC_ENT_KEY + INT_TYPE + COMMA_SEP +
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
            PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_ORG_KEY + INT_TYPE + COMMA_SEP +
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
            PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_GENERAL_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_MANUFACTURER + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC + TEXT_TYPE + ")";

    private final static String SQL_CREATE_PHYSICIAN = "CREATE TABLE " + PhrSchemaContract.PhysicianTable.TABLE_NAME + "(" +
            PhrSchemaContract.PhysicianTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_DEPT_KEY + INT_TYPE + COMMA_SEP +
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
            PhrSchemaContract.DiagnosticImagingReportTable.COLUMN_NAME_DI_BODY_PART_DEF_KEY + INT_TYPE + COMMA_SEP +
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
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_UNIT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_DI_REPORT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.ImagingObservationTable.COLUMN_NAME_IMAGE_OBR_DEF_KEY + INT_TYPE +")";

    private final static String SQL_OBR_DEF = "CREATE TABLE "+ PhrSchemaContract.ObservationDefTable.TABLE_NAME + "("+
            PhrSchemaContract.ObservationDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NORMAL_RANGE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE_SYSTEM_KEY + INT_TYPE +")";

    private final static String SQL_CREATE_RP_TYPE_DEF = "CREATE TABLE " + PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME + "("+
            PhrSchemaContract.RequestedProcedureTypeDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE_SYSTEM_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE_SYSTEM_OID + TEXT_TYPE +")";

    private final static String SQL_CREATE_BODY_PART_DEF = "CREATE TABLE " + PhrSchemaContract.BodypartDefTable.TABLE_NAME + "("+
            PhrSchemaContract.BodypartDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_OID + TEXT_TYPE +")";

    //version 21 add code system table and change the dict reference to the code system.
    private final static String SQL_CREATE_CODE_SYSTEM = "CREATE TABLE "+PhrSchemaContract.CodeSystemTable.TABLE_NAME + "(" +
            PhrSchemaContract.CodeSystemTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.CodeSystemTable.COLUMN_NAME_CODE_SYSTEM_OID + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.CodeSystemTable.COLUMN_NAME_CODE_SYSTEM_NAME + TEXT_TYPE +")";

    private final static String SQL_INSERT_DEFAULT_CODE_SYSTEM_DATA = "INSERT INTO "+PhrSchemaContract.CodeSystemTable.TABLE_NAME + "("+
            PhrSchemaContract.CodeSystemTable.COLUMN_NAME_CODE_SYSTEM_OID + COMMA_SEP + PhrSchemaContract.CodeSystemTable.COLUMN_NAME_CODE_SYSTEM_NAME+")" +
            " VALUES('1.2.84.2344223l323.22333.2333','凡森健康')";

    private final static String SQL_CREATE_ORDER_CODE_DEF = "CREATE TABLE " + PhrSchemaContract.OrderCodeDefTable.TABLE_NAME + "("+
            PhrSchemaContract.OrderCodeDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_CODE_SYSTEM_KEY + INT_TYPE +")";

    private final static String SQL_CREATE_SPECIMEN_TYPE_DEF = "CREATE TABLE " + PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME + "("+
            PhrSchemaContract.SpecimenTypeCodeDefTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_CODE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_CODE_SYSTEM_KEY + INT_TYPE +")";

    private final static String SQL_CREATE_LAB_REPORT = "CREATE TABLE " + PhrSchemaContract.LabReportTable.TABLE_NAME + "("+
            PhrSchemaContract.LabReportTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ENT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_SPECIMEN_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_COLLECTED_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportTable.COLUMN_NAME_LAB_REPORTING_DATE + TEXT_TYPE +")";

    private final static String SQL_CREATE_LAB_OBSERVATION = "CREATE TABLE " + PhrSchemaContract.LabObservationTable.TABLE_NAME + "(" +
            PhrSchemaContract.LabObservationTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_VALUE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_UNIT + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_DEF_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabObservationTable.COLUMN_NAME_LAB_OBR_LAB_REPORT_KEY + INT_TYPE + ")";

    private final static String SQL_CREATE_LAB_REF_IMAGE = "CREATE TABLE " + PhrSchemaContract.LabReportReferenceImageTable.TABLE_NAME + "("+
            PhrSchemaContract.LabReportReferenceImageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_REF_IMAGE_URI + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_THUMBNAIL_IMAGE_URI + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.LabReportReferenceImageTable.COLUMN_NAME_LAB_REPORT_CREATING_DATE + TEXT_TYPE +")";

    private final static String SQL_CREATE_MAR_SCHEDULED_TIME = "CREATE TABLE " + PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME + "("+
            PhrSchemaContract.MARScheduledTimeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SEQUENCE_NUMBER + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME + TEXT_TYPE + ")";

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
        db.execSQL(SQL_CREATE_PERSON);
        db.execSQL(SQL_CREATE_PERSON_ID);
        db.execSQL(SQL_CREATE_PATIENT);
        db.execSQL(SQL_CREATE_PATIENT_ID);
        db.execSQL(SQL_CREATE_ASSIGNED_AUTHORITY);
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
        db.execSQL(SQL_CREATE_DIAGNOSTIC_IMAGE);
        db.execSQL(SQL_CREATE_DI_REPORT);
        db.execSQL(SQL_CREATE_IMAGING_OBR);
        db.execSQL(SQL_OBR_DEF);
        db.execSQL(SQL_CREATE_RP_TYPE_DEF);
        db.execSQL(SQL_CREATE_BODY_PART_DEF);
        db.execSQL(SQL_CREATE_CODE_SYSTEM);
        db.execSQL(SQL_INSERT_DEFAULT_CODE_SYSTEM_DATA);
        db.execSQL(SQL_CREATE_LAB_REPORT);
        db.execSQL(SQL_CREATE_LAB_REF_IMAGE);
        db.execSQL(SQL_CREATE_LAB_OBSERVATION);
        db.execSQL(SQL_CREATE_ORDER_CODE_DEF);
        db.execSQL(SQL_CREATE_SPECIMEN_TYPE_DEF);
        db.execSQL(SQL_CREATE_MAR);
        db.execSQL(SQL_CREATE_MAR_SCHEDULED_TIME);

        //create index
        db.execSQL("CREATE UNIQUE INDEX DICT_DIAGNOSIS_NAME ON "+PhrSchemaContract.DictDiagnosisTable.TABLE_NAME +"("+PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX DICT_OBR_NAME ON "+PhrSchemaContract.ObservationDefTable.TABLE_NAME +"("+PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX DICT_RP_NAME ON "+PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME +"("+PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX DICT_BODY_NAME ON "+PhrSchemaContract.BodypartDefTable.TABLE_NAME +"("+PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX DICT_ORDER_NAME ON "+PhrSchemaContract.OrderCodeDefTable.TABLE_NAME +"("+PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX DICT_SPECIMEN_NAME ON "+PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME +"("+PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX ORG_NAME ON "+PhrSchemaContract.OrganizationTable.TABLE_NAME +"("+PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX DEPT_NAME ON "+PhrSchemaContract.DepartmentTable.TABLE_NAME +"("+PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME+","+PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_ORG_KEY+")");
        db.execSQL("CREATE UNIQUE INDEX PHYSICIAN_NAME ON "+PhrSchemaContract.PhysicianTable.TABLE_NAME +"("+PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME+","+PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_DEPT_KEY+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DICT_DIAGNOSIS_NAME ON "+PhrSchemaContract.DictDiagnosisTable.TABLE_NAME +"("+PhrSchemaContract.DictDiagnosisTable.COLUMN_NAME_DICT_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DICT_OBR_NAME ON "+PhrSchemaContract.ObservationDefTable.TABLE_NAME +"("+PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DICT_RP_NAME ON "+PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME +"("+PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DICT_BODY_NAME ON "+PhrSchemaContract.BodypartDefTable.TABLE_NAME +"("+PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DICT_ORDER_NAME ON "+PhrSchemaContract.OrderCodeDefTable.TABLE_NAME +"("+PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DICT_SPECIMEN_NAME ON "+PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME +"("+PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS ORG_NAME ON "+PhrSchemaContract.OrganizationTable.TABLE_NAME +"("+PhrSchemaContract.OrganizationTable.COLUMN_NAME_ORG_NAME+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS DEPT_NAME ON "+PhrSchemaContract.DepartmentTable.TABLE_NAME +"("+PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_NAME+","+PhrSchemaContract.DepartmentTable.COLUMN_NAME_DEPT_ORG_KEY+")");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS PHYSICIAN_NAME ON "+PhrSchemaContract.PhysicianTable.TABLE_NAME +"("+PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_NAME+","+PhrSchemaContract.PhysicianTable.COLUMN_NAME_PHYSICIAN_DEPT_KEY+")");
        */
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
