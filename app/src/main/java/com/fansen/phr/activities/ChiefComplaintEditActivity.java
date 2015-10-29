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
import android.widget.Spinner;
import android.widget.TextView;

import com.fansen.phr.R;

public class ChiefComplaintEditActivity extends AppCompatActivity {
    public static final String COMPLAINT_KEY = "complaint";
    public static final String DURATION_KEY = "duration";
    public static final String DURATION_UNIT_KEY = "duration_unit";

    private EditText complaintTextView;
    private EditText durationTextView;
    private Spinner spinnerUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_complaint_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        complaintTextView = (EditText) findViewById(R.id.text_symptom);
        durationTextView = (EditText) findViewById(R.id.text_symptom_duration);
        spinnerUnit = (Spinner) findViewById(R.id.spinner_duration_unit);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_complaint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit_complaint){
            String complaint = complaintTextView.getText().toString();
            String duration = durationTextView.getText().toString();
            String unit = spinnerUnit.getSelectedItem().toString();

            Intent data = new Intent();

            Bundle bundle = new Bundle();
            bundle.putString(COMPLAINT_KEY, complaint);
            bundle.putString(DURATION_KEY, duration);
            bundle.putString(DURATION_UNIT_KEY, unit);

            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }

        return true;
    }
}
