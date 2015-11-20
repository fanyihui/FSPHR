package com.fansen.phr.fragment.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.activities.ChiefComplaintEditActivity;
import com.fansen.phr.activities.EncounterProblemEditActivity;
import com.fansen.phr.adapter.ChiefComplaintListAdapter;
import com.fansen.phr.entity.ChiefComplaint;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.ObservationType;
import com.fansen.phr.service.IChiefComplaintService;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.service.implementation.ChiefComplaintServiceLocalImpl;
import com.fansen.phr.service.implementation.EncounterServiceLocalImpl;

import java.util.List;


public class ProblemsFragment extends Fragment {
    public static final int ADD_COMPLAINT_REQUEST = 4;  // The request code
    public static final int EDIT_PROBLEM_DESC_REQUEST = 5;  // The request code
    public static final int EDIT_COMPLAINT_REQUEST = 6;
    public static final int EDIT_HISTORICAL_PROBLEM_REQUEST = 1001;
    public static final int EDIT_PHYSICAL_EXAM_REQUEST = 1002;

    public static final String BUNDLE_KEY_ENT = "encounter";
    public static final String BUNDLE_KEY_PROBLEM_VALUE = "problem_value";
    public static final String BUNDLE_KEY_PROBLEM_TYPE = "problem_type";
    public static final String BUNDLE_KEY_SELECTED_COMPLAINT_POSITION = "complaint_position";
    public static final String BUNDLE_KEY_SELECTED_COMPLAINT = "selected_complaint";

    private OnFragmentInteractionListener mListener;
    private Button addComplaintBtn;
    private ListView complaintListView;
    private TextView historyProblemTextView;
    private TextView physicalExamTextView;
    private TextView problemDescriptionTextView;
    private RelativeLayout problemsView;

    private List<ChiefComplaint> chiefComplaints;
    private ChiefComplaintListAdapter chiefComplaintListAdapter;

    private Context context;
    private Encounter encounter;
    private String problemsDescription;
    private String historicalProblems;
    private String physicalExamDetails;

    private int selectChiefComplaintPosition = 0;

    private IChiefComplaintService chiefComplaintService;
    private IEncounterService encounterService;

    public static ProblemsFragment newInstance(Encounter encounter) {
        ProblemsFragment fragment = new ProblemsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_ENT, encounter);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ProblemsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this.getActivity();

        final Bundle bundle = getArguments();
        encounter = (Encounter) bundle.getSerializable(BUNDLE_KEY_ENT);

        chiefComplaintService = new ChiefComplaintServiceLocalImpl(context);
        encounterService = new EncounterServiceLocalImpl(context);

        chiefComplaints = chiefComplaintService.getComplaints(encounter.getEncounter_key());
        problemsDescription = encounterService.getProblemsDescription(encounter.getEncounter_key());
        historicalProblems = encounterService.getHistoricalProblems(encounter.getEncounter_key());
        physicalExamDetails = encounterService.getPhysicalExamDetails(encounter.getEncounter_key());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        problemsView = (RelativeLayout) inflater.inflate(R.layout.fragment_problems, container, false);

