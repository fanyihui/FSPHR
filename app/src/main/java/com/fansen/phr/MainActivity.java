package com.fansen.phr;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fansen.phr.activities.OutpatientActivity;
import com.fansen.phr.entity.Department;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.entity.Physician;
import com.fansen.phr.fragment.CarePlanFragment;
import com.fansen.phr.fragment.PhrFragment;
import com.fansen.phr.fragment.SummaryFragment;
import com.fansen.phr.service.IDepartmentService;
import com.fansen.phr.service.IDiagnosisDictService;
import com.fansen.phr.service.IDiagnosisService;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.service.IOrganizationService;
import com.fansen.phr.service.IPhysicianService;
import com.fansen.phr.service.implementation.DepartmentServiceLocalImpl;
import com.fansen.phr.service.implementation.DiagnosisDictServiceLocalImpl;
import com.fansen.phr.service.implementation.DiagnosisServiceLocalImpl;
import com.fansen.phr.service.implementation.EncounterServiceLocalImpl;
import com.fansen.phr.service.implementation.OrganizationServiceLocalImpl;
import com.fansen.phr.service.implementation.PhysicianServiceLocalImpl;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CharSequence mTitle;
    private boolean isfabAddTouched = false;
    private FloatingActionButton fabAdd = null;
    private FloatingActionButton addOutpatient = null;
    private FloatingActionButton addInpatient = null;
    private int navigationItemId = 0;

    public static final int ADD_OUTPATIENT_REQUEST = 1;  // The request code
    public static final int ADD_INPATIENT_REQUEST = 2;  // The request code
    public static final int ADD_CAREPLAN_REQUEST = 3;  // The request code
    //static final int ADD_OUTPATIENT_REQUEST = 1;  // The request code

    private Fragment fragment = null;
    private IEncounterService encounterService = null;
    private IOrganizationService organizationService = null;
    private IDepartmentService departmentService = null;
    private IDiagnosisService diagnosisService = null;
    private IDiagnosisDictService diagnosisDictService = null;
    private IPhysicianService physicianService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        encounterService = new EncounterServiceLocalImpl(context);
        organizationService = new OrganizationServiceLocalImpl(context);
        departmentService = new DepartmentServiceLocalImpl(context);
        diagnosisService = new DiagnosisServiceLocalImpl(context);
        diagnosisDictService = new DiagnosisDictServiceLocalImpl(context);
        physicianService = new PhysicianServiceLocalImpl(context);


        fabAdd = (FloatingActionButton) findViewById(R.id.action_add);
        addOutpatient = (FloatingActionButton) findViewById(R.id.action_add_op);
        addInpatient = (FloatingActionButton) findViewById(R.id.action_add_ip);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isfabAddTouched) {
                    showFloatingActionButtons();
                } else{
                    hideFloatingActionButtons();
                }
            }
        });

        addOutpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            //Open the add new outpatient activity and wait for result
            public void onClick(View v) {
                Intent intent = new Intent(context, OutpatientActivity.class);

                startActivityForResult(intent, ADD_OUTPATIENT_REQUEST);

                hideFloatingActionButtons();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void showFloatingActionButtons(){
        fabAdd.setImageResource(R.drawable.ic_clear_black_24dp);
        addOutpatient.setVisibility(View.VISIBLE);
        addInpatient.setVisibility(View.VISIBLE);
        isfabAddTouched = true;
    }

    private void hideFloatingActionButtons(){
        fabAdd.setImageResource(R.drawable.ic_add_black_24dp);
        addOutpatient.setVisibility(View.GONE);
        addInpatient.setVisibility(View.GONE);
        isfabAddTouched = false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // show different Fragment based on the selected item
        // also need to change the option items based on the selected Fragment
        navigationItemId = item.getItemId();
        ActionBar actionBar = getSupportActionBar();

        if (navigationItemId == R.id.nav_summary) {
            fragment = new SummaryFragment();
            mTitle = getString(R.string.title_summary);
        } else if (navigationItemId == R.id.nav_phr) {
            fragment = new PhrFragment();
            mTitle = getString(R.string.title_phr);
        } else if (navigationItemId == R.id.nav_careplan) {
            fragment = new CarePlanFragment();
            mTitle = getString(R.string.title_careplan);
        }

        if (fragment != null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();


            actionBar.setTitle(mTitle);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_OUTPATIENT_REQUEST){
            if (resultCode == RESULT_OK){

                //Get values from OutpatientActivity
                Bundle bundle = data.getExtras();
                String organization = bundle.getString(OutpatientActivity.KEY_ORG);
                String department = bundle.getString(OutpatientActivity.KEY_DEPT);
                String diagnosis = bundle.getString(OutpatientActivity.KEY_DIAG);
                String admit_date = bundle.getString(OutpatientActivity.KEY_DATE);
                String attending_doctor = bundle.getString(OutpatientActivity.KEY_DOCTOR);

                //Initial Encounter
                Encounter encounter = new Encounter();

                //Initial Organization
                Organization org = new Organization(organization);
                long org_key = organizationService.addOrganization(org);
                org.setOrg_key(org_key);

                //Initial department
                Department dept = new Department();
                dept.setName(department);
                long dept_key = departmentService.addDepartment(dept);
                dept.setDepartment_key(dept_key);



                //Add new Dict to database if not exist
                DictDiagnosis primaryDiagnosis = new DictDiagnosis();
                primaryDiagnosis.setName(diagnosis);
                int diagnosis_dict_key = diagnosisDictService.addDiagnosisDict(primaryDiagnosis);
                primaryDiagnosis.setKey(diagnosis_dict_key);

                //Add new physician to database if not exist
                Physician physician = new Physician();
                physician.setPhysicianName(attending_doctor);
                int attending_doctor_key = physicianService.addPhysician(physician);
                physician.setPhysicianKey(attending_doctor_key);

                //Set the value to encounter
                encounter.setAdmit_date(TimeFormat.format("yyyyMMdd", admit_date));
                encounter.setOrg(org);
                encounter.setDepartment(dept);
                encounter.setPrimaryDiagnosis(primaryDiagnosis);
                encounter.setAttendingDoctor(physician);

                // add the encounter to database, and return the key of that encounter
                long encounter_key = encounterService.addNewEncounter(encounter);
                encounter.setEncounter_key(encounter_key);

                if (navigationItemId == R.id.nav_phr) {
                    PhrFragment phrFragment = (PhrFragment) fragment;
                    phrFragment.addEncounter(encounter);
                } else if(navigationItemId == R.id.nav_summary){
                    //TODO add code here to put encounter into summary page
                }
            }

        } else if(requestCode == ADD_INPATIENT_REQUEST){
            //TODO add inpatient
        } else if(requestCode == ADD_CAREPLAN_REQUEST){
            //TODO add care plan
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
