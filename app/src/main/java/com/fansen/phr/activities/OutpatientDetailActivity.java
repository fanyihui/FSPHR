package com.fansen.phr.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.adapter.DiagnosticImagingReportListAdapter;
import com.fansen.phr.entity.BodyPartDef;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.DiagnosticImagingReport;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.RequestedProcedureTypeDef;
import com.fansen.phr.fragment.PhrFragment;
import com.fansen.phr.fragment.details.PrescriptionFragment;
import com.fansen.phr.fragment.details.ProblemsFragment;
import com.fansen.phr.fragment.details.ProcedureFragment;
import com.fansen.phr.fragment.details.DiagnosticImagingReportListFragment;
import com.fansen.phr.service.IDiagnosisDictService;
import com.fansen.phr.service.IDiagnosticImageService;
import com.fansen.phr.service.IDiagnosticImagingReportService;
import com.fansen.phr.service.ITerminologyService;
import com.fansen.phr.service.implementation.DiagnosticImageServiceLocalImpl;
import com.fansen.phr.service.implementation.DiagnosticImagingReportServiceLocalImpl;
import com.fansen.phr.service.implementation.TermilologyServiceLocalImpl;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.TimeFormat;

import java.util.List;

public class OutpatientDetailActivity extends AppCompatActivity implements DiagnosticImagingReportListFragment.OnDIRItemSelectedListener{
    public static final int ADD_DIR_REQUEST_CODE = 1000;
    public static final int EDIT_DIR_REQUEST_CODE = 1001;

    public static final String BUNDLE_KEY_SELECTED_ENCOUNTER = "selected_encounter";
    public static final String BUNDLE_KEY_SELECTED_REPORT = "selected_di_report";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView textDepartmentView;
    private TextView textOrgView;
    private TextView textAdmitDateView;
    private TextView textAttendingDoctor;
    private TextView textPrimaryDiagnosis;
    private FloatingActionButton floatingActionButton;

    IDiagnosticImagingReportService diagnosticImagingReportService;
    ITerminologyService terminologyService;
    IDiagnosticImageService diagnosticImageService;


    private Encounter selectedEncounter;
    private int currentEditingItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.id_encounter_detail_fab_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFABAction();
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedEncounter = (Encounter) bundle.getSerializable(PhrFragment.OPEN_ENT_KEY);

