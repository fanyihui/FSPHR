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
import com.fansen.phr.activities.ProblemDescriptionEditActivity;
import com.fansen.phr.adapter.ChiefComplaintListAdapter;
import com.fansen.phr.entity.ChiefComplaint;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.service.IChiefComplaintService;
import com.fansen.phr.service.IEncounterService;
import com.fansen.phr.service.implementation.ChiefComplaintServiceLocalImpl;
import com.fansen.phr.service.implementation.EncounterServiceLocalImpl;

import org.w3c.dom.Text;

import java.util.List;


public class ProblemsFragment extends Fragment {
    public static final int ADD_COMPLAINT_REQUEST = 4;  // The request code
    public static final int EDIT_PROBLEM_DESC_REQUEST = 5;  // The request code
    public static final int EDIT_COMPLAINT_REQUEST = 6;
    public static final String BUNDLE_KEY_ENT = "encounter";
    public static final String BUNDLE_KEY_DESC = "edit_problems_description";
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
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getActivity();

        final Bundle bundle = getArguments();
        encounter = (Encounter) bundle.getSerializable(BUNDLE_KEY_ENT);

        chiefComplaintService = new ChiefComplaintServiceLocalImpl(context);
        encounterService = new EncounterServiceLocalImpl(context);

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
        chiefComplaints = chiefComplaintService.getComplaints(encounter.getEncounter_key());
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
                Intent intent = new Intent(context, ProblemDescriptionEditActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString(BUNDLE_KEY_DESC, problemDescriptionTextView.getText().toString());
                intent.putExtras(bundle1);

                startActivityForResult(intent, EDIT_PROBLEM_DESC_REQUEST);
            }
        });

        String desc = encounterService.getProblemsDescription(encounter.getEncounter_key());
        problemDescriptionTextView.setText(desc);

        //initial history problem view
        historyProblemTextView = (TextView) problemsView.findViewById(R.id.id_problems_history_problem);

        //initial physical exam section
        physicalExamTextView = (TextView) problemsView.findViewById(R.id.id_problems_physical_exam);

        return problemsView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        long ent_key = encounter.getEncounter_key();

        if (requestCode == ADD_COMPLAINT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
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
        } else if (requestCode == EDIT_PROBLEM_DESC_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                String problem_desc = bundle.getString(ProblemDescriptionEditActivity.PROBLEM_DESC_KEY);

                problemDescriptionTextView.setText(problem_desc);
                encounter.setProblem_description(problem_desc);
                encounterService.updateProblemsDescription(ent_key, problem_desc);
            }
        } else if(requestCode == EDIT_COMPLAINT_REQUEST){
            if(resultCode == Activity.RESULT_OK){
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
