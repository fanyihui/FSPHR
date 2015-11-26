package com.fansen.phr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fansen.phr.R;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.LabReport;
import com.fansen.phr.fragment.details.LabReportDetailFragment;
import com.fansen.phr.utils.DensityUtil;
import com.fansen.phr.view.SelectPicPopWindow;

public class LabReportDetailActivity extends AppCompatActivity implements LabReportDetailFragment.OnLabReportDataChangeListener{
    public static final String BUNDEL_KEY_LAB_REPORT = "lab_report";
    public static final String BUNDLE_KEY_IS_EDITING = "is_editing_mode";

    public static final int REQUEST_CODE_EDITING_ORDER = 3001;
    public static final int REQUEST_CODE_EDITING_SPECIMEN = 3002;

    private Context context;
    LabReportDetailFragment labReportDetailFragment;

    private Encounter currentEncounter;
    private LabReport currentLabReport;

    private String currentImageFilePath;

    SelectPicPopWindow popWindow;

    FragmentManager fragmentManager;
    private int gridViewColumnWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        context = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentEncounter = (Encounter) bundle.getSerializable(OutpatientDetailActivity.BUNDLE_KEY_SELECTED_ENCOUNTER);

        Object object = bundle.getSerializable(OutpatientDetailActivity.BUNDLE_KEY_SELECTED_REPORT);
        boolean isEditingReport = false;

        if (object != null){
            currentLabReport = (LabReport) object;
            isEditingReport = true;
        } else {
            currentLabReport = new LabReport();
        }

        labReportDetailFragment = LabReportDetailFragment.newInstance(currentLabReport, isEditingReport);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.id_lab_report_fragment_container, labReportDetailFragment);
        fragmentTransaction.commit();

        gridViewColumnWidth = DensityUtil.dip2px(context, getResources().getDimension(R.dimen.grid_view_column_width));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lab_report_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_submit_lab_report){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_EDITING_ORDER:
                    labReportDetailFragment.setLabReportOrder(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_EDITING_SPECIMEN:
                    labReportDetailFragment.setLabReportSpecimenType(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAddLabResultItem() {
        //TODO add code here to open the LabResultItemActivity to edit the item
    }

    @Override
    public void onAddLabReportRefImage(View v) {
        //TODO add code here to open the dialog for taking or selecting pictures
    }

    @Override
    public void onEditLabResultItem() {

    }

    @Override
    public void onViewRefImage() {

    }

    @Override
    public void onOrderContentEditing(String order) {
        String hint = getResources().getString(R.string.hint_lab_report_order);
        dispatchFreeTextEditingIntent(order, hint, REQUEST_CODE_EDITING_ORDER);
    }

    @Override
    public void onSpecimenTextEditing(String specimenType) {
        String hint = getResources().getString(R.string.hint_lab_report_specimen);;
        dispatchFreeTextEditingIntent(specimenType, hint, REQUEST_CODE_EDITING_SPECIMEN);
    }

    private void dispatchFreeTextEditingIntent(String value, String hint, int requestCode){
        Intent intent = new Intent(context, FreeTextEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE, value);
        bundle.putString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_HINT, hint);
        bundle.putString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_TITLE, hint);

        intent.putExtras(bundle);

        startActivityForResult(intent, requestCode);
    }
}
