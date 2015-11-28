package com.fansen.phr.fragment.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.R;
import com.fansen.phr.adapter.LabReportListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.LabReport;

import java.util.List;

public class LabResultFragment extends Fragment implements LabReportListAdapter.LabReportItemClickedListener {
    private static final String ARG_CURRENT_ENCOUNTER = "current_encounter";

    private OnLabResultFragmentInteractionListener mListener;
    private Encounter encounter;
    private Context context;

    private LabReportListAdapter labReportListAdapter;
    private List<LabReport> labReports;

    public static LabResultFragment newInstance(Encounter encounter){
        LabResultFragment labResultFragment = new LabResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_ENCOUNTER, encounter);
        labResultFragment.setArguments(bundle);

        return labResultFragment;
    }

    public LabResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            encounter = (Encounter) getArguments().getSerializable(ARG_CURRENT_ENCOUNTER);
        }

        context = getActivity();

        //TODO get LabReportList from database
        labReportListAdapter = new LabReportListAdapter(labReports, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab_result, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (OnLabResultFragmentInteractionListener) context;
        } catch (ClassCastException cce){
            throw new ClassCastException(context.toString() + " must implement OnLabResultFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void itemClicked(View v, int position) {

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
    public interface OnLabResultFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLabResultItemSelected(int position);
    }

}
