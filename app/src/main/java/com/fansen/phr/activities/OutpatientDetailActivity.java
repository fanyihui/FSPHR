package com.fansen.phr.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.fragment.PhrFragment;
import com.fansen.phr.fragment.details.PrescriptionFragment;
import com.fansen.phr.fragment.details.ProblemsFragment;
import com.fansen.phr.fragment.details.ProcedureFragment;
import com.fansen.phr.fragment.details.RequestedProcedureFragment;
import com.fansen.phr.utils.TimeFormat;

public class OutpatientDetailActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView textDepartmentView;
    private TextView textOrgView;
    private TextView textAdmitDateView;
    private TextView textAttendingDoctor;
    private TextView textPrimaryDiagnosis;

    private Encounter selectedEncounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedEncounter = (Encounter) bundle.getSerializable(PhrFragment.OPEN_ENT_KEY);

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
                    return RequestedProcedureFragment.newInstance();
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
