package com.fansen.phr.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OutpatientActivity extends AppCompatActivity {
    private TextView textViewEntDate = null;
    private EditText editTextHospital = null;
    private EditText editTextDept = null;
    private EditText editTextDiag = null;
    private EditText editTextDoctor = null;
    private Calendar cal = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    public static final String ENT_KEY = "com.fansen.phr.entity.encounter";
    public static final String KEY_ORG = "organization";
    public static final String KEY_DEPT = "department";
    public static final String KEY_DIAG = "diagnosis";
    public static final String KEY_DATE = "date";
    public static final String KEY_DOCTOR = "attending_doctor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outpatient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_newoutpatient);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewEntDate = (TextView) findViewById(R.id.id_op_date);
        textViewEntDate.setText(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
        textViewEntDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(OutpatientActivity.this, listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        editTextHospital = (EditText) findViewById(R.id.id_op_org);
        editTextDept = (EditText) findViewById(R.id.id_op_dept);
        editTextDiag = (EditText) findViewById(R.id.id_op_diag);
        editTextDoctor = (EditText) findViewById(R.id.id_attending_doct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_outpatient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit_outpatient) {

            //Encounter encounter = new Encounter();
            //Department department = new Department();


            String hospital = editTextHospital.getText().toString();
            String dept = editTextDept.getText().toString();
            String diag = editTextDiag.getText().toString();
            String date = textViewEntDate.getText().toString();
            String doctor = editTextDoctor.getText().toString();

            Intent data = new Intent();

            Bundle bundle = new Bundle();
            bundle.putString(KEY_DEPT, dept);
            bundle.putString(KEY_ORG, hospital);
            bundle.putString(KEY_DIAG, diag);
            bundle.putString(KEY_DATE, date);
            bundle.putString(KEY_DOCTOR, doctor);

            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }

        return true;
    }

    private void updateDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        textViewEntDate.setText(simpleDateFormat.format(cal.getTime()));
    }
}
