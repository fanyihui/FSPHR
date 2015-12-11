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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fansen.phr.R;

import java.util.ArrayList;

public class SimpleListActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_SIMPLE_LIST = "simple_list";
    public static final String BUNDLE_KEY_SIMPLE_LIST_HINT = "simple_list_hint";
    public static final String BUNDLE_KEY_SIMPLE_LIST_TITLE = "simple_list_title";

    private TextView hintTextView;
    private EditText itemContentEditText;
    private Button addNewItemBtn;
    private ListView itemListView;
    private TextView emptyTipsTextView;

    private ArrayAdapter<String> itemListAdapter;
    private ArrayList<String> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
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

        ArrayList<String> data = (ArrayList) bundle.getSerializable(BUNDLE_KEY_SIMPLE_LIST);
        String hint = bundle.getString(BUNDLE_KEY_SIMPLE_LIST_HINT);
        String title = bundle.getString(BUNDLE_KEY_SIMPLE_LIST_TITLE);

        getSupportActionBar().setTitle(title);

        if(data != null){
            itemList = data;
        }

        itemListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemList);

        hintTextView = (TextView) findViewById(R.id.id_hint_simple_list);
        hintTextView.setText(hint);

        itemContentEditText = (EditText) findViewById(R.id.id_simple_list_content_edit_text);

        addNewItemBtn = (Button) findViewById(R.id.id_simple_list_add_new_item);
        addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = itemContentEditText.getText().toString();
                if (item == null || item.isEmpty()){
                    return;
                }

                itemListAdapter.add(item);
                itemListAdapter.notifyDataSetChanged();
                emptyTipsTextView.setVisibility(View.GONE);
                itemContentEditText.setText("");
            }
        });

        itemListView = (ListView) findViewById(R.id.id_simple_list_list_view);
        itemListView.setAdapter(itemListAdapter);

        emptyTipsTextView = (TextView) findViewById(R.id.id_simple_list_empty_tips);

        if (itemListAdapter.getCount() > 0){
            emptyTipsTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simple_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_SIMPLE_LIST, itemList);
            data.putExtras(bundle);

            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
