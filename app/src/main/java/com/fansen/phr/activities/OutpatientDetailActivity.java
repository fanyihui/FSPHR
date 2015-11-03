package com.fansen.phr.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.Procedure;
import com.fansen.phr.fragment.PhrFragment;
import com.fansen.phr.fragment.details.PrescriptionFragment;
import com.fansen.phr.fragment.details.ProblemsFragment;
import com.fansen.phr.fragment.details.ProcedureFragment;
import com.fansen.phr.fragment.details.RequestedProcedureFragment;
import com.fansen.phr.utils.TimeFormat;

import org.w3c.dom.Text;

public class OutpatientDetailActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView textDepartmentView;
    private TextView textOrgView;
    private TextView textAdmitDateView;
    private TextView textAttendingDoctor;
    private TextView textPrimaryDiagnosis;

    private ProblemsFragment problemsFragment;
    private RequestedProcedureFragment requestedProcedureFragment;
    private PrescriptionFragment prescriptionFragment;
    private ProcedureFragment procedureFragment;

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
        System.out.println("selected encounter is " + selectedEncounter.getEncounter_key());

        problemsFragment = ProblemsFragment.newInstance(selectedEncounter);
        requestedProcedureFragment = RequestedProcedureFragment.newInstance();
        prescriptionFragment = PrescriptionFragment.newInstance();
        procedureFragment = new ProcedureFragment();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outpatient_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
                    return problemsFragment;
                case 1:
                    return requestedProcedureFragment;
                case 2:
                    return prescriptionFragment;
                case 3:
                    return procedureFragment;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_outpatient_detail, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
