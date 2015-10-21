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
    private final static int DATABASE_VERSION = 3;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG";
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
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PERSON_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + INT_TYPE + ")";


    private final static String SQL_CREATE_DIAGNOSIS = "CREATE TABLE " + PhrSchemaContract.DiagnosisTable.TABLE_NAME + "(" +
            PhrSchemaContract.DiagnosisTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_ENT_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_DICT_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_PRIMARY_INDICATOR + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.DiagnosisTable.COLUMN_NAME_DIG_STATUS + TEXT_TYPE + ")";

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

    private final static String SQL_CREATE_PROCEDURE = "";

    private final static String SQL_CREATE_DOCUMENT = "";


    private final static String SQL_UPGRADE_DB = "";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_UPGRADE_DB);
        db.execSQL("DROP TABLE "+PhrSchemaContract.PersonTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.EncounterTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.OrganizationTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.DepartmentTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.DiagnosisTable.TABLE_NAME);
        db.execSQL("DROP TABLE "+PhrSchemaContract.DictDiagnosisTable.TABLE_NAME);

        onCreate(db);
    }

    public long insert(String table, ContentValues values){
        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(table, null, values);

        db.close();

        return id;
    }

    public void update(String table, String column, ContentValues values){
        SQLiteDatabase db = getWritableDatabase();

        //db.update(table, values, )
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
