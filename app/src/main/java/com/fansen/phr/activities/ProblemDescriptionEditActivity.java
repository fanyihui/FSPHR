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
import com.fansen.phr.fragment.details.ProblemsFragment;

public class ProblemDescriptionEditActivity extends AppCompatActivity {
    public static final String PROBLEM_DESC_KEY = "problem_desc";

    private EditText problemDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_description_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        problemDescriptionText = (EditText) findViewById(R.id.id_problems_desc_edit);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String desc = bundle.getString(ProblemsFragment.BUNDLE_KEY_DESC);
        problemDescriptionText.setText(desc);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_problem_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.id_action_submit_problem_desc){
            //TODO add code here to get the problem description and send back to ProblemsFragment
            String desc = problemDescriptionText.getText().toString();

            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(PROBLEM_DESC_KEY, desc);
            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }

        return true;
    }
}
