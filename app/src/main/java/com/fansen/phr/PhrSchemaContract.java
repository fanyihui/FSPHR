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

    public static abstract class PersonIDTable implements BaseColumns{
        public static final String TABLE_NAME = "person_ids";
        public static final String COLUMN_NAME_PERSON_ID = "person_id";
        public static final String COLUMN_NAME_PERSON_KEY = "fk_person_key";
        public static final String COLUMN_NAME_AA_KEY = "fk_authority_key";
    }

    public static abstract class AssignedAuthorityTable implements BaseColumns{
        public static final String TABLE_NAME = "assigned_authority";
        public static final String COLUMN_NAME_AA_ID = "authority_id";
        public static final String COLUMN_NAME_AA_NAME = "authority_name";
        //this is optional. the authority may be played by an org
        public static final String COLUMN_NAME_AA_ORG_KEY = "fk_aa_org_key";
    }

    public static abstract class PatientTable implements BaseColumns{
        public static final String TABLE_NAME = "patient";
        public static final String COLUMN_NAME_VIP = "patient_vip";
        public static final String COLUMN_NAME_PERSON_KEY = "fk_pat_person_key";
        public static final String COLUMN_NAME_ORG_KEY = "fk_pat_org_key";
    }

    public static abstract class PatientIDTable implements BaseColumns{
        public static final String TABLE_NAME = "patient_ids";
        public static final String COLUMN_NAME_PATIENT_ID = "patient_id";
        public static final String COLUMN_NAME_PATIENT_KEY = "fk_pid_patient_key";
        public static final String COLUMN_NAME_AA_KEY = "fk_pid_aa_key";
    }

    public static abstract class EncounterTable implements BaseColumns{
        public static final String TABLE_NAME = "encounter";
        public static final String COLUMN_NAME_ENT_PATIENT_CLASS = "patient_class";
        public static final String COLUMN_NAME_ENT_ADMIT_DATE = "ent_admit_date";
        public static final String COLUMN_NAME_ENT_DISCHARGE_DATE = "ent_discharge_date";
        public static final String COLUMN_NAME_ENT_PRIMARY_DIAGNOSIS_KEY = "primary_diagnosis_key";
        public static final String COLUMN_NAME_ENT_ATTENDING_DOCTOR_KEY = "attending_doctor_key";
        public static final String COLUMN_NAME_ENT_CHIEF_COMPLAINT = "chief_complaint";
        public static final String COLUMN_NAME_ENT_HISTORICAL_PROBLEMS = "historical_problems";
        public static final String COLUMN_NAME_ENT_PHYSICAL_EXAM = "physical_exam";
        public static final String COLUMN_NAME_ENT_PATIENT_KEY = "fk_ent_patient_key";
        public static final String COLUMN_NAME_ENT_ORG_KEY = "fk_ent_org_key";
        public static final String COLUMN_NAME_ENT_DPT_KEY = "fk_ent_dpt_key";
        public static final String COLUMN_NAME_ENT_PROBLEMS_DESC = "problems_description";
    }

    public static abstract class MedicationOrderTable implements BaseColumns{
        public static final String TABLE_NAME = "medication_order";
        public static final String COLUMN_NAME_MED_ORDER_ENT_KEY = "fk_med_ord_ent_key";
        public static final String COLUMN_NAME_MED_ORDER_MED_DICT_KEY = "fk_med_dict_key";
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
        public static final String COLUMN_NAME_MED_ORDER_STATUS = "order_status";
        public static final String COLUMN_NAME_MED_ORDER_NOTES = "order_notes";
    }

    public static abstract class MARTable implements BaseColumns{
        public static final String TABLE_NAME = "mar";
        public static final String COLUMN_NAME_MAR_ORDER_KEY = "fk_mar_med_order_key";
        public static final String COLUMN_NAME_MAR_SCHEDULED_TIME_KEY = "fk_mar_scheduled_time_key";
        public static final String COLUMN_NAME_MAR_STATUS = "mar_status";
        public static final String COLUMN_NAME_MAR_DT = "mar_admin_date";
        public static final String COLUMN_NAME_MAR_TM = "mar_admin_time";
        public static final String COLUMN_NAME_MAR_DOSAGE = "mar_dosage";
        public static final String COLUMN_NAME_MAR_DOSAGE_UNIT = "mar_unit";
    }

    public static abstract class MARScheduledTimeTable implements BaseColumns{
        public static final String TABLE_NAME="mar_scheduled_time";
        public static final String COLUMN_NAME_MED_ORDER_KEY = "fk_mar_sch_order_key";
        public static final String COLUMN_NAME_SEQUENCE_NUMBER = "seq_num";
        public static final String COLUMN_NAME_SCHEDULED_TIME = "scheduled_time";
    }

    public static abstract class ClinicalDocumentTable implements BaseColumns{
        public static final String TABLE_NAME = "clinical_document";
        public static final String COLUMN_NAME_DOC_ENT_KEY = "fk_doc_ent_key";
        public static final String COLUMN_NAME_DOC_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_DOC_THUMBNAIL_IMAGE_URI = "thumbnail_image_uri";
        public static final String COLUMN_NAME_DOC_TYPE = "doc_type";
        public static final String COLUMN_NAME_DOC_CREATING_DATE = "creating_date";
        public static final String COLUMN_NAME_DOC_LEGAL_AUTHENTICATION_KEY = "authentication_key";
        public static final String COLUMN_NAME_DOC_AUTHENTICATION_DATE = "authentication_date";
    }

    public static abstract class DiagnosticImagingReportTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnostic_imaging_report";
        public static final String COLUMN_NAME_DI_ENT_KEY = "fk_dir_ent_key";
        public static final String COLUMN_NAME_DI_RPDEF_KEY = "fk_rp_def_key";
        public static final String COLUMN_NAME_DI_BODY_PART_DEF_KEY = "fk_body_part_def_key";
        public static final String COLUMN_NAME_DI_DATE = "di_date";
        public static final String COLUMN_NAME_DI_RESULT = "di_result";
        public static final String COLUMN_NAME_DI_FINDINGS = "di_findings";
        public static final String COLUMN_NAME_DI_RECOMMENDATION = "di_recommendation";
        public static final String COLUMN_NAME_DI_MODALITY = "modality";
    }

    public static abstract class ImagingObservationTable implements BaseColumns{
        public static final String TABLE_NAME = "imaging_observation";
        public static final String COLUMN_NAME_IMAGE_OBR_VALUE = "obr_value";
        public static final String COLUMN_NAME_IMAGE_OBR_UNIT = "obr_unit";
        public static final String COLUMN_NAME_IMAGE_OBR_DATE = "obr_date";
        public static final String COLUMN_NAME_IMAGE_OBR_DEF_KEY = "fk_obr_def_key";
        public static final String COLUMN_NAME_IMAGE_OBR_DI_REPORT_KEY = "fk_img_obr_dir_key"; // key reference to diagnostic_imaging_report
    }

    public static abstract class DiagnosticImageTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnostic_image";
        public static final String COLUMN_NAME_DI_DIR_KEY = "fk_di_dir_key";
        public static final String COLUMN_NAME_DI_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_DI_THUMBNAIL_IMAGE_URI = "thumbnail_image_uri";
        public static final String COLUMN_NAME_DI_CREATING_DATE = "creating_date";
    }

    public static abstract class LabReportReferenceImageTable implements BaseColumns{
        public static final String TABLE_NAME = "lab_report_ref_images";
        public static final String COLUMN_NAME_LAB_REPORT_KEY = "fk_lab_report_key";
        public static final String COLUMN_NAME_LAB_REPORT_REF_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_LAB_REPORT_THUMBNAIL_IMAGE_URI = "thumbnail_image_uri";
        public static final String COLUMN_NAME_LAB_REPORT_CREATING_DATE = "creating_date";
    }

    public static abstract class LabObservationTable implements BaseColumns{
        public static final String TABLE_NAME = "lab_observation";
        public static final String COLUMN_NAME_LAB_OBR_VALUE = "lab_obr_value";
        public static final String COLUMN_NAME_LAB_OBR_UNIT = "lab_obr_unit";
        public static final String COLUMN_NAME_LAB_OBR_DEF_KEY = "fk_lab_obr_def_key";
        public static final String COLUMN_NAME_LAB_OBR_LAB_REPORT_KEY = "fk_lab_report_key"; // key reference to LabReport
    }

    public static abstract class LabReportTable implements BaseColumns{
        public static final String TABLE_NAME = "lab_report";
        public static final String COLUMN_NAME_LAB_REPORT_ENT_KEY = "fk_lab_ent_key";
        public static final String COLUMN_NAME_LAB_REPORT_ORDER_DEF_KEY = "fk_lab_order_def_key";
        public static final String COLUMN_NAME_LAB_SPECIMEN_KEY = "fk_specimen_def_key";
        public static final String COLUMN_NAME_LAB_COLLECTED_DATE = "specimen_collected_date";
        public static final String COLUMN_NAME_LAB_REPORTING_DATE = "reporting_date";
    }

    public static abstract class DiagnosisTable implements BaseColumns{
        public static final String TABLE_NAME = "diagnosis";
        public static final String COLUMN_NAME_DIG_ENT_KEY = "fk_diagnosis_ent_key";
        public static final String COLUMN_NAME_DIG_DICT_KEY = "fk_dig_dict_key";
        public static final String COLUMN_NAME_DIG_STATUS = "dig_status";
        public static final String COLUMN_NAME_DIG_PRIMARY_INDICATOR = "primary_indicator";
    }

    public static abstract class ChiefComplaintTable implements BaseColumns{
        public static final String TABLE_NAME = "chief_complaints";
        public static final String COLUMN_NAME_COMPLAINT_ENT_KEY = "fk_complaint_ent_key";
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
        public static final String COLUMN_NAME_DEPT_ORG_KEY = "fk_dept_org_key";
    }

    public static abstract class DictDiagnosisTable implements BaseColumns{
        public static final String TABLE_NAME = "dict_diagnosis";
        public static final String COLUMN_NAME_DICT_NAME = "diagnosis_name";
        public static final String COLUMN_NAME_DICT_CODE = "diagnosis_code";
        public static final String COLUMN_NAME_DICT_DESC = "diagnosis_desc";
    }

    public static abstract class MedicationDictTable implements BaseColumns{
        public static final String TABLE_NAME = "dict_medication";
        public static final String COLUMN_NAME_DICT_MED_NAME = "med_name";
        public static final String COLUMN_NAME_DICT_MED_GENERAL_NAME = "med_general_name";
        public static final String COLUMN_NAME_DICT_MED_CODE = "med_code";
        public static final String COLUMN_NAME_DICT_MED_SPEC = "med_spec";
        public static final String COLUMN_NAME_DICT_MED_MANUFACTURER = "med_manufacturer";
    }

    public static abstract class PhysicianTable implements BaseColumns{
        public static final String TABLE_NAME = "physician";
        public static final String COLUMN_NAME_PHYSICIAN_NAME = "physician_name";
        public static final String COLUMN_NAME_PHYSICIAN_ID = "physician_id";
        public static final String COLUMN_NAME_PHYSICIAN_DEPT_KEY = "fk_physician_dept_key";
    }

    public static abstract class ObservationDefTable implements BaseColumns{
        public static final String TABLE_NAME = "observation_def";
        public static final String COLUMN_NAME_OBR_DEF_NAME = "obr_def_name";
        public static final String COLUMN_NAME_OBR_DEF_CODE = "obr_def_code";
        public static final String COLUMN_NAME_OBR_DEF_NORMAL_RANGE = "normal_range";
        public static final String COLUMN_NAME_OBR_DEF_CODE_SYSTEM_KEY = "fk_obr_def_code_system_key";
    }

    public static abstract class RequestedProcedureTypeDefTable implements BaseColumns{
        public static final String TABLE_NAME = "rp_type_def";
        public static final String COLUMN_NAME_RP_DEF_NAME = "rp_name";
        public static final String COLUMN_NAME_RP_DEF_CODE = "rp_code";
        public static final String COLUMN_NAME_RP_DEF_CODE_SYSTEM_OID = "code_system_oid";
        public static final String COLUMN_NAME_RP_DEF_CODE_SYSTEM_NAME = "code_system_name";
    }

    public static abstract class BodypartDefTable implements BaseColumns{
        public static final String TABLE_NAME = "body_part_def";
        public static final String COLUMN_NAME_BODY_PART_DEF_NAME = "bp_name";
        public static final String COLUMN_NAME_BODY_PART_DEF_CODE = "bp_code";
        public static final String COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_OID = "code_system_oid";
        public static final String COLUMN_NAME_BODY_PART_DEF_CODE_SYSTEM_NAME = "code_system_name";
    }

    public static abstract class OrderCodeDefTable implements BaseColumns{
        public static final String TABLE_NAME = "order_code_def";
        public static final String COLUMN_NAME_ORDER_CODE_DEF_NAME = "order_name";
        public static final String COLUMN_NAME_ORDER_CODE_DEF_CODE = "order_code";
        public static final String COLUMN_NAME_ORDER_CODE_DEF_CODE_SYSTEM_KEY = "fk_order_def_code_system_key";
    }

    public static abstract class SpecimenTypeCodeDefTable implements BaseColumns{
        public static final String TABLE_NAME = "specimen_code_def";
        public static final String COLUMN_NAME_SPECIMEN_CODE_DEF_NAME = "specimen_type_name";
        public static final String COLUMN_NAME_SPECIMEN_CODE_DEF_CODE = "specimen_type_code";
        public static final String COLUMN_NAME_SPECIMEN_CODE_DEF_CODE_SYSTEM_KEY = "fk_specimen_code_system_key";
    }

    public static abstract class CodeSystemTable implements BaseColumns{
        public static final String TABLE_NAME = "code_system";
        public static final String COLUMN_NAME_CODE_SYSTEM_OID = "code_system_oid";
        public static final String COLUMN_NAME_CODE_SYSTEM_NAME = "code_system_name";
    }


}
