package com.fansen.phr.fragment.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.adapter.LabReportListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.LabReport;

import java.util.ArrayList;
import java.util.List;

public class LabResultFragment extends Fragment implements LabReportListAdapter.LabReportItemClickedListener {
    private static final String ARG_CURRENT_ENCOUNTER = "current_encounter";
    private static final String ARG_LAB_REPORT_LIST = "lab_report_list";

    private OnLabResultFragmentInteractionListener mListener;
    private Encounter encounter;
    private Context context;

    private LabReportListAdapter labReportListAdapter;
    private List<LabReport> labReports;

    private TextView emptyLabReportTipsTextView;
    private RecyclerView labReportListRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public static LabResultFragment newInstance(Encounter encounter, ArrayList<LabReport> labReports){
        LabResultFragment labResultFragment = new LabResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_ENCOUNTER, encounter);
        bundle.putSerializable(ARG_LAB_REPORT_LIST, labReports);
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
            labReports = (ArrayList<LabReport>) getArguments().getSerializable(ARG_LAB_REPORT_LIST);
        } else {
            labReports = new ArrayList<>();
        }

        context = getActivity();
        labReportListAdapter = new LabReportListAdapter(labReports, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lab_result, container, false);

        emptyLabReportTipsTextView = (TextView) view.findViewById(R.id.id_lab_report_fragment_tip_textview);
        if(labReports.size()>0){
            emptyLabReportTipsTextView.setVisibility(View.GONE);
        }

        labReportListRecyclerView = (RecyclerView) view.findViewById(R.id.id_lab_report_fragment_recyclerview);
        labReportListRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        labReportListRecyclerView.setLayoutManager(layoutManager);
        labReportListRecyclerView.setAdapter(labReportListAdapter);

        return view;
    }

    public void addLabReport(LabReport labReport){
        if (labReport != null){
            labReportListAdapter.addLabReport(labReport);
            emptyLabReportTipsTextView.setVisibility(View.GONE);
        }
    }

    public void updateLabReport(int position, LabReport labReport){
        if(labReport != null){
            labReportListAdapter.updateLabReport(position, labReport);
        }
    }

    public void setLabReports(List<LabReport> labReports){
        if (labReports != null){
            labReportListAdapter.setLabReportList(labReports);
        }
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
        LabReport labReport = labReportListAdapter.getLabReport(position);
        mListener.onLabResultItemSelected(position, labReport);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLabResultFragmentInteractionListener {
        public void onLabResultItemSelected(int position, LabReport labReport);
    }

}
