package com.fansen.phr.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.R;
import com.fansen.phr.activities.OutpatientDetailActivity;
import com.fansen.phr.adapter.MedicalRecordListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.service.IDepartmentService;
import com.fansen.phr.service.IDiagnosisDictService;
import com.fansen.phr.service.IDiagnosisService;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.service.IOrganizationService;
import com.fansen.phr.service.implementation.EncounterServiceLocalImpl;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.List;

public class PhrFragment extends Fragment implements MedicalRecordListAdapter.MedicalRecordItemClickListener{

    private Context context;

    private RecyclerView phrView = null;
    private List<Encounter> encounters;
    private RecyclerView.LayoutManager layoutManager;
    private MedicalRecordListAdapter adapter;

    private IEncounterService encounterService = null;
    private IOrganizationService organizationService = null;
    private IDepartmentService departmentService = null;
    private IDiagnosisService diagnosisService = null;
    private IDiagnosisDictService diagnosisDictService = null;

    public static final String OPEN_ENT_KEY = "open_encounter";

    public PhrFragment() {
        // Required empty public constructor
        encounters = new ArrayList<>();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();

        //setTestData();

        phrView = (RecyclerView) inflater.inflate(R.layout.fragment_phr, container, false);
        phrView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        phrView.setLayoutManager(layoutManager);

        encounterService = new EncounterServiceLocalImpl(getActivity());
        encounters = encounterService.getAllEncounters();

        // specify an adapter (see also next example)
        adapter = new MedicalRecordListAdapter(encounters);
        adapter.setItemClickListener(this);
        phrView.setAdapter(adapter);
        return phrView;
    }

    public void addEncounter(Encounter encounter){
        adapter.addEncounter(encounter);
    }

    /*
    * For test only, should be move to JUnit code
    * */
    private void setTestData(){
        encounters = new ArrayList<>();

        Encounter ent = new Encounter();
        ent.setAdmit_date(TimeFormat.format("yyyyMMdd", "20150921"));
        ent.setOrg(new Organization("复旦大学附属眼耳鼻喉医院"));
        encounters.add(ent);

        ent = new Encounter();
        ent.setAdmit_date(TimeFormat.format("yyyyMMdd", "20150920"));
        ent.setOrg(new Organization("虹梅路社区卫生服务中心"));
        encounters.add(ent);

    }

    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(context, OutpatientDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPEN_ENT_KEY, encounters.get(position));
        intent.putExtras(bundle);

        context.startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
