package com.fansen.phr.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.fragment.details.PrescriptionFragment;
import com.fansen.phr.utils.SpinnerUtil;
import com.fansen.phr.utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MedicationOrderEditActivity extends AppCompatActivity {
    public static final String MED_NAME = "medication_name";
    public static final String MED_SPEC = "medication_spec";
    public static final String QUANTITY = "quantity";
    public static final String QUANTITY_UNIT = "quantity_unit";
    public static final String FREQ_INTERVAL = "frequency_interval";
    public static final String FREQ_INTERVAL_UNIT = "frequency_interval_unit";
    public static final String FREQ_TIMES = "frequency_times";
    public static final String DOSAGE = "dosage";
    public static final String DOSAGE_UNIT ="dosage_unit";
    public static final String ROUTE ="route";
    public static final String PRN = "prn";
    public static final String START_TIME = "start_time";


    private EditText medicationNameEditText;
    private EditText medicationSpecEditText;
    private EditText quantityEditText;
    private Spinner quantityUnitSpinner;
    private EditText frequencyIntervalEditText;
    private Spinner intervalUnitSpinner;
    private EditText frequencyTimesEditText;
    private EditText dosageEditText;
    private Spinner dosageUnitSpinner;
    private Spinner routeSpinner;
    private CheckBox prnCheckBox;
    private TextView startTimeTextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_order_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //initial all UI elements
        medicationNameEditText = (EditText) findViewById(R.id.id_med_order_edit_name);
        medicationSpecEditText = (EditText) findViewById(R.id.id_med_order_edit_spec);
        quantityEditText = (EditText) findViewById(R.id.id_med_order_edit_quantity);
        quantityUnitSpinner = (Spinner) findViewById(R.id.id_med_order_edit_quantity_unit);
        frequencyIntervalEditText = (EditText) findViewById(R.id.id_med_order_edit_frequency_interval);
        intervalUnitSpinner = (Spinner) findViewById(R.id.id_med_order_edit_frequency_interval_unit);
        frequencyTimesEditText = (EditText) findViewById(R.id.id_med_order_edit_frequency_times);
        dosageEditText = (EditText) findViewById(R.id.id_med_order_edit_dosage);
        dosageUnitSpinner = (Spinner) findViewById(R.id.id_med_order_edit_dosage_unit);
        routeSpinner = (Spinner) findViewById(R.id.id_med_order_edit_route);
        prnCheckBox = (CheckBox) findViewById(R.id.id_med_order_edit_prn);

        startTimeTextView = (TextView) findViewById(R.id.id_med_order_edit_start_time);
        startTimeTextView.setText(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(MedicationOrderEditActivity.this, listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            MedicationOrder medicationOrder = (MedicationOrder)bundle.getSerializable(PrescriptionFragment.BUNDLE_KEY_SELECTED_MED_ORDER);

            medicationNameEditText.setText(medicationOrder.getMedication().getName());
            medicationSpecEditText.setText(medicationOrder.getMedication().getSpec());
            quantityEditText.setText(medicationOrder.getQuantity()+"");
            SpinnerUtil.setSpinnerItemSelectedByValue(quantityUnitSpinner, medicationOrder.getQuantity_unit());
            frequencyIntervalEditText.setText(medicationOrder.getFrequency_interval() + "");
            SpinnerUtil.setSpinnerItemSelectedByValue(intervalUnitSpinner, medicationOrder.getFrequency_interval_unit());
            frequencyTimesEditText.setText(medicationOrder.getFrequency_times() + "");
            dosageEditText.setText(medicationOrder.getDosage() + "");
            SpinnerUtil.setSpinnerItemSelectedByValue(dosageUnitSpinner, medicationOrder.getDosage_unit());
            SpinnerUtil.setSpinnerItemSelectedByValue(routeSpinner, medicationOrder.getRoute());
            prnCheckBox.setChecked(medicationOrder.getPRNIndicator()==1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_medication_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit_medication_order){
            String name = medicationNameEditText.getText().toString();
            String spec = medicationSpecEditText.getText().toString();
            String quantity = quantityEditText.getText().toString();
            String quantity_unit = quantityUnitSpinner.getSelectedItem().toString();
            String interval = frequencyIntervalEditText.getText().toString();
            String interval_unit = intervalUnitSpinner.getSelectedItem().toString();
            String times = frequencyTimesEditText.getText().toString();
            String dosage = dosageEditText.getText().toString();
            String dosage_unit = dosageUnitSpinner.getSelectedItem().toString();
            String route = routeSpinner.getSelectedItem().toString();
            boolean prnChecked = prnCheckBox.isChecked();
            String start_time = startTimeTextView.getText().toString();

            Intent intent = new Intent();

            Bundle bundle = new Bundle();
            bundle.putString(MED_NAME, name);
            bundle.putString(MED_SPEC, spec);
            bundle.putString(QUANTITY, quantity);
            bundle.putString(QUANTITY_UNIT, quantity_unit);
            bundle.putString(FREQ_INTERVAL, interval);
            bundle.putString(FREQ_INTERVAL_UNIT, interval_unit);
            bundle.putString(FREQ_TIMES, times);
            bundle.putString(DOSAGE, dosage);
            bundle.putString(DOSAGE_UNIT, dosage_unit);
            bundle.putString(ROUTE, route);
            bundle.putBoolean(PRN, prnChecked);
            bundle.putString(START_TIME, start_time);

            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);

            finish();
        }

        return true;
    }

    private void updateDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        startTimeTextView.setText(simpleDateFormat.format(cal.getTime()));
    }
}
