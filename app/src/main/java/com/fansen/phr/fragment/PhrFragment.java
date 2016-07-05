package com.fansen.phr.fragment;

import android.content.Context;
import android.os.Bundle;
//simport android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.R;
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

    public static final String BUNDLE_KEY_ALL_ENCOUNTER = "ALL_ENCOUNTERS";

    private Context context;
    private OnPhrFragmentInteractionListener onPhrFragmentInteractionListener;

    private RecyclerView phrView = null;
    private ArrayList<Encounter> encounters;
    private RecyclerView.LayoutManager layoutManager;
    private MedicalRecordListAdapter adapter;

    private IEncounterService encounterService = null;
    private IOrganizationService organizationService = null;
    private IDepartmentService departmentService = null;
    private IDiagnosisService diagnosisService = null;
    private IDiagnosisDictService diagnosisDictService = null;

    public static final String OPEN_ENT_KEY = "open_encounter";

    public static PhrFragment newInstance(ArrayList<Encounter> encounters){
        PhrFragment phrFragment = new PhrFragment();

        if (encounters != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_ALL_ENCOUNTER, encounters);

            phrFragment.setArguments(bundle);
        }

        return phrFragment;
    }

    public PhrFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null){
            this.encounters = (ArrayList<Encounter>) bundle.getSerializable(BUNDLE_KEY_ALL_ENCOUNTER);
        }

        if (encounters == null){
            encounters = new ArrayList<>();
        }

        adapter = new MedicalRecordListAdapter(encounters);
        adapter.setItemClickListener(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onPhrFragmentInteractionListener = (OnPhrFragmentInteractionListener) context;
        } catch (ClassCastException cce){
            throw new ClassCastException(context.toString()
                    +" must implement OnPhrFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onPhrFragmentInteractionListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        phrView = (RecyclerView) inflater.inflate(R.layout.fragment_phr, container, false);
        phrView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        phrView.setLayoutManager(layoutManager);
        phrView.setAdapter(adapter);
        return phrView;
    }

    public void addEncounter(Encounter encounter){
        if (adapter == null){
            Bundle bundle = getArguments();
            if (bundle != null){
                ((ArrayList<Encounter>) bundle.getSerializable(BUNDLE_KEY_ALL_ENCOUNTER)).add(encounter);
            }
        } else {
            adapter.addEncounter(encounter);
        }
        //adapter.addEncounter(encounter);
    }

    /*
    * For test only, should be move to JUnit code
    * */
    private void setTestData(){
        encounters = new ArrayList<>();

        Encounter ent = new Encounter();
        ent.setAdmit_date(TimeFormat.format("20150921"));
        ent.setOrg(new Organization("复旦大学附属眼耳鼻喉医院"));
        encounters.add(ent);

        ent = new Encounter();
        ent.setAdmit_date(TimeFormat.format("20150920"));
        ent.setOrg(new Organization("虹梅路社区卫生服务中心"));
        encounters.add(ent);

    }

    @Override
    public void itemClicked(View v, int position) {
        /*Intent intent = new Intent(context, EncounterDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPEN_ENT_KEY, encounters.get(position));
        intent.putExtras(bundle);

        context.startActivity(intent);*/

        onPhrFragmentInteractionListener.onEncounterListClicked(encounters.get(position));
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
    public interface OnPhrFragmentInteractionListener {
        public void onEncounterListClicked(Encounter encounter);
    }

}
