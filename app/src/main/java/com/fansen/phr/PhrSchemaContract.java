package com.fansen.phr;

import android.provider.BaseColumns;

/**
 * Created by 310078142 on 2015/10/16.
 */
public final class PhrSchemaContract {
    public PhrSchemaContract() {
    }

    public static abstract class PersonTable implements BaseColumns{
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_NAME_PERSON_NAME = "person_name";
        public static final String COLUMN_NAME_PERSON_BIRDAY = "person_birday";
        public static final String COLUMN_NAME_PERSON_GENDER = "person_gender";
    }

    public static abstract class EncounterTable implements BaseColumns{
        public static final String TABLE_NAME = "encounter";
        public static final String COLUMN_NAME_ENT_ADMIT_DATE = "ent_admit_date";
        public static final String COLUMN_NAME_ENT_DISCHARGE_DATE = "ent_discharge_date";
        public static final String COLUMN_NAME_ENT_PERSON_KEY = "person_key";
        public static final String COLUMN_NAME_ENT_ORG_KEY = "org_key";
        public static final String COLUMN_NAME_ENT_DPT_KEY = "dpt_key";
    }

    public static abstract class DiagnosisTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnosis";
        public static final String COLUMN_NAME_DIG_ENT_KEY = "ent_key";
        public static final String COLUMN_NAME_DIG_DICT_KEY = "dict_dig_key";
        public static final String COLUMN_NAME_DIG_STATUS = "dig_status";
        public static final String COLUMN_NAME_DIG_PRIMARY_INDICATOR = "primary_indicator";
    }


    /**
     * Master file tables, like organization, department, terminologies
     */
    public static abstract class OrganizationTable implements BaseColumns{
        public static final String TABLE_NAME = "organization";
        public static final String COLUMN_NAME_ORG_NAME = "org_name";
    }

    public static abstract class DepartmentTable implements BaseColumns{
        public static final String TABLE_NAME = "department";
        public static final String COLUMN_NAME_DEPT_NAME = "department_name";
    }

    public static abstract class DictDiagnosisTable implements BaseColumns{
        public static final String TABLE_NAME = "dict_diagnosis";
        public static final String COLUMN_NAME_DICT_NAME = "name";
        public static final String COLUMN_NAME_DICT_CODE = "code";
        public static final String COLUMN_NAME_DICT_DESC = "description";
    }
}
