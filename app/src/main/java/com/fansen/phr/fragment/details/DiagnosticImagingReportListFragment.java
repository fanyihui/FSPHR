package com.fansen.phr.fragment.details;

import android.content.Context;
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
import com.fansen.phr.adapter.DiagnosticImagingReportListAdapter;
import com.fansen.phr.entity.DiagnosticImagingReport;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.service.IDiagnosticImagingReportService;
import com.fansen.phr.service.implementation.DiagnosticImagingReportServiceLocalImpl;

import java.util.List;

public class DiagnosticImagingReportListFragment extends Fragment implements DiagnosticImagingReportListAdapter.DiagnosticImagingReportItemClickListener {
    private static final String ARG_CURRENT_ENCOUNTER = "current_encounter";

    private Encounter currentEncounter;
    private IDiagnosticImagingReportService diagnosticImagingReportService;

    private Context context;
    private RelativeLayout diagnosticImagingReportListFragmentView;
    private TextView emptyReportListTipsTextView;
    private RecyclerView diagnosticImagingReportListView;
    private DiagnosticImagingReportListAdapter diagnosticImagingReportListAdapter;
    private List<DiagnosticImagingReport> diagnosticImagingReportList;
    private RecyclerView.LayoutManager layoutManager;

    private OnDIRItemSelectedListener mListener;

    public static DiagnosticImagingReportListFragment newInstance(Encounter encounter) {
        DiagnosticImagingReportListFragment fragment = new DiagnosticImagingReportListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_ENCOUNTER, encounter);
        fragment.setArguments(bundle);

        return fragment;
    }

    public DiagnosticImagingReportListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            currentEncounter = (Encounter) getArguments().getSerializable(ARG_CURRENT_ENCOUNTER);
        }

        context = getActivity();

        diagnosticImagingReportService = new DiagnosticImagingReportServiceLocalImpl(context);

        diagnosticImagingReportList = diagnosticImagingReportService.getDiagnosticImagingReports(currentEncounter.getEncounter_key());
        diagnosticImagingReportListAdapter = new DiagnosticImagingReportListAdapter(diagnosticImagingReportList);
        diagnosticImagingReportListAdapter.setDiagnosticImagingReportItemClickListener(this);

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        diagnosticImagingReportListFragmentView = (RelativeLayout) inflater.inflate(R.layout.fragment_diagnostic_imaging_report_list, container, false);

        diagnosticImagingReportListView = (RecyclerView) diagnosticImagingReportListFragmentView.findViewById(R.id.id_di_report_fragment_recyclerview);
        diagnosticImagingReportListView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        diagnosticImagingReportListView.setLayoutManager(layoutManager);
        diagnosticImagingReportListView.setAdapter(diagnosticImagingReportListAdapter);

        emptyReportListTipsTextView = (TextView) diagnosticImagingReportListFragmentView.findViewById(R.id.id_di_report_fragment_tip_textview);

        if(diagnosticImagingReportList.size() > 0){
            emptyReportListTipsTextView.setVisibility(View.GONE);
        }

        return diagnosticImagingReportListFragmentView;
    }


    @Override
    public void itemClicked(View v, int position) {
        DiagnosticImagingReport diagnosticImagingReport = diagnosticImagingReportListAdapter.getDiagnosticImagingReport(position);
        mListener.onDIRItemSelected(position, diagnosticImagingReport);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnDIRItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport){
        if(diagnosticImagingReport != null){
            diagnosticImagingReportListAdapter.addDiagnosticImagingReport(diagnosticImagingReport);
            emptyReportListTipsTextView.setVisibility(View.GONE);
        }
    }

    public void updateDiagnosticImagingReport(int position, DiagnosticImagingReport diagnosticImagingReport){
        if (position>=0 && diagnosticImagingReport != null){
            diagnosticImagingReportListAdapter.updateDiagnosticImagingReport(position, diagnosticImagingReport);
        }
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
    public interface OnDIRItemSelectedListener {
        // TODO: Update argument type and name
        public void onDIRItemSelected(int position, DiagnosticImagingReport diagnosticImagingReport);
    }
}
