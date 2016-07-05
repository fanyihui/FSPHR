package com.fansen.phr.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.adapter.MedicationReminderAdapter;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MedicationReminderTimes;
import com.fansen.phr.fragment.details.PrescriptionFragment;
import com.fansen.phr.utils.SpinnerUtil;
import com.fansen.phr.utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MedicationOrderEditActivity extends AppCompatActivity {
    private Context context;

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
    public static final String ORDER_NOTES = "order_notes";
    public static final String REMINDER_TIMES = "reminder_times";


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
    private EditText notesEditText;
    private ListView reminderTimes;

    private MedicationReminderAdapter medicationReminderAdapter;
    private ArrayList<MedicationReminderTimes> reminderTimeList = new ArrayList<>();

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
        context = this;

        setContentView(R.layout.activity_medication_order_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //initial all UI elements
        reminderTimes = (ListView) findViewById(R.id.id_med_order_edit_reminder_time_setting);
        medicationNameEditText = (EditText) findViewById(R.id.id_med_order_edit_name);
        medicationSpecEditText = (EditText) findViewById(R.id.id_med_order_edit_spec);
        quantityEditText = (EditText) findViewById(R.id.id_med_order_edit_quantity);
        quantityUnitSpinner = (Spinner) findViewById(R.id.id_med_order_edit_quantity_unit);
        frequencyIntervalEditText = (EditText) findViewById(R.id.id_med_order_edit_frequency_interval);
        intervalUnitSpinner = (Spinner) findViewById(R.id.id_med_order_edit_frequency_interval_unit);
        frequencyTimesEditText = (EditText) findViewById(R.id.id_med_order_edit_frequency_times);
        frequencyTimesEditText.addTextChangedListener(new TextWatcher() {
            String priorStr;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                priorStr = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = frequencyTimesEditText.getText().toString();

                if((!value.equals("")) && (!value.equals(priorStr))){
                    reminderTimeList = new ArrayList<>();

                    int freqTimes = Integer.valueOf(frequencyTimesEditText.getText().toString());
                    for (int i=0; i<freqTimes; i++){
                        MedicationReminderTimes medicationReminderTimes = new MedicationReminderTimes();
                        medicationReminderTimes.setSequenceNumber(i+1);
                        medicationReminderTimes.setReminderTime("08:00");
                        reminderTimeList.add(medicationReminderTimes);
                    }

                    medicationReminderAdapter = new MedicationReminderAdapter(context, reminderTimeList);
                    reminderTimes.setAdapter(medicationReminderAdapter);
                }
            }
        });


        dosageEditText = (EditText) findViewById(R.id.id_med_order_edit_dosage);
        dosageUnitSpinner = (Spinner) findViewById(R.id.id_med_order_edit_dosage_unit);
        routeSpinner = (Spinner) findViewById(R.id.id_med_order_edit_route);
        prnCheckBox = (CheckBox) findViewById(R.id.id_med_order_edit_prn);

        startTimeTextView = (TextView) findViewById(R.id.id_med_order_edit_start_time);
        startTimeTextView.setText(TimeFormat.parseDate(new Date()));
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(MedicationOrderEditActivity.this, listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        notesEditText = (EditText) findViewById(R.id.id_med_order_edit_notes);

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
            prnCheckBox.setChecked(medicationOrder.getPRNIndicator() == 1);
            notesEditText.setText(medicationOrder.getNotes());

            //TODO add code here to initial the reminder times from database.
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
            String notes = notesEditText.getText().toString();

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
            bundle.putString(ORDER_NOTES, notes);
            bundle.putSerializable(REMINDER_TIMES, reminderTimeList);


            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);

            finish();
        }

        return true;
    }

    private void updateDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeFormat.DATEFORMAT);
        startTimeTextView.setText(simpleDateFormat.format(cal.getTime()));
    }
}
