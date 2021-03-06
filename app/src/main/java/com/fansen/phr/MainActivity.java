package com.fansen.phr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.fansen.phr.activities.EncounterCoreInfoActivity;
import com.fansen.phr.activities.EncounterDetailActivity;
import com.fansen.phr.entity.Department;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.entity.Physician;
import com.fansen.phr.fragment.CarePlanFragment;
import com.fansen.phr.fragment.PhrFragment;
import com.fansen.phr.fragment.SummaryFragment;
import com.fansen.phr.service.IDepartmentService;
import com.fansen.phr.service.IDiagnosisDictService;
import com.fansen.phr.service.IDiagnosisService;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.service.IMedicationAdminRecordService;
import com.fansen.phr.service.IMedicationOrderService;
import com.fansen.phr.service.IMedicationReminderService;
import com.fansen.phr.service.IOrganizationService;
import com.fansen.phr.service.IPhysicianService;
import com.fansen.phr.service.implementation.DepartmentServiceLocalImpl;
import com.fansen.phr.service.implementation.DiagnosisDictServiceLocalImpl;
import com.fansen.phr.service.implementation.DiagnosisServiceLocalImpl;
import com.fansen.phr.service.implementation.EncounterServiceLocalImpl;
import com.fansen.phr.service.implementation.MedicationAdminRecordServiceLocalImpl;
import com.fansen.phr.service.implementation.MedicationOrderServiceLocalImpl;
import com.fansen.phr.service.implementation.MedicationReminderServiceLocalImpl;
import com.fansen.phr.service.implementation.OrganizationServiceLocalImpl;
import com.fansen.phr.service.implementation.PhysicianServiceLocalImpl;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SummaryFragment.OnSummaryFragmentInteractionListener,
        PhrFragment.OnPhrFragmentInteractionListener {

    private CharSequence mTitle;
    private boolean isfabAddTouched = false;
    private FloatingActionButton fabAdd = null;
    private FloatingActionButton addOutpatient = null;
    //private FloatingActionButton addInpatient = null;
    private int navigationItemId = 0;

    public static final int ADD_OUTPATIENT_REQUEST = 1;  // The request code
    public static final int ADD_INPATIENT_REQUEST = 2;  // The request code
    public static final int ADD_CAREPLAN_REQUEST = 3;  // The request code
    public static final int PRESCRIPTION_CHANGE_REQUEST = 4;
    //static final int ADD_OUTPATIENT_REQUEST = 1;  // The request code

    public static String OPEN_ENT_KEY = "open_encounter";

    private Encounter latestEncounter;

    private Fragment fragment = null;
    private IEncounterService encounterService = null;
    private IOrganizationService organizationService = null;
    private IDepartmentService departmentService = null;
    private IDiagnosisService diagnosisService = null;
    private IDiagnosisDictService diagnosisDictService = null;
    private IPhysicianService physicianService = null;
    private IMedicationOrderService medicationOrderService = null;
    private IMedicationReminderService reminderService = null;
    private IMedicationAdminRecordService medicationAdminRecordService = null;

    private PhrFragment phrFragment;
    private SummaryFragment summaryFragment;

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
        medicationOrderService = new MedicationOrderServiceLocalImpl(context);
        reminderService = new MedicationReminderServiceLocalImpl(context);
        medicationAdminRecordService = new MedicationAdminRecordServiceLocalImpl(context);

        fabAdd = (FloatingActionButton) findViewById(R.id.action_add);
        addOutpatient = (FloatingActionButton) findViewById(R.id.action_add_op);
        //addInpatient = (FloatingActionButton) findViewById(R.id.action_add_ip);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isfabAddTouched) {
                    showFloatingActionButtons();
                } else {
                    hideFloatingActionButtons();
                }
            }
        });

        addOutpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            //Open the add new outpatient activity and wait for result
            public void onClick(View v) {
                Intent intent = new Intent(context, EncounterCoreInfoActivity.class);

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

        latestEncounter = encounterService.getLatestEncounter();
        ArrayList<Encounter> encounters = encounterService.getAllEncounters();

        Date currentDate = new Date();
        String today = TimeFormat.parseDate(currentDate);
        ArrayList<MedicationAdminRecord> medicationAdminRecords = medicationAdminRecordService.getMARByDate(today);

        phrFragment = PhrFragment.newInstance(encounters);
        summaryFragment = SummaryFragment.newInstance(latestEncounter, medicationAdminRecords);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, summaryFragment).commit();
    }


    private void showFloatingActionButtons(){
        fabAdd.setImageResource(R.drawable.ic_clear_black_24dp);
        addOutpatient.setVisibility(View.VISIBLE);
        //addInpatient.setVisibility(View.VISIBLE);
        isfabAddTouched = true;
    }

    private void hideFloatingActionButtons(){
        fabAdd.setImageResource(R.drawable.ic_add_black_24dp);
        addOutpatient.setVisibility(View.GONE);
        //addInpatient.setVisibility(View.GONE);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (navigationItemId == R.id.nav_summary) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, summaryFragment).commit();
            mTitle = getString(R.string.title_summary);
            fragment = summaryFragment;
        } else if (navigationItemId == R.id.nav_phr) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, phrFragment).commit();
            fragment = phrFragment;
            mTitle = getString(R.string.title_phr);
        } else if (navigationItemId == R.id.nav_careplan) {
            fragment = new CarePlanFragment();
            mTitle = getString(R.string.title_careplan);
        }

        actionBar.setTitle(mTitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ADD_OUTPATIENT_REQUEST:
                    handleOutpatientIntent(data);
                    break;
                case ADD_INPATIENT_REQUEST:
                    break;
                case ADD_CAREPLAN_REQUEST:
                    break;
                case PRESCRIPTION_CHANGE_REQUEST:
                    handlePrescriptionChanged(data);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void dispatchAddOutpatientRequest(){

    }

    private void dispatchAddInpatientRequest(){

    }

    private void handleOutpatientIntent(Intent data){
        //Get values from EncounterCoreInfoActivity
        Bundle bundle = data.getExtras();
        String organization = bundle.getString(EncounterCoreInfoActivity.KEY_ORG);
        String department = bundle.getString(EncounterCoreInfoActivity.KEY_DEPT);

        ArrayList<String> diagnosisTextList = (ArrayList) bundle.getSerializable(EncounterCoreInfoActivity.KEY_DIAG);

        String admit_date = bundle.getString(EncounterCoreInfoActivity.KEY_DATE);
        String attending_doctor = bundle.getString(EncounterCoreInfoActivity.KEY_DOCTOR);
        String patientClass = bundle.getString(EncounterCoreInfoActivity.KEY_PATIENT_CLASS);
        String discharge_date = bundle.getString(EncounterCoreInfoActivity.KEY_DISCHARGE_DATE);

        //Initial Encounter
        Encounter encounter = new Encounter();

        //Initial Organization
        Organization org = new Organization(organization);
        org.setOrg_name(organization);
        //long org_key = organizationService.addOrganization(org);
        //org.setOrg_key(org_key);

        //Initial department
        Department dept = new Department();
        dept.setName(department);
        //long dept_key = departmentService.addDepartment(dept);
        //dept.setDepartment_key(dept_key);

        //Add new physician to database if not exist
        Physician physician = new Physician();
        physician.setPhysicianName(attending_doctor);
        //int attending_doctor_key = physicianService.addPhysician(physician);
        //physician.setPhysician_key(attending_doctor_key);

        //Set the value to encounter
        encounter.setAdmit_date(TimeFormat.format(admit_date));
        encounter.setOrg(org);
        encounter.setDepartment(dept);
        encounter.setAttendingDoctor(physician);
        encounter.setPatientClass(patientClass);
        if (discharge_date != null || (!discharge_date.equals(""))){
            encounter.setDischarge_date(TimeFormat.format(discharge_date));
        }

        // add the encounter to database, and return the key of that encounter
        long encounter_key = encounterService.addNewEncounter(encounter);
        encounter.setEncounter_key(encounter_key);

        ArrayList<Diagnosis> diagnosises = new ArrayList<>();

        //add diagnosis list to an Encounter
        if (diagnosisTextList != null && diagnosisTextList.size()>0){
            diagnosises = diagnosisService.addDiagnosisList(encounter_key, diagnosisTextList);
            /*for (int i=0; i<diagnosisTextList.size(); i++){
                String diagnosisValue = diagnosisTextList.get(i);
                //Add new Dict to database if not exist
                DictDiagnosis dictDiagnosis = new DictDiagnosis();
                dictDiagnosis.setName(diagnosisValue);
                int diagnosis_dict_key = diagnosisDictService.addDiagnosisDict(dictDiagnosis);
                dictDiagnosis.setKey(diagnosis_dict_key);

                Diagnosis diagnosis = new Diagnosis();
                diagnosis.setEncounter_key(encounter_key);
                diagnosis.setDiagnosis_dict(dictDiagnosis);

                int diag_key = diagnosisService.addNewDiagnosis(diagnosis);
                diagnosis.setDiagnosis_key(diag_key);

                diagnosises.add(diagnosis);
            }*/
        }

        encounter.setDiagnosisList(diagnosises);

        phrFragment.addEncounter(encounter);

        if (latestEncounter == null || latestEncounter.getAdmit_date().before(encounter.getAdmit_date())){
            summaryFragment.setLatestEncounter(encounter);
            latestEncounter = encounter;
        }

        /*if (navigationItemId == R.id.nav_phr) {
            PhrFragment phrFragment = (PhrFragment) fragment;
            phrFragment.addEncounter(encounter);
        }*/
    }

    private void handlePrescriptionChanged(Intent data){
        //TODO add code here to refresh the prescription reminder list
        Bundle bundle = data.getExtras();
        boolean isChanged = bundle.getBoolean(EncounterDetailActivity.BUNDLE_KEY_PRESCRIPTION_CHANGED);

        if (isChanged){
            Date currentDate = new Date();
            String today = TimeFormat.parseDate(currentDate);
            ArrayList<MedicationAdminRecord> medicationAdminRecords = medicationAdminRecordService.getMARByDate(today);
            summaryFragment.updateMAR(medicationAdminRecords);
        }
    }

    @Override
    public void onLatestEncounterClicked(Encounter encounter) {
        openEncounterDetails(encounter);
    }

    @Override
    public void onEncounterListClicked(Encounter encounter) {
        openEncounterDetails(encounter);
    }

    @Override
    public void onMedicationTaken(MedicationAdminRecord medicationAdminRecord) {
        int id = medicationAdminRecord.get_id();
        if (id<=0){
            id = medicationAdminRecordService.addNewMAR(medicationAdminRecord);
            medicationAdminRecord.set_id(id);
        } else {
            medicationAdminRecordService.takenMedication(id);
        }
    }

    @Override
    public void onMedicationUntaken(MedicationAdminRecord medicationAdminRecord) {
        int id = medicationAdminRecord.get_id();
        medicationAdminRecordService.unTakenMedication(id);
    }

    private void openEncounterDetails(Encounter encounter){
        Intent intent = new Intent(this, EncounterDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPEN_ENT_KEY, encounter);
        intent.putExtras(bundle);

        startActivityForResult(intent, PRESCRIPTION_CHANGE_REQUEST);
    }
}
