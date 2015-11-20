package com.fansen.phr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fansen.phr.R;
import com.fansen.phr.view.FreeTextFragment;

public class FreeTextEditActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_FREE_TEXT_VALUE = "value";
    public static final String BUNDLE_KEY_FREE_TEXT_HINT = "hint";
    public static final String BUNDLE_KEY_FREE_TEXT_TITLE = "title";

    FreeTextFragment freeTextFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_text_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String value = bundle.getString(BUNDLE_KEY_FREE_TEXT_VALUE);
        String hint = bundle.getString(BUNDLE_KEY_FREE_TEXT_HINT);
        String title = bundle.getString(BUNDLE_KEY_FREE_TEXT_TITLE);

        freeTextFragment = FreeTextFragment.newInstance(value, hint);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_free_edit_text_container, freeTextFragment);
        fragmentTransaction.commit();

        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_free_text_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            String value = freeTextFragment.getValue();

            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_KEY_FREE_TEXT_VALUE, value);
            data.putExtras(bundle);

            setResult(RESULT_OK, data);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
