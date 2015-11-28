package com.fansen.phr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fansen.phr.R;
import com.fansen.phr.entity.LabObservation;
import com.fansen.phr.entity.ObservationDef;

import java.util.Objects;

public class LabResultItemEditingActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_LAB_OBSERVATION = "lab_observation";

    private EditText codeEditText;
    private EditText nameEditText;
    private EditText valueEditText;
    private EditText unitEditText;
    private EditText rangeEditText;

    private LabObservation labObservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_result_item_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        codeEditText = (EditText) findViewById(R.id.id_lab_result_item_editing_code);
        nameEditText = (EditText) findViewById(R.id.id_lab_result_item_editing_name);
        valueEditText = (EditText) findViewById(R.id.id_lab_result_item_editing_value);
        unitEditText = (EditText) findViewById(R.id.id_lab_result_item_editing_unit);
        rangeEditText = (EditText) findViewById(R.id.id_lab_result_item_editing_range);

        Intent data = getIntent();
        Bundle bundle = data.getExtras();

        if(bundle != null){
            Object object = bundle.getSerializable(BUNDLE_KEY_LAB_OBSERVATION);

            labObservation = (LabObservation) object;
            codeEditText.setText(labObservation.getObservationDef().getCode());
            nameEditText.setText(labObservation.getObservationDef().getName());
            rangeEditText.setText(labObservation.getObservationDef().getNormalRange());
            valueEditText.setText(labObservation.getValue());
            unitEditText.setText(labObservation.getUnit());
        } else {
            labObservation = new LabObservation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lab_result_item_editing_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit_lab_report){
            labObservation.setUnit(unitEditText.getText().toString());
            labObservation.setValue(valueEditText.getText().toString());
            ObservationDef observationDef = new ObservationDef();
            observationDef.setCode(codeEditText.getText().toString());
            observationDef.setName(nameEditText.getText().toString());
            observationDef.setNormalRange(rangeEditText.getText().toString());
            labObservation.setObservationDef(observationDef);

            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_LAB_OBSERVATION, labObservation);
            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}