package com.fansen.phr;

import android.provider.BaseColumns;

import com.fansen.phr.service.implementation.BaseServiceLocal;

import java.net.PortUnreachableException;
import java.security.PublicKey;

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
        public static final String COLUMN_NAME_ENT_PRIMARY_DIAGNOSIS_KEY = "primary_diagnosis_key";
        public static final String COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY = "attending_doctor_key";
        public static final String COLUMN_NAME_ENT_HISTORICAL_PROBLEMS = "historical_problems";
        public static final String COLUMN_NAME_ENT_PHYSICAL_EXAM = "physical_exam";
        public static final String COLUMN_NAME_ENT_PERSON_KEY = "person_key";
        public static final String COLUMN_NAME_ENT_ORG_KEY = "org_key";
        public static final String COLUMN_NAME_ENT_DPT_KEY = "dpt_key";
        public static final String COLUMN_NAME_ENT_PROBLEMS_DESC = "problems_description";
    }

    public static abstract class MedicationOrderTable implements BaseColumns{
        public static final String TABLE_NAME = "medication_order";
        public static final String COLUMN_NAME_MED_ORDER_ENT_KEY = "ent_key";
        public static final String COLUMN_NAME_MED_ORDER_MED_KEY = "med_key";
        public static final String COLUMN_NAME_MED_ORDER_QUANTITY = "quantity";
        public static final String COLUMN_NAME_MED_ORDER_QUANTITY_UNIT = "quantity_unit";
        public static final String COLUMN_NAME_MED_ORDER_DOSAGE = "dosage";
        public static final String COLUMN_NAME_MED_ORDER_DOSAGE_UNIT = "dosage_unit";
        public static final String COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL = "frequency_interval";
        public static final String COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL_UNIT = "frequency_interval_unit";
        public static final String COLUMN_NAME_MED_ORDER_FREQUENCY_TIMES = "frequency_times";
        public static final String COLUMN_NAME_MED_ORDER_ROUTE = "route";
        public static final String COLUMN_NAME_MED_ORDER_PRN = "prn_indicator";
        public static final String COLUMN_NAME_MED_ORDER_START_TIME = "start_time";
    }

    public static abstract class ClinicalDocumentTable implements BaseColumns{
        public static final String TABLE_NAME = "clinical_document";
        public static final String COLUMN_NAME_DOC_ENT_KEY = "ent_key";
        public static final String COLUMN_NAME_DOC_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_DOC_THUMBNAIL_IMAGE_URI = "thumbnail_image_uri";
        public static final String COLUMN_NAME_DOC_TYPE = "type";
        public static final String COLUMN_NAME_DOC_CREATING_DATE = "creating_date";
        public static final String COLUMN_NAME_DOC_LEGAL_AUTHENTICATION_KEY = "authentication_key";
        public static final String COLUMN_NAME_DOC_AUTHENTICATION_DATE = "authentication_date";
    }

    public static abstract class DiagnosticImagingReportTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnostic_imaging_report";
        public static final String COLUMN_NAME_DI_ENT_KEY = "ent_key";
        public static final String COLUMN_NAME_DI_RPDEF_KEY = "rp_def_key";
        public static final String COLUMN_NAME_DI_BODY_PART_DEF_KEY = "body_part_def_key";
        public static final String COLUMN_NAME_DI_DATE = "date";
        public static final String COLUMN_NAME_DI_RESULT = "result";
        public static final String COLUMN_NAME_DI_FINDINGS = "findings";
        public static final String COLUMN_NAME_DI_RECOMMENDATION = "recommendation";
        public static final String COLUMN_NAME_DI_MODALITY = "modality";
    }

    public static abstract class ImagingObservationTable implements BaseColumns{
        public static final String TABLE_NAME = "imaging_observation";
        public static final String COLUMN_NAME_IMAGE_OBR_VALUE = "value";
        public static final String COLUMN_NAME_IMAGE_OBR_DATE = "date";
        public static final String COLUMN_NAME_IMAGE_OBR_DEF_KEY = "obr_def_key";
        public static final String COLUMN_NAME_IMAGE_OBR_DI_REPORT_KEY = "di_key"; // key reference to diagnostic_imaging_report
    }

    public static abstract class DiagnosticImageTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnostic_image";
        public static final String COLUMN_NAME_DI_DIR_KEY = "dir_key";
        public static final String COLUMN_NAME_DI_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_DI_THUMBNAIL_IMAGE_URI = "thumbnail_image_uri";
        public static final String COLUMN_NAME_DI_CREATING_DATE = "creating_date";

    }

    public static abstract class DiagnosisTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnosis";
        public static final String COLUMN_NAME_DIG_ENT_KEY = "ent_key";
        public static final String COLUMN_NAME_DIG_DICT_KEY = "dict_dig_key";
        public static final String COLUMN_NAME_DIG_STATUS = "dig_status";
        public static final String COLUMN_NAME_DIG_PRIMARY_INDICATOR = "primary_indicator";
    }


    public static abstract class ChiefComplaintTable implements BaseColumns{
        public static final String TABLE_NAME = "chief_complaints";
        public static final String COLUMN_NAME_COMPLAINT_ENT_KEY = "ent_key";
        public static final String COLUMN_NAME_SYMPTOM = "symptom";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_DURATION_UNIT = "duration_unit";
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
        public static final String COLUMN_NAME_DICT_NAME = "diagnosis_name";
        public static final String COLUMN_NAME_DICT_CODE = "diagnosis_code";
        public static final String COLUMN_NAME_DICT_DESC = "diagnosis_desc";
    }

    public static abstract class MedicationDictTable implements BaseColumns{
        public static final String TABLE_NAME = "dict_medication";
        public static final String COLUMN_NAME_DICT_MED_NAME = "name";
        public static final String COLUMN_NAME_DICT_MED_CODE = "code";
        public static final String COLUMN_NAME_DICT_MED_SPEC = "spec";
    }

    public static abstract class PhysicianTable implements BaseColumns{
        public static final String TABLE_NAME = "physician";
        public static final String COLUMN_NAME_PHYSICIAN_NAME = "physician_name";
        public static final String COLUMN_NAME_PHYSICIAN_ID = "physician_id";
    }

    public static abstract class ObservationDefTable implements BaseColumns{
        public static final String TABLE_NAME = "observation_def";
        public static final String COLUMN_NAME_OBR_DEF_NAME = "name";
        public static final String COLUMN_NAME_OBR_DEF_CODE = "code";
        public static final String COLUMN_NAME_OBR_DEF_NORMAL_RANGE = "normal_range";
        public static final String COLUMN_NAME_OBR_DEF_CODE_SYSTEM_OID = "code_system_oid";
        public static final String COLUMN_NAME_OBR_DEF_CODE_SYSTEM_NAME = "code_system_name";
    }

    public static abstract class RequestedProcedureTypeDefTable implements BaseColumns{
        public static final String TABLE_NAME = "rp_type_def";
        public static final String COLUMN_NAME_RP_DEF_NAME = "name";
        public static final String COLUMN_NAME_RP_DEF_CODE = "code";
        public static final String COLUMN_NAME_RP_DEF_CODE_SYSTEM_OID = "code_system_oid";
        public static final String COLUMN_NAME_RP_DEF_CODE_SYSTEM_NAME = "code_system_name";
    }

    public static abstract class BodypartDefTable implements BaseColumns{
        public static final String TABLE_NAME = "body_part_def";
        public static final String COLUMN_NAME_BODY_PART_DEF_NAME = "name";
        public static final String COLUMN_NAME_BODY_PART_DEF_CODE = "code";
        public static final String COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_OID = "code_system_oid";
        public static final String COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_NAME = "code_system_name";
    }
}