        //Initial chief complaint section view
        addComplaintBtn = (Button) problemsView.findViewById(R.id.id_problems_add_complaint_btn);
        addComplaintBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiefComplaintEditActivity.class);
                startActivityForResult(intent, ADD_COMPLAINT_REQUEST);
            }
        });

        complaintListView = (ListView) problemsView.findViewById(R.id.id_problems_complaint_list);

        chiefComplaintListAdapter = new ChiefComplaintListAdapter(context, chiefComplaints);
        complaintListView.setAdapter(chiefComplaintListAdapter);
        complaintListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectChiefComplaintPosition = position;

                ChiefComplaint chiefComplaint = (ChiefComplaint) chiefComplaintListAdapter.getItem(position);

                Intent intent = new Intent(context, ChiefComplaintEditActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(BUNDLE_KEY_SELECTED_COMPLAINT, chiefComplaint);
                intent.putExtras(bundle1);

                startActivityForResult(intent, EDIT_COMPLAINT_REQUEST);
            }
        });

        //initial problem description section view
        problemDescriptionTextView = (TextView) problemsView.findViewById(R.id.id_problems_problem_desc);
        problemDescriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchProblemEditIntent(ObservationType.CURRENT_PROBLEM.getName(),
                        problemDescriptionTextView.getText().toString(),
                        EDIT_PROBLEM_DESC_REQUEST);
            }
        });
        problemDescriptionTextView.setText(problemsDescription);

        //initial history problem view
        historyProblemTextView = (TextView) problemsView.findViewById(R.id.id_problems_history_problem);
        historyProblemTextView.setText(historicalProblems);
        historyProblemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchProblemEditIntent(ObservationType.HISTORICAL_PROBLEM.getName(),
                        historyProblemTextView.getText().toString(),
                        EDIT_HISTORICAL_PROBLEM_REQUEST);
            }
        });

        //initial physical exam section
        physicalExamTextView = (TextView) problemsView.findViewById(R.id.id_problems_physical_exam);
        physicalExamTextView.setText(physicalExamDetails);
        physicalExamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchProblemEditIntent(ObservationType.PHYSICAL_EXAM.getName(),
                        physicalExamTextView.getText().toString(),
                        EDIT_PHYSICAL_EXAM_REQUEST);
            }
        });

        return problemsView;
    }

    private void dispatchProblemEditIntent(String type, String value, int requestCode){
        Intent intent = new Intent(context, EncounterProblemEditActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString(BUNDLE_KEY_PROBLEM_TYPE, type);
        bundle1.putString(BUNDLE_KEY_PROBLEM_VALUE, value);
        intent.putExtras(bundle1);

        startActivityForResult(intent, requestCode);
    }

    private void handleComplaintAdded(Intent data){
        Bundle bundle = data.getExtras();
        String complaint = bundle.getString(ChiefComplaintEditActivity.COMPLAINT_KEY);
        String duration = bundle.getString(ChiefComplaintEditActivity.DURATION_KEY);
        String unit = bundle.getString(ChiefComplaintEditActivity.DURATION_UNIT_KEY);

        ChiefComplaint chiefComplaint = new ChiefComplaint();
        chiefComplaint.setSymptom(complaint);
        chiefComplaint.setDuration(duration);
        chiefComplaint.setDuration_unit(unit);

        int key = chiefComplaintService.addComplaint(encounter.getEncounter_key(), chiefComplaint);
        chiefComplaint.setKey(key);

        chiefComplaintListAdapter.addComplaint(chiefComplaint);
    }

    private void handleProblemDescEdit(Intent data){
        long ent_key = encounter.getEncounter_key();
        Bundle bundle = data.getExtras();
        String problem_desc = bundle.getString(EncounterProblemEditActivity.BUNDLE_KEY_PROBLEM_VALUE);
        if(problem_desc!=null && !problem_desc.equals(problemsDescription)){
            problemsDescription = problem_desc;
            problemDescriptionTextView.setText(problemsDescription);
            encounter.setProblem_description(problemsDescription);
            encounterService.updateProblemsDescription(ent_key, problemsDescription);
        }
    }

    private void handleEditComplaint(Intent data){
        Bundle bundle = data.getExtras();
        String complaint = bundle.getString(ChiefComplaintEditActivity.COMPLAINT_KEY);
        String duration = bundle.getString(ChiefComplaintEditActivity.DURATION_KEY);
        String unit = bundle.getString(ChiefComplaintEditActivity.DURATION_UNIT_KEY);
        int key = bundle.getInt(ChiefComplaintEditActivity.ID_KEY);

        ChiefComplaint chiefComplaint = new ChiefComplaint();
        chiefComplaint.setSymptom(complaint);
        chiefComplaint.setDuration(duration);
        chiefComplaint.setDuration_unit(unit);
        chiefComplaint.setKey(key);

        chiefComplaintListAdapter.updateComplaint(selectChiefComplaintPosition, chiefComplaint);
        chiefComplaintService.updateChiefComplaint(chiefComplaint);
    }

    private void handleEditHistoricalProblem(Intent data){
        long ent_key = encounter.getEncounter_key();
        Bundle bundle = data.getExtras();
        String value = bundle.getString(EncounterProblemEditActivity.BUNDLE_KEY_PROBLEM_VALUE);
        if (value!=null && !value.equals(historicalProblems)){
            historicalProblems = value;
            historyProblemTextView.setText(historicalProblems);
            encounter.setHistoricalProblems(historicalProblems);
            encounterService.updateHistoricalProblems(ent_key, historicalProblems);
        }
    }

    private void handleEditPhysicalExam(Intent data){
        long ent_key = encounter.getEncounter_key();
        Bundle bundle = data.getExtras();
        String value = bundle.getString(EncounterProblemEditActivity.BUNDLE_KEY_PROBLEM_VALUE);
        if (value !=null && !value.equals(physicalExamDetails)){
            physicalExamDetails = value;
            physicalExamTextView.setText(physicalExamDetails);
            encounter.setPhysicalExam(physicalExamDetails);
            encounterService.updatePhysicalExamDetails(ent_key, physicalExamDetails);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ADD_COMPLAINT_REQUEST:
                    handleComplaintAdded(data);
                    break;
                case EDIT_PROBLEM_DESC_REQUEST:
                    handleProblemDescEdit(data);
                    break;
                case EDIT_COMPLAINT_REQUEST:
                    handleEditComplaint(data);
                    break;
                case EDIT_HISTORICAL_PROBLEM_REQUEST:
                    handleEditHistoricalProblem(data);
                    break;
                case EDIT_PHYSICAL_EXAM_REQUEST:
                    handleEditPhysicalExam(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
