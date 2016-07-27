package com.fansen.phr.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.utils.TextUtil;
import com.fansen.phr.utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EncounterCoreInfoActivity extends AppCompatActivity {
    public static final int EDIT_DIAGNOSIS_REQUEST = 10;
    public static final String DELIMITER = "，";

    private TextView textViewEntDate = null;
    private TextView textViewEntDischargeDate = null;
    private EditText editTextHospital = null;
    private EditText editTextDept = null;
    private TextView textViewDiagnosis = null;
    private EditText editTextDoctor = null;
    private Spinner patientClassSpinner = null;

    private RelativeLayout dischargeSectionLayout = null;

    private Calendar cal = Calendar.getInstance();

    private ArrayList<String> diagnosisList = new ArrayList<>();

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    private DatePickerDialog.OnDateSetListener dischargeDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDischargeDate();
        }
    };

    public static final String ENT_KEY = "com.fansen.phr.entity.encounter";
    public static final String KEY_ORG = "organization";
    public static final String KEY_DEPT = "department";
    public static final String KEY_DIAG = "diagnosis";
    public static final String KEY_DATE = "date";
    public static final String KEY_DOCTOR = "attending_doctor";
    public static final String KEY_PATIENT_CLASS = "patient_class";
    public static final String KEY_DISCHARGE_DATE = "discharge_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encounter_core_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_newoutpatient);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewEntDate = (TextView) findViewById(R.id.id_op_date);
        textViewEntDate.setText(TimeFormat.parseDate(new Date()));
        textViewEntDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(EncounterCoreInfoActivity.this, listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        textViewEntDischargeDate = (TextView) findViewById(R.id.id_new_ent_discharge_date);
        textViewEntDischargeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EncounterCoreInfoActivity.this, dischargeDateListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        editTextHospital = (EditText) findViewById(R.id.id_op_org);
        editTextDept = (EditText) findViewById(R.id.id_op_dept);

        textViewDiagnosis = (TextView) findViewById(R.id.id_op_diag);
        textViewDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchEditDiagnosisIntent();
            }
        });

        editTextDoctor = (EditText) findViewById(R.id.id_bar_attending_doct);

        dischargeSectionLayout = (RelativeLayout) findViewById(R.id.id_new_ent_discharge_date_section);

        patientClassSpinner = (Spinner) findViewById(R.id.id_new_ent_patient_class);
        patientClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String patientClass = parent.getItemAtPosition(position).toString();
                if (patientClass.equals(view.getContext().getResources().getString(R.string.patient_class_inpatient))){
                    dischargeSectionLayout.setVisibility(View.VISIBLE);
                    textViewEntDischargeDate.setText(TimeFormat.parseDate(new Date()));
                } else{
                    dischargeSectionLayout.setVisibility(View.GONE);
                    textViewEntDischargeDate.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_outpatient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit_outpatient) {
            String hospital = editTextHospital.getText().toString();
            String dept = editTextDept.getText().toString();
            String date = textViewEntDate.getText().toString();
            String doctor = editTextDoctor.getText().toString();
            String patientClass = patientClassSpinner.getSelectedItem().toString();
            String dischargeDate = textViewEntDischargeDate.getText().toString();

            Intent data = new Intent();

            Bundle bundle = new Bundle();
            bundle.putString(KEY_DEPT, dept);
            bundle.putString(KEY_ORG, hospital);
            bundle.putSerializable(KEY_DIAG, diagnosisList);
            bundle.putString(KEY_DATE, date);
            bundle.putString(KEY_DOCTOR, doctor);
            bundle.putString(KEY_PATIENT_CLASS, patientClass);
            bundle.putString(KEY_DISCHARGE_DATE, dischargeDate);


            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case EDIT_DIAGNOSIS_REQUEST:
                    handleDiagnosisResult(data);
                    break;
                default:
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void dispatchEditDiagnosisIntent(){
        //TODO open activity to edit diagnosis
        String hint = getResources().getString(R.string.hint_diagnosis_edit);
        String title = getResources().getString(R.string.title_activity_edit_diagnosis);
        String diagnosis = textViewDiagnosis.getText().toString();
        ArrayList<String> arrayList = TextUtil.toArrayList(diagnosis, DELIMITER);

        Intent intent = new Intent(this, SimpleListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(SimpleListActivity.BUNDLE_KEY_SIMPLE_LIST, arrayList);
        bundle.putString(SimpleListActivity.BUNDLE_KEY_SIMPLE_LIST_HINT, hint);
        bundle.putString(SimpleListActivity.BUNDLE_KEY_SIMPLE_LIST_TITLE, title);

        intent.putExtras(bundle);

        startActivityForResult(intent, EDIT_DIAGNOSIS_REQUEST);
    }

    private void handleDiagnosisResult(Intent data){
        Bundle bundle = data.getExtras();
        diagnosisList = (ArrayList) bundle.getSerializable(SimpleListActivity.BUNDLE_KEY_SIMPLE_LIST);

        String diagnosis = TextUtil.toString(diagnosisList, DELIMITER);

        textViewDiagnosis.setText(diagnosis);
    }

    private void updateDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        textViewEntDate.setText(simpleDateFormat.format(cal.getTime()));
    }

    private void updateDischargeDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        textViewEntDischargeDate.setText(simpleDateFormat.format(cal.getTime()));
    }
}
