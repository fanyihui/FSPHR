package com.fansen.phr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.fansen.phr.R;
import com.fansen.phr.entity.ChiefComplaint;
import com.fansen.phr.fragment.details.ProblemsFragment;
import com.fansen.phr.utils.SpinnerUtil;

public class ChiefComplaintEditActivity extends AppCompatActivity {
    public static final String COMPLAINT_KEY = "complaint";
    public static final String DURATION_KEY = "duration";
    public static final String DURATION_UNIT_KEY = "duration_unit";
    public static final String ID_KEY = "key";

    private EditText complaintTextView;
    private EditText durationTextView;
    private Spinner spinnerUnit;

    private int selectedChiefComplaintKey = -1;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            ChiefComplaint chiefComplaint = (ChiefComplaint) bundle.getSerializable(ProblemsFragment.BUNDLE_KEY_SELECTED_COMPLAINT);

            complaintTextView.setText(chiefComplaint.getSymptom());
            durationTextView.setText(chiefComplaint.getDuration());
            SpinnerUtil.setSpinnerItemSelectedByValue(spinnerUnit, chiefComplaint.getDuration_unit());
            selectedChiefComplaintKey = chiefComplaint.getKey();
        }
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
            bundle.putInt(ID_KEY, selectedChiefComplaintKey);

            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }

        return true;
    }
}
