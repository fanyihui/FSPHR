package com.fansen.phr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fansen.phr.R;
import com.fansen.phr.entity.ObservationType;
import com.fansen.phr.fragment.details.ProblemsFragment;

public class EncounterProblemEditActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_PROBLEM_VALUE = "problem_value";

    private EditText encounterProblemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encounter_problem_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String type = bundle.getString(ProblemsFragment.BUNDLE_KEY_PROBLEM_TYPE);
        String value = bundle.getString(ProblemsFragment.BUNDLE_KEY_PROBLEM_VALUE);

        encounterProblemEditText = (EditText) findViewById(R.id.id_encounter_problem_edit_text);

        if (type.equals(ObservationType.CURRENT_PROBLEM.getName())){
            toolbar.setTitle(R.string.title_activity_encounter_problem_current);
            encounterProblemEditText.setHint(R.string.hint_current_problems_guide);
        } else if (type.equals(ObservationType.HISTORICAL_PROBLEM.getName())){
            toolbar.setTitle(R.string.title_activity_encounter_problem_historical);
            encounterProblemEditText.setHint(R.string.hint_historical_problems_guide);
        } else if (type.equals(ObservationType.PHYSICAL_EXAM.getName())){
            toolbar.setTitle(R.string.title_activity_encounter_problem_physical_exam);
            encounterProblemEditText.setHint(R.string.hint_physical_exam_guide);
        }

        encounterProblemEditText.setText(value);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_encounter_problem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.id_action_submit_problem_desc){
            //TODO add code here to get the problem description and send back to ProblemsFragment
            String value = encounterProblemEditText.getText().toString();

            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_KEY_PROBLEM_VALUE, value);
            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }

        return true;
    }
}
