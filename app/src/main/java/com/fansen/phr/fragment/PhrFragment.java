package com.fansen.phr.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.R;
import com.fansen.phr.adapter.MedicalRecordListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.Organization;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.List;

public class PhrFragment extends Fragment {

    private RecyclerView phrView = null;
    private List<Encounter> encounters;
    private RecyclerView.LayoutManager layoutManager;
    private MedicalRecordListAdapter adapter;


    public PhrFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        setTestData();

        phrView = (RecyclerView) inflater.inflate(R.layout.fragment_phr, container, false);
        phrView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        phrView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new MedicalRecordListAdapter(encounters);
        phrView.setAdapter(adapter);
        return phrView;
    }

    private void setTestData(){
        encounters = new ArrayList<>();

        Encounter ent = new Encounter();
        ent.setEncounter_date(TimeFormat.format("yyyyMMdd", "20150921"));
        ent.setOrg(new Organization("复旦大学附属眼耳鼻喉医院"));
        encounters.add(ent);

        ent = new Encounter();
        ent.setEncounter_date(TimeFormat.format("yyyyMMdd", "20150920"));
        ent.setOrg(new Organization("虹梅路社区卫生服务中心"));
        encounters.add(ent);

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
