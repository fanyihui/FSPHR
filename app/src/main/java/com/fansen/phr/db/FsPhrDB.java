package com.fansen.phr.db;

import android.content.ContentValues;
import android.content.Context;
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
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";

    //sql for create person table
    private final static String SQL_CREATE_PERSON = "CREATE TABLE " + PhrSchemaContract.PersonTable.TABLE_NAME + "(" +
            PhrSchemaContract.PersonTable._ID + " LONG PRIMARY KEY" + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_NAME + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_BIRDAY + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.PersonTable.COLUMN_NAME_PERSON_BIRDAY + TEXT_TYPE + COMMA_SEP +")";

    //sql for create encounter table
    private final static String SQL_CREATE_ENCOUNTER = "CREATE TABLE " + PhrSchemaContract.EncounterTable.TABLE_NAME + "(" +
            PhrSchemaContract.EncounterTable._ID + " LONG PRIMARY KEY" + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ADMIT_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DISCHARGE_DATE + TEXT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_PERSON_KEY + LONG_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_ORG_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DPT_KEY + INT_TYPE + COMMA_SEP +
            PhrSchemaContract.EncounterTable.COLUMN_NAME_ENT_DIAGNOSIS_KEY + INT_TYPE + COMMA_SEP +")";


    //sql for create org table
    private final static String SQL_CREATE_ORGANIZATION = "";

    //sql for create department
    private final static String SQL_CREATE_DEPARTMENT = "";

    private final static String SQL_CREATE_DIAGNOSIS = "";

    private final static String SQL_CREATE_DICT_DIAGNOSIS = "";

    private final static String SQL_CREATE_PROCEDURE = "";

    private final static String SQL_CREATE_DOCUMENT = "";


    public FsPhrDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO add code here to create database tables and their relationships
        db.execSQL(SQL_CREATE_PERSON);
        db.execSQL(SQL_CREATE_ENCOUNTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
}
