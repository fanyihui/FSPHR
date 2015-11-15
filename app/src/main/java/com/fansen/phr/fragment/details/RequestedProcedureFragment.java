package com.fansen.phr.fragment.details;

import android.content.Context;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.R;
import com.fansen.phr.adapter.RequestedProcedureListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.RequestedProcedure;

import java.util.List;

public class RequestedProcedureFragment extends Fragment implements RequestedProcedureListAdapter.RequestedProcedureItemClickListener{
    private static final String ARG_CURRENT_ENCOUNTER = "current_encounter";

    private Encounter currentEncounter;

    private Context context;
    private RecyclerView requestedProcedureView;
    private RequestedProcedureListAdapter requestedProcedureListAdapter;
    private List<RequestedProcedure> requestedProcedureList;
    private RecyclerView.LayoutManager layoutManager;

    private OnFragmentInteractionListener mListener;

    public static RequestedProcedureFragment newInstance(Encounter encounter) {
        RequestedProcedureFragment fragment = new RequestedProcedureFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_ENCOUNTER, encounter);
        fragment.setArguments(bundle);

        return fragment;
    }

    public RequestedProcedureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //TODO add code here to initial variables

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestedProcedureView = (RecyclerView) inflater.inflate(R.layout.fragment_requested_procedure, container, false);
        requestedProcedureView.setHasFixedSize(true);
        requestedProcedureView.setAdapter(requestedProcedureListAdapter);

        return requestedProcedureView;
    }


    @Override
    public void itemClicked(View v, int position) {
        //TODO add code here to open requestedprocedure detail activity
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