        terminologyService = new TermilologyServiceLocalImpl(this);
        diagnosticImagingReportService = new DiagnosticImagingReportServiceLocalImpl(this);
        diagnosticImageService = new DiagnosticImageServiceLocalImpl(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        textDepartmentView = (TextView) findViewById(R.id.ent_dept);
        textOrgView = (TextView) findViewById(R.id.ent_org);
        textAdmitDateView = (TextView) findViewById(R.id.ent_date);
        textPrimaryDiagnosis = (TextView) findViewById(R.id.id_ent_primary_diagnosis);
        textAttendingDoctor = (TextView) findViewById(R.id.id_bar_attending_doct);

        textAdmitDateView.setText(TimeFormat.parseDate(selectedEncounter.getAdmit_date(), "yyyyMMdd"));
        textOrgView.setText(selectedEncounter.getOrg().getOrg_name());
        textDepartmentView.setText(selectedEncounter.getDepartment().getName());
        textAttendingDoctor.setText(selectedEncounter.getAttendingDoctor().getPhysicianName());
        textPrimaryDiagnosis.setText(selectedEncounter.getPrimaryDiagnosis().getName());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_outpatient_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    private void handleFABAction(){
        int position = mViewPager.getCurrentItem();
        switch (position){
            case 0:
                break;
            case 1:
                dispatchAddDirRequestIntent();
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    private void dispatchAddDirRequestIntent(){
        Intent intent = new Intent(this, ImagingReportDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_SELECTED_ENCOUNTER, selectedEncounter);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_DIR_REQUEST_CODE);
    }

    private void dispatchEditDirRequestIntent(DiagnosticImagingReport diagnosticImagingReport){
        Intent intent = new Intent(this, ImagingReportDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_SELECTED_ENCOUNTER, selectedEncounter);
        bundle.putSerializable(BUNDLE_KEY_SELECTED_REPORT, diagnosticImagingReport);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_DIR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ADD_DIR_REQUEST_CODE:
                    handleAddDirRequest(data);
                    break;
                case EDIT_DIR_REQUEST_CODE:
                    handleEditingDirRequest(data);
                    break;
                default:
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDIRItemSelected(int position, DiagnosticImagingReport diagnosticImagingReport) {
        //This is an implementation from DiagnosticImagingReportFragment.OnDIRItemSelectedListener
        currentEditingItemPosition = position;
        dispatchEditDirRequestIntent(diagnosticImagingReport);
    }

    private void handleAddDirRequest(Intent data){
        Bundle bundle = data.getExtras();
        DiagnosticImagingReport  diagnosticImagingReport = (DiagnosticImagingReport) bundle.getSerializable(ImagingReportDetailActivity.BUNDLE_KEY_DI_REPORT);

        DiagnosticImagingReportListFragment fragment  = (DiagnosticImagingReportListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        fragment.addDiagnosticImagingReport(diagnosticImagingReport);

        //TODO put following code to a background thread to improve the performance
        RequestedProcedureTypeDef requestedProcedureTypeDef = diagnosticImagingReport.getRequestedProcedureTypeDef();
        int rpdef_id = terminologyService.addRequestedProcedureCode(requestedProcedureTypeDef);
        requestedProcedureTypeDef.set_id(rpdef_id);

        BodyPartDef bodyPartDef = diagnosticImagingReport.getBodypart();
        int bodypartId = terminologyService.addBodyPartCode(bodyPartDef);
        bodyPartDef.set_id(bodypartId);

        int dirId = diagnosticImagingReportService.addDiagnosticImagingReport(selectedEncounter.getEncounter_key(), diagnosticImagingReport);
        diagnosticImagingReport.set_id(dirId);

        List<DiagnosticImage> diagnosticImages = diagnosticImagingReport.getDiagnosticImages();
        diagnosticImageService.addDiagnosticImages(dirId, diagnosticImages);
    }

    private void handleEditingDirRequest(Intent data){
        Bundle bundle = data.getExtras();
        DiagnosticImagingReport  diagnosticImagingReport =
                (DiagnosticImagingReport) bundle.getSerializable(ImagingReportDetailActivity.BUNDLE_KEY_DI_REPORT);

        DiagnosticImagingReportListFragment fragment  =
                (DiagnosticImagingReportListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        fragment.updateDiagnosticImagingReport(currentEditingItemPosition, diagnosticImagingReport);


        RequestedProcedureTypeDef requestedProcedureTypeDef = diagnosticImagingReport.getRequestedProcedureTypeDef();
        int rpdef_id = terminologyService.addRequestedProcedureCode(requestedProcedureTypeDef);
        requestedProcedureTypeDef.set_id(rpdef_id);

        BodyPartDef bodyPartDef = diagnosticImagingReport.getBodypart();
        int bodypartId = terminologyService.addBodyPartCode(bodyPartDef);
        bodyPartDef.set_id(bodypartId);

        diagnosticImagingReportService.updateDiagnosticImagingReport(diagnosticImagingReport);

        List<DiagnosticImage> diagnosticImages = diagnosticImagingReport.getDiagnosticImages();
        diagnosticImageService.addDiagnosticImages(diagnosticImagingReport.get_id(), diagnosticImages);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ProblemsFragment.newInstance(selectedEncounter);
                case 1:
                    return DiagnosticImagingReportListFragment.newInstance(selectedEncounter);
                case 2:
                    return PrescriptionFragment.newInstance(selectedEncounter);
                case 3:
                    return new ProcedureFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_name_basic);
                case 1:
                    return getString(R.string.tab_name_exam);
                case 2:
                    return getString(R.string.tab_name_prescription);
                case 3:
                    return getString(R.string.tab_name_procedure);
            }
            return null;
        }
    }
}
