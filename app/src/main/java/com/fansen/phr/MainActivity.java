package com.fansen.phr;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.fansen.phr.activities.NewOutpatientActivity;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.fragment.AddOutpatientEncounterFragment;
import com.fansen.phr.fragment.CarePlanFragment;
import com.fansen.phr.fragment.PhrFragment;
import com.fansen.phr.fragment.SummaryFragment;

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
    //static final int ADD_OUTPATIENT_REQUEST = 1;  // The request code

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

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
                Intent intent = new Intent(context, NewOutpatientActivity.class);

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
                Bundle bundle = data.getExtras();
                Encounter encounter = (Encounter) bundle.getSerializable(NewOutpatientActivity.ENT_KEY);

                System.out.println(encounter.getOrg().getOrg_name() + encounter.getDepartment().getName() + encounter.getDiagnosis());

                if (navigationItemId == R.id.nav_phr) {
                    PhrFragment phrFragment = (PhrFragment) fragment;
                    phrFragment.addEncounter(encounter);
                } else if(navigationItemId == R.id.nav_summary){
                    //TODO add code here to put encounter into summary page
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
